package com.lzkui.service.impl;

import com.lzkui.service.ZookeeperService;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryForever;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;

import java.net.InetAddress;
import java.util.List;

/**
 *
 *
 *
 *
 *
 * <p>
 * 修改历史:                                                                                    &lt;br&gt;
 * 修改日期             修改人员        版本                     修改内容
 * --------------------------------------------------------------------
 * 2018年09月21日 下午2:12   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class ZookeeperServiceImpl implements ZookeeperService{

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(ZookeeperServiceImpl.class);


	private CuratorFramework client;
	private String zookeeperServer;
	private int sessionTimeoutMs;
	private int connectionTimeoutMs;
	private int baseSleepTimeMs;
	private int maxRetries;

	private int retryInterval;

	public int getRetryInterval() {
		return retryInterval;
	}

	public void setRetryInterval(int retryInterval) {
		this.retryInterval = retryInterval;
	}

	/**

	 * 权限密码
	 */
	private String auth;

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public void setZookeeperServer(String zookeeperServer) {
		this.zookeeperServer = zookeeperServer;
	}

	public String getZookeeperServer() {
		return zookeeperServer;
	}

	public void setSessionTimeoutMs(int sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}

	public int getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	public void setConnectionTimeoutMs(int connectionTimeoutMs) {
		this.connectionTimeoutMs = connectionTimeoutMs;
	}

	public int getConnectionTimeoutMs() {
		return connectionTimeoutMs;
	}

	public void setBaseSleepTimeMs(int baseSleepTimeMs) {
		this.baseSleepTimeMs = baseSleepTimeMs;
	}

	public int getBaseSleepTimeMs() {
		return baseSleepTimeMs;
	}

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public void init() {
		try {

			RetryPolicy retryPolicy = new RetryForever(retryInterval);
			Builder builder = CuratorFrameworkFactory.builder().connectString(zookeeperServer).retryPolicy(retryPolicy)
					.sessionTimeoutMs(sessionTimeoutMs).connectionTimeoutMs(connectionTimeoutMs);
			if (StringUtils.isNotBlank(auth))
				builder.authorization("digest", auth.getBytes());
			client = builder.build();
			client.start();
			register();
		}catch (Exception e)
		{
			LOG.error("zk strat error",e);
		}
	}

	public void stop() {
		client.close();
	}

	public CuratorFramework getClient() {
		return client;
	}

	private void register() {
		try {
			String rootPath = "/" + "services";
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			String serviceInstance = "prometheus" + "-" + hostAddress + "-";
			//	client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(rootPath + "/" +
			// serviceInstance);

			// 增加eventListener
			client.getCuratorListenable().addListener(new CuratorListener() {
				@Override public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
					if (event.getWatchedEvent() != null) {
						EventType eventType = event.getWatchedEvent().getType();
						if (eventType == EventType.NodeDataChanged || eventType == EventType.NodeChildrenChanged) {
							//	ZookeeperRegistry.getRegistry().childChanged(event.getPath());
							LOG.info("the event path is :" + event.getPath());
						}
					}
				}
			});

			// 增加stateListener
			client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
				@Override public void stateChanged(CuratorFramework client, ConnectionState state) {
					//	ZookeeperRegistry.getRegistry().stateChanged(state);
					LOG.info("the zk state is :" + state.name());
					String text=state.name();


				}
			});

		} catch (Exception e) {
			LOG.error("注册出错", e);
		}
	}


	@Override public void create(String path, boolean ephemeral) {
		int i = path.lastIndexOf("/");
		if (i > 0) {
			create(path.substring(0, i), false);
		}
		if (ephemeral) {
			createPath(path, CreateMode.EPHEMERAL);
		} else {
			createPath(path, CreateMode.PERSISTENT);
		}
	}
	/**
	 * 递归创建节点, 若路径已存在, 则更新节点数据
	 *
	 * @param path	节点path
	 * @param createMode	创建的节点类型
	 * @return	成功-true 失败-false
	 */
	private void createPath(String path, CreateMode createMode) {
		try {
			this.client.create().withMode(createMode).forPath(path);
		} catch (NodeExistsException e) {
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}



	@Override public void releaseConnection() {

	}

	@Override public void delete(String path) {

	}

	/**
	 * 读取指定节点数据内容
	 *
	 * @param path    节点path
	 * @param watcher    watcher监听
	 * @return 节点数据
	 *
	 */
	@Override public String readData(String path, boolean watcher) {
		try {
			String data = "";
			if (!watcher) {
				data = new String(this.client.getData().forPath(path));
			} else {
				data = new String(this.client.getData().watched().forPath(path));
			}
			return data;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * 更新指定节点数据内容
	 *
	 * @param path    节点path
	 * @param data    数据内容
	 * @return 成功-true 失败-false
	 *
	 */
	@Override public boolean writeData(String path, String data) {
		try {
			this.client.setData().forPath(path, data.getBytes());
			return true;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * 判断指定path是否存在
	 *
	 * @param path    指定的路径
	 * @param watcher    是否需要watcher
	 * @return 返回路径上的状态
	 *
	 */
	@Override public Stat exists(String path, boolean watcher) {
		try {
			if (!watcher) {
				return this.client.checkExists().forPath(path);
			} else {
				return this.client.checkExists().watched().forPath(path);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * 获取path的下级节点
	 *
	 * @param path    指定的路径
	 * @param watcher    watcher监听
	 * @return 返回下级节点列表
	 *
	 *
	 * */
	@Override public List<String> getChildren(String path, boolean watcher) {
		try {
			if (!watcher) {
				return this.client.getChildren().forPath(path);
			} else {
				return this.client.getChildren().watched().forPath(path);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * 删除指定节点, 包括下级子节点
	 *
	 * @param path    节点path
	 *
	 */
	@Override public void truncateNode(String path) {
		_truncateNode(path);
	}

	/**
	 * 递归删除子节点
	 *
	 * @param path    节点路径
	 */
	private void _truncateNode(String path) {

	}
}

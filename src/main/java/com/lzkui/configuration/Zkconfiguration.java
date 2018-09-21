package com.lzkui.configuration;

import com.lzkui.service.ZookeeperService;
import com.lzkui.service.impl.ZookeeperServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
 * 2018年09月21日 上午10:11   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
@Configuration
public class Zkconfiguration {
	@Value("${zooKeeper.connectionString}")
	private String zookeeperServer;
	@Value(("${zooKeeper.sessionTimeoutMs}"))
	private int sessionTimeoutMs;
	@Value("${zookeeper.connectionTimeoutMs}")
	private int connectionTimeoutMs;
	@Value("${zookeeper.maxRetries}")
	private int maxRetries;
	@Value("${zookeeper.baseSleepTimeMs}")
	private int baseSleepTimeMs;
	@Value("${zooKeeper.auth}")
	private String auth;

	@Value("${zookeeper.retryInterval}")
	private int retryInterval;

	@Bean(initMethod = "init", destroyMethod = "stop")
	public ZookeeperService zkClient() {
		ZookeeperServiceImpl zkClient = new ZookeeperServiceImpl();
		zkClient.setZookeeperServer(zookeeperServer);
		zkClient.setSessionTimeoutMs(sessionTimeoutMs);
		zkClient.setConnectionTimeoutMs(connectionTimeoutMs);
		zkClient.setMaxRetries(maxRetries);
		zkClient.setBaseSleepTimeMs(baseSleepTimeMs);
		zkClient.setRetryInterval(retryInterval);
		if(!StringUtils.isBlank(auth))
		{
			zkClient.setAuth(auth);
		}


		return zkClient;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public int getBaseSleepTimeMs() {
		return baseSleepTimeMs;
	}

	public void setBaseSleepTimeMs(int baseSleepTimeMs) {
		this.baseSleepTimeMs = baseSleepTimeMs;
	}

	public int getConnectionTimeoutMs() {
		return connectionTimeoutMs;
	}

	public void setConnectionTimeoutMs(int connectionTimeoutMs) {
		this.connectionTimeoutMs = connectionTimeoutMs;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public int getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	public void setSessionTimeoutMs(int sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}

	public String getZookeeperServer() {
		return zookeeperServer;
	}

	public void setZookeeperServer(String zookeeperServer) {
		this.zookeeperServer = zookeeperServer;
	}
}

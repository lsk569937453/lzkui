package com.lzkui.service;

import org.apache.zookeeper.data.Stat;

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
 * 2018年09月21日 上午10:26   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public interface ZookeeperService {
	/**
	 *
	 * 创建zk路径
	 *
	 * @param path
	 * @param ephemeral：是否为短暂性节点
	 */
	void create(String path, boolean ephemeral);

	/**
	 * 删除指定节点
	 *
	 * @param path    节点path
	 */
	void delete(String path);

	/**
	 * 获取path的下级节点
	 *
	 * @param path    指定的路径
	 * @param watcher    watcher监听
	 * @return 返回下级节点列表
	 */
	List<String> getChildren(String path, boolean watcher);

	/**
	 * 关闭ZK连接
	 */
	void releaseConnection();

	/**
	 * 读取指定节点数据内容
	 *
	 * @param path    节点path
	 * @param watcher    watcher监听
	 * @return 节点数据
	 */
	String readData(String path, boolean watcher);

	/**
	 * 更新指定节点数据内容
	 *
	 * @param path    节点path
	 * @param data    数据内容
	 * @return 成功-true 失败-false
	 */
	boolean writeData(String path, String data);

	/**
	 * 判断指定path是否存在
	 *
	 * @param path    指定的路径
	 * @param watcher    是否需要watcher
	 * @return 返回路径上的状态
	 */
	Stat exists(String path, boolean watcher);

	/**
	 * 删除指定节点, 包括下级子节点
	 *
	 * @param path    节点path
	 */
	void truncateNode(String path);
}

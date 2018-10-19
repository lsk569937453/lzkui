package com.lzkui.entity;

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
 * 2018年10月19日 下午4:17   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class ZkNodePropertySave {
	public String path;
	public String nodeProperty;

	public String getNodeProperty() {
		return nodeProperty;
	}

	public void setNodeProperty(String nodeProperty) {
		this.nodeProperty = nodeProperty;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}

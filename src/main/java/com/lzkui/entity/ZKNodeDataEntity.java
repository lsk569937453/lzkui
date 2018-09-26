package com.lzkui.entity;

import org.apache.zookeeper.data.Stat;

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
 * 2018年09月26日 上午10:21   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class ZKNodeDataEntity {
	private Stat stat;
	private String data;

	private int statCode=0;

	private int dataCode=0;

	public int getDataCode() {
		return dataCode;
	}

	public void setDataCode(int dataCode) {
		this.dataCode = dataCode;
	}

	public int getStatCode() {
		return statCode;
	}

	public void setStatCode(int statCode) {
		this.statCode = statCode;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Stat getStat() {
		return stat;
	}

	public void setStat(Stat stat) {
		this.stat = stat;
	}
}

package com.lzkui.controller;

import com.alibaba.fastjson.JSON;
import com.lzkui.entity.BaseResponse;
import com.lzkui.entity.PathEntity;
import com.lzkui.entity.ZKNodeDataEntity;
import com.lzkui.entity.ZkAddNodeReqEntity;
import com.lzkui.entity.ZkNodePropertySave;
import com.lzkui.service.ZookeeperService;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
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
 * 2018年09月21日 下午2:26   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
@Controller
@RequestMapping("/zk")
public class Zkcontroller {
	@Autowired
	private ZookeeperService zookeeperService;

	@RequestMapping(value = "/getChildren",method = RequestMethod.POST)
	@ResponseBody
	public String getData(@RequestBody PathEntity path)
	{
		List<String> resultList=zookeeperService.getChildren(path.getPath(),false);
		Collections.sort(resultList);

		return JSON.toJSONString(resultList);

	}
	@RequestMapping(value = "/getPathData",method = RequestMethod.POST)
	@ResponseBody
	public String getPathData(@RequestBody PathEntity path)
	{

		ZKNodeDataEntity zkNodeDataEntity=new ZKNodeDataEntity();
		try {
			Stat stat = zookeeperService.exists(path.getPath(), false);
			zkNodeDataEntity.setStat(stat);

		}catch (Throwable e)
		{
			zkNodeDataEntity.setStatCode(-1);
		}
		try {

			String resultList = zookeeperService.readData(path.getPath(), false);
			zkNodeDataEntity.setData(resultList);

		}catch (Exception e)
		{
			zkNodeDataEntity.setDataCode(-1);
		}



		return JSON.toJSONString(zkNodeDataEntity);

	}
	@RequestMapping(value = "/savePathproperty",method = RequestMethod.POST)
	@ResponseBody
	public String saveNodeProperty(@RequestBody ZkNodePropertySave zkNodePropertySave)
	{
		BaseResponse response=new BaseResponse();
		try {

			boolean result = zookeeperService.writeData(zkNodePropertySave.getPath(), zkNodePropertySave.getNodeProperty());

			if(result!=true)
				response.setRetcode(-1);
		}catch (Exception e)
		{
			response.setRetcode(-1);
		}

		return JSON.toJSONString(response);

	}
	@RequestMapping(value = "/addNode",method = RequestMethod.POST)
	@ResponseBody
	public String saveNodeProperty(@RequestBody ZkAddNodeReqEntity zkAddNodeReqEntity)
	{
		BaseResponse response=new BaseResponse();
		try {
			String newPath=zkAddNodeReqEntity.getPath()+"/"+zkAddNodeReqEntity.getNodeValue();

			zookeeperService.create(newPath, false);

		}catch (Exception e)
		{
			response.setRetcode(-1);
		}

		return JSON.toJSONString(response);

	}
	@RequestMapping(value = "/deleteNode",method = RequestMethod.POST)
	@ResponseBody
	public String deleteNode(@RequestBody PathEntity pathEntity)
	{
		BaseResponse response=new BaseResponse();
		try {


			zookeeperService.delete(pathEntity.path);

		}catch (Exception e)
		{
			response.setRetcode(-1);
		}

		return JSON.toJSONString(response);

	}

}

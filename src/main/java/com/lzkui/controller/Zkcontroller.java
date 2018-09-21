package com.lzkui.controller;

import com.alibaba.fastjson.JSON;
import com.lzkui.service.ZookeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class Zkcontroller {
	@Autowired
	private ZookeeperService zookeeperService;

	@RequestMapping(value = "/getData",method = RequestMethod.POST)
	@ResponseBody
	public String getData(@RequestParam("path")String path)
	{
		List<String> resultList=zookeeperService.getChildren(path,false);

		return JSON.toJSONString(resultList);

	}
}

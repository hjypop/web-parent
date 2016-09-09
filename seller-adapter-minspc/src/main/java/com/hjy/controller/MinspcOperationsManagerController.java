package com.hjy.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hjy.service.operations.IOperationsManagerService;

/**
 * 
 * @title: com.hjy.controller.MinspcOperationsManagerController.java 
 * @descriptions 执行运营人员的线上临时需求，如：一批商品下架、一批商品上架等等。
 * 这个类中的所有接口全部用于处理运营人员的线上临时需求，不对外开放。
 *
 * @author Yangcl
 * @date 2016年9月9日 下午1:59:03 
 * @version 1.0.0
 */
@Controller
@RequestMapping("minspc")
public class MinspcOperationsManagerController {
	
	private static Logger logger=Logger.getLogger(MinspcOperationsManagerController.class);
	
	
	@Autowired
	private IOperationsManagerService service;
	
	
}










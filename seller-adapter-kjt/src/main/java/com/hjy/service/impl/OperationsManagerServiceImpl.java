package com.hjy.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.system.IScFlowBussinessHistoryDao;
import com.hjy.dao.system.IScFlowBussinesstypeDao;
import com.hjy.dao.system.IScFlowStatuschangeDao;
import com.hjy.service.IOperationsManagerService;

/**
 * @descriptions 执行运营人员的线上临时需求，如：一批商品下架、一批商品上架等等。
 *               这个类中的所有接口全部用于处理运营人员的线上临时需求，不对外开放。
 * 
 * @date 2016年8月15日下午5:08:01
 * @author Yangcl
 * @version 1.0.1
 */
@Service
public class OperationsManagerServiceImpl extends BaseServiceImpl<Object, Integer> implements IOperationsManagerService {
	
	@Resource
	private IScFlowBussinesstypeDao scFlowBussinesstypeDao;

	@Resource
	private IScFlowStatuschangeDao scFlowStatuschangeDao;
	
	@Resource
	private IScFlowBussinessHistoryDao scFlowBussinessHistoryDao;
	
	
	
	/**
	 * @descriptions 跨境通商品批量上架
	 * 
	 * @date 2016年8月15日下午5:10:43
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
	public JSONObject upStorage(String excelPath) {
		List<String> list = this.readExcel(excelPath);
		
		
		
		return null;
	}

	// for up_storage
	private List<String> readExcel(String fileName) {
		List<String> list = new ArrayList<>();

		boolean isE2007 = false; // 判断是否是excel2007格式
		if (fileName.endsWith("xlsx"))
			isE2007 = true;
		try {
			InputStream input = new FileInputStream(fileName); // 建立输入流
			Workbook wb = null;
			// 根据文件格式(2003或者2007)来初始化
			if (isE2007)
				wb = new XSSFWorkbook(input);
			else
				wb = new HSSFWorkbook(input);
			Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
			Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
			while (rows.hasNext()) {
				Row row = rows.next(); // 获得行数据
				if (row.getCell(14) != null && row.getCell(14).toString().trim().equals("否")) {
					list.add(String.valueOf(row.getCell(1)));
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return list;
	}

}

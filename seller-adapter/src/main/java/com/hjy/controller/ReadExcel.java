package com.hjy.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;

public class ReadExcel {

	public static void main(String[] args) {
		List<String> list = readExcel("D:\\7.xlsx"); 
	}
	
	private static List<String> readExcel(String fileName) {
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
				if (row.getCell(0) != null && !row.getCell(0).toString().trim().equals("uid")) {
					list.add(String.valueOf(row.getCell(1))); 
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		String json = JSON.toJSONString(list);

		System.out.println(json); 
		return list;
	}
}

package com.hjy.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class ReadExcel {

	public static void main(String[] args) {
//		Map<String , List<String>> map = readExcel("D:\\2.xlsx");   
		readProduct("D:\\5.xlsx");               
	}
	
	
	private static List<String> readProduct(String fileName) {
		Set<String> list = new HashSet();
		boolean isE2007 = false;  
		if (fileName.endsWith("xlsx"))
			isE2007 = true;
		try {
			InputStream input = new FileInputStream(fileName); // 建立输入流
			Workbook wb = null;
			if (isE2007){
				wb = new XSSFWorkbook(input);
			}else{
				wb = new HSSFWorkbook(input);
			}
			DecimalFormat df = new DecimalFormat("0");
			for(int n = 0 ; n < wb.getNumberOfSheets() ; n ++){
				Sheet sheet = wb.getSheetAt(n); // 获得第n个表单
				Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
				while (rows.hasNext()) {
					Row row = rows.next(); // 获得行数据
//					if (row.getCell(0) != null && !row.getCell(0).toString().trim().equals("商品编号")) {
//					}
					Cell cell = row.getCell(0);
					String value = "";     
					if(StringUtils.isNotBlank(cell.toString())){
						value = df.format(Double.valueOf(cell.toString()));   
					}else{
						continue;
					}
					list.add(value);
				}
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
//		String json = JSON.toJSONString(list); 
//		System.out.println(json); 
		System.out.println("list = " + list.size());  
		
		String json = "[\"8016478479\",\"7016102694\",\"8016478477\",\"7016102697\",\"8016478478\",\"7016102696\",\"8016478475\",\"8016478426\",\"7016102691\",\"8016478476\",\"7016102690\",\"8016478428\",\"7016102693\",\"7016102692\",\"7016102603\",\"7016102604\",\"7016102601\",\"7016102602\",\"7016102595\",\"7016102598\",\"7016102597\",\"8016478260\",\"8016466394\",\"7016102716\",\"7016102715\",\"7016102714\",\"7016102713\",\"7016102711\",\"8016478268\",\"8016478436\",\"7016102688\",\"7016102719\",\"7016102689\",\"7016102717\",\"8016478466\",\"7016102635\",\"7016102300\",\"8016476540\",\"8016478270\",\"7016102720\",\"7016102543\",\"8016478425\",\"7016102708\",\"7016102709\",\"7016102706\",\"7016102707\",\"7016102704\",\"8016478490\",\"7016102705\",\"7016102702\",\"7016102703\",\"7016102700\",\"7016102701\",\"8016478491\",\"8016478245\",\"7016102473\",\"8016478247\",\"8016478246\",\"8016478248\",\"7016102214\",\"7016102219\",\"8016478484\",\"8016478485\",\"8016478437\",\"8016478489\",\"7016102235\",\"8016478480\",\"7016102615\",\"7016102616\",\"8016478483\",\"8016478256\",\"8016478254\",\"8016478253\",\"8016478259\",\"8016478258\",\"7016101662\",\"8016478294\",\"8016467560\",\"7016102698\",\"7016102699\",\"8016478252\",\"8016478251\",\"7016102229\",\"8016478445\",\"8016478250\",\"7016102599\"]";
		
		List<String> err = JSONArray.parseArray(json, String.class);
		System.out.println("err = " + err.size());  
		
		List<String> res = new ArrayList<>();
		for(String l : list){
			for(String e : err){
				if(l.equals(e)){
					res.add(e);
				}
			}
		}
		
		System.out.println("res = " + res.size());   
		
		
		
		String str = "";
		for(String s : list){
			str += "'" + s + "',";
		}
		str = str.substring(0 , str.length() -1);
		System.out.println(str); 
		
		return null;   
	}
	
	
	
	
	
	private static Map<String , List<String>> readExcel(String fileName) {
		//        seller_code  List<product_code@税率>
		Map<String , List<String>> map = new HashMap<String , List<String>>();
		boolean isE2007 = false;  
		if (fileName.endsWith("xlsx"))
			isE2007 = true;
		try {
			InputStream input = new FileInputStream(fileName); // 建立输入流
			Workbook wb = null;
			if (isE2007){
				wb = new XSSFWorkbook(input);
			}else{
				wb = new HSSFWorkbook(input);
			}
			DecimalFormat df = new DecimalFormat("0");
			for(int n = 0 ; n < wb.getNumberOfSheets() ; n ++){
				Sheet sheet = wb.getSheetAt(n); // 获得第n个表单
				Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
				while (rows.hasNext()) {
					Row row = rows.next(); // 获得行数据
					if (row.getCell(0) != null && !row.getCell(0).toString().trim().equals("商品编号")) {
						Cell cell = row.getCell(0);
						String value = df.format(Double.valueOf(cell.toString())) + "@" + row.getCell(3).toString().split("%")[0];     
						String sscode = String.valueOf(row.getCell(4));
						if(map.containsKey(sscode)){ 
							map.get(sscode).add( value );
						}else{
							List<String> list = new ArrayList<>();
							list.add( value );
							map.put( String.valueOf(row.getCell(4)) , list);	 
						}
					}
				}
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		String json = JSON.toJSONString(map);
		System.out.println(json); 
		return map;  
	}
	
	
	private static String getHour(Date date , int flag){
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.HOUR , flag); 
		 date=calendar.getTime();  
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		 
		 return formatter.format(date);
	}
}

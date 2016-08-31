package com.hjy.controller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hjy.annotation.ExculdeNullField;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.request.data.OrderInfoInsert;

public class Testes {

	public static void main(String[] args) { 
		
//		new Testes().aaa(); 
		
		String stime = "2016-08-03 16:09:00";
		String etime = "2016-08-04 16:09:00";
		
		int a = stime.compareTo(etime);
		System.out.println(a);
		
		int b = etime.compareTo(stime);
		System.out.println(b);
		
	}
	
	public void aaa(){
		List<OrderInfoInsert> list = null;
		try {
			list = JSON.parseArray(DataInit.orderInfoBatchInsertTest().getData(), OrderInfoInsert.class);
		} catch (Exception e) { 
			
		}
		for(OrderInfoInsert i : list){
			OcOrderinfo e = new OcOrderinfo();
			e.setOrderCode("asdfadsf"); 
			this.validate(i, e);
		}
	}

	
	private  <T , E> boolean validate(T t , E e){
		Field[] fields = t.getClass().getDeclaredFields();
		 try {
			 for(int i = 0 ; i < fields.length ; i ++){
				 Field field = fields[i];
				 String name = field.getName();
				 String func = "get" + name.substring(0,1).toUpperCase()+name.substring(1);
				 Method m = t.getClass().getMethod(func);
	             String value = String.valueOf(m.invoke(t)); 
	             if( !field.isAnnotationPresent(ExculdeNullField.class) && StringUtils.isBlank(value) ){    
	            	 // 如果包含此标签则排除该字段的验证
	            	 return false;
	             } 
	             
	             // 赋值 
	             String func_ = "set" + name.substring(0,1).toUpperCase()+name.substring(1);
				 Method m_ = t.getClass().getMethod(func_  , m.invoke(t).getClass());
				 @SuppressWarnings("rawtypes")
				Class[] c = m_.getParameterTypes();
				 if(c[0] == String.class) {
		 	 		 m_.invoke(e , value);
		 	 		 break;
		 	 	 }else if(c[0] == BigDecimal.class) {
		 	 		 m_.invoke(e , BigDecimal.valueOf(Long.valueOf(value)));
		 	 		 break;
		 	 	 }else if(c[0] == Integer.class) {
					 m_.invoke(e ,Integer.valueOf(value));
					 break;
			 	 }else if(c[0] == Boolean.class) {
		 	 		 m_.invoke(e , Boolean.valueOf(value));
		 	 		 break;
		 	 	 }else if(c[0] == Float.class){
		 		 	 m_.invoke(e , Float.valueOf(value));
                     break;
		 	 	 }else if(c[0] == Double.class) {
	 	 		 	 m_.invoke(e , Double.valueOf(value));
	 	 		 	 break;
		 	 	 }else if(c[0] == Byte.class) {
		 	 		 m_.invoke(e , Byte.valueOf(value));
		 	 		 break;
		 	 	 }
			 }
		 } catch (NoSuchMethodException ex) {
			 ex.printStackTrace();
		 } catch (SecurityException ex) {
			 ex.printStackTrace();
		 } catch (IllegalAccessException ex) {
			 ex.printStackTrace();
		 } catch (IllegalArgumentException ex) {
			 ex.printStackTrace();
		 } catch (InvocationTargetException ex) {
			 ex.printStackTrace();
		 } 
		 
		return true;
	}
}

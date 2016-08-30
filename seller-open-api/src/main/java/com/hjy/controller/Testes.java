package com.hjy.controller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.SystemOutLogger;

import com.alibaba.fastjson.JSON;
import com.hjy.annotation.ExculdeNullField;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.request.data.OrderInfoInsert;

public class Testes {

	public static void main(String[] args) { 
		new Testes().aaa(); 
	}
	
	
	
	public void aaa(){
		List<OrderInfoInsert> list = null;
		try {
			list = JSON.parseArray(DataInit.orderInfoBatchInsertTest().getData(), OrderInfoInsert.class);
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
		
		List<OcOrderinfo> oList = new ArrayList<>();
		for(OrderInfoInsert i : list){
			OcOrderinfo e = new OcOrderinfo(); 
			this.validate(i, e);
			
			oList.add(e);
		}
		
		System.out.println("|||||||||||||||||||||||||||||"); 
	}

	
	
	
	/**
	 * @descriptions  验证对象中的值是否合法并赋值
	 * 
	 * @param t 要验证的对象
	 * @param entity 要赋值的对象
	 * @return entity
	 * @date 2016年8月29日下午12:10:38
	 * @author Yangcl 
	 * @version 1.0.0.1
	 * @param <E>
	 */
	private  <T , E> boolean validate(T t , E e){
		Field[] fields = t.getClass().getDeclaredFields();
		 try {
			 for(int i = 0 ; i < fields.length ; i ++){
				 Field field = fields[i];
				 String name = field.getName();
				 String func = "get" + name.substring(0,1).toUpperCase()+name.substring(1);
				 Method m = t.getClass().getMethod(func);
	             String value = String.valueOf(m.invoke(t)); 
	             if(value.equals("null")) {
	            	 value = null;
	             }
	             if( !field.isAnnotationPresent(ExculdeNullField.class) && StringUtils.isBlank(value) ){    
	            	 // 不包含此标签则是必传字段，如果没传值则认为此条记录错误
	            	 return false; 
	             }else if(field.isAnnotationPresent(ExculdeNullField.class) && StringUtils.isBlank(value) ){
	            	 // ExculdeNullField注解标识的字段为空，则不再对其反射设值。
	            	 continue;
	             }
	             
	             // 赋值 
	             String func_ = "set" + name.substring(0,1).toUpperCase()+name.substring(1); 
				 Method m_ = e.getClass().getMethod(func_  , m.invoke(t).getClass());
				 @SuppressWarnings("rawtypes")
				Class[] c = m_.getParameterTypes();
				 if(c[0] == String.class) {
		 	 		 m_.invoke(e , value);
		 	 	 }else if(c[0] == BigDecimal.class) {
		 	 		 m_.invoke(e , BigDecimal.valueOf(Double.valueOf(value)));
		 	 	 }else if(c[0] == Integer.class) {
					 m_.invoke(e ,Integer.valueOf(value));
			 	 }else if(c[0] == Boolean.class) {
		 	 		 m_.invoke(e , Boolean.valueOf(value));
		 	 	 }else if(c[0] == Float.class){
		 		 	 m_.invoke(e , Float.valueOf(value));
		 	 	 }else if(c[0] == Double.class) {
	 	 		 	 m_.invoke(e , Double.valueOf(value));
		 	 	 }else if(c[0] == Byte.class) {
		 	 		 m_.invoke(e , Byte.valueOf(value));
		 	 	 }
			 }
		 } catch (NoSuchMethodException ex) {
//			 ex.printStackTrace();  不做处理即可
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

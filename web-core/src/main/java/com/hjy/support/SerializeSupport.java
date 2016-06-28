package com.hjy.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Blob;

import org.apache.commons.lang.StringUtils;

import com.hjy.helper.FormatHelper;
import com.hjy.model.MDataMap;

//import com.srnpr.zapcom.basehelper.FormatHelper;
//import com.srnpr.zapcom.basehelper.TestHelper;
//import com.srnpr.zapcom.basemodel.MDataMap;


public class SerializeSupport<T>  {

	public T serialize(MDataMap mDataMap, T t) {

		MDataMap mSerializeMap = MapSupport.INSTANCE.upSerializeMap(mDataMap);

		Class<?> c = null;

		try {

			for (Field field : t.getClass().getDeclaredFields()) {

				String sFieldName = FormatHelper.upReplaceSerialize(field
						.getName());

				if (mSerializeMap.containsKey(sFieldName)) {
				
					field.setAccessible(true);
					String sValue=mSerializeMap.get(sFieldName);
					
					
					String sType=field.getType().getName();
					
					if(sType.endsWith("long"))
					{
						field.set(t, Long.parseLong(sValue));
					}
					else if(sType.equals("int")||sType.endsWith("Integer"))
					{
						field.set(t, Integer.parseInt(sValue));
					}
					else if(sType.equals("float"))
					{
						field.set(t, Float.parseFloat(sValue));
					}
					else if(sType.equals("BigDecimal")||sType.endsWith("BigDecimal")){
						if(sValue==null||"".equals(sValue)){
							field.set(t, new BigDecimal(0.00));
						}else {
							field.set(t, new BigDecimal(sValue));
						}
					}
					else
					{
						field.set(t, sValue);
					}
					
					
				
				
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		/*
		 * 
		 * Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
		 * .getGenericSuperclass()).getActualTypeArguments()[0];
		 * 
		 * T t = (T) entityClass;
		 */

		return t;
	}

}

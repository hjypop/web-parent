package com.hjy.system;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.ExculdeNullField;
import com.hjy.annotation.Inject;
import com.hjy.cache.RootCache;
import com.hjy.dao.webcore.IWcOpenApiDao;
import com.hjy.dao.webcore.IWcSellerApiDao;
import com.hjy.dao.webcore.IWcSellerinfoDao;
import com.hjy.entity.webcore.WcOpenApi;
import com.hjy.entity.webcore.WcSellerApi;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.system.cmodel.CacheWcOpenapi;
import com.hjy.system.cmodel.CacheWcSellerInfo;

/**
 * @description: 对api信息的缓存化提供支持
 * 
 * @author Yangcl
 * @date 2016年12月22日 下午3:46:12 
 * @version 1.0.0
 */
public class OpenApiEcacheSupport  extends RootCache<String, String> {

	@Inject
	private IWcOpenApiDao wcOpenApiDao;
	@Inject
	private IWcSellerinfoDao wcSellerInfoDao;
	@Inject
	private IWcSellerApiDao wcSellerApiDao;
	
	public final static OpenApiEcacheSupport Instance = new OpenApiEcacheSupport();
	
	/**
	 * @description: 首先加载 wc_openapi 表的信息到ecache
	 * 
	 * @author Yangcl 
	 * @date 2016年12月22日 下午5:08:38 
	 * @version 1.0.0.1
	 */
	public void refresh() {
		List<WcOpenApi> woaList = wcOpenApiDao.selectAllInfo(null);
		for(WcOpenApi i : woaList){
			String value = JSONObject.toJSONString(new CacheWcOpenapi(i.getMethod(), i.getApiName(), i.getApiCode(), i.getStatus(), i.getDescription(), i.getIsDeleted()));
			this.inElement(i.getApiCode(), value);
			this.inElement(i.getMethod(), value); // 分别以code 和 method为key进行存储    
		}
		
		List<WcSellerinfo> sinfoList = wcSellerInfoDao.queryPage(null);  
		List<WcSellerApi> apiList = wcSellerApiDao.queryPage(null);   
		for(WcSellerinfo s : sinfoList){
			CacheWcSellerInfo c = new CacheWcSellerInfo();
			List<CacheWcOpenapi> apis_ = new ArrayList<CacheWcOpenapi>();
			if(this.validate(s, c)){
				for(WcSellerApi a : apiList){
					CacheWcOpenapi cw = new CacheWcOpenapi();
					if(c.getSellerCode().equals(a.getSellerCode())){
						cw = JSONObject.parseObject(this.getValue(a.getApiCode()) , CacheWcOpenapi.class);   
						apis_.add(cw);
					}
				}
			}else{
				System.out.println("【" +s.getSellerName() + "】 缓存初始化异常，必要字段丢失！"); 
			}
			c.setApis(apis_); 
			this.inElement(s.getSellerCode(), JSONObject.toJSONString(c)); // 验证通过放入缓存
		}
		
	}
	 
	public String getOne(String k) {
		return null; 
	}

	/**
	 * @descriptions  验证对象中的值是否合法并为E e 对象赋值
	 * 
	 * T 与 E 两个对象字段名称需要一致。比如 T 中拥有 E 中一半的字段，但这一半字段名称类型都相同。
	 *  
	 * @param t 要验证的对象
	 * @param e 要赋值的对象|如果要赋值的对象为 null 则只验证，不赋值
	 * @return true or false
	 * @date 2016年8月29日下午12:10:38
	 * @author Yangcl 
	 * @version 1.0.0.1
	 * @param <T , E>
	 */
	private  <T , E> boolean validate(T t , E e){
		Field[] fields = t.getClass().getDeclaredFields();
		 try {
			 for(int i = 0 ; i < fields.length ; i ++){
				 Field field = fields[i];
				 String name = field.getName();
				 String func = "get" + name.substring(0,1).toUpperCase()+name.substring(1);
				 Method m = t.getClass().getMethod(func);
	             if(m.invoke(t) == null) {  // 如果getter方法取值为null，则代表T对象该字段为null，不再操作
	            	 continue;
	             }
	             String value = String.valueOf(m.invoke(t)); 
	             if(field.isAnnotationPresent(ExculdeNullField.class) && StringUtils.isBlank(value) ){
	            	 // ExculdeNullField 注解标识的字段为空，则不再对其反射设值。
	            	 continue;
	             }else if( e == null){  // 如果要赋值的对象为null 则只验证，不赋值
	                 continue;
	             }
	             
	             // 赋值 
	             String func_ = "set" + name.substring(0,1).toUpperCase()+name.substring(1); 
				 Method m_ = e.getClass().getMethod(func_  , m.invoke(t).getClass()); 
	             // 如果这里是t.getClass() 会引起 object is not an instance of declaring class 这个异常。
	             // 原因在于 T 对象有值了，你还在尝试对他赋值。
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
	            // ex.printStackTrace();  被赋值的对象中找不到的方法不做处理即可
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

























































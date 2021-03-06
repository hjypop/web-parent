package com.hjy.job;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;

import com.hjy.annotation.ExculdeNullField;
import com.hjy.annotation.Inject;
import com.hjy.dao.order.IOcKjSellerCustomsDeclarationDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.dao.webcore.IWcSellerinfoDao;
import com.hjy.dto.KjCustomsDeclarationDto;
import com.hjy.entity.order.OcKjSellerCustomsDeclaration;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.helper.WebHelper;
import com.hjy.quartz.job.RootJob;
import com.hjy.response.KjCustomsDeclarationResponse;


/**
 * @description: 对跨境商户的订单进行报关处理|将信息转存到表，等待微信和支付宝的定时任务去报关，然后更新该表中的状态
 * 
 * @author Yangcl
 * @date 2016年10月25日 下午2:56:48 
 * @version 1.0.0
 */
public class JobForKjCustomsDeclaration extends RootJob {
	
	private static Logger logger = Logger.getLogger(JobForKjCustomsDeclaration.class);
	
	@Inject
	private IOcKjSellerCustomsDeclarationDao dao;
	@Inject 
	private IOcOrderinfoDao orderInfoDao;
	@Inject 
	private IWcSellerinfoDao wcSellerinfoDao;
	
	private String startTime;
	private String endTime;

	public JobForKjCustomsDeclaration() {
		this.startTime = "2016-08-18 00:00:00";  // 测试数据 
		this.endTime = "2016-10-26 00:00:00";
	}

	public JobForKjCustomsDeclaration(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime; 
	}



	public void doExecute(JobExecutionContext context) {
		String lockCode = WebHelper.getInstance().addLock(1000 , "JobForKjCustomsDeclaration");	// 分布式锁定
		if (StringUtils.isNotBlank(lockCode)) {
			try {
				KjCustomsDeclarationDto dto = new KjCustomsDeclarationDto();
				if(StringUtils.isAnyBlank(this.startTime , this.endTime)){  // 非手动执行该定时任务 
					Date date = new Date(); 								 // 2016-09-18 16:26:08
					this.startTime = this.getHour(date , -1);   // 2016-09-18 15:00:00         带同步订单的开始时间
					this.endTime = this.getHour(date , 0);	     // 2016-09-18 16:00:00		  带同步订单的结束时间
				}
				if(this.compareDate(this.startTime , this.endTime)){  // 开始时间大于结束时间则返回
					return ;
				}
				dto.setStartTime(this.startTime);
				dto.setEndTime(this.endTime); 
				// 取出需要去报关的跨境商户列表
				List<WcSellerinfo> sscList = wcSellerinfoDao.getCustomsDeclarationSellerList();  
				if(sscList == null || sscList.size() == 0){
					return ; 
				}
				List<String> list_= new ArrayList<String>();
				for(WcSellerinfo s : sscList){
					list_.add(s.getSellerCode());
				}
				dto.setList(list_);  
				List<KjCustomsDeclarationResponse> list =  orderInfoDao.getKjCustomsDeclarationList(dto);
				if(list == null || list.size() == 0){
					return;
				}
				
				List<OcKjSellerCustomsDeclaration> insertList = new ArrayList<OcKjSellerCustomsDeclaration>();
				for(KjCustomsDeclarationResponse d : list){
					OcKjSellerCustomsDeclaration e = new OcKjSellerCustomsDeclaration();
					if(this.validate(d, e)){
						e.setUid(UUID.randomUUID().toString().replace("-", "")); 
						e.setFlag(0);
						if(e.getBankOrderId() != null && e.getBankOrderId().startsWith("20")){
							e.setType("alipay");
						}else if(e.getBankOrderId() != null && e.getBankOrderId().startsWith("40")){
							e.setType("wechat");
						}
						e.setCreateTime(new Date());
						e.setUpdateTime(new Date());
						e.setRemark("insert");
						insertList.add(e);
					}
				}
				//batch insert 
				dao.batchInsert(insertList);
			}catch (Exception e) {
				e.printStackTrace();  
			}finally {
				WebHelper.getInstance().unLock(lockCode);
			}
		}else{  // 分布式锁定中，请解锁
			logger.error("JobForKjCustomsDeclaration.java  分布式锁定中，请解锁"); 
		}
	}

	
	private String getHour(Date date , int flag){
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.HOUR , flag); 
		 date=calendar.getTime();  
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		 
		 return formatter.format(date);
	}
	
	private boolean compareDate(String a , String b){
		if(StringUtils.isAnyBlank(a , b)){
			return false;
		}
		return a.compareTo(b) > 0;
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
































package com.hjy.selleradapter.minspc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.Inject;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.dto.request.subscribeOrder.SoRequest;
import com.hjy.helper.DateHelper;
import com.hjy.request.data.MinspcOrderinfoRequest;
import com.hjy.response.MinspcOrderinfoResponse;


/**
 * @title: com.hjy.selleradapter.minspc.RsyncSubscribeOrder.java 
 * @description: 生成订阅订单（并发送海关）|完成接口请求等内容。
 *
 * @author Yangcl
 * @date 2016年9月6日 下午2:36:49 
 * @version 1.0.0
 */
public class RsyncSubscribeOrder extends RsyncMinspc{
	
	@Inject
	private IOcOrderinfoDao orderinfoDao;

	/**
	 * @description: TODO  
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月7日 下午2:58:26 
	 * @version 1.0.0.1
	 */
	public void doProcess(String responseJson) {
		// TODO 调用OpenApi 拼装响应数据
		
	}

	/**
	 * @description: TODO  
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月7日 下午2:58:26 
	 * @version 1.0.0.1
	 */
	public String getRequestMethod() {
		return "SubscribeOrder.Create";
	}

	
	/**
	 * @description: 拼装请求数据 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ | 此处暂时搁置
	 * 
	 * @author Yangcl
	 * @date 2016年9月7日 下午2:58:26 
	 * @version 1.0.0.1
	 */
	public String setRequestDataJson() {
		MinspcOrderinfoRequest dto = new MinspcOrderinfoRequest();
		String startTime = DateHelper.formatDateZero(new Date());  
		String endTime = this.getNextDate(new Date()); 
		List<MinspcOrderinfoResponse> morList = orderinfoDao.getMinspcOrderinfoList(dto);         // TODO  这里还需要联查出身份证信息，但是库里貌似没有身份证信息|此处暂时搁置
		
		List<SoRequest> list = new ArrayList<SoRequest>();
		
		SoRequest r = new SoRequest();
		
		return "";
	}

	/**
	 * @descriptions 获取第二天的时间
	*
	* @date 2016年8月9日 下午9:42:54
	* @author Yangcl 
	* @version 1.0.0.1
	 */
	private String getNextDate(Date date){
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE , 1);//把日期往后增加一天.整数往后推,负数往前移动
		 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		 
		 return formatter.format(date);
	}

}





























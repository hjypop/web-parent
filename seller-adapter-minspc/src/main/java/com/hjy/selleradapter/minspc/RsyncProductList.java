package com.hjy.selleradapter.minspc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.hjy.annotation.Inject;
import com.hjy.constant.MemberConst;
import com.hjy.dao.ILcRsyncMinspcProductDao;
import com.hjy.dto.request.product.ProductRequest;
import com.hjy.dto.response.ResultMsg;
import com.hjy.dto.response.product.Product;
import com.hjy.entity.LcRsyncMinspcProduct;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.service.IMinspcProductService;

/**
 * @descriptions 处理获取的订阅产品，记录到数据库同时插入日志
 *
 * @date 2016年9月10日 下午7:31:47
 * @author Yangcl 
 * @version 1.0.1
 */
public class RsyncProductList extends RsyncMinspc {

	@Inject
	private IMinspcProductService productService;
	
	@Inject
	private ILcRsyncMinspcProductDao lcRsyncMinspcProductDao ;
	
	public void doProcess(String responseJson) {
		// 解析请求数据
		List<Product> productList = null;
		try {
			productList = JSON.parseArray(responseJson, Product.class);
		} catch (Exception e) {
			lcRsyncMinspcProductDao.insertSelective(new LcRsyncMinspcProduct("响应参数错误，请求数据解析异常", responseJson, "", ""));
			return;
		}
		if(productList == null || productList.size() == 0){
			lcRsyncMinspcProductDao.insertSelective(new LcRsyncMinspcProduct("响应数据为空", responseJson, "", ""));
			return;
		}
		
		List<PcProductinfo> successList = new ArrayList<PcProductinfo>();
		List<Map<PcProductinfo , String>> errorList = new ArrayList<Map<PcProductinfo , String>>(); 
		
		// 开始清洗数据  响应数据报文与惠家有表实体报文转换。
		List<PcProductinfo> list = productService.productConvertion(productList);
		for(PcProductinfo e : list){ // 开始准备一条一条的处理数据 
			ResultMsg msg = null;
			PcProductinfo i = new PcProductinfo();
			i.setProductCodeOld(e.getProductCodeOld()); // product_code_old 作为查询依据
			i.setSellerCode(MemberConst.MANAGE_CODE_HOMEHAS); 
			List<PcProductinfo> pList = productService.getListBySellerCode(i); 
			if (pList == null || pList.size() == 0) { // 若果不存在，就添加
				msg = productService.insertProductToTables(e);
			}else{
				i = pList.get(0);
				e.setUid(i.getUid()); // 根据uid更新商品
				e.setProductCode(i.getProductCode());   
				msg = productService.updateProductInTables(e); 
			}
			if(msg.getCode().equals("1")){
				successList.add(msg.getEntity());
			}else{
				Map<PcProductinfo , String> error = new HashMap<>();
				error.put(e, msg.getMsg());
				errorList.add(error	); 
			}
		}  // 循环同步数据结束
		
		// 记录同步日志
		lcRsyncMinspcProductDao.insertSelective(new LcRsyncMinspcProduct("rsync success", 
				responseJson, 
				JSON.toJSONString(successList), 
				JSON.toJSONString(errorList))); 
	}
	
	
	
	public String getRequestMethod() {
		return "Subscribe.ProductList";
	}

	
	/**
	 * @descriptions 发送请求数据
	 * Status：-1 （当前有库存的全部订阅产品列表）
	 * Status：0 （当前有库存的保税贸易订阅产品列表）
	 * Status：1 （当前有库存的海外直邮订阅产品列表）
	 * Status：2 （当前有库存的一般贸易订阅产品列表）
	 * 
	 * @return {"endTime":"2016-09-10 22:00:00","startTime":"2016-09-10 21:00:00","status":"-1"}
	 * @date 2016年9月10日 下午9:45:05
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */ 
	public String setRequestDataJson() {
		Date curTime = new Date();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		String start_ = sdf.format(curTime);
		String end_ = this.getNextHour(curTime);
		ProductRequest pr = new ProductRequest();
		pr.setStatus("-1");
		pr.setStartTime(start_);
		pr.setEndTime(end_); 
		
		return JSON.toJSONString(pr); 
	}

	/**
	 * @descriptions 获取当前时间的下一个小时
	*
	* @date 2016年9月10日 下午9:42:14
	* @author Yangcl 
	* @version 1.0.0.1
	 */
	private String getNextHour(Date date){
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.HOUR , 1); 
		 date=calendar.getTime();  
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		 
		 return formatter.format(date);
	}
	
}


























package com.hjy.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.IJobExectimerDao;
import com.hjy.dao.ILcRsyncKjtLogDao;
import com.hjy.dao.ILockDao;
import com.hjy.dao.order.IOcOrderdetailDao;
import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.dao.product.IPcSkuinfoDao;
import com.hjy.dao.system.IScFlowBussinessHistoryDao;
import com.hjy.dto.KjtProductInfo;
import com.hjy.dto.ProductStatusDto;
import com.hjy.dto.QueryKjtLog;
import com.hjy.entity.LcRsyncKjtLog;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.entity.system.ScFlowBussinessHistory;
import com.hjy.helper.DateHelper;
import com.hjy.helper.ExceptionHelper;
import com.hjy.helper.WebHelper;
import com.hjy.pojo.entity.system.JobExectimer;
import com.hjy.redis.core.RedisLaunch;
import com.hjy.redis.srnpr.ERedisSchema;
import com.hjy.selleradapter.job.JobForInventory;
import com.hjy.selleradapter.job.JobGetChangeProductFromKJT;
import com.hjy.service.IKjtOperationsManagerService;


@Service("kjtOperationsManagerService")
public class KjtOperationsManagerServiceImpl implements IKjtOperationsManagerService {
	private static Logger logger=Logger.getLogger(KjtOperationsManagerServiceImpl.class);
	@Resource
	private IJobExectimerDao jobExectimerDao;
	
	@Resource
	private IPcProductinfoDao pcProductinfoDao;
	
	@Resource
	private ILcRsyncKjtLogDao lcRsyncKjtLogDao;  
	
	@Resource
	private IOcOrderdetailDao orderDetailDao;
	
	@Resource
	private IScFlowBussinessHistoryDao scFlowBussinessHistoryDao;
	
	@Resource
	private IPcSkuinfoDao pcSkuinfoDao;
	
	@Resource
	private ILockDao sysLockDao;
	
	
	public JSONObject funcOne(String json, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("kjt-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		try {
			List<String> list = JSON.parseArray(json, String.class);
			if(list == null || list.size() == 0){
				result.put("status", "success");
				result.put("desc", "可执行数据为空");
				return result;
			}
			new JobGetChangeProductFromKJT(list).doExecute(null); 
			result.put("status", "success");
			result.put("desc", "请求执行完成");
		} catch (Exception e) {
			result.put("status", "success");
			result.put("desc", "非法的Json数据");
		}
		return result;
	}

	public JSONObject funcTwo(String s, String e, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("kjt-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		
		new JobGetChangeProductFromKJT(s , e).doExecute(null); 
		result.put("status", "success");
		result.put("desc", "请求执行完成");
		return result; 
	}

	public JSONObject funcThree(String execTime, String remark, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("kjt-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		
		JobExectimer e = new JobExectimer();
		if(StringUtils.isNotBlank(execTime)){
			e.setExecTime(DateHelper.parseDate(execTime));
		}else{
			e.setExecTime(new Date()); 
		}
		e.setRemark(remark); 
		e.setExecNumber(0);
		
		jobExectimerDao.updateSelectiveByFlag(e);
		
		result.put("status", "success");
		result.put("desc", "请求执行完成");
		
		return result;
	}
	
	public JSONObject funcThreePlus(String uuids, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("kjt-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		
		if(StringUtils.isBlank(uuids)){
			result.put("status", "success");
			result.put("desc", "参数不可为空");
			return result;
		}
		
		String [] darray = uuids.split(",");
		if(darray.length != 0){
			for(int i = 0 ; i < darray.length ; i ++){
				sysLockDao.deleteByUuid(darray[i]); 
			}
			result.put("status", "success");
			result.put("desc", "请求执行完成");
		}else{
			result.put("status", "success");
			result.put("desc", "参数不可为空");  
		}
		return result;
	}

	
	public JSONObject funcFour(String json, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("kjt-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		try {
			List<String> list = JSON.parseArray(json, String.class);
			if(list == null || list.size() == 0){
				result.put("status", "success");
				result.put("desc", "可执行数据为空");
				return result;
			}
			pcProductinfoDao.updateNullByProductCode(list);
			result.put("status", "success");
			result.put("desc", "请求执行完成");
		} catch (Exception e) {
			result.put("status", "success");
			result.put("desc", "非法的Json数据");
		}
		return result;
	}

	
	public JSONObject funcFive(String json, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("kjt-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		
		try {
			List<String> list = JSON.parseArray(json, String.class);
			if(list == null || list.size() == 0){
				result.put("status", "success");
				result.put("desc", "可执行数据为空");
				return result;
			}
			new JobForInventory(list).doExecute(null); 
			result.put("status", "success");
			result.put("desc", "请求执行完成");
		} catch (Exception e) {
			result.put("status", "success");
			result.put("desc", "非法的Json数据");
		}
		return result;
	}

	
	public JSONObject funcSix(String json, HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("kjt-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		
		try {
			List<String> list = JSON.parseArray(json, String.class);
			if(list == null || list.size() == 0){
				result.put("status", "success");
				result.put("desc", "可执行数据为空");
				return result;
			}
			this.redisReloadProductInfo(list);
			
			result.put("status", "success");
			result.put("desc", "请求执行完成");
		} catch (Exception e) {
			result.put("status", "success");
			result.put("desc", "非法的Json数据");
		}
		return result;
	}
	
	
	// 刷新Sku信息 
	private boolean redisReloadProductInfo(List<String> list ){ 
		for(String i : list){ 
			RedisLaunch.setFactory(ERedisSchema.Product).del(i);
			RedisLaunch.setFactory(ERedisSchema.ProductSku).del(i);
			RedisLaunch.setFactory(ERedisSchema.ProductSales).del(i);		//刷新销量缓存
		}
		return true;
	}

	
	public JSONObject queryKjtlog(QueryKjtLog dto) {
		JSONObject result = new JSONObject();
		LcRsyncKjtLog kjt = new LcRsyncKjtLog();
		kjt.setRsyncTarget(dto.getRsyncTarget());
		kjt.setRequestTime(dto.getRequestTime());
		kjt.setResponseData(dto.getResponseData());
		kjt.setRequestData(dto.getRequestData()); 
		try {
			List<LcRsyncKjtLog> logList = lcRsyncKjtLogDao.selectLogByType(kjt); 
			if(logList != null && logList.size() != 0){
				result.put("logList", logList);
			}else{
				result.put("logList", "log-list-null");
			}
			
			Map<String , String> dtoMap =  new HashMap<>();
			dtoMap.put("orderCode", dto.getOrderCode());
			dtoMap.put("sellerCode" , dto.getSellerCode());
			// 有可能查出2条，兼容错误数据
			List<KjtProductInfo> lists = orderDetailDao.findKjtProductInfo(dtoMap);
			if(lists != null && lists.size() > 0){
				KjtProductInfo entity = lists.get(0); 
				result.put("entity", entity);
				result.put("size", lists.size());  
				result.put("lists", JSON.toJSON(lists)); 
			}else{
				result.put("entity", "entity-null");
			}
			result.put("status", "success");
		} catch (Exception ex) {
			result.put("status", "error");
			String remark_ = "{" + ExceptionHelper.allExceptionInformation(ex)+ "}";
			result.put("msg", remark_); 
		}
		
		return result;
	}

	/**
	 * @description: 上下架部分跨境通商品|也可以是全部商品的上下架
	 * 
	 * @param json 
	 * @param productStatus 4497153900060002(已上架)|4497153900060004(平台强制下架) 
	 * @param reason 上下架原因描述 + 邮件发送人
	 * @param session
	 * @return
	 * @author Yangcl 
	 * @date 2016年10月9日 下午3:14:20 
	 * @version 1.0.0.1
	 */
	public JSONObject funcSeven(String json, String productStatus , String reason , HttpSession session) {
		JSONObject result = new JSONObject();
		if(session.getAttribute("kjt-key") == null){
			result.put("status", "success");
			result.put("desc", "请输入你的秘钥");
			return result;
		}
		
		
		List<PcProductinfo> list = null;
		List<String> pcodeList = null;
		if(productStatus.equals("4497153900060002") && StringUtils.isBlank(json)){  
			// 准备将强制下架的商品 全部上架
			list = pcProductinfoDao.getSoldOutProductList("SF03KJT");
		}else if(productStatus.equals("4497153900060004") && StringUtils.isBlank(json)){  
			 // 准备将现在所有上架的商品 全部下架
			list = pcProductinfoDao.getItemUpshelfProductList("SF03KJT");
		}else{
			try { // 准备批量上下架商品
				pcodeList = JSON.parseArray(json, String.class);
				ProductStatusDto dto = new ProductStatusDto();
				if(productStatus.equals("4497153900060004")){
					dto.setProductStatus("4497153900060002");
				}else if(productStatus.equals("4497153900060002")){
					dto.setProductStatus("4497153900060004");
				}
				dto.setList(pcodeList); 
				list = pcProductinfoDao.getListByProductCodeList(dto);
			} catch (Exception e) {
				result.put("status", "success");
				result.put("desc", "非法的Json数据");
				return result;
			}
		}
		
		
		String lockcode = WebHelper.getInstance().addLock(300 , "seller-adapter-kjt@OperationsManagerServiceImpl.funcSeven");      // 分布式锁5分钟
		if(StringUtils.isNotEmpty(lockcode)) {
			try { 
				for(PcProductinfo i : list){
					String uid=i.getUid();
					String flowType = "449715390006";
					String userCode = "kjt - manually - initiated";
					pcProductinfoDao.updateProductStatus(new PcProductinfo(i.getUid() , productStatus));
					scFlowBussinessHistoryDao.insertSelective(new ScFlowBussinessHistory(
							UUID.randomUUID().toString().replace("-", ""),
							uid,    // 关联商品的uuid 
							flowType,
							userCode,
							DateHelper.formatDate(new Date()),
							" 商编：" + i.getProductCode() + "  原因：" + reason,  // " - 上下架原因描述 - 邮件发送人",
							productStatus							
							));
					boolean flag = this.redisReloadProductInfo(i.getProductCode());
					logger.info(i.getProductName() + "@"+ i.getProductCode() +"@缓存状态信息：" + flag); 
				}
				
				result.put("status", "success");
				result.put("desc", "请求执行完成");
			} catch (Exception e) {
				logger.error("" , e);
				result.put("status", "success");
				result.put("desc", "商品下架异常！");
			}finally{
				WebHelper.getInstance().unLock(lockcode);
			}
		}else{			// 处理机房断电、服务器宕机
			String message = "分布式锁生效，请删除锁：" + "seller-adapter-kjt@OperationsManagerServiceImpl.funcSeven";
			result.put("code", 14);
			result.put("desc", message);
			logger.error(message);
			return result; 
		}
		
		return result;
	}
	
	
	
	/**
	 * @descriptions 刷新Redis 
	 * 
	 * @param productCode_ 
	 * @date 2016年8月16日下午1:37:21
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	private boolean redisReloadProductInfo(String productCode_){
		// 循环删除所有商品下关联的子活动
		for(String key : RedisLaunch.setFactory(ERedisSchema.ProductIcChildren).hgetAll(productCode_).keySet()){
			RedisLaunch.setFactory(ERedisSchema.IcSku).del(key);
		}
		// 删除所有Sku相关信息
		List<PcSkuinfo> skuList = pcSkuinfoDao.findList(new PcSkuinfo(productCode_)); 
		for(PcSkuinfo i : skuList){
			RedisLaunch.setFactory(ERedisSchema.Sku).del(i.getSkuCode()); 
			RedisLaunch.setFactory(ERedisSchema.Stock).del(i.getSkuCode());
			RedisLaunch.setFactory(ERedisSchema.SkuStoreStock).del(i.getSkuCode());
		}
		// TODO 删除促销的Sku信息 
		
		
		RedisLaunch.setFactory(ERedisSchema.Product).del(productCode_);
		RedisLaunch.setFactory(ERedisSchema.ProductSku).del(productCode_);
		RedisLaunch.setFactory(ERedisSchema.ProductSales).del(productCode_);		//刷新销量缓存
		return true;
	}

	
}

















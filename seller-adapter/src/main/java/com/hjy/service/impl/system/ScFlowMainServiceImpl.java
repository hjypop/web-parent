package com.hjy.service.impl.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.system.IScFlowMainDao;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.system.ScFlowMain;
import com.hjy.helper.WebHelper;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.system.IScFlowMainService;
import com.hjy.system.cmodel.CacheWcSellerInfo;

/**
 * @description: 审批流相关服务类，用于open-api 和 minspc|这个类用于模拟创建审批流
 * @核心方法 createFlowMain() 
 * 
 * @author Yangcl
 * @date 2017年1月11日 下午11:23:05 
 * @version 1.0.0
 */
@Service("scFlowMainService")
public class ScFlowMainServiceImpl extends BaseServiceImpl<ScFlowMain, Integer> implements IScFlowMainService {

	private static String FlowHead = "SF";
	
	@Resource
	private IScFlowMainDao dao;

	
	/**
	 * @description: 为新添加的商品创建一个审批流
	 * 
	 * @param p Product information
	 * @param seller 
	 * @param platform 创建平台名称
	 * @author Yangcl 
	 * @date 2017年1月11日 下午11:34:45 
	 * @version 1.0.0.1
	 */
	public JSONObject createFlowMain(PcProductinfo p , CacheWcSellerInfo seller , String platform) {
		JSONObject result = new JSONObject();
		ScFlowMain f = new ScFlowMain();
		f.setCreator(platform); 
		if(this.isCrossBorderSeller(seller)){
			f.setCurrentStatus("4497172300160004");
		}else{
			f.setCurrentStatus("4497172300160001");  
		}
		f.setFlowTitle(p.getProductCode()); 
		f.setFlowType("449717230016");
		f.setFlowUrl(this.getConfig("seller_adapter.PreviewCheckProductUrl")); 
		f.setOuterCode(p.getProductCode());  
		f.setFlowRemark(this.getInfo(200002002, p.getProductCode()));   
		
		
		f.setFlowCode(WebHelper.getInstance().genUniqueCode(FlowHead));
		// TODO 
		
		return result;
	}
	
	/**
	 * @description: 判断当前商户是否为跨境商户|如果为跨境商户则返回true
	 * 
	 * @param seller
	 * @author Yangcl 
	 * @date 2017年1月11日 下午11:43:50 
	 * @version 1.0.0.1
	 */
	private boolean isCrossBorderSeller(CacheWcSellerInfo seller){
		boolean flag = false;
		if("4497478100050002".equals(seller.getSellerType()) || "4497478100050003".equals(seller.getSellerType())){
			flag = true;
		}
		
		return flag;		
	}
	
}
































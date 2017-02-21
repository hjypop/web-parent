package com.hjy.service.impl.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.system.IScFlowHistoryDao;
import com.hjy.dao.system.IScFlowMainDao;
import com.hjy.dao.system.IScFlowStatuschangeDao;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.system.ScFlowHistory;
import com.hjy.entity.system.ScFlowMain;
import com.hjy.entity.system.ScFlowStatuschange;
import com.hjy.helper.DateHelper;
import com.hjy.helper.ExceptionHelper;
import com.hjy.helper.WebHelper;
import com.hjy.model.flow.FlowNextOperator;
import com.hjy.model.flow.RoleStatus;
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

	private static String FlowHead = "SF9";
	
	@Resource
	private IScFlowMainDao dao;
	@Resource
	private IScFlowStatuschangeDao flowStatusChangeDao;  
	@Resource
	private IScFlowHistoryDao flowHistoryDao;  
	

	
	/**
	 * @description: 为新添加的商品创建一个审批流
	 * 
	 * @模仿蓝本 该方法主要模仿自【商家管理后台】(商户后台)->【商品相关】->【商品管理】->【添加商品】的部分代码逻辑
	 * 					该部分的代码逻辑为：创建审批流。代码原有逻辑调用位于systemcenter库下的存储过程 proc_flow_create
	 * 					此处改为向sc_flow_main表和sc_flow_history表直接插入数据信息。
	 * 
	 * @param p product information
	 * @param seller 
	 * @param platform 创建平台名称，如：open-api or minspc
	 * @author Yangcl 
	 * @date 2017年1月11日 下午11:34:45 
	 * @version 1.0.0.1
	 */
	public JSONObject createFlowMain(PcProductinfo p , CacheWcSellerInfo seller , String platform) {
		JSONObject result = new JSONObject();
		ScFlowMain f = new ScFlowMain();
		f.setUid(UUID.randomUUID().toString().replace("-", ""));  
		f.setCreateTime(DateHelper.formatDate(new Date())); 
		f.setUpdateTime(DateHelper.formatDate(new Date()));
		
		f.setCreator(platform); 
		/**
		 * za_role表
		 * 	46770318000100030001 HJY-质检专员审批
		 * 	46770318000100030004 HJY-跨境商品运营审批
		 * 
		 * 4497172300160002 普通运营待审批
		 * 4497172300160003 网站编辑待审批
		 * 4497172300160006 质检员驳回
		 * 4497172300160009 跨境运营审批驳回
		 * 
		 */
		if(this.isCrossBorderSeller(seller)){
			f.setCurrentStatus("4497172300160004");  // 跨境运营待审批
			f.setNextOperators("46770318000100030004");
			f.setNextOperatorStatus("46770318000100030004:4497172300160003;46770318000100030004:4497172300160009");
		}else{
			f.setCurrentStatus("4497172300160001");    // 质检员待审批
			f.setNextOperators("46770318000100030001");
			f.setNextOperatorStatus("46770318000100030001:4497172300160002;46770318000100030001:4497172300160006");
		}
		f.setFlowTitle(p.getProductCode()); 
		f.setFlowType("449717230016");
		f.setFlowUrl(this.getConfig("seller_adapter.PreviewCheckProductUrl") + p.getProductCode() + "_1"); 
		f.setOuterCode(p.getProductCode());  
		f.setFlowRemark(this.getInfo(200002002, p.getProductCode()));   
		f.setFlowCode(WebHelper.getInstance().genUniqueCode(FlowHead)); 
		f.setFlowIsend(0);
		 
		// 代码原有逻辑调用位于systemcenter库下的存储过程 proc_flow_create
		try {
			dao.insertSelective(f);
			ScFlowHistory sfh = new ScFlowHistory();
			sfh.setUid(f.getUid());
			sfh.setFlowCode(f.getFlowCode());
			sfh.setFlowType(f.getFlowType());
			sfh.setCreator("seller-adapter");
			sfh.setCreateTime(DateHelper.formatDate(new Date())); 
			sfh.setFlowRemark(f.getFlowRemark());
			sfh.setCurrentStatus(f.getCurrentStatus()); 
			flowHistoryDao.insertSelective(sfh);
		} catch (Exception ex) {
			logger.error("该商品在创建审批流时出现异常！【" + p.getProductCode() + "】"); 
			String exstring = ExceptionHelper.allExceptionInformation(ex);
			logger.error("ScFlowMainServiceImpl.createFlowMain()方法在创建审批流时出现严重异常！请查实！" + exstring);
		}
		
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








//private FlowNextOperator getNextAll(String flowType , String toStatus){
//	FlowNextOperator o = new FlowNextOperator();
//	List<RoleStatus> rsList = this.getRoleToStatusList(flowType, toStatus);
//	if(rsList != null && rsList.size() != 0){
//		o.setNextOperator(this.getNextOperator(rsList));  
//		o.setNextOperatorStatus(this.getNextOpoeratorStatus(rsList)); 
//	}
//	return o;
//}


/**
 * @description: 获取角色 状态 对应关系
 * 
 * @param flowType
 * @param toStatus
 * @author Yangcl 
 * @date 2017年1月12日 下午3:15:42 
 * @version 1.0.0.1
 */
//private List<RoleStatus> getRoleToStatusList(String flowType , String toStatus){
//	List<RoleStatus> list = new ArrayList<RoleStatus>();
//	
//	Map<String , String> map = new HashMap<String , String>(2);
//	map.put("flow_type", flowType);
//	map.put("from_status", toStatus);
//	List<ScFlowStatuschange> list_ = flowStatusChangeDao.findListByFlowTyp(map);
//	if(list_ != null && list_.size() != 0){
//		for(ScFlowStatuschange s : list_){
//			list.add(new RoleStatus(s.getRoleId(), s.getToStatus()));
//		}
//	}
//	
//	return list;  
//}

/**
 * @description: 获取下一级审批人
 * 
 * @param list
 * @author Yangcl 
 * @date 2017年1月12日 下午4:38:24 
 * @version 1.0.0.1
 */
//private String getNextOperator(List<RoleStatus> list){
//	String str = "";
//	Map<String , String> map = new HashMap<String , String>();
//	if(list != null && list.size() != 0){
//		for(RoleStatus s : list){
//			if(!map.containsKey(s.getRoleCode())){
//				map.put(s.getRoleCode(), "");
//				str += s.getRoleCode() + "," ;
//			}
//		}
//		if(StringUtils.isNotBlank(str)){
//			str = str.substring(0  , str.length()-1); 
//		}
//	}
//	return str;
//}

/**
 * @description: 获取下一审批人状态 
 * 
 * @param list
 * @author Yangcl 
 * @date 2017年1月12日 下午5:55:01 
 * @version 1.0.0.1
 */
//private String getNextOpoeratorStatus(List<RoleStatus> list){
//	String str = "";
//	if(list != null && list.size() != 0){
//		for(RoleStatus s : list){
//			str += s.getRoleCode() + ":" + s.getToStatus() + ";" ;
//		}
//		if(StringUtils.isNotBlank(str)){
//			str = str.substring(0  , str.length()-1); 
//		}
//	}
//	return str;
//}






























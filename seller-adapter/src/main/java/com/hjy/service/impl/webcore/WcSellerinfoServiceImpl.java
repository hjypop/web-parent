package com.hjy.service.impl.webcore;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjy.common.DateUtil;
import com.hjy.dao.webcore.IWcOpenApiDao;
import com.hjy.dao.webcore.IWcSellerinfoDao;
import com.hjy.entity.webcore.WcOpenApi;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.helper.WebHelper;
import com.hjy.pojo.entity.login.UserInfo;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.webcore.IWcSellerinfoService;
import com.hjy.system.ApiCacheVisitor;
import com.hjy.system.cmodel.CacheWcOpenapi;
import com.hjy.system.cmodel.CacheWcSellerInfo;

/**
 * 类: WcSellerinfoServiceImpl <br>
 * 描述: 商家信息业务处理接口实现类 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月17日 上午11:39:29
 * 
 * @refactor 全面重构|所有信息缓存化，提高Api性能
 * @author Yangcl
 * @date 2016年12月22日 下午3:00:50
 * @version 1.0.0
 */
@Service
public class WcSellerinfoServiceImpl extends BaseServiceImpl<WcSellerinfo, Integer> implements IWcSellerinfoService {

	@Autowired
	private IWcSellerinfoDao dao;

	@Autowired
	private IWcOpenApiDao apidao;
	/**
	 * 
	 * 方法: selectBySellerCode <br>
	 * 描述: TODO
	 * 
	 * @param sellerCode
	 * @return
	 * @see com.hjy.service.webcore.IWcSellerinfoService#selectBySellerCode(java.lang.String)
	 */
	@Override
	public WcSellerinfo selectBySellerCode(String sellerCode) {
		return dao.selectBySellerCode(sellerCode);
	}

	/**
	 * 
	 * 方法: deleteBySellerCode <br>
	 * 描述: TODO
	 * 
	 * @param sellerCode
	 * @return
	 * @see com.hjy.service.webcore.IWcSellerinfoService#deleteBySellerCode(java.lang.String)
	 */
	@Override
	public int deleteBySellerCode(String sellerCode) {
		return dao.deleteBySellerCode(sellerCode);
	}

	/**
	 * 
	 * 方法: selectBySellerCodeByApi <br>
	 * 描述: TODO
	 * 
	 * @param sellerCode
	 * @return
	 * @see com.hjy.service.webcore.IWcSellerinfoService#selectBySellerCodeByApi(java.lang.String)
	 */
	@Override
	public WcSellerinfo selectBySellerCodeByApi(String sellerCode) {
		return dao.selectBySellerCodeByApi(sellerCode);
	}

	/**
	 * @description: 添加商户信息
	 * 
	 * @param entity
	 * @param session
	 * @author Yangcl 
	 * @date 2016年12月20日 下午5:21:43 
	 * @version 1.0.0.1
	 */
	public JSONObject insertWcSellerInfo(WcSellerinfo entity, HttpSession session) {
		JSONObject obj = new JSONObject();
		try {
			String type_ = "SI";  // 默认为惠家有商户标识
			if(entity.getType() == 2){
				type_ = "DP"; // 1：惠家有的商户；2：分销平台
			}
			UserInfo user = (UserInfo) session.getAttribute("userInfo");
			entity.setUid(WebHelper.getInstance().genUuid());
			entity.setSellerCode(WebHelper.getInstance().genUniqueCode(type_));
			JSONObject validate = this.validateEntity(entity);
			if( !validate.getBoolean("status") ){
				return validate;
			}
			entity.setCreator(user.getUserName());
			entity.setCreateTime(DateUtil.getSysDateTimeString());
			entity.setUpdator(user.getUserName());
			entity.setUpdateTime(DateUtil.getSysDateTimeString());
			
			obj.put("status", "error");
			int result = dao.insertSelective(entity);
			if (result >= 0) {
				obj.put("status", "success");
				obj.put("msg", "添加成功");
			} else {
				obj.put("msg", "添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("msg", "【添加商户信息】异常!"); 
		}
		return obj;
	}

	/**
	 * @description: 修改商户信息
	 * 
	 * @param entity
	 * @param session
	 * @author Yangcl 
	 * @date 2016年12月21日 下午5:51:10 
	 * @version 1.0.0.1
	 */
	public JSONObject updateWcSellerInfo(WcSellerinfo entity, HttpSession session) {
		JSONObject obj = new JSONObject();
		try {
			UserInfo user = (UserInfo) session.getAttribute("userInfo");
			entity.setUpdator(user.getUserName());
			entity.setUpdateTime(DateUtil.getSysDateTimeString());
			entity.setType(1); // 不会更新此字段，仅用来兼容验证
			JSONObject validate = this.validateEntity(entity);
			if( !validate.getBoolean("status") ){
				return validate;
			}
			obj.put("status", "error");
			int result = dao.updateSelective(entity);
			if (result >= 0) {
				ApiCacheVisitor.update(entity.getSellerCode());   // 更新ecache缓存中的信息  
				obj.put("status", "success");
				obj.put("msg", "修改成功");
			} else {
				obj.put("msg", "修改失败"); 
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			obj.put("msg", "【修改商户信息】异常!"); 
		}finally{
			
		}
		return obj;
	}
	
	
	/**
	 * @description: 获取open-api接口列表并且关联出哪些接口已经被该用户使用
	 * 
	 * @param sellerCode
	 * @param session
	 * @author Yangcl 
	 * @date 2016年12月26日 下午3:38:28  
	 * @version 1.0.0.1
	 */
	public JSONObject sellerOpenapiList(String sellerCode) {
		JSONObject obj = new JSONObject();
		obj.put("status", "success");
		try {
			CacheWcSellerInfo info = JSONObject.parseObject(ApiCacheVisitor.find(sellerCode) , CacheWcSellerInfo.class); // 取出缓存中的商户信息
			WcOpenApi entity = new WcOpenApi();
			entity.setFlag(info.getType()); 
			List<WcOpenApi> apiList = apidao.selectAllInfo(entity);  
			if(apiList != null && apiList.size() > 0){
				if(info.getApis() != null && info.getApis().size() != 0){
					for(int i = 0 ; i < apiList.size() ; i ++){
						for(CacheWcOpenapi o : info.getApis()){
							if(apiList.get(i).getApiCode().equals(o.getApiCode())){
								apiList.get(i).setUid("true"); // 借用uid 传递信息，标识这条api是否关联了该商户。不为true则未关联
							}
						}
					}
				}
				obj.put("data", apiList); 
			}else{
				obj.put("status", "error");
				obj.put("msg", "未找到有效的api结果集，数据库可能链接失败。");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("status", "error");
			obj.put("msg", "服务器捕获未知的异常！请联系技术人员");
		}
		return obj;
	}
	
	
	/**
	 * @description: 验证信息完整性
	 * 
	 * @param e
	 * @author Yangcl 
	 * @date 2016年12月21日 下午5:52:04 
	 * @version 1.0.0.1
	 */
	private JSONObject validateEntity(WcSellerinfo e){
		JSONObject o = new JSONObject();
		o.put("status", true);
		if(StringUtils.isAnyBlank(
				e.getSellerName(),
				e.getSellerCode(),
				e.getSellerDescrption(),
				e.getSellerTelephone(),
				e.getSellerEmail(),
				e.getType().toString()
				)){
			o.put("status", false);
			o.put("msg", "关键字段不得为空!");
			return o;
		}
		if(e.getFlag() == 1 && StringUtils.isAnyBlank(e.getSellerCustomNumber() , e.getSellerCustomLocation())){
			o.put("status", false);
			o.put("msg", "报关商户需要填写【商户海关备案编号】和【商户报关地点】信息");
			return o;
		}
		
		JSONArray c = JSONObject.parseArray(e.getCommission());   // 开始验证 佣金设置
		for(int i = 0 ; i < c.size() ; i ++){
			JSONObject c_ = c.getJSONObject(i);
			if(StringUtils.isBlank(c_.getString("commission"))){
				o.put("status", false);
				o.put("msg", "【佣金设置】不得为空!");
				return o;
			}
			if( !this.isNumeric(c_.getString("commission")) ){
				o.put("status", false);
				o.put("msg", "【佣金设置】必须为正整数!");
				return o;
			}
		}
		return o;
	}
	
	public boolean isNumeric(String str){  
		   for(int i=str.length();--i>=0;){  
		      int chr=str.charAt(i);  
		      if(chr<48 || chr>57)  
		         return false;  
		   }  
		   return true;  
		}
}















package com.hjy.service.impl.webcore;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.common.DateUtil;
import com.hjy.dao.webcore.IWcOpenApiDao;
import com.hjy.entity.webcore.WcOpenApi;
import com.hjy.helper.WebHelper;
import com.hjy.pojo.entity.login.UserInfo;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.webcore.IWcOpenApiService;
import com.hjy.system.ApiCacheVisitor;

/**
 * @类: WcOpenApiServiceImpl <br>
 * @描述: openApi接口表业务处理接口实现类 <br>
 * @作者: zhy<br>
 * @时间: 2016年8月25日 上午9:43:05
 * @version 1.0.0
 * 
 * @refactor 全面重构|所有信息缓存化，提高Api性能
 * @author Yangcl
 * @date 2016年12月22日 下午3:00:50
 * @version 1.0.0
 */
@Service
public class WcOpenApiServiceImpl extends BaseServiceImpl<WcOpenApi, Integer> implements IWcOpenApiService {

	@Autowired
	private IWcOpenApiDao dao;
	
	/**
	 * @description: 删除一条记录
	 * 
	 * 	TODO 删除记录的同时需要考虑 同时删除 wc_seller_api 下关联到所有用户的记录|同时删除缓存中的记录
	 * 
	 * @author Yangcl 
	 * @date 2016年12月22日 下午2:58:28 
	 * @version 1.0.0.1
	 */
	public JSONObject deleteApiCode(String apiCode) {
		JSONObject obj = new JSONObject();
		int result = dao.deleteApiCode(apiCode);
		if (result >= 0) {
			obj.put("status", "success");
			obj.put("msg", "删除成功");
		} else {
			obj.put("status", "error");
			obj.put("msg", "删除失败");
		}
		
		return obj; 
	}

	/**
	 * @description: 添加一条记录 
	 * 
	 *  TODO 同时要考虑缓存中是否已经有这条记录 && 如果没有，需要在入库的同时加入缓存
	 * 
	 * @author Yangcl 
	 * @date 2016年12月22日 下午2:50:14 
	 * @version 1.0.0.1
	 */
	public JSONObject insertWcOpenApi(WcOpenApi entity, HttpSession session) {
		UserInfo user = (UserInfo) session.getAttribute("userInfo");
		JSONObject obj = new JSONObject();
		entity.setUid(WebHelper.getInstance().genUuid());
		entity.setApiCode(WebHelper.getInstance().genUniqueCode("API"));
		entity.setCreator(user.getUserName());
		entity.setCreateTime(DateUtil.getSysDateTimeString());
		entity.setUpdator(user.getUserName());
		entity.setUpdateTime(DateUtil.getSysDateTimeString());
		int result = dao.insertSelective(entity);
		if (result >= 0) {
			ApiCacheVisitor.addapi(entity); 
			obj.put("status", "success");
			obj.put("msg", "添加成功");
		} else {
			obj.put("status", "error");
			obj.put("msg", "添加失败");
		}
		return obj;
	}

	/**
	 * @description: 
	 * 	TODO 更新数据库的同时更新缓存
	 * 
	 * @author Yangcl 
	 * @date 2016年12月22日 下午2:52:05 
	 * @version 1.0.0.1
	 */
	public JSONObject updateWcOpenApi(WcOpenApi entity, HttpSession session) {
		UserInfo user = (UserInfo) session.getAttribute("userInfo");
		JSONObject obj = new JSONObject();
		entity.setUpdator(user.getUserName());
		entity.setUpdateTime(DateUtil.getSysDateTimeString());
		int result = dao.updateSelective(entity);
		if (result >= 0) { 
			ApiCacheVisitor.updateapi(entity); 
			obj.put("status", "success");
			obj.put("msg", "编辑成功");
		} else {
			obj.put("status", "error");
			obj.put("msg", "编辑失败");
		}
		return obj;
	}

}





















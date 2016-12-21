package com.hjy.service.impl.webcore;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.common.DateUtil;
import com.hjy.dao.webcore.IWcSellerinfoDao;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.helper.WebHelper;
import com.hjy.pojo.entity.login.UserInfo;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.webcore.IWcSellerinfoService;

/**
 * 
 * 类: WcSellerinfoServiceImpl <br>
 * 描述: 商家信息业务处理接口实现类 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月17日 上午11:39:29
 */
@Service
public class WcSellerinfoServiceImpl extends BaseServiceImpl<WcSellerinfo, Integer> implements IWcSellerinfoService {

	@Autowired
	private IWcSellerinfoDao dao;

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
		String type_ = "SI";  // 默认为惠家有商户标识
		if(entity.getType() == 2){
			type_ = "DP"; // 1：惠家有的商户；2：分销平台
		}
		UserInfo user = (UserInfo) session.getAttribute("userInfo");
		entity.setUid(WebHelper.getInstance().genUuid());
		entity.setSellerCode(WebHelper.getInstance().genUniqueCode(type_));
		entity.setCreator(user.getUserName());
		entity.setCreateTime(DateUtil.getSysDateTimeString());
		entity.setUpdator(user.getUserName());
		entity.setUpdateTime(DateUtil.getSysDateTimeString());
		int result = dao.insertSelective(entity);
		if (result >= 0) {
			obj.put("status", "success");
			obj.put("msg", "添加成功");
		} else {
			obj.put("status", "error");
			obj.put("msg", "添加失败");
		}
		return obj;
	}

	@Override
	public JSONObject updateWcSellerInfo(WcSellerinfo entity, HttpSession session) {
		UserInfo user = (UserInfo) session.getAttribute("userInfo");
		JSONObject obj = new JSONObject();
		entity.setUpdator(user.getUserName());
		entity.setUpdateTime(DateUtil.getSysDateTimeString());
		entity.setType(null);
		int result = dao.updateSelective(entity);
		if (result >= 0) {
			obj.put("status", "success");
			obj.put("msg", "修改成功");
		} else {
			obj.put("status", "error");
			obj.put("msg", "修改失败"); 
		}
		return obj;
	}
}















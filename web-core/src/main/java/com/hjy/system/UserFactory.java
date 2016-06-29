package com.hjy.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.alibaba.fastjson.JSON;
import com.hjy.base.BaseClass;
import com.hjy.constant.WebConst;
import com.hjy.helper.WebSessionHelper;
import com.hjy.iface.IBaseInstance;
import com.hjy.model.MDataMap;
import com.hjy.model.MUserInfo;

/**
 * 用户
 * 
 * @author srnpr
 * 
 */
public class UserFactory extends BaseClass implements IBaseInstance, IBaseCreate {

	public static final UserFactory INSTANCE = new UserFactory();

	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	public MUserInfo create() {
		MUserInfo mUserInfo = null;
		Object oUserInfo = WebSessionHelper.create().upSession(WebConst.CONST_WEB_SESSION_USER);
		if(oUserInfo!=null){
			// 保留session中的用户信息，否则退出时无法确定当前用户
//			WebSessionHelper.create().inSession(WebConst.CONST_WEB_SESSION_USER, null);
			return (MUserInfo) oUserInfo;
		}
		String sCookieUser = WebSessionHelper.create().upCookie(WebConst.CONST_WEB_SESSION_USER);
		if(StringUtils.isBlank(sCookieUser)){
			return mUserInfo;
		}
		String userStr=KvUp.upDefault().get(WebConst.CONST_WEB_SESSION_KEY+WebConst.CONST_WEB_SESSION_USER+"-"+sCookieUser);
		if (StringUtils.isNotBlank(userStr)) {
			mUserInfo = JSON.parseObject(userStr, MUserInfo.class);
		} else {
			if (StringUtils.isNotEmpty(sCookieUser)) {
				MDataMap mUserMap = DbUp.upTable("za_userinfo").one("cookie_user", sCookieUser);
				if (mUserMap != null) {
					mUserInfo = inUserInfo(mUserMap);
				}
			}
		}
		return mUserInfo;

	}


}
















package com.hjy.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hjy.base.BaseClass;
import com.hjy.constant.WebConst;
import com.hjy.helper.LogHelper;
import com.hjy.helper.WebSessionHelper;
import com.hjy.iface.IBaseInstance;
import com.hjy.model.MDataMap;
import com.hjy.model.MUserInfo;

public class UserFactory extends BaseClass implements IBaseInstance {
	public static final UserFactory INSTANCE = new UserFactory();

	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	public MUserInfo create() {

		MUserInfo mUserInfo = null;

		Object oUserInfo = WebSessionHelper.create().upSession(WebConst.CONST_WEB_SESSION_USER);

		if (oUserInfo != null) {
			return (MUserInfo) oUserInfo;
		}

		String sCookieUser = WebSessionHelper.create().upCookie(WebConst.CONST_WEB_SESSION_USER);

		if (StringUtils.isBlank(sCookieUser)) {
			return mUserInfo;
		}

		String userStr = KvUp.upDefault()
				.get(WebConst.CONST_WEB_SESSION_KEY + WebConst.CONST_WEB_SESSION_USER + "-" + sCookieUser);

		if (StringUtils.isNotBlank(userStr)) {
			mUserInfo = JSON.parseObject(userStr, MUserInfo.class);
		} else {
			if (StringUtils.isNotEmpty(sCookieUser)) {
				MDataMap mUserMap = null;// DbUp.upTable("za_userinfo").one("cookie_user",
											// sCookieUser);

				if (mUserMap != null) {
					mUserInfo = inUserInfo(mUserMap);
				}

			}
		}
		return mUserInfo;

	}

	/**
	 * 判断用户是否登录
	 * 
	 * @return
	 */
	public boolean checkUserLogin() {

		boolean bFlagLogin = false;

		MUserInfo mUserInfo = this.create();

		if (mUserInfo != null && mUserInfo.getFlagLogin() == 1) {
			bFlagLogin = true;
			KvUp.upDefault().setex(
					WebConst.CONST_WEB_SESSION_KEY + WebConst.CONST_WEB_SESSION_USER + "-" + mUserInfo.getCookieUser(),
					20 * 60, JSON.toJSONString(mUserInfo));
		}

		// 提供无效cookie时要求重新登录
		String sCookieUser = WebSessionHelper.create().upCookie(WebConst.CONST_WEB_SESSION_USER);
		if (StringUtils.isNotEmpty(sCookieUser)) {
			MDataMap mUserMap = DbUp.upTable("za_userinfo").one("cookie_user", sCookieUser);
			if (mUserMap == null) {
				bFlagLogin = false;
			}
		}

		return bFlagLogin;
	}

	/**
	 * 登陆信息初始化并写入session
	 * 
	 * @param mUserData
	 */
	public MUserInfo inUserInfo(MDataMap mUserData) {

		MUserInfo mLoginUserInfo = new MUserInfo();

		if (mUserData.get("flag_enable").equals("1")) {

			mLoginUserInfo.setFlagLogin(1);
			mLoginUserInfo.setLoginName(mUserData.get("user_name"));
			mLoginUserInfo.setUserCode(mUserData.get("user_code"));
			mLoginUserInfo.setRealName(mUserData.get("real_name"));
			mLoginUserInfo.setManageCode(mUserData.get("manage_code"));
			mLoginUserInfo.setCookieUser(mUserData.get("cookie_user"));
			mLoginUserInfo.setTraderCode(mUserData.get("trader_code"));
			mLoginUserInfo.setPassWordUpdateTime(mUserData.get("password_update_time"));
			ArrayList<String> aRoleList = new ArrayList<String>();
			for (MDataMap mDataMap : DbUp.upTable("za_userrole").queryByWhere("user_code",
					mLoginUserInfo.getUserCode())) {
				aRoleList.add(mDataMap.get("role_code"));
			}

			mLoginUserInfo.setUserRole(StringUtils.join(aRoleList, WebConst.CONST_SPLIT_LINE));

			MDataMap mMenuMap = new MDataMap();

			// 如果角色数量大于0
			if (aRoleList.size() > 0) {

				List<String> listMenuCode = new ArrayList<String>();

				for (MDataMap mDataMap : DbUp.upTable("za_rolemenu").queryAll("", "",
						"role_code in ('" + StringUtils.join(aRoleList, "','") + "')", null)) {

					listMenuCode.add(mDataMap.get("menu_code"));

					mMenuMap.put(mDataMap.get("menu_code"), mDataMap.get("menu_code"));

				}

			}

			for (MDataMap mDataMap : DbUp.upTable("za_usermenu").queryByWhere("user_code",
					mLoginUserInfo.getUserCode())) {
				mMenuMap.put(mDataMap.get("menu_code"), mDataMap.get("menu_code"));
			}

			mLoginUserInfo.setUserMenu(StringUtils.join(mMenuMap.keySet(), WebConst.CONST_SPLIT_LINE));

			// mLoginUserInfo.setUserMenu(userMenu)
			WebSessionHelper.create().inSession(WebConst.CONST_WEB_SESSION_USER, mLoginUserInfo);

			KvUp.upDefault().setex(WebConst.CONST_WEB_SESSION_KEY + WebConst.CONST_WEB_SESSION_USER + "-"
					+ mLoginUserInfo.getCookieUser(), 20 * 60, JSON.toJSONString(mLoginUserInfo));

			// 插入日志信息
			String sIp = WebSessionHelper.create().upIpaddress();
			WebLogFactory.INSTANCE.addLog("467723120001", "system_login",
					getInfo(969912002, mLoginUserInfo.getUserCode(), mLoginUserInfo.getLoginName(), sIp));

			LogHelper.addLog("manage_login", mLoginUserInfo);

		}
		return mLoginUserInfo;

	}
}
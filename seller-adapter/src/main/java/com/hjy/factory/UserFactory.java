package com.hjy.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hjy.annotation.Inject;
import com.hjy.base.BaseClass;
import com.hjy.constant.WebConst;
import com.hjy.entity.zapdata.ZaRolemenu;
import com.hjy.entity.zapdata.ZaUserinfo;
import com.hjy.entity.zapdata.ZaUsermenu;
import com.hjy.entity.zapdata.ZaUserrole;
import com.hjy.entity.zapdata.ZaWeblog;
import com.hjy.helper.FormatHelper;
import com.hjy.helper.LogHelper;
import com.hjy.helper.WebSessionHelper;
import com.hjy.iface.IBaseInstance;
import com.hjy.model.MDataMap;
import com.hjy.model.MUserInfo;
import com.hjy.service.zapdata.IZaRolemenuService;
import com.hjy.service.zapdata.IZaUserinfoService;
import com.hjy.service.zapdata.IZaUsermenuService;
import com.hjy.service.zapdata.IZaUserroleService;
import com.hjy.service.zapdata.IZaWeblogService;

public class UserFactory extends BaseClass implements IBaseInstance {
	public static final UserFactory INSTANCE = new UserFactory();

	@Inject
	private IZaUserinfoService zaUserinfoService;
	@Inject
	private IZaUserroleService zaUserroleService;
	@Inject
	private IZaRolemenuService zaRolemenuService;
	@Inject
	private IZaUsermenuService zaUsermenuService;
	@Inject
	private IZaWeblogService zaWeblogService;

	/**
	 * 获取用户信息 | properties配置信息核对完成
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

		// String userStr = KvUp.upDefault()
		// .get(WebConst.CONST_WEB_SESSION_KEY + WebConst.CONST_WEB_SESSION_USER
		// + "-" + sCookieUser);
		//
		// if (StringUtils.isNotBlank(userStr)) {
		// mUserInfo = JSON.parseObject(userStr, MUserInfo.class);
		// } else {
		// if (StringUtils.isNotEmpty(sCookieUser)) {
		// MDataMap mUserMap = null;//
		// DbUp.upTable("za_userinfo").one("cookie_user",
		// // sCookieUser);
		//
		// if (mUserMap != null) {
		// mUserInfo = inUserInfo(mUserMap);
		// }
		//
		// }
		// }
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
			// KvUp.upDefault().setex(
			// WebConst.CONST_WEB_SESSION_KEY + WebConst.CONST_WEB_SESSION_USER
			// + "-" + mUserInfo.getCookieUser(),
			// 20 * 60, JSON.toJSONString(mUserInfo));
		}

		// 提供无效cookie时要求重新登录
		String sCookieUser = WebSessionHelper.create().upCookie(WebConst.CONST_WEB_SESSION_USER);
		if (StringUtils.isNotEmpty(sCookieUser)) {
			ZaUserinfo user = zaUserinfoService.findUserInfoByCookie(sCookieUser);
			if (user == null) {
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
			// 根据用户编号查询用户角色信息 2016-07-04 zhy
			List<ZaUserrole> roleList = zaUserroleService.findRoleByUserCode(mLoginUserInfo.getUserCode());
			for (ZaUserrole role : roleList) {
				aRoleList.add(role.getRoleCode());
			}

			mLoginUserInfo.setUserRole(StringUtils.join(aRoleList, WebConst.CONST_SPLIT_LINE));

			MDataMap mMenuMap = new MDataMap();

			// 如果角色数量大于0
			if (aRoleList.size() > 0) {

				List<String> listMenuCode = new ArrayList<String>();
				List<ZaRolemenu> roleMenus = zaRolemenuService.findMenuByRoleCode(aRoleList);
				for (ZaRolemenu menu : roleMenus) {
					listMenuCode.add(menu.getMenuCode());
					mMenuMap.put(menu.getMenuCode(), menu.getMenuCode());
				}

			}
			List<ZaUsermenu> userMenus = zaUsermenuService.findMenuByUserCode(mLoginUserInfo.getUserCode());
			for (ZaUsermenu menu : userMenus) {
				mMenuMap.put(menu.getMenuCode(), menu.getMenuCode());
			}

			mLoginUserInfo.setUserMenu(StringUtils.join(mMenuMap.keySet(), WebConst.CONST_SPLIT_LINE));

			// mLoginUserInfo.setUserMenu(userMenu)
			WebSessionHelper.create().inSession(WebConst.CONST_WEB_SESSION_USER, mLoginUserInfo);

			// KvUp.upDefault().setex(WebConst.CONST_WEB_SESSION_KEY +
			// WebConst.CONST_WEB_SESSION_USER + "-"
			// + mLoginUserInfo.getCookieUser(), 20 * 60,
			// JSON.toJSONString(mLoginUserInfo));

			// 插入日志信息
			String sIp = WebSessionHelper.create().upIpaddress();
			ZaWeblog weblog = new ZaWeblog();
			weblog.setLogType("467723120001");
			weblog.setLogTitle("system_login");
			weblog.setLogContent(getInfo(200002001, mLoginUserInfo.getUserCode(), mLoginUserInfo.getLoginName(), sIp));
			weblog.setCreateTime(FormatHelper.upDateTime());
			zaWeblogService.insertSelective(weblog);

			LogHelper.addLog("manage_login", mLoginUserInfo);

		}
		return mLoginUserInfo;

	}
}

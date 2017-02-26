package com.drwljrtv.service.impl.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.drwljrtv.request.user.UserRequest;
import com.drwljrtv.service.user.IUserService;
import com.drwljrtv.util.ApiHelper;

/**
 * 
 * 类: UserServiceImpl <br>
 * 描述: 用户相关信息业务处理接口实现类 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月5日 下午7:48:50
 */
@Service
public class UserServiceImpl implements IUserService {

	/**
	 * 
	 * 方法: login <br>
	 * 
	 * @param request
	 * @return
	 * @see com.drwljrtv.service.user.IUserService#login(com.drwljrtv.request.user.UserRequest)
	 */
	@Override
	public JSONObject login(UserRequest request) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "login");
		param.put("username", request.getUsername());
		param.put("password", request.getPassword());
		param.put("remember", request.getPassword());
		JSONObject obj = ApiHelper.getInstance().getObj(param);
		return obj;
	}

	/**
	 * 
	 * 方法: register <br>
	 * 
	 * @param request
	 * @return
	 * @see com.drwljrtv.service.user.IUserService#register(com.drwljrtv.request.user.UserRequest)
	 */
	@Override
	public JSONObject register(UserRequest request) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "register");
		param.put("username", request.getUsername());
		param.put("password", request.getPassword());
		param.put("cpassword", request.getCpassword());
		param.put("email", request.getUsername() + "@abc.com");
		param.put("category", String.valueOf(request.getCategory()));
		param.put("gender", request.getGender());
		param.put("org_id", "1");
		JSONObject obj = ApiHelper.getInstance().getObj(param);
		return obj;
	}

	/**
	 * 
	 * 方法: isLogin <br>
	 * 
	 * @param userid
	 * @return
	 * @see com.drwljrtv.service.user.IUserService#isLogin(java.lang.Integer)
	 */
	@Override
	public JSONObject isLogin(Integer userid) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "is_login");
		param.put("userid", String.valueOf(userid));
		JSONObject obj = ApiHelper.getInstance().getObj(param);
		return obj;
	}

	/**
	 * 
	 * 方法: getUserInfo <br>
	 * 
	 * @param userid
	 * @return
	 * @see com.drwljrtv.service.user.IUserService#getUserInfo(java.lang.Integer)
	 */
	@Override
	public JSONObject getUserInfo(Integer userid) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "get_user_info");
		param.put("userid", String.valueOf(userid));
		JSONObject obj = ApiHelper.getInstance().getObj(param);
		return obj;
	}

	@Override
	public JSONObject logout(UserRequest request) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "logout");
		param.put("userid", String.valueOf(request.getUserid()));
		param.put("token", request.getToken());
		JSONObject obj = ApiHelper.getInstance().getObj(param);
		return obj;

	}

	/**
	 * 
	 * 方法: changePassword <br>
	 * 
	 * @param request
	 * @return
	 * @see com.drwljrtv.service.user.IUserService#changePassword(com.drwljrtv.request.user.UserRequest)
	 */
	@Override
	public JSONObject changePassword(UserRequest request) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "change_password");
		param.put("userid", String.valueOf(request.getUserid()));
		param.put("token", request.getToken());
		param.put("old_password", request.getPassword());
		param.put("new_password", request.getCpassword());
		JSONObject obj = ApiHelper.getInstance().getObj(param);
		return obj;
	}

	/**
	 * 
	 * 方法: changeUserInfo <br>
	 * 
	 * @param request
	 * @return
	 * @see com.drwljrtv.service.user.IUserService#changeUserInfo(com.drwljrtv.request.user.UserRequest)
	 */
	@Override
	public JSONObject changeUserInfo(UserRequest request) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "change_user_info");
		param.put("userid", String.valueOf(request.getUserid()));
		param.put("token", request.getToken());
		param.put("truename", request.getTruename());
		param.put("sex", request.getGender());
		param.put("email", request.getEmail());
		param.put("link_type", request.getLinkType());
		param.put("ext_org", request.getExtOrg());
		param.put("about_me", request.getAboutMe());
		JSONObject obj = ApiHelper.getInstance().getObj(param);
		return obj;
	}

	/**
	 * 
	 * 方法: uploadAvatar <br>
	 * 
	 * @param request
	 * @return
	 * @see com.drwljrtv.service.user.IUserService#uploadAvatar(com.drwljrtv.request.user.UserRequest)
	 */
	@Override
	public JSONObject uploadAvatar(UserRequest request) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("cmd", "change_user_info");
		param.put("userid", String.valueOf(request.getUserid()));
		param.put("token", request.getToken());
		param.put("avatar_file", request.getAvatarFile());
		JSONObject obj = ApiHelper.getInstance().getObj(param);
		return obj;
	}
}
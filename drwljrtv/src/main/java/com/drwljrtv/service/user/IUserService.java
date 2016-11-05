package com.drwljrtv.service.user;

import com.alibaba.fastjson.JSONObject;
import com.drwljrtv.request.user.UserRequest;

/**
 * 
 * 类: IUserService <br>
 * 描述: 用户相关信息业务处理接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月5日 下午7:44:10
 */
public interface IUserService {

	/**
	 * 
	 * 方法: login <br>
	 * 描述: 用户登录 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月5日 下午7:44:16
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	JSONObject login(UserRequest request);

	/**
	 * 
	 * 方法: register <br>
	 * 描述: 用户注册 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月5日 下午7:48:24
	 * 
	 * @param request
	 * @return
	 */
	JSONObject register(UserRequest request);

	/**
	 * 
	 * 方法: isLogin <br>
	 * 描述: 用户登录状态 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月5日 下午9:58:37
	 * 
	 * @param userid
	 * @return
	 */
	JSONObject isLogin(Integer userid);

	/**
	 * 
	 * 方法: getUserInfo <br>
	 * 描述: 获取用户信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月5日 下午10:01:30
	 * 
	 * @param userid
	 * @return
	 */
	JSONObject getUserInfo(Integer userid);

	/**
	 * 
	 * 方法: logout <br>
	 * 描述: 用户注销 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月5日 下午10:04:03
	 * 
	 * @param request
	 * @return
	 */
	JSONObject logout(UserRequest request);

	/**
	 * 
	 * 方法: changePassword <br>
	 * 描述: 修改密码 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月5日 下午10:05:50
	 * 
	 * @param request
	 * @return
	 */
	JSONObject changePassword(UserRequest request);

	/**
	 * 
	 * 方法: changeUserInfo <br>
	 * 描述: 修改用户的个人信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月5日 下午10:06:38
	 * 
	 * @param request
	 * @return
	 */
	JSONObject changeUserInfo(UserRequest request);

	/**
	 * 
	 * 方法: uploadAvatar <br>
	 * 描述: 上传头像 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年11月5日 下午10:07:08
	 * 
	 * @param request
	 * @return
	 */
	JSONObject uploadAvatar(UserRequest request);
}

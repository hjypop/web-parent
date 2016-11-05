package com.drwljrtv.request.user;

public class UserRequest {

	/**
	 * 用户id
	 */
	private Integer userid;
	/**
	 * 用户令牌
	 */
	private String token;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;

	/**
	 * 重复密码
	 */
	private String cpassword;

	/**
	 * 电子邮箱
	 */
	private String email;

	/**
	 * 用户类型，1：学生，2：教师
	 */
	private Integer category;

	/**
	 * 性别：male：男性，female：女性
	 */
	private String gender;

	/**
	 * 是否记住登录状态，值：true 为记住
	 */
	private String remember;

	/**
	 * 真实姓名
	 */
	private String truename;
	/**
	 * 联系方式
	 */
	private String linkType;

	/**
	 * 工作单位
	 */
	private String extOrg;

	/**
	 * 个人简介
	 */
	private String aboutMe;;

	/**
	 * 表单元素为文件类型
	 */
	private String avatarFile;

	public String getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(String avatarFile) {
		this.avatarFile = avatarFile;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getExtOrg() {
		return extOrg;
	}

	public void setExtOrg(String extOrg) {
		this.extOrg = extOrg;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getRemember() {
		return remember;
	}

	public void setRemember(String remember) {
		this.remember = remember;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

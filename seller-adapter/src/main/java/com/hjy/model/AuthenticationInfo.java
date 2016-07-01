package com.hjy.model;

/**
 * 用户真实认证信息
 * @author jlin
 *
 */
public class AuthenticationInfo {

	private String auth_code;
	private String true_name;
	private String idcard_type;
	private String idcard_number;
	private String phone_number;
	private String email;
	private String address;
	
	
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	public String getTrue_name() {
		return true_name;
	}
	public void setTrue_name(String true_name) {
		this.true_name = true_name;
	}
	public String getIdcard_type() {
		return idcard_type;
	}
	public void setIdcard_type(String idcard_type) {
		this.idcard_type = idcard_type;
	}
	public String getIdcard_number() {
		return idcard_number;
	}
	public void setIdcard_number(String idcard_number) {
		this.idcard_number = idcard_number;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}

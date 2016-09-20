package com.hjy.dto.request.subscribeOrder;

import com.alibaba.fastjson.annotation.JSONField;

public class AuthenticationInfo {
	
	private String Name;
	private int IDCardType;
	private String IDCardNumber;
	private String PhoneNumber;
	private String Email;
	
	@JSONField(name = "Name")
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	@JSONField(name = "IDCardType")
	public int getIDCardType() {
		return IDCardType;
	}
	public void setIDCardType(int iDCardType) {
		IDCardType = iDCardType;
	}
	
	@JSONField(name = "IDCardNumber")
	public String getIDCardNumber() {
		return IDCardNumber;
	}
	public void setIDCardNumber(String iDCardNumber) {
		IDCardNumber = iDCardNumber;
	}
	
	@JSONField(name = "PhoneNumber")
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	
	@JSONField(name = "Email")
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
	
}

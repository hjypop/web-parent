package com.hjy.dto.request.subscribeOrder;

public class AuthenticationInfo {
	
	private int IDCardType;
	private String Name;
	private String IDCardNumber;
	private String PhoneNumber;
	private String Email;
	
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getIDCardType() {
		return IDCardType;
	}
	public void setIDCardType(int iDCardType) {
		IDCardType = iDCardType;
	}
	public String getIDCardNumber() {
		return IDCardNumber;
	}
	public void setIDCardNumber(String iDCardNumber) {
		IDCardNumber = iDCardNumber;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
	
}

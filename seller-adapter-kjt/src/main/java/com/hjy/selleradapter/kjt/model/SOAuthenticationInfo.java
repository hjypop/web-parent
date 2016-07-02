package com.hjy.selleradapter.kjt.model;

public class SOAuthenticationInfo {

	private String Name;
	private int IDCardType;
	private String IDCardNumber;
	private String PhoneNumber;
	private String Email;
	private String Address;
	
	
	public SOAuthenticationInfo() {
		super();
	}
	public SOAuthenticationInfo(String name, int iDCardType,
			String iDCardNumber, String phoneNumber, String email,
			String address) {
		super();
		Name = name;
		IDCardType = iDCardType;
		IDCardNumber = iDCardNumber;
		PhoneNumber = phoneNumber;
		Email = email;
		Address = address;
	}
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
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
}

package com.hjy.request.customsDeclaration;


public class RequestParam {
	private String c_mid;  // 支付网关商户号 1000447 惠家有自己的
	private String c_ymd;   // 20160601131934
	private Customs customs;
	
	public String getC_mid() {
		return c_mid;
	}
	public void setC_mid(String c_mid) {
		this.c_mid = c_mid;
	}
	public String getC_ymd() {
		return c_ymd;
	}
	public void setC_ymd(String c_ymd) {
		this.c_ymd = c_ymd;
	}
	public Customs getCustoms() {
		return customs;
	}
	public void setCustoms(Customs customs) {
		this.customs = customs;
	}
}

package com.hjy.request.customsDeclaration;

import java.math.BigDecimal;

public class Customs {
	private Integer c_customs; // 海关编号 宁波什么的，附件中有
	private BigDecimal c_customs_amount;  // 报关金额*,单位:元
	private String c_customs_code; // 备案号* 商户提供？？？跨境商户的报关码???????
	private String c_customs_name;  // 备案名称* 商户提供？？？海关备案名称????????
	private String c_customs_order;  // 商户报关单*  唯一 orderCode + 时间戳
	private BigDecimal c_duty;  // 关税,可空,单位:元
	private String c_idno;  // 证件号 * 身份证号
	private String c_idnotype = "IDCARD"; // 证件类型 *
	private Integer c_moneytype;  // 币种,可空.拆单后不可空.
	private String c_name;  // 用户姓名,可空
	private String c_order ;  // //已支付订单号*   大订单号
	private BigDecimal c_product_fee; // 产品费用,拆单后不可空,单位:元
	private String c_sub_order; // DD99878987   小订单号
	private BigDecimal c_transport_fee; // //运费,拆单后不可空,单位:元
	
	
	public Integer getC_customs() {
		return c_customs;
	}
	public void setC_customs(Integer c_customs) {
		this.c_customs = c_customs;
	}
	public BigDecimal getC_customs_amount() {
		return c_customs_amount;
	}
	public void setC_customs_amount(BigDecimal c_customs_amount) {
		this.c_customs_amount = c_customs_amount;
	}
	public String getC_customs_code() {
		return c_customs_code;
	}
	public void setC_customs_code(String c_customs_code) {
		this.c_customs_code = c_customs_code;
	}
	public String getC_customs_name() {
		return c_customs_name;
	}
	public void setC_customs_name(String c_customs_name) {
		this.c_customs_name = c_customs_name;
	}
	public String getC_customs_order() {
		return c_customs_order;
	}
	public void setC_customs_order(String c_customs_order) {
		this.c_customs_order = c_customs_order;
	}
	public BigDecimal getC_duty() {
		return c_duty;
	}
	public void setC_duty(BigDecimal c_duty) {
		this.c_duty = c_duty;
	}
	public String getC_idno() {
		return c_idno;
	}
	public void setC_idno(String c_idno) {
		this.c_idno = c_idno;
	}
	public String getC_idnotype() {
		return c_idnotype;
	}
	public void setC_idnotype(String c_idnotype) {
		this.c_idnotype = c_idnotype;
	}
	public Integer getC_moneytype() {
		return c_moneytype;
	}
	public void setC_moneytype(Integer c_moneytype) {
		this.c_moneytype = c_moneytype;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getC_order() {
		return c_order;
	}
	public void setC_order(String c_order) {
		this.c_order = c_order;
	}
	public BigDecimal getC_product_fee() {
		return c_product_fee;
	}
	public void setC_product_fee(BigDecimal c_product_fee) {
		this.c_product_fee = c_product_fee;
	}
	public String getC_sub_order() {
		return c_sub_order;
	}
	public void setC_sub_order(String c_sub_order) {
		this.c_sub_order = c_sub_order;
	}
	public BigDecimal getC_transport_fee() {
		return c_transport_fee;
	}
	public void setC_transport_fee(BigDecimal c_transport_fee) {
		this.c_transport_fee = c_transport_fee;
	}
	
}

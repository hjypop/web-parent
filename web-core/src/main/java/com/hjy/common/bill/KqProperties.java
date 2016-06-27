package com.hjy.common.bill;

public class KqProperties 
{
	private String merchant_id = "";
	//退款接口版本号 目前固定为此值
	private String version = "";
	//操作类型
	private String command_type =  "";
	//加密所需的key值，线上的话发到商户快钱账户邮箱里
	private String merchant_key=  "";
	//原商户订单号
	private String orderid = "";
	//退款金额，整数或小数，小数位为2位   以人民币元为单位
	private String amount = "";
	//退款提交时间 数字串，一共14位 格式为：年[4 位]月[2 位]日[2 位]时[2 位]分[2 位]秒[2位]
	private String postdate = "";
	//退款流水号  字符串
	private String txOrder = "";
	//生成加密签名串
	private String mac = "";
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCommand_type() {
		return command_type;
	}
	public void setCommand_type(String command_type) {
		this.command_type = command_type;
	}
	public String getMerchant_key() {
		return merchant_key;
	}
	public void setMerchant_key(String merchant_key) {
		this.merchant_key = merchant_key;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPostdate() {
		return postdate;
	}
	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}
	public String getTxOrder() {
		return txOrder;
	}
	public void setTxOrder(String txOrder) {
		this.txOrder = txOrder;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public KqProperties(String merchant_id, String version,
			String command_type, String merchant_key, String orderid,
			String amount, String postdate, String txOrder, String mac) {
		super();
		this.merchant_id = merchant_id;
		this.version = version;
		this.command_type = command_type;
		this.merchant_key = merchant_key;
		this.orderid = orderid;
		this.amount = amount;
		this.postdate = postdate;
		this.txOrder = txOrder;
		this.mac = mac;
	}
	public KqProperties() {
		super();
		// TODO Auto-generated constructor stub
	}
}

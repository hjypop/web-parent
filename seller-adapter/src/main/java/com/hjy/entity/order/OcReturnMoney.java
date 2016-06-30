package com.hjy.entity.order;

import java.math.BigDecimal;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: OcReturnMoney <br>
 * 描述: 退款管理表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月30日 上午10:22:11
 */

public class OcReturnMoney extends BaseModel {
	// 退货单编码
	private String returnGoodsCode;
	// 退款单号
	private String returnMoneyCode;
	// 买家编号
	private String buyerCode;
	// 订单编号
	private String orderCode;
	// 商家编码
	private String sellerCode;
	// 联系人
	private String contacts;
	// 状态
	private String status;
	// 退款金额
	private BigDecimal returnMoney;
	// 电话
	private String mobile;
	// 创建时间
	private String createTime;
	// 手续费
	private BigDecimal poundage;
	// 支付方式
	private String payMethod;
	// 退款金额
	private BigDecimal onlineMoney;
	// 交易流水号
	private String batchNo;
	// 退款确认标识
	private String returnConf;
	// 小卖家编号
	private String smallSellerCode;
	// 已退款成功金额
	private BigDecimal returnedMoney;

	public String getReturnGoodsCode() {
		return returnGoodsCode;
	}

	public void setReturnGoodsCode(String returnGoodsCode) {
		this.returnGoodsCode = returnGoodsCode;
	}

	public String getReturnMoneyCode() {
		return returnMoneyCode;
	}

	public void setReturnMoneyCode(String returnMoneyCode) {
		this.returnMoneyCode = returnMoneyCode;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(BigDecimal returnMoney) {
		this.returnMoney = returnMoney;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public BigDecimal getOnlineMoney() {
		return onlineMoney;
	}

	public void setOnlineMoney(BigDecimal onlineMoney) {
		this.onlineMoney = onlineMoney;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getReturnConf() {
		return returnConf;
	}

	public void setReturnConf(String returnConf) {
		this.returnConf = returnConf;
	}

	public String getSmallSellerCode() {
		return smallSellerCode;
	}

	public void setSmallSellerCode(String smallSellerCode) {
		this.smallSellerCode = smallSellerCode;
	}

	public BigDecimal getReturnedMoney() {
		return returnedMoney;
	}

	public void setReturnedMoney(BigDecimal returnedMoney) {
		this.returnedMoney = returnedMoney;
	}

}

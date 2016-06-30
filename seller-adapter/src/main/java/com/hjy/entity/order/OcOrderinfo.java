package com.hjy.entity.order;

import java.math.BigDecimal;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: OcOrderinfo <br>
 * 描述: 订单信息表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午6:14:58
 */
public class OcOrderinfo extends BaseModel{

	private String orderCode;

	private String orderSource;

	private String orderType;

	private String orderStatus;

	private String sellerCode;

	private String buyerCode;

	private String payType;

	private String sendType;

	private BigDecimal productMoney;

	private BigDecimal transportMoney;

	private BigDecimal promotionMoney;

	private BigDecimal orderMoney;

	private BigDecimal payedMoney;

	private String createTime;

	private String updateTime;

	private String productName;

	private BigDecimal freeTransportMoney;

	private BigDecimal dueMoney;

	private String orderChannel;

	private String appVersion;

	private String deleteFlag;

	private String outOrderCode;

	private String bigOrderCode;

	private String orderStatusExt;

	private String smallSellerCode;

	private String orderSeq;

	private String orderAuditStatus;

	private String lowOrder;

	private String roomId;

	private String anchorId;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public BigDecimal getProductMoney() {
		return productMoney;
	}

	public void setProductMoney(BigDecimal productMoney) {
		this.productMoney = productMoney;
	}

	public BigDecimal getTransportMoney() {
		return transportMoney;
	}

	public void setTransportMoney(BigDecimal transportMoney) {
		this.transportMoney = transportMoney;
	}

	public BigDecimal getPromotionMoney() {
		return promotionMoney;
	}

	public void setPromotionMoney(BigDecimal promotionMoney) {
		this.promotionMoney = promotionMoney;
	}

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}

	public BigDecimal getPayedMoney() {
		return payedMoney;
	}

	public void setPayedMoney(BigDecimal payedMoney) {
		this.payedMoney = payedMoney;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getFreeTransportMoney() {
		return freeTransportMoney;
	}

	public void setFreeTransportMoney(BigDecimal freeTransportMoney) {
		this.freeTransportMoney = freeTransportMoney;
	}

	public BigDecimal getDueMoney() {
		return dueMoney;
	}

	public void setDueMoney(BigDecimal dueMoney) {
		this.dueMoney = dueMoney;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getOutOrderCode() {
		return outOrderCode;
	}

	public void setOutOrderCode(String outOrderCode) {
		this.outOrderCode = outOrderCode;
	}

	public String getBigOrderCode() {
		return bigOrderCode;
	}

	public void setBigOrderCode(String bigOrderCode) {
		this.bigOrderCode = bigOrderCode;
	}

	public String getOrderStatusExt() {
		return orderStatusExt;
	}

	public void setOrderStatusExt(String orderStatusExt) {
		this.orderStatusExt = orderStatusExt;
	}

	public String getSmallSellerCode() {
		return smallSellerCode;
	}

	public void setSmallSellerCode(String smallSellerCode) {
		this.smallSellerCode = smallSellerCode;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getOrderAuditStatus() {
		return orderAuditStatus;
	}

	public void setOrderAuditStatus(String orderAuditStatus) {
		this.orderAuditStatus = orderAuditStatus;
	}

	public String getLowOrder() {
		return lowOrder;
	}

	public void setLowOrder(String lowOrder) {
		this.lowOrder = lowOrder;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getAnchorId() {
		return anchorId;
	}

	public void setAnchorId(String anchorId) {
		this.anchorId = anchorId;
	}

}

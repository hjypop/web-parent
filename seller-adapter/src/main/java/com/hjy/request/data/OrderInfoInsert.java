package com.hjy.request.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hjy.annotation.ExculdeNullField;

/**
 * @descriptions 插入订单状态信息
 *  惠家有：商户；第三方：销售平台。
 *  第三方将订单信息发送给惠家有，惠家有插入订单
 * 
 * @date 2016年8月29日上午10:59:10
 * @author Yangcl
 * @version 1.0.1
 */
public class OrderInfoInsert {
	
	private String orderCode;
//	private String orderSource; // 订单来源: default 449715190009 分销订单
//	private String orderType;  // 订单类型: default 449715200005 普通订单
//	private String orderStatus; // 订单状态: default  4497153900010002 下单成功-未发货
//	private String sellerCode; // default SI2003
//	private String buyerCode; // TODO 怎么定？
	private String payType; // 付款方式 449716200001 ~ 5  1@在线支付|2@货到付款|4@微信支付  
	private String sendType; // 配送方式 449715210001 快递 |449715210002 邮局
	private BigDecimal productMoney; 
	
	@ExculdeNullField
	private BigDecimal transportMoney; // 商品运费
	
	private BigDecimal orderMoney;  // 订单金额
	private BigDecimal payedMoney; // 已支付金额
//	private Date createTime;
	private String productName;
	private BigDecimal dueMoney; // 应付款
	
//	private String orderChannel; // 订单来源频道 default  449747430006
	// TODO 此处在sc_define表中没有 需要添加如下数据：449747430006 | 分销订单 |44974743
	
	// order_status_ext 订单辅助状态
	// 4497153900140001 ~ 5   已取消|@空|部分退货|全部退货|取消发货
//	private String orderStatusExt; // 订单辅助状态  default  4497153900140002
	
//	private String smallSellerCode; // 商家code 商家无需传递此字段
	
	// order_audit_status 订单审核状态
	// 449746680001 ~ 4  全部|待审核|@通过|未通过
//	private String orderAuditStatus; // default  449746680003
	
	
	// low_order 抄低价订单 
	// 449747110001 @否  449747110002 是
	private String lowOrder; // default  449747110001
	
	
	private List<OrderDetailInsert> list ;  // 一个订单买了多个sku商品 



	public String getOrderCode() {
		return orderCode;
	}


	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public BigDecimal getDueMoney() {
		return dueMoney;
	}


	public void setDueMoney(BigDecimal dueMoney) {
		this.dueMoney = dueMoney;
	}


	public String getLowOrder() {
		return lowOrder;
	}


	public void setLowOrder(String lowOrder) {
		this.lowOrder = lowOrder;
	}


	public List<OrderDetailInsert> getList() {
		return list;
	}


	public void setList(List<OrderDetailInsert> list) {
		this.list = list;
	}
	
	
	
	
}































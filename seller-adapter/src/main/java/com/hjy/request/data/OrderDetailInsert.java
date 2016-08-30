package com.hjy.request.data;

import java.math.BigDecimal;

/**
 * @descriptions 插入订单状态信息
 *  惠家有：商户；第三方：销售平台。
 *  第三方将订单信息发送给惠家有，惠家有插入订单
 * 
 * @date 2016年8月29日上午10:59:10
 * @author Yangcl
 * @version 1.0.1
 */
public class OrderDetailInsert   implements Comparable<OrderDetailInsert> {
	
//	private String uid;
//	private String orderCode; // 此字段无需传递
	private String skuCode;
	private String productCode;
	private String skuName;
	private BigDecimal skuPrice;
	private int skuNmu;
//	private String giftFlag;                     // 赠品标示 1：非赠品  0：赠品
	private BigDecimal showPrice;
	
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public BigDecimal getSkuPrice() {
		return skuPrice;
	}
	public void setSkuPrice(BigDecimal skuPrice) {
		this.skuPrice = skuPrice;
	}
	public int getSkuNmu() {
		return skuNmu;
	}
	public void setSkuNmu(int skuNmu) {
		this.skuNmu = skuNmu;
	}
	public BigDecimal getShowPrice() {
		return showPrice;
	}
	public void setShowPrice(BigDecimal showPrice) {
		this.showPrice = showPrice;
	}
	@Override
	public int compareTo(OrderDetailInsert o) {
		return this.getSkuCode().compareTo(o.getSkuCode());
	}
	 
}











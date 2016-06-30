package com.hjy.entity;

import java.math.BigDecimal;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: OcOrderKjtDetail <br>
 * 描述: 跨境通订单明细表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午5:18:49
 */
public class OcOrderKjtDetail extends BaseModel {

	// 发货单号
	private String orderCodeSeq;
	// 商品编号
	private String productCode;
	// 产品编号
	private String skuCode;
	// 产品价格
	private BigDecimal skuPrice;
	// 产品名称
	private String skuName;
	// 产品数量
	private Integer skuNum;
	// 外部商品编号
	private String productCodeOut;
	// 订单编号
	private String orderCode;

	public String getOrderCodeSeq() {
		return orderCodeSeq;
	}

	public void setOrderCodeSeq(String orderCodeSeq) {
		this.orderCodeSeq = orderCodeSeq;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public BigDecimal getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(BigDecimal skuPrice) {
		this.skuPrice = skuPrice;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Integer getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(Integer skuNum) {
		this.skuNum = skuNum;
	}

	public String getProductCodeOut() {
		return productCodeOut;
	}

	public void setProductCodeOut(String productCodeOut) {
		this.productCodeOut = productCodeOut;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

}

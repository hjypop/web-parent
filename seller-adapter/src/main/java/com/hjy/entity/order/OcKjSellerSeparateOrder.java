package com.hjy.entity.order;

import java.math.BigDecimal;
import java.util.Date;

public class OcKjSellerSeparateOrder {
	
    private Integer zid;

    private String orderCode;

    private String sellerOrderCode;

    private String orderCodeSeq;

    private String status;

    private String sellerStatus;

    private Date createTime;

    private BigDecimal skuSellPrice;

    private BigDecimal taxPrice;

    private Integer quantity;

    private String orderType;

    private BigDecimal freight;

    private Date updateTime;

    private String productCode;

    private String skuCode;

    private String skuName;

    private String sellerCode;

    private String sellerProductCode;

    private String sellerSkuCode;

    
    private String requestJson;

    private String remark;
    
    
    
    
    
    
    
    public Integer getZid() {
        return zid;
    }

    public void setZid(Integer zid) {
        this.zid = zid;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getSellerOrderCode() {
        return sellerOrderCode;
    }

    public void setSellerOrderCode(String sellerOrderCode) {
        this.sellerOrderCode = sellerOrderCode == null ? null : sellerOrderCode.trim();
    }

    public String getOrderCodeSeq() {
        return orderCodeSeq;
    }

    public void setOrderCodeSeq(String orderCodeSeq) {
        this.orderCodeSeq = orderCodeSeq == null ? null : orderCodeSeq.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSellerStatus() {
        return sellerStatus;
    }

    public void setSellerStatus(String sellerStatus) {
        this.sellerStatus = sellerStatus == null ? null : sellerStatus.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getSkuSellPrice() {
        return skuSellPrice;
    }

    public void setSkuSellPrice(BigDecimal skuSellPrice) {
        this.skuSellPrice = skuSellPrice;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode == null ? null : sellerCode.trim();
    }

    public String getSellerProductCode() {
        return sellerProductCode;
    }

    public void setSellerProductCode(String sellerProductCode) {
        this.sellerProductCode = sellerProductCode == null ? null : sellerProductCode.trim();
    }

    public String getSellerSkuCode() {
        return sellerSkuCode;
    }

    public void setSellerSkuCode(String sellerSkuCode) {
        this.sellerSkuCode = sellerSkuCode == null ? null : sellerSkuCode.trim();
    }
    
    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson == null ? null : requestJson.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
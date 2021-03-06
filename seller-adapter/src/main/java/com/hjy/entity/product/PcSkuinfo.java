package com.hjy.entity.product;

import java.math.BigDecimal;

import com.hjy.base.BaseModel;

public class PcSkuinfo extends BaseModel {
	private String skuCode;
	private String skuCodeOld;
	private String productCode;
	private BigDecimal sellPrice;
	private BigDecimal marketPrice;
	private BigDecimal costPrice;
	private Long stockNum;
	private String skuKey;
	private String skuKeyvalue;
	private String skuPicurl;
	private String sellProductcode;
	private String sellerCode;
	private Long securityStockNum;
	private String productCodeOld;
	private String qrcodeLink;
	private Integer sellCount;
	private String saleYn;
	private Integer flagEnable;
	private String barcode;
	private Integer miniOrder;
	private String skuName;
	private String skuAdv;

	
	
	
	public PcSkuinfo() {
	}
	
	

	public PcSkuinfo(String productCode) {
		this.productCode = productCode;
	}



	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuAdv() {
		return skuAdv;
	}

	public void setSkuAdv(String skuAdv) {
		this.skuAdv = skuAdv;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.sku_code_old
	 *
	 * @return the value of pc_skuinfo.sku_code_old
	 *
	 * @mbggenerated
	 */
	public String getSkuCodeOld() {
		return skuCodeOld;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.sku_code_old
	 *
	 * @param skuCodeOld
	 *            the value for pc_skuinfo.sku_code_old
	 *
	 * @mbggenerated
	 */
	public void setSkuCodeOld(String skuCodeOld) {
		this.skuCodeOld = skuCodeOld;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.sku_code
	 *
	 * @return the value of pc_skuinfo.sku_code
	 *
	 * @mbggenerated
	 */
	public String getSkuCode() {
		return skuCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.sku_code
	 *
	 * @param skuCode
	 *            the value for pc_skuinfo.sku_code
	 *
	 * @mbggenerated
	 */
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.product_code
	 *
	 * @return the value of pc_skuinfo.product_code
	 *
	 * @mbggenerated
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.product_code
	 *
	 * @param productCode
	 *            the value for pc_skuinfo.product_code
	 *
	 * @mbggenerated
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.sell_price
	 *
	 * @return the value of pc_skuinfo.sell_price
	 *
	 * @mbggenerated
	 */
	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.sell_price
	 *
	 * @param sellPrice
	 *            the value for pc_skuinfo.sell_price
	 *
	 * @mbggenerated
	 */
	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.market_price
	 *
	 * @return the value of pc_skuinfo.market_price
	 *
	 * @mbggenerated
	 */
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.market_price
	 *
	 * @param marketPrice
	 *            the value for pc_skuinfo.market_price
	 *
	 * @mbggenerated
	 */
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.cost_price
	 *
	 * @return the value of pc_skuinfo.cost_price
	 *
	 * @mbggenerated
	 */
	public BigDecimal getCostPrice() {
		return costPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.cost_price
	 *
	 * @param costPrice
	 *            the value for pc_skuinfo.cost_price
	 *
	 * @mbggenerated
	 */
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.stock_num
	 *
	 * @return the value of pc_skuinfo.stock_num
	 *
	 * @mbggenerated
	 */
	public Long getStockNum() {
		return stockNum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.stock_num
	 *
	 * @param stockNum
	 *            the value for pc_skuinfo.stock_num
	 *
	 * @mbggenerated
	 */
	public void setStockNum(Long stockNum) {
		this.stockNum = stockNum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.sku_key
	 *
	 * @return the value of pc_skuinfo.sku_key
	 *
	 * @mbggenerated
	 */
	public String getSkuKey() {
		return skuKey;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.sku_key
	 *
	 * @param skuKey
	 *            the value for pc_skuinfo.sku_key
	 *
	 * @mbggenerated
	 */
	public void setSkuKey(String skuKey) {
		this.skuKey = skuKey;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.sku_keyvalue
	 *
	 * @return the value of pc_skuinfo.sku_keyvalue
	 *
	 * @mbggenerated
	 */
	public String getSkuKeyvalue() {
		return skuKeyvalue;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.sku_keyvalue
	 *
	 * @param skuKeyvalue
	 *            the value for pc_skuinfo.sku_keyvalue
	 *
	 * @mbggenerated
	 */
	public void setSkuKeyvalue(String skuKeyvalue) {
		this.skuKeyvalue = skuKeyvalue;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.sku_picurl
	 *
	 * @return the value of pc_skuinfo.sku_picurl
	 *
	 * @mbggenerated
	 */
	public String getSkuPicurl() {
		return skuPicurl;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.sku_picurl
	 *
	 * @param skuPicurl
	 *            the value for pc_skuinfo.sku_picurl
	 *
	 * @mbggenerated
	 */
	public void setSkuPicurl(String skuPicurl) {
		this.skuPicurl = skuPicurl;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.sell_productcode
	 *
	 * @return the value of pc_skuinfo.sell_productcode
	 *
	 * @mbggenerated
	 */
	public String getSellProductcode() {
		return sellProductcode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.sell_productcode
	 *
	 * @param sellProductcode
	 *            the value for pc_skuinfo.sell_productcode
	 *
	 * @mbggenerated
	 */
	public void setSellProductcode(String sellProductcode) {
		this.sellProductcode = sellProductcode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.seller_code
	 *
	 * @return the value of pc_skuinfo.seller_code
	 *
	 * @mbggenerated
	 */
	public String getSellerCode() {
		return sellerCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.seller_code
	 *
	 * @param sellerCode
	 *            the value for pc_skuinfo.seller_code
	 *
	 * @mbggenerated
	 */
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.security_stock_num
	 *
	 * @return the value of pc_skuinfo.security_stock_num
	 *
	 * @mbggenerated
	 */
	public Long getSecurityStockNum() {
		return securityStockNum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.security_stock_num
	 *
	 * @param securityStockNum
	 *            the value for pc_skuinfo.security_stock_num
	 *
	 * @mbggenerated
	 */
	public void setSecurityStockNum(Long securityStockNum) {
		this.securityStockNum = securityStockNum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.product_code_old
	 *
	 * @return the value of pc_skuinfo.product_code_old
	 *
	 * @mbggenerated
	 */
	public String getProductCodeOld() {
		return productCodeOld;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.product_code_old
	 *
	 * @param productCodeOld
	 *            the value for pc_skuinfo.product_code_old
	 *
	 * @mbggenerated
	 */
	public void setProductCodeOld(String productCodeOld) {
		this.productCodeOld = productCodeOld;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.qrcode_link
	 *
	 * @return the value of pc_skuinfo.qrcode_link
	 *
	 * @mbggenerated
	 */
	public String getQrcodeLink() {
		return qrcodeLink;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.qrcode_link
	 *
	 * @param qrcodeLink
	 *            the value for pc_skuinfo.qrcode_link
	 *
	 * @mbggenerated
	 */
	public void setQrcodeLink(String qrcodeLink) {
		this.qrcodeLink = qrcodeLink;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.sell_count
	 *
	 * @return the value of pc_skuinfo.sell_count
	 *
	 * @mbggenerated
	 */
	public Integer getSellCount() {
		return sellCount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.sell_count
	 *
	 * @param sellCount
	 *            the value for pc_skuinfo.sell_count
	 *
	 * @mbggenerated
	 */
	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.sale_yn
	 *
	 * @return the value of pc_skuinfo.sale_yn
	 *
	 * @mbggenerated
	 */
	public String getSaleYn() {
		return saleYn;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.sale_yn
	 *
	 * @param saleYn
	 *            the value for pc_skuinfo.sale_yn
	 *
	 * @mbggenerated
	 */
	public void setSaleYn(String saleYn) {
		this.saleYn = saleYn;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.flag_enable
	 *
	 * @return the value of pc_skuinfo.flag_enable
	 *
	 * @mbggenerated
	 */
	public Integer getFlagEnable() {
		return flagEnable;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.flag_enable
	 *
	 * @param flagEnable
	 *            the value for pc_skuinfo.flag_enable
	 *
	 * @mbggenerated
	 */
	public void setFlagEnable(Integer flagEnable) {
		this.flagEnable = flagEnable;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.barcode
	 *
	 * @return the value of pc_skuinfo.barcode
	 *
	 * @mbggenerated
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.barcode
	 *
	 * @param barcode
	 *            the value for pc_skuinfo.barcode
	 *
	 * @mbggenerated
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pc_skuinfo.mini_order
	 *
	 * @return the value of pc_skuinfo.mini_order
	 *
	 * @mbggenerated
	 */
	public Integer getMiniOrder() {
		return miniOrder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pc_skuinfo.mini_order
	 *
	 * @param miniOrder
	 *            the value for pc_skuinfo.mini_order
	 *
	 * @mbggenerated
	 */
	public void setMiniOrder(Integer miniOrder) {
		this.miniOrder = miniOrder;
	}
}
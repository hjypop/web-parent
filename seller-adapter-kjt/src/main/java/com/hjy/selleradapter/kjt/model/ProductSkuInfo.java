package com.hjy.selleradapter.kjt.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**   
*    
* 项目名称：productcenter   
* 类名称：ProductSkuInfo   
* 类描述：   
* 创建人：yanzj  
* 创建时间：2013-9-2 上午11:09:03   
* 修改人：yanzj
* 修改时间：2013-9-2 上午11:09:03   
* 修改备注：   
* @version    
*    
*/
public class ProductSkuInfo  {
	
	
	private String uid="";

	/**
	 * 产品编号
	 */
	private String skuCode ="";

	/**
	 * 老sku编号
	 */
	private String skuCodeOld ="";
	
	/**
	 * 商品编号
	 */
	private String productCode="";
	/**
	 * 销售价
	 */
	private BigDecimal sellPrice=new BigDecimal(0.00);
	/**
	 * 市场价
	 */
	private BigDecimal marketPrice = new BigDecimal(0.00);
	/**
	 * 成本价
	 */
	private BigDecimal costPrice = new BigDecimal(0.00);
	/**
	 * 库存数
	 */
	private int stockNum= 0;
	
	
	/**
	 * skuKey
	 */
	private String skuKey = "";
	
	/**
	 * skuValue
	 */
	private String skuValue = "";
	
	/**
	 * skuValue
	 */
	private String skuKeyvalue = "";
	
	
	 /**
     * 商品的Sku的图片信息
     */
    private String skuPicUrl = "";
	
    
    /**
     * 商品的sku信息
     */
    private String skuName="";
    
    /**
     * 商家编码
     */
    private String sellProductcode="";
    
    
    /**
     * sku安全库存
     */
    private int securityStockNum = 0;
    
    
    /**
     * 卖家编号
     */
    private String sellerCode = "";
    
    
    /**
     * 广告语
     */
    private String skuAdv="";
    
    
    /**
     * sku二维码
     */
    private String qrcodeLink = "";
    
    
    /**
     * 积分抵扣 单位 个 需要 10%折 钱。
     */
    private BigDecimal virtualMoneyDeduction= new BigDecimal(0.00);
    /**
     * 销售数量
     */
    private int sellCount = 0;
    
    /**
     * 是否可买
     */
    private String saleYn="Y";
    
    /**
     * 是否删除
     */
    private String flagEnable="0";
    /**
     * 条形码
     */
    private String barcode = "";

    /**
     * 最小购买数
     */
    private Integer miniOrder = 1;
    /**
     * 商品库存
     */
    private List<ScStoreSkunum> scStoreSkunumList = new ArrayList<ScStoreSkunum>();
    
    private String validateFlag="";
    private String smallSellerCode="";
    
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getQrcodeLink() {
		return qrcodeLink;
	}
	public void setQrcodeLink(String qrcodeLink) {
		this.qrcodeLink = qrcodeLink;
	}
	public String getSkuAdv() {
		return skuAdv;
	}
	public void setSkuAdv(String skuAdv) {
		this.skuAdv = skuAdv;
	}
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public int getSecurityStockNum() {
		return securityStockNum;
	}
	public void setSecurityStockNum(int securityStockNum) {
		this.securityStockNum = securityStockNum;
	}
	
	public String getSellProductcode() {
		return sellProductcode;
	}
	public void setSellProductcode(String sellProductcode) {
		this.sellProductcode = sellProductcode;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getSkuKey() {
		return skuKey;
	}
	public void setSkuKey(String skuKey) {
		this.skuKey = skuKey;
	}
	public String getSkuValue() {
		return skuValue;
	}
	public void setSkuValue(String skuValue) {
		this.skuValue = skuValue;
	}
	
	public String getSkuPicUrl() {
		return skuPicUrl;
	}
	public void setSkuPicUrl(String skuPicUrl) {
		this.skuPicUrl = skuPicUrl;
	}
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
	public int getStockNum() {
		return stockNum;
	}
	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}
	public BigDecimal getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public BigDecimal getVirtualMoneyDeduction() {
		return virtualMoneyDeduction;
	}
	public void setVirtualMoneyDeduction(BigDecimal virtualMoneyDeduction) {
		this.virtualMoneyDeduction = virtualMoneyDeduction;
	}
	public int getSellCount() {
		return sellCount;
	}
	public void setSellCount(int sellCount) {
		this.sellCount = sellCount;
	}
	public String getSkuKeyvalue() {
		return skuKeyvalue;
	}
	public void setSkuKeyvalue(String skuKeyvalue) {
		this.skuKeyvalue = skuKeyvalue;
	}
	public String getSaleYn() {
		return saleYn;
	}
	public void setSaleYn(String saleYn) {
		this.saleYn = saleYn;
	}
	public String getFlagEnable() {
		return flagEnable;
	}
	public void setFlagEnable(String flagEnable) {
		this.flagEnable = flagEnable;
	}
	public List<ScStoreSkunum> getScStoreSkunumList() {
		return scStoreSkunumList;
	}
	public void setScStoreSkunumList(List<ScStoreSkunum> scStoreSkunumList) {
		this.scStoreSkunumList = scStoreSkunumList;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public Integer getMiniOrder() {
		return miniOrder;
	}
	public void setMiniOrder(Integer miniOrder) {
		this.miniOrder = miniOrder;
	}
	public String getSkuCodeOld() {
		return skuCodeOld;
	}
	public void setSkuCodeOld(String skuCodeOld) {
		this.skuCodeOld = skuCodeOld;
	}
	public String getValidateFlag() {
		return validateFlag;
	}
	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}
	public String getSmallSellerCode() {
		return smallSellerCode;
	}
	public void setSmallSellerCode(String smallSellerCode) {
		this.smallSellerCode = smallSellerCode;
	}
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	
}

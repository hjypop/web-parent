package com.hjy.model.order;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hjy.base.BaseClass;


/**   
*    订单明细表
*    
* 项目名称：ordercenter   
* 类名称：OrderDetail   
* 类描述：   
* 创建人：yanzj  
* 创建时间：2013-9-2 上午11:03:07   
* 修改人：yanzj
* 修改时间：2013-9-2 上午11:03:07   
* 修改备注： 
* 
* 修改人：Yangcl
* 修改时间：2016-6-30  
* 修改备注：与数据库所有字段已经对应   
* @version    
*    
*/
public class OrderDetail extends BaseClass implements Serializable{
	
	
	/**
	 * 订单明细的zid(创建订单勿传)
	 */
	private String zid= "";
	
    private String uid ="";
    
	/**
	 * 订单编号(创建接口勿传,此值会被覆盖，请勿传)
	 */
	private String orderCode = "";
	/**
	 * 产品编号
	 */
	private String skuCode = "";
	/**
	 * 商品编号
	 */
	private String productCode = "";
	
	/**
	 * 产品名称
	 */
	private String skuName = "";
	
    private String giftCd ="";
    
	/**
	 * 产品价格
	 */
	private BigDecimal skuPrice = new BigDecimal(0.00) ;
	
	/**
	 * 产品数量
	 */
	private int skuNum = 0;

	
	/**
	 * 商品的主图url
	 */
	private String productPicUrl = "";
	
	private String detailCode ="";
	
	
	 /**
     * 单个商品所用积分
     */
    private BigDecimal virtualMoneyDeduction=new BigDecimal(0.00);
	
    /** 仓库代码 */
    private String storeCode;
    
    private String giftFlag;
    
    private String skuKey;
    private String skuValue;
    
    //新扩展字段，用于拆单
    private String prchType;//一地入库类型
    private String oaSiteNo;//入库仓库编号
    private String dlrId;//供应商编号
    private String validateFlag="N";//虚拟商品标示   Y：是  N：否
    private String  gift_cd;//赠品类别
    private BigDecimal saveAmt = BigDecimal.ZERO;
	
    private BigDecimal costPrice = BigDecimal.ZERO;//成本价
    
    private String productCodeOut;
    
    private BigDecimal groupPrice=BigDecimal.ZERO;//微公社价格
    
    private BigDecimal couponPrice=BigDecimal.ZERO;
    private BigDecimal showPrice=BigDecimal.ZERO;
    
    private BigDecimal taxRate=BigDecimal.ZERO;//税率
    
    private String flagAsale ="";

    private String asaleCode ="";
    
	public String getZid() {
		return zid;
	}

	public void setZid(String zid) {
		this.zid = zid;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGiftCd() {
		return giftCd;
	}

	public void setGiftCd(String giftCd) {
		this.giftCd = giftCd;
	}

	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}

	public String getFlagAsale() {
		return flagAsale;
	}

	public void setFlagAsale(String flagAsale) {
		this.flagAsale = flagAsale;
	}

	public String getAsaleCode() {
		return asaleCode;
	}

	public void setAsaleCode(String asaleCode) {
		this.asaleCode = asaleCode;
	}

	public String getProductPicUrl() {
		return productPicUrl;
	}

	public void setProductPicUrl(String productPicUrl) {
		this.productPicUrl = productPicUrl;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public int getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(int skuNum) {
		this.skuNum = skuNum;
	}

	public BigDecimal getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(BigDecimal skuPrice) {
		this.skuPrice = skuPrice;
	}

	public BigDecimal getVirtualMoneyDeduction() {
		return virtualMoneyDeduction;
	}

	public void setVirtualMoneyDeduction(BigDecimal virtualMoneyDeduction) {
		this.virtualMoneyDeduction = virtualMoneyDeduction;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getGiftFlag() {
		return giftFlag;
	}

	public void setGiftFlag(String giftFlag) {
		this.giftFlag = giftFlag;
	}

	public String getPrchType() {
		return prchType;
	}

	public void setPrchType(String prchType) {
		this.prchType = prchType;
	}

	public String getOaSiteNo() {
		return oaSiteNo;
	}

	public void setOaSiteNo(String oaSiteNo) {
		this.oaSiteNo = oaSiteNo;
	}

	public String getDlrId() {
		return dlrId;
	}

	public void setDlrId(String dlrId) {
		this.dlrId = dlrId;
	}

	public String getValidateFlag() {
		return validateFlag;
	}

	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
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

	public String getGift_cd() {
		return gift_cd;
	}

	public void setGift_cd(String gift_cd) {
		this.gift_cd = gift_cd;
	}

	public BigDecimal getSaveAmt() {
		return saveAmt;
	}

	public void setSaveAmt(BigDecimal saveAmt) {
		this.saveAmt = saveAmt;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public String getProductCodeOut() {
		return productCodeOut;
	}

	public void setProductCodeOut(String productCodeOut) {
		this.productCodeOut = productCodeOut;
	}

	public BigDecimal getGroupPrice() {
		return groupPrice;
	}

	public void setGroupPrice(BigDecimal groupPrice) {
		this.groupPrice = groupPrice;
	}

	public BigDecimal getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(BigDecimal couponPrice) {
		this.couponPrice = couponPrice;
	}

	public BigDecimal getShowPrice() {
		return showPrice;
	}

	public void setShowPrice(BigDecimal showPrice) {
		this.showPrice = showPrice;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	
}

package com.hjy.entity.webcore;


import com.hjy.annotation.ExculdeNullField;
import com.hjy.base.BaseModel;

/**
 * 
 * 类: WcSellerinfo <br>
 * 描述: 接口商家信息表 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月24日 上午11:27:21
 */
public class WcSellerinfo extends BaseModel {

	private String uid;
	/**
	 * 商家编码
	 */
	private String sellerCode;
	/**
	 * 商家名称
	 */
	private String sellerName;
	/**
	 * 商家描述
	 */
	private String sellerDescrption;
	/**
	 * 联系电话
	 */
	private String sellerTelephone;
	/**
	 * 邮箱
	 */
	private String sellerEmail;
	
	// 商户类型:普通商户4497478100050001|跨境商户4497478100050002|跨境直邮4497478100050003|平台入驻4497478100050004
	private String sellerType = ""; 
	
	/**
	 * 商户状态 0 未开通 1 已开通 2 已禁用
	 */
	private Integer status;
	/**
	 * 价格类型 0 成本价 1 销售价
	 */
	private Integer priceType;
	/**
	 * 佣金比例 json存储
	 */
	private String commission;
	/**
	 * 创建人
	 */
	@ExculdeNullField
	private String creator;
	/**
	 * 创建时间
	 */
	@ExculdeNullField
	private String createTime;
	/**
	 * 修改人
	 */
	@ExculdeNullField
	private String updator;
	/**
	 * 修改时间
	 */
	@ExculdeNullField
	private String updateTime;
	
	private Integer type;

	private Integer flag;

    private String sellerCustomNumber;  // 商户的【商户海关备案编号】

    private String sellerCustomLocation;   // 商户报关地点|支付网关柄春做了2次封装后的对应码
	
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getSellerCustomNumber() {
		return sellerCustomNumber;
	}

	public void setSellerCustomNumber(String sellerCustomNumber) {
		this.sellerCustomNumber = sellerCustomNumber;
	}

	public String getSellerCustomLocation() {
		return sellerCustomLocation;
	}

	public void setSellerCustomLocation(String sellerCustomLocation) {
		this.sellerCustomLocation = sellerCustomLocation;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerDescrption() {
		return sellerDescrption;
	}

	public void setSellerDescrption(String sellerDescrption) {
		this.sellerDescrption = sellerDescrption;
	}

	public String getSellerTelephone() {
		return sellerTelephone;
	}

	public void setSellerTelephone(String sellerTelephone) {
		this.sellerTelephone = sellerTelephone;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
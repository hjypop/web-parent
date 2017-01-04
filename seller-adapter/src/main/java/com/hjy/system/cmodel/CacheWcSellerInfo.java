package com.hjy.system.cmodel;

import java.util.List;

/**
 * @description: wc_sellerinfo表的缓存值
 * 
 * @author Yangcl
 * @date 2016年12月22日 下午4:32:49 
 * @version 1.0.0
 */
public class CacheWcSellerInfo {
	private String uid;  // app secret  
	private String sellerCode;  // 商家编码
	private String sellerName;  // 商家名称
	private String sellerDescrption;  // 商家描述
	private String sellerTelephone;  // 联系电话
	private String sellerEmail; 	// 邮箱
	private String sellerType; 		// 商户类型:普通商户4497478100050001|跨境商户4497478100050002|跨境直邮4497478100050003|平台入驻4497478100050004
	private Integer status;		// 商户状态 0 未开通 1 已开通 2 已禁用
	private Integer priceType;  // 价格类型 0 成本价 1 销售价
	private String commission;  //  佣金比例 json存储
	private Integer type;   // 1：惠家有的商户；2：分销平台
	private Integer flag;   // 是否需要惠家有为其报关 1是 2否
    private String sellerCustomNumber;  // 商户的【商户海关备案编号】
    private String sellerCustomLocation;   // 商户报关地点|支付网关柄春做了2次封装后的对应码
    private List<CacheWcOpenapi> apis; // 商户所属API信息集合
    private String settlement;  // 结算方式  常规结算4497471600110001| 特殊结算4497471600110002|服务费结算 4497471600110003
    private String purchase; // 采购类型 代销 4497471600160001|经销4497471600160002|代收代付4497471600160003
    
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public List<CacheWcOpenapi> getApis() {
		return apis;
	}
	public void setApis(List<CacheWcOpenapi> apis) {
		this.apis = apis;
	}
	public String getSellerType() {
		return sellerType;
	}
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
	public String getSettlement() {
		return settlement;
	}
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	public String getPurchase() {
		return purchase;
	}
	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}
}




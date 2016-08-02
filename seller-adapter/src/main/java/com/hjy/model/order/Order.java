package com.hjy.model.order;

import java.math.BigDecimal;
import java.util.List;

import com.hjy.annotation.ZapcomApi;
import com.hjy.entity.order.OcOrderActivity;
import com.hjy.entity.order.OcOrderPay;
import com.hjy.entity.order.OcOrderShipments;
import com.hjy.entity.user.UcSellerinfo;


/**   
*    
* 项目名称：ordercenter   
* 类名称：Order   
* 类描述：   
* 创建人：yanzj  
* 创建时间：2013-9-2 上午11:02:57   
* 修改人：yanzj
* 修改时间：2013-9-2 上午11:02:57   
* 修改备注：   
* @version    
*    
*/
public class Order {
	
	
	/**
	 * 订单uid(创建接口勿传,此值会被覆盖，请勿传)
	 */
	@ZapcomApi(value="订单uid",remark="创建接口勿传,此值会被覆盖，请勿传")
	private String uid = "";
	

	/**
	 * 订单编号(创建接口勿传,此值会被覆盖，请勿传)
	 */
	@ZapcomApi(value="订单编号",remark="创建接口勿传,此值会被覆盖，请勿传")
	private String orderCode = "";
	
	
	/**
	 * 订单来源       值				说明
	 *          449715190001	正常订单
	 *          449715190002	android订单
	 *          449715190003	ios订单
	 *          449715190004	网站手机订单
	 */
	@ZapcomApi(value="订单来源值说明")
	private String orderSource = "";
	
	/**
	 * 订单类型	值				说明
	 * 			449715200001	商城订单
	 * 			449715200002	好物产订单
	 * 			449715200003	试用订单
	 * 			449715200004	闪购订单
	 * 			449715200005	普通订单
	 */
	@ZapcomApi(value="订单类型值说明")
	private String orderType = "";
	
	/**
	 * 
	 * 订单状态 值  说明 
	 * 	4497153900010001	下单成功-未付款
	 * 	4497153900010002	下单成功-未发货
	 * 	4497153900010003	已发货
	 * 	4497153900010004	已收货
	 * 	4497153900010005	交易成功
	 * 	4497153900010006	交易失败

	 */
	@ZapcomApi(value="订单状态 值说明")
	private String orderStatus = "";
	
	/**
	 * 商家编码
	 */
	@ZapcomApi(value="商家编码")
	private String sellerCode = "";
	
	/**
	 * 买家编号
	 */
	@ZapcomApi(value="买家编号")
	private String buyerCode="";
	
	
	/**
	 * 支付方式 值  说明
	 *449716200001	在线支付
	 *449716200002	货到付款
	 */
	@ZapcomApi(value="支付方式 值  说明")
	private String payType = "";
	
	/**
	 * 配送方式 值  说明
	 * 449715210001	快递
	 * 449715210002	邮局
	 */
	@ZapcomApi(value="配送方式 值  说明")
	private String sendType = "";
	
	
	/**
	 * 商品金额
	 */
	@ZapcomApi(value="商品金额")
	private BigDecimal productMoney = new BigDecimal(0.00);
	
	
	/**
	 * 商品运费(实际运费)
	 */
	@ZapcomApi(value="商品运费",remark="实际运费")
	private BigDecimal transportMoney =new BigDecimal(0.00);
	
	
	/**
	 * 商品活动金额
	 */
	@ZapcomApi(value="商品活动金额")
	private BigDecimal promotionMoney = new BigDecimal(0.00);
	
	
	/**
	 * 订单金额=商品金额+商品运费-商品活动金额-
	 */
	@ZapcomApi(value="订单金额")
	private BigDecimal orderMoney = new BigDecimal(0.00);
	
	/**
	 * 已支付金额
	 */
	@ZapcomApi(value="已支付金额")
	private BigDecimal payedMoney = new BigDecimal(0.00);
	
	
	/**
	 * 免运费金额（原始运费）
	 */
	@ZapcomApi(value="免运费金额",remark="原始运费")
	private BigDecimal freeTransportMoney = new BigDecimal(0.00);
	
	
	/**
	 *  应付款
	 */
	@ZapcomApi(value="应付款")
	private BigDecimal dueMoney = new BigDecimal(0.00); 
	
	
	
	/**
	 * 创建时间 -接口勿传，此值会被覆盖
	 */
	@ZapcomApi(value="创建时间",remark="接口勿传，此值会被覆盖")
	private String createTime = "";
	
	
	
	/**
	 * 更新时间
	 */
	@ZapcomApi(value="更新时间")
	private String updateTime = "";
	
	/**
	 *  小卖家编号
	 */
	@ZapcomApi(value="小卖家编号")
	private String smallSellerCode = ""; 
	
	
	/**
	 * 订单商品明细
	 */
	@ZapcomApi(value="订单商品明细")
	private List<OrderDetail> productList = null;
	
	
	/**
	 * 活动列表
	 */
	@ZapcomApi(value="活动列表")
	private List<OcOrderActivity> activityList = null; 
	
	
	/**
	 * 支付列表的List
	 */
	@ZapcomApi(value="支付列表的List")
	private List<OcOrderPay> ocOrderPayList = null;
	
	
	
	/**
	 * 订单地址
	 */
	@ZapcomApi(value="订单地址")
	private OrderAddress address = null;
	
	
	/**
	 * 卖家信息
	 */
	@ZapcomApi(value="卖家信息")
	private UcSellerinfo sellerInfo = null;
	
	
	/**
	 * 运单信息
	 */
	@ZapcomApi(value="运单信息")
	private OcOrderShipments ocorderShipments = null;
	
	/**
	 * 订单来源平台
	 */
	@ZapcomApi(value="订单来源平台")
	private String orderChannel="";
	
	
	
	/**
	 * 订单的快递信息
	 */
	@ZapcomApi(value="订单的快递信息")
	private List<Express> expressList = null;
	
	
	/**
	 * 前端订单流水
	 */
	@ZapcomApi(value="前端订单流水")
	private String orderPhpcode = "";
	
	@ZapcomApi(value="APP 版本",require=0)
	private String appVersion = "";
	
	
	@ZapcomApi(value="订单序列号")
	private String orderSeq = "";

	/**
    * 本订单所用积分
    */
	@ZapcomApi(value="本订单所用积分")
    private float virtualMoneyDeduction=0;
	
	private String lowOrder = ""; 
	
	public float getVirtualMoneyDeduction() {
		return virtualMoneyDeduction;
	}

	public void setVirtualMoneyDeduction(float virtualMoneyDeduction) {
		this.virtualMoneyDeduction = virtualMoneyDeduction;
	}
	
	/**
    * 拆单前所用积分
    */
	@ZapcomApi(value="拆单前所用积分")
	private float allVirtualMoneyDeduction=0;
	
	
	public float getAllVirtualMoneyDeduction() {
		return allVirtualMoneyDeduction;
	}

	public void setAllVirtualMoneyDeduction(float allVirtualMoneyDeduction) {
		this.allVirtualMoneyDeduction = allVirtualMoneyDeduction;
	}
	

	public String getOrderPhpcode() {
		return orderPhpcode;
	}

	public void setOrderPhpcode(String orderPhpcode) {
		this.orderPhpcode = orderPhpcode;
	}

	public List<Express> getExpressList() {
		return expressList;
	}

	public void setExpressList(List<Express> expressList) {
		this.expressList = expressList;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public OcOrderShipments getOcorderShipments() {
		return ocorderShipments;
	}

	public void setOcorderShipments(OcOrderShipments ocorderShipments) {
		this.ocorderShipments = ocorderShipments;
	}

	public UcSellerinfo getSellerInfo() {
		return sellerInfo;
	}

	public void setSellerInfo(UcSellerinfo sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<OcOrderPay> getOcOrderPayList() {
		return ocOrderPayList;
	}

	public void setOcOrderPayList(List<OcOrderPay> ocOrderPayList) {
		this.ocOrderPayList = ocOrderPayList;
	}

	public List<OcOrderActivity> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<OcOrderActivity> activityList) {
		this.activityList = activityList;
	}

	public List<OrderDetail> getProductList() {
		return productList;
	}

	public void setProductList(List<OrderDetail> productList) {
		this.productList = productList;
	}

	public OrderAddress getAddress() {
		return address;
	}

	public void setAddress(OrderAddress address) {
		this.address = address;
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
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

	public String getLowOrder() {
		return lowOrder;
	}

	public void setLowOrder(String lowOrder) {
		this.lowOrder = lowOrder;
	}

	
}

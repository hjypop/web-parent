package com.hjy.dto.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hjy.annotation.ExculdeNullField;
import com.hjy.request.data.OrderProductInfo;

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
	
	private Double check_pay_money;  // 应付款
	private String buyer_name; // 收货人姓名
	private String buyer_mobile; // 收货人手机号
	private String buyer_address; // 收货人地址
	private String remark; // 订单备注
	private String pay_type; // 支付方式 449716200001:在线支付,449716200002:货到付款
	private String app_vision; // app 版本信息 
	/**
	 * 订单来源,可选值:
	 * 	449715190001(正常订单)，
	 * 	449715190002(android订单),
	 * 	449715190003(ios订单),
	 * 	449715190004(网站wap手机订单),
	 * 	微信订单(449715190006),
	 * 	449715190007(扫码购订单),
	 * 	449715190008(客服订单),
	 * 	449715190012(百度外卖订单)
	 */
	private String order_souce; // 订单来源     此处传递为 [449715190001+第三方平台编号] 
	private String order_type; // 订单类型 449715200005 普通订单
	
	private List<OrderProductInfo> goods;  // 商品列表	不可为空
	private BillInfo billInfo;  // 发票信息
	
	
	public Double getCheck_pay_money() {
		return check_pay_money;
	}
	public void setCheck_pay_money(Double check_pay_money) {
		this.check_pay_money = check_pay_money;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBuyer_mobile() {
		return buyer_mobile;
	}
	public void setBuyer_mobile(String buyer_mobile) {
		this.buyer_mobile = buyer_mobile;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getBuyer_address() {
		return buyer_address;
	}
	public void setBuyer_address(String buyer_address) {
		this.buyer_address = buyer_address;
	}
	public String getApp_vision() {
		return app_vision;
	}
	public void setApp_vision(String app_vision) {
		this.app_vision = app_vision;
	}
	public String getBuyer_name() {
		return buyer_name;
	}
	public void setBuyer_name(String buyer_name) {
		this.buyer_name = buyer_name;
	}
	public String getOrder_souce() {
		return order_souce;
	}
	public void setOrder_souce(String order_souce) {
		this.order_souce = order_souce;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public List<OrderProductInfo> getGoods() {
		return goods;
	}
	public void setGoods(List<OrderProductInfo> goods) {
		this.goods = goods;
	}
	public BillInfo getBillInfo() {
		return billInfo;
	}
	public void setBillInfo(BillInfo billInfo) {
		this.billInfo = billInfo;
	}
}































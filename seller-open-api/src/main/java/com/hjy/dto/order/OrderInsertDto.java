package com.hjy.dto.order;

import java.util.List;

public class OrderInsertDto {
	// 应付款
	private Double check_pay_money;
	// 商品列表
	private List<GoodsInfoForAdd> goods;
	// 收货人地址编号 | 收货人地址所在地区选择的第三级编号
	private String buyer_address_code;
	// 买家编号	可为空，默认取当前登录人的编号|
	// TODO 这里是否设置为该商户的code?
	private String buyer_code;
	// 收货人手机号
	private String buyer_mobile;
	// 支付方式：默认在线支付|449746280003:支付宝支付,449746280005:微信支付
	private String pay_type = "449716200001";
	// 收货人地址
	private String buyer_address;
	// 发票信息
	private BillInfo billInfo;
	// app版本信息
	private String app_vision = "1.0.0	";
	// 收货人姓名
	private String buyer_name;
	// 订单类型 默认：449715200005普通订单
	private String order_type;
}










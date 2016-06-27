package com.hjy.jms;

public enum JmsNameEnumer {

	/**
	 * 商品修改时
	 */
	OnProductChange,
	
	/**
	 * 同步家有用户等级，积分
	 */
	OnUserChange,
	/**
	 * 商品状态修改时
	 */
	OnProductStatusChange,
	/**
	 * 订单创建生成时，监控订单
	 */
    OnProductMonitor,
    /**
	 * 微信商城修改密码
	 */
	OnChangePwdWX,
	/**
	 * 微信商城注册
	 */
    OnRegisterWX,
    /**
	 * 系统发放优惠券
	 */
    OnDistributeCoupon,
    /**
     * 搜索关键词
     */
    OnSearchKeyWord,
    /**
     * 微公社发送消息
     */
    OnGroupSendIM,
    /**
     * 商品修改5.2.1
     */
    OnProductUpdate
}

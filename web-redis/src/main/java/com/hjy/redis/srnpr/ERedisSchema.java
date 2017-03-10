package com.hjy.redis.srnpr;

/**
 * @descriptions 
 * @alias EKvSchema
 * 
 * @date 2016年8月1日上午10:53:24
 * @author Yangcl
 * @version 1.0.1
 */
public enum ERedisSchema {
	/*
	 * 商品库存信息 该库存信息存放各种SKU或者活动明细的库存数量
	 */
	Stock,

	/**
	 * 商品价格
	 */
	Price,

	/**
	 * SKU的信息 该SKU信息中包含着实时的价格等可购买信息
	 */
	Sku,

	/**
	 * 活动
	 */
	Event,

	/**
	 * 根据活动明细编号缓存的SKU信息
	 */
	IcSku,

	/**
	 * 活动明细的基本信息
	 */
	Item,

	/**
	 * 活动明细上同一商品编号关联出的明细，逗号分隔，主要用于同一活动下多个SKU的信息的分解
	 */
	Iconst,

	/**
	 * 库存变为0的时间
	 */
	EmptyStock,

	/**
	 * 活动明细的日志，用于核算活动库存限制
	 */
	LogItem,

	/**
	 * 订单占用活动库存日志，用户取消订单时返回促销库存
	 */
	LogOrderStock,

	/**
	 * 订单
	 */
	Order,

	/**
	 * 在线支付的方式
	 */
	PayFrom,

	/**
	 * 锁定库存 一般是根据SKU的活动来锁定的库存
	 */
	LockStock,

	/**
	 * 活动锁定库存
	 */
	EventLock,

	/**
	 * 预计订单取消时间
	 */
	TimeCancelOrder,

	/**
	 * 记录活动下所有进入过缓存系统的子编号
	 */
	EventChildren,

	/*
	 * 记录下商品下关联的IC编号信息 以备清除缓存用
	 */
	ProductIcChildren,

	/**
	 * 特殊子活动下的子活动类型对应的活动的编号
	 */
	SubEventCode,

	/**
	 * 明细编号
	 */
	SubIcCode,

	/**
	 * 二维码链接
	 */
	Qrcode,

	/**
	 * 密码校验
	 */
	PasswordCheck,
	/**
	 * 扫码购允许商品
	 */
	ScannerAllow,
	/**
	 * 用于新用户首次下单上级返券总金额
	 */
	SuperiorTotalCoupon,

	/*
	 * SKU分库库存数据
	 */
	SkuStoreStock,

	/**
	 * 基本哈希数据
	 */
	DefineHashKey,

	/**
	 * 图片缩放
	 */
	ImageZoom,

	/**
	 * 创建订单
	 */
	CreateOrder,
	/**
	 * sku扩展信息
	 */
	SkuInfoSpread,

	/**
	 * 订单地址
	 */
	Address,

	/**
	 * 赠品信息
	 */
	Gift,

	/**
	 * 商品扩展
	 */
	Product,

	/**
	 * 运费模板
	 */
	Freight,
	/*
	 * 关联SKU编号，用于复制商品之类的判断和校验 存放的是sku关联的复制或者被复制的sku的编号 双向关联关系
	 */
	ConcatSku,

	/**
	 * 商品下面的所有的SKU编号，用逗号分隔
	 */
	ProductSku,

	/**
	 * 全场类活动
	 */
	EventSale,

	/**
	 * 购物车信息
	 */
	ShopCart,
	/**
	 * 第三级行政编号
	 */
	AreaCode,
	/**
	 * 区域模板对应的城市编号
	 */
	templateAreaCode,
	/**
	 * 用户相关信息
	 */
	Member,
	/**
	 * 黑名单
	 */
	Black,
	/**
	 * 运费减免
	 */
	FreeShipping,
	/**
	 * 商品近30天销量
	 */
	ProductSales,
	/**
	 * 商品标签
	 */
	ProductLabels,
	/**
	 * 拼好货
	 */
	GoodsProduct,
	/**
	 * 内购
	 */
	EvenetPurchase,

	/**
	 * 订单活动信息
	 */
	ActivityInfo,
	/***
	 * 是否内购
	 */
	UserMemberCode,
	/**
	 * 拼好货
	 */
	PinHaoHuo,
	/**
	 * 惠家有后台冻结用户累计触发值
	 */
	FreezeOperator,

	/**
	 * 商户信息
	 */
	Seller,
	
	// 权威标识
	// get xs-ProductAuthorityLogo-ProductLog 获取所有非通路权威标识
	// get xs-ProductAuthorityLogo-SI2003 获取所有通路权威标识
	// get xs-ProductAuthorityLogo-8016410750 获取某一个商品的非通路权威标识
	ProductAuthorityLogo
}













package com.hjy.dto.product;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: open-api 惠家有商户商品的数据封装体
 * 
 * TODO 待删除：RequestProducts  &  com.hjy.dto.product.ProductInfo
 * 
 * @author Yangcl
 * @date 2016年12月29日 下午6:03:24 
 * @version 1.0.0
 */
public class ApiSellerProduct {
	private Integer operate;    // 操作类型 0为添加，1为编辑
	private String sellerCode;    // 卖家编号
	private String productOutCode;    // 外部商品编号|商户自己的商品编码
	private String productCode;    // 惠家有商品编号
	private String productName;    // 商品名称
	private String productShortname;    // 商品简称
	private BigDecimal productWeight;    // 商品重量
	private BigDecimal costPrice;    // 成本价
	private BigDecimal marketPrice;    // 市场价
	private String mainPicUrl;    // 主图的Url
	private String productVolumeItem;    // 长 宽 高 ，用逗号隔开
	private Productdescription description;    // 商品描述信息
	private List<String> pcPicList;    // 商品图片信息
	private BigDecimal productVolume;    // 商品体积
	private int expiryDate;    // 保质期
	private String expiryUnit;    // 保质期单位 4497471600290001:天，4497471600290002:月,4497471600290003:年
	private List<ApiSellerSkuInfo> skuInfoList;    // 商品的Sku列表的属性信息

	

	 
	
	
}
























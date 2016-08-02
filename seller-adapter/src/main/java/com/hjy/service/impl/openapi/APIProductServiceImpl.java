package com.hjy.service.impl.openapi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjy.common.DateUtil;
import com.hjy.dao.product.IPcBrandinfoDao;
import com.hjy.dao.product.IPcCategoryinfoDao;
import com.hjy.dao.product.IPcProductdescriptionDao;
import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.dao.product.IPcProductinfoExtDao;
import com.hjy.dao.product.IPcProductpicDao;
import com.hjy.dao.product.IPcProductpropertyDao;
import com.hjy.dao.product.IPcSkuinfoDao;
import com.hjy.dao.system.IScStoreSkunumDao;
import com.hjy.dao.user.IUcSellerinfoDao;
import com.hjy.entity.member.McAuthenticationInfo;
import com.hjy.entity.product.PcBrandinfo;
import com.hjy.entity.product.PcCategoryinfo;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.entity.user.UcSellerinfo;
import com.hjy.helper.JsonHelper;
import com.hjy.helper.WebHelper;
import com.hjy.model.ProductSkuInfo;
import com.hjy.model.openapi.APIResult;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.openapi.IAPIProductService;

/**
 * 
 * 类: APIProductServiceImpl <br>
 * 描述: 商品相关接口实现类 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月1日 下午3:48:46
 */
@Service
public class APIProductServiceImpl extends BaseServiceImpl<McAuthenticationInfo, Integer>
		implements IAPIProductService {

	public static String ProductHead = "8016";
	public static String SKUHead = "8019";
	public static String ProductFlowHead = "PF";
	public static String ColorHead = "449746200001";
	public static String MainHead = "449746200002";
	public static String NormalHead = "449746200003";

	@Autowired
	private IPcProductinfoDao productInfo;
	@Autowired
	private IPcProductinfoExtDao productInfoExt;
	@Autowired
	private IPcSkuinfoDao skuInfo;
	@Autowired
	private IScStoreSkunumDao storeSkunum;
	@Autowired
	private IPcCategoryinfoDao categoryinfo;
	@Autowired
	private IPcBrandinfoDao brandinfo;
	@Autowired
	private IPcProductpropertyDao productProperty;
	@Autowired
	private IPcProductdescriptionDao productDescription;
	@Autowired
	private IPcProductpicDao productPic;
	@Autowired
	private IUcSellerinfoDao sellerInfo;

	@Override
	public APIResult addProduct(String json) {
		APIResult result = new APIResult();
		try {
			if (json != null && !"".equals(json)) {
				PcProductinfo product = new PcProductinfo();
				JsonHelper<PcProductinfo> helper = new JsonHelper<PcProductinfo>();
				product = helper.GsonFromJson(json, product);
				result = verifyProduct(product);
				// 验证信息成功
				if (result.getCode() == 0) {
					// 获取整理的商品信息
					product = trimProduct(product);
					/**
					 * 执行添加操作
					 */
					// 1.添加商品信息表pc_productinfo
					productInfo.insertSelective(product);
					productInfoExt.insertSelective(product.getPcProductinfoExt());
					// 2.添加商品属性表pc_productproperty
					productProperty.batchInsert(product.getPcProductpropertyList());
					// 3.添加商品扩展信息pc_productinfo_ext
					if (product.getPcProductinfoExt() != null) {
						productInfoExt.insertSelective(product.getPcProductinfoExt());
					}
					// 4.添加商品描述信息pc_productdescription
					if (product.getDescription() != null) {
						productDescription.insertSelective(product.getDescription());
					}
					// 5.添加商品图片信息pc_productpic
					productPic.batchInsert(product.getPcPicList());
					// 6.添加产品表pc_skuinfo
					skuInfo.batchInsert(product.getPcSkuInfoList());
					// 7.添加产品库存sc_store_skunum
					storeSkunum.batchInsert(product.getSkunumList());

				}
			} else {
				result.setCode(1);
				result.setMessage("无效参数");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * 方法: verifyProduct <br>
	 * 描述: 验证商品信息是否正确 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月1日 下午4:46:06
	 * 
	 * @param product
	 * @return
	 */
	public APIResult verifyProduct(PcProductinfo product) {
		APIResult result = new APIResult();
		if (product == null) {
			result.setCode(1);
			result.setMessage("获取商品信息失败!");
			return result;
		}
		if (StringUtils.isBlank(product.getProductName())) {
			result.setCode(1);
			result.setMessage("商品名称不能为空");
			return result;
		}
		if (StringUtils.isBlank(product.getBrandCode())) {
			result.setCode(1);
			result.setMessage("商品品牌编号不能为空");
			return result;
		} else {
			// 验证商品编号是否存在
			PcBrandinfo param = new PcBrandinfo();
			param.setBrandCode(product.getBrandCode());
			PcBrandinfo brand = brandinfo.findByType(param);
			if (brand == null) {
				result.setCode(1);
				result.setMessage("商品品牌编号不存在");
			}
		}
		if (product.getProductSkuInfoList() == null || product.getProductSkuInfoList().size() == 0) {
			result.setCode(1);
			result.setMessage("商品sku不能为空");
			return result;
		}
		for (ProductSkuInfo sku : product.getProductSkuInfoList()) {
			if (sku.getStockNum() < 0) {
				result.setCode(1);
				result.setMessage("商品sku库存不能小于0");
				return result;
			}
			if (sku.getSellPrice().doubleValue() < 0) {
				result.setCode(1);
				result.setMessage("商品售价不能小于0");
				return result;
			}
			// 商品的市场价格必须不小于商品sku的销售价格.
			if (sku.getSellPrice().compareTo(product.getMarketPrice()) > 0) {
				result.setCode(1);
				result.setMessage("商品的市场价格必须不小于商品sku的销售价格.");
				return result;
			}
			// 商品的sku成本价小于销售价
			if (sku.getSellPrice().compareTo(sku.getCostPrice()) <= 0) {
				result.setCode(1);
				result.setMessage("商品的sku成本价小于销售价");
				return result;
			}
		}
		// 校验商品名字的外链合法性 暂时不考虑
		return result;
	}

	public PcProductinfo trimProduct(PcProductinfo product) {
		PcProductinfo info = product;
		info.setUid(WebHelper.getInstance().genUuid());
		info.setCreateTime(DateUtil.getSysDateTimeString());
		info.setUpdateTime(DateUtil.getSysDateTimeString());
		// 获取prodcutcode值
		String productCode = WebHelper.getInstance().genUniqueCode(ProductHead);
		info.setProductCode(productCode);
		if (null != product.getDescription() && null != product.getDescription().getKeyword()
				&& !"".equals(product.getDescription().getKeyword())) {
			String[] labelsArr = product.getDescription().getKeyword().trim().split(" ");
			String labelStr = ""; // 将空格替换掉
			for (int i = 0; i < labelsArr.length; i++) {
				if ("".endsWith(labelsArr[i])) {
					continue;
				}
				labelStr += labelsArr[i] + ",";
			}
			product.getDescription().setKeyword(labelStr.substring(0, labelStr.length() - 1)); // 截去最后一个逗号并把商品标签添加到商品描述表中
		}
		/**
		 * 根据品牌编号获取品牌名称
		 */
		product.setBrandName("品牌名称");

		/**
		 * 商品实类
		 */
		if (null != product.getPcProductcategoryRel()) {
			product.getPcProductcategoryRel().setProductCode(product.getProductCode());
			product.getPcProductcategoryRel().setFlagMain(Long.parseLong("1"));
		}
		/**
		 * 商品扩展信息
		 */
		if (null != product.getPcProductinfoExt()) {
			product.getPcProductinfoExt().setProductCode(product.getProductCode());
		}
		/**
		 * 整理sku信息
		 */
		List<ScStoreSkunum> scStoreSkunumList = new ArrayList<ScStoreSkunum>();
		for (ProductSkuInfo sku : product.getProductSkuInfoList()) {
			sku.setProductCode(product.getProductCode());
			sku.setSkuCode(WebHelper.getInstance().genUniqueCode(SKUHead));
			sku.setSellerCode(product.getSellerCode());
			// 如果当前商品的sku图片不存在，则 设置sku图片。
			if (sku.getSkuPicUrl() == null || sku.getSkuPicUrl().equals("")) {
				sku.setSkuPicUrl(product.getMainPicUrl());
			}
			if (sku.getSkuPicUrl() == null) {
				sku.setSkuPicUrl("");
			}
			if (sku.getSkuKey() == null) {
				sku.setSkuKey("");
			}
			if (sku.getSkuValue() == null) {
				sku.setSkuValue("");
			}
			if (sku.getSellProductcode() == null) {
				sku.setSellProductcode("");
			}
			if (sku.getSkuName() == null) {
				sku.setSkuName("");
			}
			/**
			 * sku商品库存
			 */
			ScStoreSkunum store = new ScStoreSkunum();
			store.setBatchCode("");
			store.setSkuCode(sku.getSkuCode());
			store.setStockNum(Long.parseLong(sku.getStockNum() + ""));
			// 第三方虚拟库，待讨论
			store.setStoreCode("TDS1");
			scStoreSkunumList.add(store);
		}
		/**
		 * 设置商品的最高售价和最低售价
		 */
		if (product.getProductSkuInfoList() != null) {
			int size = product.getProductSkuInfoList().size();
			BigDecimal tempMin = new BigDecimal(0.00);
			BigDecimal tempMax = new BigDecimal(0.00);
			for (int i = 0; i < size; i++) {
				ProductSkuInfo pic = product.getProductSkuInfoList().get(i);

				if (i == 0) {
					tempMin = pic.getSellPrice();
					tempMax = pic.getSellPrice();
					product.setMarketPrice(pic.getMarketPrice());
				} else {
					if (tempMin.compareTo(pic.getSellPrice()) == 1)
						tempMin = pic.getSellPrice();
					if (tempMax.compareTo(pic.getSellPrice()) == -1)
						tempMax = pic.getSellPrice();
				}
			}
			product.setMinSellPrice(tempMin);
			product.setMaxSellPrice(tempMax);
		}
		// 如果商品主图为空并且轮播图列表不为空时，商品主图设为轮播图第一张
		if ((null == product.getMainPicUrl() || "".equals(product.getMainPicUrl())) && product.getPcPicList() != null
				&& product.getPcPicList().size() > 0) {
			product.setMainPicUrl(product.getPcPicList().get(0).getPicUrl());
		}
		/*
		 * 查询供应商名称
		 */
		UcSellerinfo seller = sellerInfo.selectBySmallSellerCode(product.getSmallSellerCode());
		product.getPcProductinfoExt().setDlrId(product.getSmallSellerCode());
		product.getPcProductinfoExt().setDlrNm(seller.getSellerName());
		/*
		 * 添加审批流程，讨论结果：不添加
		 */
		return info;
	}
}

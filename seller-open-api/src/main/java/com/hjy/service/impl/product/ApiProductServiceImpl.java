package com.hjy.service.impl.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hjy.common.DateUtil;
import com.hjy.dao.product.IPcProductdescriptionDao;
import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.dao.product.IPcProductpicDao;
import com.hjy.dao.product.IPcSkuinfoDao;
import com.hjy.dao.system.IScStoreSkunumDao;
import com.hjy.dto.product.PcSkuInfo;
import com.hjy.dto.product.ProductInfo;
import com.hjy.entity.product.PcProductdescription;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcProductpic;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.helper.WebHelper;
import com.hjy.request.RequestProduct;
import com.hjy.response.ResponseAddProduct;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.product.IApiProductService;

@Service
public class ApiProductServiceImpl extends BaseServiceImpl<PcProductinfo, Integer> implements IApiProductService {
	public static String ProductHead = "8016";
	public static String SKUHead = "8019";
	@Autowired
	private IPcProductinfoDao productInfoDao;
	@Autowired
	private IPcProductdescriptionDao productdescription;
	@Autowired
	private IPcProductpicDao pcProductpic;
	@Autowired
	private IPcSkuinfoDao skuInfoDao;
	@Autowired
	private IScStoreSkunumDao scStoreSkunumDao;

	@Override
	public ResponseAddProduct addProduct(String product) {
		RequestProduct requestProduct = JSON.toJavaObject(JSON.parseObject(product), RequestProduct.class);
		if (requestProduct != null) {
			if (requestProduct.getProductInfos() != null && requestProduct.getProductInfos().size() > 0) {
				ResponseAddProduct result = verifyProduct(requestProduct.getProductInfos());
				if (result.getCode() == 0) {
					try {
						//WebHelper.getInstance().addLock(10, "openapi-test-addproduct");
						String productCode = "80169999999999";//WebHelper.getInstance().genUniqueCode(ProductHead);
						String sellerCode = "";
						/**
						 * 批量添加商品
						 */
						List<PcProductinfo> productInfoList = getPcProdcutInfoList(requestProduct.getProductInfos(),
								productCode, sellerCode);
						productInfoDao.batchInsert(productInfoList);
						/**
						 * 批量添加商品描述
						 */
						List<PcProductdescription> pcProductdescriptionList = getPcProductdescriptionList(
								requestProduct.getProductInfos(), productCode);
						productdescription.batchInsert(pcProductdescriptionList);
						/**
						 * 批量添加商品图片
						 */
						List<PcProductpic> productpicList = getPcProductpicList(requestProduct.getProductInfos(),
								productCode);
						pcProductpic.batchInsert(productpicList);
						/**
						 * 批量添加sku
						 */
						List<PcSkuinfo> skuInfoList = getPcSkuInfoList(requestProduct.getProductInfos(), productCode,
								sellerCode);
						skuInfoDao.batchInsert(skuInfoList);
						/**
						 * 批量添加库存信息
						 */
						List<ScStoreSkunum> storeList = getScStoreSkunumList(skuInfoList);
						scStoreSkunumDao.batchInsert(storeList);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {

					}
				}
			}
		}
		return null;
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
	public ResponseAddProduct verifyProduct(List<ProductInfo> productList) {
		ResponseAddProduct response = new ResponseAddProduct();
		for (int i = 0; i < productList.size(); i++) {
			ProductInfo product = productList.get(i);
			if (product == null) {
				response.setCode(1);
				response.setDesc("获取商品信息失败!");
				break;
			}
			if (StringUtils.isBlank(product.getProductName())) {
				response.setCode(1);
				response.setDesc("商品名称不能为空");
				break;
			}
			if (StringUtils.isBlank(product.getBrandCode())) {
				response.setCode(1);
				response.setDesc("商品品牌编号不能为空");
				break;
			} else {
				// 验证商品编号是否存在
			}
			if (product.getSkuInfoList() == null || product.getSkuInfoList().size() == 0) {
				response.setCode(1);
				response.setDesc("商品sku不能为空");
				break;
			}
			for (PcSkuInfo sku : product.getSkuInfoList()) {
				if (sku.getStockNum() < 0) {
					response.setCode(1);
					response.setDesc("商品sku库存不能小于0");
					break;
				}
				if (sku.getSellPrice().doubleValue() < 0) {
					response.setCode(1);
					response.setDesc("商品售价不能小于0");
					break;
				}
			}
		}
		// 校验商品名字的外链合法性 暂时不考虑
		return response;
	}

	/**
	 * 
	 * 方法: getPcProdcutInfoList <br>
	 * 描述: 获取商品集合 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月2日 下午4:42:40
	 * 
	 * @param infos
	 * @param productCode
	 * @param sellerCode
	 * @return
	 */
	private static List<PcProductinfo> getPcProdcutInfoList(List<ProductInfo> infos, String productCode,
			String sellerCode) {
		List<PcProductinfo> list = new ArrayList<PcProductinfo>();
		if (infos != null && infos.size() > 0) {
			for (ProductInfo info : infos) {
				if (info != null) {
					PcProductinfo product = new PcProductinfo();
					product.setUid(WebHelper.getInstance().genUuid());
					product.setProductCode(productCode);
					product.setProductCodeOld(info.getProductCode());
					product.setProductCodeCopy(info.getProductCode());
					product.setProductName(info.getProductName());
					product.setProductShortname(info.getProductShortname());
					product.setSellerCode("SI2003");
					product.setSmallSellerCode(sellerCode);
					product.setProductWeight(info.getProductWeight());
					product.setCostPrice(info.getCostPrice());
					product.setMarketPrice(info.getMarketPrice());
					product.setMainPicUrl(info.getMainPicUrl());
					product.setLabels(info.getLabels());
					product.setProductVolumeItem(info.getProductVolumeItem());
					product.setProductVolume(info.getProductVolume());
					product.setProductAdv(info.getProductAdv());
					product.setAdPicUrl(info.getAdpicUrl());
					product.setExpiryDate(info.getExpiryDate());
					product.setExpiryUnit(info.getExpiryUnit());
					product.setCreateTime(DateUtil.getSysTimeString());
					product.setUpdateTime(DateUtil.getSysTimeString());
					/**
					 * 获取商品的最小销售价格和最大销售价格
					 */
					if (info.getSkuInfoList() != null && info.getSkuInfoList().size() > 0) {
						BigDecimal tempMin = new BigDecimal(0.00);
						BigDecimal tempMax = new BigDecimal(0.00);
						for (int i = 0; i < info.getSkuInfoList().size(); i++) {
							PcSkuInfo sku = info.getSkuInfoList().get(i);
							if (i == 0) {
								tempMin = sku.getSellPrice();
								tempMax = sku.getSellPrice();
							} else {
								if (tempMin.compareTo(sku.getSellPrice()) == 1)
									tempMin = sku.getSellPrice();
								if (tempMax.compareTo(sku.getSellPrice()) == -1)
									tempMax = sku.getSellPrice();
							}
						}
						product.setMinSellPrice(tempMin);
						product.setMaxSellPrice(tempMax);
					}
					list.add(product);
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * 方法: getPcProductdescriptionList <br>
	 * 描述: 获取商品描述信息集合 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月3日 上午7:26:49
	 * 
	 * @param infos
	 * @param productCode
	 * @return
	 */
	private static List<PcProductdescription> getPcProductdescriptionList(List<ProductInfo> infos, String productCode) {
		List<PcProductdescription> list = new ArrayList<PcProductdescription>();
		if (infos != null && infos.size() > 0) {
			for (ProductInfo product : infos) {
				if (product.getDescription() != null) {
					PcProductdescription description = new PcProductdescription();
					description.setUid(WebHelper.getInstance().genUuid());
					description.setDescriptionInfo(product.getDescription().getDescriptionInfo());
					description.setDescriptionPic(product.getDescription().getDescriptionPic());
					list.add(description);
				}
			}
		}
		return list;
	}

	private static List<PcProductpic> getPcProductpicList(List<ProductInfo> infos, String productCode) {
		List<PcProductpic> list = new ArrayList<PcProductpic>();
		if (infos != null && infos.size() > 0) {
			for (ProductInfo product : infos) {
				if (product.getPcPicList() != null && product.getPcPicList().size() > 0) {
					for (String pic : product.getPcPicList()) {
						PcProductpic ppic = new PcProductpic();
						ppic.setUid(WebHelper.getInstance().genUuid());
						ppic.setPicUrl(pic);
						ppic.setProductCode(productCode);
						list.add(ppic);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * 方法: getPcSkuInfoList <br>
	 * 描述: 获取sku集合 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月2日 下午4:45:12
	 * 
	 * @param infos
	 * @param productCode
	 * @param sellerCode
	 * @return
	 */
	private static List<PcSkuinfo> getPcSkuInfoList(List<ProductInfo> infos, String productCode, String sellerCode) {
		List<PcSkuinfo> list = new ArrayList<PcSkuinfo>();
		if (infos != null && infos.size() > 0) {
			for (ProductInfo product : infos) {
				if (product.getSkuInfoList() != null && product.getSkuInfoList().size() > 0) {
					for (PcSkuInfo info : product.getSkuInfoList()) {
						PcSkuinfo sku = new PcSkuinfo();
						sku.setUid(WebHelper.getInstance().genUuid());
						sku.setProductCode(productCode);
						//sku.setSkuCode(WebHelper.getInstance().genUniqueCode(SKUHead));
						sku.setSkuCode("80199999999999");;
						sku.setSellerCode(sellerCode);
						sku.setSellPrice(info.getSellPrice());
						sku.setStockNum(info.getStockNum());
						sku.setSkuPicurl(info.getSkuPicUrl());
						sku.setSkuName(info.getSkuName());
						sku.setSkuAdv(info.getSkuAdv());
						sku.setMiniOrder(info.getMiniOrder());
						list.add(sku);
					}
				}
			}
		}
		return list;
	}

	private static List<ScStoreSkunum> getScStoreSkunumList(List<PcSkuinfo> infos) {
		List<ScStoreSkunum> list = new ArrayList<ScStoreSkunum>();
		if (infos != null && infos.size() > 0) {
			for (PcSkuinfo sku : infos) {
				ScStoreSkunum store = new ScStoreSkunum();
				store.setUid(WebHelper.getInstance().genUuid());
				store.setSkuCode(sku.getSkuCode());
				store.setStockNum(sku.getStockNum());
				store.setStoreCode("");
				store.setBatchCode("");
			}
		}
		return list;
	}
}

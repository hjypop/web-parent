package com.hjy.service.impl.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hjy.common.DateUtil;
import com.hjy.dao.api.IApiProductInfoDao;
import com.hjy.dao.api.IApiSkuInfoDao;
import com.hjy.dao.product.IPcProductdescriptionDao;
import com.hjy.dao.product.IPcProductpicDao;
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
import com.hjy.request.RequestProducts;
import com.hjy.response.product.ResponseProduct;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.product.IApiProductService;

/**
 * 
 * 类: ApiProductServiceImpl <br>
 * 描述: openapi商品业务处理接口实现类 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月4日 上午9:20:07
 */
@Service
public class ApiProductServiceImpl extends BaseServiceImpl<PcProductinfo, Integer> implements IApiProductService {
	public static String ProductHead = "8016";
	public static String SKUHead = "8019";
	@Resource
	private IApiProductInfoDao productInfoDao;
	@Resource
	private IPcProductdescriptionDao productdescription;
	@Resource
	private IPcProductpicDao pcProductpic;
	@Resource
	private IApiSkuInfoDao skuInfoDao;
	@Resource
	private IScStoreSkunumDao scStoreSkunumDao;

	/**
	 * 
	 * 方法: addProduct <br>
	 * 描述: TODO
	 * 
	 * @param product
	 * @return
	 * @see com.hjy.service.product.IApiProductService#addProduct(java.lang.String)
	 */
	@Override
	public ResponseProduct addProduct(String product) {
		ResponseProduct response = new ResponseProduct();
		if (product != null && !"".equals(product)) {
			try {
				WebHelper.getInstance().addLock(10, "Product.addproduct");
				RequestProduct requestProduct = JSON.toJavaObject(JSON.parseObject(product), RequestProduct.class);
				if (requestProduct != null) {
					if (requestProduct.getProduct() != null) {
						List<ProductInfo> productList = new ArrayList<ProductInfo>();
						requestProduct.getProduct().setOperate(0);
						requestProduct.getProduct().setProductCode(WebHelper.getInstance().genUniqueCode(ProductHead));
						productList.add(requestProduct.getProduct());
						response = verifyProduct(productList);
						if (response.getCode() == 0) {
							// 执行添加商品方法
							if (addProduct(productList)) {
								response.setCode(0);
								response.setDesc(getInfo(0));
							} else {
								response.setCode(10);
								response.setDesc(getInfo(10));
							}
						} else {
							response.setCode(10);
							response.setDesc(getInfo(10));
						}
					} else {
						response.setCode(10);
						response.setDesc(getInfo(10));
					}
				} else {
					response.setCode(10);
					response.setDesc(getInfo(10));
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setCode(10);
				response.setDesc(getInfo(10));
			} finally {
				WebHelper.getInstance().unLock("Product.addproduct");
			}
		} else {
			response.setCode(10);
			response.setDesc(getInfo(10));
		}
		return response;
	}

	/**
	 * 
	 * 方法: editProduct <br>
	 * 描述: TODO
	 * 
	 * @param product
	 * @return
	 * @see com.hjy.service.product.IApiProductService#editProduct(java.lang.String)
	 */
	@Override
	public ResponseProduct editProduct(String product) {
		ResponseProduct response = new ResponseProduct();
		String lock = "";
		try {
			lock = WebHelper.getInstance().addLock(10, "Product.editproduct");
			if (lock != null && !"".equals(lock)) {
				if (product != null && !"".equals(product)) {
					RequestProduct requestProduct = JSON.toJavaObject(JSON.parseObject(product), RequestProduct.class);
					if (requestProduct != null) {
						if (requestProduct.getProduct() != null) {
							List<ProductInfo> productList = new ArrayList<ProductInfo>();
							requestProduct.getProduct().setOperate(1);
							productList.add(requestProduct.getProduct());
							for (ProductInfo productInfo : productList) {
								// 根据外部商品编号查询惠家有商品编号
								String productCode = productInfoDao
										.findProductCodeByOutCode(productInfo.getProductOutCode());
								productInfo.setProductCode(productCode);
							}
							// 执行编辑商品
							if (editProduct(productList)) {
								response.setCode(0);
								response.setDesc(getInfo(0));
							} else {
								response.setCode(10);
								response.setDesc(getInfo(10));
							}
						} else {
							response.setCode(10);
							response.setDesc(getInfo(10));
						}
					} else {
						response.setCode(10);
						response.setDesc(getInfo(10));
					}
				} else {
					response.setCode(10);
					response.setDesc(getInfo(10));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (lock != null && !"".equals(lock)) {
				WebHelper.getInstance().unLock(lock);
			}
		}
		return response;
	}

	/**
	 * 
	 * 方法: syncProductList <br>
	 * 描述: TODO
	 * 
	 * @param products
	 * @return
	 * @see com.hjy.service.product.IApiProductService#syncProductList(java.lang.String)
	 */
	@Override
	public ResponseProduct syncProductList(String products) {
		ResponseProduct response = new ResponseProduct();
		if (products != null && !"".equals(products)) {
			String lock = "";
			try {
				lock = WebHelper.getInstance().addLock(10, "Product.syncProductList");
				if (lock != null && !"".equals(lock)) {
					RequestProducts requestProduct = JSON.toJavaObject(JSON.parseObject(products),
							RequestProducts.class);
					if (requestProduct != null) {
						if (requestProduct.getProductInfos() != null && requestProduct.getProductInfos().size() > 0) {
							response = verifyProduct(requestProduct.getProductInfos());
							if (response.getCode() == 0) {
								for (ProductInfo info : requestProduct.getProductInfos()) {
									if (info.getOperate() == 1) {
										// 根据外部商品编号查询惠家有商品编号
										String productCode = productInfoDao
												.findProductCodeByOutCode(info.getProductOutCode());
										info.setProductCode(productCode);
									} else {
										info.setProductCode(WebHelper.getInstance().genUniqueCode(ProductHead));
									}
								}
								/**
								 * 遍历商品集合，根据操作类型区分是添加还是编辑，根据类型进行不同的操作
								 */
								List<ProductInfo> addList = new ArrayList<ProductInfo>();
								List<ProductInfo> editList = new ArrayList<ProductInfo>();
								for (int i = 0; i < requestProduct.getProductInfos().size(); i++) {
									ProductInfo info = requestProduct.getProductInfos().get(i);
									if (info.getOperate() == 0) {
										addList.add(info);
									} else if (info.getOperate() == 1) {
										editList.add(info);
									}
								}
								// 执行添加操作
								addProduct(addList);
								// 执行编辑操作
								editProduct(editList);
								// 返回处理结果
								response.setCode(0);
								response.setDesc(getInfo(0));
							}
						} else {
							response.setCode(10);
							response.setDesc(getInfo(10));
						}
					} else {
						response.setCode(10);
						response.setDesc(getInfo(10));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setCode(10);
				response.setDesc(getInfo(10));
			} finally {
				if (lock != null && !"".equals(lock)) {
					WebHelper.getInstance().unLock(lock);
				}
			}
		} else {
			response.setCode(10);
			response.setDesc(getInfo(10));
		}
		return response;
	}

	/**
	 * 
	 * 方法: syncProductPrice <br>
	 * 描述: TODO
	 * 
	 * @param products
	 *            要修改价格的product列表
	 * @return
	 * @see com.hjy.service.product.IApiProductService#syncProductPrice(java.lang.String)
	 */
	@Override
	public ResponseProduct syncProductPrice(String products) {
		ResponseProduct response = new ResponseProduct();
		String lock = "";
		try {
			lock = WebHelper.getInstance().addLock(10, "Product.syncProductPrice");
			if (lock != null && !"".equals(lock)) {
				if (products != null && !"".equals(products)) {
					RequestProducts requestProducts = JSON.toJavaObject(JSON.parseObject(products),
							RequestProducts.class);
					if (requestProducts != null) {
						List<ProductInfo> productList = requestProducts.getProductInfos();
						if (productList != null && productList.size() > 0) {
							String sellerCode = "";
							for (ProductInfo info : productList) {
								// 根据外部商品编号查询惠家有商品编号
								String productCode = productInfoDao.findProductCodeByOutCode(info.getProductOutCode());
								info.setProductCode(productCode);
							}
							/**
							 * 编辑商品的最小售价和最大售价
							 */
							List<PcProductinfo> productInfos = getPcProdcutInfoList(productList, sellerCode);
							if (productInfos != null && productInfos.size() > 0) {
								for (PcProductinfo pcProductinfo : productInfos) {
									productInfoDao.updateProductPrice(pcProductinfo);
								}
							}
							/**
							 * 编辑sku的售价
							 */
							List<PcSkuinfo> skuInfos = getPcSkuInfoList(productList, sellerCode);
							if (skuInfos != null && skuInfos.size() > 0) {
								for (PcSkuinfo pcSkuinfo : skuInfos) {
									skuInfoDao.updateSkuPrice(pcSkuinfo);
								}
							}
							response.setCode(0);
							response.setDesc(getInfo(0));
						} else {
							response.setCode(10);
							response.setDesc(getInfo(10));
						}
					} else {
						response.setCode(10);
						response.setDesc(getInfo(10));
					}
				} else {
					response.setCode(10);
					response.setDesc(getInfo(10));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(10);
			response.setDesc(getInfo(10));
		} finally {
			if (lock != null && !"".equals(lock)) {
				WebHelper.getInstance().unLock(lock);
			}
		}
		return response;
	}

	/**
	 * 
	 * 方法: syncSkuStore <br>
	 * 描述: TODO
	 * 
	 * @param products
	 * @return
	 * @see com.hjy.service.product.IApiProductService#syncSkuStore(java.lang.String)
	 */
	@Override
	public ResponseProduct syncSkuStore(String products) {
		ResponseProduct response = new ResponseProduct();
		String lock = "";
		try {
			lock = WebHelper.getInstance().addLock(10, "Product.syncSkuStore");
			if (lock != null && !"".equals(lock)) {
				RequestProducts requestProducts = JSON.toJavaObject(JSON.parseObject(products), RequestProducts.class);
				if (requestProducts != null) {
					List<ProductInfo> productList = requestProducts.getProductInfos();
					if (productList != null && productList.size() > 0) {
						List<String> productCodes = new ArrayList<String>();
						List<PcSkuInfo> skuInfos = new ArrayList<PcSkuInfo>();
						for (ProductInfo info : productList) {
							productCodes.add(info.getProductOutCode());
							skuInfos.addAll(info.getSkuInfoList());
						}
						/**
						 * 修改pc_skuinfo表中的库存信息
						 */
						for (PcSkuInfo info : skuInfos) {
							PcSkuinfo sku = new PcSkuinfo();
							sku.setSkuCodeOld(info.getSkuCode());
							sku.setStockNum(info.getStockNum());
							skuInfoDao.updateSkuStore(sku);
						}
						/**
						 * 根据外部订单编号查询pc_skuinfo数据
						 * 将pc_skuinfo已修改库存同步到sc_store_skunum的库存信息
						 */
						List<PcSkuinfo> skuinfoList = skuInfoDao.findSkuInfoListByProductCodeOld(productCodes);
						if (skuinfoList != null && skuinfoList.size() > 0) {
							for (PcSkuinfo sku : skuinfoList) {
								ScStoreSkunum skunum = new ScStoreSkunum();
								skunum.setSkuCode(sku.getSkuCode());
								skunum.setStockNum(sku.getStockNum());
								scStoreSkunumDao.updateSelectiveBySkuCode(skunum);
							}
						}
						response.setCode(0);
						response.setDesc(getInfo(0));
					} else {
						response.setCode(10);
						response.setDesc(getInfo(10));
					}
				} else {
					response.setCode(10);
					response.setDesc(getInfo(10));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(10);
			response.setDesc(getInfo(10));
		} finally {
			if (lock != null && !"".equals(lock)) {
				WebHelper.getInstance().unLock(lock);
			}
		}
		return response;
	}

	/**
	 * 
	 * 方法: addProduct <br>
	 * 描述: 添加商品公共方法 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 下午2:07:17
	 * 
	 * @param producsts
	 * @return
	 */
	private boolean addProduct(List<ProductInfo> producsts) {
		boolean flag = false;
		try {
			String sellerCode = "";
			/**
			 * 批量添加商品
			 */
			List<PcProductinfo> productInfoList = getPcProdcutInfoList(producsts, sellerCode);
			productInfoDao.batchInsert(productInfoList);
			/**
			 * 批量添加商品描述
			 */
			List<PcProductdescription> pcProductdescriptionList = getPcProductdescriptionList(producsts);
			if (pcProductdescriptionList != null && pcProductdescriptionList.size() > 0) {
				productdescription.batchInsert(pcProductdescriptionList);
			}
			/**
			 * 批量添加商品图片
			 */
			List<PcProductpic> productpicList = getPcProductpicList(producsts);
			if (productpicList != null && productpicList.size() > 0) {
				pcProductpic.batchInsert(productpicList);
			}
			/**
			 * 批量添加sku
			 */
			List<PcSkuinfo> skuInfoList = getPcSkuInfoList(producsts, sellerCode);
			if (skuInfoList != null && skuInfoList.size() > 0) {
				skuInfoDao.batchInsert(skuInfoList);
			}
			/**
			 * 批量添加库存信息
			 */
			List<ScStoreSkunum> storeList = getScStoreSkunumList(skuInfoList);
			if (storeList != null && storeList.size() > 0) {
				scStoreSkunumDao.batchInsert(storeList);
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 
	 * 方法: editProduct <br>
	 * 描述: 编辑商品公共方法 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 下午2:08:03
	 * 
	 * @param producsts
	 * @return
	 */
	private boolean editProduct(List<ProductInfo> producsts) {
		boolean flag = false;
		try {
			String sellerCode = "";
			List<PcProductinfo> productInfoList = getPcProdcutInfoList(producsts, sellerCode);
			/**
			 * 商品描述集合
			 */
			List<PcProductdescription> productDescriptionList = new ArrayList<PcProductdescription>();
			/**
			 * 商品图片集合
			 */
			List<PcProductpic> productPicList = new ArrayList<PcProductpic>();
			/**
			 * 商品sku集合
			 */
			List<PcSkuinfo> skuInfoList = new ArrayList<PcSkuinfo>();
			/**
			 * 批量编辑商品
			 */
			if (productInfoList != null && productInfoList.size() > 0) {
				for (PcProductinfo info : productInfoList) {
					productInfoDao.updateProductByProductCodeOld(info);
					/**
					 * 获取商品描述
					 */
					List<PcProductdescription> descriptions = getPcProductdescriptionList(producsts);
					productDescriptionList.addAll(descriptions);
					/**
					 * 获取商品图片
					 */
					List<PcProductpic> pics = getPcProductpicList(producsts);
					productPicList.addAll(pics);
					/**
					 * 获取sku
					 */
					List<PcSkuinfo> skus = getPcSkuInfoList(producsts, sellerCode);
					skuInfoList.addAll(skus);
				}
			}
			/**
			 * sku库存集合
			 */
			List<ScStoreSkunum> skuStoreList = getScStoreSkunumList(skuInfoList);
			/**
			 * 批量编辑商品描述
			 */
			if (productDescriptionList.size() > 0) {
				for (PcProductdescription desc : productDescriptionList) {
					productdescription.updateSelective(desc);
				}
			}
			/**
			 * 批量编辑商品图片
			 */
			if (productPicList.size() > 0) {
				for (PcProductpic pic : productPicList) {
					pcProductpic.updateSelective(pic);
				}
			}
			/**
			 * 批量编辑sku
			 */
			for (PcSkuinfo pcSkuinfo : skuInfoList) {
				skuInfoDao.updateSkuInfoBySkuCodeOld(pcSkuinfo);
			}
			/**
			 * 批量编辑库存信息
			 */
			for (ScStoreSkunum store : skuStoreList) {
				scStoreSkunumDao.updateSelectiveBySkuCode(store);
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
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
	private ResponseProduct verifyProduct(List<ProductInfo> productList) {
		ResponseProduct response = new ResponseProduct();
		for (int i = 0; i < productList.size(); i++) {
			ProductInfo product = productList.get(i);
			if (product == null) {
				response.setCode(11);
				response.setDesc(getInfo(11));
				break;
			}
			if (StringUtils.isBlank(product.getProductName())) {
				response.setCode(12);
				response.setDesc(getInfo(12));
				break;
			}
			if (StringUtils.isBlank(product.getBrandCode())) {
				response.setCode(13);
				response.setDesc(getInfo(13));
				break;
			} else {
				// 验证商品编号是否存在 暂时不考虑
			}
			if (product.getSkuInfoList() == null || product.getSkuInfoList().size() == 0) {
				response.setCode(14);
				response.setDesc(getInfo(14));
				break;
			}
			for (PcSkuInfo sku : product.getSkuInfoList()) {
				if (sku.getStockNum() < 0) {
					response.setCode(15);
					response.setDesc(getInfo(15));
					break;
				}
				if (sku.getSellPrice().doubleValue() < 0) {
					response.setCode(16);
					response.setDesc(getInfo(16));
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
	private static List<PcProductinfo> getPcProdcutInfoList(List<ProductInfo> infos, String sellerCode) {
		List<PcProductinfo> list = new ArrayList<PcProductinfo>();
		if (infos != null && infos.size() > 0) {
			for (ProductInfo info : infos) {
				if (info != null) {
					PcProductinfo product = new PcProductinfo();
					product.setUid(WebHelper.getInstance().genUuid());
					product.setProductCode(info.getProductCode());
					product.setProductCodeOld(info.getProductOutCode());
					product.setProductCodeCopy(info.getProductOutCode());
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
	private static List<PcProductdescription> getPcProductdescriptionList(List<ProductInfo> infos) {
		List<PcProductdescription> list = new ArrayList<PcProductdescription>();
		if (infos != null && infos.size() > 0) {
			for (ProductInfo product : infos) {
				if (product.getDescription() != null) {
					PcProductdescription description = new PcProductdescription();
					description.setUid(WebHelper.getInstance().genUuid());
					description.setProductCode(product.getProductCode());
					description.setDescriptionInfo(product.getDescription().getDescriptionInfo());
					description.setDescriptionPic(product.getDescription().getDescriptionPic());
					list.add(description);
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * 方法: getPcProductpicList <br>
	 * 描述: 商品图片数据集合 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月3日 下午1:52:05
	 * 
	 * @param infos
	 * @param productCode
	 * @return
	 */
	private static List<PcProductpic> getPcProductpicList(List<ProductInfo> infos) {
		List<PcProductpic> list = new ArrayList<PcProductpic>();
		if (infos != null && infos.size() > 0) {
			for (ProductInfo product : infos) {
				if (product.getPcPicList() != null && product.getPcPicList().size() > 0) {
					for (String pic : product.getPcPicList()) {
						PcProductpic ppic = new PcProductpic();
						ppic.setUid(WebHelper.getInstance().genUuid());
						ppic.setPicUrl(pic);
						ppic.setProductCode(product.getProductCode());
						ppic.setProductCodeOld(product.getProductOutCode());
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
	private static List<PcSkuinfo> getPcSkuInfoList(List<ProductInfo> infos, String sellerCode) {
		List<PcSkuinfo> list = new ArrayList<PcSkuinfo>();
		if (infos != null && infos.size() > 0) {
			for (ProductInfo product : infos) {
				if (product.getSkuInfoList() != null && product.getSkuInfoList().size() > 0) {
					for (PcSkuInfo info : product.getSkuInfoList()) {
						PcSkuinfo sku = new PcSkuinfo();
						sku.setUid(WebHelper.getInstance().genUuid());
						sku.setSkuCode(WebHelper.getInstance().genUniqueCode(SKUHead));
						sku.setProductCode(product.getProductCode());
						sku.setProductCodeOld(product.getProductOutCode());
						sku.setSellProductcode(product.getProductCode());
						sku.setSkuCodeOld(info.getSkuCode());
						sku.setSkuName(info.getSkuName());
						sku.setSkuPicurl(info.getSkuPicUrl());
						sku.setSkuAdv(info.getSkuAdv());
						sku.setSellerCode(sellerCode);
						sku.setSellPrice(info.getSellPrice());
						sku.setMarketPrice(info.getSellPrice());
						sku.setCostPrice(info.getSellPrice());
						sku.setSecurityStockNum(info.getSecurityStockNum());
						sku.setStockNum(info.getStockNum());
						sku.setMiniOrder(info.getMiniOrder());
						list.add(sku);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * 方法: getScStoreSkunumList <br>
	 * 描述: 获取sku库存列表集合 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午9:47:54
	 * 
	 * @param infos
	 * @return
	 */
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
				list.add(store);
			}
		}
		return list;
	}
}

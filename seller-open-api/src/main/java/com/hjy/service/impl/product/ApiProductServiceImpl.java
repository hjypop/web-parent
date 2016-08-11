package com.hjy.service.impl.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.common.DateUtil;
import com.hjy.dao.api.IApiProductInfoDao;
import com.hjy.dao.api.IApiSkuInfoDao;
import com.hjy.dao.api.ILcOpenApiProductErrorDao;
import com.hjy.dao.product.IPcProductdescriptionDao;
import com.hjy.dao.product.IPcProductpicDao;
import com.hjy.dao.system.IScStoreSkunumDao;
import com.hjy.dto.product.PcSkuInfo;
import com.hjy.dto.product.ProductInfo;
import com.hjy.entity.log.LcOpenApiProductError;
import com.hjy.entity.product.PcProductdescription;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcProductpic;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.factory.UserFactory;
import com.hjy.helper.WebHelper;
import com.hjy.request.RequestProduct;
import com.hjy.request.RequestProducts;
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
	@Resource
	private ILcOpenApiProductErrorDao errorDao;

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
	public JSONObject addProduct(String product, String sellerCode) {
		JSONObject response = new JSONObject();
		String lock = "";
		if (product != null && !"".equals(product)) {
			try {
				lock = WebHelper.getInstance().addLock(10, sellerCode + "_Product.addproduct");
				RequestProduct requestProduct = JSON.toJavaObject(JSON.parseObject(product), RequestProduct.class);
				if (requestProduct != null) {
					if (requestProduct.getProduct() != null) {
						List<ProductInfo> productList = new ArrayList<ProductInfo>();
						requestProduct.getProduct().setOperate(0);
						requestProduct.getProduct().setProductCode(WebHelper.getInstance().genUniqueCode(ProductHead));
						productList.add(requestProduct.getProduct());
						response = verifyProduct(productList);
						if (response.getInteger("code") == 0) {
							// 执行添加商品方法
							String errorCode = addProduct(productList, sellerCode);
							if (errorCode != null && !"".equals(errorCode)) {
								response.put("code", 10);
								response.put("desc", getInfo(10));
							} else {
								response.put("code", 0);
								response.put("desc", getInfo(0));
							}
						} else {
							response.put("code", 10);
							response.put("desc", getInfo(10));
						}
					} else {
						response.put("code", 10);
						response.put("desc", getInfo(10));
					}
				} else {
					response.put("code", 10);
					response.put("desc", getInfo(10));
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.put("code", 10);
				response.put("desc", getInfo(10));
			} finally {
				WebHelper.getInstance().unLock(lock);
			}
		} else {
			response.put("code", 10);
			response.put("desc", getInfo(10));
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
	public JSONObject editProduct(String product, String sellerCode) {
		JSONObject response = new JSONObject();
		String lock = "";
		try {
			lock = WebHelper.getInstance().addLock(10, sellerCode + "_Product.editproduct");
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
							String errorCode = editProduct(productList, sellerCode);
							if (errorCode != null && !"".equals(errorCode)) {
								response.put("code", 10);
								response.put("desc", getInfo(10));
							} else {
								response.put("code", 0);
								response.put("desc", getInfo(0));
							}
						} else {
							response.put("code", 10);
							response.put("desc", getInfo(10));
						}
					} else {
						response.put("code", 10);
						response.put("desc", getInfo(10));
					}
				} else {
					response.put("code", 10);
					response.put("desc", getInfo(10));
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
	public JSONObject batchProducts(String products, String sellerCode) {
		JSONObject response = new JSONObject();
		if (products != null && !"".equals(products)) {
			String lock = "";
			try {
				lock = WebHelper.getInstance().addLock(10, sellerCode + "_Product.syncProductList");
				if (lock != null && !"".equals(lock)) {
					RequestProducts requestProduct = JSON.toJavaObject(JSON.parseObject(products),
							RequestProducts.class);
					if (requestProduct != null) {
						if (requestProduct.getProductInfos() != null && requestProduct.getProductInfos().size() > 0) {
							response = verifyProduct(requestProduct.getProductInfos());
							if (response.getInteger("code") == 0) {
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
								String errorCode = addProduct(addList, sellerCode);
								// 执行编辑操作
								errorCode = errorCode + editProduct(editList, sellerCode);
								// 返回操作结果
								if (errorCode != null && !"".equals(errorCode)) {
									response.put("code", 10);
									response.put("desc", getInfo(10));
								} else {
									response.put("code", 0);
									response.put("desc", getInfo(0));
								}

							}
						} else {
							response.put("code", 10);
							response.put("desc", getInfo(10));
						}
					} else {
						response.put("code", 10);
						response.put("desc", getInfo(10));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.put("code", 10);
				response.put("desc", getInfo(10));
			} finally {
				if (lock != null && !"".equals(lock)) {
					WebHelper.getInstance().unLock(lock);
				}
			}
		} else {
			response.put("code", 10);
			response.put("desc", getInfo(10));
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
	public JSONObject batchProductsPrice(String products, String sellerCode) {
		JSONObject response = new JSONObject();
		String lock = "";
		try {
			lock = WebHelper.getInstance().addLock(10, sellerCode + "_Product.batchProductsPrice");
			if (lock != null && !"".equals(lock)) {
				if (products != null && !"".equals(products)) {
					RequestProducts requestProducts = JSON.toJavaObject(JSON.parseObject(products),
							RequestProducts.class);
					if (requestProducts != null) {
						StringBuffer errorCode = new StringBuffer();
						List<ProductInfo> productList = requestProducts.getProductInfos();
						if (productList != null && productList.size() > 0) {
							for (ProductInfo info : productList) {
								// 根据外部商品编号查询惠家有商品编号
								String productCode = productInfoDao.findProductCodeByOutCode(info.getProductOutCode());
								info.setProductCode(productCode);
								try {
									/**
									 * 编辑商品的最小售价和最大售价
									 */
									PcProductinfo product = setPcProductinfo(info, sellerCode);
									productInfoDao.updateProductPrice(product);
									/**
									 * 编辑sku的售价
									 */
									List<PcSkuinfo> skuInfos = setPcSkuinfos(info, sellerCode);
									if (skuInfos != null && skuInfos.size() > 0) {
										for (PcSkuinfo pcSkuinfo : skuInfos) {
											skuInfoDao.updateSkuPrice(pcSkuinfo);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
									errorCode.append(info.getProductOutCode()).append(",");
									TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
									/**
									 * 记录错误日志
									 */
									LcOpenApiProductError error = new LcOpenApiProductError();
									error.setUid(WebHelper.getInstance().genUuid());
									error.setMethod(
											"com.hjy.service.impl.product.ApiProductServiceImpl.batchProductsPrice");
									error.setProductData(JSON.toJSONString(info));
									error.setProductOutCode(info.getProductOutCode());
									error.setSellerCode(sellerCode);
									error.setCreateTime(DateUtil.getSysDateTimestamp());
									error.setCreateUser(UserFactory.INSTANCE.create().getUserCode());
									errorDao.insertSelective(error);
								}
							}
							if (errorCode != null && !"".equals(errorCode.toString())) {
								response.put("code", 10);
								response.put("desc", getInfo(10));
							} else {
								response.put("code", 0);
								response.put("desc", getInfo(0));
							}
						} else {
							response.put("code", 10);
							response.put("desc", getInfo(10));
						}
					} else {
						response.put("code", 10);
						response.put("desc", getInfo(10));
					}
				} else {
					response.put("code", 10);
					response.put("desc", getInfo(10));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("code", 10);
			response.put("desc", getInfo(10));
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
	public JSONObject batchProductsSkuStore(String products, String sellerCode) {
		JSONObject response = new JSONObject();
		String lock = "";
		try {
			lock = WebHelper.getInstance().addLock(10, sellerCode + "_Product.syncSkuStore");
			if (lock != null && !"".equals(lock)) {
				RequestProducts requestProducts = JSON.toJavaObject(JSON.parseObject(products), RequestProducts.class);
				if (requestProducts != null) {
					List<ProductInfo> productList = requestProducts.getProductInfos();
					if (productList != null && productList.size() > 0) {
						try {
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
						} catch (Exception e) {
							e.printStackTrace();
							response.put("code", 10);
							response.put("desc", getInfo(10));
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							/**
							 * 记录错误日志
							 */
							LcOpenApiProductError error = new LcOpenApiProductError();
							error.setUid(WebHelper.getInstance().genUuid());
							error.setMethod("com.hjy.service.impl.product.ApiProductServiceImpl.batchProductsSkuStore");
							error.setProductData(JSON.toJSONString(productList));
							error.setProductOutCode("");
							error.setSellerCode(sellerCode);
							error.setCreateTime(DateUtil.getSysDateTimestamp());
							error.setCreateUser(UserFactory.INSTANCE.create().getUserCode());
							errorDao.insertSelective(error);
						}
						response.put("code", 0);
						response.put("desc", getInfo(0));
					} else {
						response.put("code", 10);
						response.put("desc", getInfo(10));
					}
				} else {
					response.put("code", 10);
					response.put("desc", getInfo(10));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("code", 10);
			response.put("desc", getInfo(10));
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
	private String addProduct(List<ProductInfo> producsts, String sellerCode) {
		StringBuffer errorCode = new StringBuffer();
		try {
			for (ProductInfo info : producsts) {
				/**
				 * 批量添加商品
				 */
				PcProductinfo product = setPcProductinfo(info, sellerCode);
				if (product != null) {
					try {
						productInfoDao.insert(product);
						/**
						 * 批量添加商品描述
						 */
						PcProductdescription description = setPcProductdescription(info);
						if (description != null) {
							productdescription.insertSelective(description);
						}
						/**
						 * 批量添加商品图片
						 */
						List<PcProductpic> productpicList = setPcProductpics(info);
						if (productpicList != null && productpicList.size() > 0) {
							pcProductpic.batchInsert(productpicList);
						}
						/**
						 * 批量添加sku
						 */
						List<PcSkuinfo> skuInfoList = setPcSkuinfos(info, sellerCode);
						if (skuInfoList != null && skuInfoList.size() > 0) {
							skuInfoDao.batchInsert(skuInfoList);
						}
						/**
						 * 批量添加库存信息
						 */
						List<ScStoreSkunum> storeList = setScStoreSkunums(skuInfoList);
						if (storeList != null && storeList.size() > 0) {
							scStoreSkunumDao.batchInsert(storeList);
						}
					} catch (Exception e) {
						e.printStackTrace();
						errorCode.append(info.getProductOutCode()).append(",");
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						/**
						 * 记录错误日志
						 */
						LcOpenApiProductError error = new LcOpenApiProductError();
						error.setUid(WebHelper.getInstance().genUuid());
						error.setMethod("com.hjy.service.impl.product.ApiProductServiceImpl.addProduct");
						error.setProductData(JSON.toJSONString(info));
						error.setProductOutCode(info.getProductOutCode());
						error.setSellerCode(sellerCode);
						error.setCreateTime(DateUtil.getSysDateTimestamp());
						error.setCreateUser(UserFactory.INSTANCE.create().getUserCode());
						errorDao.insertSelective(error);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorCode.toString();
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
	private String editProduct(List<ProductInfo> producsts, String sellerCode) {
		StringBuffer errorCode = new StringBuffer();
		try {
			for (ProductInfo info : producsts) {
				PcProductinfo product = setPcProductinfo(info, sellerCode);
				if (product != null) {
					try {
						/**
						 * 编辑商品
						 */
						productInfoDao.updateProductByProductCodeOld(product);

						/**
						 * 编辑商品描述
						 */
						PcProductdescription description = setPcProductdescription(info);
						if (description != null) {
							productdescription.updateSelective(description);
						}
						/**
						 * 编辑商品图片
						 */
						List<PcProductpic> pics = setPcProductpics(info);
						/**
						 * 批量编辑商品图片
						 */
						if (pics.size() > 0) {
							for (PcProductpic pic : pics) {
								pcProductpic.updateSelective(pic);
							}
						}
						/**
						 * 编辑sku
						 */
						List<PcSkuinfo> skus = setPcSkuinfos(info, sellerCode);
						if (skus != null && skus.size() > 0) {
							for (PcSkuinfo sku : skus) {
								skuInfoDao.updateSkuInfoBySkuCodeOld(sku);
							}
						}
						/**
						 * 编辑sku库存信息
						 */
						List<ScStoreSkunum> skuStoreList = setScStoreSkunums(skus);
						if (skuStoreList != null && skuStoreList.size() > 0) {
							for (ScStoreSkunum store : skuStoreList) {
								scStoreSkunumDao.updateSelectiveBySkuCode(store);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						errorCode.append(info.getProductOutCode()).append(",");
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						/**
						 * 记录错误日志
						 */
						LcOpenApiProductError error = new LcOpenApiProductError();
						error.setUid(WebHelper.getInstance().genUuid());
						error.setMethod("com.hjy.service.impl.product.ApiProductServiceImpl.editProduct");
						error.setProductData(JSON.toJSONString(info));
						error.setProductOutCode(info.getProductOutCode());
						error.setSellerCode(sellerCode);
						error.setCreateTime(DateUtil.getSysDateTimestamp());
						error.setCreateUser(UserFactory.INSTANCE.create().getUserCode());
						errorDao.insertSelective(error);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorCode.toString();
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
	private JSONObject verifyProduct(List<ProductInfo> productList) {
		JSONObject response = new JSONObject();
		response.put("code", 0);
		response.put("desc", getInfo(0));
		for (int i = 0; i < productList.size(); i++) {
			ProductInfo product = productList.get(i);
			if (product == null) {
				response.put("code", 11);
				response.put("desc", getInfo(11));
				break;
			}
			if (StringUtils.isBlank(product.getProductName())) {
				response.put("code", 12);
				response.put("desc", getInfo(12));
				break;
			}
			if (StringUtils.isBlank(product.getBrandCode())) {
				response.put("code", 13);
				response.put("desc", getInfo(13));
				break;
			} else {
				// 验证商品编号是否存在 暂时不考虑
			}
			if (product.getSkuInfoList() == null || product.getSkuInfoList().size() == 0) {
				response.put("code", 14);
				response.put("desc", getInfo(14));
				break;
			}
			for (PcSkuInfo sku : product.getSkuInfoList()) {
				if (sku.getStockNum() < 0) {
					response.put("code", 15);
					response.put("desc", getInfo(15));
					break;
				}
				if (sku.getSellPrice().doubleValue() < 0) {
					response.put("code", 16);
					response.put("desc", getInfo(16));
					break;
				}
			}
		}
		// 校验商品名字的外链合法性 暂时不考虑
		return response;
	}

	/**
	 * 
	 * 方法: setPcProductinfo <br>
	 * 描述: 将ProductInfo转换为PcProductinfo <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月11日 下午2:09:42
	 * 
	 * @param info
	 * @param sellerCode
	 * @return
	 */
	private static PcProductinfo setPcProductinfo(ProductInfo info, String sellerCode) {
		PcProductinfo product = null;
		if (info != null) {
			product = new PcProductinfo();
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
		}
		return product;
	}

	/**
	 * 
	 * 方法: setPcProductdescription <br>
	 * 描述: 转换PcProductdescription <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月11日 下午2:10:01
	 * 
	 * @param info
	 * @return
	 */
	private static PcProductdescription setPcProductdescription(ProductInfo info) {
		PcProductdescription description = null;
		if (info != null && info.getDescription() != null) {
			description = new PcProductdescription();
			description.setUid(WebHelper.getInstance().genUuid());
			description.setProductCode(info.getProductCode());
			description.setDescriptionInfo(info.getDescription().getDescriptionInfo());
			description.setDescriptionPic(info.getDescription().getDescriptionPic());
		}
		return description;
	}

	/**
	 * 
	 * 方法: setPcProductpic <br>
	 * 描述: 获取PcProductpic <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月11日 下午2:11:17
	 * 
	 * @param info
	 * @return
	 */
	private static List<PcProductpic> setPcProductpics(ProductInfo info) {
		List<PcProductpic> list = new ArrayList<PcProductpic>();
		if (info != null) {
			if (info.getPcPicList() != null && info.getPcPicList().size() > 0) {
				for (String pic : info.getPcPicList()) {
					PcProductpic ppic = new PcProductpic();
					ppic.setUid(WebHelper.getInstance().genUuid());
					ppic.setPicUrl(pic);
					ppic.setProductCode(info.getProductCode());
					ppic.setProductCodeOld(info.getProductOutCode());
					list.add(ppic);
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * 方法: setPcSkuinfos <br>
	 * 描述: 获取sku列表 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月11日 下午2:13:11
	 * 
	 * @param info
	 * @param sellerCode
	 * @return
	 */
	private static List<PcSkuinfo> setPcSkuinfos(ProductInfo product, String sellerCode) {
		List<PcSkuinfo> list = new ArrayList<PcSkuinfo>();
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
		return list;
	}

	/**
	 * 
	 * 方法: setScStoreSkunums <br>
	 * 描述: 获取sku库存列表集合 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午9:47:54
	 * 
	 * @param infos
	 * @return
	 */
	private static List<ScStoreSkunum> setScStoreSkunums(List<PcSkuinfo> infos) {
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

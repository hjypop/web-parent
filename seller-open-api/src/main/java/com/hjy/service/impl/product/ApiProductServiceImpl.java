package com.hjy.service.impl.product;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.ExculdeNullField;
import com.hjy.annotation.TargetField;
import com.hjy.common.DateUtil;
import com.hjy.dao.IApiProductInfoDao;
import com.hjy.dao.IApiSkuInfoDao;
import com.hjy.dao.ILcOpenApiProductErrorDao;
import com.hjy.dao.ILcOpenApiQueryLogDao;
import com.hjy.dao.product.IPcProductdescriptionDao;
import com.hjy.dao.product.IPcProductpicDao;
import com.hjy.dao.system.IScStoreSkunumDao;
import com.hjy.dto.product.ApiProductDesc;
import com.hjy.dto.product.ApiSellerProduct;
import com.hjy.dto.product.ApiSellerSkuInfo;
import com.hjy.dto.product.PcSkuInfo;
import com.hjy.dto.product.ProductInfo;
import com.hjy.dto.product.ProductStatus;
import com.hjy.dto.product.Property;
import com.hjy.entity.log.LcOpenApiProductError;
import com.hjy.entity.log.LcOpenApiQueryLog;
import com.hjy.entity.product.PcProductdescription;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcProductpic;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.factory.UserFactory;
import com.hjy.helper.DateHelper;
import com.hjy.helper.ExceptionHelper;
import com.hjy.helper.WebHelper;
import com.hjy.request.RequestProduct;
import com.hjy.request.RequestProducts;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.product.IApiProductService;
import com.hjy.system.cmodel.CacheWcSellerInfo;

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
	@Resource
	private ILcOpenApiQueryLogDao openApiQueryDao;

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
	public JSONObject syncDemooooooooooooooo(String products, CacheWcSellerInfo seller){
		String sellerCode = seller.getSellerCode();
		JSONObject result = new JSONObject();  
		result.put("code", 1);  // 默认成功，为1
		String productHead = this.getConfig("seller_adapter.product_" + seller.getSellerType()); ;
		String skuHead = this.getConfig("seller_adapter.sku_" + seller.getSellerType()); ;
		if(StringUtils.isNotBlank(products)){
			String lock = "";
			try {
				lock = WebHelper.getInstance().addLock(180 , sellerCode + "@syncDemooooooooooooooo");
				if(StringUtils.isNotBlank(lock)){
					List<ProductInfo> plist =  null; 
					try {
						plist =  JSONArray.parseArray(products , ProductInfo.class);
						if(plist != null && plist.size() > 0){
							if(plist.size() > 100){
								result.put("code", 3);
								result.put("desc", this.getInfo(100009004 , 100));  // 请求数据量过大，超过限制{0}条
								return result; 
							}
						}
					} catch (Exception e) {
						result.put("code", 3);
						result.put("desc", this.getInfo(100009003));  // 请求参数错误，请求数据解析异常
						return result; 
					}
				}else{
					result.put("code", 0);
					result.put("desc", this.getInfo(100009002));  // 分布式锁生效中
				}
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		}else{
			result.put("code", -1);
			result.put("desc", this.getInfo(100009001));  // 请求数据报文data为空
		}
		return result;
	}
	
	/**
	 * @description: 商户同步自己的商品到惠家有平台|同时同步一批商品，上限100件商品
	 * 	
	 * @接口所属：惠家有商户接口|Product.SyncSellerProductList
	 * 
	 * @param products
	 * @param seller
	 * @author Yangcl 
	 * @date 2016年12月29日 下午4:45:50 
	 * @version 1.0.0.1
	 */
	public JSONObject syncSellerProductList(String products, CacheWcSellerInfo seller){
		String sellerCode = seller.getSellerCode();
		JSONObject result = new JSONObject();  
		result.put("code", 1);  // 默认成功，为1
		String productHead = this.getConfig("seller_adapter.product_" + seller.getSellerType()); ;
		String skuHead = this.getConfig("seller_adapter.sku_" + seller.getSellerType()); ;
		if(StringUtils.isNotBlank(products)){
			String lock = "";
			try {
				lock = WebHelper.getInstance().addLock(180 , sellerCode + "@Product.SyncSellerProductList");  // 三分钟内不得访问 
				if(StringUtils.isNotBlank(lock)){
					List<ApiSellerProduct> plist =  null; 
					try {
						plist =  JSONArray.parseArray(products , ApiSellerProduct.class);
						if(plist != null && plist.size() > 0){
							if(plist.size() > 100){
								result.put("code", 3);
								result.put("desc", this.getInfo(100009004 , 100));  // 请求数据量过大，超过限制{0}条
								return result; 
							}
							List<ApiSellerProduct> inlist = new ArrayList<ApiSellerProduct>();
							List<ApiSellerProduct> uplist = new ArrayList<ApiSellerProduct>(); 
							List<String> errors = new ArrayList<String>();      // 作为结果返回给商户           
							for(ApiSellerProduct p : plist){
								JSONObject vali = this.entityValidate(p);  // 验证数据合法性
								if( !vali.getBoolean("flag") ){
									errors.add(p.getSellerProductCode() + "@" + vali.getString("desc"));
									continue;
								}
								
								Integer count = productInfoDao.findSellerProductCode(sellerCode + "-" +p.getSellerProductCode());
								if(count != null && count != 0){
									uplist.add(p);
								}else if(count != null && count == 0){
									 // 对于通过open-api平台接入的商品，其外部商品编号均以"seller_code"-"seller_product_code"的形式来区分
									p.setSellerProductCode(sellerCode + "-" + p.getSellerProductCode());   
									inlist.add(p);
								}
							}
							// 插入商品信息
							if(inlist.size() != 0){
								for(ApiSellerProduct p : inlist){
									
								}
							}
							// 更新商品信息
							
						}
					} catch (Exception e) {
						result.put("code", 3);
						result.put("desc", this.getInfo(100009003));  // 请求参数错误，请求数据解析异常
						return result; 
					}
				}else{
					result.put("code", 0);
					result.put("desc", this.getInfo(100009002));  // 分布式锁生效中
				}
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		}else{
			result.put("code", -1);
			result.put("desc", this.getInfo(100009001));  // 请求数据报文data为空
		}
		return result;
	}
	
	/**
	 * @description: 数据合法性验证
	 * 
	 * @author Yangcl 
	 * @date 2016年12月30日 下午3:11:57 
	 * @version 1.0.0.1
	 */
	private  JSONObject entityValidate(ApiSellerProduct p){
		JSONObject result = new JSONObject();
		result.put("flag", false);
		if(StringUtils.isAnyBlank(p.getSellerProductCode(),
			p.getProductName() , p.getProductShortname(),
			String.valueOf(p.getProductWeight()) , String.valueOf(p.getCostPrice()),
			String.valueOf(p.getMarketPrice()), p.getMainPicUrl(),p.getProductVolumeItem(),
			String.valueOf(p.getProductVolume()), p.getExpiryUnit() )){
			result.put("desc", this.getInfo(100009005));  // 请求参数体中包含不合法的字段
			return result;
		}
		if(p.getPcPicList() == null || p.getPcPicList().size() == 0){
			result.put("desc", this.getInfo(100009005));  // 请求参数体中包含不合法的字段
			return result;
		}
		if(p.getDescription() == null){
			result.put("desc", this.getInfo(100009006));  // 商品描述信息不得为空
			return result;
		}
		ApiProductDesc de = p.getDescription();
		if(StringUtils.isAnyBlank(de.getDescriptionInfo(),de.getDescriptionPic() , de.getKeyword())){
			result.put("desc", this.getInfo(100009006));  // 商品描述信息不得为空
			return result;
		}
		if(!this.isNumeric(p.getExpiryDate().toString())){
			result.put("desc", this.getInfo(100009007));  // 保质期格式非法
			return result;
		}
		if(!(StringUtils.startsWith(p.getExpiryUnit(), "449747160029000") && 
				StringUtils.endsWithAny(p.getExpiryUnit(), "1" , "2" , "3"))){
			result.put("desc", this.getInfo(100009008));  // 保质期单位非法
			return result;
		}
		// 开始判定sku相关信息 
		if(p.getSkuList() == null || p.getSkuList().size() == 0){
			result.put("desc", this.getInfo(100009009));  // 商品的Sku列表不得为空
			return result;
		}
		List<ApiSellerSkuInfo> skus = p.getSkuList();
		for(ApiSellerSkuInfo s : skus){
			if(StringUtils.isAnyBlank(s.getSellPrice().toString(),
					s.getCostPrice().toString(), s.getStockNum().toString(),
					s.getSkuPicUrl() , s.getSkuName() , s.getSkuAdv() , 
					s.getSecurityStockNum().toString() , s.getMiniOrder().toString()
					)){
				result.put("desc", this.getInfo(100009010));  // 商品的Sku关键信息不得为空
				return result;
			}
			if(!this.isNumeric(s.getStockNum().toString())){
				result.put("desc", this.getInfo(100009011));  // 非法的商品Sku库存数
				return result;
			}
			if(!this.isNumeric(s.getSecurityStockNum().toString())){
				result.put("desc", this.getInfo(100009012));  // 非法的商品Sku安全库存
				return result;
			}
			if(!this.isNumeric(s.getMiniOrder().toString())){
				result.put("desc", this.getInfo(100009013));  // 非法的商品Sku起订数量
				return result;
			}
		}
		
		result.put("flag", true);
		return result;
	}
	
	private boolean isNumeric(String str){  
	   for(int i=str.length();--i>=0;){  
	      int chr=str.charAt(i);  
	      if(chr<48 || chr>57)  
	         return false;  
	   }  
	   return true;  
	}

	private  <T , E> JSONObject validate(T t , E e){
		JSONObject result = new JSONObject();
		result.put("flag", true);
		
		Field[] fields = t.getClass().getDeclaredFields();
		 try {
			 for(int i = 0 ; i < fields.length ; i ++){
				 Field field = fields[i];
				 String name = field.getName();
				 String func = "get" + name.substring(0,1).toUpperCase()+name.substring(1);
				 Method m = t.getClass().getMethod(func);
	             String value = ""; 
	             if(field.isAnnotationPresent(ExculdeNullField.class)){
	            	 continue; // ExculdeNullField 注解标识的字段为空，则不再对其反射设值。
	             }else if( e == null){  // 如果要赋值的对象为null 则只验证，不赋值
	                 continue;
	             }
	             if(m.invoke(t) != null) { 
	            	 value = String.valueOf(m.invoke(t)); 
	             }else{ // 如果getter方法取值为null，则代表T对象该字段为null，不再操作
	            	 continue; 
	             }
	             
	             // 如果字段包含目标反射声明注解 
	             if(field.isAnnotationPresent(TargetField.class)){
	            	 name = field.getAnnotation(TargetField.class).value();
	             }
	             // 赋值 
	             String func_ = "set" + name.substring(0,1).toUpperCase()+name.substring(1); 
				 Method m_ = e.getClass().getMethod(func_  , m.invoke(t).getClass()); 
	             // 如果这里是t.getClass() 会引起 object is not an instance of declaring class 这个异常。
	             // 原因在于 T 对象有值了，你还在尝试对他赋值。
				@SuppressWarnings("rawtypes")
				Class[] c = m_.getParameterTypes();
				 if(c[0] == String.class) {
		 	 		 m_.invoke(e , value);
		 	 	 }else if(c[0] == BigDecimal.class) {
		 	 		 m_.invoke(e , BigDecimal.valueOf(Double.valueOf(value)));
		 	 	 }else if(c[0] == Integer.class) {
					 m_.invoke(e ,Integer.valueOf(value));
			 	 }else if(c[0] == Boolean.class) {
		 	 		 m_.invoke(e , Boolean.valueOf(value));
		 	 	 }else if(c[0] == Float.class){
		 		 	 m_.invoke(e , Float.valueOf(value));
		 	 	 }else if(c[0] == Double.class) {
	 	 		 	 m_.invoke(e , Double.valueOf(value));
		 	 	 }else if(c[0] == Byte.class) {
		 	 		 m_.invoke(e , Byte.valueOf(value));
		 	 	 }
			 }
		 } catch (NoSuchMethodException ex) {
	            // ex.printStackTrace();  被赋值的对象中找不到的方法不做处理即可
		 } catch (SecurityException ex) {
			 ex.printStackTrace();
		 } catch (IllegalAccessException ex) {
			 ex.printStackTrace();
		 } catch (IllegalArgumentException ex) {
			 ex.printStackTrace();
		 } catch (InvocationTargetException ex) {
			 ex.printStackTrace();
		 } 
		 
		return result;
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
					RequestProducts requestProduct = JSON.toJavaObject(JSON.parseObject(products), RequestProducts.class);
					if (requestProduct != null) {
						if (requestProduct.getProductInfos() != null && requestProduct.getProductInfos().size() > 0) {
							response = verifyProduct(requestProduct.getProductInfos());
							if (response.getInteger("code") == 0) {
								for (ProductInfo info : requestProduct.getProductInfos()) {
									if (info.getOperate() == 1) {
										// 根据外部商品编号查询惠家有商品编号
										String productCode = productInfoDao.findProductCodeByOutCode(info.getProductOutCode()); 
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

	/**
	 * 
	 * 方法: pushProduct <br>
	 * 描述: 根据日期范围推送产品
	 * 
	 * @param seller
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.hjy.service.product.IApiProductService#pushProduct(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public JSONObject pushProduct(CacheWcSellerInfo seller, String startDate, String endDate) {
		JSONObject response = new JSONObject();
		List<ProductInfo> responseProduct = new ArrayList<ProductInfo>();
		String lock = "";
		try {
			lock = WebHelper.getInstance().addLock(10, seller.getSellerCode() + "_Product.pushProduct");
			// 读取合作商的产品获取权限
			if (seller.getCommission() != null && !"".equals(seller.getCommission())) {
				JSONArray commissions = JSONArray.parseArray(seller.getCommission());
				// 获取参数
				for (int i = 0; i < commissions.size(); i++) {
					JSONObject c = commissions.getJSONObject(i);
					Map<String, String> map = new HashMap<String, String>();
					map.put("startTime", startDate);
					map.put("endTime", endDate);
					if ("LD".equals(c.getString("type"))) {
						map.put("LD", "LD");
					} else {
						map.put("sellerType", c.getString("type"));
					}
					List<PcProductinfo> list = productInfoDao.findProductBySellerProductype(map);
					List<ProductInfo> products = initPcProduct(list, c, seller.getPriceType());
					if (products != null && products.size() > 0) {
						for(int n = 0 ; n < products.size() ; n ++){
							String pcode =  products.get(i).getProductCode();
							List<Property> prolist = productInfoDao.getProductPropertyByCode(pcode);
							products.get(i).setPropertys(prolist); 
						}
						
						responseProduct.addAll(products);
					}
				}
				response.put("code", 0);
				response.put("desc", getInfo(0));
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
		/**
		 * 生成响应报文
		 */
		response.put("data", responseProduct);
		response.put("total", responseProduct.size());
		return response;
	}

	/**
	 * 
	 * 方法: initPcProductinof <br>
	 * 描述: 获取需要推送的产品信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月25日 下午4:54:20
	 * 
	 * @param list
	 * @param commission
	 * @return
	 */
	public List<ProductInfo> initPcProduct(List<PcProductinfo> list, JSONObject commission, Integer priceType) {
		List<ProductInfo> products = new ArrayList<ProductInfo>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				PcProductinfo info = list.get(i);
				/**
				 * 生成推送到第三方产品对象信息
				 */
				ProductInfo product = new ProductInfo();
				/**
				 * 商品编号
				 */
				product.setProductCode(info.getProductCode());
				/**
				 * 商品名称
				 */
				product.setProductName(info.getProductName());
				/**
				 * 商品简称
				 */
				product.setProductShortname(info.getProductShortname());
				/**
				 * 商品重量
				 */
				product.setProductWeight(info.getProductWeight());
				/**
				 * 成本价
				 */
				if (priceType == 0) {
					product.setCostPrice(info.getCostPrice()
							.add(info.getCostPrice().multiply(BigDecimal.valueOf(commission.getDouble("commission")))));
				} else {
					product.setCostPrice(info.getSellPrice()
							.add(info.getSellPrice().multiply(BigDecimal.valueOf(commission.getDouble("commission")))));
				}
				/**
				 * 主图的Url
				 */
				product.setMainPicUrl(info.getMainpicUrl());
				/**
				 * 商品描述
				 */
				product.setDescription(productInfoDao.getProductDescByCode(info.getProductCode()));
				/**
				 * 商品图片信息
				 */
				List<String> pcPicList = productInfoDao.getProductPicByCode(info.getProductCode());
				product.setPcPicList(pcPicList);
				/**
				 * 商品体积
				 */
				product.setProductVolume(info.getProductVolume());
				/**
				 * sku集合
				 */
				product.setSkuInfoList(initSkuList(info.getProductCode(), commission.getDouble("commission")));
				/**
				 * 广告图的Url
				 */
				product.setAdpicUrl(info.getAdPicUrl());
				/**
				 * 商品广告
				 */
				product.setProductAdv(info.getProductAdv());
				/**
				 * 保质期
				 */
				product.setExpiryDate(info.getExpiryDate());

				/**
				 * 是否是虚拟商品 Y：是 N：否
				 */
				product.setValidateFlag(info.getValidate_flag());

				/**
				 * 商户类型
				 */
				product.setSellerType(commission.getString("type"));
				products.add(product);
			}
		}
		return products;
	}

	/**
	 * 
	 * 方法: initSkuList <br>
	 * 描述: 获取需要推送的sku信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月25日 下午5:31:19
	 * 
	 * @param productCode
	 * @param commission
	 * @return
	 */
	public List<PcSkuInfo> initSkuList(String productCode, Double commission) {
		List<PcSkuinfo> skuInfos = skuInfoDao.findSkuByProductCode(productCode);
		/**
		 * 商品的Sku列表的属性信息
		 */
		List<PcSkuInfo> skuList = new ArrayList<PcSkuInfo>();
		for (int i = 0; i < skuInfos.size(); i++) {
			PcSkuinfo skuInfo = skuInfos.get(i);
			PcSkuInfo sku = new PcSkuInfo();
			/**
			 * sku编码
			 */
			sku.setSkuCode(skuInfo.getSkuCode());
			/**
			 * 商品的sku名称信息
			 */
			sku.setSkuName(skuInfo.getSkuName());
			/**
			 * 销售价
			 */
			sku.setSellPrice(
					skuInfo.getSellPrice().add(skuInfo.getSellPrice().multiply(BigDecimal.valueOf(commission))));
			/**
			 * 库存数
			 */
			sku.setStockNum(skuInfo.getStockNum());
			/**
			 * 商品的Sku的图片信息
			 */
			sku.setSkuPicUrl(skuInfo.getSkuPicurl());
			/**
			 * 广告语
			 */
			sku.setSkuAdv(skuInfo.getSkuAdv());
			/**
			 * 最小购买数
			 */
			sku.setMiniOrder(skuInfo.getMiniOrder());

			sku.setSkuKey(skuInfo.getSkuKey());

			sku.setSkuKeyvalue(skuInfo.getSkuKeyvalue());
			skuList.add(sku);
		}
		return skuList;
	}

	/**
	 * 
	 * 方法: pushSkuStock <br>
	 * 描述: TODO
	 * 
	 * @param seller
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.hjy.service.product.IApiProductService#pushSkuStock(com.hjy.entity.webcore.WcSellerinfo,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public JSONObject pushSkuStock(CacheWcSellerInfo seller, String productCodes) {
		JSONObject response = new JSONObject();
		String lock = "";
		try {
			lock = WebHelper.getInstance().addLock(10, seller.getSellerCode() + "_Product.pushSkuStock");
			/**
			 * 根据productCode读取sku库存
			 */
			JSONArray array = new JSONArray();
			if (productCodes != null && !"".equals(productCodes) && productCodes.split(",").length > 0) {
				List<String> codes = Arrays.asList(productCodes.split(","));
				List<Map<String, Object>> skuList = skuInfoDao.findSkuDataByProductCode(codes);
				for (int i = 0; i < codes.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					String productCode = codes.get(i);
					JSONArray productSku = new JSONArray();
					if (skuList != null && skuList.size() > 0) {
						// 遍历SKU信息集合
						for (int j = 0; j < skuList.size(); j++) {
							Map<String, Object> sku = skuList.get(j);
							// 如果sku的productCode与productCode值相同存储到集合表
							if (sku.get("product_code").toString().equals(productCode)) {
								JSONObject obj = new JSONObject();
								obj.put("skuCode", sku.get("sku_code").toString());
								obj.put("stockNum", sku.get("stock_num").toString());
								productSku.add(obj);
							}
						}
					}
					// 存储到map中格式为 productCode:skuStock
					map.put("productCode", productCode);
					map.put("skuList", productSku);
					array.add(map);
				}
				response.put("code", 0);
				response.put("data", array);
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
		return response;
	}

	/**
	 * 
	 * 方法: pushProductPrice <br>
	 * 描述: TODO
	 * 
	 * @param seller
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.hjy.service.product.IApiProductService#pushProductPrice(com.hjy.entity.webcore.WcSellerinfo,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public JSONObject pushProductPrice(CacheWcSellerInfo seller, String productCodes) {
		JSONObject response = new JSONObject();
		String lock = "";
		try {
			lock = WebHelper.getInstance().addLock(10, seller.getSellerCode() + "_Product.pushProductPrice");
			/**
			 * 根据productCode读取sku价格
			 */
			JSONArray array = new JSONArray();
			if (productCodes != null && !"".equals(productCodes) && productCodes.split(",").length > 0) {
				List<String> codes = Arrays.asList(productCodes.split(","));
				List<Map<String, Object>> skuList = skuInfoDao.findSkuPriceByProducts(codes);
				for (int i = 0; i < codes.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					String productCode = codes.get(i);
					JSONArray productSku = new JSONArray();
					if (skuList != null && skuList.size() > 0) {
						// 遍历SKU信息集合
						for (int j = 0; j < skuList.size(); j++) {
							Map<String, Object> sku = skuList.get(j);
							// 如果sku的productCode与productCode值相同存储到集合表
							if (sku.get("product_code").toString().equals(productCode)) {
								JSONObject obj = new JSONObject();
								obj.put("skuCode", sku.get("sku_code").toString());
								// 根据类型获取商户佣金，计算价格
								JSONArray commissions = JSONArray.parseArray(seller.getCommission());
								for (int k = 0; k < commissions.size(); k++) {
									JSONObject c = commissions.getJSONObject(k);
									double commission = 0;
									double price = 0;
									// 如果是LD商品
									if (c.getString("type").equals("LD")
											&& sku.get("small_seller_code").toString().equals("SI2003")) {
										commission = c.getDoubleValue("commission");
									} else if (sku.get("uc_seller_type") != null && c.getString("type").toString()
											.equals(sku.get("uc_seller_type").toString())) {
										commission = c.getDoubleValue("commission");
									}
									// 0 成本价 1 销售价
									if (seller.getPriceType() == 0) {
										price = Double.valueOf(sku.get("cost_price").toString()) * (1 + commission);
									} else {
										price = Double.valueOf(sku.get("sell_price").toString()) * (1 + commission);
									}
									obj.put("sellPrice", price);
								}
								productSku.add(obj);
							}
						}
					}
					// 存储到map中格式为 productCode:skuStock
					map.put("productCode", productCode);
					map.put("skuList", productSku);
					array.add(map);
				}
				response.put("code", 0);
				response.put("data", array);
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
		return response;
	}

	/**
	 * @description: 批量返回时间段内商品上下架状态有变化的商品信息 update_tim between '2016-10-01
	 *               15:00:00' and '2016-10-01 16:00:00'
	 * 
	 * @说明 惠家有：商户；第三方：销售平台。惠家有将有变化的商品的上下架状态信息返回给商户
	 * 
	 * @接口标识 Product.RsyncProductStatus。
	 * 
	 * @param seller
	 * @param productCodes
	 * @return list
	 * @author Yangcl
	 * @date 2016年9月30日 上午11:29:46
	 * @version 1.0.0.1
	 */
	public JSONObject rsyncProductStatus(String json, CacheWcSellerInfo seller) {
		JSONObject response = new JSONObject();
		String responseTime = DateHelper.formatDate(new Date());
		response.put("responseTime", responseTime);
		String lockCode = WebHelper.getInstance().addLock(1000, seller.getSellerCode() + "@com.hjy.service.impl.product.ApiProductServiceImpl.rsyncProductStatus"); // 分布式锁定
		if (StringUtils.isNotBlank(lockCode)) {
			if (!seller.getStatus().equals(1)) {
				response.put("code", 0);
				response.put("desc", "非法的商户合作状态，未开通或已禁用");
				return response;
			}
			String pcode = ""; // 商品编号
			Map<String, String> map = new HashMap<String, String>();
			if (StringUtils.isBlank(json)) {
				Date date = new Date();
				map.put("startTime", this.getHour(date, -1));
				map.put("endTime", this.getHour(date, 0));
			} else {
				JSONObject o = null;
				Date date = new Date();
				try {
					o = JSONObject.parseObject(json);
					String s = o.getString("startTime");
					String e = o.getString("endTime");
					
					if(StringUtils.isNotBlank(o.getString("productCode"))){
						pcode = o.getString("productCode");
					}
					if (StringUtils.isAnyBlank(s, e)) {
						map.put("startTime", this.getHour(date, -1));
						map.put("endTime", this.getHour(date, 0));
					} else {
						if (s.compareTo(e) < 0) {
							map.put("startTime", s);
							map.put("endTime", e);
						} else {
							response.put("code", 0);
							response.put("desc", "开始时间需要小于结束时间");
							return response;
						}
					}
				} catch (Exception e) {
					map.put("startTime", this.getHour(date, -1));
					map.put("endTime", this.getHour(date, 0));
				}
			}
			map.put("pcode", pcode);

			try {
				List<ProductStatus> list = productInfoDao.selectProductByUpdateTime(map);
				if (list != null && list.size() != 0) {
					response.put("code", 1);
					response.put("desc", "SUCCESS");
					response.put("data", list);
				} else {
					response.put("code", 0);
					response.put("desc", "没有查询到状态变化的商品");
				}
			} catch (Exception ex) {
				response.put("code", 0);
				response.put("desc", "平台内部错误，查询失败");

				String remark_ = "{" + ExceptionHelper.allExceptionInformation(ex) + "}"; // 记录异常信息到数据库表
				logger.error("查询订单状态信息异常|", ex);
				openApiQueryDao.insertSelective(new LcOpenApiQueryLog(UUID.randomUUID().toString().replace("-", ""),
						seller.getSellerCode(), "Product.RsyncProductStatus",
						"com.hjy.service.impl.product.ApiProductServiceImpl.rsyncProductStatus", new Date(), 2, "",
						response.toJSONString(), remark_));
				return response;
			} finally {
				WebHelper.getInstance().unLock(lockCode);
			}
		}

		openApiQueryDao.insertSelective(new LcOpenApiQueryLog(UUID.randomUUID().toString().replace("-", ""),
				seller.getSellerCode(), "Product.RsyncProductStatus",
				"com.hjy.service.impl.product.ApiProductServiceImpl.rsyncProductStatus", new Date(), 1, "",
				response.toJSONString(), "query success"));
		return response;
	}

	/**
	 * 根据商品编码数组查询<br>
	 * 2016-10-20 zhy<br>
	 * 
	 * @param codes
	 * @return
	 */
	@Override
	public JSONObject findProductByProductCodes(CacheWcSellerInfo seller, String data) {
		JSONObject response = new JSONObject();
		List<ProductInfo> responseProduct = new ArrayList<ProductInfo>();
		if (StringUtils.isNotBlank(data)) {
			String lock = "";
			try {
				lock = WebHelper.getInstance().addLock(10 , seller.getSellerCode() + "_Product.findProductByProductCodes");
				JSONObject jsonData = JSON.parseObject(data);
				String codes = jsonData.getString("codes");
				// 读取合作商的产品获取权限
				if (seller.getCommission() != null && !"".equals(seller.getCommission())) {
					JSONArray commissions = JSONArray.parseArray(seller.getCommission());
					// 获取参数
					for (int i = 0; i < commissions.size(); i++) {
						JSONObject c = commissions.getJSONObject(i);
						ProductInfo dto = new ProductInfo();
						dto.setCodes(Arrays.asList(codes.split(",")));
						if ("LD".equals(c.getString("type"))) {
							dto.setLD("LD");
						} else {
							dto.setSellerType(c.getString("type"));
						}
						List<PcProductinfo> list = productInfoDao.findProductByProductCodes(dto);
						List<ProductInfo> products = initPcProduct(list, c, seller.getPriceType());
						if (products != null && products.size() > 0) {
							for(int n = 0 ; n < products.size() ; n ++){
								String pcode =  products.get(i).getProductCode();
								List<Property> prolist = productInfoDao.getProductPropertyByCode(pcode);
								products.get(i).setPropertys(prolist); 
							}
							responseProduct.addAll(products);
						}
					}
					response.put("code", 0);
					response.put("desc", getInfo(0));
				} else {
					response.put("code", 10);
					response.put("desc", "商家无访问权限");
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.put("code", 10);
				response.put("desc", "系统错误，请联系技术人员");
			} finally {
				WebHelper.getInstance().unLock(lock);
			}
		} else {
			response.put("code", -1);
			response.put("desc", "请求参数错误，请检查请求参数");
		}
		/**
		 * 生成响应报文
		 */
		response.put("data", responseProduct);
		response.put("total", responseProduct.size());
		return response;
	}

	private String getHour(Date date, int flag) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.HOUR, flag);
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:00:00");

		return formatter.format(date);
	}

}

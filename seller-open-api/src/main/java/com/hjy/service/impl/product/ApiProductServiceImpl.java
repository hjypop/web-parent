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
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.ExculdeNullField;
import com.hjy.annotation.TargetField;
import com.hjy.common.DateUtil;
import com.hjy.common.product.SkuCommon;
import com.hjy.constant.MemberConst;
import com.hjy.dao.IApiProductInfoDao;
import com.hjy.dao.IApiSkuInfoDao;
import com.hjy.dao.ILcOpenApiProductErrorDao;
import com.hjy.dao.ILcOpenApiQueryLogDao;
import com.hjy.dao.log.ILcStockchangeDao;
import com.hjy.dao.product.IPcBrandinfoDao;
import com.hjy.dao.product.IPcProductcategoryRelDao;
import com.hjy.dao.product.IPcProductdescriptionDao;
import com.hjy.dao.product.IPcProductflowDao;
import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.dao.product.IPcProductinfoExtDao;
import com.hjy.dao.product.IPcProductpicDao;
import com.hjy.dao.product.IPcProductpropertyDao;
import com.hjy.dao.product.IPcSkuinfoDao;
import com.hjy.dao.system.IScStoreSkunumDao;
import com.hjy.dao.user.IUcSellercategoryProductRelationDao;
import com.hjy.dto.product.ApiProductDesc;
import com.hjy.dto.product.ApiSellerProduct;
import com.hjy.dto.product.ApiSellerSkuInfo;
import com.hjy.dto.product.PcSkuInfo;
import com.hjy.dto.product.ProductInfo;
import com.hjy.dto.product.ProductStatus;
import com.hjy.dto.product.Property;
import com.hjy.entity.log.LcOpenApiProductError;
import com.hjy.entity.log.LcOpenApiQueryLog;
import com.hjy.entity.log.LcStockchange;
import com.hjy.entity.product.ApiPcProductInfo;
import com.hjy.entity.product.PcProductcategoryRel;
import com.hjy.entity.product.PcProductdescription;
import com.hjy.entity.product.PcProductflow;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcProductinfoExt;
import com.hjy.entity.product.PcProductpic;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.factory.UserFactory;
import com.hjy.helper.DateHelper;
import com.hjy.helper.ExceptionHelper;
import com.hjy.helper.WebHelper;
import com.hjy.jms.ProductJmsSupport;
import com.hjy.model.ProductSkuInfo;
import com.hjy.redis.core.RedisLaunch;
import com.hjy.redis.srnpr.ERedisSchema;
import com.hjy.request.RequestProduct;
import com.hjy.request.RequestProducts;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.product.IApiProductService;
import com.hjy.system.cmodel.CacheWcSellerInfo;
 
/**
 * @description: openapi商品业务处理接口实现类
 * 
 * 	TODO requestConvertion()  方法的如下两个地方还没有完成，等待上一层的修改
 * 						    ext.setSettlementType(seller.getSettlement());	// 服务费结算方式    TODO 此处需要从缓存中取得信息 但此功能尚未实现 
 * 							ext.setPurchaseType(seller.getPurchase());     //  TODO 此处需要从缓存中取得信息 但此功能尚未实现
 * 
 * 	TODO 准备删除 LcOpenApiProductError相关的实体、配置文件、dao、Service 以及 数据库中的表
 * 				 新加入了商品的日志表：lc_open_api_seller_product_operations，该表专门记录所有惠家有商户同步商品的信息
 * 
 * @author Yangcl
 * @date 2017年1月5日 下午7:15:38 
 * @version 1.0.0
 */
@Service
public class ApiProductServiceImpl extends BaseServiceImpl<PcProductinfo, Integer> implements IApiProductService {
	
	private static Logger logger = Logger.getLogger(ApiProductServiceImpl.class);   
	
	@Resource
	private IPcProductflowDao pcpFlowdao;
	@Resource
	private IPcProductinfoDao pcProductInfoDao;
	@Resource
	private IPcProductcategoryRelDao pcProductcategoryRelDao;
	@Resource
	private IPcProductdescriptionDao pcProductdescriptionDao;
	@Resource
	private IPcProductpicDao pcProductpicDao;
	@Resource
	private IPcProductpropertyDao pcProductpropertyDao;
	@Resource
	private IPcSkuinfoDao pcSkuinfoDao;
	@Resource
	private IPcBrandinfoDao pcBrandinfoDao;
	@Resource
	private IPcProductinfoExtDao pcProductinfoExtDao;
	@Resource
	private IUcSellercategoryProductRelationDao ucSellercategoryProductRelationDao;
	@Resource
	private IScStoreSkunumDao scStoreSkunumDao;
	@Resource
	private ILcStockchangeDao lcStockchangeDao;
	
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
		JSONArray ra = new JSONArray();   // 这里存放具体的操作结果，商品同步成功和失败的信息 
		String productHead = this.getConfig("seller_adapter.product_" + seller.getSellerType()); 
		String skuHead = this.getConfig("seller_adapter.sku_" + seller.getSellerType()); ;
		if(StringUtils.isNotBlank(products)){
			String lock  = WebHelper.getInstance().addLock(180 , sellerCode + "@Product.SyncSellerProductList");  // 三分钟内不得访问 
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
						List<ApiSellerProduct> rlist = new ArrayList<ApiSellerProduct>();  // 存放正确的数据  
						List<String> errors = new ArrayList<String>();      // 作为结果返回给商户           
						for(ApiSellerProduct p : plist){
							JSONObject vali = this.entityValidate(p , skuHead);  // 验证数据合法性
							if( !vali.getBoolean("flag") ){
								errors.add(p.getSellerProductCode() + "@" + vali.getString("desc"));
								continue;
							}
							rlist.add(p);  
						}
						
						List<PcProductinfo> targetList = this.requestConvertion(rlist, seller, productHead, skuHead); 
						for(PcProductinfo e : targetList){
							if(!e.getIsUpdate()){  // 准备添加一条记录 
								ra.add(this.insertSellerProduct(e)); 
							}else{	   
								ra.add(this.updateSellerProduct(e)); 
								// 删掉缓存中的商品信息 
								this.redisDeleteProductInfo(e.getProductCode()); 
							}
						}
					}
				} catch (Exception e) {
					result.put("code", 3);
					result.put("desc", this.getInfo(100009003));  // 请求参数错误，请求数据解析异常
//					return result; 
				}finally{
					WebHelper.getInstance().unLock(lock);
				}
			}else{
				result.put("code", 0);
				result.put("desc", this.getInfo(100009002));  // 分布式锁生效中
			} 
		}else{
			result.put("code", -1);
			result.put("desc", this.getInfo(100009001));  // 请求数据报文data为空
		}
		
		
		return result;
	}
	
	
	/**
	 * @description: 数据合法性验证|syncSellerProductList接口专属验证方法。
	 * 
	 * @author Yangcl 
	 * @date 2016年12月30日 下午3:11:57 
	 * @version 1.0.0.1
	 */
	private  JSONObject entityValidate(ApiSellerProduct p , String skuHead){
		JSONObject result = new JSONObject();
		result.put("flag", false);
		if(StringUtils.isAnyBlank(p.getSellerProductCode(),
			p.getProductName() , p.getProductShortname(),
			p.getCostPrice().toString(), p.getMarketPrice().toString() , 
			p.getProductWeight().toString() , p.getMainpicUrl() , p.getProductVolumeItem(),
			p.getProductVolume().toString() , p.getExpiryDate().toString() , p.getExpiryUnit() ,
			p.getTaxes().toString() , p.getLabels() )){
			result.put("desc", this.getInfo(100009005));  // 请求参数体中包含不合法的字段
			return result;
		}
		if(Double.valueOf(p.getCostPrice().toString()) <= 0){
			result.put("desc", this.getInfo(100009015));  // 100009015=非法的商品成本价
			return result;
		}
		if(Double.valueOf(p.getMarketPrice().toString()) <= 0){
			result.put("desc", this.getInfo(100009015));  // 100009015=非法的商品成本价
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
		if(Double.valueOf(p.getTaxes().toString()) >= 1){
			result.put("desc", this.getInfo(100009014));  // 100009014=非法的商品税率值
			return result;
		}
		if(p.getProductPictures() == null || p.getProductPictures().size() == 0){
			result.put("desc", this.getInfo(100009017));  // 100009017=商品轮播图列表不得为空
			return result;
		}
		if(p.getProductDescribe() == null || p.getProductDescribe().size() == 0){
			result.put("desc", this.getInfo(100009018));  // 100009018=商品描述图列表不得为空
			return result;
		}
		
		// 开始判定sku相关信息 
		if(p.getSkuList() == null || p.getSkuList().size() == 0){
			result.put("desc", this.getInfo(100009009));  // 商品的Sku列表不得为空
			return result;
		}
		Map<String , String> vmap = new HashMap<String , String>(); // 用于验证 规格属性+颜色属性，sku中这两个属性不得重复。 
		List<ApiSellerSkuInfo> skus = p.getSkuList();
		for(ApiSellerSkuInfo s : skus){
			if(StringUtils.isAnyBlank(s.getSellPrice().toString(),
					s.getCostPrice().toString(), s.getStockNum().toString(),
					s.getSkuPicUrl() , s.getSkuName() , s.getSkuAdv() , 
					s.getSecurityStockNum().toString() , s.getMiniOrder().toString(),
					s.getSaleYn() , s.getFlagEnable().toString() , s.getSpecification() , s.getColor())){
				result.put("desc", this.getInfo(100009010));  // 商品的Sku关键信息不得为空
				return result;
			}
			vmap.put(StringUtils.trim(s.getColor()) + StringUtils.trim(s.getSpecification()), "validate");   
			if(StringUtils.isNotBlank(s.getSkuCode()) && !StringUtils.startsWith(s.getSkuCode(), skuHead)){
				result.put("desc", this.getInfo(100009021));  // 100009021=非法的sku编号前缀
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
			if( !(s.getSaleYn().equals("Y") || s.getSaleYn().equals("N")) ){
				result.put("desc", this.getInfo(100009019));  // 100009019=非法的可卖状态标识，saleYn: Y可卖/N不可卖
				return result;
			}
			if( !(s.getFlagEnable() == 0 || s.getFlagEnable() == 1)){
				result.put("desc", this.getInfo(100009020));  // 100009020=非法的可用状态标识，flagEnable: 0不可用/1可用
				return result;
			}
		}
		
		if(vmap.size() != skus.size()){
			result.put("desc", this.getInfo(100009022));  // 100009022=sku规格属性不得重复！
			return result;
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

	/**
	 * @descriptions  验证对象中的值是否合法并为E e 对象赋值
	 * 
	 * T 与 E 两个对象字段名称需要一致。比如 T 中拥有 E 中一半的字段，但这一半字段名称类型都相同。
	 *  
	 * @param t 要验证的对象
	 * @param e 要赋值的对象|如果要赋值的对象为 null 则只验证，不赋值
	 * @return E
	 * @date 2016年8月29日下午12:10:38
	 * @author Yangcl 
	 * @version 1.0.0.1
	 * @param <T , E>
	 */
	private  <T , E> E validate(T t , E e){ 
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
		 
		return e;
	}
	
 
	/**
	 * @descriptions 商品信息转换|syncSellerProductList接口专属转换方法。 
	 *
	 * @param list
	 * @param seller
	 * @param phead productHead
	 * @param shead skuHead
	 * @date 2017年1月2日 下午5:27:58
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	private  List<PcProductinfo> requestConvertion(List<ApiSellerProduct> list , CacheWcSellerInfo seller , String phead , String shead){
		List<PcProductinfo> list_ = new ArrayList<PcProductinfo>();
		for(ApiSellerProduct p : list){
			String uid = UUID.randomUUID().toString().replace("-", "");
			PcProductinfo e = new PcProductinfo();
			e.setProductCodeOld(seller.getSellerCode() + "-" + p.getSellerProductCode());  // 添加标示头，用于区分可能存在的编号
			e.setSellerCode(MemberConst.MANAGE_CODE_HOMEHAS);  // SI2003
			List<PcProductinfo> pList = pcProductInfoDao.getListBySellerCode(e);
			if (pList == null || pList.size() == 0) { // 若果不存在，就添加
				e.setUid(uid); 
				e.setProductCode(WebHelper.getInstance().genUniqueCode(phead));
				e.setIsUpdate(false);  	// 标志位 用于区分更新还是添加 
			}else{
				e.setUid(pList.get(0).getUid()); // 根据uid更新商品
				e.setProductCode(pList.get(0).getProductCode());   
				e.setIsUpdate(true);  
			}
			
			e.setProductName(p.getProductName());
			e.setProductShortname(p.getProductShortname());
			e.setMaxSellPrice(p.getMarketPrice());
			e.setMinSellPrice(p.getCostPrice());
			e.setCostPrice(p.getCostPrice());
			e.setMarketPrice(p.getMarketPrice());
			e.setMainpicUrl(p.getProductPictures().get(0));  // 主图默认为轮播图的第一张
			e.setSmallSellerCode(seller.getSellerCode()); 
			e.setProductStatus("4497153900060003");// 商品下架
			e.setValidate_flag("Y");//是否是虚拟商品
			e.setTaxRate(p.getTaxes());
			e.setProductWeight(p.getProductWeight());
			e.setTransportTemplate("0");  // 运费模板默认为卖家包邮
			e.setLabels(p.getLabels());
			
			// 设置商品描述
			PcProductdescription description = new PcProductdescription();
			description.setUid(uid);
			description.setProductCode(e.getProductCode());
			String img = "";	// <img src="http://www.minspc.com/webupload/welfare/T130979285971471323.jpg"/><br/>
			String img_ = "";
			List<String> picList = p.getProductDescribe(); // 商品描述图片列表
			for(String s : picList){
				img += "<img src='" + s + "'/><br/>";
				img_ += s + "|";
			}
			img_ = img_.substring(0, img_.length()-1);
			description.setDescriptionInfo(img);
			description.setDescriptionPic(img_);
			description.setKeyword(p.getLabels());
			e.setDescription(description);
			
			
			/*
			 * 根据product_code 找出该商品下所有的Sku信息，
			 * 以skuKey+"@"+skuValue 作为map的key，sku_code 作为value
			 * map中的key是区分一个product_code下的唯一依据  
			 */
			Map<String , String> skmap = new HashMap<String , String>();  
			if(e.getIsUpdate()){    // 如果是更新商品，则验证sku的规格属性信息
				List<PcSkuinfo>  mapList = pcSkuinfoDao.getSkuinfoByPcode(e.getProductCode());  
				if(mapList != null && mapList.size() != 0){
					for(PcSkuinfo s : mapList	){
						skmap.put(s.getSkuKey() , s.getSkuCode());
					}
				}
			}
			
			// 设置Sku信息。
			List<ApiSellerSkuInfo> slist = p.getSkuList();
			List<ProductSkuInfo> skuInfoList = new ArrayList<ProductSkuInfo>();
			for(int k = 0 ; k < slist.size() ; k ++){
				ApiSellerSkuInfo s = slist.get(k);
				ProductSkuInfo i = new ProductSkuInfo();
				// sku规格属性信息 参看：PlusSupportProduct.propertyListSku() 这个方法 
				i.setSkuKey("color_id=" + k + "&style_id=" + k);  
				i.setSkuValue("颜色属性=" + StringUtils.trimToEmpty(s.getColor()) + "&规格属性=" + StringUtils.trimToEmpty(s.getSpecification()) );  
				
				if(!e.getIsUpdate()){  // 如果是添加商品 则直接为sku生成一个code
					i.setSkuCode(WebHelper.getInstance().genUniqueCode(shead)); 
				}else{   
					/** 更新商品信息分为2种情况
					 * 		1. 更新已经存在的sku信息
					 * 		2. 在原有sku信息的基础上增加一条sku信息 
					 */
					if(skmap.containsKey(i.getSkuKey() + "@" + i.getSkuKeyvalue())){  // 以sku的规格属性确定该商品是否包含这条sku，不包含则生成一个sku_code
						i.setSkuCode( skmap.get(i.getSkuKey() + "@" + i.getSkuKeyvalue()) );  
					}else{
						i.setSkuCode(WebHelper.getInstance().genUniqueCode(shead)); 
					}
				}
				
				i.setProductCode(e.getProductCode());
				i.setSellPrice(s.getSellPrice());
				i.setMarketPrice(p.getMarketPrice());
				i.setCostPrice(s.getCostPrice());     // 设置sku的成本价
				i.setSkuName(s.getSkuName());
				i.setSkuPicUrl(s.getSkuPicUrl());   // 商品的Sku的图片信息
				i.setSellProductcode(p.getSellerProductCode());    // 设置外部商品 id 
				i.setStockNum(Integer.valueOf(s.getStockNum().toString()));   
				i.setSecurityStockNum(Integer.valueOf(s.getSecurityStockNum().toString()));  // 商品库存
				i.setSaleYn(s.getSaleYn());
				i.setFlagEnable(s.getFlagEnable().toString());
				i.setSellerCode(MemberConst.MANAGE_CODE_HOMEHAS);  // SI2003   
				skuInfoList.add(i);
			}
			e.setProductSkuInfoList(skuInfoList);  
			
			// 设置商品轮播图  经过确定，惠家有库中并不是每个sku都有一个轮播图，轮播图只绑定于product - Yangcl
			List<PcProductpic> lunBoList = new ArrayList<PcProductpic>();
			for(String url : p.getProductPictures()){
				PcProductpic pic = new PcProductpic();
				pic.setUid(UUID.randomUUID().toString().replace("-", ""));
				pic.setPicUrl(url);
				pic.setProductCode(e.getProductCode());
				pic.setSkuCode(" "); // 所以这里设置为空 
				lunBoList.add(pic);
			}
			e.setPcPicList(lunBoList);
			
			// 设置商品扩展信息
			PcProductinfoExt ext = new PcProductinfoExt();// 设置扩展信息
			ext.setProductCodeOld(e.getProductCodeOld());
			ext.setProductCode(e.getProductCode());
			ext.setPrchType("10");  // 异地入库类型
			ext.setDlrId( seller.getSellerCode() );  // 供应商编号
			ext.setDlrNm(seller.getSellerName());  // 供应商名称
			ext.setValidateFlag("Y"); // 是否是虚拟商品  Y：是  N：否 
			ext.setPoffer("open-api-platform");
			ext.setSettlementType(seller.getSettlement());	// 服务费结算方式    TODO 此处需要从缓存中取得信息 但此功能尚未实现!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			ext.setPurchaseType(seller.getPurchase());     //  TODO 此处需要从缓存中取得信息 但此功能尚未实现
			e.setPcProductinfoExt(ext);
			
			// 商品变更状态
			PcProductflow pf = new PcProductflow();
			pf.setFlowCode(WebHelper.getInstance().genUniqueCode("PF"));
			pf.setFlowStatus(SkuCommon.ProAddOr);
			pf.setProductCode(e.getProductCode()); 
			e.setPcProdcutflow(pf);
			
			list_.add(e);
		}
		return list_;
	}
	
	/**
	 * @description: 插入商户商品信息到数据库
	 *  
	 * @author Yangcl 
	 * @date 2017年1月3日 下午2:32:07 
	 * @version 1.0.0.1
	 */
	private JSONObject insertSellerProduct(PcProductinfo e){
		JSONObject result = new JSONObject();
		result.put("type", "insert");
		result.put("code", "1"); // 默认插入成功
		result.put("entity", e);  
		String createTime = DateUtil.getSysDateTimeString();
		try {
			// 插入商品基本信息
			PcProductinfo p = new PcProductinfo();
			p.setUid(e.getUid());
			p.setCreateTime(createTime);
			p.setBrandCode("");
			p.setFlagPayway(0);  // 是否货到付款 0 否 1 是|经过验证，pc_productinfo都是0 - Yangcl 
			p.setFlagSale(e.getFlagSale()); 
			p.setLabels(e.getLabels());
			p.setMainpicUrl(e.getMainpicUrl());   
			p.setMarketPrice(e.getMarketPrice());
			p.setMaxSellPrice(e.getMarketPrice());
			p.setMinSellPrice(e.getCostPrice());
			p.setProductCode(e.getProductCode());
			p.setProductCodeOld(e.getProductCodeOld());  // 只有LD商品没有外部商品编号  
			p.setProductName(e.getProdutName());  
			p.setProductStatus(e.getProductStatus());
			p.setProductVolume(e.getProductVolume());
			p.setProductVolumeItem(e.getProductVolumeItem());
			p.setProductWeight(e.getProductWeight());
			p.setSellerCode(e.getSellerCode());
			p.setSmallSellerCode(e.getSmallSellerCode());
			p.setTransportTemplate(e.getTransportTemplate());
			p.setUpdateTime(createTime);
			p.setCostPrice(e.getCostPrice());
			p.setProductShortname(e.getProductShortname());
			p.setValidate_flag(e.getValidate_flag());// 添加是否是虚拟商品字段
			p.setTaxRate(e.getTaxRate());
			p.setExpiryDate(e.getExpiryDate());// 保质期
			p.setExpiryUnit(e.getExpiryUnit());// 保质期单位
//			p.setQualificationCategoryCode(e.getQualificationCategoryCode());		// 资质品类 由运营维护 
			pcProductInfoDao.insertSelective(p); 
			
			// 添加商品的实类信息
			PcProductcategoryRel pprModel = new PcProductcategoryRel();
			pprModel.setUid(UUID.randomUUID().toString().replace("-", ""));
			pprModel.setProductCode(e.getProductCode());	
			pprModel.setFlagMain(Long.parseLong(1 + ""));
			pcProductcategoryRelDao.insertSelective(pprModel);
			
			// 添加 描述信息
			PcProductdescription ppdModel = new PcProductdescription();
			ppdModel.setUid(UUID.randomUUID().toString().replace("-", ""));
			ppdModel.setProductCode(e.getProductCode());
			ppdModel.setKeyword(e.getDescription().getKeyword());
			ppdModel.setDescriptionPic(e.getDescription().getDescriptionPic());
			ppdModel.setDescriptionInfo(e.getDescription().getDescriptionInfo()); 
			pcProductdescriptionDao.insertSelective(ppdModel);			
			
			// 插入商品轮播图
			List<PcProductpic> picList = e.getPcPicList();
			for (PcProductpic pic : picList) {
				pcProductpicDao.insertSelective(pic);
			}
			
			// 插入sku信息
			List<ProductSkuInfo> skuList = e.getProductSkuInfoList();
			for (ProductSkuInfo sku : skuList) {
				PcSkuinfo si = new PcSkuinfo();
				si.setUid(UUID.randomUUID().toString().replace("-", ""));  
				si.setMarketPrice(sku.getMarketPrice());
				si.setProductCode(e.getProductCode());
				si.setProductCodeOld(e.getProductCodeOld());
				si.setQrcodeLink(sku.getQrcodeLink());
				si.setSecurityStockNum(Long.valueOf(sku.getSecurityStockNum()));
				si.setSellerCode(e.getSellerCode());
				si.setSellPrice(sku.getSellPrice());
				si.setSellProductcode(e.getProductCode());   // 历史遗留问题，无从追溯。数据库表就这么定义的。 - Yangcl  
				si.setSkuCode(sku.getSkuCode());
//				si.setSkuCodeOld(sku.getSkuCodeOld());
				si.setSkuKey(sku.getSkuKey());
				si.setSkuKeyvalue(sku.getSkuValue());
				si.setSkuPicurl(sku.getSkuPicUrl());
				si.setSkuName(sku.getSkuName());
				si.setSkuAdv(sku.getSkuAdv()); 
				si.setStockNum(Long.valueOf(sku.getStockNum()));    
				si.setSaleYn(sku.getSaleYn());
				si.setCostPrice(sku.getCostPrice());
				si.setMiniOrder(sku.getMiniOrder());
				pcSkuinfoDao.insertSelective(si);

				// 插入商品sku库存
				ScStoreSkunum sssModel = new ScStoreSkunum();
				sssModel.setUid(UUID.randomUUID().toString().replace("-", ""));
				sssModel.setSkuCode(sku.getSkuCode());
				sssModel.setStockNum(Long.valueOf(sku.getStockNum()));
				sssModel.setStoreCode("TDS1");   // TDS1即第三方仓库，麦乐购使用此库存编号，此处也使用
				sssModel.setBatchCode("");
				scStoreSkunumDao.insertSelective(sssModel);
			}
			
			// 插入商品属性信息。 
//			PcProductproperty ppp = new PcProductproperty(); 
//			pcProductpropertyDao.insertSelective(ppp);
			
			// 插入商品扩展信息
			PcProductinfoExt ppe = new PcProductinfoExt();
			ppe.setUid(UUID.randomUUID().toString().replace("-", ""));
			ppe.setProductCode(e.getPcProductinfoExt().getProductCode());
			ppe.setPrchType(e.getPcProductinfoExt().getPrchType());
			ppe.setDlrId(e.getPcProductinfoExt().getDlrId());
			ppe.setDlrNm(e.getPcProductinfoExt().getDlrNm());
//			ppe.setOaSiteNo(e.getPcProductinfoExt().getOaSiteNo());
			ppe.setValidateFlag(e.getPcProductinfoExt().getValidateFlag());
			ppe.setProductCodeOld(e.getProductCodeOld());
//			ppe.setProductStoreType(e.getPcProductinfoExt().getProductStoreType());
//			ppe.setProductTradeType(e.getPcProductinfoExt().getProductTradeType());
			ppe.setPoffer(e.getPcProductinfoExt().getPoffer());
			Integer fictitious = e.getPcProductinfoExt().getFictitiousSales();  // 虚拟销售量基数
			if (fictitious == null ||StringUtils.isBlank(fictitious.toString())) {
				fictitious = 0;
			}
			ppe.setFictitiousSales(fictitious);
			String grossProfit ="";       // 毛利润
			if(null != e.getPcProductinfoExt().getGrossProfit()){
				grossProfit = e.getPcProductinfoExt().getGrossProfit().toString();
			}
			ppe.setGrossProfit(Long.parseLong((StringUtils.isBlank(grossProfit) ? "0" : grossProfit)));
			String accmrng = "";   // 积分
			if(null != e.getPcProductinfoExt().getAccmRng()){
				accmrng = e.getPcProductinfoExt().getAccmRng().toString();
			}
			ppe.setAccmRng(Double.parseDouble(StringUtils.isBlank(accmrng) ? "0" : accmrng));
			ppe.setMdId("open-api-01"); 
			ppe.setMdNm("open-api-platform");
			ppe.setSettlementType(e.getPcProductinfoExt().getSettlementType());   // 结算方式  常规结算4497471600110001| 特殊结算4497471600110002|服务费结算 4497471600110003
			ppe.setPurchaseType(e.getPcProductinfoExt().getPurchaseType());   // 采购类型 代销 4497471600160001|经销4497471600160002|代收代付4497471600160003
			ppe.setPicMaterialUrl(e.getPcProductinfoExt().getPicMaterialUrl());
			ppe.setPicMaterialUpload(e.getPcProductinfoExt().getPicMaterialUpload());
			pcProductinfoExtDao.insertSelective(ppe);
			
			// 插入商品历史流水信息
			PcProductflow ppf = new PcProductflow();
			ppf.setUid(UUID.randomUUID().toString().replace("-", ""));
			ppf.setCreator("open-api-platform");
			ppf.setFlowCode(e.getPcProdcutflow().getFlowCode());
			ppf.setFlowStatus(e.getPcProdcutflow().getFlowStatus());
			ppf.setProductCode(e.getProductCode());
			ppf.setUpdateTime(createTime);
			ppf.setUpdator("open-api-platform");
			ppf.setProductJson(JSON.toJSONString(e)); 
			pcpFlowdao.insertSelective(ppf);
			
			// 插入商品库存流水
			for (ProductSkuInfo sku : skuList) {
				LcStockchange lsModel = new LcStockchange();
				lsModel.setChangeStock(sku.getStockNum());
				lsModel.setChangeType(SkuCommon.SkuStockChangeTypeCreateProduct);
				lsModel.setCode(sku.getSkuCode());
				lsModel.setCreateTime(createTime);
				lsModel.setCreateUser("open-api-platform");
				lsModel.setUid(UUID.randomUUID().toString().replace("-", ""));
				lcStockchangeDao.insertSelective(lsModel);
			}
			
			ProductJmsSupport pjs = new ProductJmsSupport();
			pjs.onChangeProductText(e.getProductCode());
			this.genarateJmsStaticPageForProduct(e);
		} catch (Exception ex) { 
			String exstring = ExceptionHelper.allExceptionInformation(ex);
			logger.error(exstring);
			result.put("code", "0");  // 操作出现异常
			result.put("exception", exstring); 
			return result;
		}
		return result; 
	}
	
	/**                                    
	 * @description: 更新商户信息到数据库，同时下架已经上架的商品，并且删除Redis中的缓存 
	 *  
	 * @author Yangcl 
	 * @date 2017年1月4日 上午10:39:36 
	 * @version 1.0.0.1
	 */
	private JSONObject updateSellerProduct(PcProductinfo e){
		JSONObject result = new JSONObject();
		result.put("type", "update");
		result.put("code", "1"); // 默认更新成功
		result.put("entity", e);  
		String updateTime = DateUtil.getSysDateTimeString();
		try {
			// 更新商品基本信息
			PcProductinfo pinfo = new PcProductinfo();
			pinfo.setUid(e.getUid()); 
			pinfo.setLabels(e.getLabels());
			pinfo.setMainPicUrl(e.getMainPicUrl());
			pinfo.setCostPrice(e.getCostPrice());
			pinfo.setMarketPrice(e.getMarketPrice());
			pinfo.setMaxSellPrice(e.getMaxSellPrice());
			pinfo.setMinSellPrice(e.getMinSellPrice());
			pinfo.setProductName(e.getProdutName());  
			pinfo.setProductStatus(e.getProductStatus());
			pinfo.setProductWeight(e.getProductWeight());
			pinfo.setUpdateTime(updateTime);
			pinfo.setTaxRate(e.getTaxRate());
			pcProductInfoDao.updateSelective(pinfo); 
			
			
			// 根据product_code更新商品描述信息
			PcProductdescription ppd = new PcProductdescription();
			ppd.setProductCode(e.getProductCode()); 
			ppd.setKeyword(e.getDescription().getKeyword());
			ppd.setDescriptionPic(e.getDescription().getDescriptionPic());
			ppd.setDescriptionInfo(e.getDescription().getDescriptionInfo()); 
			pcProductdescriptionDao.updateSelective(ppd);
			
			// 更新商品轮播图|先删除再添加 
			pcProductpicDao.deleteByProductCode(e.getProductCode());
			List<PcProductpic> picList = e.getPcPicList();
			for (PcProductpic pic : picList) {
				pcProductpicDao.insertSelective(pic);
			}
			
			// 更新商品sku信息|根据product_code删除其下所有的sku信息，然后重新添加
			List<ProductSkuInfo> skuList = e.getProductSkuInfoList();
			for (ProductSkuInfo sku : skuList) {
				// 删除这条sku信息
				Map<String , String > pmap = new HashMap<String , String>();
				pmap.put("scode", sku.getSkuCode());    
				pmap.put("skey", sku.getSkuKey());   
				pmap.put("svalue", sku.getSkuValue());  
				pcSkuinfoDao.deleteSkuinfo(pmap);  
				// 插入 
				PcSkuinfo si = new PcSkuinfo();
				si.setUid(UUID.randomUUID().toString().replace("-", ""));  
				si.setMarketPrice(sku.getMarketPrice());
				si.setProductCode(e.getProductCode());
				si.setProductCodeOld(e.getProductCodeOld());
				si.setQrcodeLink(sku.getQrcodeLink());
				si.setSecurityStockNum(Long.valueOf(sku.getSecurityStockNum()));
				si.setSellerCode(e.getSellerCode());
				si.setSellPrice(sku.getSellPrice());
				si.setSellProductcode(e.getProductCode());   // 历史遗留问题，无从追溯。数据库表就这么定义的。 - Yangcl  
				si.setSkuCode(sku.getSkuCode());
//				si.setSkuCodeOld(sku.getSkuCodeOld());
				si.setSkuKey(sku.getSkuKey());
				si.setSkuKeyvalue(sku.getSkuValue());
				si.setSkuPicurl(sku.getSkuPicUrl());
				si.setSkuName(sku.getSkuName());
				si.setSkuAdv(sku.getSkuAdv()); 
				si.setStockNum(Long.valueOf(sku.getStockNum()));    
				si.setSaleYn(sku.getSaleYn());
				si.setCostPrice(sku.getCostPrice());
				si.setMiniOrder(sku.getMiniOrder());
				pcSkuinfoDao.insertSelective(si);

				// 先删除sku库存，再添加商品sku库存
				scStoreSkunumDao.deleteSkuStore(sku.getSkuCode());   
				// 插入 
				ScStoreSkunum sssModel = new ScStoreSkunum();
				sssModel.setUid(UUID.randomUUID().toString().replace("-", ""));
				sssModel.setSkuCode(sku.getSkuCode());
				sssModel.setStockNum(Long.valueOf(sku.getStockNum()));
				sssModel.setStoreCode("TDS1");   // TDS1即第三方仓库，麦乐购使用此库存编号，此处也使用
				sssModel.setBatchCode("");
				scStoreSkunumDao.insertSelective(sssModel);
			}
		} catch (Exception ex) {
			String exstring = ExceptionHelper.allExceptionInformation(ex);
			logger.error(exstring);
			result.put("code", "0");  // 操作出现异常
			result.put("exception", exstring); 
			return result;
		}
		
		return result;
	}
	
	/**
	 * @description: 生成静态页面 通过商品|
	 * 此方法拷贝自ProductServiceImpl.java -> genarateJmsStaticPageForProduct()
	 * 原作者不详 
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月9日 上午11:41:08 
	 * @version 1.0.0.1
	 */
	private void genarateJmsStaticPageForProduct(PcProductinfo product) {
		ProductJmsSupport pjs = new ProductJmsSupport();
		// 通知前端生成静态页面
		String skuCodes = "";
		if (product.getProductSkuInfoList() != null) {
			int j = product.getProductSkuInfoList().size();
			for (int i = 0; i < j; i++) {
				if (i == (j - 1)) {
					skuCodes += product.getProductSkuInfoList().get(i).getSkuCode();
				} else {
					skuCodes += product.getProductSkuInfoList().get(i).getSkuCode() + ",";
				}
			}
			if (j > 0) {
				String jsonData = "{\"type\":\"sku\",\"data\":\"" + skuCodes + "\"}";
				pjs.OnChangeSku(jsonData);
			}
		}
	}
	
	/**
	 * @descriptions 刷新Redis 
	 * 
	 * @param productCode_ 
	 * @date 2016年8月16日下午1:37:21
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	private boolean redisDeleteProductInfo(String productCode_){
		// 循环删除所有商品下关联的子活动
		for(String key : RedisLaunch.setFactory(ERedisSchema.ProductIcChildren).hgetAll(productCode_).keySet()){
			RedisLaunch.setFactory(ERedisSchema.IcSku).del(key);
		}
		// 删除所有Sku相关信息
		List<PcSkuinfo> skuList = pcSkuinfoDao.findList(new PcSkuinfo(productCode_)); 
		for(PcSkuinfo i : skuList){
			RedisLaunch.setFactory(ERedisSchema.IcSku).del(i.getSkuCode()); 
			RedisLaunch.setFactory(ERedisSchema.Stock).del(i.getSkuCode());
			RedisLaunch.setFactory(ERedisSchema.SkuStoreStock).del(i.getSkuCode());
		}
		RedisLaunch.setFactory(ERedisSchema.Product).del(productCode_);
		RedisLaunch.setFactory(ERedisSchema.ProductSku).del(productCode_);
		RedisLaunch.setFactory(ERedisSchema.ProductSales).del(productCode_);		//刷新销量缓存
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////  以下方法准备废弃不用 /////////////////////////////////////////////////
	
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
//	@Resource
//	private IScStoreSkunumDao scStoreSkunumDao;
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

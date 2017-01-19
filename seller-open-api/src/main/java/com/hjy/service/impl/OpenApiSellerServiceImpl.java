package com.hjy.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.ExculdeNullField;
import com.hjy.annotation.TargetField;
import com.hjy.common.DateUtil;
import com.hjy.common.product.SkuCommon;
import com.hjy.constant.MemberConst;
import com.hjy.dao.ILcOpenApiQueryLogDao;
import com.hjy.dao.ILcOpenApiSellerProductOperationsDao;
import com.hjy.dao.log.ILcOpenApiOrderStatusDao;
import com.hjy.dao.log.ILcOpenApiShipmentStatusDao;
import com.hjy.dao.log.ILcStockchangeDao;
import com.hjy.dao.order.IOcOrderShipmentsDao;
import com.hjy.dao.order.IOcOrderinfoDao;
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
import com.hjy.dto.product.ApiSellerProduct;
import com.hjy.dto.product.ApiSellerSkuInfo;
import com.hjy.dto.product.ProductInfo;
import com.hjy.entity.log.LcOpenApiOrderStatus;
import com.hjy.entity.log.LcOpenApiQueryLog;
import com.hjy.entity.log.LcOpenApiShipmentStatus;
import com.hjy.entity.log.LcStockchange;
import com.hjy.entity.order.OcOrderShipments;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.entity.product.PcProductcategoryRel;
import com.hjy.entity.product.PcProductdescription;
import com.hjy.entity.product.PcProductflow;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcProductinfoExt;
import com.hjy.entity.product.PcProductpic;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.helper.DateHelper;
import com.hjy.helper.ExceptionHelper;
import com.hjy.helper.RedisHelper;
import com.hjy.helper.WebHelper;
import com.hjy.jms.ProductJmsSupport;
import com.hjy.model.ProductSkuInfo;
import com.hjy.request.data.OrderInfoRequestDto;
import com.hjy.request.data.OrderInfoStatus;
import com.hjy.request.data.OrderInfoStatusDto;
import com.hjy.request.data.OrderShipment;
import com.hjy.response.OrderInfoResponse;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.system.IScFlowMainService;
import com.hjy.system.cmodel.CacheWcSellerInfo;
import com.hjy.service.IOpenApiSellerService;


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
@Service("openApiSellerService") 
public class OpenApiSellerServiceImpl  extends BaseServiceImpl<PcProductinfo, Integer>  implements IOpenApiSellerService {

	private static Logger logger = Logger.getLogger(OpenApiSellerServiceImpl.class);   
	
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
	@Resource
	private IOcOrderinfoDao orderInfoDao;
	@Resource
	private ILcOpenApiSellerProductOperationsDao openApiSellerProductOperationsDao;
	@Resource
	private ILcOpenApiQueryLogDao openApiQueryDao;
	@Resource
	private ILcOpenApiOrderStatusDao openApiOrderStatusDao;
	@Resource
	private IOcOrderShipmentsDao orderShipmentsDao ;
	@Resource
	private ILcOpenApiShipmentStatusDao logShipmentStatusDao;
	
	
	@Autowired
	private IScFlowMainService scFlowMainService;
	
	/**
	 * @description: 商户同步自己的商品到惠家有平台|同时同步一批商品，上限100件商品
	 * 	
	 * @接口所属：惠家有商户接口|Product.SyncSellerProductList
	 *  @访问间隔：3分钟 
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
			String lock  = WebHelper.getInstance().addLock(180 , sellerCode + "@OpenApiSellerServiceImpl.syncSellerProductList");  // 三分钟内不得访问 
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
						result.put("errors", errors); 
						Map<String , List<SkuResult>> successMap = new HashMap<String , List<SkuResult>>();
						List<PcProductinfo> targetList = this.requestConvertion(rlist, seller, productHead, skuHead); 
						for(PcProductinfo e : targetList){
							if(!e.getIsUpdate()){  // 准备添加一条记录 
								JSONObject add = this.insertSellerProduct(e , seller);
								successMap.put(e.getProductCodeOld().split("-")[1], this.skuCodeList(add));  
								ra.add(add); 
							}else{	   
								JSONObject update = this.updateSellerProduct(e);
								successMap.put(e.getProductCodeOld().split("-")[1], this.skuCodeList(update));  
								ra.add(update);                          
								// 删掉缓存中的商品信息 
								new RedisHelper().reloadProductInRedis(e.getProductCode()); 
							}
						}
						result.put("success", successMap);
						result.put("desc", this.getInfo(100009024));  		   // 100009024=请求执行完成!
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
		
		String responseTime = DateHelper.formatDate(new Date());
		result.put("responseTime", responseTime);
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
			p.getProductWeight().toString() , p.getMainPicUrl() , p.getProductVolumeItem(),
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
	private JSONObject insertSellerProduct(PcProductinfo e , CacheWcSellerInfo seller){
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
			
			scFlowMainService.createFlowMain(p , seller , "open-api");   
			
//			ProductJmsSupport pjs = new ProductJmsSupport();
//			pjs.onChangeProductText(e.getProductCode());
//			this.genarateJmsStaticPageForProduct(e);
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
	
	
	private List<SkuResult> skuCodeList(JSONObject o){
		List<SkuResult> skuList = new ArrayList<SkuResult>();
		if(o.getString("code").equals("1")){    
			PcProductinfo e = JSONObject.parseObject(o.getString("entity"), PcProductinfo.class);
			for(int i = 0 ; i < e.getProductSkuInfoList().size() ; i ++){
				skuList.add(new SkuResult(e.getProductSkuInfoList().get(i).getSkuCode(), 
						e.getProductSkuInfoList().get(i).getSkuName(), 
						e.getProductSkuInfoList().get(i).getSkuKeyvalue() ) );
			}
		} 
		return skuList;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @description: 依据开始时间和结束时间来查询一批订单，惠家有返回订单列表。
	 * 
	 * @接口所属：惠家有商户接口|Order.SyncSellerOrderList
	 * @访问间隔：10分钟 
	 * 
	 * @param request
	 * @param seller 
	 * @author Yangcl 
	 * @date 2017年1月6日 上午10:14:35 
	 * @version 1.0.0.1
	 */
	public JSONObject syncSellerOrderList(String request, CacheWcSellerInfo seller){
		String sellerCode = seller.getSellerCode();
		JSONObject result = new JSONObject();  
		result.put("code", 1);  // 默认成功，为1
		if(StringUtils.isNotBlank(request)){
			String lock = WebHelper.getInstance().addLock(600 , sellerCode + "@OpenApiSellerServiceImpl.syncSellerOrderList");  // 10分钟内不得访问 
			if(StringUtils.isNotBlank(lock)){
				String startTime = "";
				String endTime = "";
				try {
					JSONObject r = JSONObject.parseObject(request);
					if(StringUtils.isAnyBlank(r.getString("startTime") , r.getString("endTime"))){
						startTime = this.getCustomDate(new Date() , -1);  // startTime和endTime不传，则默认为昨天0点到今天0点。
						endTime = this.getCustomDate(new Date() , 0);  
					}else{
						startTime = r.getString("startTime") ;  
						endTime = r.getString("endTime");  
						if(!this.compareDate(startTime, endTime)){
							result.put("code", 3);
							result.put("desc", this.getInfo(100009023));
							return result; 
						}
					}
					List<OrderInfoResponse> list = orderInfoDao.getOpenApiOrderinfoList(new OrderInfoRequestDto(sellerCode, null ,  startTime, endTime));
					result.put("desc", this.getInfo(100009024));  		   // 100009024=请求执行完成!
					result.put("data", list);
					openApiQueryDao.insertSelective(new LcOpenApiQueryLog(UUID.randomUUID().toString().replace("-", ""),
							sellerCode , 
							"Order.SyncSellerOrderList" ,   
							"com.hjy.service.impl.OpenApiSellerServiceImpl.syncSellerOrderList" , 
							new Date(),
							1 , 
							request,
							result.toJSONString(),  
							"query success")); 
				} catch (Exception ex) {
					logger.error("查询订单状态信息异常|"  , ex);  
					String remark_ = "{" + ExceptionHelper.allExceptionInformation(ex)+ "}";  // 记录异常信息到数据库表
					result.put("code", 3);
					result.put("desc", remark_); 
					openApiQueryDao.insertSelective(new LcOpenApiQueryLog(UUID.randomUUID().toString().replace("-", ""),
							sellerCode , 
							"Order.SyncSellerOrderList" , 
							"com.hjy.service.impl.OpenApiSellerServiceImpl.syncSellerOrderList" , 
							new Date(),
							2 , 
							request,
							result.toJSONString(), 
							remark_)); 
				}finally {
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
		result.put("responseTime", DateHelper.formatDate(new Date()));
		return result;
	}
	
 
	private String getCustomDate(Date date , Integer flag){
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE , flag);//把日期往后增加一天|正数往后推,负数往前移动
		 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		 
		 return formatter.format(date);
	}
	
	/**
	 * @descriptions 比较两个时间的大小 如果两个时间相等则返回false
	 * 
	 * @tips 如果两个时间相等则a.compareTo(b) = 0
	 * 
	 * @param a not null
	 * @param b not null 
	 * @return boolean 
	 * 
	 * @refactor 
	 * @author Yangcl
	 * @date 2016-5-5-下午2:52:13
	 * @version 1.0.0.1
	 */
	private boolean compareDate(String a , String b){
		if(StringUtils.isAnyBlank(a , b)){
			return false;
		}
		return a.compareTo(b) < 0;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @description: 批量更新订单状态信息|商户已经发货或用户取消订单后，将订单对应的状态传递给惠家有
	 * 
	 * @接口所属：惠家有商户接口|Order.UpdateOrderStatus
	 * @访问间隔：10分钟 
	 * 
	 * @param json
	 * @param seller
	 * @author Yangcl 
	 * @date 2017年1月10日 下午2:41:47 
	 * @version 1.0.0.1
	 */
	public JSONObject updateOrderStatus(String json, CacheWcSellerInfo seller){
		String sellerCode = seller.getSellerCode();
		JSONObject result = new JSONObject();  
		result.put("code", 1);  // 默认成功，为1
		if(StringUtils.isNotBlank(json)){
			OrderInfoStatus e = null;
			String lock = "";
			try {
				lock = WebHelper.getInstance().addLock(600 , sellerCode + "@OpenApiSellerServiceImpl.updateOrderStatus");
				if(StringUtils.isNotBlank(lock)){
					List<OrderInfoStatus> list =  JSONArray.parseArray(json , OrderInfoStatus.class);
					if(list != null && list.size() > 0){
						if(list.size() > 100){
							result.put("code", 3);
							result.put("desc", this.getInfo(100009004 , 100));  // 请求数据量过大，超过限制{0}条
							return result; 
						}                                     // 449715390001000
						List<OrderInfoStatus> updateList = new ArrayList<OrderInfoStatus>();
						List<String> error = new ArrayList<String>(); // 保存效验失败的订单编号 
						for( int i = 0 ; i < list.size() ; i ++){
							list.get(i).setUpdateTime(DateHelper.formatDate(new Date()));
							if(this.validateOrderStatus(list.get(i) , sellerCode)){
								list.get(i).setOrderStatus("449715390001000" + list.get(i).getOrderStatus()); 
								updateList.add(list.get(i));
							}else{
								error.add(list.get(i).getOrderCode());    
							}
						}
						
						List<OrderInfoStatus> successList = new ArrayList<OrderInfoStatus>(); // 保存同步成功的记录
						for(OrderInfoStatus o : updateList){
							e = o;
							Integer count = orderInfoDao.apiUpdateOrderinfoStatus(new OrderInfoStatusDto(o.getOrderCode() , o.getOrderStatus() , o.getUpdateTime() , sellerCode));
							// 插入一条同步日志记录      zid   sellerCode  orderCode   orderStatus createTime 
							if(count != null && count == 1){
								openApiOrderStatusDao.insertSelective(new LcOpenApiOrderStatus(sellerCode , o.getOrderCode() , o.getOrderStatus() , 1 , new Date() , "update success"));
								successList.add(o);
							}else if(count != null && count == 0){
								error.add(o.getOrderCode());
							}
						}
						result.put("success", successList);
						result.put("error", error); 
						result.put("desc", this.getInfo(100009024));  		   // 100009024=请求执行完成!
					} 
				}else{
					result.put("code", 0);
					result.put("desc", this.getInfo(100009002));  // 分布式锁生效中
				}
			} catch (Exception ex) {
				ex.printStackTrace(); 
				logger.error("更新订单状态信息异常|" , ex);  
				String remark_ = "{" + ExceptionHelper.allExceptionInformation(ex)+ "}";  		// 记录异常信息到数据库表
				openApiOrderStatusDao.insertSelective(new LcOpenApiOrderStatus(sellerCode , e.getOrderCode() , e.getOrderStatus() , 2 , new Date() , remark_));
				result.put("code", 3);
				result.put("desc", this.getInfo(100009003));  		// 请求参数错误，请求数据解析异常
				return result; 
			}finally{
				WebHelper.getInstance().unLock(lock); 
			}
		}else{
			result.put("code", -1);
			result.put("desc", this.getInfo(100009001));  		   // 请求数据报文data为空
		}
		return result;
	}	
	
	/**
	 * @descriptions 效验订单状态
	 * 
	 * @param status 
	 * @date 2016年8月5日下午2:37:39
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	private boolean validateOrderStatus(OrderInfoStatus info , String sellerCode){
		boolean flag = false;
		if(StringUtils.isBlank(info.getOrderCode())){
			return flag;
		}
		String status = info.getOrderStatus();
		if(status.length() == 1 && StringUtils.startsWithAny(status, new String[] {"1" , "2" , "3" , "4" , "5" , "6" , "7" })){
			flag = true;
		}
		
		// 验证是否为该商户下的订单。
//		Integer count = orderInfoDao.apiOrderinfoValidate(new OrderInfoStatusDto(info.getOrderCode() , sellerCode)); 
//		if(count == 0){
//			flag = false;
//		}
		
		return flag;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @description: 订单的物流变更信息|效验订单后插入物流信息
	 * 
	 * @接口所属：惠家有商户接口|Order.SyncSellerShipments
	 * @访问间隔：5分钟 
	 * 
	 * @param json
	 * @param seller
	 * @author Yangcl 
	 * @date 2017年1月18日 上午11:40:01 
	 * @version 1.0.0.1
	 */
	public JSONObject apiInsertShipments(String json, CacheWcSellerInfo seller){
		String sellerCode = seller.getSellerCode();
		JSONObject result = new JSONObject();  
		result.put("code", 1);  // 默认成功，为1
		
		if(StringUtils.isNotBlank(json)){
			String lock = "";
			try {
				lock = WebHelper.getInstance().addLock(180 , sellerCode + "@OpenApiSellerServiceImpl.apiInsertShipments");
				if(StringUtils.isNotBlank(lock)){
					List<OrderShipment> list = JSONArray.parseArray(json , OrderShipment.class);
					if(list != null && list.size() > 0){
						if(list.size() > 100){
							result.put("code", 3);
							result.put("desc", this.getInfo(100009004 , 100));  // 请求数据量过大，超过限制{0}条
							return result; 
						}
						// 效验订单，判断状态正确的订单	 
						List<OrderShipment> correctList = new ArrayList<OrderShipment>(); // 保存合法的物流信息
						List<OrderShipment> errorList = new ArrayList<OrderShipment>();  // 异常的订单物流信息|关键字段不全，不做处理，返回给调用方
						for(int i = 0 ; i < list.size() ; i ++){
							if(StringUtils.isAnyBlank(list.get(i).getOrderCode(), list.get(i).getLogisticseCode(), 
									list.get(i).getWaybill(), list.get(i).getLogisticseName())){
								
								errorList.add(list.get(i)); 
							}else{
								list.get(i).setUid(UUID.randomUUID().toString().replace("-", ""));
								list.get(i).setCreator(sellerCode); 
								list.get(i).setCreateTime(DateHelper.formatDate(new Date())); 
								correctList.add(list.get(i)); 
							}
						}
						if(errorList.size() > 0){
							result.put("errorList", errorList); // 关键字段不全订单
						}
						
						// 效验订单，判断是否有不是该商户下的订单	 
						List<OrderShipment> insertList = new ArrayList<OrderShipment>(); // 保存在我们库中的订单的物流信息，排除非法订单
						List<String> otherOrderList = new ArrayList<String>();  // 非惠家有订单的物流信息，不做处理，返回给调用方
						for(OrderShipment o : correctList){
							OcOrderinfo info = orderInfoDao.getOrderInfoByCode(new OcOrderinfo(o.getOrderCode() , sellerCode)); 
							if(null == info){ 
								otherOrderList.add(o.getOrderCode());   
							}else{
								insertList.add(o);
							}
						}
						if(otherOrderList.size() > 0){
							result.put("otherList", otherOrderList); // 非该商户订单
						}
						
						// 准备处理订单对应的物流信息
						List<OrderShipment> successList = new ArrayList<OrderShipment>(); // 保存同步成功的记录
						List<OrderShipment> updateList = new ArrayList<OrderShipment>(); // 保存insertList中在库中已经存在的对象
						OrderShipment ex = null;
						try {
							List<OrderShipment> insertList_ = new ArrayList<OrderShipment>(); 
							for(OrderShipment s : insertList){
								OcOrderShipments info = orderShipmentsDao.findWayBill(s); // 如果存在多条记录，此处mybatis会报异常
								if(null != info){
									updateList.add(new OrderShipment(info.getUid() , info.getOrderCode() , 
														s.getLogisticseCode() , s.getLogisticseName() , s.getWaybill() ,
														info.getCreator() , info.getCreateTime() , s.getRemark()));  
								}else{
									insertList_.add(s);
								}
							}
							insertList = insertList_;
							
							for(OrderShipment s : insertList){
								ex = s;
								orderShipmentsDao.insertSelective(new OcOrderShipments(s.getUid() , s.getOrderCode() , s.getLogisticseCode() , s.getLogisticseName() , s.getWaybill() , s.getCreator() , s.getCreateTime() , s.getRemark()));   
								logShipmentStatusDao.insertSelective(new LcOpenApiShipmentStatus(ex.getUid() , sellerCode , ex.getOrderCode() , ex.getLogisticseName() , ex.getWaybill() , 1 , new Date() , "insert success"));
								successList.add(s);
							}
							for(OrderShipment s : updateList){
								ex = s;
								orderShipmentsDao.updateSelectiveByUid(new OcOrderShipments(s.getUid() , s.getOrderCode() , s.getLogisticseCode() , s.getLogisticseName() , s.getWaybill() , s.getCreator() , s.getCreateTime() , s.getRemark()));   
								logShipmentStatusDao.insertSelective(new LcOpenApiShipmentStatus(ex.getUid() , sellerCode , ex.getOrderCode() , ex.getLogisticseName() , ex.getWaybill() , 1 , new Date() , "update success"));
								successList.add(s);
							}
						} catch (Exception e) { 
							// 记录异常信息到数据库表  shipmentUid sellerCode orderCode logisticseName wayBill  flag createTime remark
							String desc_ = "平台内部错误，成功 " + successList.size() + " 条，失败 " + (insertList.size() + updateList.size() - successList.size()) + " 条";
							logger.error("插入物流数据异常|" + desc_ , e); 
							String remark_ = "{" + ExceptionHelper.allExceptionInformation(e) + "}";
							logShipmentStatusDao.insertSelective(new LcOpenApiShipmentStatus(ex.getUid() , sellerCode , 
									ex.getOrderCode() , ex.getLogisticseName() , ex.getWaybill() , 2 , new Date() , remark_));
							
							result.put("code", 5);
							result.put("desc", desc_);
							return result; 
						}finally{
							result.put("successList", successList);
						}
						
					} 
				}else{
					result.put("code", 0);
					result.put("desc", this.getInfo(100009002));  // 分布式锁生效中
				}
			} catch (Exception e) {
				e.printStackTrace(); 
				result.put("code", 3);
				result.put("desc", this.getInfo(100009003));  // 请求参数错误，请求数据解析异常
				return result; 
			}finally{
				WebHelper.getInstance().unLock(lock); 
			}
		}else{
			result.put("code", -1);
			result.put("desc", this.getInfo(100009001));  // 请求数据报文data为空
		}
		result.put("responseTime", DateHelper.formatDate(new Date()));
		return result;
	}
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public JSONObject syncDemooooooooooooooo(String json, CacheWcSellerInfo seller){
		String sellerCode = seller.getSellerCode();
		JSONObject result = new JSONObject();  
		result.put("code", 1);  // 默认成功，为1
		String productHead = this.getConfig("seller_adapter.product_" + seller.getSellerType()); ;
		String skuHead = this.getConfig("seller_adapter.sku_" + seller.getSellerType()); 
		if(StringUtils.isNotBlank(json)){
			String lock = "";
			try {
				lock = WebHelper.getInstance().addLock(180 , sellerCode + "@syncDemooooooooooooooo");
				if(StringUtils.isNotBlank(lock)){
					List<ProductInfo> plist =  null; 
					try {
						plist =  JSONArray.parseArray(json , ProductInfo.class);
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
			}finally{
				WebHelper.getInstance().unLock(lock); 
			}
		}else{
			result.put("code", -1);
			result.put("desc", this.getInfo(100009001));  // 请求数据报文data为空
		}
		
		result.put("responseTime", DateHelper.formatDate(new Date()));
		return result;
	}
}

/**
 * @description: 返回同步成功的sku信息 
 * 
 * @author Yangcl
 * @date 2017年1月10日 下午2:16:23 
 * @version 1.0.0
 */
class SkuResult{
	private String skuCode;
	private String skuName;
	private String property;  // sku规格属性
	
	public SkuResult(String skuCode, String skuName, String property) {
		this.skuCode = skuCode;
		this.skuName = skuName;
		this.property = property;
	}
	
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
}
















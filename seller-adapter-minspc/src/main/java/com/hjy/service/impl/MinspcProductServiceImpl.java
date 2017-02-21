package com.hjy.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.base.BaseClass;
import com.hjy.common.DateUtil;
import com.hjy.common.product.SkuCommon;
import com.hjy.constant.MemberConst;
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
import com.hjy.dao.system.IScFlowBussinessHistoryDao;
import com.hjy.dao.system.IScStoreSkunumDao;
import com.hjy.dao.user.IUcSellercategoryProductRelationDao;
import com.hjy.dto.response.ResultMsg;
import com.hjy.dto.response.product.Product;
import com.hjy.entity.log.LcStockchange;
import com.hjy.entity.product.PcCategoryinfo;
import com.hjy.entity.product.PcProductcategoryRel;
import com.hjy.entity.product.PcProductdescription;
import com.hjy.entity.product.PcProductflow;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcProductinfoExt;
import com.hjy.entity.product.PcProductpic;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.entity.system.ScFlowBussinessHistory;
import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.entity.user.UcSellercategoryProductRelation;
import com.hjy.helper.DateHelper;
import com.hjy.helper.ExceptionHelper;
import com.hjy.helper.RedisHelper;
import com.hjy.helper.WebHelper;
import com.hjy.jms.ProductJmsSupport;
import com.hjy.model.ProductSkuInfo;
import com.hjy.service.IMinspcProductService;
import com.hjy.service.system.IScFlowMainService;
import com.hjy.system.ApiCacheVisitor;
import com.hjy.system.cmodel.CacheWcSellerInfo;

@Service
public class MinspcProductServiceImpl extends BaseClass implements IMinspcProductService{
	
	private static Logger logger = Logger.getLogger(MinspcProductServiceImpl.class);
	
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
	
	@Autowired
	private IScFlowMainService scFlowMainService;
	
	@Resource
	private IScFlowBussinessHistoryDao scFlowBussinessHistoryDao;
	
	private static String ProductHead = "6016";  // 8016
	private static String SKUHead = "6019";  // 8019
	private static String ProductFlowHead = "PF";
	
	/**
	 * @description: 插入产品数据到多个表 
	 * 							 这里参考了跨境通com.hjy.service.impl.TxProductServiceImpl.java -> insertProduct() 
	 * @throws                  
	 * @author Yangcl
	 * @date 2016年9月9日 下午3:32:46 
	 * @version 1.0.0.1
	 */
	public ResultMsg insertProductToTables(PcProductinfo e) {
		ResultMsg result = new ResultMsg();
		result.setType("insert"); 
		String createTime = DateUtil.getSysDateTimeString();
		try {
			// 插入商品基本信息
			PcProductinfo pinfo = new PcProductinfo();
			pinfo.setUid(UUID.randomUUID().toString().replace("-", ""));
			pinfo.setCreateTime(createTime);
			pinfo.setBrandCode(e.getBrandCode());
			pinfo.setFlagPayway(e.getFlagPayway());
			pinfo.setFlagSale(e.getFlagSale());
			pinfo.setLabels(e.getLabels());
//			pinfo.setMainPicUrl(e.getMainPicUrl());
			pinfo.setMainpicUrl(e.getMainpicUrl());   // 数据库主图
			pinfo.setMarketPrice(e.getMarketPrice());
			pinfo.setMaxSellPrice(e.getMaxSellPrice());
			pinfo.setMinSellPrice(e.getMinSellPrice());
			pinfo.setProductCode(e.getProductCode());
			pinfo.setProductCodeOld(e.getProductCodeOld() == null ? "" : e.getProductCodeOld());
			pinfo.setProductName(e.getProdutName());  
			pinfo.setProductStatus(e.getProductStatus());
			pinfo.setProductVolume(e.getProductVolume());
			pinfo.setProductVolumeItem(e.getProductVolumeItem());
			pinfo.setProductWeight(e.getProductWeight());
			pinfo.setSellerCode(e.getSellerCode());
			pinfo.setSmallSellerCode(e.getSmallSellerCode());
			pinfo.setSellProductcode(e.getSellProductcode());
			pinfo.setTransportTemplate(e.getTransportTemplate());
			pinfo.setAreaTemplate(e.getAreaTemplate()); // 限购地区
			pinfo.setUpdateTime(createTime);
			pinfo.setCostPrice(e.getCostPrice());
			pinfo.setProductShortname(e.getProductShortname());
			pinfo.setSupplierName(e.getSupplierName());
			pinfo.setVideoUrl(e.getVideoUrl());
			pinfo.setValidate_flag(e.getValidate_flag());// 添加是否是虚拟商品字段
			pinfo.setTaxRate(e.getTaxRate());
			pinfo.setProductCodeCopy(e.getProductCodeCopy());
			pinfo.setAdPicUrl(e.getAdPicUrl());
			pinfo.setExpiryDate(e.getExpiryDate());// 保质期
			pinfo.setExpiryUnit(e.getExpiryUnit());// 保质期单位
			pinfo.setQualificationCategoryCode(e.getQualificationCategoryCode());// 资质品类
			pcProductInfoDao.insertSelective(pinfo); 
			
			// 添加商品的实类信息 
			pcProductcategoryRelDao.insertSelective(e.getPcProductcategoryRel());
			
			// 添加 描述信息
			PcProductdescription ppdModel = new PcProductdescription();
			ppdModel.setUid(UUID.randomUUID().toString().replace("-", ""));
			ppdModel.setProductCode(e.getProductCode());
			ppdModel.setKeyword(e.getDescription().getKeyword());
			ppdModel.setDescriptionPic(e.getDescription().getDescriptionPic());
			ppdModel.setDescriptionInfo(e.getDescription().getDescriptionInfo()); 
			pcProductdescriptionDao.insertSelective(ppdModel);
			
			List<PcProductpic> picList_ = new ArrayList<PcProductpic>(); 
			// 插入商品轮播图
			List<PcProductpic> picList = e.getPcPicList();
			for (PcProductpic pic : picList) {
				pcProductpicDao.insertSelective(pic);
				PcProductpic pic_ = new PcProductpic();
				pic_.setPicUrl(pic.getPicUrl()); 
				picList_.add(pic_);
			} 
			/*
			 * 否则编辑商品的时候无法显示主图|modproductDraftBox.js -> set_value()方法里的坑代码。
			 * 貌似在其他涉及到编辑商品的代码里，也都是这样的。在编辑代码的时候会读取对应表中记录的
			 * product_json字段中的json串，里面PcProductpic结构只有picUrl有值
			 */
			e.setPcPicList(picList_);  
			
			// 插入sku信息
			List<ProductSkuInfo> skuList = e.getProductSkuInfoList();
			for (ProductSkuInfo sku : skuList) {
				PcSkuinfo psModel = new PcSkuinfo();
				psModel.setUid(UUID.randomUUID().toString().replace("-", ""));
				psModel.setMarketPrice(sku.getMarketPrice());
				psModel.setProductCode(e.getProductCode());
				psModel.setProductCodeOld(e.getProductCodeOld());
				psModel.setQrcodeLink(sku.getQrcodeLink());
				psModel.setSecurityStockNum(Long.valueOf(sku.getSecurityStockNum()));
				psModel.setSellerCode(e.getSellerCode());
				psModel.setSellPrice(sku.getSellPrice());
				psModel.setSellProductcode(sku.getSellProductcode());
				psModel.setSkuCode(sku.getSkuCode());
				psModel.setSkuCodeOld(sku.getSkuCodeOld());
				psModel.setSkuKey(sku.getSkuKey());
				psModel.setSkuKeyvalue(sku.getSkuValue());
				psModel.setSkuPicurl(sku.getSkuPicUrl());
				psModel.setSkuName(sku.getSkuName());
				psModel.setSkuAdv(sku.getSkuAdv()); 
				psModel.setStockNum(Long.valueOf(sku.getStockNum()));    
				psModel.setSaleYn(sku.getSaleYn());
				psModel.setCostPrice(sku.getCostPrice());
				psModel.setMiniOrder(sku.getMiniOrder());
				pcSkuinfoDao.insertSelective(psModel);

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
			ppe.setOaSiteNo(e.getPcProductinfoExt().getOaSiteNo());
			ppe.setValidateFlag(e.getPcProductinfoExt().getValidateFlag());
			ppe.setProductCodeOld(e.getProductCodeOld());
			ppe.setProductStoreType(e.getPcProductinfoExt().getProductStoreType());
			ppe.setProductTradeType(e.getPcProductinfoExt().getProductTradeType());
			ppe.setPoffer(e.getPcProductinfoExt().getPoffer());
			Integer fictitious = e.getPcProductinfoExt().getFictitiousSales();
			if (fictitious == null ||StringUtils.isBlank(fictitious.toString())) {
				fictitious = 0;
			}
			ppe.setFictitiousSales(fictitious);
			String grossProfit ="";
			if(null != e.getPcProductinfoExt().getGrossProfit()){
				grossProfit = e.getPcProductinfoExt().getGrossProfit().toString();
			}
			ppe.setGrossProfit(Long.parseLong((StringUtils.isBlank(grossProfit) ? "0" : grossProfit)));
			String accmrng = "";
			if(null != e.getPcProductinfoExt().getAccmRng()){
				accmrng = e.getPcProductinfoExt().getAccmRng().toString();
			}
			ppe.setAccmRng(Double.parseDouble(StringUtils.isBlank(accmrng) ? "0" : accmrng));
			ppe.setMdId(e.getPcProductinfoExt().getMdId());
			ppe.setMdNm(e.getPcProductinfoExt().getMdNm());
			ppe.setSettlementType(e.getPcProductinfoExt().getSettlementType());
			ppe.setPurchaseType(e.getPcProductinfoExt().getPurchaseType());
			ppe.setPicMaterialUrl(e.getPcProductinfoExt().getPicMaterialUrl());
			ppe.setPicMaterialUpload(e.getPcProductinfoExt().getPicMaterialUpload());
			ppe.setKjtSellerCode(e.getPcProductinfoExt().getKjtSellerCode());
			pcProductinfoExtDao.insertSelective(ppe);
			
			
			// 插入商品历史流水信息
			PcProductflow ppf = new PcProductflow();
			ppf.setUid(UUID.randomUUID().toString().replace("-", ""));
			ppf.setCreator("job-system-minspc");
			ppf.setFlowCode(e.getPcProdcutflow().getFlowCode());
			ppf.setFlowStatus(e.getPcProdcutflow().getFlowStatus());
			ppf.setProductCode(e.getProductCode());
			ppf.setCreateTime(createTime); 
			ppf.setUpdateTime(createTime);
			ppf.setUpdator("job-system-minspc");
			ppf.setProductJson(JSON.toJSONString(e)); 
			pcpFlowdao.insertSelective(ppf);
			
			// 插入商品库存流水
			for (ProductSkuInfo sku : skuList) {
				LcStockchange lsModel = new LcStockchange();
				lsModel.setChangeStock(sku.getStockNum());
				lsModel.setChangeType(SkuCommon.SkuStockChangeTypeCreateProduct);
				lsModel.setCode(sku.getSkuCode());
				lsModel.setCreateTime(createTime);
				lsModel.setCreateUser("job-system-minspc");
				lsModel.setUid(UUID.randomUUID().toString().replace("-", ""));
				lcStockchangeDao.insertSelective(lsModel);
			}
			// 创建审批流
//			CacheWcSellerInfo seller = JSONObject.parseObject(ApiCacheVisitor.find("SF03100646") , CacheWcSellerInfo.class); // 取出缓存中的商户信息
			CacheWcSellerInfo seller = new CacheWcSellerInfo();
			seller.setSellerType("4497478100050002"); 
			scFlowMainService.createFlowMain(pinfo, seller, "minspc-job");   
			
			// 校验输入的数据合法性
			ProductJmsSupport pjs = new ProductJmsSupport();
			pjs.onChangeProductText(e.getProductCode());
			this.genarateJmsStaticPageForProduct(e);
			
		} catch (Exception ex) {
			String exstring = ExceptionHelper.allExceptionInformation(ex);
			logger.error(exstring);
			result.setCode("0"); 
			result.setEntity(e); 
			result.setMsg(exstring); 
			return result;
		}
		result.setCode("1"); 
		result.setEntity(e); 
		return result; 
	}
	
	 
	/**
	 * @description: 更新产品数据到多个表 
	 * 	更新商品基本信息|商品主图不再更新。只更新库存
	 * 
	 * @param entity 新数据
	 * @param info 数据库中的记录
	 * @return
	 * @author Yangcl 
	 * @date 2016年9月9日 下午3:32:46 
	 * @version 1.0.0.1
	 */
	public ResultMsg updateProductInTables(PcProductinfo e  , PcProductinfo info) {
		ResultMsg result = new ResultMsg();
		result.setType("update"); 
		String createTime = DateUtil.getSysDateTimeString();
		try { 
			if(info.getProductStatus().equals("4497153900060002")){     // 如果是上架的商品才更新
				PcProductinfo pinfo = new PcProductinfo();
				pinfo.setUid(e.getUid()); 
				pinfo.setUpdateTime(createTime);
				pcProductInfoDao.updateSelective(pinfo); 
				// 更新商品sku信息
				List<ProductSkuInfo> skuList = e.getProductSkuInfoList();
				for (ProductSkuInfo sku : skuList) {
					List<PcSkuinfo> slist = pcSkuinfoDao.getSkuinfoByPcode(e.getProductCode());   
					if(slist != null && slist.size() > 0){ 
						// 更新商品sku库存|sku 的实际库存保存在sc_store_skunum表中
						PcSkuinfo i = slist.get(0);
						ScStoreSkunum sss = new ScStoreSkunum();
						sss.setSkuCode(i.getSkuCode());
						// 博略德传来的是总数，减去pc_skuinfo中的数量 再加上原有的数量 才是正确的库存
						sss.setStockNum(Long.valueOf(sku.getStockNum() - i.getStockNum())); 
						scStoreSkunumDao.updateSelectiveBySkuCode(sss);
					}
					
					PcSkuinfo ps = new PcSkuinfo();
					ps.setProductCode(info.getProductCode()); 
					ps.setStockNum(Long.valueOf(sku.getStockNum()));    
					pcSkuinfoDao.updateSelectiveByProductCode(ps); // 因为一个商品对应一个Sku，所以这里偷懒
				}
				
				boolean flag = new RedisHelper().reloadProductInRedis(info.getProductCode());
				logger.info(info.getProductName() + "@"+ info.getProductCode() +"@缓存状态信息：" + flag);
			}
			
//			if(e.getCostPrice().toString().equals(info.getCostPrice())){
//				pcProductInfoDao.updateSelective(pinfo); 
//				// 更新商品sku信息
//				List<ProductSkuInfo> skuList = e.getProductSkuInfoList();
//				for (ProductSkuInfo sku : skuList) {
//					PcSkuinfo psModel = new PcSkuinfo();
//					psModel.setStockNum(Long.valueOf(sku.getStockNum()));    
//					pcSkuinfoDao.updateSelectiveByProductCode(psModel); // 因为一个商品对应一个Sku，所以这里偷懒
//				}
//			}else{
				// 如果成本价有变动，则商品下架，redis中的信息删除，solr中的索引也同时下架|根据需求，此处不再处理
//				pinfo.setCostPrice(e.getCostPrice());
//				pinfo.setProductStatus("4497153900060003"); // 商品下架
//				pcProductInfoDao.updateSelective(pinfo);
//				scFlowBussinessHistoryDao.insertSelective(new ScFlowBussinessHistory(
//					UUID.randomUUID().toString().replace("-", ""),
//					pinfo.getUid(),    // 关联商品的uuid
//					"449715390006",
//					"job-system-minspc",
//					DateHelper.formatDate(new Date()),
//					" 商编：" + pinfo.getProductCode() + "  原因：成本价变动后自动下架",  // " - 上下架原因描述 - 邮件发送人",
//					"4497153900060003"
//				));
				
//			}
			
		} catch (Exception ex) {
			String exstring = ExceptionHelper.allExceptionInformation(ex);
			logger.error(exstring);
			result.setCode("0"); 
			result.setEntity(e); 
			result.setMsg(exstring); 
			return result;
		}
		
		result.setCode("1"); 
		result.setEntity(e); 
		return result;
	}
	
	
	/**
	 * @description: 响应数据报文与惠家有表实体报文转换。
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月8日 下午4:13:38 
	 * @version 1.0.0.1
	 */
	public List<PcProductinfo> productConvertion(List<Product> list){
		List<PcProductinfo> list_ = new ArrayList<PcProductinfo>();
		for(Product p : list){
			String uid = UUID.randomUUID().toString().replace("-", "");
			BigDecimal price = BigDecimal.valueOf(Double.valueOf(p.getPrice()));
			PcProductinfo e = new PcProductinfo();
			e.setProductCode(WebHelper.getInstance().genUniqueCode(ProductHead)); // 8016
			e.setUid(uid);
			e.setProductCodeOld("minspc-" +p.getProductID()); // 添加标示头，用于区分跨境通中可能存在的编号
			e.setProductShortname(p.getProductName());
			e.setStock(Integer.valueOf(p.getStock())); 
			// 品牌id 和 品牌名称 插入时候默认为空，这两个字段由运营来维护。
//			e.setBrandCode(String.valueOf(p.getBrandID()));
//			e.setBrandName(p.getProductBrand()); 
			e.setProdutName(p.getProductName());
			e.setMaxSellPrice(price);
			e.setMinSellPrice(price);
			e.setCostPrice(price);
			e.setMarketPrice(price);
			e.setSellerCode(MemberConst.MANAGE_CODE_HOMEHAS);  // SI2003
			e.setMainpicUrl(p.getProductPictures().get(0));   // 主图默认为轮播图的第一张  
			e.setSmallSellerCode(getConfig("seller_adapter_minspc.small_seller_code"));  // 线上配置文件 small_seller_code 
			e.setProductStatus("4497153900060001");// 商品待上架
			e.setValidate_flag("Y");//是否是虚拟商品
			e.setTaxRate(BigDecimal.valueOf(Double.valueOf(0))); 
			e.setProductWeight(BigDecimal.valueOf(Double.valueOf(p.getProductWeight())));
			e.setTransportTemplate("0");// 运费模板默认为卖家包邮
			e.setLabels(p.getProductKey());   //  关键字
			e.setPicUpdate(p.getPicUpdate()); // 商品图片是否被编辑过。更新商品时候使用。
			
			// 补充信息
			PcCategoryinfo category = new PcCategoryinfo();
			category.setCategoryCode("44971603002900010001");
			category.setCategoryName("");
			category.setParentCode("");
			category.setUid("");
			category.setZid(0); 
			e.setCategory(category); 
			PcProductcategoryRel pcr = new PcProductcategoryRel();
			pcr.setUid(UUID.randomUUID().toString().replace("-", ""));
			pcr.setProductCode(e.getProductCode());	
			pcr.setCategoryCode("44971603002900010001");  // 惠家有默认
			pcr.setFlagMain(Long.parseLong(1 + ""));
			e.setPcProductcategoryRel(pcr); 
			// 资质品类：select * from systemcenter.`sc_define` where parent_code = '449747160031'
			// TODO 但此处有问题，是否在open-api中开放此类目？
			e.setQualificationCategoryCode(""); 
			// 店铺商品分类关系
//			List<UcSellercategoryProductRelation> usprList = new ArrayList<UcSellercategoryProductRelation>();
//			UcSellercategoryProductRelation uspr = new UcSellercategoryProductRelation();
//			uspr.setProductCode(e.getProductCode());
//			uspr.setCategoryCode("");  // uc_sellercategory表 榨汁机
//			uspr.setSellerCode("SI2003"); 
//			usprList.add(uspr);
//			e.setUsprList(usprList);  

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
			description.setKeyword(p.getProductKey());
			e.setDescription(description);
			
			// 设置Sku信息。民生品粹没有给Sku信息，这里默认其为商品信息
			List<ProductSkuInfo> skuInfoList = new ArrayList<ProductSkuInfo>(1);
			ProductSkuInfo skuInfo = new ProductSkuInfo();
			skuInfo.setSkuCode(WebHelper.getInstance().genUniqueCode(SKUHead));
			skuInfo.setProductCode(e.getProductCode());
			skuInfo.setSellPrice(price);  
			skuInfo.setMarketPrice(price);
			skuInfo.setCostPrice(price);// 设置sku的成本价
			skuInfo.setSkuName(e.getProductName()); 
			skuInfo.setSkuPicUrl(p.getProductPictures().get(0)); // 默认轮播图的第一张，是否可以？？？？？
			skuInfo.setSellProductcode(e.getProductCodeOld());// 设置外部商品 id 
			skuInfo.setStockNum(Integer.valueOf(e.getStock())); 
			skuInfo.setSecurityStockNum(Integer.valueOf(e.getStock()));  // 商品库存
			skuInfo.setSaleYn("Y");// 是否可卖为可买
			skuInfo.setFlagEnable("1");// 是否可用为可用
			skuInfo.setSellerCode(MemberConst.MANAGE_CODE_HOMEHAS);  // SI2003
			skuInfo.setSkuKey("4497462000010001=44974620000100010000&4497462000020001=44974620000200010000");
			skuInfo.setSkuValue("颜色=共同&款式=共同");
			skuInfoList.add(skuInfo);
			e.setProductSkuInfoList(skuInfoList); 
			
			// 设置商品轮播图
			List<PcProductpic> lunBoList = new ArrayList<PcProductpic>();
			for(String url : p.getProductPictures()){
				PcProductpic picModel = new PcProductpic();
				picModel.setUid(UUID.randomUUID().toString().replace("-", ""));
				picModel.setPicUrl(url);
				picModel.setProductCode(e.getProductCode());
				picModel.setSkuCode(skuInfo.getSkuCode());
				lunBoList.add(picModel);
			}
			e.setPcPicList(lunBoList);
			
			
			// 设置商品扩展信息
			PcProductinfoExt ext = new PcProductinfoExt();// 设置扩展信息
			ext.setProductCodeOld(e.getProductCodeOld());
			ext.setProductCode(e.getProductCode());
			ext.setPrchType("10");  // 一地入库类型
			// 供应商编号
			ext.setDlrId(getConfig("seller_adapter_minspc.small_seller_code"));
			// 供应商名称
			ext.setDlrNm(getConfig("seller_adapter_minspc.seller_company_name"));
			ext.setValidateFlag("Y"); // 是否是虚拟商品  Y：是  N：否
			// 贸易类型 -> 惠家有：(0:直邮，1:自贸)  ||  民生品粹：0为保税贸易，1为海外直邮，2为一般贸易
			ext.setProductTradeType(p.getTradeType());
			// 存储方式(0:常温，1:冷藏，2:冷冻) 
			ext.setProductStoreType(p.getColdStorage()); 
			ext.setPoffer("job-system-minspc");
			ext.setSettlementType("4497471600110003");	// 服务费结算
			ext.setPurchaseType("4497471600160003");  // purchase_type 采购类型  代收代付：4497471600160003
			e.setPcProductinfoExt(ext);
			
			// 商品变更状态
			PcProductflow pcProdcutflow = new PcProductflow();
			pcProdcutflow.setFlowCode(WebHelper.getInstance().genUniqueCode(ProductFlowHead));
			pcProdcutflow.setFlowStatus(SkuCommon.ProAddInit); // 10 新增商品未审核状态
			e.setPcProdcutflow(pcProdcutflow);
			
			list_.add(e);
		}
		
		return list_;
	}



	public List<PcProductinfo> getListBySellerCode(PcProductinfo entity) {
		return pcProductInfoDao.getListBySellerCode(entity);
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
	
	
	
	
	
	
}



















































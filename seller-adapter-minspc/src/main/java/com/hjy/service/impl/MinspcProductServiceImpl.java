package com.hjy.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.base.BaseClass;
import com.hjy.common.DateUtil;
import com.hjy.common.product.SkuCommon;
import com.hjy.constant.MemberConst;
import com.hjy.dao.product.IPcBrandinfoDao;
import com.hjy.dao.product.IPcProductcategoryRelDao;
import com.hjy.dao.product.IPcProductdescriptionDao;
import com.hjy.dao.product.IPcProductflowDao;
import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.dao.product.IPcProductinfoExtDao;
import com.hjy.dao.product.IPcProductpicDao;
import com.hjy.dao.product.IPcProductpropertyDao;
import com.hjy.dao.product.IPcSkuinfoDao;
import com.hjy.dao.user.IUcSellercategoryProductRelationDao;
import com.hjy.dto.response.product.Product;
import com.hjy.entity.product.PcProductcategoryRel;
import com.hjy.entity.product.PcProductdescription;
import com.hjy.entity.product.PcProductflow;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcProductinfoExt;
import com.hjy.helper.WebHelper;
import com.hjy.model.ProductSkuInfo;
import com.hjy.service.IMinspcProductService;

@Service
public class MinspcProductServiceImpl extends BaseClass implements IMinspcProductService{
	
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
	
	private static String ProductHead = "8016";
	private static String SKUHead = "8019";
	private static String ProductFlowHead = "PF";
	
	@Override
	public Integer insertProductToTables(PcProductinfo e) {
		JSONObject result = new JSONObject();
		String createTime = DateUtil.getSysDateTimeString();
		
		// 插入商品基本信息
		PcProductinfo pinfo = new PcProductinfo();
		pinfo.setUid(UUID.randomUUID().toString().replace("-", ""));
		pinfo.setCreateTime(createTime);
		pinfo.setBrandCode(e.getBrandCode());
		pinfo.setFlagPayway(e.getFlagPayway());
		pinfo.setFlagSale(e.getFlagSale());
		pinfo.setLabels(e.getLabels());
		pinfo.setMainPicUrl(e.getMainPicUrl());
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
		
		// 插入商品图片信息  TODO 我操，这里没有啊？插入轮播图还是描述图啊？需要在productConvertion组织数据
		
		
		
		
		
		return null;
	}
	
	
	
	@Override
	public Integer updateProductInTables(PcProductinfo entity) {
		
		return null;
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
			// 品牌id 和 品牌名称 插入时候默认为空，这两个字段由运营来维护。
//			e.setBrandCode(String.valueOf(p.getBrandID()));
//			e.setBrandName(p.getProductBrand()); 
			e.setProdutName(p.getProductName());
			e.setMaxSellPrice(price);
			e.setMinSellPrice(price);
			e.setCostPrice(price);
			e.setMarketPrice(price);
			e.setSellerCode(MemberConst.MANAGE_CODE_HOMEHAS);  // SI2003
			e.setMainpicUrl(p.getProductPictures().get(0)); 
			// TODO @@@@@@@@@@@@@@@@@@ 线上配置文件 small_seller_code 
			e.setSmallSellerCode(getConfig("seller_adapter_minspc.small_seller_code"));
			e.setProductStatus("4497153900060003");// 商品下架
			e.setValidate_flag("Y");//是否是虚拟商品
			e.setTaxRate(BigDecimal.valueOf(Double.valueOf(p.getProductTaxes())));
			e.setProductWeight(BigDecimal.valueOf(Double.valueOf(p.getProductWeight())));
			e.setTransportTemplate("0");// 运费模板默认为卖家包邮
		 
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
			description.setDescriptionInfo(img);
			description.setDescriptionPic(img_);
			description.setKeyword(p.getProductKey());
			e.setDescription(description);
			
			// 设置Sku信息。民生品粹没有给Sku信息，这里默认其为商品信息
			List<ProductSkuInfo> skuInfoList = new ArrayList<ProductSkuInfo>(1);
			ProductSkuInfo skuInfo = new ProductSkuInfo();
			skuInfo.setSkuCode(WebHelper.getInstance().genUniqueCode(SKUHead));
			skuInfo.setProductCode(p.getProductID());
			skuInfo.setSellPrice(price);
			skuInfo.setMarketPrice(price);
			skuInfo.setCostPrice(price);// 设置sku的成本价
			skuInfo.setSkuName(p.getProductName());// 过滤html标签
			skuInfo.setSellProductcode(p.getProductID());// 设置外部商品id
			skuInfo.setSaleYn("Y");// 是否可卖为可买
			skuInfo.setFlagEnable("1");// 是否可用为可用
			skuInfo.setSellerCode(MemberConst.MANAGE_CODE_HOMEHAS);  // SI2003
			skuInfo.setSkuKey("color_id=0&style_id=0");
			skuInfo.setSkuValue("颜色属性=共同&规格属性=共同");
			skuInfoList.add(skuInfo);
			e.setProductSkuInfoList(skuInfoList); 
			
			// 设置商品扩展信息
			PcProductinfoExt ext = new PcProductinfoExt();// 设置扩展信息
			ext.setProductCodeOld(p.getProductID());
			ext.setProductCode(e.getProductCode());
			ext.setPrchType("10");  // 一地入库类型
			// TODO @@@@@@@@@@@@@@@@@@ 供应商编号
			ext.setDlrId(getConfig("seller_adapter_minspc.seller_company_id"));
			// TODO @@@@@@@@@@@@@@@@@@ 供应商名称
			ext.setDlrNm(getConfig("seller_adapter_minspc.seller_company_name"));
			ext.setValidateFlag("Y"); // 是否是虚拟商品  Y：是  N：否
			// 贸易类型 -> 惠家有：(0:直邮，1:自贸)  ||  民生品粹：0为保税贸易，1为海外直邮，2为一般贸易
			ext.setProductTradeType(p.getTradeType());
			// 存储方式(0:常温，1:冷藏，2:冷冻) 
			ext.setProductStoreType(p.getColdStorage()); 
			ext.setPoffer("job-system-minspc");
			ext.setSettlementType("4497471600110003");	// 服务费结算
			e.setPcProductinfoExt(ext);
			
			// 商品变更状态
			PcProductflow pcProdcutflow = new PcProductflow();
			pcProdcutflow.setFlowCode(WebHelper.getInstance().genUniqueCode(ProductFlowHead));
			pcProdcutflow.setFlowStatus(SkuCommon.ProAddOr);
			e.setPcProdcutflow(pcProdcutflow);
			
			list_.add(e);
		}
		
		return list_;
	}



	public List<PcProductinfo> getListBySellerCode(PcProductinfo entity) {
		return pcProductInfoDao.getListBySellerCode(entity);
	}
	
	
	
	
	
	
	
	
	
}



















































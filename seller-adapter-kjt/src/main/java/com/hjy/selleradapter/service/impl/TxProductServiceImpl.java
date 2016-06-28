package com.hjy.selleradapter.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hjy.api.RootResult;
import com.hjy.base.BaseClass;
import com.hjy.common.DateUtil;
import com.hjy.common.product.SkuCommon;
import com.hjy.dao.log.ILcStockchangeDao;
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
import com.hjy.entity.log.LcStockchange;
import com.hjy.entity.product.PcProductcategoryRel;
import com.hjy.entity.product.PcProductdescription;
import com.hjy.entity.product.PcProductflow;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcProductinfoExt;
import com.hjy.entity.product.PcProductpic;
import com.hjy.entity.product.PcProductproperty;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.entity.user.UcSellercategoryProductRelation;
import com.hjy.helper.JsonHelper;
import com.hjy.model.ProductSkuInfo;
import com.hjy.selleradapter.service.ITxProductService;

/**
 * @descriptions 这个类的原名称：TxProductService.java
 * 
 * @date 2016年6月28日上午9:46:57
 * @author Yangcl
 * @version 1.0.1
 */
@Service("txProductService")
public class TxProductServiceImpl extends BaseClass implements ITxProductService {

	@Resource
	private IUcSellercategoryProductRelationDao usprm;
	@Resource
	private IPcProductdescriptionDao ppsm;
	@Resource
	private IPcProductflowDao ppfm;
	@Resource
	private IPcSkuinfoDao pcsm;
	@Resource
	private IPcProductinfoDao pcpm;
	@Resource
	private IPcProductpicDao pppm;
	@Resource
	private IPcProductpropertyDao pppmr;
	@Resource
	private ILcStockchangeDao lsom;
	@Resource
	private IScStoreSkunumDao sssm;
	@Resource
	private IPcProductinfoExtDao ppem;
	@Resource
	private IPcProductcategoryRelDao pprm;

	
	public void insertProduct(PcProductinfo pc, RootResult ret, String operator) {

		String createTime = DateUtil.getSysDateTimeString();
		// 插入商品基本信息
		PcProductinfo pptModel = new PcProductinfo();
		pptModel.setUid(UUID.randomUUID().toString().replace("-", ""));
		pptModel.setCreateTime(createTime);
		pptModel.setBrandCode(pc.getBrandCode());
		pptModel.setFlagPayway(pc.getFlagPayway());
		pptModel.setFlagSale(pc.getFlagSale());
		pptModel.setLabels(pc.getLabels());
		pptModel.setMainPicUrl(pc.getMainPicUrl());
		pptModel.setMarketPrice(pc.getMarketPrice());
		pptModel.setMaxSellPrice(pc.getMaxSellPrice());
		pptModel.setMinSellPrice(pc.getMinSellPrice());
		pptModel.setProductCode(pc.getProductCode());
		pptModel.setProductCodeOld(pc.getProductCodeOld() == null ? "" : pc.getProductCodeOld());
		pptModel.setProductName(pc.getProdutName());
		pptModel.setProductStatus(pc.getProductStatus());
		pptModel.setProductVolume(pc.getProductVolume());
		pptModel.setProductVolumeItem(pc.getProductVolumeItem());
		pptModel.setProductWeight(pc.getProductWeight());
		pptModel.setSellerCode(pc.getSellerCode());
		pptModel.setSmallSellerCode(pc.getSmallSellerCode());
		pptModel.setSellProductcode(pc.getSellProductcode());
		pptModel.setTransportTemplate(pc.getTransportTemplate());
		pptModel.setAreaTemplate(pc.getAreaTemplate()); // 限购地区
		pptModel.setUpdateTime(createTime);
		pptModel.setCostPrice(pc.getCostPrice());
		pptModel.setProductShortname(pc.getProductShortname());
		pptModel.setSupplierName(pc.getSupplierName());
		pptModel.setVideoUrl(pc.getVideoUrl());
		pptModel.setValidate_flag(pc.getValidate_flag());// 添加是否是虚拟商品字段
		pptModel.setTaxRate(pc.getTaxRate());
		pptModel.setProductCodeCopy(pc.getProductCodeCopy());
		pptModel.setAdPicUrl(pc.getAdPicUrl());
		pptModel.setExpiryDate(pc.getExpiryDate());// 保质期
		pptModel.setExpiryUnit(pc.getExpiryUnit());// 保质期单位
		pptModel.setQualificationCategoryCode(pc.getQualificationCategoryCode());// 资质品类
		pcpm.insertSelective(pptModel);

		// 添加商品的分类信息
		if (pc.getUsprList() != null && pc.getUsprList().size() > 0) {
			for (int i = 0; i < pc.getUsprList().size(); i++) {
				UcSellercategoryProductRelation usprModel = new UcSellercategoryProductRelation();
				usprModel.setUid(UUID.randomUUID().toString().replace("-", ""));
				usprModel.setCategoryCode(pc.getUsprList().get(i).getCategoryCode());
				usprModel.setProductCode(pc.getUsprList().get(i).getProductCode());
				usprModel.setSellerCode(pc.getUsprList().get(i).getSellerCode());
				usprm.insertSelective(usprModel);
			}
		}
		
		// 添加商品的实类信息
		if (null != pc.getPcProductcategoryRel()) {
			PcProductcategoryRel pprModel = new PcProductcategoryRel();
			pprModel.setCategoryCode(pc.getPcProductcategoryRel().getCategoryCode());
			pprModel.setFlagMain(Long.parseLong(pc.getPcProductcategoryRel().getFlagMain() + ""));
			pprModel.setProductCode(pc.getPcProductcategoryRel().getProductCode());
			pprModel.setUid(UUID.randomUUID().toString().replace("-", ""));
			pprm.insertSelective(pprModel);
		}
		
		// 添加 描述信息
		if (pc.getDescription() != null) {
			PcProductdescription ppdModel = new PcProductdescription();
			ppdModel.setProductCode(pc.getProductCode());
			ppdModel.setKeyword(pc.getDescription().getKeyword());
			ppdModel.setDescriptionPic(pc.getDescription().getDescriptionPic());
			ppdModel.setDescriptionInfo(pc.getDescription().getDescriptionInfo());
			ppdModel.setUid(UUID.randomUUID().toString().replace("-", ""));
			ppsm.insertSelective(ppdModel);
		}

		// 插入商品图片信息
		if (pc.getPcPicList() != null) {
			List<PcProductpic> picList = pc.getPcPicList();
			for (PcProductpic pic : picList) {
				PcProductpic picModel = new PcProductpic();
				picModel.setPicUrl(pic.getPicUrl());
				picModel.setProductCode(pc.getProductCode());
				picModel.setSkuCode(pic.getSkuCode());
				picModel.setUid(UUID.randomUUID().toString().replace("-", ""));
				pppm.insertSelective(picModel);
			}
		}
		
		// 插入sku信息
		if (pc.getProductSkuInfoList() != null) {
			List<ProductSkuInfo> skuList = pc.getProductSkuInfoList();

			for (ProductSkuInfo sku : skuList) {
				PcSkuinfo psModel = new PcSkuinfo();
				psModel.setMarketPrice(sku.getMarketPrice());
				psModel.setProductCode(pc.getProductCode());
				psModel.setProductCodeOld("");
				psModel.setQrcodeLink(sku.getQrcodeLink());
				psModel.setSecurityStockNum(Long.valueOf(sku.getSecurityStockNum()));
				psModel.setSellerCode(sku.getSellerCode());
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
				psModel.setUid(UUID.randomUUID().toString().replace("-", ""));
				psModel.setSaleYn(sku.getSaleYn());
				psModel.setCostPrice(sku.getCostPrice());
				psModel.setMiniOrder(sku.getMiniOrder());
				pcsm.insertSelective(psModel);
				
				// 插入商品sku库存
				if (sku.getScStoreSkunumList() != null) {
					List<ScStoreSkunum> skuStoreList = sku.getScStoreSkunumList();
					for (ScStoreSkunum skuStore : skuStoreList) {
						ScStoreSkunum sssModel = new ScStoreSkunum();
						sssModel.setUid(UUID.randomUUID().toString().replace("-", ""));
						sssModel.setSkuCode(skuStore.getSkuCode());
						sssModel.setStockNum(skuStore.getStockNum());
						sssModel.setStoreCode(skuStore.getStoreCode());
						sssModel.setBatchCode(skuStore.getBatchCode());
						sssm.insertSelective(sssModel);
					}
				}
			}
		}
		
		// 插入商品属性信息
		if (pc.getPcProductpropertyList() != null) {
			List<PcProductproperty> pppList = pc.getPcProductpropertyList();
			for (PcProductproperty ppp : pppList) {
				PcProductproperty pppModel = new PcProductproperty();
				pppModel.setProductCode(pc.getProductCode());
				pppModel.setPropertyCode(ppp.getPropertyCode());
				pppModel.setPropertyKey(ppp.getPropertyKey());
				pppModel.setPropertyKeycode(ppp.getPropertyKeycode());
				pppModel.setPropertyType(ppp.getPropertyType());
				pppModel.setPropertyValue(ppp.getPropertyValue());
				pppModel.setUid(UUID.randomUUID().toString().replace("-", ""));
				pppmr.insertSelective(pppModel);
			}
		}
		
		// 插入商品扩展信息
		if (pc.getPcProductinfoExt() != null) {
			PcProductinfoExt ppe = new PcProductinfoExt();
			ppe.setUid(UUID.randomUUID().toString().replace("-", ""));
			ppe.setProductCode(pc.getPcProductinfoExt().getProductCode());
			ppe.setPrchType(pc.getPcProductinfoExt().getPrchType());
			ppe.setDlrId(pc.getPcProductinfoExt().getDlrId());
			ppe.setDlrNm(pc.getPcProductinfoExt().getDlrNm());
			ppe.setOaSiteNo(pc.getPcProductinfoExt().getOaSiteNo());
			ppe.setValidateFlag(pc.getPcProductinfoExt().getValidateFlag());
			ppe.setProductCodeOld(pc.getProductCodeOld());
			ppe.setProductStoreType(pc.getPcProductinfoExt().getProductStoreType());
			ppe.setProductTradeType(pc.getPcProductinfoExt().getProductTradeType());
			ppe.setPoffer(pc.getPcProductinfoExt().getPoffer());
			
			Integer fictitious = pc.getPcProductinfoExt().getFictitiousSales();
			if(StringUtils.isBlank(fictitious.toString())){
				fictitious = 0;
			}
			ppe.setFictitiousSales(fictitious);	
			String grossProfit = pc.getPcProductinfoExt().getGrossProfit().toString();
			ppe.setGrossProfit(Long.parseLong((StringUtils.isBlank(grossProfit) ? "0" : grossProfit)));
			String accmrng = pc.getPcProductinfoExt().getAccmRng().toString();
			ppe.setAccmRng(Double.parseDouble(StringUtils.isBlank(accmrng) ? "0" : accmrng));
			
			ppe.setMdId(pc.getPcProductinfoExt().getMdId());
			ppe.setMdNm(pc.getPcProductinfoExt().getMdNm());
			ppe.setSettlementType(pc.getPcProductinfoExt().getSettlementType());
			ppe.setPurchaseType(pc.getPcProductinfoExt().getPurchaseType());
			ppe.setPicMaterialUrl(pc.getPcProductinfoExt().getPicMaterialUrl());
			ppe.setPicMaterialUpload(pc.getPcProductinfoExt().getPicMaterialUpload());
			ppe.setKjtSellerCode(pc.getPcProductinfoExt().getKjtSellerCode());
			ppem.insertSelective(ppe);
		}

		// 插入商品历史流水信息
		if (pc.getPcProdcutflow() != null) {
			PcProductflow ppf = new PcProductflow();
			ppf.setCreator(operator);
			ppf.setFlowCode(pc.getPcProdcutflow().getFlowCode());
			ppf.setFlowStatus(pc.getPcProdcutflow().getFlowStatus());
			ppf.setProductCode(pc.getProductCode());
			ppf.setUid(UUID.randomUUID().toString().replace("-", ""));
			ppf.setUpdateTime(createTime);
			ppf.setUpdator(operator);
			JsonHelper<PcProductinfo> pHelper = new JsonHelper<PcProductinfo>();
			ppf.setProductJson(pHelper.ObjToString(pc));
			ppfm.insertSelective(ppf);
		}

		// 插入商品库存流水
		if (pc.getProductSkuInfoList() != null) {
			List<ProductSkuInfo> skuList = pc.getProductSkuInfoList();
			for (ProductSkuInfo sku : skuList) {
				LcStockchange lsModel = new LcStockchange();
				lsModel.setChangeStock(sku.getStockNum());
				lsModel.setChangeType(SkuCommon.SkuStockChangeTypeCreateProduct);
				lsModel.setCode(sku.getSkuCode());
				lsModel.setCreateTime(createTime);
				lsModel.setCreateUser(operator);
				lsModel.setUid(UUID.randomUUID().toString().replace("-", ""));
				lsom.insertSelective(lsModel);
			}
		}
	}

	
	
	
	
	
	
	
}
























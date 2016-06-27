package com.hjy.selleradapter.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hjy.api.RootResult;
import com.hjy.base.BaseClass;
import com.hjy.common.DateUtil;
import com.hjy.dao.ILockDao;
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
import com.hjy.entity.product.PcProductinfo;
import com.hjy.selleradapter.service.ITxProductService;

@Service("txProductService")
public class TxProductServiceImpl extends BaseClass implements ITxProductService{
	
	@Resource
	private IUcSellercategoryProductRelationDao usprm;
//	com.cmall.dborm.txmapper.UcSellercategoryProductRelationMapper usprm = BeansHelper.upBean("bean_com_cmall_dborm_txmapper_UcSellercategoryProductRelationMapper");
	@Resource
	private IPcProductdescriptionDao ppsm ; 
//	com.cmall.dborm.txmapper.PcProductdescriptionMapper ppsm = BeansHelper.upBean("bean_com_cmall_dborm_txmapper_PcProductdescriptionMapper");
	@Resource
	private IPcProductflowDao ppfm;
//	com.cmall.dborm.txmapper.PcProductflowMapper ppfm = BeansHelper.upBean("bean_com_cmall_dborm_txmapper_PcProductflowMapper");
	@Resource
	private IPcSkuinfoDao pcsm;
//	com.cmall.dborm.txmapper.PcSkuinfoMapper pcsm = BeansHelper.upBean("bean_com_cmall_dborm_txmapper_PcSkuinfoMapper");
	@Resource
	private IPcProductinfoDao pcpm;
//	com.cmall.dborm.txmapper.PcProductinfoMapper pcpm = BeansHelper.upBean("bean_com_cmall_dborm_txmapper_PcProductinfoMapper");
	@Resource
	private IPcProductpicDao pppm;
//	com.cmall.dborm.txmapper.PcProductpicMapper pppm = BeansHelper.upBean("bean_com_cmall_dborm_txmapper_PcProductpicMapper");
	@Resource
	private IPcProductpropertyDao pppmr;
//	com.cmall.dborm.txmapper.PcProductpropertyMapper pppmr = BeansHelper.upBean("bean_com_cmall_dborm_txmapper_PcProductpropertyMapper");
	@Resource
	private ILcStockchangeDao lsom;
//	com.cmall.dborm.txmapper.LcStockchangeMapper lsom = BeansHelper.upBean("bean_com_cmall_dborm_txmapper_LcStockchangeMapper");
	@Resource
	private IScStoreSkunumDao sssm;
//	com.cmall.dborm.txmapper.ScStoreSkunumMapper sssm = BeansHelper.upBean("bean_com_cmall_dborm_txmapper_ScStoreSkunumMapper");
	@Resource
	private IPcProductinfoExtDao ppem;
//	com.cmall.dborm.txmapper.PcProductinfoExtMapper ppem = BeansHelper.upBean("bean_com_cmall_dborm_txmapper_PcProductinfoExtMapper");
	@Resource
	private IPcProductcategoryRelDao pprm;
//	com.cmall.dborm.txmapper.PcProductcategoryRelMapper pprm = BeansHelper.upBean("bean_com_cmall_dborm_txmapper_PcProductcategoryRelMapper");
	
	public void insertProduct(PcProductinfo pc, RootResult ret, String operator) {
		
		String createTime = DateUtil.getSysDateTimeString();
		// 插入商品基本信息
		com.hjy.entity.product.cmall.dborm.txmodel.PcProductinfo pptModel = new com.hjy.entity.product.cmall.dborm.txmodel.PcProductinfo();
		pptModel.setUid(UUID.randomUUID().toString().replace("-", ""));
		pptModel.setCreateTime(createTime);
		pptModel.setBrandCode(pc.getBrandCode());
		pptModel.setFlagPayway(pc.getFlagPayway());
		pptModel.setFlagSale(pc.getFlagSale());
		pptModel.setLabels(pc.getLabels());
		pptModel.setMainpicUrl(pc.getMainPicUrl());
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
		pptModel.setValidateFlag(pc.getValidate_flag());// 添加是否是虚拟商品字段
		pptModel.setTaxRate(pc.getTaxRate());
		pptModel.setProductCodeCopy(pc.getProductCodeCopy());
		pptModel.setAdpicUrl(pc.getAdPicUrl());
		pptModel.setExpiryDate(pc.getExpiryDate());// 保质期
		pptModel.setExpiryUnit(pc.getExpiryUnit());// 保质期单位
		pptModel.setQualificationCategoryCode(pc.getQualificationCategoryCode());// 资质品类
		pcpm.insertSelective(pptModel);

		// 添加商品的分类信息
		// if(pc.getCategory()!=null){
		// com.cmall.dborm.txmodel.PcProductcategoryRel ppcrModel = new
		// PcProductcategoryRel();
		// ppcrModel.setCategoryCode(pc.getCategory().getCategoryCode());
		// ppcrModel.setProductCode(pc.getProductCode());
		// ppcrModel.setUid(UUID.randomUUID().toString().replace("-", ""));
		// ppcrModel.setFlagMain(1l);
		// pptrm.insertSelective(ppcrModel);
		// }
		// 添加商品的分类信息
		if (pc.getUsprList() != null && pc.getUsprList().size() > 0) {

			for (int i = 0; i < pc.getUsprList().size(); i++) {
				com.hjy.entity.user.cmall.dborm.txmodel.UcSellercategoryProductRelation usprModel = new UcSellercategoryProductRelation();
				usprModel.setCategoryCode(pc.getUsprList().get(i).getCategoryCode());
				usprModel.setProductCode(pc.getUsprList().get(i).getProductCode());
				usprModel.setSellerCode(pc.getUsprList().get(i).getSellerCode());
				usprModel.setUid(UUID.randomUUID().toString().replace("-", ""));
				usprm.insertSelective(usprModel);
			}
		}
		// 添加商品的实类信息
		if (null != pc.getPcProductcategoryRel()) {
			com.hjy.entity.product.cmall.dborm.txmodel.PcProductcategoryRel pprModel = new PcProductcategoryRel();
			pprModel.setCategoryCode(pc.getPcProductcategoryRel().getCategoryCode());
			pprModel.setFlagMain(Long.parseLong(pc.getPcProductcategoryRel().getFlagMain() + ""));
			pprModel.setProductCode(pc.getPcProductcategoryRel().getProductCode());
			pprModel.setUid(UUID.randomUUID().toString().replace("-", ""));
			pprm.insertSelective(pprModel);
		}
		// 添加 描述信息
		if (pc.getDescription() != null) {
			com.cmall.dborm.txmodel.PcProductdescriptionWithBLOBs ppdModel = new PcProductdescriptionWithBLOBs();
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
				com.hjy.entity.cmall.dborm.txmodel.PcProductpic picModel = new com.hjy.entity.cmall.dborm.txmodel.PcProductpic();
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
				com.cmall.dborm.txmodel.PcSkuinfoWithBLOBs psModel = new PcSkuinfoWithBLOBs();

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
						com.hjy.entity.cmall.dborm.txmodel.ScStoreSkunum sssModel = new com.hjy.entity.cmall.dborm.txmodel.ScStoreSkunum();
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

				com.hjy.entity.cmall.dborm.txmodel.PcProductproperty pppModel = new com.hjy.entity.cmall.dborm.txmodel.PcProductproperty();

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

			com.hjy.entity.product.cmall.dborm.txmodel.PcProductinfoExt ppe = new PcProductinfoExt();
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
			String fictitious = pc.getPcProductinfoExt().getFictitiousSales();
			ppe.setFictitiousSales(Integer.parseInt(StringUtils.isBlank(fictitious) ? "0" : fictitious));
			String grossProfit = pc.getPcProductinfoExt().getGrossProfit();
			ppe.setGrossProfit(Long.parseLong(StringUtils.isBlank(grossProfit) ? "0" : grossProfit));
			String accmrng = pc.getPcProductinfoExt().getAccmRng();
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

			com.hjy.entity.product.cmall.dborm.txmodel.PcProductflow ppf = new PcProductflow();

			ppf.setCreateTime(createTime);
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
				com.cmall.dborm.txmodel.LcStockchange lsModel = new LcStockchange();

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






























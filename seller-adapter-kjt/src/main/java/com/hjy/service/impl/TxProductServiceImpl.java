package com.hjy.service.impl;

import java.util.ArrayList;
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
import com.hjy.entity.product.PcProductpicExample;
import com.hjy.entity.product.PcProductproperty;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.entity.product.ProductChangeFlag;
import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.entity.user.UcSellercategoryProductRelation;
import com.hjy.helper.JsonHelper;
import com.hjy.helper.WebHelper;
import com.hjy.model.ProductSkuInfo;
import com.hjy.service.ITxProductService;

/**
 * @descriptions 这个类的原名称：TxProductService.java  | properties配置信息核对完成
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
			if (fictitious == null ||StringUtils.isBlank(fictitious.toString())) {
				fictitious = 0;
			}
			ppe.setFictitiousSales(fictitious);
			String grossProfit ="";
			if(null != pc.getPcProductinfoExt().getGrossProfit()){
				grossProfit = pc.getPcProductinfoExt().getGrossProfit().toString();
			}
			ppe.setGrossProfit(Long.parseLong((StringUtils.isBlank(grossProfit) ? "0" : grossProfit)));
			String accmrng = "";
			if(null != pc.getPcProductinfoExt().getAccmRng()){
				accmrng = pc.getPcProductinfoExt().getAccmRng().toString();
			}
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

	public void updateProduct(PcProductinfo pc, RootResult ret, String operator, ProductChangeFlag pcf) {
		// 创建时间
		String createTime = DateUtil.getSysDateTimeString();
		// 更新商品的基本信息
		PcProductinfo ppModel = new PcProductinfo();

		ppModel.setProductCode(pc.getProductCode());
		ppModel.setProductName(pc.getProdutName());
		ppModel.setMarketPrice(pc.getMarketPrice());
		ppModel.setBrandCode(pc.getBrandCode());
		ppModel.setProductWeight(pc.getProductWeight());
		ppModel.setProductVolume(pc.getProductVolume());
		ppModel.setProductVolumeItem(pc.getProductVolumeItem());
		ppModel.setSellProductcode(pc.getSellProductcode());
		ppModel.setLabels(pc.getLabels());
		ppModel.setTransportTemplate(pc.getTransportTemplate());
		ppModel.setFlagPayway(pc.getFlagPayway());
		ppModel.setCostPrice(pc.getCostPrice());
		ppModel.setProductShortname(pc.getProductShortname());
		ppModel.setSupplierName(pc.getSupplierName());
		ppModel.setVideoUrl(pc.getVideoUrl());
		ppModel.setTaxRate(pc.getTaxRate());// 税率

		ppModel.setValidate_flag(pc.getValidate_flag());// 添加 是否是虚拟商品 字段
		ppModel.setUpdateTime(createTime);
		ppModel.setProductCodeCopy(pc.getProductCodeCopy());
		ppModel.setMinSellPrice(pc.getMinSellPrice());
		ppModel.setMaxSellPrice(pc.getMaxSellPrice());

		ppModel.setAdPicUrl(pc.getAdPicUrl()); // 广告图
		ppModel.setProductAdv(pc.getProductAdv()); // 商品广告“卖点”
		ppModel.setAreaTemplate(pc.getAreaTemplate()); // 限购地区
		ppModel.setExpiryDate(pc.getExpiryDate()); // 保质期
		ppModel.setExpiryUnit(pc.getExpiryUnit()); // 保质期单位
		ppModel.setQualificationCategoryCode(pc.getQualificationCategoryCode());// 资质品类

		// 更新商品的扩展信息
		PcProductinfoExt ppExtModel = new PcProductinfoExt();
		// ppExtModel.setUid(pc.getPcProductinfoExt().getUid());
		ppExtModel.setProductCode(pc.getPcProductinfoExt().getProductCode());
		ppExtModel.setPrchType(pc.getPcProductinfoExt().getPrchType());
		ppExtModel.setDlrId(pc.getPcProductinfoExt().getDlrId());
		ppExtModel.setDlrNm(pc.getPcProductinfoExt().getDlrNm());
		ppExtModel.setOaSiteNo(pc.getPcProductinfoExt().getOaSiteNo());
		String grossProfit = pc.getPcProductinfoExt().getGrossProfit().toString();
		String accmRng = pc.getPcProductinfoExt().getAccmRng().toString();
		String fictitiousSales = pc.getPcProductinfoExt().getFictitiousSales().toString();
		ppExtModel.setGrossProfit((null == grossProfit || "".equals(grossProfit)) ? 0L : Long.parseLong(grossProfit));
		ppExtModel.setAccmRng((null == accmRng || "".equals(accmRng)) ? 0.0 : Double.parseDouble(accmRng));
		ppExtModel.setValidateFlag(pc.getPcProductinfoExt().getValidateFlag());
		ppExtModel.setMdId(pc.getPcProductinfoExt().getMdId());
		ppExtModel.setMdNm(pc.getPcProductinfoExt().getMdNm());
		ppExtModel.setKjtSellerCode(pc.getPcProductinfoExt().getKjtSellerCode());

		// 暂时只支持家有汇修改虚拟销售量基数（2015-08-13开始支持惠家有与沙皮狗）
		try {
			ppExtModel.setFictitiousSales(
					(null == fictitiousSales || "".equals(fictitiousSales)) ? 0 : Integer.parseInt(fictitiousSales));
		} catch (NumberFormatException e) {
			ppExtModel.setFictitiousSales(0);
		}
		ppExtModel.setSettlementType(pc.getPcProductinfoExt().getSettlementType()); // 结算方式
																					// //
																					// 2015-08-24添加
		ppExtModel.setPurchaseType(pc.getPcProductinfoExt().getPurchaseType()); // 采购类型2015-10-08添加
		ppExtModel.setPicMaterialUrl(pc.getPcProductinfoExt().getPicMaterialUrl()); // 图片相关素材地址2015-12-03添加
		ppExtModel.setPicMaterialUpload(pc.getPcProductinfoExt().getPicMaterialUpload());// 图片相关素材上传2015-12-03添加

		// 商品扩展属性(暂只支持家有汇)(2015-08-13开始支持惠家有与沙皮狗)
		if (pcf.isChangeProductExt() && ("SI2009".equals(pc.getSellerCode()) || "SI2003".equals(pc.getSellerCode())
				|| "SI3003".equals(pc.getSellerCode()))) {
			// PcProductinfoExtExample extExample = new
			// PcProductinfoExtExample();
			// extExample.createCriteria().andProductCodeEqualTo(ppModel.getProductCode());
			// ppem.updateByExampleSelective(ppExtModel, extExample);
			ppem.updateSelectiveByProductCode(ppExtModel);
		}

		if (pcf.isChangeProductPic()) {
			// 图片操作--先删除--再添加
			PcProductpicExample ppeModel = new PcProductpicExample();
			ppeModel.createCriteria().andProductCodeEqualTo(pc.getProductCode());
			pppm.deleteByExample(ppeModel);
			// 插入商品图片信息
			if (pc.getPcPicList() != null) {
				// 如果商品主图不为空和轮播图列表不为0的时候商品主图为轮播图第一张
				// edit 内容 （新增判断条件(null == pc.getMainPicUrl()
				// ||"".equals(pc.getMainPicUrl())）
				if ((null == pc.getMainPicUrl() || "".equals(pc.getMainPicUrl())) && pc.getPcPicList().size() > 0) {
					ppModel.setMainPicUrl(pc.getPcPicList().get(0).getPicUrl());
				} else {
					ppModel.setMainPicUrl(pc.getMainPicUrl());
				}

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
		}

		if (pcf.isChangeProductMain()) {
			pcpm.updateSelectiveByProductCode(ppModel);
		}

		if (pcf.isChangeProductSku()) {
			// 插入或者更新sku信息
			if (pc.getProductSkuInfoList() != null) {
				List<ProductSkuInfo> skuList = pc.getProductSkuInfoList();
				for (int i = 0; i < skuList.size(); i++) {
					ProductSkuInfo sku = skuList.get(i);
					// 新增DSF开头，只是用来对第三方商户商品做特殊判断，入库时需要把DSF去掉，并且skuCode无须再次生成--lgj
					if (sku.getSkuCode() == null || sku.getSkuCode().equals("") || sku.getSkuCode().startsWith("WSP")
							|| sku.getSkuCode().startsWith("DSF")) {
						if (sku.getSkuKey() == null || sku.getSkuKey().equals("")) {
							continue;
						}
						PcSkuinfo psModel = new PcSkuinfo();
						psModel.setSellerCode(pc.getSellerCode());
						if (sku.getSkuCode().startsWith("DSF")) {
							pc.getProductSkuInfoList().get(i).setSkuCode(sku.getSkuCode().replace("DSF", ""));
							psModel.setSkuCode(sku.getSkuCode().replace("DSF", ""));
						} else {
							psModel.setSkuCode(WebHelper.getInstance().genUniqueCode(SkuCommon.SKUHead));
						}
						psModel.setSkuCodeOld(sku.getSkuCodeOld());
						pc.getProductSkuInfoList().get(i).setSkuCode(psModel.getSkuCode());
						psModel.setMarketPrice(sku.getMarketPrice());
						psModel.setProductCode(pc.getProductCode());
						psModel.setProductCodeOld("");
						psModel.setQrcodeLink(sku.getQrcodeLink());
						psModel.setSecurityStockNum(Long.valueOf(sku.getSecurityStockNum()));
						psModel.setSellPrice(sku.getSellPrice());
						psModel.setSellProductcode(sku.getSellProductcode());
						psModel.setSkuKey(sku.getSkuKey());
						psModel.setSkuKeyvalue(sku.getSkuValue());
						psModel.setSkuName(sku.getSkuName());
						psModel.setSkuAdv(sku.getSkuAdv());
						psModel.setStockNum(Long.valueOf(sku.getStockNum()));
						psModel.setUid(UUID.randomUUID().toString().replace("-", ""));
						psModel.setBarcode(sku.getBarcode());
						psModel.setMiniOrder(sku.getMiniOrder());
						psModel.setCostPrice(sku.getCostPrice());
						psModel.setStockNum(Long.parseLong(sku.getStockNum() + ""));

						if (sku.getSkuPicUrl() == null || sku.getSkuPicUrl().equals("")) {
							psModel.setSkuPicurl(pc.getMainPicUrl());
						} else {
							psModel.setSkuPicurl(sku.getSkuPicUrl());
						}

						pcsm.insertSelective(psModel);

						if (sku.getScStoreSkunumList() != null) {
							int changeStock = 0; // 库存变化总量
							for (ScStoreSkunum scStore : sku.getScStoreSkunumList()) {
								// 插入库存
								ScStoreSkunum sssModel = new ScStoreSkunum();
								sssModel.setUid(UUID.randomUUID().toString().replace("-", ""));
								sssModel.setSkuCode(psModel.getSkuCode()); // 此处取skuCode一定要取psModel里面的。
								sssModel.setStockNum(scStore.getStockNum());
								sssModel.setStoreCode(scStore.getStoreCode());
								sssm.insertSelective(sssModel);
								changeStock += Integer.parseInt(scStore.getStockNum() + "");
							}
							LcStockchange lsModel = new LcStockchange();
							lsModel.setChangeStock(changeStock);
							lsModel.setChangeType(SkuCommon.SkuStockChangeTypeCreateProduct);
							lsModel.setCode(psModel.getSkuCode());
							lsModel.setCreateTime(createTime);
							lsModel.setCreateUser(operator);
							lsModel.setUid(UUID.randomUUID().toString().replace("-", ""));
							lsom.insertSelective(lsModel);
						}
					} else {
						PcSkuinfo psModel = new PcSkuinfo();
						psModel.setSkuCode(sku.getSkuCode());
						psModel.setSkuCodeOld(sku.getSkuCodeOld());
						psModel.setSkuAdv(sku.getSkuAdv());
						psModel.setSecurityStockNum(Long.valueOf(sku.getSecurityStockNum()));
						psModel.setSkuName(sku.getSkuName());
						psModel.setSellPrice(sku.getSellPrice());
						psModel.setSellProductcode(sku.getSellProductcode());
						psModel.setBarcode(sku.getBarcode());
						psModel.setMiniOrder(sku.getMiniOrder());
						psModel.setSaleYn(sku.getSaleYn());
						psModel.setCostPrice(sku.getCostPrice());
						psModel.setStockNum(Long.parseLong(sku.getStockNum() + ""));
						// 解决商户后台草稿箱中被驳回的商品修改规格型号名字不存库的bug
						psModel.setSkuKey(sku.getSkuKey());
						psModel.setSkuKeyvalue(sku.getSkuKeyvalue());
						if (sku.getSkuPicUrl() == null || sku.getSkuPicUrl().equals("")) {
							psModel.setSkuPicurl(pc.getMainPicUrl());
						} else {
							psModel.setSkuPicurl(sku.getSkuPicUrl());
						}
						// PcSkuinfoExample psexample = new PcSkuinfoExample();
						// psexample.createCriteria().andSkuCodeEqualTo(sku.getSkuCode());
						// pcsm.updateByExampleSelective(psModel, psexample);
						pcsm.updateSelectiveBySkuCode(psModel);

						if (sku.getScStoreSkunumList() != null) {
							int changeStock = 0; // 库存变化总量
							int oldStock = 0;
							int nowStock = 0;
							boolean flagModStock = false;
							for (ScStoreSkunum scStore : sku.getScStoreSkunumList()) {
								// 更新库存
								ScStoreSkunum sssModel = new ScStoreSkunum();
								sssModel.setSkuCode(sku.getSkuCode());
								sssModel = sssm.findByType(sssModel);
								if (sssModel != null) {
									sssModel.setSkuCode(sku.getSkuCode());
									sssModel.setStockNum(scStore.getStockNum());
									sssModel.setStoreCode(scStore.getStoreCode());
									nowStock = Integer.parseInt(scStore.getStockNum() + "");
									oldStock = Integer.parseInt(sssModel.getStockNum() + ""); // mDataMap.get("stock_num")
								}
								changeStock += (nowStock - oldStock);
								if (nowStock != oldStock) {
									sssm.updateSelectiveByUuid(sssModel);
									flagModStock = true;
								}
								// 这个if必须写在这里。不能再这个if (nowStock != oldStock )
								// {}上面。
								if (null == sssModel) {
									// 如果没有库存则添加
									sssModel.setUid(UUID.randomUUID().toString().replace("-", ""));
									sssModel.setSkuCode(psModel.getSkuCode()); // 此处取skuCode一定要取psModel里面的。
									sssModel.setStockNum(scStore.getStockNum());
									sssModel.setStoreCode(scStore.getStoreCode());
									sssm.insertSelective(sssModel);
									nowStock = Integer.parseInt(scStore.getStockNum() + "");
									flagModStock = true;
								}
							}
							if (flagModStock) {
								LcStockchange lsModel = new LcStockchange();
								lsModel.setCode(sku.getSkuCode());
								lsModel.setCreateTime(createTime);
								lsModel.setCreateUser(operator);
								lsModel.setChangeStock(changeStock);
								lsModel.setOldStock(oldStock);
								lsModel.setNowStock(nowStock);
								lsModel.setInfo("商品库存被修改！");
								lsModel.setChangeType(SkuCommon.SkuStockChangeTypeChangeProduct);
								lsModel.setUid(UUID.randomUUID().toString().replace("-", ""));
								lsom.insertSelective(lsModel);
							}
						}

					}
				}
			}
		}

		if (pcf.isChangeSkuPropertyMain()) {
			// 属性信息操作--先删除--再添加
			PcProductproperty ppteample = new PcProductproperty();
			List<String> values = new ArrayList<String>();
			values.add("449736200001");
			values.add("449736200002");
			ppteample.setPropertyTypeList(values);
			ppteample.setProductCode(pc.getProductCode());
			pppmr.deleteByParam(ppteample);
			// 插入商品属性信息
			if (pc.getPcProductpropertyList() != null) {
				List<PcProductproperty> pppList = pc.getPcProductpropertyList();
				for (PcProductproperty ppp : pppList) {
					if (ppp.getPropertyType().equals("449736200001") || ppp.getPropertyType().equals("449736200002")) {
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
			}
		}

		if (pcf.isChangeSkuPropertySub()) {
			// 属性信息操作--先删除--再添加
			PcProductproperty ppteample = new PcProductproperty();
			List<String> values = new ArrayList<String>();
			values.add("449736200003");
			values.add("449736200004");
			ppteample.setPropertyTypeList(values);
			ppteample.setProductCode(pc.getProductCode());
			pppmr.deleteByParam(ppteample);

			// 插入商品属性信息
			if (pc.getPcProductpropertyList() != null) {
				List<PcProductproperty> pppList = pc.getPcProductpropertyList();
				for (PcProductproperty ppp : pppList) {
					if (ppp.getPropertyType().equals("449736200003") || ppp.getPropertyType().equals("449736200004")) {
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
			}

		}

		if (pcf.isChangeProductFlow()) {
			// 插入商品历史流水信息
			if (pc.getPcProdcutflow() != null) {
				PcProductflow ppf = new PcProductflow();
				ppf.setCreateTime(createTime);
				ppf.setCreator(operator);
				ppf.setFlowCode(pc.getPcProdcutflow().getFlowCode());
				ppf.setFlowStatus(pc.getPcProdcutflow().getFlowStatus());
				ppf.setProductCode(pc.getProductCode());
				JsonHelper<PcProductinfo> pHelper = new JsonHelper<PcProductinfo>();
				ppf.setProductJson(pHelper.ObjToString(pc));
				ppf.setUid(UUID.randomUUID().toString().replace("-", ""));
				ppf.setUpdateTime(createTime);
				ppf.setUpdator(operator);
				ppfm.insertSelective(ppf);
			}
		}

		if (pcf.isChangeDescription()) {
			// 描述信息操作--先删除--再添加
			PcProductdescription pd = new PcProductdescription();
			pd.setProductCode(pc.getProductCode());
			ppsm.deleteByProductCode(pd);
			// 添加 描述信息
			if (pc.getDescription() != null) {
				PcProductdescription ppdModel = new PcProductdescription();
				ppdModel.setProductCode(pc.getProductCode());
				ppdModel.setKeyword(pc.getDescription().getKeyword());
				ppdModel.setDescriptionInfo(pc.getDescription().getDescriptionInfo());
				ppdModel.setDescriptionPic(pc.getDescription().getDescriptionPic());
				ppdModel.setUid(UUID.randomUUID().toString().replace("-", ""));
				ppsm.insertSelective(ppdModel);
			}

		}
		if (pcf.isChangeProductCategory()) {
			if (pc.getUsprList() != null && pc.getUsprList().size() > 0) {
				UcSellercategoryProductRelation spr = new UcSellercategoryProductRelation();
				spr.setProductCode(pc.getProductCode());
				usprm.deleteByProductCode(spr);

				for (int i = 0; i < pc.getUsprList().size(); i++) {
					UcSellercategoryProductRelation usprModel = new UcSellercategoryProductRelation();
					usprModel.setCategoryCode(pc.getUsprList().get(i).getCategoryCode());
					usprModel.setProductCode(pc.getProductCode());
					usprModel.setSellerCode(pc.getSellerCode());
					usprModel.setUid(UUID.randomUUID().toString().replace("-", ""));
					usprm.insertSelective(usprModel);
				}
			}
		}
	}

}

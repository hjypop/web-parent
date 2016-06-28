package com.hjy.selleradapter.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.hjy.annotation.Inject;
import com.hjy.api.RootResult;
import com.hjy.base.BaseClass;
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
import com.hjy.entity.product.PcBrandinfo;
import com.hjy.entity.product.PcCategoryinfo;
import com.hjy.entity.product.PcProductcategoryRel;
import com.hjy.entity.product.PcProductdescription;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcProductinfoExt;
import com.hjy.entity.product.PcProductpic;
import com.hjy.entity.product.PcProductproperty;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.entity.user.UcSellercategoryProductRelation;
import com.hjy.helper.JsonHelper;
import com.hjy.iface.IFlowFunc;
import com.hjy.jms.ProductJmsSupport;
import com.hjy.model.MDataMap;
import com.hjy.model.ProductSkuInfo;
import com.hjy.selleradapter.service.IProductService;
import com.hjy.selleradapter.service.ITxProductService;
import com.hjy.support.SerializeSupport;

public class ProductServiceImpl extends BaseClass implements IFlowFunc, IProductService {

	@Inject
	private ITxProductService txs; // TxProductServiceImpl txs = BeansHelper.upBean("bean_com_cmall_productcenter_txservice_TxProductService");
	@Inject
	private IPcProductflowDao pcpFlowdao;
	@Inject
	private IPcProductinfoDao pcProductInfoDao;
	@Inject
	private IPcProductcategoryRelDao pcProductcategoryRelDao;
	@Inject
	private IPcProductdescriptionDao pcProductdescriptionDao;
	@Inject
	private IPcProductpicDao pcProductpicDao;
	@Inject
	private IPcProductpropertyDao pcProductpropertyDao;  
	@Inject
	private IPcSkuinfoDao pcSkuinfoDao;
	@Inject
	private IPcBrandinfoDao pcBrandinfoDao;
	
	@Inject
	private IPcProductinfoExtDao pcProductinfoExtDao;
	
	@Inject
	private IUcSellercategoryProductRelationDao ucSellercategoryProductRelationDao; 
	
	


	public int AddProductTx(PcProductinfo pc, StringBuffer error, String manageCode) {
		RootResult rr = new RootResult();
		try {
			txs.insertProduct(pc, rr, manageCode);
		} catch (Exception e) {
			e.printStackTrace();
			rr.setCode(941901049);
			rr.setMessage(getInfo(941901049, e.getMessage()));
		}
		try {
			// 校验输入的数据合法性
			ProductJmsSupport pjs = new ProductJmsSupport();
			pjs.onChangeProductText(pc.getProductCode());
			this.genarateJmsStaticPageForProduct(pc);
		} catch (Exception e) {
		}
		error.append(rr.getMessage());
		return rr.getCode();
	}

	/**
	 * 生成静态页面 通过商品
	 * 
	 * @param product
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

	public PcProductinfo getProduct(String productCode) {
		try {
			PcProductinfo product = new PcProductinfo();
			if(productCode.trim().length() == 32){
				product.setUid(productCode); 
			}else{
				product.setProductCode(productCode); 
			}
			product = pcProductInfoDao.findByType(product);

			if (product == null){
				return null;
			}else {
				productCode = product.getProductCode();

				PcProductcategoryRel ppcr = new PcProductcategoryRel();
				ppcr.setProductCode(productCode);
				ppcr.setFlagMain(Long.valueOf("1")); 
				ppcr = pcProductcategoryRelDao.findByType(ppcr);
				// 取得商品分类信息
				PcCategoryinfo category = new PcCategoryinfo();
				if (ppcr != null) {
					category.setCategoryCode( ppcr.getCategoryCode());  
				}
				product.setCategory(category);
				
				PcProductdescription description = new PcProductdescription();
				description.setProductCode(productCode);
				description = pcProductdescriptionDao.findByType(description);
				// 取得商品描述信息
				if (description != null)
					product.setDescription(description);

				// 图片
				PcProductpic pic = new PcProductpic();
				pic.setProductCode(productCode); 
				List<PcProductpic> pcPicList = pcProductpicDao.findListBySkuNull(pic);
				if (pcPicList != null)
					product.setPcPicList(pcPicList);

				
				// 取得商品属性信息
				PcProductproperty property = new PcProductproperty();
				property.setProductCode(productCode); 
				List<PcProductproperty> pcProductpropertyList = pcProductpropertyDao.findListByProductCode(property); 
				if (pcProductpropertyList != null)
					product.setPcProductpropertyList(pcProductpropertyList);

				
				// 取得商品的sku信息
				PcSkuinfo skuInfo = new PcSkuinfo();
				skuInfo.setProductCode(productCode); 
				List<PcSkuinfo> skuInfoList = pcSkuinfoDao.findList(skuInfo);
				if (skuInfoList != null && skuInfoList.size() > 0) {
					List <ProductSkuInfo> productSkuInfoList = new ArrayList<ProductSkuInfo>();
					for(PcSkuinfo sku : skuInfoList){
						ProductSkuInfo info = new ProductSkuInfo();
						info.setUid(sku.getUid()); 
						info.setSkuCode(sku.getSkuCode());
						info.setSkuCodeOld(sku.getSkuCodeOld());
						info.setProductCode(sku.getProductCode());
						info.setSellPrice(sku.getSellPrice());
						info.setMarketPrice(sku.getMarketPrice());
						info.setCostPrice(sku.getCostPrice());
						info.setStockNum(Integer.valueOf(sku.getStockNum().toString()));
						info.setSkuKey(sku.getSkuKey());
//						info.setSkuValue("");// 数据库无此字段
						info.setSkuKeyvalue(sku.getSkuKeyvalue());
						info.setSkuPicUrl(sku.getSkuPicurl());
						info.setSkuName(sku.getSkuName());
						info.setSellProductcode(sku.getSellProductcode());
						info.setSecurityStockNum(Integer.valueOf(sku.getSecurityStockNum().toString()));
						info.setSellerCode(sku.getSellerCode());
						info.setSkuAdv(sku.getSkuAdv());
						info.setQrcodeLink(sku.getQrcodeLink());
						info.setVirtualMoneyDeduction(new BigDecimal(0.00)); // 数据库无此字段
						info.setSellCount(sku.getSellCount());
						info.setSaleYn(sku.getSaleYn());
						info.setFlagEnable(sku.getFlagEnable().toString());
						info.setBarcode(sku.getBarcode());
						info.setMiniOrder(sku.getMiniOrder());
//						info.setScStoreSkunumList();// 数据库无此字段
//						info.setValidateFlag("");// 数据库无此字段
//						info.setSmallSellerCode("");// 数据库无此字段
						productSkuInfoList.add(info);
					}
					product.setProductSkuInfoList(productSkuInfoList);
				}

				PcBrandinfo brandInfo = new PcBrandinfo();
				brandInfo.setBrandCode(product.getBrandCode()); 
				brandInfo = pcBrandinfoDao.findByType(brandInfo);
				if (brandInfo != null) {
					product.setBrandName(brandInfo.getBrandName());
				}

				// 商品的扩展属性
				PcProductinfoExt pExt = new PcProductinfoExt();
				pExt.setProductCode(productCode); 
				pExt = pcProductinfoExtDao.findByType(pExt);
				if (pExt != null) {
					product.setPcProductinfoExt(pExt);
				}
				
				// 商品虚类
				UcSellercategoryProductRelation spr = new UcSellercategoryProductRelation();
				spr.setProductCode(productCode); 
				List<UcSellercategoryProductRelation> sprList = ucSellercategoryProductRelationDao.findList(spr);
				if(sprList == null ){
					sprList = new ArrayList<UcSellercategoryProductRelation>();
				}
				product.setUsprList(sprList);
			}

			return product;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public RootResult BeforeFlowChange(String flowCode, String outCode, String fromStatus, String toStatus,
			MDataMap mSubMap) {

		return null;
	}

	@Override
	public RootResult afterFlowChange(String flowCode, String outCode, String fromStatus, String toStatus,
			MDataMap mSubMap) {

		return null;
	}

}












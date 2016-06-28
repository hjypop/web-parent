package com.hjy.selleradapter.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hjy.annotation.Inject;
import com.hjy.api.RootResult;
import com.hjy.base.BaseClass;
import com.hjy.entity.product.PcCategoryinfo;
import com.hjy.entity.product.PcProductdescription;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcProductinfoExt;
import com.hjy.entity.product.PcProductpic;
import com.hjy.entity.product.PcProductproperty;
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
			
			if (productCode == null || productCode.length() <= 2){
				return null;
			}else {
				if (productCode.indexOf("_1") > 0) {
					productCode = productCode.substring(0, productCode.length() - 2);
					MDataMap productData = new MDataMap();

					List<MDataMap> productDataList = DbUp.upTable("pc_productflow").query("", "zid desc", "product_code='" + productCode + "'", null, 0, 1);

					if (null != productDataList && productDataList.size() > 0) {
						productData = productDataList.get(0);
					}

					if (productData == null)
						return null;

					String pValue = productData.get("product_json");
					JsonHelper<PcProductinfo> pHelper = new JsonHelper<PcProductinfo>();
					product = pHelper.StringToObj(pValue, product);

					return product;
				}
			}

			MDataMap prodcutData = null;
			if (productCode.trim().length() == 32) {
				prodcutData = DbUp.upTable("pc_productinfo").one("uid", productCode);
			} else {
				prodcutData = DbUp.upTable("pc_productinfo").one("product_code", productCode);
			}

			if (prodcutData == null)
				return null;
			else {
				productCode = prodcutData.get("product_code");
				product = new SerializeSupport<PcProductinfo>().serialize(prodcutData, new PcProductinfo());

				product.setProdutName(prodcutData.get("product_name"));

				MDataMap pcCategorypropertyRelData = DbUp.upTable("pc_productcategory_rel").one("product_code",
						productCode, "flag_main", "1");
				// 取得商品分类信息
				PcCategoryinfo category = new PcCategoryinfo();
				if (pcCategorypropertyRelData != null) {
					category.setCategoryCode(pcCategorypropertyRelData.get("category_code"));
				}

				product.setCategory(category);

				PcProductdescription description = null;
				MDataMap pcProductdescriptionData = DbUp.upTable("pc_productdescription").one("product_code" , productCode);

				if (pcProductdescriptionData != null && !pcProductdescriptionData.isEmpty()) {
					description = new SerializeSupport<PcProductdescription>().serialize(pcProductdescriptionData , new PcProductdescription());
				}

				// 取得商品描述信息
				if (description != null)
					product.setDescription(description);

				MDataMap pcPicListMapParam = new MDataMap();
				pcPicListMapParam.put("product_code", productCode);
				List<PcProductpic> pcPicList = null;
				List<MDataMap> pcPicListMap = DbUp.upTable("pc_productpic").query("", "", "product_code=:product_code  and (sku_code='' or sku_code is null)", pcPicListMapParam, -1, -1);
				if (pcPicListMap != null) {
					int size = pcPicListMap.size();
					pcPicList = new ArrayList<PcProductpic>();
					SerializeSupport ss = new SerializeSupport<PcProductpic>();
					for (int i = 0; i < size; i++) {
						PcProductpic pic = new PcProductpic();
						ss.serialize(pcPicListMap.get(i), pic);
						pcPicList.add(pic);
					}
				}

				if (pcPicList != null)
					product.setPcPicList(pcPicList);

				// 取得商品属性信息
				MDataMap pcProductpropertyListMapParam = new MDataMap();
				pcProductpropertyListMapParam.put("product_code", productCode);
				List<PcProductproperty> pcProductpropertyList = null;
				List<MDataMap> pcProductpropertyListMap = DbUp.upTable("pc_productproperty").query("" , " property_type,small_sort desc,zid asc ", "product_code=:product_code", pcProductpropertyListMapParam, -1, -1);
				if (pcProductpropertyListMap != null) {
					int size = pcProductpropertyListMap.size();
					pcProductpropertyList = new ArrayList<PcProductproperty>();
					SerializeSupport ss = new SerializeSupport<PcProductproperty>();
					for (int i = 0; i < size; i++) {
						PcProductproperty pic = new PcProductproperty();
						ss.serialize(pcProductpropertyListMap.get(i), pic);
						pcProductpropertyList.add(pic);
					}
				}
				if (pcProductpropertyList != null)
					product.setPcProductpropertyList(pcProductpropertyList);

				// 取得商品的sku信息
				MDataMap pcSkuMapParam = new MDataMap();
				pcSkuMapParam.put("product_code", productCode);
				List<ProductSkuInfo> productSkuInfoList = null;
				List<MDataMap> productSkuInfoListMap = DbUp.upTable("pc_skuinfo").query("" ,  "" , "product_code=:product_code", pcSkuMapParam, -1, -1);

				if (productSkuInfoListMap != null) {
					int size = productSkuInfoListMap.size();
					productSkuInfoList = new ArrayList<ProductSkuInfo>();
					SerializeSupport ss = new SerializeSupport<ProductSkuInfo>();
					for (int i = 0; i < size; i++) {
						ProductSkuInfo pic = new ProductSkuInfo();
						ss.serialize(productSkuInfoListMap.get(i), pic);
						pic.setSkuValue(productSkuInfoListMap.get(i).get("sku_keyvalue"));
						productSkuInfoList.add(pic);
					}
				}

				if (productSkuInfoList != null) {
					product.setProductSkuInfoList(productSkuInfoList);
				}

				MDataMap brandMapParam = new MDataMap();
				brandMapParam = DbUp.upTable("pc_brandinfo").one("brand_code", product.getBrandCode());
				if (brandMapParam != null) {
					product.setBrandName(brandMapParam.get("brand_name"));
				}

				// 商品的扩展属性
				MDataMap fictitiousSalesMap = DbUp.upTable("pc_productinfo_ext").one("product_code", productCode);
				if (fictitiousSalesMap != null) {
					SerializeSupport<PcProductinfoExt> ss = new SerializeSupport<PcProductinfoExt>();
					PcProductinfoExt pcProductinfoExt = new PcProductinfoExt();
					ss.serialize(fictitiousSalesMap, pcProductinfoExt);
					product.setPcProductinfoExt(pcProductinfoExt);
				}
				
				// 商品虚类
				List<MDataMap> categoryProductRelationMap = DbUp.upTable("uc_sellercategory_product_relation").queryAll("", "", "product_code='" + productCode + "'", null);
				List<UcSellercategoryProductRelation> categoryProductRelationList = new ArrayList<UcSellercategoryProductRelation>();
				SerializeSupport<UcSellercategoryProductRelation> ss = new SerializeSupport<UcSellercategoryProductRelation>();
				for (MDataMap mDataMap : categoryProductRelationMap) {
					UcSellercategoryProductRelation categoryProductRelation = new UcSellercategoryProductRelation();
					ss.serialize(mDataMap, categoryProductRelation);
					categoryProductRelationList.add(categoryProductRelation);
				}
				product.setUsprList(categoryProductRelationList);
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












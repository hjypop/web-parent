package com.hjy.selleradapter.service;

import com.hjy.api.RootResult;
import com.hjy.base.BaseClass;
import com.hjy.iface.IFlowFunc;
import com.hjy.jms.ProductJmsSupport;
import com.hjy.model.MDataMap;
import com.hjy.selleradapter.kjt.model.PcProductinfo;

public class ProductService extends BaseClass implements IFlowFunc {
	
	public int AddProductTx(PcProductinfo pc , StringBuffer error , String manageCode){
		RootResult rr= new RootResult();
		TxProductService txs = BeansHelper.upBean("bean_com_cmall_productcenter_txservice_TxProductService");
		try {
			txs.insertProduct(pc, rr, manageCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	public void genarateJmsStaticPageForProduct(PcProductinfo product) {
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

	@Override
	public RootResult BeforeFlowChange(String flowCode, String outCode, String fromStatus, String toStatus, MDataMap mSubMap) {
		
		return null;
	}

	@Override
	public RootResult afterFlowChange(String flowCode, String outCode, String fromStatus, String toStatus, MDataMap mSubMap) {
		
		return null;
	}

}

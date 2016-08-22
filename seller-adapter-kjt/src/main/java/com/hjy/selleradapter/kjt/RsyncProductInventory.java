package com.hjy.selleradapter.kjt;

import java.util.ArrayList;
import java.util.UUID;

import com.hjy.annotation.Inject;
import com.hjy.constant.MemberConst;
import com.hjy.entity.product.PcProductinfoExt;
import com.hjy.entity.system.ScStore;
import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.helper.PlusHelperNotice;
import com.hjy.jms.ProductJmsSupport;
import com.hjy.model.MWebResult;
import com.hjy.model.RsyncDateCheck;
import com.hjy.model.RsyncResult;
import com.hjy.selleradapter.kjt.config.RsyncConfigInventory;
import com.hjy.selleradapter.kjt.request.RsyncRequestInventory;
import com.hjy.selleradapter.kjt.response.RsyncResponseInventory;
import com.hjy.selleradapter.kjt.response.RsyncResponseInventory.Data;
import com.hjy.service.product.IPcProductinfoExtService;
import com.hjy.service.product.IPcProductinfoServivce;
import com.hjy.service.product.IPcSkuinfoService;
import com.hjy.service.system.IScStoreService;
import com.hjy.service.system.IScStoreSkunumService;

/**
 * alias RsyncGetKjtProductChannelInventoryById<br>
 * | properties配置信息核对完成 类: RsyncProductInventory <br>
 * 描述: 商品分销渠道库存批量获取 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月27日 下午5:29:53
 */
public class RsyncProductInventory
		extends RsyncKjt<RsyncConfigInventory, RsyncRequestInventory, RsyncResponseInventory> {
	final static RsyncConfigInventory CONFIG_GET_TV_BY_ID = new RsyncConfigInventory();
	@Inject
	private IPcProductinfoServivce productInfoService;
	@Inject
	private IPcSkuinfoService skuInfoService;
	@Inject
	private IScStoreSkunumService scStoreSkunumService;
	@Inject
	private IScStoreService scStoreService;
	@Inject
	private IPcProductinfoExtService pcProductinfoExtService;

	public RsyncConfigInventory upConfig() {
		return CONFIG_GET_TV_BY_ID;
	}

	RsyncRequestInventory ChannelInventoryById = new RsyncRequestInventory();

	public RsyncRequestInventory upRsyncRequest() {
		return ChannelInventoryById;
	}

	public RsyncResult doProcess(RsyncRequestInventory tRequest, RsyncResponseInventory tResponse) {

		RsyncResult result = new RsyncResult();

		// 定义成功的数量合计
		int iSuccessSum = 0;

		if (result.upFlagTrue()) {
			if (tResponse != null && tResponse.getData() != null) {
				result.setProcessNum(tResponse.getData().size());
			} else {
				result.setProcessNum(0);
			}
		}
		// 开始循环处理结果数据
		if (result.upFlagTrue()) {
			// 判断有需要处理的数据才开始处理
			if (result.getProcessNum() > 0) {
				// 设置预期处理数量
				result.setProcessNum(tResponse.getData().size());
				for (Data info : tResponse.getData()) {
					MWebResult mResult = saveProductData(info);
					// 如果成功则将成功计数加1
					if (mResult.upFlagTrue()) {
						iSuccessSum++;
					} else {
						if (result.getResultList() == null) {
							result.setResultList(new ArrayList<Object>());
						}
						result.getResultList().add(mResult.getMessage());
					}
				}
				result.setProcessData(
						getInfo(100001102, result.getProcessNum(), iSuccessSum, result.getProcessNum() - iSuccessSum));
			}
		}
		// 如果操作都成功 则设置状态保存数据为同步结束时间 以方便下一轮调用
		if (result.upFlagTrue()) {
			// 设置处理成功数量
			result.setSuccessNum(iSuccessSum);
			// 特殊处理 由于时间格式不对 状态数据需要切换掉
			RsyncDateCheck rsyncDateCheck = upDateCheck(upConfig());
			result.setStatusData(rsyncDateCheck.getEndDate());
		}
		return result;
	}

	private MWebResult saveProductData(Data info) {
		MWebResult result = new MWebResult();
		try {
			String productId = info.getProductID();// 跨境通的商品编号
			String onlineQty = String.valueOf(info.getOnlineQty());// 库存数
			String wareGouse = String.valueOf(info.getWareHouseID());// 仓库编号
			// 根据旧编号获取商品编码
			String product_code = productInfoService.findProductCodeByOldCode(productId);
			if (product_code != null && !"".equals(product_code)) {
				// 根据商品编号查询产品编号
				String sku_code = skuInfoService.findSkuCodeByProductCode(product_code);
				// 根据查询条件查询ScStoreSkunum对象
				ScStoreSkunum scStoreSkunum = new ScStoreSkunum();
				scStoreSkunum.setUid(UUID.randomUUID().toString().replace("-", ""));
				scStoreSkunum.setStoreCode(wareGouse);
				scStoreSkunum.setSkuCode(sku_code);
				scStoreSkunum.setStockNum(Long.valueOf(onlineQty));
				// 判断scStoreSkunum对象是否存在，如果存在更新对象的stock_num库存数
				if (scStoreSkunumService.findScStoreSkunumByParams(scStoreSkunum) != null) {
					scStoreSkunum = scStoreSkunumService.findScStoreSkunumByParams(scStoreSkunum);
					scStoreSkunum.setStockNum(Long.valueOf(onlineQty));
					// 更新库存数stock_num
					scStoreSkunumService.updateSelective(scStoreSkunum);
				} else {
					// 判断对象在sc_store中是否存在
					int storeCount = scStoreService.findScStoreIsExists(wareGouse);
					if (storeCount == 0) {
						ScStore scStore = new ScStore();
						scStore.setUid(UUID.randomUUID().toString().replace("-", ""));
						scStore.setAppCode(MemberConst.MANAGE_CODE_HOMEHAS);
						scStore.setAppName("惠家有");
						scStore.setStoreName("跨境通");
						scStore.setStoreCode(wareGouse);
						// 插入仓库信息
						scStoreService.insertSelective(scStore);
					}
					// 插入库存信息
					scStoreSkunumService.insertSelective(scStoreSkunum);
				}
				// 修改SKU库存
				new PlusHelperNotice().onChangeSkuStock(sku_code);
				// 编辑商品属性
				PcProductinfoExt pcProductinfoExt = new PcProductinfoExt();
				pcProductinfoExt.setProductCode(product_code);
				pcProductinfoExt.setOaSiteNo(wareGouse);
				pcProductinfoExtService.updatePcProductinfoExt(pcProductinfoExt);

				new PlusHelperNotice().onChangeProductInfo(product_code);
				// 触发消息队列
				ProductJmsSupport pjs = new ProductJmsSupport();
				pjs.onChangeForProductChangeAll(product_code);
			}
		} catch (Exception e) {
			result.inErrorMessage(100009034, info.toString());
			e.printStackTrace();
		}
		return result;
	}

	public RsyncResponseInventory upResponseObject() {
		return new RsyncResponseInventory();
	}

}

package com.hjy.selleradapter.kjt.response;

import java.util.ArrayList;
import java.util.List;

/**
 * alias RsyncResponseGetKjtProductInventoryById<br>
 * 类: RsyncResponseInventory <br>
 * 描述: 跨境通商品库存同步响应参数类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月27日 下午5:45:23
 */
public class RsyncResponseInventory extends RsyncKjtResponseBase {
	private List<Data> Data = new ArrayList<Data>();

	public List<Data> getData() {
		return Data;
	}

	public void setData(List<Data> data) {
		Data = data;
	}

	public static class Data {
		/**
		 * 商品库存列表
		 */
		/**
		 * 商品编号
		 */
		private String ProductID = "";
		/**
		 * 可销售库存
		 */
		private String OnlineQty = "";
		/**
		 * 出库仓 仓库编号
		 */
		private String WareHouseID = "";

		public String getOnlineQty() {
			return OnlineQty;
		}

		public void setOnlineQty(String onlineQty) {
			OnlineQty = onlineQty;
		}

		public String getWareHouseID() {
			return WareHouseID;
		}

		public void setWareHouseID(String wareHouseID) {
			WareHouseID = wareHouseID;
		}

		public String getProductID() {
			return ProductID;
		}

		public void setProductID(String productID) {
			ProductID = productID;
		}

	}
}

package com.hjy.selleradapter.kjt.request;

import java.util.List;

import com.hjy.iface.IRsyncRequest;
import com.hjy.selleradapter.kjt.model.SOAuthenticationInfo;
import com.hjy.selleradapter.kjt.model.SOItemInfo;
import com.hjy.selleradapter.kjt.model.SOPayInfo;
import com.hjy.selleradapter.kjt.model.SOShippingInfo;

public class RsyncRequestOrderSoCreate implements IRsyncRequest {

	private long SaleChannelSysNo;
	private String MerchantOrderID;
	private String ServerType;
	private long WarehouseID;
	private SOPayInfo PayInfo;
	private SOShippingInfo ShippingInfo;
	private SOAuthenticationInfo AuthenticationInfo;
	private List<SOItemInfo> ItemList;

	public long getSaleChannelSysNo() {
		return SaleChannelSysNo;
	}

	public void setSaleChannelSysNo(long saleChannelSysNo) {
		SaleChannelSysNo = saleChannelSysNo;
	}

	public String getMerchantOrderID() {
		return MerchantOrderID;
	}

	public void setMerchantOrderID(String merchantOrderID) {
		MerchantOrderID = merchantOrderID;
	}

	public String getServerType() {
		return ServerType;
	}

	public void setServerType(String serverType) {
		ServerType = serverType;
	}

	public long getWarehouseID() {
		return WarehouseID;
	}

	public void setWarehouseID(long warehouseID) {
		WarehouseID = warehouseID;
	}

	public SOPayInfo getPayInfo() {
		return PayInfo;
	}

	public void setPayInfo(SOPayInfo payInfo) {
		PayInfo = payInfo;
	}

	public SOShippingInfo getShippingInfo() {
		return ShippingInfo;
	}

	public void setShippingInfo(SOShippingInfo shippingInfo) {
		ShippingInfo = shippingInfo;
	}

	public SOAuthenticationInfo getAuthenticationInfo() {
		return AuthenticationInfo;
	}

	public void setAuthenticationInfo(SOAuthenticationInfo authenticationInfo) {
		AuthenticationInfo = authenticationInfo;
	}

	public List<SOItemInfo> getItemList() {
		return ItemList;
	}

	public void setItemList(List<SOItemInfo> itemList) {
		ItemList = itemList;
	}

}

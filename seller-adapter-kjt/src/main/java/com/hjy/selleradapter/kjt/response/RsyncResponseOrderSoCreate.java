package com.hjy.selleradapter.kjt.response;

import java.math.BigDecimal;


public class RsyncResponseOrderSoCreate extends RsyncKjtResponseBase {

	private Data Data;

	public Data getData() {
		return Data;
	}

	public void setData(Data data) {
		Data = data;
	}

	public static class Data {
		private String MerchantOrderID;
		private long SOSysNo;
		private BigDecimal ProductAmount;
		private BigDecimal TaxAmount;
		private BigDecimal ShippingAmount;

		public String getMerchantOrderID() {
			return MerchantOrderID;
		}

		public void setMerchantOrderID(String merchantOrderID) {
			MerchantOrderID = merchantOrderID;
		}

		public long getSOSysNo() {
			return SOSysNo;
		}

		public void setSOSysNo(long sOSysNo) {
			SOSysNo = sOSysNo;
		}

		public BigDecimal getProductAmount() {
			return ProductAmount;
		}

		public void setProductAmount(BigDecimal productAmount) {
			ProductAmount = productAmount;
		}

		public BigDecimal getTaxAmount() {
			return TaxAmount;
		}

		public void setTaxAmount(BigDecimal taxAmount) {
			TaxAmount = taxAmount;
		}

		public BigDecimal getShippingAmount() {
			return ShippingAmount;
		}

		public void setShippingAmount(BigDecimal shippingAmount) {
			ShippingAmount = shippingAmount;
		}
	}
}

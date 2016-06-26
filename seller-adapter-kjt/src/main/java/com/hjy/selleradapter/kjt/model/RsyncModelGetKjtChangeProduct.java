package com.hjy.selleradapter.kjt.model;

import java.io.Serializable;

public class RsyncModelGetKjtChangeProduct implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 商品ID
	 */
	private String ProductID = "";
	
	/**
	 * 商品状态，0,仅展示 1, 上架。2-不展示，-2-已终止，-1,已作废
	 */
	private int Status = 0;
	
	/**
	 * 发生商品创建、修改或订阅的最后时间
	 */
	private String ChangeDate = "";

	public String getProductID() {
		return ProductID;
	}

	public void setProductID(String productID) {
		ProductID = productID;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getChangeDate() {
		return ChangeDate;
	}

	public void setChangeDate(String changeDate) {
		ChangeDate = changeDate;
	}
	
	
}

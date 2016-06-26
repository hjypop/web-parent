package com.hjy.selleradapter.kjt.request;

import com.hjy.iface.IRsyncRequest;

/**
 * 根据ID批量获取商品基本信息
 * 
 * @author xiegj
 * 
 */
public class RsyncRequestGetKjtProductIdByDate implements IRsyncRequest {

	/**
	 * 订阅商品用的渠道
	 */
	private String SaleChannelSysNo;
	
	private String ChangedDateBegin;
	
	private String ChangedDateEnd;
	
	private String LimitRows = "";
	
	private int StartRow = 1;
	
	public String getSaleChannelSysNo() {
		return SaleChannelSysNo;
	}

	public void setSaleChannelSysNo(String saleChannelSysNo) {
		SaleChannelSysNo = saleChannelSysNo;
	}

	public String getChangedDateBegin() {
		return ChangedDateBegin;
	}

	public void setChangedDateBegin(String changedDateBegin) {
		ChangedDateBegin = changedDateBegin;
	}

	public String getChangedDateEnd() {
		return ChangedDateEnd;
	}

	public void setChangedDateEnd(String changedDateEnd) {
		ChangedDateEnd = changedDateEnd;
	}

	public int getStartRow() {
		return StartRow;
	}

	public void setStartRow(int startRow) {
		StartRow = startRow;
	}

	public String getLimitRows() {
		return LimitRows;
	}

	public void setLimitRows(String limitRows) {
		LimitRows = limitRows;
	}
	
}

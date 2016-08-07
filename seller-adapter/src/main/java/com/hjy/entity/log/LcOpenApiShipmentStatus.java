package com.hjy.entity.log;

import java.util.Date;

public class LcOpenApiShipmentStatus {
	
    private Integer zid;

    private String shipmentUid;

    private String sellerCode;

    private String orderCode;

    private String logisticseName;

    private String wayBill;

    private Integer flag; // 1 成功 2失败

    private Date createTime;

    private String remark;

    
    
    public LcOpenApiShipmentStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

    
    
	public LcOpenApiShipmentStatus(String shipmentUid, String sellerCode, String orderCode, String logisticseName,
			String wayBill, Integer flag, Date createTime, String remark) {
		super();
		this.shipmentUid = shipmentUid;
		this.sellerCode = sellerCode;
		this.orderCode = orderCode;
		this.logisticseName = logisticseName;
		this.wayBill = wayBill;
		this.flag = flag;
		this.createTime = createTime;
		this.remark = remark;
	}



	public Integer getZid() {
        return zid;
    }

    public void setZid(Integer zid) {
        this.zid = zid;
    }

    public String getShipmentUid() {
        return shipmentUid;
    }

    public void setShipmentUid(String shipmentUid) {
        this.shipmentUid = shipmentUid == null ? null : shipmentUid.trim();
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode == null ? null : sellerCode.trim();
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getLogisticseName() {
        return logisticseName;
    }

    public void setLogisticseName(String logisticseName) {
        this.logisticseName = logisticseName == null ? null : logisticseName.trim();
    }

    public String getWayBill() {
        return wayBill;
    }

    public void setWayBill(String wayBill) {
        this.wayBill = wayBill == null ? null : wayBill.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
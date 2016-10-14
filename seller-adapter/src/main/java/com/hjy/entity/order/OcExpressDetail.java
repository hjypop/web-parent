package com.hjy.entity.order;

public class OcExpressDetail {
    private Integer zid;

    private String orderCode;

    private String logisticseCode;

    private String waybill;

    private String context;

    private String time;

    public Integer getZid() {
        return zid;
    }

    public void setZid(Integer zid) {
        this.zid = zid;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getLogisticseCode() {
        return logisticseCode;
    }

    public void setLogisticseCode(String logisticseCode) {
        this.logisticseCode = logisticseCode == null ? null : logisticseCode.trim();
    }

    public String getWaybill() {
        return waybill;
    }

    public void setWaybill(String waybill) {
        this.waybill = waybill == null ? null : waybill.trim();
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }
}
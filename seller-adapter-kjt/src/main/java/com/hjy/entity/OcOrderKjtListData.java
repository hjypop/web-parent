package com.hjy.entity;

public class OcOrderKjtListData {
    private Integer zid;

    private String uid;

    private String orderCodeSeq;

    private String requestClazz;

    public Integer getZid() {
        return zid;
    }

    public void setZid(Integer zid) {
        this.zid = zid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getOrderCodeSeq() {
        return orderCodeSeq;
    }

    public void setOrderCodeSeq(String orderCodeSeq) {
        this.orderCodeSeq = orderCodeSeq == null ? null : orderCodeSeq.trim();
    }

    public String getRequestClazz() {
        return requestClazz;
    }

    public void setRequestClazz(String requestClazz) {
        this.requestClazz = requestClazz == null ? null : requestClazz.trim();
    }
}
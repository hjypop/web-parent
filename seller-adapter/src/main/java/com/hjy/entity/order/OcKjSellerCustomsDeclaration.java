package com.hjy.entity.order;

import java.math.BigDecimal;
import java.util.Date;

public class OcKjSellerCustomsDeclaration {
    private Integer zid;

    private String uid;

    private String sellerCode;

    private String orderCode;

    private String bigOrderCode;

    private BigDecimal dueMoney;
    
    private Integer flag;

    private String type;

    private String bankOrderId;

    private BigDecimal orderAmount;

    private Integer moneyType;

    private Date createTime;

    private Date updateTime;

    private String remark;
    
    private String sellerName;
    
    private String tradeNo;
    
	private String transNum;  // 支付网关订单号  oc_payment_paygate -> c_transnum
	private BigDecimal transportMoney; //  运费 oc_orderinfo-> transport_money
	private String authName;  // 支付人姓名  oc_orderadress -> auth_true_name 
	private String idcard;  // 支付人的身份证编号 oc_orderadress -> auth_idcard_number
	private String payGate; // 支付网关类型 oc_payment_paygate -> c_paygate
	
	
    
    public String getPayGate() {
		return payGate;
	}

	public void setPayGate(String payGate) {
		this.payGate = payGate;
	}

	public String getTransNum() {
		return transNum;
	}

	public void setTransNum(String transNum) {
		this.transNum = transNum;
	}

	public BigDecimal getTransportMoney() {
		return transportMoney;
	}

	public void setTransportMoney(BigDecimal transportMoney) {
		this.transportMoney = transportMoney;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public BigDecimal getDueMoney() {
		return dueMoney;
	}

	public void setDueMoney(BigDecimal dueMoney) {
		this.dueMoney = dueMoney;
	}

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

    public String getBigOrderCode() {
        return bigOrderCode;
    }

    public void setBigOrderCode(String bigOrderCode) {
        this.bigOrderCode = bigOrderCode == null ? null : bigOrderCode.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getBankOrderId() {
        return bankOrderId;
    }

    public void setBankOrderId(String bankOrderId) {
        this.bankOrderId = bankOrderId == null ? null : bankOrderId.trim();
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Integer moneyType) {
        this.moneyType = moneyType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
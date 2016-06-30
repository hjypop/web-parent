package com.hjy.entity.order;


/**   
*    
* 项目名称：ordercenter   
* 类名称：OcOrderPay   
* 类描述：   
* 创建人：yanzj  
* 创建时间：2013-10-29 上午9:30:15   
* 修改人：yanzj
* 修改时间：2013-10-29 上午9:30:15   
* 修改备注：   
* @version    
*    
*/
public class OcOrderPay  {
    
  
    /**
     * 订单编号
     */
    private String orderCode  = ""  ;
    /**
     * 流水编号，可以是礼品卡卡号，优惠券号，银行流水号，支付宝流水号
     */
    private String paySequenceid  = ""  ;
    /**
     * 此流水支付了多少钱
     */
    private float payedMoney = 0   ;
    
    
    /**
     * 礼品卡密码
     */
    private String passWord = "";
    
 
    /**
     * 支付类型449746280001:礼品卡    449746280002:优惠券    449746280003:支付宝支付   449746280004:快钱支付 449746280005:微信支付 449746280006:储值金 (家有汇)449746280007:暂存款(家有汇) 449746280008:积分(家有汇)
     */
    private String payType  = ""  ;
    
    /**
     * 买家编号 
     */
    private String merchantId = "";
    
    public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	/**
     * 支付备注
     */
    private String payRemark  = ""  ;
  
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    
    public String getOrderCode() {
        return this.orderCode;
    }
    public void setPaySequenceid(String paySequenceid) {
        this.paySequenceid = paySequenceid;
    }
    
    public String getPaySequenceid() {
        return this.paySequenceid;
    }
    public void setPayedMoney(float payedMoney) {
        this.payedMoney = payedMoney;
    }
    
    public float getPayedMoney() {
        return this.payedMoney;
    }
   
    public void setPayType(String payType) {
        this.payType = payType;
    }
    
    public String getPayType() {
        return this.payType;
    }
    public void setPayRemark(String payRemark) {
        this.payRemark = payRemark;
    }
    
    public String getPayRemark() {
        return this.payRemark;
    }
}


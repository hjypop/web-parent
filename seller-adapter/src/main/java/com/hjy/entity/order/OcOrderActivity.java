package com.hjy.entity.order;


/**   
*    
* 项目名称：ordercenter   
* 类名称：OcOrderActivity   
* 类描述：   
* 创建人：yanzj  
* 创建时间：2013-9-25 上午9:41:22   
* 修改人：yanzj
* 修改时间：2013-9-10 上午9:41:22   
* 修改备注：   
* @version    
*    
*/
public class OcOrderActivity  {
    
    /**
     * 订单编号(接口勿传,此值会被覆盖，请勿传)
     */
    private String orderCode  = ""  ;
    /**
     * 商品编号
     */
    private String productCode  = ""  ;
    /**
     * sku商品编号
     */
    private String skuCode  = ""  ;
    /**
     * 优惠的金钱
     */
    private float preferentialMoney = 0   ;
    /**
     * 活动编号
     */
    private String activityCode  = ""  ;
    /**
     * 活动类型
     */
    private String activityType  = ""  ;
    
    
    /**
     * 活动名称  非数据库字段
     */
    private String activityName = "";
    
    
    /**
     * 订单最低价格 非数据库字段
     */ 
    private float orderMinMoney = 0;
    
    /**
     * 外部活动编号
     */
    private String outActiveCode = "";
    
    /**活动券码*/
    private String ticketCode = "";
    
    
    public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public float getOrderMinMoney() {
		return orderMinMoney;
	}

	public void setOrderMinMoney(float orderMinMoney) {
		this.orderMinMoney = orderMinMoney;
	}

	public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    
    public String getOrderCode() {
        return this.orderCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
    public String getProductCode() {
        return this.productCode;
    }
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }
    
    public String getSkuCode() {
        return this.skuCode;
    }
    public void setPreferentialMoney(float preferentialMoney) {
        this.preferentialMoney = preferentialMoney;
    }
    
    public float getPreferentialMoney() {
        return this.preferentialMoney;
    }
    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }
    
    public String getActivityCode() {
        return this.activityCode;
    }
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
    
    public String getActivityType() {
        return this.activityType;
    }

	public String getOutActiveCode() {
		return outActiveCode;
	}

	public void setOutActiveCode(String outActiveCode) {
		this.outActiveCode = outActiveCode;
	}

	public String getTicketCode() {
		return ticketCode;
	}

	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}
    
    
}


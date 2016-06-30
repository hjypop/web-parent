package com.hjy.entity.order;


/**   
*    
* 项目名称：systemcenter   
* 类名称：OcOrderShipments   
* 类描述：   
* 创建人：yanzj  
* 创建时间：2013-9-25 上午9:41:22   
* 修改人：yanzj
* 修改时间：2013-9-10 上午9:41:22   
* 修改备注：   
* @version    
*    
*/
public class OcOrderShipments  {

    /**
     * 订单编号
     */
    private String orderCode  = ""  ;
    /**
     * 物流商家code
     */
    private String logisticseCode  = ""  ;
    /**
     * 物流商家name
     */
    private String logisticseName  = ""  ;
    /**
     * 运单号码
     */
    private String waybill  = ""  ;
    /**
     * 创建人
     */
    private String creator  = ""  ;
    /**
     * 创建时间
     */
    private String createTime  = ""  ;
    /**
     * 发货说明
     */
    private String remark  = ""  ;
    
    /*跨境通运单号*/
    private String order_code_seq = "";

  
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    
    public String getOrderCode() {
        return this.orderCode;
    }
    public void setLogisticseCode(String logisticseCode) {
        this.logisticseCode = logisticseCode;
    }
    
    public String getLogisticseCode() {
        return this.logisticseCode;
    }
    public void setLogisticseName(String logisticseName) {
        this.logisticseName = logisticseName;
    }
    
    public String getLogisticseName() {
        return this.logisticseName;
    }
    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }
    
    public String getWaybill() {
        return this.waybill;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    
    public String getCreator() {
        return this.creator;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    public String getCreateTime() {
        return this.createTime;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getRemark() {
        return this.remark;
    }

    /**
     * 获取跨境通运单号
     * @return
     */
	public String getOrder_code_seq() {
		return order_code_seq;
	}

	/**
	 * 设置跨境通运单号
	 * @param order_code_seq
	 */
	public void setOrder_code_seq(String order_code_seq) {
		this.order_code_seq = order_code_seq;
	}
}


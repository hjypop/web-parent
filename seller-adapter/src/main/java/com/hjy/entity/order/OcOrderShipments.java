package com.hjy.entity.order;

import com.hjy.base.BaseModel;

/**
 * 
 * 项目名称：systemcenter 类名称：OcOrderShipments 类描述： 创建人：yanzj 创建时间：2013-9-25
 * 上午9:41:22 修改人：yanzj 修改时间：2013-9-10 上午9:41:22 修改备注：
 * 
 * 修改人：Yangcl 修改时间：2016-6-30 修改备注：已经与数据库字段同步
 * 
 * @version
 * 
 */
public class OcOrderShipments {

	private Integer zid=0;
	
	private String uid=null;

	
	/**
	 * 订单编号
	 */
	private String orderCode = "";
	/**
	 * 物流商家code
	 */
	private String logisticseCode = "";
	/**
	 * 物流商家name
	 */
	private String logisticseName = "";

	private Integer sendCount;

	private String sendRemark;

	private String orderCodeSeq;

	private String updateTime;

	private String updateUser;

	private String shipmentsCode;

	/**
	 * 运单号码
	 */
	private String waybill = "";

	private Integer isSend100Flag;

	/**
	 * 创建人
	 */
	private String creator = "";
	/**
	 * 创建时间
	 */
	private String createTime = "";
	/**
	 * 发货说明
	 */
	private String remark = "";
	
	

	public OcOrderShipments() {
		super();
	}

	public OcOrderShipments(String uid, String orderCode, String logisticseCode, String logisticseName, String waybill,
			String creator, String createTime, String remark) {
		super();
		this.uid = uid;
		this.orderCode = orderCode;
		this.logisticseCode = logisticseCode;
		this.logisticseName = logisticseName;
		this.waybill = waybill;
		this.creator = creator;
		this.createTime = createTime;
		this.remark = remark;
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
		this.uid = uid;
	}

	/* 跨境通运单号 */
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
	 * 
	 * @return
	 */
	public String getOrder_code_seq() {
		return order_code_seq;
	}

	/**
	 * 设置跨境通运单号
	 * 
	 * @param order_code_seq
	 */
	public void setOrder_code_seq(String order_code_seq) {
		this.order_code_seq = order_code_seq;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public String getSendRemark() {
		return sendRemark;
	}

	public void setSendRemark(String sendRemark) {
		this.sendRemark = sendRemark;
	}

	public String getOrderCodeSeq() {
		return orderCodeSeq;
	}

	public void setOrderCodeSeq(String orderCodeSeq) {
		this.orderCodeSeq = orderCodeSeq;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getShipmentsCode() {
		return shipmentsCode;
	}

	public void setShipmentsCode(String shipmentsCode) {
		this.shipmentsCode = shipmentsCode;
	}

	public Integer getIsSend100Flag() {
		return isSend100Flag;
	}

	public void setIsSend100Flag(Integer isSend100Flag) {
		this.isSend100Flag = isSend100Flag;
	}

}

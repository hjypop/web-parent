package com.hjy.request.data;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.hjy.pojo.entity.BaseEntity;

/**
 * @descriptions 订单对应的物流信息|ShipmentRequest.java中使用
 * 
 * @date 2016年8月5日上午11:05:20
 * @author Yangcl
 * @version 1.0.1
 */
public class OrderShipment {
	
	private String uid;
	
	private String orderCode; 		// 订单编号
	
	private String logisticseCode ;  // 物流公司 code
	private String logisticseName;  // 物流公司 名称
	private String waybill ; 				// 运单号码
	private String creator;  // 商家编码
	private String createTime; // 创建时间
	
	private String remark ; // 备注 

	/**
	 * 4497153900010001 下单成功-未付款 
	 * 4497153900010002 下单成功-未发货 
	 * 4497153900010003 已发货      
	 * 4497153900010004 已收货
	 * 4497153900010005 交易成功	
	 * 
	 * 4497153900010006 交易失败 
	 * 4497153900010007 交易无效
	 */
//	private String orderStatus; // 订单状态
	
	
	

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) { 
		this.uid = uid;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}


	public String getLogisticseCode() {
		return logisticseCode;
	}

	public void setLogisticseCode(String logisticseCode) {
		this.logisticseCode = logisticseCode;
	}

	public String getLogisticseName() {
		return logisticseName;
	}

	public void setLogisticseName(String logisticseName) {
		this.logisticseName = logisticseName;
	}

	public String getWaybill() {
		return waybill;
	}

	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "OrderShipment [orderCode=" + orderCode + ", logisticseCode=" + logisticseCode + ", logisticseName="
				+ logisticseName + ", waybill=" + waybill + ", creator=" + creator + ", remark=" + remark + "]";
	}
	
	
}

package com.hjy.entity.product;

import com.hjy.base.BaseModel;

/**
 * 
 * 项目名称：productcenter 类名称：PcProductflow 类描述： 创建人：yanzj 创建时间：2013-9-10 下午12:41:22
 * 修改人：yanzj 修改时间：2013-9-10 下午12:41:22 修改备注：
 * 
 * @version
 * 
 */
public class PcProductflow extends BaseModel {

	/**
	 * 流程编号
	 */
	private String flowCode = "";
	/**
	 * 商品编号
	 */
	private String productCode = "";
	/**
	 * 商品草稿数据
	 */
	private String productJson = "";
	/**
	 * 流程状态
	 */
	private String flowStatus = "";
	/**
	 * 创建时间
	 */
	private String createTime = "";
	/**
	 * 更新时间
	 */
	private String updateTime = "";
	/**
	 * 创建人
	 */
	private String creator = "";
	/**
	 * 更新人
	 */
	private String updator = "";

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

	public String getFlowCode() {
		return this.flowCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductJson(String productJson) {
		this.productJson = productJson;
	}

	public String getProductJson() {
		return this.productJson;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	public String getFlowStatus() {
		return this.flowStatus;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public String getUpdator() {
		return this.updator;
	}
}

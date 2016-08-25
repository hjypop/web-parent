package com.hjy.entity.webcore;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: WcOpenApi <br>
 * 描述: openApi接口表 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月25日 上午8:58:22
 */
public class WcOpenApi extends BaseModel{

	/**
	 * 接口编号
	 */
	private String apiCode;

	/**
	 * 接口中文名称
	 */
	private String apiName;

	/**
	 * 接口方法名称
	 */
	private String method;

	/**
	 * 接口状态 0 未开通 1 已开通 2 已禁用
	 */
	private Integer status;

	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 是否已删除 0 未删除 1 已删除
	 */
	private Integer isDeleted;
	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 修改人
	 */
	private String updator;

	/**
	 * 修改时间
	 */
	private String updateTime;

	public String getApiCode() {
		return apiCode;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
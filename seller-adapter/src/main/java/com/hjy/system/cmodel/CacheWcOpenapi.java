package com.hjy.system.cmodel;

 /**
  * @description: wc_openapi表的缓存值|商户所属API信息描述
  * 
  * @author Yangcl
  * @date 2016年12月22日 下午4:52:50 
  * @version 1.0.0
  */
public class CacheWcOpenapi {
	
	public CacheWcOpenapi() {
	}
	
	public CacheWcOpenapi(String method, String apiName, String apiCode, Integer status, Integer flag , String description, Integer isDeleted) {
		this.method = method;
		this.apiName = apiName;
		this.apiCode = apiCode;
		this.status = status;
		this.flag = flag;
		this.description = description;
		this.isDeleted = isDeleted;
	}
	
	private String method;  // 接口方法名称 重要标识
	private String apiName;
	private String apiCode;
	private Integer status;  // 接口状态 0 未开通 1 已开通 2 已禁用
	private Integer flag;  // 接口类型：1 商户接口 2 分销平台接口
	private String description;
	private Integer isDeleted;  // 是否已删除 0 未删除 1 已删除
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getApiCode() {
		return apiCode;
	}
	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
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
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}















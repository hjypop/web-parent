package com.drwljrtv.request.video;

import com.drwljrtv.request.BaseRequest;

/**
 * 
 * 类: GetVideos <br>
 * 描述: 获取视频列表列表参数 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月4日 下午5:49:48
 */
public class GetVideos extends BaseRequest {

	/**
	 * 视频的分类ID
	 */
	private Integer categoryId;
	/**
	 * 视频标记（1：首页头条，2：首页顶部推荐，3：首页栏目列表推荐，4：手机端推荐）
	 */
	private Integer tag;
	/**
	 * 用户id，如果传入此参数，需要带上token
	 */
	private Integer userId;
	/**
	 * 排序方式（支持的字段：views:点播次数 , date_added：上传日期,videoid视频id，）
	 */
	private String order;
	/**
	 * 视频来源：0：本地上传，1：直播源
	 */
	private Integer source;

	/**
	 * 转码状态：Successful：成功，processing：处理中，Failed：失败
	 */
	private String status;
	/**
	 * 是否公开，public：公开，unlisted：本人可见
	 */
	private String broadcast;

	/**
	 * 是否审核通过，yes表示审核通过，no表示待审核
	 */
	private String active;

	/**
	 * 要排除的视频id
	 */
	private String exclude;

	/**
	 * 是否显示相关视频
	 */
	private String showRelated;

	/**
	 * 获取的记录数，如果page参数大于0，则表示每页数目
	 */
	private Integer pageSize;

	/**
	 * 当前页码，如果page=0，表示不分页输出
	 */
	private Integer page;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBroadcast() {
		return broadcast;
	}

	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

	public String getShowRelated() {
		return showRelated;
	}

	public void setShowRelated(String showRelated) {
		this.showRelated = showRelated;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}

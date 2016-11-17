package com.drwljrtv.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 类: Video <br>
 * 描述: 视频 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月17日 下午2:35:31
 */
public class Video {

	/**
	 * 视频id
	 */
	private Integer videoid;

	/**
	 * 视频标题
	 */
	private String title;

	/**
	 * 视频长度
	 */
	private String duration;

	/**
	 * 播放次数
	 */
	private Integer views;

	/**
	 * 点赞次数
	 */
	private Integer rating;

	/**
	 * 视频描述
	 */
	private String description;
	/**
	 * 视频分类
	 */
	@JSONField(name = "category_id")
	private Integer categoryId;

	/**
	 * 视频标记（1：首页头条，2：首页顶部推荐，3：首页栏目列表推荐，4：手机端推荐）
	 */
	private String tag;

	/**
	 * 用户id，如果传入此参数，需要带上token
	 */
	private Integer userid;
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
	@JSONField(name = "show_related")
	private String showRelated;

	/**
	 * 获取的记录数，如果page参数大于0，则表示每页数目
	 */
	@JSONField(name = "page_size")
	private Integer pageSize;

	/**
	 * 当前页码，如果page=0，表示不分页输出
	 */
	private Integer page;

	/**
	 * 缩略图地址
	 */
	private String thumb;
	/**
	 * 视频截图大图
	 */
	@JSONField(name = "big_thumb")
	private String bigThumb;
	/**
	 * 
	 */
	@JSONField(name = "video_href")
	private String videoHref;

	/**
	 * 
	 */
	@JSONField(name = "video_size")
	private String videoSize;
	
	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
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

	public String getBigThumb() {
		return bigThumb;
	}

	public void setBigThumb(String bigThumb) {
		this.bigThumb = bigThumb;
	}

	public String getVideoHref() {
		return videoHref;
	}

	public void setVideoHref(String videoHref) {
		this.videoHref = videoHref;
	}

	public String getVideoSize() {
		return videoSize;
	}

	public void setVideoSize(String videoSize) {
		this.videoSize = videoSize;
	}

	public Integer getVideoid() {
		return videoid;
	}

	public void setVideoid(Integer videoid) {
		this.videoid = videoid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

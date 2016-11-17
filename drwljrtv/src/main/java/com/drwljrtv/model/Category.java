package com.drwljrtv.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 类: Category <br>
 * 描述: 视频分类 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月17日 下午2:40:57
 */
public class Category {

	/**
	 * 分类id
	 */
	@JSONField(name="category_id")
	private Integer categoryId;

	/**
	 * 分类名称
	 */
	@JSONField(name="category_name")
	private String categoryName;
	/**
	 * 分类的缩略图
	 */
	private String thumb;
	/**
	 * 下级分类列表
	 */
	private String sunclass;

	/**
	 * 分类下视频列表
	 */
	private List<Video> videos;

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getSunclass() {
		return sunclass;
	}

	public void setSunclass(String sunclass) {
		this.sunclass = sunclass;
	}

}

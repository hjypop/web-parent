package com.drwljrtv.request.video;

import com.drwljrtv.request.BaseRequest;

/**
 * 
 * 类: GetCategory <br>
 * 描述: 获取视频分类请求参数 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月4日 下午5:49:18
 */
public class GetCategory extends BaseRequest {

	/**
	 * 分类id
	 */
	private Integer categoryId;

	/**
	 * 视频名称
	 */
	private String categoryName;

	/**
	 * 分类标记（1：首页显示，2：主导航显示，3：移动端推荐）
	 */
	private Integer tag;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

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

}

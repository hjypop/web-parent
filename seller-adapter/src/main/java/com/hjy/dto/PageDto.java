package com.hjy.dto;

/**
 * 
 * 类: PageDto <br>
 * 描述: 分页参数 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月17日 下午2:58:09
 */
public class PageDto {

	/**
	 * 当前页
	 */
	private Integer pageNum = 0;
	/**
	 * 页面最大显示数
	 */
	private Integer pageSize = 10;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}

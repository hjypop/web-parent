package com.hjy.entity.zapdata;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: ZaWeblog <br>
 * 描述: 日志表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午9:00:24
 */
public class ZaWeblog extends BaseModel {

	private String logType;
	private String logTitle;
	private String logContent;
	private String createTime;

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogTitle() {
		return logTitle;
	}

	public void setLogTitle(String logTitle) {
		this.logTitle = logTitle;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}

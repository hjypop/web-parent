package com.hjy.base;

/**
 * 
 * 类: BaseModel <br>
 * 描述: 持久化对象基类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月30日 上午10:57:00
 */
public class BaseModel {

	private Integer zid=null;
	private String uid=null;

	public Integer getZid() {
		return zid;
	}

	public void setZid(Integer zid) {
		this.zid = zid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}

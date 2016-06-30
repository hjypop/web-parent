package com.hjy.entity.log;

import com.hjy.base.BaseModel;

/**
 * 
 * 类: LcStockchange <br>
 * 描述: 库存变动日志表 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月30日 上午11:02:29
 */
public class LcStockchange extends BaseModel {
	private String code;
	private String info;
	private String createTime;
	private String createUser;
	private Integer changeStock;
	private Integer oldStock;
	private Integer nowStock;
	private String changeType;
	private String stockArea;
	private String stockBatch;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Integer getChangeStock() {
		return changeStock;
	}

	public void setChangeStock(Integer changeStock) {
		this.changeStock = changeStock;
	}

	public Integer getOldStock() {
		return oldStock;
	}

	public void setOldStock(Integer oldStock) {
		this.oldStock = oldStock;
	}

	public Integer getNowStock() {
		return nowStock;
	}

	public void setNowStock(Integer nowStock) {
		this.nowStock = nowStock;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getStockArea() {
		return stockArea;
	}

	public void setStockArea(String stockArea) {
		this.stockArea = stockArea;
	}

	public String getStockBatch() {
		return stockBatch;
	}

	public void setStockBatch(String stockBatch) {
		this.stockBatch = stockBatch;
	}

}
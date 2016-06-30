package com.hjy.entity.product;

import java.math.BigDecimal;

import com.hjy.base.BaseModel;

public class PcBrandinfo extends BaseModel {

	private String brandCode;
	private String brandName;
	private String brandNameEn;
	private Integer flagEnable;
	private String brandPic;
	private String brandNote;
	private String parentCode;
	private BigDecimal cpsrate;
	private String createUsernm;
	private String createTime;
	private String createUsercode;

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandNameEn() {
		return brandNameEn;
	}

	public void setBrandNameEn(String brandNameEn) {
		this.brandNameEn = brandNameEn;
	}

	public Integer getFlagEnable() {
		return flagEnable;
	}

	public void setFlagEnable(Integer flagEnable) {
		this.flagEnable = flagEnable;
	}

	public String getBrandPic() {
		return brandPic;
	}

	public void setBrandPic(String brandPic) {
		this.brandPic = brandPic;
	}

	public String getBrandNote() {
		return brandNote;
	}

	public void setBrandNote(String brandNote) {
		this.brandNote = brandNote;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public BigDecimal getCpsrate() {
		return cpsrate;
	}

	public void setCpsrate(BigDecimal cpsrate) {
		this.cpsrate = cpsrate;
	}

	public String getCreateUsernm() {
		return createUsernm;
	}

	public void setCreateUsernm(String createUsernm) {
		this.createUsernm = createUsernm;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUsercode() {
		return createUsercode;
	}

	public void setCreateUsercode(String createUsercode) {
		this.createUsercode = createUsercode;
	}

}

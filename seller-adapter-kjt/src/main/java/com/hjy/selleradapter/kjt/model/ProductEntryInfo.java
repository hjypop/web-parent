package com.hjy.selleradapter.kjt.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductEntryInfo implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 商品英文名称
	 */
	private String ProductName_EN="";
	/**
	 * 规格
	 */
	private String Specifications="";
	/**
	 * 功能
	 */
	private String Functions="";
	/**
	 * 成分
	 */
	private String Component="";
	/**
	 * 产地
	 */
	private String Origin="";
	/**
	 * 用途
	 */
	private String Purpose="";
	/**
	 * 计税单位
	 */
	private String TaxUnit="";
	/**
	 * 申报单位
	 */
	private String ApplyUnit="";
	/**
	 * 计税单位数量
	 */
	private BigDecimal TaxQty;
	/**
	 * 毛重
	 */
	private BigDecimal GrossWeight;
	/**
	 * 业务类型    0=一般进口,1=保税进口
	 */
	private int BizType;
	/**
	 * 净重
	 */
	private BigDecimal SuttleWeight;
	/**
	 * 其他备注
	 */
	private String Note="";
	/**
	 * 税率
	 */
	private BigDecimal TariffRate;
	/**
	 * 备案信息
	 */
	private String EntryCode="";
	/**
	 * 存储方式  0=常温,1=冷藏,2=冷冻
	 */
	private int ProductStoreType;
	/**
	 * 出厂日期
	 */
	private String ManufactureDate="";
	/**
	 * 出产国家
	 */
	private String OriginCountryName="";
	public String getProductName_EN() {
		return ProductName_EN;
	}
	public void setProductName_EN(String productName_EN) {
		ProductName_EN = productName_EN;
	}
	public String getSpecifications() {
		return Specifications;
	}
	public void setSpecifications(String specifications) {
		Specifications = specifications;
	}
	public String getFunctions() {
		return Functions;
	}
	public void setFunctions(String functions) {
		Functions = functions;
	}
	public String getComponent() {
		return Component;
	}
	public void setComponent(String component) {
		Component = component;
	}
	public String getOrigin() {
		return Origin;
	}
	public void setOrigin(String origin) {
		Origin = origin;
	}
	public String getPurpose() {
		return Purpose;
	}
	public void setPurpose(String purpose) {
		Purpose = purpose;
	}
	public String getTaxUnit() {
		return TaxUnit;
	}
	public void setTaxUnit(String taxUnit) {
		TaxUnit = taxUnit;
	}
	public String getApplyUnit() {
		return ApplyUnit;
	}
	public void setApplyUnit(String applyUnit) {
		ApplyUnit = applyUnit;
	}
	public BigDecimal getTaxQty() {
		return TaxQty;
	}
	public void setTaxQty(BigDecimal taxQty) {
		TaxQty = taxQty;
	}
	public BigDecimal getGrossWeight() {
		return GrossWeight;
	}
	public void setGrossWeight(BigDecimal grossWeight) {
		GrossWeight = grossWeight;
	}
	public int getBizType() {
		return BizType;
	}
	public void setBizType(int bizType) {
		BizType = bizType;
	}
	public BigDecimal getSuttleWeight() {
		return SuttleWeight;
	}
	public void setSuttleWeight(BigDecimal suttleWeight) {
		SuttleWeight = suttleWeight;
	}
	public String getNote() {
		return Note;
	}
	public void setNote(String note) {
		Note = note;
	}
	public BigDecimal getTariffRate() {
		return TariffRate;
	}
	public void setTariffRate(BigDecimal tariffRate) {
		TariffRate = tariffRate;
	}
	public String getEntryCode() {
		return EntryCode;
	}
	public void setEntryCode(String entryCode) {
		EntryCode = entryCode;
	}
	public int getProductStoreType() {
		return ProductStoreType;
	}
	public void setProductStoreType(int productStoreType) {
		ProductStoreType = productStoreType;
	}
	public String getManufactureDate() {
		return ManufactureDate;
	}
	public void setManufactureDate(String manufactureDate) {
		ManufactureDate = manufactureDate;
	}
	public String getOriginCountryName() {
		return OriginCountryName;
	}
	public void setOriginCountryName(String originCountryName) {
		OriginCountryName = originCountryName;
	}
	
	
}

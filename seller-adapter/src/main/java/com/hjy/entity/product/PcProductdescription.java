package com.hjy.entity.product;

import com.hjy.base.BaseModel;

/**
 * 
 * 项目名称：productcenter 类名称：PcProductdescription 类描述： 创建人：yanzj 创建时间：2013-9-10
 * 下午12:41:22 修改人：李国杰 修改时间：2014-9-22 下午21:03:22 修改备注：
 * 
 * @version
 * 
 */
public class PcProductdescription extends BaseModel {

	/**
	 * 商品编号
	 */
	private String productCode = "";

	/**
	 * 描述信息
	 */
	private String descriptionInfo = "";

	/**
	 * 描述图片
	 */
	private String descriptionPic = "";

	/**
	 * 关键字
	 */
	private String keyword = "";

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setDescriptionInfo(String descriptionInfo) {
		this.descriptionInfo = descriptionInfo;
	}

	public String getDescriptionInfo() {
		return this.descriptionInfo;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public String getDescriptionPic() {
		return descriptionPic;
	}

	public void setDescriptionPic(String descriptionPic) {
		this.descriptionPic = descriptionPic;
	}

}

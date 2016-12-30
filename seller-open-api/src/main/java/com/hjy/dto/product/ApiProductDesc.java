package com.hjy.dto.product;
/**
 * @description: 商品描述
 * 
 * @author Yangcl
 * @date 2016年12月30日 下午2:20:22 
 * @version 1.0.0
 */
public class ApiProductDesc {
	private String descriptionInfo = "";  // 描述信息
	private String descriptionPic = "";  // 描述图片
	private String keyword = "";  // 关键字
	
	public String getDescriptionInfo() {
		return descriptionInfo;
	}
	public void setDescriptionInfo(String descriptionInfo) {
		this.descriptionInfo = descriptionInfo;
	}
	public String getDescriptionPic() {
		return descriptionPic;
	}
	public void setDescriptionPic(String descriptionPic) {
		this.descriptionPic = descriptionPic;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}

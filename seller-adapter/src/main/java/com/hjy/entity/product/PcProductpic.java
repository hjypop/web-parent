package com.hjy.entity.product;


/**   
*    
* 项目名称：productcenter   
* 类名称：PcProductpic   
* 类描述：   
* 创建人：yanzj  
* 创建时间：2013-9-10 下午12:41:22   
* 修改人：yanzj
* 修改时间：2013-9-10 下午12:41:22   
* 修改备注：   
* @version    
*    
*/
public class PcProductpic  {
    
    /**
     * 
     */
    private Integer zid   = 0 ;
    /**
     * 
     */
    private String uid  = ""  ;
    /**
     * 商品编号
     */
    private String productCode  = ""  ;
    /**
     * 图片路径
     */
    private String picUrl  = ""  ;
    
    /**
     * 商品的skuCode
     */
    private String skuCode="";
    
    
    

    public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public void setZid(Integer zid) {
        this.zid = zid;
    }
    
    public Integer getZid() {
        return this.zid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    
    public String getUid() {
        return this.uid;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
    public String getProductCode() {
        return this.productCode;
    }
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    
    public String getPicUrl() {
        return this.picUrl;
    }
}


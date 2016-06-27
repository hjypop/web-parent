package com.hjy.entity.product;


/**   
*    
* 项目名称：productcenter   
* 类名称：PcProductproperty   
* 类描述：   
* 创建人：yanzj  
* 创建时间：2013-9-10 下午12:41:22   
* 修改人：yanzj
* 修改时间：2013-9-10 下午12:41:22   
* 修改备注：   
* @version    
*    
*/
public class PcProductproperty  {
    
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
     * 属性名称编号
     */
    private String propertyKeycode  = ""  ;
    /**
     * 属性编号
     */
    private String propertyCode  = ""  ;
    /**
     * 属性名称
     */
    private String propertyKey  = ""  ;
    /**
     * 属性值
     */
    private String propertyValue  = ""  ;
    /**
     * 属性类型
     *  449736200001	颜色属性
	 * 	449736200002	关键属性
	 * 	449736200003	销售属性
	 * 	449736200004	自定义属性
	 * 
     */
    private String propertyType  = ""  ;
    
    /**
     * 大项排序
     */
    private int bigSort = 0;
    
    /**
     * 大项的某个小项排序
     */
    private int smallSort = 0;
    
    private String skuCode = "";
    
    
    public int getBigSort() {
		return bigSort;
	}

	public void setBigSort(int bigSort) {
		this.bigSort = bigSort;
	}

	public int getSmallSort() {
		return smallSort;
	}

	public void setSmallSort(int smallSort) {
		this.smallSort = smallSort;
	}

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
    public void setPropertyKeycode(String propertyKeycode) {
        this.propertyKeycode = propertyKeycode;
    }
    
    public String getPropertyKeycode() {
        return this.propertyKeycode;
    }
    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }
    
    public String getPropertyCode() {
        return this.propertyCode;
    }
    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }
    
    public String getPropertyKey() {
        return this.propertyKey;
    }
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
    
    public String getPropertyValue() {
        return this.propertyValue;
    }
    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
    
    public String getPropertyType() {
        return this.propertyType;
    }
}


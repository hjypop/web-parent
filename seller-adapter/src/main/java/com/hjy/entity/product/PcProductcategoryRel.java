package com.hjy.entity.product;


/**   
*    
* 项目名称：productcenter   
* 类名称：PcProductcategoryRel   
* 类描述：   
* 创建人：yanzj  
* 创建时间：2013-9-10 下午12:41:22   
* 修改人：yanzj
* 修改时间：2013-9-10 下午12:41:22   
* 修改备注：   
* @version    
*    
*/
public class PcProductcategoryRel  {
    
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
     * 分类编号
     */
    private String categoryCode  = ""  ;
    /**
     * 是否主分类
     */
    private Long flagMain   = (long) 0 ;
    
    private String categoryCodeOld;
    

    public String getCategoryCodeOld() {
		return categoryCodeOld;
	}

	public void setCategoryCodeOld(String categoryCodeOld) {
		this.categoryCodeOld = categoryCodeOld;
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
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
    
    public String getCategoryCode() {
        return this.categoryCode;
    }

	public Long getFlagMain() {
		return flagMain;
	}

	public void setFlagMain(Long flagMain) {
		this.flagMain = flagMain;
	}
    
    
    
}


package com.hjy.dao.api;

import java.util.List;
import java.util.Map;

import com.hjy.dto.product.ProductStatus;
import com.hjy.dto.product.Productdescription;
import com.hjy.entity.product.PcProductinfo;

/**
 * 
 * 类: IProductDao <br>
 * 描述: openapi单独使用的商品数据库访问接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月4日 上午7:07:22
 */
public interface IApiProductInfoDao {

	/**
	 * 
	 * 方法: insert <br>
	 * 描述: 添加新商品 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月11日 下午2:18:00
	 * 
	 * @param product
	 * @return
	 */
	int insert(PcProductinfo product);

	/**
	 * 
	 * 方法: batchInsert <br>
	 * 描述: 批量添加商品 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午7:38:49
	 * 
	 * @param list
	 * @return
	 */
	int batchInsert(List<PcProductinfo> list);

	/**
	 * 
	 * 方法: updateProductByProductCodeOld <br>
	 * 描述: 根据外部商品编号修改商品信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午7:08:21
	 * 
	 * @param entity
	 * @return
	 */
	int updateProductByProductCodeOld(PcProductinfo entity);

	/**
	 * 
	 * 方法: updateProductPrice <br>
	 * 描述: 修改商品价格 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月8日 下午1:41:50
	 * 
	 * @param entity
	 * @return
	 */
	int updateProductPrice(PcProductinfo entity);

	/**
	 * 
	 * 方法: findProductCodeByOutCode <br>
	 * 描述: 根据外部商品编号查询惠家有商品编号 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月8日 上午10:07:09
	 * 
	 * @param productCodeOld
	 * @return
	 */
	String findProductCodeByOutCode(String productCodeOld);

	/**
	 * 
	 * 方法: getProductDescByCode <br>
	 * 描述: 根据商品编号productCode查询描述信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月26日 下午1:40:58
	 * 
	 * @param productCode
	 * @return
	 */
	Productdescription getProductDescByCode(String productCode);

	/**
	 * 
	 * 方法: getProductPicByCode <br>
	 * 描述: 根据商品编号productCode查询图片列表 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月26日 下午1:40:34
	 * 
	 * @param productCode
	 * @return
	 */
	List<String> getProductPicByCode(String productCode);

	/**
	 * 
	 * 方法: findProductBySellerProductype <br>
	 * 描述: 根据开放接口商户权限获取商品列表 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月26日 下午2:22:57
	 * 
	 * @param map
	 * @return
	 */
	List<PcProductinfo> findProductBySellerProductype(Map<String, String> map);

	/**
	 * 
	 * 方法: getProdcutCategoryName <br>
	 * 描述: 获取商品类目名称 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月26日 下午5:55:55
	 * 
	 * @param productCode
	 * @return
	 */
	String getProdcutCategoryName(String productCode);

	/**
	 * @description: 取当前时间上一个小时的，update_time有变化的商品
	 * 
	 * @param map
	 * @return
	 * @author Yangcl
	 * @date 2016年9月30日 下午2:14:25
	 * @version 1.0.0.1
	 */
	List<ProductStatus> selectProductByUpdateTime(Map<String, String> map);

	/**
	 * 根据商品编码数组查询商品列表<br>
	 * 
	 * @param dto
	 * @return
	 */
	List<PcProductinfo> findProductByProductCodes(com.hjy.dto.product.ProductInfo dto);
}
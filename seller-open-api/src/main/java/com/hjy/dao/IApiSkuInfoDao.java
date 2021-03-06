package com.hjy.dao;

import java.util.List;
import java.util.Map;

import com.hjy.entity.product.PcSkuinfo;

/**
 * 
 * 类: IApiSkuInfoDao <br>
 * 描述: openapi单独使用的sku数据库访问接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月4日 上午9:19:14
 */
public interface IApiSkuInfoDao {
	/**
	 * 
	 * 方法: batchInsert <br>
	 * 描述: 批量添加sku <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午7:38:49
	 * 
	 * @param list
	 * @return
	 */
	int batchInsert(List<PcSkuinfo> list);

	/**
	 * 
	 * 方法: updateSkuBySkuCodeOld <br>
	 * 描述: 根据外部sku编号修改sku信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 上午7:11:46
	 * 
	 * @param entity
	 * @return
	 */
	int updateSkuInfoBySkuCodeOld(PcSkuinfo entity);

	/**
	 * 
	 * 方法: updateSkuPrice <br>
	 * 描述: 修改sku价格 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月8日 下午1:41:13
	 * 
	 * @param entity
	 * @return
	 */
	int updateSkuPrice(PcSkuinfo entity);

	/**
	 * 
	 * 方法: updateSkuStore <br>
	 * 描述: 修改库存 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月8日 下午2:14:29
	 * 
	 * @param entity
	 * @return
	 */
	int updateSkuStore(PcSkuinfo entity);

	/**
	 * 
	 * 方法: findSkuInfoListByProductCodeOld <br>
	 * 描述: 根据外部商品编号查询sku列表 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月4日 下午4:25:03
	 * 
	 * @param productCodeOlds
	 * @return
	 */
	List<PcSkuinfo> findSkuInfoListByProductCodeOld(List<String> productCodeOlds);

	/**
	 * 
	 * 方法: findSkuByProductCode <br>
	 * 描述: 根据商品编号查询sku集合 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月25日 下午5:45:59
	 * 
	 * @param productCode
	 * @return
	 */
	List<PcSkuinfo> findSkuByProductCode(String productCode);

	/**
	 * 
	 * 方法: findSkuDataByProductCode <br>
	 * 描述: 根据商品编号集合查询sku信息列表 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月26日 下午4:10:03
	 * 
	 * @param codes
	 * @return
	 */
	List<Map<String, Object>> findSkuDataByProductCode(List<String> codes);

	/**
	 * 
	 * 方法: findSkuPriceByProducts <br>
	 * 描述: 根据productcode集合查询sku价格信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月29日 下午2:17:10
	 * 
	 * @param codes
	 * @return
	 */
	List<Map<String, Object>> findSkuPriceByProducts(List<String> codes);
}

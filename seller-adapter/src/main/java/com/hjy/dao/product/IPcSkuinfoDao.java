package com.hjy.dao.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;

import com.hjy.dao.BaseDao;
import com.hjy.entity.product.PcSkuinfo;

/**
 * @descriptions PcSkuinfo xml配置文件中有
 *               "com.cmall.dborm.txmodel.PcSkuinfoWithBLOBs" 请注意修改配置文件再抄
 * 
 * @date 2016年6月27日下午6:05:29
 * @author Yangcl
 * @version 1.0.1
 */
public interface IPcSkuinfoDao extends BaseDao<PcSkuinfo, Integer> {

	/**
	 * 
	 * 方法: findSkuCodeByProductCode <br>
	 * 描述: 根据商品编号查询产品编号 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 上午11:10:27
	 * 
	 * @param productCode
	 * @return
	 */
	String findSkuCodeByProductCode(String productCode);

	int updateSelectiveByProductCode(PcSkuinfo psModel); 
	
	int updateSelectiveBySkuCode(PcSkuinfo psModel); 
	
	/**
	 * @description:  根据product_code 找出该商品下所有的Sku信息，
	 * 以skuKey+"@"+skuValue 作为map的key，sku_code 作为value.
	 * map中的key是区分一个product_code下的唯一依据  
	 * 
	 * @param productCode
	 * @author Yangcl 
	 * @date 2017年1月4日 下午2:31:10 
	 * @version 1.0.0.1
	 */
	
	public List<PcSkuinfo> getSkuinfoByPcode(String productCode);
	
	/**
	 * @description: 根据这三个条件：sku_code、sku_key、sku_keyvalue 删除一条记录|这个方法用于open-api的ApiProductServiceImpl类
	 * 	 	 	 	 	 	 	 	 	 	 
	 * @author Yangcl 
	 * @date 2017年1月5日 下午6:14:12 
	 * @version 1.0.0.1
	 */
	public Integer deleteSkuinfo(Map<String , String> map);
}





































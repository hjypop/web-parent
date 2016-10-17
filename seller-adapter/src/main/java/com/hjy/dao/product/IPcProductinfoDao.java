package com.hjy.dao.product;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.hjy.dao.BaseDao;
import com.hjy.dto.ProductStatusDto;
import com.hjy.entity.product.PcProductinfo;

public interface IPcProductinfoDao extends BaseDao<PcProductinfo, Integer> {

	/**
	 * 
	 * 方法: findProductCodeOld <br>
	 * 描述: 根据卖家编号和第三方商户编号,查询旧的商品编号 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 上午7:46:44
	 * 
	 * @param info
	 * @return
	 */
	List<String> findProductCodeOld(PcProductinfo info);

	/**
	 * 
	 * 方法: findProductCodeOldByCode <br>
	 * 描述: 根据旧编号获取商品编码 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 上午10:17:38
	 * 
	 * @param productCodeOld
	 * @return
	 */
	String findProductCodeByOldCode(String productCodeOld);

	int updateSelectiveByProductCode(PcProductinfo ppModel);

	/**
	 * @descriptions p.product_code_old = :product_code_old p.seller_code =
	 *               :seller_code
	 * @param entity
	 * @return
	 * @date 2016年6月29日下午6:25:53
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
	public List<PcProductinfo> getListBySellerCode(PcProductinfo entity);

	/**
	 * @descriptions
	 * 
	 * @param list
	 *            productCodeOld list 另一个查询条件是 product_status =
	 *            '4497153900060002'
	 * @return
	 * @date 2016年6月30日上午10:05:58
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
	public List<PcProductinfo> getListByOldProductCode(List<String> list);

	/**
	 * @descriptions 条件依据如下： set product_status 4497153900060003 flag_sale 0
	 * 
	 *               where small_seller_code='SF03KJT' seller_code='SI2003'
	 *               product_code_old list
	 * @param list
	 * @return
	 * @date 2016年6月30日上午10:20:44
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
	public int updateByOldProductCode(List<String> list);
	
	/**
	 * @descriptions 获取平台上架或者下架的产品|条件：List<Strnig> productCodeList ; productStatus
	 * 
	 * @param
	 * @return
	 * @date 2016年6月30日上午10:05:58
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
	public List<PcProductinfo> getListByProductCodeList(ProductStatusDto dto);
	
	/**
	 * @descriptions 查询条件是 product_status = '4497153900060004'(平台强制下架的)
	 * @return
	 * @date 2016年6月30日上午10:05:58
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
	public List<PcProductinfo> getSoldOutProductList(String code);
	
	/**
	 * @description: 获取全部上架商品|查询条件是 product_status = '4497153900060002'(平台强制上架的)
	 * 
	 * @return
	 * @author Yangcl 
	 * @date 2016年10月9日 下午4:20:41 
	 * @version 1.0.0.1
	 */
	public List<PcProductinfo> getItemUpshelfProductList(String code);
	
	
	

	Integer updateProductStatus(PcProductinfo pcProductinfo); 
	
	
	/**
	 * @descriptions 根据产品编号Json批量置空跨境通商品编号(pc_productinfo 表 product_code_old 字段将被置空)
	 * 
	 * 危险操作|KjtOperationsManagerController.funcFour(String json, HttpSession session)
	 * 
	 * @param list 
	 * @date 2016年8月30日下午2:46:24
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public int updateNullByProductCode(List<String> list);
}






















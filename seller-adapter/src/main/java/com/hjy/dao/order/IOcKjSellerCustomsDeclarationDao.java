package com.hjy.dao.order;

import java.util.List;
import java.util.Map;

import com.hjy.dao.BaseDao;
import com.hjy.entity.order.OcKjSellerCustomsDeclaration;

public interface IOcKjSellerCustomsDeclarationDao extends BaseDao<OcKjSellerCustomsDeclaration, Integer> {
	
	/**
	 * @description: 获取报关数据集合 
	 * 
	 * @param map
	 * @return
	 * @author Yangcl 
	 * @date 2016年10月26日 下午5:23:21 
	 * @version 1.0.0.1
	 */
	public List<OcKjSellerCustomsDeclaration> getRequestList(Map<String , String> map);

}

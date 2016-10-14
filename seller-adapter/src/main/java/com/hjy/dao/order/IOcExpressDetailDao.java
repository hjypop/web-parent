package com.hjy.dao.order;

import java.util.List;

import com.hjy.dao.BaseDao;
import com.hjy.entity.order.OcExpressDetail;
import com.hjy.response.ApiShipmentsResponse;

public interface IOcExpressDetailDao extends BaseDao<OcExpressDetail , Integer>{

	/**
	 * @description: 根据order_code查询物流信息
	 * 
	 * @param list
	 * @return
	 * @author Yangcl 
	 * @date 2016年10月14日 上午11:36:40 
	 * @version 1.0.0.1
	 */
	public List<ApiShipmentsResponse> apiSelectLdShipments(List<String> list);
}

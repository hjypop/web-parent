package com.hjy.service.impl.order;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.order.IOcOrderaddressDao;
import com.hjy.entity.order.OcOrderaddress;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.order.IOcOrderadressService;

/**
 * 
 * 类: OcOrderadressServiceImpl <br>
 * 描述: 订单地址发票表业务处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午5:48:56
 */
@Service
public class OcOrderaddressServiceImpl extends BaseServiceImpl<OcOrderaddress, Integer>
		implements IOcOrderadressService {

	@Resource
	private IOcOrderaddressDao dao;

	/**
	 * 
	 * 方法: findOrderAddressByOrderCode <br>
	 * 描述: TODO
	 * 
	 * @param orderCode
	 * @return
	 * @see com.hjy.service.order.IOcOrderadressService#findOrderAddressByOrderCode(java.lang.String)
	 */
	@Override
	public OcOrderaddress findOrderAddressByOrderCode(String orderCode) {
		return dao.findOrderAddressByOrderCode(orderCode);
	}

}

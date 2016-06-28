package com.hjy.service.impl.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.order.IOcOrderinfoService;

/**
 * 
 * 类: OcOrderinfoServiceImpl <br>
 * 描述: 订单信息表业务逻辑处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午6:15:57
 */
@Service
public class OcOrderinfoServiceImpl extends BaseServiceImpl<OcOrderinfo, Integer> implements IOcOrderinfoService {

	@Resource
	private IOcOrderinfoDao dao;

	/**
	 * 
	 * 方法: findOrderInfoByOrderCode <br>
	 * 描述: TODO
	 * 
	 * @param orderCode
	 * @return
	 * @see com.hjy.service.order.IOcOrderinfoService#findOrderInfoByOrderCode(java.lang.String)
	 */
	@Override
	public OcOrderinfo findOrderInfoByOrderCode(String orderCode) {
		return dao.findOrderInfoByOrderCode(orderCode);
	}
}

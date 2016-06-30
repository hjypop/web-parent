package com.hjy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.IOcOrderKjtDetailDao;
import com.hjy.entity.OcOrderKjtDetail;
import com.hjy.service.IOcOrderKjtDetailService;

/**
 * 
 * 类: OcOrderKjtDetailServiceImpl <br>
 * 描述: 跨境通订单明细表业务处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午5:21:15
 */
@Service
public class OcOrderKjtDetailServiceImpl extends BaseServiceImpl<OcOrderKjtDetail, Integer>
		implements IOcOrderKjtDetailService {

	@Resource
	private IOcOrderKjtDetailDao dao;

	/**
	 * 
	 * 方法: findOrderDetailByCodeSeq <br>
	 * 描述: TODO
	 * 
	 * @param orderCodeSeq
	 * @return
	 * @see com.hjy.service.IOcOrderKjtDetailService#findOrderDetailByCodeSeq(java.lang.String)
	 */
	@Override
	public List<OcOrderKjtDetail> findOrderDetailByCodeSeq(String orderCodeSeq) {
		return dao.findOrderDetailByCodeSeq(orderCodeSeq);
	}

}

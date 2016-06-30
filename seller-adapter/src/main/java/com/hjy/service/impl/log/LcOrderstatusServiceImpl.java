package com.hjy.service.impl.log;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.log.ILcOrderstatusDao;
import com.hjy.entity.log.LcOrderstatus;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.log.ILcOrderstatusService;

/**
 * 
 * 类: LcOrderstatusServiceImpl <br>
 * 描述: 订单状态日志表业务处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月30日 上午9:50:12
 */
@Service
public class LcOrderstatusServiceImpl extends BaseServiceImpl<LcOrderstatus, Integer> implements ILcOrderstatusService {

	@Resource
	private ILcOrderstatusDao dao;
}

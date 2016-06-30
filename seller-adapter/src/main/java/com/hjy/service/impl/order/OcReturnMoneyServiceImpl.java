package com.hjy.service.impl.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.order.IOcReturnMoneyDao;
import com.hjy.entity.order.OcReturnMoney;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.order.IOcReturnMoneyService;

/**
 * 
 * 类: OcReturnMoneyServiceImpl <br>
 * 描述: 退款管理表业务处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月30日 上午10:32:11
 */
@Service
public class OcReturnMoneyServiceImpl extends BaseServiceImpl<OcReturnMoney, Integer> implements IOcReturnMoneyService {

	@Resource
	private IOcReturnMoneyDao dao;
}

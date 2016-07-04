package com.hjy.service.impl.zapdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjy.dao.zapdata.IZaWeblogDao;
import com.hjy.entity.zapdata.ZaWeblog;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.zapdata.IZaWeblogService;

/**
 * 
 * 类: ZaWeblogServiceImpl <br>
 * 描述: 日志表业务处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午9:03:30
 */
@Service
public class ZaWeblogServiceImpl extends BaseServiceImpl<ZaWeblog, Integer> implements IZaWeblogService {

	@Autowired
	private IZaWeblogDao dao;
}

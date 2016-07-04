package com.hjy.service.impl.zapdata;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.zapdata.IZaUserinfoDao;
import com.hjy.entity.zapdata.ZaUserinfo;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.zapdata.IZaUserinfoService;

/**
 * 
 * 类: ZaUserinfoServiceImpl <br>
 * 描述: 用户信息表业务处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午8:10:21
 */
@Service
public class ZaUserinfoServiceImpl extends BaseServiceImpl<ZaUserinfo, Integer> implements IZaUserinfoService {

	@Resource
	private IZaUserinfoDao dao;

	/**
	 * 
	 * 方法: findUserInfoByCookie <br>
	 * 描述: TODO
	 * 
	 * @param cookieUser
	 * @return
	 * @see com.hjy.service.zapdata.IZaUserinfoService#findUserInfoByCookie(java.lang.String)
	 */
	@Override
	public ZaUserinfo findUserInfoByCookie(String cookieUser) {
		return dao.findUserInfoByCookie(cookieUser);
	}

}

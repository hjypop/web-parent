package com.hjy.service.impl.zapdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjy.dao.zapdata.IZaUsermenuDao;
import com.hjy.entity.zapdata.ZaUsermenu;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.zapdata.IZaUsermenuService;

/**
 * 
 * 类: ZaUsermenuServiceImpl <br>
 * 描述: 用户菜单表业务处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午8:57:23
 */
@Service
public class ZaUsermenuServiceImpl extends BaseServiceImpl<ZaUsermenu, Integer> implements IZaUsermenuService {

	@Autowired
	private IZaUsermenuDao dao;

	/**
	 * 
	 * 方法: findMenuByUserCode <br>
	 * 描述: TODO
	 * 
	 * @param userCode
	 * @return
	 * @see com.hjy.service.zapdata.IZaUsermenuService#findMenuByUserCode(java.lang.String)
	 */
	@Override
	public List<ZaUsermenu> findMenuByUserCode(String userCode) {
		return dao.findMenuByUserCode(userCode);
	}

}

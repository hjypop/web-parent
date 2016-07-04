package com.hjy.service.impl.zapdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjy.dao.zapdata.IZaRolemenuDao;
import com.hjy.entity.zapdata.ZaRolemenu;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.zapdata.IZaRolemenuService;

/**
 * 
 * 类: ZaRolemenuServiceImpl <br>
 * 描述: 角色菜单表业务处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午8:51:08
 */
@Service
public class ZaRolemenuServiceImpl extends BaseServiceImpl<ZaRolemenu, Integer> implements IZaRolemenuService {

	@Autowired
	private IZaRolemenuDao dao;

	/**
	 * 
	 * 方法: findMenuByRoleCode <br>
	 * 描述: TODO
	 * 
	 * @param roleCodes
	 * @return
	 * @see com.hjy.service.zapdata.IZaRolemenuService#findMenuByRoleCode(java.util.List)
	 */
	@Override
	public List<ZaRolemenu> findMenuByRoleCode(List<String> roleCodes) {
		return dao.findMenuByRoleCode(roleCodes);
	}

}

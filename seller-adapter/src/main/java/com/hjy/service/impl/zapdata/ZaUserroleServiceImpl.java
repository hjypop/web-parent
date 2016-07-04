package com.hjy.service.impl.zapdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjy.dao.zapdata.IZaUserroleDao;
import com.hjy.entity.zapdata.ZaUserrole;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.zapdata.IZaUserroleService;

/**
 * 
 * 类: ZaUserroleServiceImpl <br>
 * 描述: 用户角色表业务处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月4日 上午8:36:15
 */
@Service
public class ZaUserroleServiceImpl extends BaseServiceImpl<ZaUserrole, Integer> implements IZaUserroleService {

	@Autowired
	private IZaUserroleDao dao;

	@Override
	public List<ZaUserrole> findRoleByUserCode(String userCode) {
		return dao.findRoleByUserCode(userCode);
	}

}

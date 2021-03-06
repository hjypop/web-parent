package com.hjy.service.impl.member;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.member.IMcAuthenticationInfoDao;
import com.hjy.entity.member.McAuthenticationInfo;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.member.IMcAuthenticationInfoService;

/**
 * 
 * 类: McAuthenticationInfoServiceImpl <br>
 * 描述: mc_authenticationInfo表业务处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午6:32:27
 */
@Service
public class McAuthenticationInfoServiceImpl extends BaseServiceImpl<McAuthenticationInfo, Integer>
		implements IMcAuthenticationInfoService {

	@Resource
	private IMcAuthenticationInfoDao dao;

	/**
	 * 
	 * 方法: updateCustomsStatus <br>
	 * 描述: TODO
	 * 
	 * @param entity
	 * @return
	 * @see com.hjy.service.member.IMcAuthenticationInfoService#updateCustomsStatus(com.hjy.entity.member.McAuthenticationInfo)
	 */
	@Override
	public int updateCustomsStatus(McAuthenticationInfo entity) {
		return dao.updateCustomsStatus(entity);
	}

}

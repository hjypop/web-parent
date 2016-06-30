package com.hjy.service.impl.member;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.member.IMcLoginInfoDao;
import com.hjy.entity.member.McLoginInfo;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.member.IMcLoginInfoService;

/**
 * 
 * 类: McLoginInfoServiceImpl <br>
 * 描述: 登陆信息表业务处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月30日 下午1:49:12
 */
@Service
public class McLoginInfoServiceImpl extends BaseServiceImpl<McLoginInfo, Integer> implements IMcLoginInfoService {

	@Resource
	private IMcLoginInfoDao dao;

	/**
	 * 
	 * 方法: findLoginInfoByMemberCode <br>
	 * 描述: TODO
	 * 
	 * @param memberCode
	 * @return
	 * @see com.hjy.service.member.IMcLoginInfoService#findLoginInfoByMemberCode(java.lang.String)
	 */
	@Override
	public McLoginInfo findLoginInfoByMemberCode(String memberCode) {
		return dao.findLoginInfoByMemberCode(memberCode);
	}

}

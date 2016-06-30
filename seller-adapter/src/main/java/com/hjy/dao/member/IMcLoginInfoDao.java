package com.hjy.dao.member;

import com.hjy.dao.BaseDao;
import com.hjy.entity.member.McLoginInfo;

/**
 * 
 * 类: IMcLoginInfoDao <br>
 * 描述: 登陆信息表数据库访问接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月30日 下午1:48:49
 */
public interface IMcLoginInfoDao extends BaseDao<McLoginInfo, Integer> {

	/**
	 * 
	 * 方法: findLoginInfoByMemberCode <br>
	 * 描述: 根据用户编号查询登陆信息 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月30日 下午1:54:09
	 * 
	 * @param memberCode
	 * @return
	 */
	McLoginInfo findLoginInfoByMemberCode(String memberCode);
}

package com.hjy.dao.member;

import com.hjy.dao.BaseDao;
import com.hjy.entity.member.McAuthenticationInfo;

/**
 * 
 * 类: IMcAuthenticationInfoDao <br>
 * 描述: mc_authenticationInfo表数据库访问接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午6:25:41
 */
public interface IMcAuthenticationInfoDao extends BaseDao<McAuthenticationInfo, Integer> {

	/**
	 * 
	 * 方法: updateCustomsStatus <br>
	 * 描述: 根据member_code和idcard_number修改customs_status <br>
	 * 		用于定时任务：定时同步跨境通订单状态<br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月30日 上午7:48:29
	 * @param entity
	 * @return
	 */
	int updateCustomsStatus(McAuthenticationInfo entity);
}

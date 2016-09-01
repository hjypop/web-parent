package com.hjy.dao;

import java.util.List;

import com.hjy.entity.LcRsyncKjtLog;

/**
 * 
 * 类: ILcRsyncKjtLogDao <br>
 * 描述: 同步跨境通日志表数据库访问接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午3:13:18
 */
public interface ILcRsyncKjtLogDao extends BaseDao<LcRsyncKjtLog, Integer> {
	/**
	 * 
	 * 方法: findLatelyStatusData <br>
	 * 描述: 获取最近一次成功处理的状态数据 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 下午3:42:10
	 * 
	 * @param rsyncTarget
	 * @return
	 */
	String findLatelyStatusData(String rsyncTarget);
	
	/**
	 * @descriptions 根据右侧4个条件 rsync_target |requestTime|responseData|requestData 查询40条记录
	 * 
	 * @param info 
	 * @date 2016年9月1日下午4:22:42
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public List<LcRsyncKjtLog> selectLogByType(LcRsyncKjtLog info);
}















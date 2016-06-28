package com.hjy.service;

import com.hjy.entity.LcRsyncKjtLog;

/**
 * 
 * 类: ILcRsyncKjtLogService <br>
 * 描述: 同步跨境通日志表业务逻辑处理实现接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午3:48:56
 */
public interface ILcRsyncKjtLogService extends IBaseService<LcRsyncKjtLog, Integer> {
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
}

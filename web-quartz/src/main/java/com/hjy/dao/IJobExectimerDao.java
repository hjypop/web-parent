package com.hjy.dao;

import java.util.List;

import com.hjy.pojo.entity.system.JobExectimer;

public interface IJobExectimerDao extends BaseDao<JobExectimer, Integer> {

	/**
	 * @descriptions JobServiceImpl 使用一次
	 * 
	 * @param entity
	 * @return
	 * @date 2016年6月22日下午3:38:32
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public Integer updateSelectiveByExecCode(JobExectimer entity);
	
	/**
	 * @descriptions KjtOperationsManagerServiceImpl 调用一次
	 *  
	 * @date 2016年8月25日下午1:47:41
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public Integer updateSelectiveByFlag(JobExectimer entity);
	
	public Integer updateSelectiveByOrderCode(JobExectimer entity);
	
	/**
	 * @description: 根据  exec_type 和 exec_info 来查询一个订单
	 *
	 * @author Yangcl
	 * @date 2016年9月18日 下午5:54:43 
	 * @version 1.0.0.1
	 */
	public List<JobExectimer> findByOrderCode(JobExectimer entity);
}

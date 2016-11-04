package com.quartz.quartz;

import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

import com.core.annotation.Inject;
import com.core.iface.IBaseJob;
import com.core.model.MDataMap;
import com.core.system.init.RootInit;
import com.quartz.pojo.entity.system.SysJob;
import com.quartz.quartz.model.MJobInfo;
import com.quartz.quartz.support.JobSupport;
import com.quartz.service.IJobService;


public class JobInit extends RootInit {
	
	@Inject
	private IJobService jobService;

	public boolean onInit() {
		boolean flag = true;
		SysJob entity = new SysJob();
		entity.setFlagEnable(1);
		entity.setRunGroupDid(""); 
		List<SysJob> list = jobService.findSysJobList(entity);
		for(SysJob sj : list){
			String jobTriger = sj.getJobTriger();
			// 如果事件定义的时间为空 则系统加载时则执行
			if (StringUtils.isBlank(jobTriger)) {
				try {
					IBaseJob iJob = (IBaseJob) ClassUtils.getClass(sj.getJobClass()).newInstance();
					iJob.doExecute(null);
					getLogger().logInfo(970212017, sj.getJobTitle());   // TODO 970212017 $$$$$$$$$$$$$$$$$$$$$$$$$$
				} catch (Exception e) {
					flag = false;
					e.printStackTrace();
				}
			}else{
				MJobInfo mJobInfo = new MJobInfo();
				mJobInfo.setJobClass(sj.getJobClass());
				mJobInfo.setJobTriger(sj.getJobTriger());
				mJobInfo.setJobName(sj.getUid());
				if(StringUtils.isNotBlank(sj.getSetExtend())){
					MDataMap mExtendMap = new MDataMap().inUrlParams(sj.getSetExtend());
					if (mExtendMap.containsKey("type_log")) {
						mJobInfo.setExtendTypeLog(Integer.valueOf(mExtendMap.get("type_log")));
					}
					
					// 如果有默认锁定时间 则使用默认锁定时间 否则默认锁定300秒
					if (mExtendMap.containsKey("lock_timer")) {
						mJobInfo.setExtendLockTimer(Integer.valueOf(mExtendMap.get("lock_timer")));
					} else {
						mJobInfo.setExtendLockTimer(300);
					}
				}
				
				JobSupport.getInstance().addJob(mJobInfo);
				getLogger().logInfo(970212016 , sj.getJobTitle() , sj.getJobTriger() ); // TODO 970212016 $$$$$$$$$$$$$$$$$$$$$$$$$$
			}
		}
		return flag;     
	}
	
	@Override
	public boolean onDestory() {
		// TODO Auto-generated method stub
		return false;
	}

}

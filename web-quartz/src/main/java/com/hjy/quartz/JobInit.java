package com.hjy.quartz;

import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hjy.iface.IBaseJob;
import com.hjy.model.MDataMap;
import com.hjy.pojo.entity.system.SysJob;
import com.hjy.quartz.model.MJobInfo;
import com.hjy.quartz.support.JobSupport;
import com.hjy.service.IJobService;
import com.hjy.system.init.RootInit;

public class JobInit extends RootInit {
	
	@Autowired
	private IJobService jobService;
	

	public boolean onInit(){
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
		return true;      // TODO 这里是否应该返回 flag ??????????
	}
	
	public boolean onInit2() {
		boolean bFlag = true;

//		MDataMap mJoQuerybMap = new MDataMap();
//		for (MDataMap mJob : DbUp.upTable("za_job") .queryIn("", "", "flag_enable=1", mJoQuerybMap, -1, -1, "run_group_did", sGroups)) {
//
//			String sJobTriger = mJob.get("job_triger");
//
//			// 如果事件定义的时间为空 则系统加载时则执行
//			if (StringUtils.isBlank(sJobTriger)) {
//
//				try {
//					IBaseJob iJob = (IBaseJob) ClassUtils.getClass(
//							mJob.get("job_class")).newInstance();
//
//					iJob.doExecute(null);
//					getLogger().logInfo(970212017, mJob.get("job_title"));
//
//				} catch (Exception e) {
//					bFlag = false;
//					e.printStackTrace();
//				}
//
//			} else {
//				// JobSupport.getInstance().addJob(mJob.get("job_class"),mJob.get("job_triger"),
//				// mJob.get("uid"));
//
//				MJobInfo mJobInfo = new MJobInfo();
//
//				mJobInfo.setJobClass(mJob.get("job_class"));
//				mJobInfo.setJobTriger(mJob.get("job_triger"));
//				mJobInfo.setJobName(mJob.get("uid"));
//
//				if (mJob.containsKey("set_extend") && StringUtils.isNotBlank(mJob.get("set_extend"))) {
//
//					MDataMap mExtendMap = new MDataMap().inUrlParams(mJob.get("set_extend"));
//
//					if (mExtendMap.containsKey("type_log")) {
//						mJobInfo.setExtendTypeLog(Integer.valueOf(mExtendMap.get("type_log")));
//					}
//
//					// 如果有默认锁定时间 则使用默认锁定时间 否则默认锁定300秒
//					if (mExtendMap.containsKey("lock_timer")) {
//						mJobInfo.setExtendLockTimer(Integer.valueOf(mExtendMap
//								.get("lock_timer")));
//					} else {
//						mJobInfo.setExtendLockTimer(300);
//					}
//
//				}
//
//				JobSupport.getInstance().addJob(mJobInfo);
//				getLogger().logInfo(970212016, mJob.get("job_title"),
//						mJob.get("job_triger"));
//			}
//
//		}
		return true;
	}

	@Override
	public boolean onDestory() {
		// TODO Auto-generated method stub
		return false;
	}

}

package com.hjy.quartz.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hjy.annotation.Inject;
import com.hjy.helper.FormatHelper;
import com.hjy.helper.GsonHelper;
import com.hjy.helper.LogHelper;
import com.hjy.helper.WebHelper;
import com.hjy.iface.IBaseResult;
import com.hjy.model.MDataMap;
import com.hjy.pojo.entity.system.JobExectimer;
import com.hjy.quartz.model.ConfigJobExec;
import com.hjy.quartz.model.JobResult;
import com.hjy.service.IJobService;
// properties配置信息核对完成
public abstract class RootJobForExec extends RootJob {
	
	@Inject
	public IJobService jobService;

	public void doExecute(JobExecutionContext context){
		ConfigJobExec configJobExec = getConfig();
		Date currentTime = new Date();
		JobExectimer entity = new JobExectimer();
		entity.setExecTime(currentTime);
		entity.setExecType(configJobExec.getExecType());
		entity.setExecNumber(configJobExec.getMaxExecNumber());
		List<JobExectimer> list = jobService.listJobExectimer(entity);
		for(JobExectimer je : list){
			String execCode = je.getExecCode();
			String execInfo = je.getExecInfo();
			String lockKey = WebHelper.getInstance().addLock(configJobExec.getExecJobLock(), execCode, execInfo);
			if (StringUtils.isNotEmpty(lockKey)){
				IBaseResult iResult = null;
				try {
					iResult = execByInfo(execInfo.trim());
					LogHelper.addLog("job_exec", iResult);
				}catch(Exception e){
					JobResult rootResult = new JobResult();
					rootResult.setCode(300000002);           // 执行任务失败：info.zapweb.9699.properties line51
					rootResult.setMessage(getInfo(300000002) + e.getMessage());
					e.printStackTrace();
				}
				if (iResult.getCode() != 1) {
					// 当已执行次数等于该数字时 发送报警邮件 一条记录只发送一次
					if (configJobExec.getNoticeOnce() > 0 && je.getExecNumber() == configJobExec.getNoticeOnce()){
						// TODO 暂不处理 - Yangcl
					}
				}
				JobExectimer update = new JobExectimer();
				update.setBeginTime(currentTime);
				update.setEndTime(new Date());
				update.setFlagSuccess(Integer.parseInt(iResult.getCode() == 1 ? "1" : "0")); 
				update.setRemark(je.getRemark() + GsonHelper.toJson(iResult)); 
				update.setExecCode(execCode); 
				update.setExecNumber(je.getExecNumber()+1); 
				int flag = jobService.updateJobExectimer(update);
				
				WebHelper.getInstance().unLock(lockKey);
			}
		}
	}
	 
	

	/**
	 * 根据内容执行
	 * 
	 * @param sInfo
	 * @return
	 */
	public abstract IBaseResult execByInfo(String sInfo);

	/**
	 * 获取配置
	 * 
	 * @return
	 */
	public abstract ConfigJobExec getConfig();

	
	
}



























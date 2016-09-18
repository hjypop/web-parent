package com.hjy.selleradapter.job;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.annotation.Inject;
import com.hjy.dao.order.IOcKjSellerSeparateOrderDao;
import com.hjy.dto.minspc.VoidDto;
import com.hjy.entity.order.OcKjSellerSeparateOrder;
import com.hjy.helper.WebHelper;
import com.hjy.quartz.job.RootJob;
import com.hjy.selleradapter.minspc.RsyncVoidOrder;

/**
 * 
 * @title: com.hjy.selleradapter.job.JobForVoidOrder.java 
 * @description: 订单作废
 *
 * @author Yangcl
 * @date 2016年9月7日 下午1:34:02 
 * @version 1.0.0
 */
public class JobForVoidOrder extends RootJob {

	@Inject 
	private IOcKjSellerSeparateOrderDao kjSellerSeparateOrderDao; 
	
	
	@Override
	public void doExecute(JobExecutionContext context) {
		String lockCode = WebHelper.getInstance().addLock(1000 , "JobForVoidOrder");	// 分布式锁定
		if (StringUtils.isNotBlank(lockCode)) {
			try {
				VoidDto dto = new VoidDto();
				dto.setSellerCode("SF03MINSPC");
				dto.setSellerStatus("0");
				List<OcKjSellerSeparateOrder> voidList = kjSellerSeparateOrderDao.selectVoidOrderInfo(dto);
				if(voidList != null && voidList.size() != 0){
					for(OcKjSellerSeparateOrder e : voidList){
						RsyncVoidOrder rvo = new RsyncVoidOrder();
						rvo.setUpdateInfo(e); 
						rvo.doRsync();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();  
			}finally {
				WebHelper.getInstance().unLock(lockCode);
			}
		}
	}

}



















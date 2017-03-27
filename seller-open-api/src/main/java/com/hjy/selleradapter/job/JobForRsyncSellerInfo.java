package com.hjy.selleradapter.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.annotation.Inject;
import com.hjy.dao.user.IUcSellerinfoDao;
import com.hjy.dao.webcore.IWcSellerinfoDao;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.model.SellerInfoModel;
import com.hjy.quartz.job.RootJob;

/**
 * @description: 定时同步 uc_sellerinfo、uc_seller_info_extend两个表中的部分有效字段到wc_sellerinfo表中
 * 
 * @group seller-adapter-openapi 
 * 
 * @author Yangcl
 * @date 2017年2月21日 下午5:48:59 
 * @version 1.0.0
 */
public class JobForRsyncSellerInfo extends RootJob {

	@Inject
	private IWcSellerinfoDao wcSellerinfoDao;   
	@Inject 
	private IUcSellerinfoDao ucSellerinfoDao;
	
	
	
	@Override
	public void doExecute(JobExecutionContext context) {
		String formate_ = "yyyy-MM-dd 00:00:00";
		Map<String , String> map = new HashMap<String , String>();
		map.put("startTime", this.getNextDate(new Date(), 0, formate_));
		map.put("endTime", this.getNextDate(new Date(), 1, formate_)); 
		List<SellerInfoModel> mlist = wcSellerinfoDao.selectUcSellerInfo(map);
		if(mlist != null && mlist.size() != 0){   // 如果当天存在新创建的商户，则插入到数据库   
			List<WcSellerinfo> list = wcSellerinfoDao.queryPage(null); 
			if(list != null && list.size() != 0){
				List<SellerInfoModel> excludeList = new ArrayList<>();
				for(SellerInfoModel m : mlist){   // 开始过滤出不在wc_sellerinfo表中的数据，然后插入数据库
					for(WcSellerinfo w : list){
						if(m.getSmallSellerCode().equals(w.getSellerCode())){
							excludeList.add(m);
							break;
						}
					}
				}
				mlist.removeAll(excludeList);
			}
			
			// 开始插入数据到wc_sellerinfo表
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(SellerInfoModel m : mlist){
				WcSellerinfo e = new WcSellerinfo();
				e.setUid(UUID.randomUUID().toString().replace("-", "")); 
				e.setSellerCode(m.getSmallSellerCode());
				if(StringUtils.isNotBlank(m.getSellerName())){
					e.setSellerName(m.getSellerName());
				}
				if(StringUtils.isNotBlank(m.getCompanyPhone())){
					e.setSellerTelephone(m.getCompanyPhone());
				}
				e.setType(1);  // 1：惠家有的商户；2：分销平台   |    注：此处默认为惠家有商户。如果要变更问平台在修改页面进行修改操作 
				e.setCommission("[{\"type\":\"4497478100050000\",\"commission\":\"0\"},{\"type\":\"4497478100050001\",\"commission\":\"0\"},{\"type\":\"4497478100050002\",\"commission\":\"0\"},{\"type\":\"4497478100050003\",\"commission\":\"0\"},{\"type\":\"4497478100050004\",\"commission\":\"0\"}]");
				e.setSellerType(m.getUcSellerType());
				e.setCreateTime(formatter.format(new Date())); 
				e.setCreator("open-api-JobForRsyncSellerInfo"); 
				e.setStatus(0); // 商户状态 0 未开通 1 已开通 2 已禁用
				if(e.getSellerType().equals("4497478100050001")){  // 普通商户
					e.setSettlement("4497471600110001");  // 结算方式：常规结算
					e.setPurchase("4497471600160001");  // 采购类型：代销
				}else if(e.getSellerType().equals("4497478100050002")){  // 跨境商户  
					e.setSettlement("4497471600110003");  // 结算方式：服务费结算
					e.setPurchase("4497471600160003");  // 采购类型：代收代付
				}else if(e.getSellerType().equals("4497478100050003")){  // 跨境直邮
					e.setSettlement("4497471600110003");  // 结算方式：服务费结算
					e.setPurchase("4497471600160003");  // 采购类型：代收代付  
				}else if(e.getSellerType().equals("4497478100050004")){  // 平台入驻
					e.setSettlement("4497471600110003");  // 结算方式：服务费结算
					e.setPurchase("4497471600160003");  // 采购类型：代收代付  
				}
				wcSellerinfoDao.insertSelective(e);
			}
		}
	}

	private String getNextDate(Date date , int flag , String formate_){  
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE , flag);//把日期往后增加一天.整数往后推,负数往前移动
		 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		 SimpleDateFormat formatter = new SimpleDateFormat(formate_);
		 
		 return formatter.format(date);
	}
}

















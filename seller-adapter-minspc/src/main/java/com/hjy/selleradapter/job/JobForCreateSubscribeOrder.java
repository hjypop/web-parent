package com.hjy.selleradapter.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.annotation.Inject;
import com.hjy.dao.IJobExectimerDao;
import com.hjy.dao.order.IOcOrderPayDao;
import com.hjy.dao.order.IOcOrderdetailDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.dao.system.IScTmpDao;
import com.hjy.dto.minspc.MinspcOrderdetailOne;
import com.hjy.dto.minspc.MinspcOrderinfoSelect;
import com.hjy.dto.request.subscribeOrder.AuthenticationInfo;
import com.hjy.dto.request.subscribeOrder.Item;
import com.hjy.dto.request.subscribeOrder.PayInfo;
import com.hjy.dto.request.subscribeOrder.ShippingInfo;
import com.hjy.dto.request.subscribeOrder.SoRequest;
import com.hjy.entity.system.ScTmp;
import com.hjy.helper.WebHelper;
import com.hjy.pojo.entity.system.JobExectimer;
import com.hjy.quartz.job.RootJob;
import com.hjy.selleradapter.minspc.RsyncSubscribeOrder;

/**
 * @title: com.hjy.job.JobForCreateSubscribeOrder.java 
 * @description: 生成订阅订单（并发送海关）
 *
 * @author Yangcl
 * @date 2016年9月6日 下午2:24:05 
 * @version 1.0.0
 */
public class JobForCreateSubscribeOrder extends RootJob {
	@Inject
	private IOcOrderinfoDao orderinfoDao;
	@Inject
	private IJobExectimerDao jobExectimerDao;
	@Inject
	private IOcOrderdetailDao orderDetailDao;
	@Inject
	private IScTmpDao scTmpDao;
	@Inject
	private IOcOrderPayDao ocOrderPayDao; 
	
	private List<MinspcOrderdetailOne> mooList;  // 此处使用全局变量为RsyncSubscribeOrder.java赋值。不得已而用之。
	
	/**
	 * @descriptions 订单同步，一条一条的同步。
	 *
	 * @date 2016年9月18日 下午10:07:59
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public void doExecute(JobExecutionContext context) {
		String lockCode = WebHelper.getInstance().addLock(1000 , "JobForCreateSubscribeOrder");	// 分布式锁定
		if (StringUtils.isNotBlank(lockCode)) {
			Date currentTime = new Date();
			List<String> orderCodeList_ = new ArrayList<String>();
			try {
				JobExectimer entity = new JobExectimer();
				entity.setExecTime(currentTime);
				entity.setExecType(getConfig("seller_adapter_minspc.exec_type"));  // 449746990014 针对民生品粹
				entity.setExecNumber(20);
				List<JobExectimer> jobExectimerList = jobExectimerDao.findList(entity);  // 取出民生品粹 等待同步的订单
				for(JobExectimer job : jobExectimerList){
					String orderCode = job.getExecInfo();
					orderCodeList_.add(orderCode);
				}
				// 多表联查，获取所需信息
				List<MinspcOrderinfoSelect> morList = orderinfoDao.getMinspcOrderinfoList(orderCodeList_);         
				for(MinspcOrderinfoSelect mo : morList){
					Thread.sleep(1000);// 防止接口访问过快
					RsyncSubscribeOrder rso = new RsyncSubscribeOrder();
					rso.setSoRequest(this.requestInit(mo));  
					rso.setMooList(mooList); // 注意这句代码在下面，有顺序性
					rso.doRsync();
				}
			} catch (Exception e) {
				e.printStackTrace();  
			}finally {
				WebHelper.getInstance().unLock(lockCode);
			}
		}
	}

	/**
	 * @description: 请求数据转换
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月12日 下午2:49:43 
	 * @version 1.0.0.1
	 */
	private SoRequest requestInit(MinspcOrderinfoSelect mo){
		SoRequest r = new SoRequest();
		r.setMerchantOrderID(mo.getOrderCode());
		
		PayInfo pi = new PayInfo();
		pi.setProductAmount(mo.getProductMoney()); // 商品总金额 TODO 这里应该是拆单后Sku sell
		pi.setShippingAmount(mo.getTransportMoney()); // 运费总金额
		pi.setTaxAmount(new BigDecimal(0.00)); // 行邮税总金额，不是税率|经过沟通默认为0.00 - Yangcl
		pi.setCommissionAmount(new BigDecimal(0.00)); // 手续费，可填0.00|经过沟通默认为0.00 - Yangcl
		pi.setPayTypeSysNo(117);  // 支付方式：112支付宝117银联118微信|默认117，避免对我平台用户行为进行分析 - Yangcl
		String paySequenceid = ocOrderPayDao.findListByOrderCode(mo.getOrderCode()).get(0).getPaySequenceid();
		pi.setPaySerialNumber(paySequenceid); // 支付流水号，不能重复       
		r.setPayInfo(pi); 
		
		ShippingInfo si = new ShippingInfo();
		si.setReceiveName(mo.getAuthName()); 
		si.setReceivePhone(mo.getMobile());
		si.setReceiveAddress(mo.getAddress());
		si.setReceiveAreaCode(mo.getAreaCode()); // 收获地区编号（根据国家统计局的《最新县及县以上行政区划代码》）
		si.setShipTypeID("375");  // 订单物流运输编号 |经与对接人沟通，统一设置为375：韵达快递
		si.setReceiveAreaName(this.getAreaName(mo.getAreaCode())); // 根据 oc_orderadress 表的 area_code字段来拼接中文
		r.setShippingInfo(si); 
		
		AuthenticationInfo ai = new AuthenticationInfo();
		ai.setName(mo.getAuthName());
		ai.setIDCardType(0); // 经与对接人沟通，统一设置为0：身份证
		ai.setIDCardNumber(mo.getAuthIdcardNumber());
		ai.setPhoneNumber(mo.getMobile());
		ai.setEmail(mo.getEmail());
		r.setAuthenticationInfo(ai);
		
		mooList =  orderDetailDao.getMinspcOrderdetailOneList(mo.getOrderCode());
		// 拆单信息 | 将民生品粹的商品拆出来，然后商品信息发给他们 
		List<Item> itemList = new ArrayList<Item>();
		for(MinspcOrderdetailOne e : mooList){
			Item sku = new Item();
			sku.setProductID(e.getProductID());
			sku.setQuantity(e.getQuantity());
			sku.setSalePrice(e.getSalePrice());
			sku.setTaxPrice(new BigDecimal(0.00)); // 行邮税 - 单品税费|经过沟通默认为0.00 - Yangcl
			itemList.add(sku);
		}
		r.setItemList(itemList); 
		
		
		return r;
	}
	
	/**
	 * @description:  根据 oc_orderadress 表的 area_code字段来拼接中文
	 *
	 * @throws 
	 * @author Yangcl
	 * @date 2016年9月13日 上午10:32:21 
	 * @version 1.0.0.1
	 */
	private String getAreaName(String areaCode) {
		ScTmp p = new ScTmp();
		p.setCode(areaCode.subSequence(0, 2) + "0000");
		p = scTmpDao.findByType(p);
		ScTmp c = new ScTmp();
		c.setCode(areaCode.subSequence(0, 4) + "00");
		c = scTmpDao.findByType(c);
		ScTmp a = new ScTmp();
		a.setCode(areaCode);
		a = scTmpDao.findByType(a);
		String prov = p.getName();
		String city = c.getName();
		String area = a.getName();
		return prov + "," + (StringUtils.startsWith(city, "省直辖县级行政区划") ? area : city) + "," + area;
	}


}














































package com.hjy.selleradapter.kjt;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hjy.annotation.Inject;
import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.helper.FormatHelper;
import com.hjy.model.MDataMap;
import com.hjy.model.MWebResult;
import com.hjy.model.RsyncDateCheck;
import com.hjy.model.RsyncResult;
import com.hjy.selleradapter.kjt.config.RsyncConfigGetKjtProductIdByDate;
import com.hjy.selleradapter.kjt.model.RsyncModelGetKjtChangeProduct;
import com.hjy.selleradapter.kjt.request.RsyncRequestGetKjtProductIdByDate;
import com.hjy.selleradapter.kjt.response.RsyncResponseGetKjtProductIdByDate;
import com.hjy.support.MailSupport;

/**
 * 获取时间段内信息变化的商品ID并查询详细信息入库 | properties配置信息核对完成
 * 
 * @author ligj
 * 
 */
public class RsyncGetKjtProductIdByDate extends RsyncKjt<RsyncConfigGetKjtProductIdByDate, RsyncRequestGetKjtProductIdByDate, RsyncResponseGetKjtProductIdByDate> {
	
	private static Logger logger=Logger.getLogger(RsyncGetKjtProductIdByDate.class);
	
	@Inject
	private IPcProductinfoDao productinfoDao;
	
	
	private List<String> pcodeList = null;
	private String startDate = null;
	private String endDate = null;
	
	

	public RsyncGetKjtProductIdByDate() {
	}

	public RsyncGetKjtProductIdByDate(List<String> pcodeList, String startDate, String endDate) {
		this.pcodeList = pcodeList;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	final static RsyncConfigGetKjtProductIdByDate CONFIG_GET_TV_BY_DATE = new RsyncConfigGetKjtProductIdByDate();
	final static int qnum = 5;// 每次查询的数量
	final static String downStatus = "4497153900060003";// 商品状态为下架

	public RsyncConfigGetKjtProductIdByDate upConfig() {
		return CONFIG_GET_TV_BY_DATE;
	}

	public RsyncRequestGetKjtProductIdByDate upRsyncRequest() {
		// 返回输入参数
		RsyncRequestGetKjtProductIdByDate request = new RsyncRequestGetKjtProductIdByDate();
		RsyncDateCheck rsyncDateCheck = upDateCheck(upConfig());
		
		if(StringUtils.isNotBlank(this.startDate) && StringUtils.isNotBlank(this.endDate)){             // 兼容线上运营人员的临时需求 - Yangcl
			request.setChangedDateBegin(this.startDate);
			request.setChangedDateEnd(this.endDate);
		}else{
			request.setChangedDateBegin(rsyncDateCheck.getStartDate());
			request.setChangedDateEnd(rsyncDateCheck.getEndDate());
		}
		
		
		request.setSaleChannelSysNo(getConfig("seller_adapter_kjt.rsync_kjt_SaleChannelSysNo"));
		request.setLimitRows(getConfig("seller_adapter_kjt.rsync_kjt_limit_rows"));// 默认足够大的条数1百万
		return request;
	}

	public RsyncResult doProcess(RsyncRequestGetKjtProductIdByDate tRequest,
			RsyncResponseGetKjtProductIdByDate tResponse) {

		RsyncResult result = new RsyncResult();

		// 定义成功的数量合计
		int iSuccessSum = 0;
		if (result.upFlagTrue()) {
			if (tResponse != null && tResponse.getData() != null) {
				result.setProcessNum(tResponse.getData().getProductList().size());
			} else {
				result.setProcessNum(0);

			}
		}
		// 开始循环处理结果数据
		if (result.upFlagTrue()) {
			// 判断有需要处理的数据才开始处理
			if (result.getProcessNum() > 0) {
				List<String> dowProductCodeOldList = new ArrayList<String>();// 用于存放需要下架的商品code
				List<String> queryProductCodeOldList = new ArrayList<String>();// 用于查询商品详细信息的商品code
				for (RsyncModelGetKjtChangeProduct rp : tResponse.getData().getProductList()) {
					int status = rp.getStatus();
					String productId = rp.getProductID();
					if (StringUtils.isEmpty(productId))
						continue;
					if (status != 1) {// 0上架不展示 ，-1已作废，1上架，2，下架,3:初始
						dowProductCodeOldList.add(productId);
					} else {
						queryProductCodeOldList.add(productId);
					}
				}
				if (dowProductCodeOldList != null && dowProductCodeOldList.size() > 0) {
					List<PcProductinfo> downProductList = productinfoDao.getListByOldProductCode(dowProductCodeOldList);
					if (downProductList != null && downProductList.size() > 0) {
						List<String> pCode1 = new ArrayList<String>();// 商品在跨境通的编号
						List<String> pCode2 = new ArrayList<String>();// 商品在惠家有的编号
						List<String> pName = new ArrayList<String>();// 商品的名字
						for (PcProductinfo pro : downProductList) {
							pCode1.add(pro.getProductCodeOld());
							pCode2.add(pro.getProductCode());
							pName.add(pro.getProductName());
						}
						this.sendMail(StringUtils.join(pCode1, ","), StringUtils.join(pCode2, ","),
								StringUtils.join(pName, ","));// 发送下架商品的邮件
//						MDataMap upMap = new MDataMap();
//						upMap.put("product_status", RsyncGetKjtProductIdByDate.downStatus);// 下架
//						upMap.put("flag_sale", "0");// 是否可售

						if (pCode1 != null && pCode1.size() > 0) {
							productinfoDao.updateByOldProductCode(pCode1);// 商品下架
						}
					}
				}
				
				// 兼容线上运营人员的临时需求 - Yangcl
				if(pcodeList != null && pcodeList.size() != 0){
					queryProductCodeOldList = pcodeList;
				}
				
				// 设置预期处理数量
				int productSize = queryProductCodeOldList.size();
				logger.info("@@@@@@@@@@@@@@@@@@@@@@ productSize = " + productSize);
				
				result.setProcessNum(productSize);
				int queryCount = 0;// 由于查询商品详细信息，每次最多传5个商品id,这里记录需要查询的次数
				if (productSize % qnum == 0) {
					queryCount = productSize / qnum;
				} else {
					queryCount = productSize / qnum + 1;
				}
				for (int i = 0; i < queryCount; i++) {
					List<String> changeProductCodeList = new ArrayList<String>();// 存放用于查询商品详细信息的商品id
					for (int j = i * 5; j < (i + 1) * 5; j++) {// 每次最多根据5个商品id去查询商品信息
						if (j < productSize) {
							changeProductCodeList.add(queryProductCodeOldList.get(j));
						}
					}
					
					logger.info("########## 第：" + i + "次 changeProductCodeList = " + changeProductCodeList.toString()); 

					if (changeProductCodeList != null && changeProductCodeList.size() > 0) {
						MWebResult mResult = saveProductData((String[]) changeProductCodeList.toArray(new String[changeProductCodeList.size()]));
						// 如果成功则将成功计数加1
						if (mResult.upFlagTrue()) {
							iSuccessSum += 5;

						} else {
							if (result.getResultList() == null) {
								result.setResultList(new ArrayList<Object>());
							}
							result.getResultList().add(mResult.getMessage());
						}
					}
					try {
						Thread.sleep(1000);// 防止访问过快，睡眠一秒
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				result.setProcessData(
						getInfo(100001102, result.getProcessNum(), iSuccessSum, result.getProcessNum() - iSuccessSum));
			}
		}
		// 如果操作都成功 则设置状态保存数据为同步结束时间 以方便下一轮调用
		if (result.upFlagTrue()) {
			// 设置处理成功数量
			result.setSuccessNum(iSuccessSum);
			// 特殊处理 由于时间格式不对 状态数据需要切换掉
			RsyncDateCheck rsyncDateCheck = upDateCheck(upConfig());
			result.setStatusData(rsyncDateCheck.getEndDate());
		}

		return result;

	}

	/**
	 * @Description:查询商品信息，并入库 @param productIds @author 张海生 @date 2015-7-22
	 * 上午9:55:39 @return MWebResult @throws
	 */
	private MWebResult saveProductData(String[] productIds) {
		MWebResult result = new MWebResult();
		try {
			RsyncGetKjtProductById aa = new RsyncGetKjtProductById();
			aa.upRsyncRequest().setProductIDs(productIds);
			aa.upRsyncRequest().setSaleChannelSysNo(getConfig("seller_adapter_kjt.rsync_kjt_SaleChannelSysNo"));
			aa.doRsync();
		} catch (Exception e) {
			result.inErrorMessage(918519034, StringUtils.join(productIds, ","));
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Description:跨境通商品下架通知邮件 @param pCodes1 跨境通商品code @param pCodes2
	 * 惠家有商品code @param pName 商品名称 @author 张海生 @date 2015-8-21 下午2:09:49 @return
	 * void @throws
	 */
	private void sendMail(String pCodes1, String pCodes2, String pName) {

		String receives[] = getConfig("seller_adapter_kjt.kjt_dowProduct_sendMail_receives").split(",");
		String title = getConfig("seller_adapter_kjt.kjt_dowProduct_title");
		String content = getConfig("seller_adapter_kjt.kjt_dowProduct_content");

		for (String receive : receives) {
			if (StringUtils.isNotBlank(receive)) {
				MailSupport.INSTANCE.sendMail(receive, title,
						FormatHelper.formatString(content, pCodes1, pCodes2, pName));
			}
		}
	}

	public RsyncResponseGetKjtProductIdByDate upResponseObject() {

		return new RsyncResponseGetKjtProductIdByDate();
	}
}

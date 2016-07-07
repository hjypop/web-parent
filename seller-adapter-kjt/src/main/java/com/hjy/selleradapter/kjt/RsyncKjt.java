package com.hjy.selleradapter.kjt;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.ParseException;

import com.hjy.annotation.Inject;
import com.hjy.base.BaseClass;
import com.hjy.common.DateUtil;
import com.hjy.common.bill.HexUtil;
import com.hjy.common.bill.MD5Util;
import com.hjy.entity.LcRsyncKjtLog;
import com.hjy.helper.DateHelper;
import com.hjy.helper.FormatHelper;
import com.hjy.helper.JsonHelper;
import com.hjy.helper.WebHelper;
import com.hjy.iface.IRsyncConfig;
import com.hjy.iface.IRsyncDateCheck;
import com.hjy.iface.IRsyncDo;
import com.hjy.iface.IRsyncRequest;
import com.hjy.iface.IRsyncResponse;
import com.hjy.model.MDataMap;
import com.hjy.model.RsyncDateCheck;
import com.hjy.model.RsyncResult;
import com.hjy.service.ILcRsyncKjtLogService;
import com.hjy.support.WebClientSupport;

/**
 * 同步跨境通接口的基类 | properties配置信息核对完成
 * 
 * @author srnpr
 * 
 * @param <TConfig>
 * @param <TRequest>
 * @param <TResponse>
 */
public abstract class RsyncKjt<TConfig extends IRsyncConfig, TRequest extends IRsyncRequest, TResponse extends IRsyncResponse>
		extends BaseClass implements IRsyncDo<TConfig, TRequest, TResponse> {

	@Inject
	private ILcRsyncKjtLogService lcRsyncKjtLogService;
	private TResponse processResult = null;

	/**
	 * 调用处理逻辑 返回操作
	 * 
	 * @param sRequestString
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws CertificateException
	 */
	private String getHttps(String sUrl, String sRequestString) throws Exception {
		MDataMap mrequest = getsignMap(sRequestString);
		String sResponseString = WebClientSupport.upPost(sUrl, mrequest);
		return sResponseString;
	}

	private MDataMap getsignMap(String requestStr) {
		MDataMap dataMap = new MDataMap();
		dataMap.put("method", upConfig().getRsyncTarget());
		dataMap.put("format", "json");
		dataMap.put("version", getConfig("seller_adapter_kjt.rsync_kjt_version"));
		dataMap.put("appid", getConfig("seller_adapter_kjt.rsync_kjt_appid"));
		dataMap.put("timestamp", DateUtil.getSysDateTimeString(DateUtil.sdfDateTimeTamp));
		dataMap.put("nonce", String.valueOf(new Random().nextInt(10000000)));
		dataMap.put("data", requestStr);
		List<String> list = new ArrayList<String>();
		for (Iterator<String> iter = dataMap.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String value = dataMap.get(name);

			if (name.equals("data")) {
				value = URLEncoder.encode(requestStr);
			}

			list.add(name + "=" + value);
		}
		Collections.sort(list); // 对List内容进行排序
		StringBuffer str = new StringBuffer();
		for (String nameString : list) {
			str.append(nameString + "&");
		}
		dataMap.put("sign", HexUtil.toHexString(MD5Util.md5(str.substring(0, str.toString().length() - 1) + "&"
				+ getConfig("seller_adapter_kjt.rsync_kjt_password"))));
		return dataMap;
	}

	/**
	 * 获取请求的url
	 * 
	 * @return
	 */
	private String upRequestUrl() {
		return getConfig("seller_adapter_kjt.rsync_kjt_url");
	}

	/**
	 * 
	 * 方法: upLastSuccessStatusData <br>
	 * 描述: 获取最近一次成功处理的状态数据 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月28日 下午3:53:22
	 * 
	 * @return
	 */
	public String upLastSuccessStatusData() {
		return lcRsyncKjtLogService.findLatelyStatusData(upConfig().getRsyncTarget());
	}

	public boolean doRsync() {
		String sCode = WebHelper.getInstance().genUniqueCode("KCRL");
		try {
			String sUrl = upRequestUrl();
			TRequest tRequest = upRsyncRequest();
			JsonHelper<IRsyncRequest> requestJsonHelper = new JsonHelper<IRsyncRequest>();
			String sRequest = requestJsonHelper.ObjToString(tRequest);

			// 插入日志记录表
			LcRsyncKjtLog log = new LcRsyncKjtLog();
			log.setUid(UUID.randomUUID().toString().replace("-", ""));
			log.setCode(sCode);
			log.setRsyncTarget(upConfig().getRsyncTarget());
			log.setRsyncUrl(sUrl);
			log.setRequestData(sRequest);
			log.setRequestTime(FormatHelper.upDateTime());
			// 执行插入操作
			lcRsyncKjtLogService.insertSelective(log);
			// 获取响应数据
			String sResponseString = getHttps(sUrl, sRequest);
			// 根据日志的响应时间和相应数据
			log.setResponseTime(FormatHelper.upDateTime());
			log.setResponseData(sResponseString);
			// 更新响应内容和响应时间
			lcRsyncKjtLogService.updateSelective(log);

			TResponse tResponse = upResponseObject();

			JsonHelper<TResponse> responseJsonHelper = new JsonHelper<TResponse>();

			tResponse = responseJsonHelper.GsonFromJson(sResponseString, tResponse);

			processResult = tResponse;
			RsyncResult rsyncResult = doProcess(tRequest, tResponse);

			// 更新处理完成时间
			log.setProcessTime(FormatHelper.upDateTime());
			log.setProcessData(rsyncResult.upJson());
			log.setStatusData(rsyncResult.getStatusData());
			log.setFlagSuccess(rsyncResult.upFlagTrue() ? 1 : 0);
			log.setProcessNum(rsyncResult.getProcessNum());
			log.setSuccessNum(rsyncResult.getSuccessNum());
			lcRsyncKjtLogService.updateSelective(log);
			if (rsyncResult.getCode() == 1) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			// 如果失败更新错误日志信息
			LcRsyncKjtLog log = new LcRsyncKjtLog();
			log.setCode(sCode);
			log.setFlagSuccess(0);
			log.setProcessTime(FormatHelper.upDateTime());
			log.setErrorExpection(e.getMessage());
			lcRsyncKjtLogService.updateSelective(log);
		}
		return false;
	}

	/**
	 * 获取日期的检查计算结果 该方法仅适用于传入的是时间范围的跨境通接口函数 会自动调整输入输出参数
	 * 
	 * @param iRsyncDateCheck
	 * @return
	 */
	public RsyncDateCheck upDateCheck(IRsyncDateCheck iRsyncDateCheck) {
		RsyncDateCheck rsyncDateCheck = new RsyncDateCheck();

		String sStatusDate = upLastSuccessStatusData();
		// 判断如果没有最近一次成功的开始时间 则使用最最原始的开始时间
		if (StringUtils.isEmpty(sStatusDate)) {
			sStatusDate = iRsyncDateCheck.getBaseStartTime();
		}

		Date dStateDate = DateHelper.parseDate(sStatusDate);

		// 将开始时间减去回退时间 以兼容异常情况
		Date dStart = DateUtils.addSeconds(dStateDate, -iRsyncDateCheck.getBackSecond());
		rsyncDateCheck.setStartDate(DateHelper.formatDate(dStart));

		Date dEnd = DateUtils.addSeconds(dStateDate, iRsyncDateCheck.getMaxStepSecond());

		Date dNowDate = new Date();
		// 判断如果结束时间晚于当前时间 则将结束时间设置为当前时间
		if (dEnd.after(dNowDate)) {
			dEnd = dNowDate;
		}
		rsyncDateCheck.setEndDate(DateHelper.formatDate(dEnd));
		return rsyncDateCheck;
	}

	/**
	 * 获取调用接口之后的结果
	 * 
	 * @return
	 */
	public TResponse upProcessResult() {
		return processResult;
	}

}

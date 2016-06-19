package com.hjy.support;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.hjy.base.BaseClass;
import com.hjy.global.InitDir;
import com.hjy.global.TopConst;
import com.hjy.helper.FormatHelper;
import com.hjy.helper.GsonHelper;
import com.hjy.model.MDataMap;

public class LogSupport extends BaseClass {

	private static LogSupport instance = null;

	private static String serverCode = "";

	public static LogSupport getInstance() {
		if (instance == null) {
			synchronized (LogSupport.class) {
				if (null == instance) {
					instance = new LogSupport();
					serverCode = NetHelper.getLocalIP()
							+ ":"
							+ StringUtils.substringAfterLast(
									new InitDir().upServerletPath(""), "/");
				}
			}
		}
		return instance;
	}

	private LogCache logCache = new LogCache();

	public LogSupport() {}

	/**
	 * 添加日志
	 * 
	 * @param sType
	 * @param sContent
	 * @return
	 */
	public boolean addLog(String sType, String sContent) {
		boolean bReturn = true;
		if (StringUtils.isNotBlank(TopConst.CONST_LOG_TYPE)) {
			String sUuid = UUID.randomUUID().toString().replace("-", "");
			LogInfo logInfo = new LogInfo();
			logInfo.setUid(sUuid);
			logInfo.setType(sType);
			logInfo.setCreate(FormatHelper.upDateTime());
			logInfo.setInfo(sContent);
			logInfo.setServer(serverCode);
			logCache.inElement(sUuid, logInfo);
		} else {
			bLogDebug(967912030);
			bReturn = false;
		}
		return bReturn;
	}

	/**
	 * 获取并删除定指定数量量的日志内容
	 * 
	 * @return
	 */
	public List<LogInfo> upLogListAndRemove(int iNumber) {
		List<LogInfo> logList = new ArrayList<LogInfo>();

		List<String> listKeys = logCache.upKeys();

		for (int i = 0; i < Math.min(iNumber, listKeys.size()); i++) {
			logList.add(logCache.upValueAndRemove(listKeys.get(i)));
		}
		return logList;
	}

	public boolean sendLogToServer() {
		SendLog sendLog = new SendLog();
		sendLog.setModel(TopConst.CONST_CURRENT_MODEL);
		// 防止出现过大body包 每次最大1000条
		sendLog.setLogs(upLogListAndRemove(1000));
		// TopConst.CONST_LOG_ADDRESS="http://localhost:3000/log/baselog";

		if (sendLog.getLogs() != null && sendLog.getLogs().size() > 0) {
			try {
				WebClientSupport.upPost(TopConst.CONST_LOG_ADDRESS,
						new MDataMap("api_input", GsonHelper.toJson(sendLog)));
			} catch (Exception e) {
				bLogError(967912031, e.getMessage());
				e.printStackTrace();
			}
		}
		return true;
	}
}

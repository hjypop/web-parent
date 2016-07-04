package com.hjy.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hjy.entity.zapdata.ZaWeblog;
import com.hjy.helper.FormatHelper;
import com.hjy.iface.IBaseInstance;
import com.hjy.service.zapdata.IZaWeblogService;

/**
 * 日志
 * 
 * @author srnpr
 *
 */
@Component
public class WebLogFactory implements IBaseInstance {

	public final static WebLogFactory INSTANCE = new WebLogFactory();

	@Autowired
	private IZaWeblogService zaWeblogService;

	/**
	 * 添加日志内容
	 * 
	 * @param sType
	 * @param sTitle
	 * @param sContent
	 */
	public void addLog(String sType, String sTitle, String sContent) {
		ZaWeblog log = new ZaWeblog();
		log.setLogType(sType);
		log.setLogTitle(sTitle);
		log.setLogContent(sContent);
		log.setCreateTime(FormatHelper.upDateTime());
		zaWeblogService.insertSelective(log);
	}

}

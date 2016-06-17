package com.hjy.cache.impl;

import com.hjy.cache.RootCache;
import com.hjy.helper.IoHelper;
import com.hjy.iface.IBaseCache;

/**
 * 初始化加载配置
 * @author HJY 
 */
public class TopConfig extends RootCache<String, String> implements IBaseCache {

	public final static TopConfig Instance = new TopConfig();

	public synchronized void refresh() {

		TopDir topDir = new TopDir();
		String sTempConfigString = topDir
				.upTempDir(TopConst.CONST_TOP_CUSTOM_CONFIG_PATH);
		// topDir.upZapDir();
		bLogInfo(0, "refresh " + sTempConfigString);
		IoHelper ioHelper = new IoHelper();
		ioHelper.copyResources(
				"classpath*:META-INF/hjy/config/*.properties",
				sTempConfigString, "/hjy/config/");
		LoadProperties loadProperties = new LoadProperties();

		// 开始读取配置
		{
			MStringMap mStringMap = loadProperties.loadMap(sTempConfigString);

			for (String s : mStringMap.upKeys()) {
				this.inElement(s, mStringMap.get(s));
			}
		}

		// 开始扫描扩展自定义的设置
		{
			String sCustom = topDir
					.upCustomPath(TopConst.CONST_TOP_CUSTOM_CONFIG_PATH);
			bLogInfo(0, "scan custom config " + sCustom + "");

			MStringMap mCustomMap = loadProperties.loadMap(sCustom);

			if (mCustomMap.size() == 0) {
				bLogWarn(0, "scan custom config  not exist");
			} else {

				for (String s : mCustomMap.upKeys()) {
					this.inElement(s, mCustomMap.get(s));
				}
			}
		}

		// 开始加载最后本地配置项
		{
			String sLocal = topDir.upLocalConfigPath();
			bLogInfo(0, "scan local config " + sLocal + "");
			MStringMap mCustomMap = loadProperties.loadMap(sLocal);
			for (String s : mCustomMap.upKeys()) {
				this.inElement(s, mCustomMap.get(s));
			}
		}

		// ConfigObservable.INSTANCE.doUpdate(this);

	}

	@Override
	public String upOne(String k) {

		return null;
	}

}

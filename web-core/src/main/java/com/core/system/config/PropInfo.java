package com.core.system.config;

import com.core.cache.RootCache;
import com.core.helper.IoHelper;
import com.core.model.MStringMap;
import com.core.system.SysWorkDir;

/**
 * alias TopInfo
 * 获取顶级配置消息
 * 
 * @author HJY
 * 
 */
public class PropInfo extends RootCache<Long, String> {

	public synchronized void refresh() {
		SysWorkDir GlobalDir = new SysWorkDir();
		String sTempConfigString = GlobalDir.getTempDir("info/");
		getLogger().logDebug(0, "refresh " + sTempConfigString);
		IoHelper ioHelper = new IoHelper();
		ioHelper.copyResources(
				"classpath*:META-INF/hjy/info/*.properties",
				sTempConfigString,"/hjy/info/");
		LoadProperties loadProperties = new LoadProperties();
		MStringMap mStringMap = loadProperties.loadMap(sTempConfigString);
		for (String s : mStringMap.getKeys()) {

			Long lKey=Long.parseLong(s);
			
			if(containsKey(lKey))
			{
				getLogger().logError(0, "key ["+lKey.toString()+"] exist more then one");
			}
			
			this.inElement(Long.parseLong(s), mStringMap.get(s));
		}

	}

	@Override
	public String getOne(Long k) {
		return null;
	}
}

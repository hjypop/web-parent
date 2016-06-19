package com.hjy.global;

import com.hjy.helper.IoHelper;
import com.hjy.model.MStringMap;
import com.hjy.tools.LoadProperties;
import com.hjy.cache.RootCache;

/**
 * alias TopInfo
 * 获取顶级配置消息
 * 
 * @author HJY
 * 
 */
class InitInfo extends RootCache<Long, String> {

	/* (non-Javadoc)
	 * @see com.srnpr.zapcom.baseface.IBaseCache#refresh()
	 */
	public synchronized void refresh() {
		InitDir GlobalDir = new InitDir();
		String sTempConfigString = GlobalDir.upTempDir("info/");
		bLogDebug(0, "refresh " + sTempConfigString);
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
				bLogError(0, "key ["+lKey.toString()+"] exist more then one");
			}
			
			this.inElement(Long.parseLong(s), mStringMap.get(s));
		}

	}

	@Override
	public String getOne(Long k) {
		return null;
	}
}

package com.hjy.system.config;

import org.apache.commons.lang.StringUtils;

import com.hjy.cache.RootCache;
import com.hjy.model.MStringMap;

public class ConfigMap extends RootCache<String, MStringMap> {

	public synchronized void refresh() {

		PropConfig tConfig = PropConfig.Instance;

		if (tConfig.getKeys().size() == 0) {
			tConfig.refresh();
		}

		for (String s : tConfig.getKeys()) {
			if (StringUtils.indexOf(s, "[") > -1) {
				String sTopKey = StringUtils.substringBefore(s, "[");

				if (!this.containsKey(sTopKey)) {
					this.inElement(sTopKey, new MStringMap());
				}

				String sSubKeyString = StringUtils
						.substringBetween(s, "[", "]");

				this.getValue(sTopKey).put(sSubKeyString, tConfig.getValue(s));

			}
		}

	}

	@Override
	public MStringMap getOne(String k) {

		return null;
	}

}

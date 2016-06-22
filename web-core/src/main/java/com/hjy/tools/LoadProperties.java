package com.hjy.tools;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.hjy.base.BaseClass;
import com.hjy.model.MStringMap;
import com.hjy.system.config.TopConst;

/**
 * 读取classpath*:META-INF/hjy/config/*.properties 配置文件
 * 
 * @author HJY
 *
 */
public class LoadProperties extends BaseClass {

	/**
	 * 加载属性配置
	 * 
	 * @param sDir
	 * @return
	 */
	public MStringMap loadMap(String sDir) {

		@SuppressWarnings({ "unchecked" })
		Collection<File> files = FileUtils.listFiles((new File(sDir)),
				new String[] { "properties" }, true);

		return loadMapFromFiles(files);

	}

	/**
	 * 从文件中读取配置文件
	 * 
	 * @param files
	 * @return
	 */
	public MStringMap loadMapFromFiles(Collection<File> files) {
		MStringMap mReturnMap = new MStringMap();

		try {
			for (File f : files) {
				PropertiesConfiguration pConfiguration = new PropertiesConfiguration();

				FileInputStream fInputStream = FileUtils.openInputStream(f);

				pConfiguration.load(fInputStream, TopConst.CONST_BASE_ENCODING);

				Iterator<String> em = pConfiguration.getKeys();

				// 定义命名空间
				String sNameSpace = StringUtils.defaultString(
						pConfiguration.getString("@this$namespace"), "");

				while (em.hasNext()) {
					String sKeyString = em.next();
					// String sValueString = new
					// String(pConfiguration.getString(sKeyString).toString());

					// 定义是否强制覆盖
					boolean bOverWrite = false;

					String sValueString = StringUtils.join(
							pConfiguration.getStringArray(sKeyString), ",");

					if (StringUtils.isNotEmpty(sNameSpace)) {
						if (!StringUtils.startsWith(sKeyString, "@")
								&& !StringUtils.startsWith(sKeyString,
										sNameSpace)) {
							sKeyString = sNameSpace + "." + sKeyString;
						}
					}

					// 进行特殊判断模式
					if (StringUtils.startsWith(sKeyString, "@")) {
						String sTarget = StringUtils.substringBetween(
								sKeyString, "@", "$");

						// 覆写配置
						if (sTarget.equals("override")) {
							// override
							bOverWrite = true;
						}
						// 本配置指向
						else if (sTarget.equals("this")) {

						}

						sKeyString = StringUtils
								.substringAfter(sKeyString, "$");

					}

					// String
					// sValueString=pJarConfiguration.getString(sKeyString);

					if (bOverWrite || !mReturnMap.containsKey(sKeyString)) {
						mReturnMap.put(sKeyString, sValueString);
					}

				}

				fInputStream.close();
			}
		} catch (Exception e) {

			e.printStackTrace();

		}

		return mReturnMap;
	}

}

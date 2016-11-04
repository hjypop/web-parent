package com.core.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class IoHelper {

	/**
	 * @param sDir
	 *            目录地址 创建目录
	 */
	public static void createDir(String sDir) {
		try {
			FileUtils.forceMkdir(new File(sDir));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param sResourceName
	 *            根据名称获取资源 名称支持正则表达式
	 * @return
	 * @throws IOException
	 */
	public Resource[] upResources(String sResourceName) throws IOException {

		Resource[] resources = null;

		PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();

		resources = patternResolver.getResources(sResourceName);

		return resources;
	}

	/**
	 * 复制资源
	 * 
	 * @param sFromClass
	 * @param sToPath
	 * @param sKeyName
	 */
	public void copyResources(String sFromClass, String sToPath, String sKeyName) {
		try {
			Resource[] resources = upResources(sFromClass);

			for (Resource r : resources) {

				String sUrlString = StringUtils.substringAfter(r.getURI().toString(), sKeyName);

				InputStream inStream = r.getInputStream(); // 读入原文件

				new File(sToPath + sUrlString).getParentFile().mkdirs();
				FileOutputStream fs = new FileOutputStream(sToPath + sUrlString);

				IOUtils.copy(inStream, fs);
				fs.flush();
				fs.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

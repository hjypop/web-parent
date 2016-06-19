package com.hjy.global;

import org.apache.commons.lang.StringUtils;

import com.hjy.base.BaseClass;
import com.hjy.helper.IoHelper;

/**
 * alias TopDir
 * 主路径
 * 
 * @author srnpr 路径获取类
 */
public class InitDir extends BaseClass {

	/**
	 * 获取临时文件夹路径
	 * 
	 * @param sTempDir
	 *            临时目录的子文件夹
	 * @return
	 */
	public String upTempDir(String sTempDir) {

		if (StringUtils.isEmpty(TopConst.CONST_TOP_DIR_TEMP)) {
			TopConst.CONST_TOP_DIR_TEMP = upServerletPath("hjyzoos/hjydir/temp/");
			bLogDebug(0, "init GlobalConst.CONST_TOP_DIR_TEMP="
					+ TopConst.CONST_TOP_DIR_TEMP);
		}
		String sReturnString = TopConst.CONST_TOP_DIR_TEMP + sTempDir;
		IoHelper.createDir(sReturnString);
		return sReturnString;
	}

	/**
	 * 获取程序路径
	 * 
	 * @param sSubDir
	 * @return
	 */
	public String upServerletPath(String sSubDir) {

		String sReturnString = "";

		if (StringUtils.isBlank(TopConst.CONST_TOP_DIR_SERVLET)) {
			TopConst.CONST_TOP_DIR_SERVLET = System.getProperty("user.home");
		}

		if (StringUtils.isNotBlank(sSubDir)) {
			sSubDir = "/" + sSubDir;
		}

		sReturnString = TopConst.CONST_TOP_DIR_SERVLET + sSubDir;
		IoHelper.createDir(sReturnString);
		return sReturnString;

	}

	/**
	 * 获取加载扩展配置目录
	 * 
	 * @param sPath
	 *            目录名称 如果传入的参数以/结尾则自动创建文件夹
	 * @return
	 */
	public String upCustomPath(String sPath) {
		String sReturn = "";

		if (StringUtils.isBlank(TopConst.CONST_TOP_DIR_CUSTOM)) {

			String sServerPath = upServerletPath("");

			String sStart = "/etc/hjy/";

			// 判断如果是windows系统 则默认取系统所在路径的根目录
			if (StringUtils.substring(sServerPath, 1, 2).equals(":")) {
				sStart = sServerPath.substring(0, 2) + sStart;
			}

			sServerPath = sServerPath.toLowerCase().replace(":", "_")
					.replace("/", "_").replace("\\", "_");

			sStart = sStart + sServerPath;

			TopConst.CONST_TOP_DIR_CUSTOM = sStart + "/";

		}

		sReturn = TopConst.CONST_TOP_DIR_CUSTOM + sPath;

		if (sReturn.endsWith("/")) {
			IoHelper.createDir(sReturn);
		}

		return sReturn;

	}

	/**
	 * 获取本地配置目录 该目录为最后加载的配置 会覆盖所有已加载配置
	 * 
	 * @return
	 */
	public String upLocalConfigPath() {
		String sReturn = "";

		String sServerPath = upServerletPath("");

		String sStart = "/etc/hjy/local/";

		// 判断如果是windows系统 则默认取系统所在路径的根目录
		if (StringUtils.substring(sServerPath, 1, 2).equals(":")) {
			sStart = sServerPath.substring(0, 2) + sStart;
		}
		
		sReturn=sStart;
		IoHelper.createDir(sReturn);
		return sReturn;

	}

}

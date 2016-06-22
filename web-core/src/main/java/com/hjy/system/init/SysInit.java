package com.hjy.system.init;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

import com.hjy.model.MStringMap;
import com.hjy.system.config.PropVisitor;
import com.hjy.system.SysWorkDir;
import com.hjy.system.config.PropConfig;
import com.hjy.system.config.PropInfo;

/**
 * alias TopInit
 * 1.删除系统临时目录中的文件
 * 2.将系统所有垂直子工程的config.xxx.properties和info.xxxx.properties文件从jar包中拷入系统临时目录并加载
 * 3.依次调用config.xxx.properties中initclass类,完成垂直子工程的初始工作
 * 
 * @author HJY
 */
public class SysInit extends RootInit {

	public synchronized boolean onInit() {

		initDelete();
		initProps();
		return initClass();

	}

	/**
	 * 初始化删除操作
	 */
	private void initDelete() {
		String sZapDirString = new SysWorkDir().getTempDir("");
		try {
			FileUtils.deleteDirectory(new File(sZapDirString));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化顶级配置
	 */
	private void initProps() {
		topInitCache(PropConfig.Instance, new PropInfo());
	}

	/**
	 * 初始化加载各个类
	 * 
	 * @return
	 */
	private boolean initClass() {

		boolean bFlagInit = true;

		String sConfigName = "webcore.initclass";

		MStringMap mStringMap = PropVisitor.getConfigMap(sConfigName);

		for (String sClassName : mStringMap.values()) {

			if (!StringUtils.isEmpty(sClassName)) {
				try {

					Class<?> cClass = ClassUtils.getClass(sClassName);
					if (cClass != null && cClass.getDeclaredMethods() != null) {
						RootInit init = (RootInit) cClass.newInstance();
						if (!init.init()) {
							bFlagInit = false;
						}
					}
				} catch (Exception e) {

					bFlagInit = false;
					getLogger().logInfo(967905001, sClassName);
					e.printStackTrace();

				}
			}

		}

		return bFlagInit;
	}

	@Override
	public boolean onDestory() {
		boolean bFlagInit = true;

		String sConfigName = "webcore.initclass";

		MStringMap mStringMap = PropVisitor.getConfigMap(sConfigName);

		for (String sClassName : mStringMap.values()) {

			if (!StringUtils.isEmpty(sClassName)) {
				try {

					Class<?> cClass = ClassUtils.getClass(sClassName);
					if (cClass != null && cClass.getDeclaredMethods() != null) {
						RootInit init = (RootInit) cClass.newInstance();
						if (!init.destory()) {
							bFlagInit = false;
						}
					}
				} catch (Exception e) {

					bFlagInit = false;
					getLogger().logInfo(967905001, sClassName);
					e.printStackTrace();

				}
			}

		}

		return bFlagInit;
	}

}

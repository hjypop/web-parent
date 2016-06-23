package com.hjy.base;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.hjy.annotation.Inject;
import com.hjy.helper.FormatHelper;
import com.hjy.system.SpringCtxUtil;
import com.hjy.system.config.PropVisitor;

/**
 * 基类
 * 
 * @author HJY
 */
public abstract class BaseClass {
	public static BaseLog logger = new BaseLog();

	public BaseClass() {
		inject();
	}

	public void inject() {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Inject.class)) {
				Inject inject = field.getAnnotation(Inject.class);
				String className = inject.className();
				try {
					if (StringUtils.isNotBlank(className)) {
						this.getBean(className);
					} else {
						Object obj = this.getBean(field.getType());
						field.setAccessible(true);
						field.set(this, obj);
					}
				} catch (NoSuchBeanDefinitionException e) {
					e.printStackTrace();
					getLogger().logError(e.getMessage());
				} catch(IllegalAccessException e) {
					e.printStackTrace();
					getLogger().logError(e.getMessage());
				}
			}
		}
	}

	public BaseLog getLogger() {
		return logger;
	}

	/**
	 * alias bConfig
	 * 
	 * @param sKey
	 *            配置主键
	 * @return 配置内容字符串
	 */
	public String getConfig(String sKey) {
		return PropVisitor.getConfig(sKey);
	}

	/**
	 * alias bInfo
	 * 
	 * @param lInfoId
	 *            文本编号
	 * @param sParms
	 *            拼接字符串
	 * @return
	 */
	public String getInfo(long iInfoCode, Object... sParms) {
		return FormatHelper.formatString(PropVisitor.getInfo(iInfoCode), sParms);
	}

	public <T> T getBean(Class<T> clazz) throws BeansException {
		return (T) SpringCtxUtil.getBean(clazz);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanName) throws BeansException {
		return (T) SpringCtxUtil.getBean(beanName);
	}
}

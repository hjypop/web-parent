package com.core.base;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.core.annotation.Inject;
import com.core.helper.FormatHelper;
import com.core.system.SpringCtxUtil;
import com.core.system.config.PropVisitor;

/**
 * 基类
 * 
 * @author zht
 */
public abstract class BaseClass {
	public static BaseLog logger = new BaseLog();

	public BaseClass() {
		inject(this.getClass());
	}

	public void inject(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Inject.class)) {
				Inject inject = field.getAnnotation(Inject.class);
				String className = inject.className();
				try {
					if (StringUtils.isNotBlank(className)) {
						Object obj = this.getBean(className);
						field.setAccessible(true);
						field.set(this, obj);
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
		Class<?> parentClazz = clazz.getSuperclass();
		if(parentClazz != null)
			inject(parentClazz);
	}

	public BaseLog getLogger() {
		return logger;
	}

	/**
	 * 错误日志
	 * @param lInfoId 默认请写0 否则读取配置文件
	 * @param sParms 替换参数
	 */
	public void bLogError(int iInfoCode, Object... sParms) {
		LogFactory.getLog(this.getClass()).error( "[" + String.valueOf(iInfoCode) + "] " + PropVisitor.getLogInfo(iInfoCode, sParms));
	}
	
	/**
	 * alias bConfig
	 * 
	 * @param sKey  配置主键
	 * @return 配置内容字符串
	 */
	public String getConfig(String sKey) {
		return PropVisitor.getConfig(sKey);
	}

	/**
	 * alias bInfo
	 * 
	 * @param lInfoId 文本编号
	 * @param sParms  拼接字符串
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





















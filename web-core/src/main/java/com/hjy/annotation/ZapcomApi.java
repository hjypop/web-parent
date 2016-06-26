package com.hjy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * @descriptions API的注解信息
 * 输入输出参数添加此注解 其注解信息将会反射到API说明文档中
 * 所添加的注解信息相当于数据库表中的备注内容
 * 
 * @author srnpr
 * @date 2016年6月26日-下午3:03:44
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZapcomApi  {
	/**
	 * 名称 默认等同于title
	 * 
	 * @return
	 */
	public String[] value() default {};

	/**
	 * 备注信息
	 * 
	 * @return
	 */
	public String[] remark() default {};

	/**
	 * 是否必须参数 默认为1必填 0为非必填 该参数仅用于输入参数的字段标记
	 * 
	 * @return
	 */
	public int require() default 0;

	/**
	 * 参数示例
	 * 
	 * @return
	 */
	public String[] demo() default {};

	/**
	 * 验证规则表达式 支持标准处理语法
	 * 
	 * @return
	 */
	public String[] verify() default {};

	/*
	 * 扩展规则设置
	 * 
	 * html:EncodeHelper.htmlEncode
	 * 
	 * url:EncodeHelper.urlEncode
	 * 
	 * public String[] extend() default {};
	 */
}

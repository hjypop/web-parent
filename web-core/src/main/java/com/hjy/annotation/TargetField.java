package com.hjy.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** 
 * @description: 要标识的目标字段名称
 * 
 * @author Yangcl
 * @date 2016年12月30日 下午4:39:54 
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetField {
	public String value() default "";
	
	public String fieldType() default "";
}



















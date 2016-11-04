package com.se.model.lesson;

import com.se.model.BaseModel;

/**
 * 
 * 类: TClasses <br>
 * 描述: 班级 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月2日 下午5:28:22
 */
public class TClasses extends BaseModel {

	private String schoolName;
	private String gradeName;
	private String className;

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
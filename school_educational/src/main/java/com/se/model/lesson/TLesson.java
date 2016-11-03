package com.se.model.lesson;

import java.util.Date;

import com.se.model.BaseModel;

/**
 * 
 * 类: TLesson <br>
 * 描述: 课程 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月2日 下午5:28:57
 */
public class TLesson extends BaseModel {

	/**
	 * 类型
	 */
	private String typeCode;

	/**
	 * 教师编码
	 */
	private String teacherCode;
	/**
	 * 班级编码
	 */
	private String classesCode;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 时长
	 */
	private Integer length;

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	public String getClassesCode() {
		return classesCode;
	}

	public void setClassesCode(String classesCode) {
		this.classesCode = classesCode;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

}
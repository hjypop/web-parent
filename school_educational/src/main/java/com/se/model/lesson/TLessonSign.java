package com.se.model.lesson;

import com.se.model.BaseModel;

/**
 * 
 * 类: TLessonSign <br>
 * 描述: 课程签到 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月2日 下午5:47:17
 */
public class TLessonSign extends BaseModel {
	/**
	 * 学生编码
	 */
	private String studentCode;

	/**
	 * 课程编码
	 */
	private String lessonCode;

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public String getLessonCode() {
		return lessonCode;
	}

	public void setLessonCode(String lessonCode) {
		this.lessonCode = lessonCode;
	}

}
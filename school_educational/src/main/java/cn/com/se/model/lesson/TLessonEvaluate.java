package cn.com.se.model.lesson;

import cn.com.se.model.BaseModel;

/**
 * 
 * 类: TLessonEvaluate <br>
 * 描述: 课程评价 <br>
 * 作者: zhy<br>
 * 时间: 2016年11月2日 下午5:30:17
 */
public class TLessonEvaluate extends BaseModel {

	/**
	 * 学生编码
	 */
	private String studentCode;

	/**
	 * 课程编码
	 */
	private String lessonCode;

	/**
	 * 评分
	 */
	private Integer score;

	/**
	 * 评价内容
	 */
	private String content;

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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
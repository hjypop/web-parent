package com.hjy.common;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供两种用法 1.利用public static method进行日期格式的转换 2.利用内容的Calendar object
 * 进行日期的计算，适合有大量的日期计算，用method 1则效率低的场合
 * 
 * @priority high
 * @alias Date相关的utility
 * @author ChenJP <cjp@sunjapan.com.cn>
 * @version 1.0
 * @since 1.0
 * @history
 */
public class DateUtil {
	public static final long DAY_MILLI = 24 * 60 * 60 * 1000; // 一天的MilliSecond

	public static final long MINUTE_MILLI = 60 * 1000; // 一分钟的MilliSecond
	
	public static final long SECOND_MILLI = 1000; // 一秒钟的MilliSecond

	// 数

	/**
	 * 没有分割符号的日期格式
	 */
	public static final String DATE_FORMAT_DATEONLYNOSP = "yyyyMMdd"; // 年/月/日

	/**
	 * 要用到的DATE Format的定义
	 */
	public static final String DATE_FORMAT_DATEONLY = "yyyy-MM-dd"; // 年/月/日

	public static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss"; // 年/月/日

	public static final String DATE_FORMAT_TIME = "HH:mm:ss"; // 时:分:秒

	public static final String DATE_FORMAT_DATETIMEONLY = "yyyy-MM-ddHHmm"; // 年/月/日

	public static final String DATE_FORMAT_DATESHORT = "M月d日"; // 年/月/日

	// 时:分:秒

	// public final static String DATE_FORMAT_DATEHOURMIN = "yyyy/MM/dd HH:mm";
	// // 年/月/日 时:分
	// public final static String DATE_FORMAT_SESSION = "yyyyMMddHHmm"; // 年/月/日
	// 时:分:秒
	public static String DATE_FORMAT_DATETIMETAMP ="yyyyMMddHHmmss"; //年月日时分秒
	
	public static final SimpleDateFormat sdfDateOnlyNoSp = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATEONLYNOSP);

	/**
	 * Global SimpleDateFormat object
	 */
	public static final SimpleDateFormat sdfDateOnly = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATEONLY);

	public static final SimpleDateFormat sdfDateTimeOnly = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATETIMEONLY);

	public static final SimpleDateFormat sdfDateTime = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATETIME);

	public static final SimpleDateFormat sdfTime = new SimpleDateFormat(DateUtil.DATE_FORMAT_TIME);

	public static final SimpleDateFormat sdfDateShort = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATESHORT);

	public static final SimpleDateFormat sdfDateTimeTamp = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATETIMETAMP);
	
	// private static SimpleDateFormat sdfDateSession = new
	// SimpleDateFormat(DateUtil.DATE_FORMAT_SESSION );

	/**
	 * Calendar Object
	 */
	@SuppressWarnings("unused")
	private GregorianCalendar gcal = null;

	@SuppressWarnings("unused")
	private Timestamp time = null;

	/***************************************************************************
	 * private attribute
	 **************************************************************************/
	/**
	 * 节假日情报的汉字名：如国庆节
	 */
	private String holidayString;

	/**
	 * Constructor
	 * 
	 * @return
	 * @since 1.0
	 * @history
	 */
	public DateUtil() {
		// TODO : Holiday情报的初期化
		gcal = new GregorianCalendar();
		time = new Timestamp(System.currentTimeMillis());
		// TODO : 节假日情报的初期化
	}

	/**
	 * 判断今天是否是节假日
	 * 
	 * @param
	 * @return true : 是
	 * @return false: 否
	 * @since 1.0
	 * @history
	 */
	public boolean isHoliday() {
		return false;
	}

	/**
	 * 取得节假日情报的汉字名
	 * 
	 * @param
	 * @return 节假日情报的汉字名
	 * @since 1.0
	 * @history
	 */
	public String getHolidayString() {
		return holidayString;
	}

	/**
	 * 设定节假日情报的汉字名
	 * 
	 * @param 节假日情报的汉字名
	 * @return
	 * @since 1.0
	 * @history
	 */
	public void setHolidayString(String holidayString) {
		this.holidayString = holidayString;
	}

	/**
	 * 取得DateUtil Instance
	 * 
	 * @return DateUtil Instance
	 * @since 1.0
	 * @history
	 */
	/*
	 * public static DateUtil getInstance(){ if (instance == null) { instance =
	 * new cn.com.sunjapan.ioffice.util.DateUtil(); } return instance; }
	 */

	/**
	 * @link
	 * @shapeType PatternLink
	 * @pattern Singleton
	 * @supplierRole Singleton factory
	 */
	/*
	 * private static DateUtil instance = null;
	 */

	/***************************************************************************
	 * java.util.Date ==> String 的转换函数
	 **************************************************************************/
	/**
	 * 利用缺省的Date格式(YYYY/MM/DD)转化String到Date
	 * 
	 * @param sDate
	 *            Date string
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static java.util.Date toDate(String sDate) {
		return toDate(sDate, DateUtil.sdfDateOnly);
	}

	/**
	 * 根据指定的Format转化String到Date
	 * 
	 * @param sDate
	 *            Date string
	 * @param sFmt
	 *            Date format , DATE_FORMAT_DATEONLY or DATE_FORMAT_DATETIME
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static java.util.Date toDate(String sDate, String sFmt) {
		if (sFmt.equals(DateUtil.DATE_FORMAT_DATETIME)) { // "YYYY/MM/DD
			// HH24:MI:SS"
			return toDate(sDate, DateUtil.sdfDateTime);
			// }else if( sFmt.equals (DateUtil.DATE_FORMAT_SESSION ) ){
			// //YYYYMMDDHHMI
			// return toDate(sDate,DateUtil.sdfDateSession );
		} else if (sFmt.equals(DateUtil.DATE_FORMAT_DATEONLY)) { // YYYY/MM/DD
			return toDate(sDate, DateUtil.sdfDateOnly);
		} else {
			return null;
		}
	}

	/**
	 * 利用指定SimpleDateFormat instance转化String到Date
	 * 
	 * @param sDate
	 *            Date string
	 * @param formatter
	 *            SimpleDateFormat instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public static java.util.Date toDate(String sDate, SimpleDateFormat formatter) {
		java.util.Date dt = null;
		try {
			dt = formatter.parse(sDate);
		} catch (Exception e) {
			e.printStackTrace();
			dt = null;
		}
		return dt;
	}

	/***************************************************************************
	 * String ==> java.util.Date 的转换函数
	 **************************************************************************/
	/**
	 * 根据缺省的Format(YYYY/MM/DD)转化java.util.Date到String
	 * 
	 * @param dt
	 *            java.util.Date instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public synchronized static String toString(java.util.Date dt) {
		if (dt == null) {
			return "";
		}
		String format = "yyyy-MM-dd"; // 年/月/日
		SimpleDateFormat formatDateOnly = new SimpleDateFormat(format);
		return toString(dt,formatDateOnly);
	}

	/**
	 * 
	 * @param date long
	 * @param fmt  格式化样式
	 * @return
	 */
	public synchronized static String toString(long date,String fmt){
		
		SimpleDateFormat sysDateTime = new SimpleDateFormat(fmt);
		return toString(new java.util.Date(date),sysDateTime);
	}
	/**
	 * 根据指定的Format转化java.util.Date到String
	 * 
	 * @param dt
	 *            java.util.Date instance
	 * @param sFmt
	 *            Date format , DATE_FORMAT_DATEONLY or DATE_FORMAT_DATETIME
	 * @return
	 * @since 1.0
	 * @history
	 */
	public synchronized static String toString(java.util.Date dt, String sFmt) {
		// add by SJNS/zq 03/16
		if (dt == null) {
			return "";
		}
		if (sFmt.equals(DateUtil.DATE_FORMAT_DATETIME)) { // "YYYY/MM/DD
			// HH24:MI:SS"
			return toString(dt, DateUtil.sdfDateTime);
		} else if (sFmt.equals(DateUtil.DATE_FORMAT_DATESHORT)) { // "YYYY/MM/DD
			// 月日"
			return toString(dt, DateUtil.sdfDateShort);

		} else { // Default , YYYY/MM/DD
			return toString(dt, DateUtil.sdfDateOnly);
		}
	}

	/**
	 * 利用指定SimpleDateFormat instance转换java.util.Date到String
	 * 
	 * @param dt
	 *            java.util.Date instance
	 * @param formatter
	 *            SimpleDateFormat Instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public synchronized static String toString(java.util.Date dt, SimpleDateFormat formatter) {
		String sRet = null;

		try {
			sRet = formatter.format(dt).toString();
		} catch (Exception e) {
			e.printStackTrace();
			sRet = null;
		}

		return sRet;
	}

	/**
	 * 比较两个java.sql.Timestamp instance 的年月日部分是否相同
	 * 
	 * @param date1 ,
	 *            java.sql.Timestamp Object
	 * @param date2 ,
	 *            java.sql.Timestamp Object
	 * @return true : 年月日部分相同
	 * @return false : 不同
	 * @since 1.0
	 * @history
	 */
	public synchronized static boolean isSameDay(java.sql.Timestamp date1, java.sql.Timestamp date2) {
		String s1, s2 = null;
		s1 = date1.toString().substring(0, DateUtil.DATE_FORMAT_DATEONLY.length());
		s2 = date2.toString().substring(0, DateUtil.DATE_FORMAT_DATEONLY.length());
		// cat.debug("in isSameDay() , s1=" + s1 + ",s2=" + s2);
		return s1.equalsIgnoreCase(s2);
	}

	// 获取某年的所有星期几的日期(如:weekDay=2为星期一,weekDay=3为星期2)
	public synchronized static List<String> getAllWeekDays(int year, int weekDay) {

		Calendar c = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat(DateUtil.DATE_FORMAT_DATEONLY);
		c.set(year, 0, 1);

		// 平年365天，即52周余一天，闰年366天，即52周余二天
		List<String> strWeekDayList = new ArrayList<String>();

		int i = -1;
		while (true) {
			i++;
			if (c.get(Calendar.YEAR) != year) {
				break;
			} else {
				if (c.get(Calendar.DAY_OF_WEEK) == weekDay) {
					strWeekDayList.add(f.format(c.getTime()).toString());
				}
				c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			}
		}
		return strWeekDayList;
	}

	/**
	 * 取得指定日期所在周的第一天(Sunday)
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return java.sql.Timestamp instance
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.sql.Timestamp getFirstDayOfWeek(java.sql.Timestamp timestamp) {
		int no = DateUtil.getWeekdayOfTimestamp(timestamp);
		java.sql.Timestamp out = DateUtil.addDays(timestamp, 1 - no);
		return out;
	}

	/**
	 * 取得指定日期所在周的最后一天(Saturday)
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return java.sql.Timestamp instance
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.sql.Timestamp getLastDayOfWeek(java.sql.Timestamp timestamp) {
		int no = DateUtil.getWeekdayOfTimestamp(timestamp);
		java.sql.Timestamp out = DateUtil.addDays(timestamp, 7 - no);
		return out;
	}

	/**
	 * 取得指定日期所在月的1号所在周的Sunday(可能是上个月的日期)
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return java.sql.Timestamp instance
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.sql.Timestamp getFirstSundayOfMonth(java.sql.Timestamp timestamp) {
		java.sql.Timestamp out = null;
		if (timestamp == null) {
			return null;
		}
		out = getFirstDayOfMonth(timestamp);
		out = DateUtil.getFirstDayOfWeek(out);
		return out;
	}

	/**
	 * 取得指定日期所在月的最后一天(如31号)所在周的Saturday(可能是下个月的日期)
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return java.sql.Timestamp instance
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.sql.Timestamp getLastSaturdayOfMonth(java.sql.Timestamp timestamp) {
		java.sql.Timestamp out = null;
		if (timestamp == null) {
			return null;
		}
		// cat.debug("In timestamp=" + timestamp.toString() );
		out = getLastDayOfMonth(timestamp);
		// cat.debug("LastDayOfMonth=" + out.toString() );
		out = DateUtil.getLastDayOfWeek(out);
		// cat.debug("LastSaturdayOfMonth=" + out.toString() );
		return out;
	}

	/**
	 * 取得指定日期所在月的第一天
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return java.sql.Timestamp instance
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.sql.Timestamp getFirstDayOfMonth(java.sql.Timestamp timestamp) {
		java.sql.Timestamp out = null;
		if (timestamp == null) {
			return null;
		}
		int day = DateUtil.getDayOfTimestamp(timestamp);
		out = DateUtil.addDays(timestamp, 1 - day);
		// out = DateUtil.getFirstDayOfWeek(out);
		return out;
	}

	/**
	 * 取得两个日期之间的日数
	 * 
	 * @return t1到t2间的日数，如果t2 在 t1之后，返回正数，否则返回负数
	 */
	public synchronized static long daysBetween(java.sql.Timestamp t1, java.sql.Timestamp t2) {
		return (t2.getTime() - t1.getTime()) / DAY_MILLI;
	}

	/**
	 * 取得指定日期所在月的最后一天
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return java.sql.Timestamp instance
	 * @since 1.0
	 * @history
	 */
	public static final int[] DAY_OF_MONTH_LEAP_YEAR = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public static final int[] DAY_OF_MONTH_NON_LEAP_YEAR = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * Format year/month/day to YYYY/MM/DD format
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return YYYY/MM/DD format String
	 */
	private synchronized static String formatYMD(int year, int month, int day) {
		String temp = String.valueOf(year) + "/";
		if (month < 10) {
			temp += "0" + String.valueOf(month) + "/";
		} else {
			temp += String.valueOf(month) + "/";
		}
		if (day < 10) {
			temp += "0" + String.valueOf(day);
		} else {
			temp += String.valueOf(day);
		}
		return temp;
	}

	/**
	 * Format day/month/year to YYYY/MM/DD format
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param DMYString
	 *            like 2/3/06
	 * @return day/MM/year format String
	 */
	public synchronized static String formatYMD(String dmyString) {
		String day = "";
		String month = "";
		String year = "";
		String[] dmy = dmyString.split("/");
		if (dmy != null && dmy.length > 0) {
			day = dmy[0];
			month = dmy[1];
			year = dmy[2];
		}
		StringBuffer temp = new StringBuffer();
		if (Integer.parseInt(year) < 100) {
			temp.append("20" + year + "-");
		} else {
			temp.append("21" + year + "-");
		}
		if (Integer.parseInt(month) < 10) {
			temp.append("0" + month + "-");
		} else {
			temp.append(month + "-");
		}
		if (Integer.parseInt(day) < 10) {
			temp.append("0" + day);
		} else {
			temp.append(day);
		}
		return temp.toString();
	}

	public synchronized static java.sql.Timestamp getLastDayOfMonth(java.sql.Timestamp timestamp) {
		java.sql.Timestamp out = null;
		if (timestamp == null) {
			return null;
		}
		// out = (java.sql.Timestamp )timestamp.clone() ;
		// day = DateUtil.getDayOfTimestamp(timestamp);
		// int month = DateUtil.getMonthOfTimestamp(timestamp);
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(timestamp);
		// month : 0 -- 11
		int day = 0;
		int year = obj.get(GregorianCalendar.YEAR);
		int month = obj.get(GregorianCalendar.MONTH) + 1;
		if (obj.isLeapYear(obj.get(GregorianCalendar.YEAR))) {
			day = DateUtil.DAY_OF_MONTH_LEAP_YEAR[month - 1];
		} else {
			day = DateUtil.DAY_OF_MONTH_NON_LEAP_YEAR[month - 1];
		}
		/*
		 * modified by ChenJP 2000/11/16 String temp = String.valueOf(year) +
		 * "/"; if( month < 10 ){ temp += "0" + String.valueOf(month) + "/";
		 * }else{ temp += String.valueOf(month)+ "/"; } if( day < 10 ){ temp +=
		 * "0" + String.valueOf(day); }else{ temp += String.valueOf(day); }
		 * //cat.debug("temp=" + temp); out = DateUtil.toSqlTimestamp(temp);
		 */
		out = DateUtil.toSqlTimestamp(DateUtil.formatYMD(year, month, day));

		/*
		 * obj.set(GregorianCalendar.DAY_OF_MONTH , day) ; out = new
		 * java.sql.Timestamp(obj.getTimeInMillis()); out =
		 * DateUtil.addDays(timestamp, 10) ; out =
		 * DateUtil.getFirstDayOfWeek(out);
		 */
		return out;

	}

	/***************************************************************************
	 * 把java.sql.Timestamp Object 取出各部分的值
	 **************************************************************************/
	/**
	 * 从java.sql.Timestamp Object 中取出Year value
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return year value
	 * @since 1.0
	 * @history
	 */
	public synchronized static int getYearOfTimestamp(java.sql.Timestamp timestamp) {
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(timestamp);
		return obj.get(GregorianCalendar.YEAR);
	}

	/**
	 * 从java.sql.Timestamp Object 中取出month value
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return month value(1 -- 12 )
	 * @since 1.0
	 * @history
	 */
	public synchronized static int getMonthOfTimestamp(java.sql.Timestamp timestamp) {
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(timestamp);
		return (obj.get(GregorianCalendar.MONTH) + 1);
	}

	/**
	 * 从java.sql.Timestamp Object 中取出day-of-month value
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return day value
	 * @since 1.0
	 * @history
	 */
	public synchronized static int getDayOfTimestamp(java.sql.Timestamp timestamp) {
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(timestamp);
		return obj.get(GregorianCalendar.DAY_OF_MONTH);
	}

	/**
	 * 从java.sql.Timestamp Object 中取出Weekday value
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return weekday value
	 * @since 1.0
	 * @history
	 */
	public synchronized static int getWeekdayOfTimestamp(java.sql.Timestamp timestamp) {
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(timestamp);
		return obj.get(GregorianCalendar.DAY_OF_WEEK);
	}

	/**
	 * 返回当天零时的Timestamp值
	 * 
	 * @param timestamp,
	 *            java.sql.Timestamp Object
	 * @return Zero time Timestamp
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.sql.Timestamp getZeroTime(java.sql.Timestamp timestamp) {
		String tempStr = timestamp.toString().substring(0, 10);
		return DateUtil.toSqlTimestamp(tempStr);
	}

	/**
	 * 从java.sql.Timestamp Object 中取出hour:minute
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return Hour:Minute format string
	 * @since 1.0
	 * @history
	 */
	public synchronized static String getHourAndMinuteString(java.sql.Timestamp timestamp) {
		String out = null;
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(timestamp);
		int hour = obj.get(GregorianCalendar.HOUR_OF_DAY);
		int minute = obj.get(GregorianCalendar.MINUTE);
		if (minute < 10) {
			out = String.valueOf(hour) + ":0" + String.valueOf(minute);
		} else {
			out = String.valueOf(hour) + ":" + String.valueOf(minute);
		}
		return out;
	}

	/**
	 * 从java.sql.Timestamp Object 中取出hour:minute
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return Hour:Minute format string
	 * @since 1.0
	 * @history
	 */
	public synchronized static String getCurrentHourAndMinuteString() {
		String out = null;
		java.sql.Timestamp time = new Timestamp(System.currentTimeMillis());
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(time);
		int hour = obj.get(GregorianCalendar.HOUR_OF_DAY);
		int minute = obj.get(GregorianCalendar.MINUTE);
		if (minute < 10) {
			out = String.valueOf(hour) + "0" + String.valueOf(minute);
		} else {
			out = String.valueOf(hour) + String.valueOf(minute);
		}
		return out;
	}

	/**
	 * 从java.sql.Timestamp Object 中取出hour:minute
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return Hour:Minute format string
	 * @since 1.0
	 * @history
	 */
	public synchronized static String getCurrentHourString() {
		String out = null;
		java.sql.Timestamp time = new Timestamp(System.currentTimeMillis());
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(time);
		int hour = obj.get(GregorianCalendar.HOUR_OF_DAY);
		if (hour < 10) {
			out = "0" + String.valueOf(hour);
		} else {
			out = String.valueOf(hour);
		}
		return out;
	}

	/**
	 * 从java.sql.Timestamp Object 中取出hour:minute
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return Hour:Minute format string
	 * @since 1.0
	 * @history
	 */
	public synchronized static String getCurrentMinuteString() {
		String out = null;
		java.sql.Timestamp time = new Timestamp(System.currentTimeMillis());
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(time);
		int minute = obj.get(GregorianCalendar.MINUTE);
		if (minute < 10) {
			out = "0" + String.valueOf(minute);
		} else {
			out = String.valueOf(minute);
		}
		return out;
	}

	/**
	 * 从java.sql.Timestamp Object 中取出secend
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return Hour:Minute format string
	 * @since 1.0
	 * @history
	 */
	public synchronized static String getCurrentSecendString() {
		String out = null;
		java.sql.Timestamp time = new Timestamp(System.currentTimeMillis());
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(time);
		int second = obj.get(GregorianCalendar.SECOND);
		if (second < 10) {
			out = "0" + String.valueOf(second);
		} else {
			out = String.valueOf(second);
		}
		return out;
	}

	/**
	 * 从java.sql.Timestamp Object 中取出hour value
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return 24小时制的hour value
	 * @since 1.0
	 * @history
	 */
	public synchronized static int getHourOfTimestamp(java.sql.Timestamp timestamp) {
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(timestamp);
		return obj.get(GregorianCalendar.HOUR_OF_DAY);
	}

	/**
	 * 从java.sql.Timestamp Object 中取出minute value
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return minute value
	 * @since 1.0
	 * @history
	 */
	public synchronized static int getMinuteOfTimestamp(java.sql.Timestamp timestamp) {
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(timestamp);
		return obj.get(GregorianCalendar.MINUTE);
	}

	/**
	 * 从java.sql.Timestamp Object 中取出second value
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return minute value
	 * @since 1.0
	 * @history
	 */
	public synchronized static int getSecondOfTimestamp(java.sql.Timestamp timestamp) {
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(timestamp);
		return obj.get(GregorianCalendar.SECOND);
	}

	/**
	 * 把java.sql.Timestamp Object 转换为java.util.GregorianCalendar Object
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return java.util.GregorianCalendar Object
	 * @since 1.0
	 * @history
	 * @deprecated please use confertToCalendar(java.sql.Timestamp)
	 */
	public synchronized static java.util.GregorianCalendar convertTimestampToCalendar(java.sql.Timestamp timestamp) {
		return convertToCalendar(timestamp);
	}

	/**
	 * 把java.sql.Timestamp Object 转换为java.util.GregorianCalendar Object
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return java.util.GregorianCalendar Object
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.util.GregorianCalendar convertToCalendar(java.sql.Timestamp timestamp) {
		GregorianCalendar obj = new GregorianCalendar();
		// Modified by ChenJP 2000/11/17
		obj.setTime(DateUtil.convertTimestampToDate(timestamp));
		// 下面的method不能用，long ==> int 精度不对
		// obj.set(GregorianCalendar.MILLISECOND , (int)timestamp.getTime() );
		return obj;
	}

	/**
	 * 把java.sql.Timestamp Object 转换为java.util.Date Object
	 * 
	 * @param timestamp ,
	 *            java.sql.Timestamp Object
	 * @return java.util.Date Object
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.util.Date convertTimestampToDate(java.sql.Timestamp timestamp) {
		java.util.Date date = null;
		/*
		 * modified by ChenJP 2000/11/17 String temp = null; temp =
		 * timestamp.toString (); temp = temp.substring
		 * (0,DateUtil.DATE_FORMAT_DATETIME.length ()); temp = temp.replace
		 * ('-','/'); try{ date = DateUtil.sdfDateTime.parse (temp);
		 * }catch(Exception e){ e.printStackTrace(); date = null; }
		 */
		date = new Date(timestamp.getTime());
		return date;
	}

	/***************************************************************************
	 * 取系统日期、时间的函数
	 **************************************************************************/
	/**
	 * 返回long型的SYSDATE
	 * 
	 * @return long型的SYSDATE
	 * @since 1.0
	 * @history
	 */
	public synchronized static long getSysDateLong() {
		return System.currentTimeMillis();
	}

	/**
	 * 返回java.sql.Timestamp型的SYSDATE
	 * 
	 * @return java.sql.Timestamp型的SYSDATE
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.sql.Timestamp getSysDateTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	/**
	 * 以YYYY/MM/DD格式返回系统日期
	 * 
	 * @return 系统日期
	 * @since 1.0
	 * @history
	 */
	public synchronized static String getSysDateString() {
		String sysFormat = "yyyy-MM-dd"; // 年/月/日
		SimpleDateFormat sysDateTime = new SimpleDateFormat(sysFormat);
		return toString(new java.util.Date(System.currentTimeMillis()), sysDateTime);
	}
	
	/**
	 * @param date输入日期增加一年
	 * @param sFromat
	 * @param y
	 * @return
	 */
	public static String addYearString(java.util.Date date,String sFromat,int y){
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, y);
		SimpleDateFormat sysDateTime = new SimpleDateFormat(sFromat);
		return toString(c.getTime(), sysDateTime);
	}

	public synchronized static String getNoSpSysDateString() {
		String sysFormat = "yyyyMMdd"; // 年/月/日
		SimpleDateFormat sysDateTime = new SimpleDateFormat(sysFormat);
		return toString(new java.util.Date(System.currentTimeMillis()), sysDateTime);
	}

	public synchronized static String getNoSpSysDateTimeString() {
		String DATETIMEONLY = "yyyy-MM-ddHHmm"; // 年/月/日
		SimpleDateFormat sysDateTimeOnly = new SimpleDateFormat(DATETIMEONLY);
		return toString(new java.util.Date(System.currentTimeMillis()),sysDateTimeOnly);
	}

	/**
	 * 以YYYY/MM/DD HH24:MI:SS格式返回系统日期时间
	 * 
	 * @return 系统日期时间
	 * @since 1.0
	 * @history
	 */
	public synchronized static String getSysDateTimeString() {
		String sysFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sysDateTime = new SimpleDateFormat(sysFormat);
		return toString(new java.util.Date(System.currentTimeMillis()),sysDateTime);
	}
	public synchronized static String getSysDateTimeString(SimpleDateFormat sdf){
		
		return toString(new java.util.Date(System.currentTimeMillis()), sdf);
	}
	
	/**
	 * HH24:MI:SS格式返回系统时间
	 * 
	 * @return 系统时间
	 * @since 1.0
	 * @history
	 */
	public synchronized static String getSysTimeString() {
		return toString(new java.util.Date(System.currentTimeMillis()), DateUtil.sdfTime);
	}

	/***************************************************************************
	 * java.sql.Date ==> String 的转换函数
	 **************************************************************************/
	/**
	 * 利用缺省的Date格式(YYYY/MM/DD)转化String到java.sql.Date
	 * 
	 * @param sDate
	 *            Date string
	 * @return
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.sql.Date toSqlDate(String sDate) {
		// return toSqlDate( sDate, this.DATE_FORMAT_DATEONLY );
		// java.sql.Date.value() 要求的格式必须为YYYY-MM-DD
		// System.out.println("sDate :"+sDate);
		if (sDate == null || sDate.equals("")) {
			return null;
		}
		if (sDate.equals("1899-12-30")) {
			return null;
		}
		return java.sql.Date.valueOf(sDate.replace('/', '-'));
	}

	/***************************************************************************
	 * String ==> java.sql.Date 的转换函数
	 **************************************************************************/
	/**
	 * 转换java.sql.Date到String，g格式为YYYY/MM/DD
	 * 
	 * @param dt
	 *            java.sql.Date instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public synchronized static String toSqlDateString(java.sql.Date dt) {
		String temp = null;
		temp = dt.toString();
		return temp.replace('-', '/');
	}

	/***************************************************************************
	 * java.sql.Timestamp ==> String 的转换函数
	 **************************************************************************/
	/**
	 * 转换GregorianCalendar Object到java.sql.Timestamp
	 * 
	 * @param gcal
	 *            GregorianCalendar Object
	 * @return java.sql.Timestamp object
	 * @since 1.0
	 * @history
	 */
	/*
	 * public static java.sql.Timestamp toSqlTimestamp(GregorianCalendar gcal){
	 * return new
	 * java.sql.Timestamp((long)gcal.get(GregorianCalendar.MILLISECOND) ); }
	 */
	/**
	 * 利用缺省的Date格式(YYYY/MM/DD)转换String到java.sql.Timestamp
	 * 
	 * @param sDate
	 *            Date string
	 * @return
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.sql.Timestamp toSqlTimestamp(String sDate) {
		if (sDate == null) {
			return null;
		}
		if (sDate.length() != DateUtil.DATE_FORMAT_DATEONLY.length()) {
			return null;
		}
		return toSqlTimestamp(sDate, DateUtil.DATE_FORMAT_DATEONLY);
	}

	/**
	 * 利用缺省的Date格式(YYYY/MM/DD hh:mm:ss)转化String到java.sql.Timestamp
	 * 
	 * @param sDate
	 *            Date string
	 * @param sFmt
	 *            Date format DATE_FORMAT_DATEONLY/DATE_FORMAT_DATETIME
	 * @return
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.sql.Timestamp toSqlTimestamp(String sDate, String sFmt) {
		String temp = null;
		//System.out.println("sDate = " + sDate);
		//System.out.println("sFmt  = " + sFmt);
		if (sDate == null || sFmt == null) {
			return null;
		}
		if (sDate.length() != sFmt.length()) {
			return null;
		}
		if (sFmt.equals(DateUtil.DATE_FORMAT_DATETIME)) {
			temp = sDate.replace('/', '-');
			temp = temp + ".000000000";
		} else if (sFmt.equals(DateUtil.DATE_FORMAT_DATEONLY)) {
			temp = sDate.replace('/', '-');
			temp = temp + " 00:00:00.000000000";
			// }else if( sFmt.equals (DateUtil.DATE_FORMAT_SESSION )){
			// //Format: 200009301230
			// temp =
			// sDate.substring(0,4)+"-"+sDate.substring(4,6)+"-"+sDate.substring(6,8);
			// temp += " " + sDate.substring(8,10) + ":" +
			// sDate.substring(10,12) + ":00.000000000";
		} else {
			return null;
		}
		//System.out.println("Temp = " + temp);
		// java.sql.Timestamp.value() 要求的格式必须为yyyy-mm-dd hh:mm:ss.fffffffff
		return java.sql.Timestamp.valueOf(temp);
	}

	/***************************************************************************
	 * String ==> java.sql.Date 的转换函数
	 **************************************************************************/
	/**
	 * 转换java.sql.Timestamp到String，g格式为YYYY/MM/DD
	 * 
	 * @param dt
	 *            java.sql.Timestamp instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public synchronized static String toSqlTimestampString(java.sql.Timestamp dt) {
		if (dt == null) {
			return null;
		}
		return toSqlTimestampString(dt, DateUtil.DATE_FORMAT_DATEONLY);
	}

	/**
	 * 转换java.sql.Timestamp到String，格式为YYYY/MM/DD HH24:MI
	 * 
	 * @param dt
	 *            java.sql.Timestamp instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public synchronized static String toSqlTimestampString2(java.sql.Timestamp dt) {
		if (dt == null) {
			return null;
		}
		String temp = toSqlTimestampString(dt, DateUtil.DATE_FORMAT_DATETIME);
		return temp.substring(0, 16);
	}

	public synchronized static String toSqlTimestampString3(java.sql.Timestamp dt) {
		if (dt == null) {
			return null;
		}
		String temp = toSqlTimestampString(dt, DateUtil.DATE_FORMAT_DATETIME);
		return temp.substring(0, 19);
	}

	public synchronized static String toString(java.sql.Timestamp dt) {
		return dt == null ? "" : toSqlTimestampString2(dt);
	}

	/**
	 * 将指定的java.sql.Timestamp类型的转变为指定的中文日期格式
	 */
	public synchronized static String convertTimestampToChinaCalendar(Timestamp timestamp) {
		StringBuffer sb = new StringBuffer();
		if (timestamp == null) {
			sb.append("&nbsp");
		} else {
			sb = new StringBuffer();
			sb.append(DateUtil.getYearOfTimestamp(timestamp));
			sb.append("年");
			sb.append(DateUtil.getMonthOfTimestamp(timestamp));
			sb.append("月");
			sb.append(DateUtil.getDayOfTimestamp(timestamp));
			sb.append("日");
			sb.append("　");
			sb.append(DateUtil.getHourOfTimestamp(timestamp));
			sb.append(":");
			if (DateUtil.getMinuteOfTimestamp(timestamp) < 10) {
				sb.append(0);
				sb.append(DateUtil.getMinuteOfTimestamp(timestamp));
			} else {
				sb.append(DateUtil.getMinuteOfTimestamp(timestamp));
			}
			sb.append(":");
			if (DateUtil.getSecondOfTimestamp(timestamp) < 10) {
				sb.append(0);
				sb.append(DateUtil.getSecondOfTimestamp(timestamp));
			} else {
				sb.append(DateUtil.getSecondOfTimestamp(timestamp));
			}
		}
		return sb.toString();

	}

	/**
	 * 根据指定的格式转换java.sql.Timestamp到String
	 * 
	 * @param dt
	 *            java.sql.Timestamp instance
	 * @param sFmt
	 *            Date
	 *            格式，DATE_FORMAT_DATEONLY/DATE_FORMAT_DATETIME/DATE_FORMAT_SESSION
	 * @return
	 * @since 1.0
	 * @history
	 */
	public synchronized static String toSqlTimestampString(java.sql.Timestamp dt, String sFmt) {
		String temp = null;
		String out = null;
		if (dt == null || sFmt == null) {
			return null;
		}
		temp = dt.toString();
		if (sFmt.equals(DateUtil.DATE_FORMAT_DATETIME) || // "YYYY/MM/DD
				// HH24:MI:SS"
				sFmt.equals(DateUtil.DATE_FORMAT_DATEONLY)) { // YYYY/MM/DD
			temp = temp.substring(0, sFmt.length());
			out = temp.replace('/', '-');
			// }else if( sFmt.equals (DateUtil.DATE_FORMAT_SESSION ) ){
			// //Session
			// out =
			// temp.substring(0,4)+temp.substring(5,7)+temp.substring(8,10);
			// out += temp.substring(12,14) + temp.substring(15,17);
		}
		return out;
	}

	/**
	 * 转换java.sql.Timestamp到HH24:MI String
	 * 
	 * @param dt
	 *            java.sql.Timestamp instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	public synchronized static String toHourMinString(java.sql.Timestamp dt) {
		String temp = null;
		temp = dt.toString();
		// int len = 0;
		// len = DateUtil.DATE_FORMAT_DATETIME.length ();
		temp = temp.substring(11, 16);
		return temp;

	}

	/***************************************************************************
	 * java.sql.Timestamp +/- 几天的计算函数
	 **************************************************************************/
	/**
	 * 判断指定的日期是否是一个月的最后一天
	 * 
	 * @param gcal
	 *            GregorianCalendar object
	 */
	private synchronized static boolean isLastDayOfMonth(GregorianCalendar obj) {
		int year = obj.get(GregorianCalendar.YEAR);
		int month = obj.get(GregorianCalendar.MONTH) + 1;
		int day = obj.get(GregorianCalendar.DAY_OF_MONTH);
		if (obj.isLeapYear(year)) {
			if (day == DateUtil.DAY_OF_MONTH_LEAP_YEAR[month - 1]) {
				return true;
			}
		} else {
			if (day == DateUtil.DAY_OF_MONTH_NON_LEAP_YEAR[month - 1]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 在java.sql.Timestamp Object上增加/减少几个月
	 * 
	 * @param timestamp
	 *            java.sql.Timestamp instance
	 * @param mon
	 *            增加/减少的月数
	 * @return java.sql.Timestamp Object
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.sql.Timestamp addMonths(java.sql.Timestamp timestamp, int mon) {
		java.sql.Timestamp out = null;
		GregorianCalendar obj = DateUtil.convertTimestampToCalendar(timestamp);
		// month : 0 -- 11
		int year = obj.get(GregorianCalendar.YEAR);
		int month = obj.get(GregorianCalendar.MONTH) + 1;
		int day = obj.get(GregorianCalendar.DAY_OF_MONTH);
		month += mon;
		if (month < 1) {
			month += 12;
			year--;
		} else if (month > 12) {
			month -= 12;
			year++;
		}
		if (isLastDayOfMonth(obj)) {
			if (obj.isLeapYear(year)) {
				day = DateUtil.DAY_OF_MONTH_LEAP_YEAR[month - 1];
			} else {
				day = DateUtil.DAY_OF_MONTH_NON_LEAP_YEAR[month - 1];
			}
		}
		String temp = DateUtil.formatYMD(year, month, day);
		out = DateUtil.toSqlTimestamp(temp);
		return out;
	}

	// 获取某年某月的总天数
	public synchronized static int getMonthAllDays(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 0);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 在java.sql.Timestamp Object上增加/减少几天
	 * 
	 * @param timestamp
	 *            java.sql.Timestamp instance
	 * @param days
	 *            增加/减少的天数
	 * @return java.sql.Timestamp Object
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.sql.Timestamp addDays(java.sql.Timestamp timestamp, int days) {
		/*
		 * 这种方法不能用，取得的MilliSeconds = 0 GregorianCalendar gcal =
		 * DateUtil.convertTimestampToCalendar (timestamp); long temp =
		 * gcal.get(GregorianCalendar.MILLISECOND );
		 */
		java.util.Date date = DateUtil.convertTimestampToDate(timestamp);
		long temp = date.getTime();

		return new java.sql.Timestamp(temp + DateUtil.DAY_MILLI * days);
	}

	/**
	 * 在java.util.DateObject上增加/减少几天
	 * 
	 * @param timestamp
	 *            java.util.Date instance
	 * @param days
	 *            增加/减少的天数
	 * @return java.util.Date Object
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.util.Date addDays(java.util.Date date, int days) {
		long temp = date.getTime();

		return new java.util.Date(temp + DateUtil.DAY_MILLI * days);
	}

	/**
	 * 在java.util.DateObject上增加/减少几分钟
	 * 
	 * @param timestamp
	 *            java.util.Date instance
	 * @param days
	 *            增加/减少的分钟数
	 * @return java.util.Date Object
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.util.Date addMinute(java.util.Date date, int minute) {
		long temp = date.getTime();

		return new java.util.Date(temp + DateUtil.MINUTE_MILLI * minute);
	}

	/**
	 * minute 为增加或者减少的分钟
	 */
	public synchronized static String addMinute(int minute) {
		return DateUtil.toString(new java.util.Date(System.currentTimeMillis() + minute * DateUtil.MINUTE_MILLI), DateUtil.sdfDateTime);
	}
	
	/**
	 * 在java.util.DateObject上增加/减少几秒钟
	 * 
	 * @param timestamp
	 *            java.util.Date instance
	 * @param days
	 *            增加/减少的秒钟数
	 * @return java.util.Date Object
	 * @since 1.0
	 * @history
	 */
	public synchronized static java.util.Date addSecond(java.util.Date date, int second) {
		long temp = date.getTime();

		return new java.util.Date(temp + DateUtil.SECOND_MILLI * second);
	}

	/**
	 * minute 为增加或者减少的分钟
	 */
	public synchronized static String addSecond(int second) {
		return DateUtil.toString(new java.util.Date(System.currentTimeMillis() + second * DateUtil.SECOND_MILLI), DateUtil.sdfDateTime);
	}

	/**
	 * 根据生日得到年龄
	 * 
	 * @param birthday
	 *            String 生日
	 * @return int 年龄
	 * @since 1.0
	 * @history
	 */
	public synchronized static int getAge(String birthday) {
		if (birthday == null || birthday.equals("")) {
			return 0;
		} else {
			String year = birthday.substring(0, 4);
			int birth = Integer.parseInt(year);

			String nowyear = getSysDateString().substring(0, 4);
			int sysYear = Integer.parseInt(nowyear);
			int result = sysYear - birth;
			return result;
		}
	}

	/**
	 * 判断是否过期
	 */
	@SuppressWarnings("deprecation")
	public synchronized static boolean isBefore(String date) {
		long sysTime = System.currentTimeMillis();
		java.util.Date sysDate = new java.util.Date(sysTime);
		java.util.Date lastTime = new java.util.Date(date);
		if (sysDate.before(lastTime)) {
			return true;
		}
		return false;
	}

	public synchronized static String getSystemWeekdayString() {
		int day = getWeekdayOfTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
		if (day == 1) {
			return ("星期日");
		}
		if (day == 2) {
			return ("星期一");
		}
		if (day == 3) {
			return ("星期二");
		}
		if (day == 4) {
			return ("星期三");
		}
		if (day == 5) {
			return ("星期四");
		}
		if (day == 6) {
			return ("星期五");
		}
		if (day == 7) {
			return ("星期六");
		}
		return "";
	}

	/*
	public synchronized static void main(String[] args) {
		int day = DateUtil.getWeekdayOfTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
		// System.out.println(toString(toDate("2006-12-25")));

		if (day == 1) {
			System.out.println("星期日");
		}
		if (day == 2) {
			System.out.println("星期一");
		}
		if (day == 3) {
			System.out.println("星期二");
		}

		// getAllWeekDays(2008,2);

		// String t = "2000-02-01 01:02:00";
		// String t2 = "2000-03-10 10:02:00";
		// System.out.print((double)compareTimestamp(t,t2)/(24*60*60*1000));
	}
*/
	


	/**
	 * 将LONG型的时间数据转换成一个Calendar。
	 * 
	 * @param timeIsLong
	 * @return
	 */
	public synchronized static Calendar getCalendar(Long timeIsLong) {
		Calendar calendar = Calendar.getInstance();
		if (timeIsLong != null) {
			calendar.setTimeInMillis(timeIsLong);
		}
		return calendar;
	}

	/**
	 * 通过LONG型的时间数据得到年份。
	 * 
	 * @param timeIsLong
	 * @return
	 */
	public synchronized static int getYear(Long timeIsLong) {
		Calendar calendar = Calendar.getInstance();
		if (timeIsLong != null) {
			calendar.setTimeInMillis(timeIsLong);
		}
		int year = calendar.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 通过Calendar型的时间数据得到年份。
	 * 
	 * @param timeIsLong
	 * @return
	 */
	public synchronized static int getCalendar(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 比较两个时间的大小(时:分:秒)
	 * 
	 * @param time1
	 *            时间1(时:分:秒)
	 * @param time2
	 *            时间2(时:分:秒)
	 * @return true:时间1小于或等于时间2 false:时间1大于或等于时间2
	 */
	public synchronized static boolean comparTime(String time1, String time2,boolean isEquals) {
		try {
			long date1 = sdfTime.parse(time1).getTime();
			long date2 = sdfTime.parse(time2).getTime();
			if(isEquals){
				if (date1 >= date2) {
					return false;
				} else {
					return true;
				}
			}else{
				if (date1 > date2) {
					return false;
				} else {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;

	}
	
	/**
	 * 两个日期比较,不能相等，格式必须为2012-12-12,起始日期大于结束日期 返回false
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public synchronized static boolean compareDate(String startDate,String endDate){
		if(java.sql.Date.valueOf(startDate).after(java.sql.Date.valueOf(endDate))){
			return false;
		}else{
			return true;
		}
	}

	public static void main(String[] args) throws ParseException {
		String datea="2012-03-31 00:00:02";
		java.util.Date date1  = convertToDate(datea,DATE_FORMAT_DATETIME);
		
		
		String dateb="2012-03-31 00:00:03";
		java.util.Date date2  = convertToDate(dateb,DATE_FORMAT_DATETIME);
		
		//System.out.print(date1.compareTo(date2));
	}

	public static java.util.Date convertToDate(String date,String sysFormat) throws ParseException{
		SimpleDateFormat sysDateTime = new SimpleDateFormat(sysFormat);
		return (java.util.Date) sysDateTime.parse(date);
		
		
	}
	
	
	public static boolean isValidDateFor19(String sDate){ 
        
        Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|(1[0-9])|(2[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$"); 

        if ((sDate != null)) {
			Matcher match = pattern.matcher(sDate);
			if (match.matches()) {
				
				match = pattern.matcher(sDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
  }
	
	/**
	 * 判断日期格式:yyyy-mm-dd
	 * 
	 * @param sDate
	 * @return
	 */
	public static boolean isValidDate(String sDate) {
		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if ((sDate != null)) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(sDate);
			if (match.matches()) {
				pattern = Pattern.compile(datePattern2);
				match = pattern.matcher(sDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
	}
}


package com.lee.common;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title:公共工具类
 * Description: 对日期的一些操作
 */
public class DateTimeUtil {

	/**
	 * 由java.util.Date到java.sql.Date的类型转换
	 * 
	 * @param date
	 * 
	 * @return Date
	 */
	public static Date getSqlDate(Date date) {
		return new Date(date.getTime());
	}

	public static Date nowDate() {
		Calendar calendar = Calendar.getInstance();
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 判断给定的年月日，是否为一周的开始
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	private static boolean getIsWeekBegin(int year, int month, int day) {
		boolean flag = false;
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day); // 设置日期
		int week = c.get(Calendar.DAY_OF_WEEK); // 获取当前日期星期，英国那边的算法(周日算一周第一天)
		if (week == 2) { // 如果是周1就返回true
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * 给定一个日期，获得这个日期所在周的周一的日期
	 * 
	 * @param date
	 *            void
	 * @exception 2012-10-20 下午5:56:58
	 */
	public static String getMonday(String date, String df) {
		if (!StringUtils.isEmpty(date)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			Date d = null;
			try {
				d = format.parse(date);
				Calendar cal = Calendar.getInstance();
				cal.setTime(d);
				// 关于DAY_OF_WEEK的说明
				// Field number for get and set indicating
				// the day of the week. This field takes values
				// SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
				// and SATURDAY
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

				if (!StringUtils.isEmpty(df))
					format = new SimpleDateFormat(df);
				return format.format(cal.getTime());
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		} else {
			return "";
		}
	}

	/**
	 * 判断给定的年月日，是否为一周的结束
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static boolean getIsWeekEnd(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day); // 设置日期
		int week = c.get(Calendar.DAY_OF_WEEK); // 获取当前日期星期，英国那边的算法(周日算一周第一天)
		if (week == 7 || week == 1) { // 如果是周六或周日就返回true
			return true;
		}
		return false;
	}

	/**
	 * 获得某月包含的周一个数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getWeekNumberOfMonth(int year, int month) {
		// 周1 的计数器count
		int count = 0;
		int day = getDayCountOfMonth(year, month);
		for (int i = 1; i <= day; i++) {
			if (getIsWeekBegin(year, month, i)) {
				count += 1;
			}
		}
		return count;
	}

	/**
	 * 获得某一日期的后一天
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getNextDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day + 1);
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 获得某一日期的前一天
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getPreviousDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day - 1);
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 返回给定日期所在月的第一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getFirstDayOfMonth(final String date)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d = format.parse(date);
		Calendar cal = new GregorianCalendar();
		cal.setTime(d);
		return getFirstDayOfMonth(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH) + 1);
	}

	/**
	 * 取得给定日期所在月的天数
	 * 
	 * @return int
	 */
	public static int getDayCountOfMonth(final String date) {
		Date d = StringToDate(date);
		Calendar cal = new GregorianCalendar();
		cal.setTime(d);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		calendar.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		calendar.set(Calendar.DATE, 0);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获取当前月的天数
	 * 
	 * @return int
	 * @exception 2012-10-19 下午9:58:07
	 */
	public static int getDayCountOfMonth() {
		return getDayCountOfMonth(Integer.parseInt(yyyyStr()),
				Integer.parseInt(mmStr()));
	}

	/**
	 * 获得某年某月第一天的日期
	 * 
	 * @param year
	 * @param month
	 * @return Date
	 */
	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 获得某年某月最后一天的日期
	 * 
	 * @param year
	 * @param month
	 * @return Date
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);
		return getPreviousDate(getSqlDate(calendar.getTime()));
	}

	/**
	 * 由年月日构建java.sql.Date类型
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return Date
	 */
	public static Date buildDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date);
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 取得某月的天数
	 * 
	 * @param year
	 * @param month
	 * @return int
	 */
	public static int getDayCountOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 0);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获得某年某季度的最后一天的日期
	 * 
	 * @param year
	 * @param quarter
	 * @return Date
	 */
	public static Date getLastDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = quarter * 3;
		}
		return getLastDayOfMonth(year, month);

	}

	/**
	 * 获得某年某季度的第一天的日期
	 * 
	 * @param year
	 * @param quarter
	 * @return Date
	 */
	public static Date getFirstDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = (quarter - 1) * 3 + 1;
		}
		return getFirstDayOfMonth(year, month);
	}

	/**
	 * 获得某年的第一天的日期
	 * 
	 * @param year
	 * @return Date
	 */
	public static Date getFirstDayOfYear(int year) {
		return getFirstDayOfMonth(year, 1);
	}

	/**
	 * 获得某年的最后一天的日期
	 * 
	 * @param year
	 * @return Date
	 */
	public static Date getLastDayOfYear(int year) {
		return getLastDayOfMonth(year, 12);
	}

	/**
	 * String到java.sql.Date的类型转换
	 * 
	 * @param param
	 * @return Date
	 */
	public static Date StringToDate(String param) {
		if (StringUtils.isEmpty(param)) {
			return null;
		} else {
			Date date = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				date = sdf.parse(param);
				return date;
			} catch (ParseException ex) {
				return null;
			}
		}
	}

	/**
	 * 时间格式： 20050601081202
	 */
	public static String nowNumStr() {
		Calendar now = Calendar.getInstance();
		String yyyy = String.valueOf(now.get(Calendar.YEAR));
		String mm = String.valueOf(now.get(Calendar.MONTH) + 1);
		String dd = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		String hh = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		String ii = String.valueOf(now.get(Calendar.MINUTE));
		String ss = String.valueOf(now.get(Calendar.SECOND));
		mm = (1 == mm.length()) ? ("0" + mm) : mm;
		dd = (1 == dd.length()) ? ("0" + dd) : dd;
		hh = (1 == hh.length()) ? ("0" + hh) : hh;
		ii = (1 == ii.length()) ? ("0" + ii) : ii;
		ss = (1 == ss.length()) ? ("0" + ss) : ss;
		String timeStr = yyyy + mm + dd + hh + ii + ss;
		return timeStr;
	}
	/**
	 * 时间格式： 20050601081202
	 */
	public static String nowymdNumStr() {
		Calendar now = Calendar.getInstance();
		String yyyy = String.valueOf(now.get(Calendar.YEAR));
		String mm = String.valueOf(now.get(Calendar.MONTH) + 1);
		String dd = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		mm = (1 == mm.length()) ? ("0" + mm) : mm;
		dd = (1 == dd.length()) ? ("0" + dd) : dd;
		String timeStr = yyyy + mm + dd;
		return timeStr;
	}
	/**
	 * 时间格式：2005-01
	 */
	public static String ymStr() {
		Calendar now = Calendar.getInstance();
		String yyyy = String.valueOf(now.get(Calendar.YEAR));
		String mm = String.valueOf(now.get(Calendar.MONTH) + 1);
		String dd = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		mm = (1 == mm.length()) ? ("0" + mm) : mm;
		return yyyy + "-" + mm;
	}
	/**
	 * 时间格式：2005-01-20
	 */
	public static String ymdStr() {
		Calendar now = Calendar.getInstance();
		String yyyy = String.valueOf(now.get(Calendar.YEAR));
		String mm = String.valueOf(now.get(Calendar.MONTH) + 1);
		String dd = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		mm = (1 == mm.length()) ? ("0" + mm) : mm;
		dd = (1 == dd.length()) ? ("0" + dd) : dd;
		return yyyy + "-" + mm + "-" + dd;
	}

	/**
	 * 将给定的日期增加相应年
	 * 
	 * @param date
	 *            待增加日期
	 * @param year
	 *            需要增加的年数
	 * @return 格式yyyy-MM-dd
	 * @throws ParseException
	 */
	public static String addOneYearForYmdStr(String date, int year)
			throws ParseException {
		Calendar now = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		Date d = dateFormat.parse(date);  
		now.setTime(d);
		String yyyy = String.valueOf(now.get(Calendar.YEAR) + year);
		String mm = String.valueOf(now.get(Calendar.MONTH) + 1);
		String dd = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		mm = (1 == mm.length()) ? ("0" + mm) : mm;
		dd = (1 == dd.length()) ? ("0" + dd) : dd;
		return yyyy + "-" + mm + "-" + dd;
	}

	/**
	 * 时间格式：08:12:45
	 */
	public static String hisStr() {
		Calendar now = Calendar.getInstance();
		String hh = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		String ii = String.valueOf(now.get(Calendar.MINUTE));
		String ss = String.valueOf(now.get(Calendar.SECOND));
		hh = (1 == hh.length()) ? ("0" + hh) : hh;
		ii = (1 == ii.length()) ? ("0" + ii) : ii;
		ss = (1 == ss.length()) ? ("0" + ss) : ss;
		return hh + ":" + ii + ":" + ss;
	}

	/**
	 * 时间格式： 2005-01-23 08:12:45
	 * 
	 * @return
	 */
	public static String nowTimeStr() {
		return ymdStr() + " " + hisStr();
	}

	/**
	 * 根据格式获得当前日期
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getNowDateStrForPattern(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

	/**
	 * 获取年 2006
	 */
	public static String yyyyStr() {
		Calendar now = Calendar.getInstance();
		now = Calendar.getInstance();
		String yyyy = String.valueOf(now.get(Calendar.YEAR));
		return yyyy;
	}

	/**
	 * 获取月 06
	 */
	public static String mmStr() {
		Calendar now = Calendar.getInstance();
		String mm = String.valueOf(now.get(Calendar.MONTH) + 1);
		mm = (1 == mm.length()) ? ("0" + mm) : mm;
		return mm;
	}

	/**
	 * 获取日
	 */
	public static String ddStr() {
		Calendar now = Calendar.getInstance();
		String dd = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		dd = (1 == dd.length()) ? ("0" + dd) : dd;
		return dd;
	}

	/**
	 * 格式化SQL日期
	 */
	public static String format(String pattern, java.sql.Date date) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 格式化JAVA日期
	 */
	public static String format(String pattern, Date date) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 在当前日期加或减多少天得到新日期
	 * 
	 * @param i
	 *            增加的天数
	 * @param operator
	 *            :+ -
	 * @return 新日期
	 */
	public static String strToDateByInt(int i, String operator, String df) {
		Date d = nowDate();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		if ("+".equals(operator)) {
			gc.add(GregorianCalendar.DATE, i);
		} else if ("-".equals(operator)) {
			gc.add(GregorianCalendar.DATE, (-i));
		}
		Date now = gc.getTime();
		if (df.equals(""))
			df = "yyyy-MM-dd hh:mm:ss"; // 默认输出时间格式
		SimpleDateFormat sdf = new SimpleDateFormat(df);
		return sdf.format(now);
	}

	/**
	 * 在指定日期上增加天数
	 * 
	 * @param day
	 * @param operator
	 * @param df
	 * @return String
	 * @exception
	 */
	public static String strDateByInt(int day, String oldDate, String operator,
			String df) {
		if (!StringUtils.isEmpty(df)) {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if (!StringUtils.isEmpty(df))
				format = new SimpleDateFormat(df);
			Date d = null;
			try {
				d = format.parse(oldDate);
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(d);
				if ("+".equals(operator)) {
					gc.add(GregorianCalendar.DATE, day);
				} else if ("-".equals(operator)) {
					gc.add(GregorianCalendar.DATE, (-day));
				}
				Date now = gc.getTime();
				return format.format(now);
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		} else {
			return "";
		}
	}

	/**
	 * 计算某年某月有多少天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int daysInMonth(int year, int month) {
		if (month <= 0 || month > 12) {
			return -1;
		}
		int days = 31;
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			days = 30;
		}
		if (month == 2) {
			if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
				days = 29;
			} else {
				days = 28;
			}
		}
		return days;
	}

	/**
	 * 取得下个月
	 * 
	 */
	public static void getNextMonth() {
		try {
			Calendar cl = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			cl.setTime(sdf.parse("200601"));
			// cl.add(cl.MONTH, 1);
			System.out.print(cl.get(Calendar.MONTH + 1));//
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计算两个日期之间的月差
	 */
	public static int dispersionMonth2(String strDate1, String strDate2) {
		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(DateFormat.getDateInstance().parse(
					strDate1));
			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(DateFormat.getDateInstance().parse(
					strDate2));
			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1
					.get(Calendar.DAY_OF_MONTH))
				flag = 1;
			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1
					.get(Calendar.YEAR))
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1
						.get(Calendar.YEAR))
						* 12
						+ objCalendarDate2.get(Calendar.MONTH) - flag)
						- objCalendarDate1.get(Calendar.MONTH);
			else
				iMonth = objCalendarDate2.get(Calendar.MONTH)
						- objCalendarDate1.get(Calendar.MONTH) - flag;
		} catch (Exception e) {
		}
		return iMonth;
	}
	
	  /** 
     * 两个时间之间相差距离多少天 
     * @return 相差天数
     */  
    public static long getDistanceDays(String str1, String str2){  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
        Date one;  
        Date two;  
        long days=0;  
        try {  
            one = df.parse(str1);  
            two = df.parse(str2);  
            long time1 = one.getTime();  
            long time2 = two.getTime();  
            long diff ;  
            if(time1<time2) {  
                diff = time2 - time1;  
            } else {  
                diff = time1 - time2;  
            }  
            days = diff / (1000 * 60 * 60 * 24);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return days;  
    }  
    
    /**
     * 返回日期中间的所有日期list形式
     * @param str1
     * @param str2
     * @return
     */
    public static List<Map<String,Object>> getDistanceDaysList(String str1, String str2){  
    	List<Map<String,Object>> daysList = new ArrayList<Map<String,Object>>();
    	Map<String,Object> daysMap = null;
    	long dayFlag = compare_date(str1,str2);
    	if(dayFlag == 1){//str1大于str2日期(格式不正确)
    		return null;
    	}else if(dayFlag == 0){//两个日期一样 
    		daysMap = new HashMap<String,Object>();
    		daysMap.put("date",str1); 
    		daysMap.put("count","0"); 
    		daysList.add(daysMap);
    		return daysList;
    	} else{ 
    		long daysNum = getDistanceDays(str1,str2);
    		daysMap = new HashMap<String,Object>(); 
    		daysMap.put("date",str1); 
    		daysMap.put("count","0"); 
    		daysList.add(daysMap);
    		for(int i = 1; i < daysNum; i++){ 
    			String dayStr = strDateByInt(i,str1,"+","yyyy-MM-dd");
    			daysMap = new HashMap<String,Object>(); 
        		daysMap.put("date",dayStr); 
        		daysMap.put("count","0"); 
        		daysList.add(daysMap);
    		}
    		daysMap = new HashMap<String,Object>(); 
    		daysMap.put("date",str2); 
    		daysMap.put("count","0");   
    		daysList.add(daysMap);
    	}
        return daysList;  
    }  
    /**
     * 返回日期中间的所有日期Map形式
     * @param str1
     * @param str2
     * @return
     */
    public static Map<String,Object> getDistanceDaysMap(String str1, String str2){  
    	Map<String,Object> daysMap = new LinkedHashMap<String,Object>();
    	long dayFlag = compare_date(str1,str2);
    	if(dayFlag == 1){//str1大于str2日期(格式不正确)
    		return null;
    	}else if(dayFlag == 0){//两个日期一样 
    		daysMap.put(str1, "0"); 
    		return daysMap;
    	} else{ 
    		long daysNum = getDistanceDays(str1,str2);
    		daysMap.put(str1, "0");
    		for(int i = 1; i < daysNum; i++){ 
    			String dayStr = strDateByInt(i,str1,"+","yyyy-MM-dd");
    			daysMap.put(dayStr, "0"); 
    		}
    		daysMap.put(str2, "0");
    	}
        return daysMap;  
    }  
     
	public static int dispersionDays2(String sEndDate, String sBeginDate)
			throws Exception {
		Calendar calendar1 = Calendar.getInstance();

		Calendar calendar2 = Calendar.getInstance();

		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");// 格式很重要：是20051031，还是2005-10-31格式呢？
		if (sBeginDate.equals("0")) {
			sBeginDate = "19000101";
		}
		calendar1.setTime(formatter1.parse(sBeginDate));
		calendar2.setTime(formatter1.parse(sEndDate));
		return (int) ((calendar2.getTimeInMillis() - calendar1
				.getTimeInMillis()) / 1000 / 60 / 60 / 24);// 获取天数的差值。
	}

	public static String GMTToString(String dateGMT) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		@SuppressWarnings("deprecation")
		Date satrt = new Date(dateGMT);
		String startStr = sdf.format(satrt);
		return startStr;
	}

	public static String dateFormat(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(dateStr);
		sdf = new SimpleDateFormat("HH:mm");
		String startStr = sdf.format(date);
		return startStr;
	}

	public static String dateFormat(String dateStr, String viewFormat)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(dateStr);
		sdf = new SimpleDateFormat(viewFormat);
		String startStr = sdf.format(date);
		return startStr;
	}

	public static String getDateByLong(long LongTime) {
		Date date = new Date(LongTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 日期比较
	 * 
	 * @param DATE1
	 * @param DATE2
	 * @return 0:两个日期一样 1：dt1 日期大。-1：dt1日期小。
	 */
	public static int compare_date(String DATE1, String DATE2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
//				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
//				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	/**
	 * 时间比较
	 * yyyy-MM-dd HH:mm:ss"
	 * @param DATE1
	 * @param DATE2
	 * @return 0:两个日期一样 1：dt1 日期大。-1：dt1日期小。
	 */
	public static int compare_date_time(String DATE1, String DATE2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
//				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
//				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获得当前日期所在季度
	 * 
	 * @return 1：一季度，2：二季度，3：三季度，4：四季度
	 */
	public static int nowQuarter() {
		Calendar now = Calendar.getInstance();
		int month = now.get(Calendar.MONTH) + 1;
		if (month == 1 || month == 2 || month == 3) {
			return 1;
		} else if (month == 4 || month == 5 || month == 6) {
			return 2;
		} else if (month == 7 || month == 8 || month == 9) {
			return 3;
		} else {
			return 4;
		}
	}
	
	/**
	 * 根据当前年月获取上个月时间
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getLastMonthDate(String date) throws Exception{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
		c.setTime(sf.parse(date));
		c.add(Calendar.MONTH,-1);
		return sf.format(c.getTime());
	}
	/**
	 * 根据当前年月获取几个月前（后）时间
	 * @param date
	 * @param number 月数 正数为后 负数为前
	 * @return
	 * @throws Exception
	 */
	public static String getSomeMonthDate(String date,int number) throws Exception{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
		c.setTime(sf.parse(date));
		c.add(Calendar.MONTH,number);
		return sf.format(c.getTime());
	}
	/**
	 * 根据当前年月获取上一年时间
	 * @param date
	 * @param format: 例："yyyy-MM" 
	 * @return
	 * @throws Exception
	 */
	public static String getLastYearDate(String date,String format) throws Exception{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat(format);
		c.setTime(sf.parse(date));
		c.add(Calendar.YEAR,-1);
		return sf.format(c.getTime());
	}
	/**
	 * a - b 的秒数
	 * 时间格式必须为  yyyy-MM-dd HH:mm:ss
	 * @param a
	 * @param b
	 * @return
	 */
	public static long getDaysFromTwoTime(String a,String b){
		long days = 0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			Date d1 = df.parse(a);
			Date d2 = df.parse(b);
			long diff = d1.getTime() - d2.getTime();
			days = diff/(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return days;
	}
	public static String addDays(String date,int days) throws Exception{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		c.setTime(sf.parse(date));
		c.add(Calendar.DATE,days);
		return sf.format(c.getTime());
	} 
	
	/**
	 * 将yyyy-MM-dd转换为yyyy年MM月dd日
	 * @param  date
	 * @return yyyy年MM月dd日
	 * @throws Exception
	 */
	public static String ymdprintStr(String date){
		String printYmd="";
		if(!StringUtils.isEmpty(date)){
			if(date.length()>10){
				date = date.substring(0, 10);
			}
			String[] dates =date.split("-");
			printYmd=dates[0]+"年"+dates[1]+"月"+dates[2]+"日";
		}
		return printYmd;
	}
	/**
	 * 将yyyy-MM-dd转换为yyyyMMdd
	 * @param  date
	 * @return yyyyMMdd
	 * @throws Exception
	 */
	public static String yyyymmddprintStr(String date){
		String printYmd="";
		if(!StringUtils.isEmpty(date)){
			if(date.length()>10){
				date = date.substring(0, 10);
			}
			String[] dates =date.split("-");
			printYmd=dates[0]+dates[1]+dates[2];
		}
		return printYmd;
	}
	/**
	 * 开发区补贴发放记录导入功能
	 * 验证身份证号合法性
	 * 非空、18位合法
	 * @param idnumber
	 * @return
	 */
	public static boolean validateFfjlIdnumber(String idnumber){
		if(!StringUtils.isEmpty(idnumber)&&idnumber.trim().length()==18){
			return true;
		}
		return false;
	}
	
	/**
	 * 开发区补贴发放记录导入功能
	 * 验证发放金额合法性
	 * 非空、数字合法
	 * @param money
	 * @return
	 */
	public static boolean validateFfjlMoney(String money){
		if(!StringUtils.isEmpty(money) && money.trim().matches("\\d+(\\.\\d+)?")){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 检验日期是否符合格式	
	 * 支持格式： yyyy-M, yyyy-MM, yyyy.M, yyyy.MM, yyyy/M, yyyy/MM
	 * @param date
	 * @return
	 */
	public static boolean validateDateFormat(String date){
		
		String regex = "^(\\d{4})-(0{0,1}\\d{1}|1[0-2])"
				+ "|(\\d{4})\\.(0{0,1}\\d{1}|1[0-2])"
				+ "|(\\d{4})/(0{0,1}\\d{1}|1[0-2])$";
		Pattern pat = Pattern.compile(regex);
		Matcher matcher = pat.matcher(date);
		return matcher.find();
	}
	
	
	/**
	 * 检验日期是否符合格式	
	 * 支持格式：yyyy-MM, yyyy.MM, yyyy/MM
	 * @param date
	 * @return
	 */
	public static boolean validateDateFormatYMD(String date){
		
		String regex = "^(\\d{4})-(0{0,1}\\d{1}|1[0-2])-(0{0,1}\\d{1}|[12]\\d{1}|3[01])"
				+ "|(\\d{4})\\.(0{0,1}\\d{1}|1[0-2])\\.(0{0,1}\\d{1}|[12]\\d{1}|3[01])"
				+ "|(\\d{4})/(0{0,1}\\d{1}|1[0-2])/(0{0,1}\\d{1}|[12]\\d{1}|3[01])$";
		Pattern pat = Pattern.compile(regex);
		Matcher matcher = pat.matcher(date);
		return matcher.find();
	}
	

}

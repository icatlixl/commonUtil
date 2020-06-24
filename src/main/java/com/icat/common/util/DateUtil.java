package com.icat.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 时间工具类
 * author:icat  
 * blog:https://blog.techauch.com
 */
public class DateUtil {
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String MM_SS = "mm:ss";

	/**
	 * 获取date时间戳
	 */
	public static long getMillis(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	/**
	 * 添加天数 date 时间 day 天数
	 */
	public static Date addDate(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		long millis = getMillis(date) + day * 24L * 3600L * 1000L;
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	/**
	 * 判断时间是否为同一个月
	 */
	public static boolean isSameMonth(Date date1, Date date2) {
		if (null == date1 || null == date2) {
			return false;
		}
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
				&& calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
	}

	/**
	 * 时间转字符串 日期转字符串：默认格式
	 * 
	 */
	public static String formatDate(Date date) {
		String result = "";
		try {
			if (date != null) {
				DateFormat dateFormat = new SimpleDateFormat(YYYYMMDD);
				result = dateFormat.format(date);
			}
		} catch (Exception localException) {
		}
		return result;
	}

	/**
	 * 时间转字符串自定义类型
	 */
	public static String format(Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				DateFormat dateFormat = new SimpleDateFormat(format);
				result = dateFormat.format(date);
			}
		} catch (Exception localException) {
		}
		return result;
	}

	/**
	 * 字符串转时间
	 */
	public static Date parse(String dateStr, String format) {
		Date date = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			date = dateFormat.parse(dateStr);
		} catch (Exception localException) {
			System.err.println(localException.getMessage());
		}
		return date;
	}

	/**
	 * 字符串时间添加天数（自定义类型）
	 */
	public static String addDay(String data, int add, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cd = Calendar.getInstance();
		try {
			cd.setTime(sdf.parse(data));
			cd.add(5, add);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sdf.format(cd.getTime());
	}

	/**
	 * 添加天数
	 */
	public static Date addDay(Date date, int add) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(5, add);
		return cd.getTime();
	}

	/**
	 * 添加月份
	 */
	public static Date addMonth(Date data, int add) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(data);
		cd.add(Calendar.MONTH, add);
		return cd.getTime();
	}

	/**
	 * 获取本年所有的月份
	 */
	public static List<Integer> getInitMonthMapWithZero() {
		List<Integer> list = new ArrayList<Integer>();
		Calendar c = Calendar.getInstance();
		int count = c.get(Calendar.MONTH);
		for (int i = count; i >= 0; i--) {
			int j = count + 1 - i;
			Integer date = 0;
			if (j >= 1) {
				date = j;
			} else {
				int p = 11 - i;
				int n = count + 2 + p;
				date = (n >= 10 ? 0 : 0) + n;
			}
			list.add(date);
		}
		return list;
	}

	/**
	 * 清空时分秒
	 */
	public static Date getTimeDate(Date date) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		// 将时分秒,毫秒域清零
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		return cal1.getTime();
	}

	/**
	 * 两个日期的相差天数(date2-date1)
	 * 
	 * @param date2
	 * @param date1
	 * @return
	 */
	public static int daysDiff(Date date1, Date date2) {
		if (date2 == null || date1 == null) {
			throw new IllegalArgumentException();
		}
		return new Float((date2.getTime() - date1.getTime()) / 86400000L).intValue();
	}

	/**
	 * 判断时间是否为同一个时间区间 WEEK：周 MONTH：月 YEAR：年
	 */
	public static final String WEEK = "week";
	public static final String MONTH = "month";
	public static final String YEAR = "year";

	@SuppressWarnings("deprecation")
	public static final boolean isBelong(String type, Date dateTime) {
		Calendar currentTime = Calendar.getInstance();
		Calendar time = Calendar.getInstance();
		time.setTime(dateTime);
		boolean result = false;
		if (WEEK.equals(type)) {
			result = currentTime.getTime().getYear() == dateTime.getYear()
					&& currentTime.get(Calendar.WEEK_OF_YEAR) == time.get(Calendar.WEEK_OF_YEAR);
		}
		if (MONTH.equals(type)) {
			result = currentTime.getTime().getYear() == dateTime.getYear()
					&& currentTime.getTime().getMonth() == dateTime.getMonth();
		}
		if (YEAR.equals(type)) {
			result = currentTime.getTime().getYear() == dateTime.getYear();
		}
		return result;
	}

	public static Date format(int index) {
		Date result = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, index);// 当前时间减去一年，即一年前的时间
			result = calendar.getTime();
		} catch (Exception localException) {
		}
		return result;
	}

	/**
	 * 
	 * 自定义取值，Date类型转为String类型
	 * 
	 * @param date    日期
	 * @param pattern 格式化常量
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String dateToStr(Date date, String pattern) {
		SimpleDateFormat format = null;

		if (null == date)
			return null;
		format = new SimpleDateFormat(pattern, Locale.getDefault());

		return format.format(date);
	}

	/**
	 * 将字符串转换成Date类型的时间
	 * <hr>
	 * 
	 * @param s 日期类型的字符串<br>
	 *          datePattern :YYYY_MM_DD<br>
	 * @return java.util.Date
	 */
	public static Date strToDate(String s, String pattern) {
		if (s == null) {
			return null;
		}
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			date = sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}

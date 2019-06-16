package com.insight.core.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * 
 * 描述: 日期处理
 * 
 * Copyright © 2013 Frank All rights reserved
 * 
 * @author Frank
 * @version 1.0
 */
public class DateUtil {

	/**
	 *  默认显示日期的格式
	 */
	public static final String DATAFORMAT_STR = "yyyy-MM-dd";

	/**
	 *  默认显示日期的格式
	 */
	public static final String YYYY_MM_DATAFORMAT_STR = "yyyy-MM";

	/**
	 *  默认显示日期时间的格式
	 */
	public static final String DATATIMEF_STR = "yyyy-MM-dd HH:mm:ss";

	/**
	 *  默认显示简体中文日期的格式
	 */
	public static final String ZHCN_DATAFORMAT_STR = "yyyy年MM月dd日";

	/**
	 *  默认显示简体中文日期时间的格式
	 */
	public static final String ZHCN_DATATIMEF_STR = "yyyy年MM月dd日HH时mm分ss秒";

	/**
	 *  默认显示简体中文日期时间的格式
	 */
	public static final String ZHCN_DATATIMEF_STR_4yMMddHHmm = "yyyy年MM月dd日HH时mm分";

	private static DateFormat dateFormat = null;

	private static DateFormat dateTimeFormat = null;

	private static DateFormat zhcnDateFormat = null;

	private static DateFormat zhcnDateTimeFormat = null;
	static {
		dateFormat = new SimpleDateFormat(DATAFORMAT_STR);
		dateTimeFormat = new SimpleDateFormat(DATATIMEF_STR);
		zhcnDateFormat = new SimpleDateFormat(ZHCN_DATAFORMAT_STR);
		zhcnDateTimeFormat = new SimpleDateFormat(ZHCN_DATATIMEF_STR);
	}
		
	 private static DateFormat getDateFormat(String formatStr) {
			if (formatStr.equalsIgnoreCase(DATAFORMAT_STR)) {
				return dateFormat;
			} else if (formatStr.equalsIgnoreCase(DATATIMEF_STR)) {
				return dateTimeFormat;
			} else if (formatStr.equalsIgnoreCase(ZHCN_DATAFORMAT_STR)) {
				return zhcnDateFormat;
			} else if (formatStr.equalsIgnoreCase(ZHCN_DATATIMEF_STR)) {
				return zhcnDateTimeFormat;
			} else {
				return new SimpleDateFormat(formatStr);
			}
		}	
	
	private static DateFormat getDefaultDateFomat(){
    	return new SimpleDateFormat("yyyy-MM-dd");
    }
    
	private static DateFormat getFullDateFomat(){
    	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
	 /////////////////////////////////获取当前日期//////////////////////////////////////////////////////////
	 /**
     * 中文格式日期
	 * @author WangZhuzhu
	 * @param d : 日期
	 * @return String
	 */
    public static String getChineseDate(Date d)
    {
        if (d == null)
        {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd",new DateFormatSymbols());
        String dtrDate = df.format(d);
        return dtrDate.substring(0, 4) + "年"
                + Integer.parseInt(dtrDate.substring(4, 6)) + "月"
                + Integer.parseInt(dtrDate.substring(6, 8)) + "日";

    }
    
    /**
     * 获取当前时间的时间戳
     * 转换形式是 获得当前时间的毫秒数，再将毫秒转化成16进制 字符串
     * @author WangZhuzhu
     * @return String
     */
    public static  String getTimeStamp(){
    	Date date =new Date();
    	String timeStemp = Long.toHexString(date.getTime());
    	return timeStemp;
    }
    
    /**
     * 当前日期 yyyyMMdd格式
     * 例如20161020
     * @author WangZhuzhu
     * @return String
     */
    public static String getCurrentDate_String()
    {
        Calendar cal = Calendar.getInstance();
        String currentDate = null;

        String currentYear = (new Integer(cal.get(1))).toString();
        String currentMonth = null;
        String currentDay = null;
        if (cal.get(2) < 9)
            currentMonth = "0" + (new Integer(cal.get(2) + 1)).toString();
        else
            currentMonth = (new Integer(cal.get(2) + 1)).toString();
        if (cal.get(5) < 10)
            currentDay = "0" + (new Integer(cal.get(5))).toString();
        else
            currentDay = (new Integer(cal.get(5))).toString();
        currentDate = currentYear + currentMonth + currentDay;
        return currentDate;
    }
    
    /**
     * 当前日期
     * @author WangZhuzhu
     * @return Date 
     */
    public static Date getCurrentDate()
    {
        Calendar cal = Calendar.getInstance();
        Date d = cal.getTime();
        return d;
    }

    /**
     * 根据日期格式获取当前日期
     * @author WangZhuzhu
     * @param strFormat : 日期格式
     * @return String
     */
    public static String getCurrentDate_String(String strFormat)
    {
        Calendar cal = Calendar.getInstance();
        Date d = cal.getTime();
        return getDate(d, strFormat);
    }
    
    /**
     * 当前年
     * @author WangZhuzhu
     * @return int
     */
    public static int getCurrentYear()
    {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(1);
        return currentYear;
    }
    
    /**
     * 得到当前日期的年和月 格式yyyyMM
     * @author WangZhuzhu
     * @return String
     */
    public static String getCurrentYearMonth()
    {
        Calendar cal = Calendar.getInstance();
        String currentYear = (new Integer(cal.get(1))).toString();
        String currentMonth = null;
        if (cal.get(2) < 9)
            currentMonth = "0" + (new Integer(cal.get(2) + 1)).toString();
        else
            currentMonth = (new Integer(cal.get(2) + 1)).toString();
        return currentYear + currentMonth;
    }
    
    /**
	 * 当天的开始时间 日期格式
	 * @author WangZhuzhu
	 * @return Date
	 * @throws ParseException 日期格式转化错误
	 */
	public static Date getStartTimeOfToday() throws ParseException{
		DateFormat format = new SimpleDateFormat(DATAFORMAT_STR);
		String str = format.format(new Date()) + " " + "00:00:00";
		return getDateTime(str);
	}
	/**
	 * 当天的结束时间 日期格式
	 * @author WangZhuzhu
	 * @return Date
	 * @throws ParseException 日期格式转化错误
	 */
	public static Date getEndTimeOfToday() throws ParseException{
		DateFormat format = new SimpleDateFormat(DATAFORMAT_STR);
		String str = format.format(new Date()) + " " + "23:59:59";
		return getDateTime(str);
	}
	
	/**
	 * 当天起始点 字符串格式为  yyyy-MM-dd HH:mm:ss
	 * @author WangZhuzhu
	 * @return String
	 */

	public static String getStartTimeOfTodayToString() {
		String d = dateToDateString(new Date(), "yyyy-MM-dd HH:mm:ss");
		return d.substring(0, 11) + "00:00:00";
	}

	/**
	 * 获取当天结束点  字符串格式为  yyyy-MM-dd HH:mm:ss
	 * @author WangZhuzhu
	 * @return String
	 */
	public static String getEndTimeOfTodayToString() {
		String d = dateToDateString(new Date(), "yyyy-MM-dd HH:mm:ss");
		return d.substring(0, 11) + "23:59:59";
	}
	
    /////////////////////////////////////////////////////根据指定日期获取想要的日期/////////////////////////////////////////////////////////////////////////////
	/**
	 * 获得某一天的开始时间 日期格式
	 * @author WangZhuzhu
 	 * @param dateStr 日期字符串 格式要求 yyyy-MM-dd 或者 yyyy/MM/dd
	 * @return Date
	 * @throws ParseException  日期格式转化错误
	 */
	public static Date getStartTimeOfDay(String dateStr) throws ParseException{
		if(StringUtils.isBlank(dateStr)){
			return getStartTimeOfToday();
		} else if (dateStr.length() == 10){
			dateStr = dateStr + " " + "00:00:00";
		}
		return getDateTime(dateStr);
	}
	
	/**
	 * 获得某一天的结束时间 日期格式
	 * @author WangZhuzhu
	 * @param dateStr 日期字符串 格式要求 yyyy-MM-dd 或者 yyyy/MM/dd
	 * @return Date
	 * @throws ParseException 日期格式转化错误
	 */
	public static Date getEndTimeOfDay(String dateStr) throws ParseException{
		if(StringUtils.isBlank(dateStr)){
			return getEndTimeOfToday();
		} else if (dateStr.length() == 10){
			dateStr = dateStr + " " + "23:59:59";
		}
		return getDateTime(dateStr);
	}
    /**
     * 根据日期获取年份
     * @author WangZhuzhu
     * @param date :日期 （规则：date != null）
     * @return int
     */
    public static int convertDateToYear(Date date)
    {
    	if(date==null){
    		return 0;
    	}
        SimpleDateFormat df = new SimpleDateFormat("yyyy",
                new DateFormatSymbols());
        return Integer.parseInt(df.format(date));
    }

    /**
     * 根据日期获取年月
     * @author WangZhuzhu
     * @param d : 日期（规则：d不能为null）
     * @return String
     */
    public static String convertDateToYearMonth(Date d)
    {
    	if(d==null){
    		return null;
    	}
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM",
                new DateFormatSymbols());
        return df.format(d);
    }

    /**
     * 根据日期获取年月日
     * @author WangZhuzhu
     * @param d : 日期（规则：d不能为null）
     * @return String
     */
    public static String convertDateToYearMonthDay(Date d)
    {
    	if(d==null){
    		return null;
    	}
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd",
                new DateFormatSymbols());
        return df.format(d);
    }

    /**
     * 获得当前日期的下个月的第一天
     * @author WangZhuzhu
     * @return String
     */
    public static String getFirstDayOfNextMonth()
    {
        String strToday = getCurrentDate_String();
        return increaseYearMonth(strToday.substring(0, 6)) + "01";
    }
   

	/**
	 * 获得下一个月的第一天日期
	 * @author WangZhuzhu
	 * @param year 年 格式：yyyy
	 * @param month 月 格式：MM
	 * @return Date 
	 * @throws ParseException 日期格式转化错误
	 */
	public static Date getNextMonth(String year, String month) throws ParseException {
		String datestr = year + "-" + month + "-01";
		Date date = getDate(datestr, DATAFORMAT_STR);
		return getInternalDateByMon(date, 1);
	}

	/**
	 * 获得上一个月的第一天日期
	 * @author WangZhuzhu
	 * @param year 年 格式：yyyy
	 * @param month 月 ：mm
	 * @return Date 
	 * @throws ParseException  日期格式转化错误 
	 */
	public static Date getLastMonth(String year, String month) throws ParseException {
		String datestr = year + "-" + month + "-01";
		Date date = getDate(datestr, DATAFORMAT_STR);
		return getInternalDateByMon(date, -1);
	}
	
	/**
	 * 获取日期d的days天后的一个Date
	 * @author WangZhuzhu
	 * @param d 日期
	 * @param days 天数
	 * @return Date 
	 */
	public static Date getInternalDateByDay(Date d, int days) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.DATE, days);
		return now.getTime();
	}
	/**
	 *  获取日期d的months月后的一个Date
	 * @author WangZhuzhu
	 * @param d 日期
	 * @param months 月数
	 * @return Date
	 */
	public static Date getInternalDateByMon(Date d, int months) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.MONTH, months);
		return now.getTime();
	}

	/**
	 * 获取日期d的years年后的一个Date
	 * @author WangZhuzhu
	 * @param d 日期 
	 * @param years 年数
	 * @return Date
	 */
	public static Date getInternalDateByYear(Date d, int years) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.YEAR, years);
		return now.getTime();
	}
	
	/**
	 * 获取日期d的sec秒后的一个Date
	 * @author WangZhuzhu
	 * @param d 日期
	 * @param sec 秒数
	 * @return Date
	 */
	public static Date getInternalDateBySec(Date d, int sec) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.SECOND, sec);
		return now.getTime();
	}

	/**
	 * 获取日期d的min分后的一个Date
	 * @author WangZhuzhu
	 * @param d 日期
	 * @param min 分钟数
	 * @return Date
	 */
	public static Date getInternalDateByMin(Date d, int min) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.MINUTE, min);
		return now.getTime();
	}

	/**
	 * 获取日期d的hours小时后的一个Date
	 * @author WangZhuzhu
	 * @param d 日期
	 * @param hours 分钟数
	 * @return Date
	 */
	public static Date getInternalDateByHour(Date d, int hours) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.HOUR_OF_DAY, hours);
		return now.getTime();
	}
	
	/**
	 * 获取当前日期yyyy-MM-dd的形式
	 * @author WangZhuzhu
	 * @return String
	 */
	public static String getCurDate() {
		return dateToDateString(Calendar.getInstance().getTime(),
				DATAFORMAT_STR);
	}

	/**
	 * 获取当前日期yyyy年MM月dd日的形式
	 * @author WangZhuzhu
	 * @return String
	 */
	public static String getCurZhCNDate() {
		return dateToDateString(new Date(), ZHCN_DATAFORMAT_STR);
	}

	/**
	 * 获取当前日期时间yyyy-MM-dd HH:mm:ss的形式
	 * @author WangZhuzhu
	 * @return String
	 */
	public static String getCurDateTime() {
		return dateToDateString(new Date(), DATATIMEF_STR);
	}

	/**
	 * 获取当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
	 * @author WangZhuzhu
	 * @return String
	 */
	public static String getCurZhCNDateTime() {
		return dateToDateString(new Date(), ZHCN_DATATIMEF_STR);
	}

	
	
    /////////////////////////////////////////////////////日期之间的计算///////////////////////////////////////////////////////////////////
    /**
     * 计算两个年月之间间隔的月数
     * @author WangZhuzhu
     * @param dealMonth :第一个月份 （规则： 不能为null ）
     * @param alterMonth :第二个月份 （规则： 不能为null ）
     * @return int
     */
    public static int calBetweenTwoMonth(String dealMonth, String alterMonth){
    	//如果其中一个为null时结束本次计算
    	if(StringUtil.isEmpty(dealMonth)||StringUtil.isEmpty(alterMonth)){
    		return 0;
    	}
        int length = 0;
        if (dealMonth.length() != 6 || alterMonth.length() != 6)
        {
            length = -1;
        } else
        {
            int dealInt = Integer.parseInt(dealMonth);
            int alterInt = Integer.parseInt(alterMonth);
            if (dealInt < alterInt)
            {
                length = -2;
            } else
            {
                int dealYearInt = Integer.parseInt(dealMonth.substring(0, 4));
                int dealMonthInt = Integer.parseInt(dealMonth.substring(4, 6));
                int alterYearInt = Integer.parseInt(alterMonth.substring(0, 4));
                int alterMonthInt = Integer
                        .parseInt(alterMonth.substring(4, 6));
                length = (dealYearInt - alterYearInt) * 12
                        + (dealMonthInt - alterMonthInt);
            }
        }
        return length;
    }
    
    /**
     * 计算两个年月之间的相差月数
     * @author WangZhuzhu
     * @param strDateBegin 开始日期
     * @param strDateEnd 结束日期
     * @return String
     */
    public static String getMonthBetween(String strDateBegin, String strDateEnd)
    {
    		if(StringUtil.isEmpty(strDateBegin)||StringUtil.isEmpty(strDateEnd)){
    			return "0";
    		}
    		String strOut;
            if (strDateBegin.equals("") || strDateEnd.equals("")
                    || strDateBegin.length() != 6 || strDateEnd.length() != 6)
            {
                strOut = "";
            } else
            {
                int intMonthBegin = Integer.parseInt(strDateBegin.substring(0,
                        4))
                        * 12 + Integer.parseInt(strDateBegin.substring(4, 6));
                int intMonthEnd = Integer.parseInt(strDateEnd.substring(0, 4))
                        * 12 + Integer.parseInt(strDateEnd.substring(4, 6));
                strOut = String.valueOf(intMonthBegin - intMonthEnd);
            }
            return strOut;
    }
    
    /**
     * 计算日期间隔的天数
     * @author WangZhuzhu
     * @param beginDate : 开始日期 （规则：日期不能为null） 
     * @param endDate : 结束日期 （规则：日期不能为null）
     * @return 日期间隔的天数
     */
    public static int daysBetweenDates(Date beginDate, Date endDate)
    {   int days = 0;
    	if(beginDate==null||endDate==null){
    		 return days;
    	}
        Calendar calo = Calendar.getInstance();
        Calendar caln = Calendar.getInstance();
        calo.setTime(beginDate);
        caln.setTime(endDate);
        int oday = calo.get(6);
        int nyear = caln.get(1);
        for (int oyear = calo.get(1); nyear > oyear;)
        {
            calo.set(2, 11);
            calo.set(5, 31);
            days += calo.get(6);
            oyear++;
            calo.set(1, oyear);
        }

        int nday = caln.get(6);
        days = (days + nday) - oday;
        return days;
    }
   
	/**
	 * 获得指定日期月份的天数
	 * @author WangZhuzhu
	 * @param date 日期
	 * @return int
	 */
	public static int getMonthDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得指定日期月份的天数
	 * @author WangZhuzhu
	 * @param date 日期字符串 格式 yyyy-mm-dd
	 * @return int
	 * @throws ParseException  日期格式转化错误  
	 */
	public static int getMonthDay(String date) throws ParseException {
		Date strDate = getDate(date, DATAFORMAT_STR);
		return getMonthDay(strDate);

	}
    
	/**
	 * 获得系统当前月份的天数
	 * @author WangZhuzhu
	 * @return int
	 */
	public static int getCurentMonthDay() {
		Date date = Calendar.getInstance().getTime();
		return getMonthDay(date);
	}
	
	/**
	 * 获取Date中的分钟
	 * @author WangZhuzhu
	 * @param d 日期
	 * @return int
	 */
	public static int getMin(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.MINUTE);
	}
	
	/**
	 * 获取Date中的小时(24小时)
	 * @author WangZhuzhu
	 * @param d 日期
	 * @return int
	 */
	public static int getHour(Date d) {
		if(d==null)
			return 0;
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取Date中的秒
	 * @author WangZhuzhu
	 * @param d 日期
	 * @return int
	 */
	public static int getSecond(Date d) {
		if(d==null)
			return 0;
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.SECOND);
	}

	/**
	 * 获取指定日期的一个月的第几天
	 * @author WangZhuzhu
	 * @param d 日期
	 * @return int
	 */
	public static int getDay(Date d) {
		if(d==null)
			return 0;
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取指定日期的月份，一年的第几个月
	 * @author WangZhuzhu
	 * @param d 日期
	 * @return int
	 */
	public static int getMonth(Date d) {
		if(d==null)
			return 0;
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.MONTH) + 1;
	}
	
	   
    /**
     * 计算间隔天数后的日期
     * @author WangZhuzhu
     * @param date : 日期 (规则： 日期不能为null)
     * @param intBetween : 间隔天数
     * @return Date
     */
    public static Date getDateBetween(Date date, int intBetween)
    {
    	if(date==null){
    		return null;
    	}
        Calendar calo = Calendar.getInstance();
        calo.setTime(date);
        calo.add(Calendar.DATE, intBetween);
        return calo.getTime();
    }

    /**
     * 计算指定天数后的日期（带日期格式化）
     * @author WangZhuzhu
     * @param date  : 日期 (规则： 日期不能为null)
     * @param intBetween : 间隔天数
     * @param strFromat 日期格式
     * @return String
     */
    public static String getDateBetween_String(Date date, int intBetween,
            String strFromat)
    {
    	if(date==null){
    		return null;
    	}
        Date dateOld = getDateBetween(date, intBetween);
        return getDate(dateOld, strFromat);
    }
	
    /**
     * 年月值增1
     * @author WangZhuzhu
     * @param yearMonth :初始年月 （规则:yearMonth != null）
     * @return String
     * @throws java.lang.StringIndexOutOfBoundsException 字符串索引越界
     */
    public static String increaseYearMonth(String yearMonth)
    {
        int year = (new Integer(yearMonth.substring(0, 4))).intValue();
        int month = (new Integer(yearMonth.substring(4, 6))).intValue();
        if (++month <= 12 && month >= 10)
            return yearMonth.substring(0, 4) + (new Integer(month)).toString();
        if (month < 10)
            return yearMonth.substring(0, 4) + "0"
                    + (new Integer(month)).toString();

        return (new Integer(year + 1)).toString() + "0"
                + (new Integer(month - 12)).toString();
    }
    
    /**
     * 年月值增加指定的值
     * @author WangZhuzhu
     * @param yearMonth 初始年月 （规则：yearMonth != null）
     * @param addMonth 指定的值
     * @return String
     * @throws java.lang.StringIndexOutOfBoundsException 字符串索引越界
     */
    public static String increaseYearMonth(String yearMonth, int addMonth)
    {
        int year = (new Integer(yearMonth.substring(0, 4))).intValue();
        int month = (new Integer(yearMonth.substring(4, 6))).intValue();
        month += addMonth;
        year += month / 12;
        month %= 12;
        if (month <= 12 && month >= 10)
            return year + (new Integer(month)).toString();

        return year + "0" + (new Integer(month)).toString();
    }
    
    /**
     * 增加月
     * @author WangZhuzhu
     * @param strDate 初始年月
     * @param intDiff 增加的数量
     * @return  String
     */
    @Deprecated
    public static String getMonthBetween(String strDate, int intDiff)
    {
        try
        {
            int intYear = Integer.parseInt(strDate.substring(0, 4));
            int intMonth = Integer.parseInt(strDate.substring(4, 6));
            String strDay = "";
            if (strDate.length() > 6)
                strDay = strDate.substring(6, strDate.length());
            for (intMonth += intDiff; intMonth <= 0; intMonth += 12)
                intYear--;

            for (; intMonth > 12; intMonth -= 12)
                intYear++;

            if (intMonth < 10)
            {
                return Integer.toString(intYear) + "0"
                        + Integer.toString(intMonth) + strDay;
            }
            return Integer.toString(intYear) + Integer.toString(intMonth)
                    + strDay;
        } catch (Exception e)
        {
            return "";
        }
    }

    /**
     * 年月值减1
     * @author WangZhuzhu
     * @param yearMonth : 初始年月  （规则:yearMonth != null）
     * @return String
     * @throws java.lang.StringIndexOutOfBoundsException 字符串索引越界
     */
    public static String descreaseYearMonth(String yearMonth)
    {
        int year = (new Integer(yearMonth.substring(0, 4))).intValue();
        int month = (new Integer(yearMonth.substring(4, 6))).intValue();
        if (--month >= 10)
            return yearMonth.substring(0, 4) + (new Integer(month)).toString();
        if (month > 0 && month < 10)
            return yearMonth.substring(0, 4) + "0"
                    + (new Integer(month)).toString();

        return (new Integer(year - 1)).toString()
                + (new Integer(month + 12)).toString();
    }
    
    /**
     * 获取日期的最后一天 
     * @author WangZhuzhu
     * @param term  日期 格式yyyyMM
     * @return String
     */
    public static String getLastDay(String term)
    {
        int getYear = Integer.parseInt(term.substring(0, 4));
        int getMonth = Integer.parseInt(term.substring(4, 6));
        String getLastDay = "";
        if (getMonth == 2)
        {
            if (getYear % 4 == 0 && getYear % 100 != 0 || getYear % 400 == 0)
                getLastDay = "29";
            else
                getLastDay = "28";
        } else if (getMonth == 4 || getMonth == 6 || getMonth == 9
                || getMonth == 11)
            getLastDay = "30";
        else
            getLastDay = "31";
        return String.valueOf(getYear) + "年" + String.valueOf(getMonth)
                + "月" + getLastDay + "日";
    }
    
    /**
     * 比较日期1是否大于等于日期2
     * <p> true: s1大于等于s2 false s1小于s2</P>
     * @author WangZhuzhu
     * @param s1 日期1 格式yyyyMM
     * @param s2 日期2 格式yyyyMM
     * @return boolean
     */
    public static boolean yearMonthGreatEqual(String s1, String s2)
    {
    	if(StringUtil.isEmpty(s1)||StringUtil.isEmpty(s2)){
    		return false;
    	}
        String temp1 = s1.substring(0, 4);
        String temp2 = s2.substring(0, 4);
        String temp3 = s1.substring(4, 6);
        String temp4 = s2.substring(4, 6);
        if (Integer.parseInt(temp1) > Integer.parseInt(temp2))
            return true;
        if (Integer.parseInt(temp1) == Integer.parseInt(temp2))
            return Integer.parseInt(temp3) >= Integer.parseInt(temp4);

        return false;
    }
    

	/**
	 * 比较两个"yyyy-MM-dd HH:mm:ss"格式的日期，之间相差多少毫秒,time2-time1
	 * @author WangZhuzhu
	 * @param time1 时间1
	 * @param time2 时间2
	 * @return long
	 * @throws ParseException  日期格式转化错误  
	 */
	public static long compareDateStr(String time1, String time2) throws ParseException {
		Date d1 = getDate(time1);
		Date d2 = getDate(time2);
		return d2.getTime() - d1.getTime();
	}

	/**
	 * 将小时数换算成返回以毫秒为单位的时间
	 * @author WangZhuzhu
	 * @param hours 小时数
	 * @return long
	 */
	public static long getMicroSec(BigDecimal hours) {
		BigDecimal bd;
		bd = hours.multiply(new BigDecimal(3600 * 1000));
		return bd.longValue();
	}
    
	/////////////////////////////////////////////////////日期格式化成字符串///////////////////////////////////////////////////////////////////////
	 /**
     * 将日期格式化,如果时间部分全是0,将被舍弃
     * 如:  2014-08-26 00:00:00  转化成  2014-08-26
     * @author WangZhuzhu
     * @param d 日期
     * @return String
     */
    public static String formatDateWithout00(Date d){
    	if(d == null){
    		return "";
    	}
	    Calendar c = Calendar.getInstance();
	    c.setTime(d);
	    //如果日期最后时间部分全是0, 舍弃
	    if(c.get(Calendar.HOUR) == 0 && c.get(Calendar.MINUTE) == 0){
	    	return DateUtil.getDefaultDateFomat().format(d);
	    } else {
	    	return DateUtil.getFullDateFomat().format(d);
	    }
    }
    /**
     * 格式化日期
     * @author WangZhuzhu
     * @param d 日期
     * @param format 格式
     * @return String
     */
    public static String getDate(Date d, String format)
    {
        if (d == null)
            return "";
        Hashtable h = new Hashtable();
        String javaFormat = "" ;
        String s = format.toLowerCase();
        if (s.indexOf("yyyy") != -1)
            h.put(new Integer(s.indexOf("yyyy")), "yyyy");
        else if (s.indexOf("yy") != -1)
            h.put(new Integer(s.indexOf("yy")), "yy");
        if (s.indexOf("mm") != -1)
            h.put(new Integer(s.indexOf("mm")), "MM");
        if (s.indexOf("dd") != -1)
            h.put(new Integer(s.indexOf("dd")), "dd");
        if (s.indexOf("hh24") != -1)
            h.put(new Integer(s.indexOf("hh24")), "HH");
        if (s.indexOf("mi") != -1)
            h.put(new Integer(s.indexOf("mi")), "mm");
        if (s.indexOf("ss") != -1)
            h.put(new Integer(s.indexOf("ss")), "ss");
        for (int intStart = 0; s.indexOf("-", intStart) != -1; intStart++)
        {
            intStart = s.indexOf("-", intStart);
            h.put(new Integer(intStart), "-");
        }
        for (int intStart = 0; s.indexOf("/", intStart) != -1; intStart++)
        {
            intStart = s.indexOf("/", intStart);
            h.put(new Integer(intStart), "/");
        }
        for (int intStart = 0; s.indexOf(" ", intStart) != -1; intStart++)
        {
            intStart = s.indexOf(" ", intStart);
            h.put(new Integer(intStart), " ");
        }
        for (int intStart = 0; s.indexOf(":", intStart) != -1; intStart++)
        {
            intStart = s.indexOf(":", intStart);
            h.put(new Integer(intStart), ":");
        }
        if (s.indexOf("年") != -1)
            h.put(new Integer(s.indexOf("年")), "年");
        if (s.indexOf("月") != -1)
            h.put(new Integer(s.indexOf("月")), "月");
        if (s.indexOf("日") != -1)
            h.put(new Integer(s.indexOf("日")), "日");
        if (s.indexOf("时") != -1)
            h.put(new Integer(s.indexOf("时")), "时");
        if (s.indexOf("分") != -1)
            h.put(new Integer(s.indexOf("分")), "分");
        if (s.indexOf("秒") != -1)
            h.put(new Integer(s.indexOf("秒")), "秒");
        int i = 0;
        while (h.size() != 0)
        {
            Enumeration e = h.keys();
            int n = 0;
            while (e.hasMoreElements())
            {
                i = ((Integer) e.nextElement()).intValue();
                if (i >= n)
                    n = i;
            }
            String temp = (String) h.get(new Integer(n));
            h.remove(new Integer(n));
            javaFormat = temp + javaFormat;
        }
        SimpleDateFormat df = new SimpleDateFormat(javaFormat,
                new DateFormatSymbols());
        return df.format(d);
    }
    
    
    /**
	 * 获取指定日期 的yyyy形式的年 
	 * @author WangZhuzhu
	 * @param d 日期 
	 * @return int
	 */
	public static int getYear(Date d) {
		if(d==null)
			return 0;
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.YEAR);
	}
	
	/**
     * 将日期格式化成 yyyy-MM-dd格式
     * @author WangZhuzhu
     * @param date :日期
     * @return 日期格式化后的字符串
     */
    public static String getYmdDateStr(Date date){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	return df.format(date);
    }
    
	/**
	 * 将日期转换为 中文的字符格式   yyyy年MM月dd日
	 * @author WangZhuzhu
	 * @param date 日期
	 * @return String
	 */
	public static String parseDateToZHCnYmd(Date date){
		return zhcnDateFormat.format(date);
	}
	
 	/**
 	 * 依据指定的Long时间 返回格式为 yyyy-MM-dd HH:mm:ss 的时间字符串
 	 * @author WangZhuzhu
 	 * @param time 时间
 	 * @return  String
 	 */
 	public static String getDateStrByLongTime(long time){
 		return dateToDateString(new Date(time));
 	}
 	
 	/**
	 * 将日期转换成yyyymmdd格式字符串
	 * @author WangZhuzhu
 	 * @param date 日期
	 * @return  String
	 */
	public static String getYYYYMMDDDate(Date date) {
		if (date == null)
			return null;
		String yyyy = getYear(date) + "";
		String mm = getMonth(date) + "";
		String dd = getDay(date) + "";
		return yyyy + mm + dd;
	}
	
	/**
	 * 将日期转换成YYYYMMDDHHMMSS格式字符串
	 * @author WangZhuzhu
 	 * @param date 日期
	 * @return String
	 */
	public static String getYYYYMMDDHHMMSSDate(Date date) {
		if (date == null)
			return null;
		String yyyy = getYear(date) + "";
		String mm = getMonth(date) + "";
		String dd = getDay(date) + "";
		String hh = getHour(date) + "";
		String min = getMin(date) + "";
		String ss = getSecond(date) + "";
		return yyyy + mm + dd + hh + min + ss;
	}

	/**
	 * 将日期转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
	 * @author WangZhuzhu
 	 * @param date 日期
	 * @return String
	 */
	public static String dateToDateString(Date date) {
		return dateToDateString(date, DATATIMEF_STR);
	}

	/**
	 * 将Date转换成formatStr格式的字符串
	 * @author WangZhuzhu
	 * @param date 日期 
	 * @param formatStr 日期格式化字符串
	 * @return String
	 */
	public static String dateToDateString(Date date, String formatStr) {
		DateFormat df = getDateFormat(formatStr);
		return df.format(date);
	}
	
    
    /////////////////////////////////////////////////////字符串格式化成日期///////////////////////////////////////////////////////////////////////

    /**
     * 字符串转换成日期
     * @author WangZhuzhu
     * @param str 日期字符串
     * @param fmt 格式化字符串  包括 yyyy-MM-dd ; yyyy-MM-dd HH:mm:ss 等
     * @return date
	 * @throws ParseException  日期格式转化错误 
	 */
     public static Date stringToDate(String str,String fmt) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat(fmt);
        Date date = null;
        date = format.parse(str);
        return date;
     }
     

     /**
      * 字符串转换成日期
      * @author WangZhuzhu
      * @param str 日期字符串
      * @param fmt 格式化字符串  包括 yyyy-MM-dd ; yyyy-MM-dd HH:mm:ss 等
      * @return date
 	 * @throws ParseException  日期格式转化错误 
 	 */
      public static Date stringToDate(String str ) throws ParseException{
    	 String fmt = getFormateStr(str);
         SimpleDateFormat format = new SimpleDateFormat(fmt);
         Date date = null;
         date = format.parse(str);
         return date;
      }
     
 	/**
 	 * 按照默认显示日期时间的格式"yyyy-MM-dd HH:mm:ss"，转化dateTimeStr为Date类型
 	 * dateTimeStr必须是"yyyy-MM-dd HH:mm:ss"的形式
 	 * @author WangZhuzhu
 	 * @param dateTimeStr  日期字符串 
 	 * @return Date
 	 * @throws ParseException  日期格式转化错误  
 	 */
 	public static Date getDateTime(String dateTimeStr) throws ParseException {
 		return getDate(dateTimeStr, DATATIMEF_STR);
 	}
 	
 	
 	/**
 	 * 按照默认显示日期时间的格式"yyyy-MM-dd "，转化dateStr为Date类型
 	 * dateStr必须是"yyyy-MM-dd "的形式
 	 * @author WangZhuzhu
 	 * @param dateStr 日期字符串 
 	 * @return Date
 	 * @throws ParseException  日期格式转化错误  
 	 */
 	public static Date getDate(String dateStr) throws ParseException {
 		return getDate(dateStr, DATAFORMAT_STR);
 	}


 	/**
 	 * 按照formatStr的格式，转化dateTimeStr为Date类型 dateTimeStr必须是formatStr的形式
 	 * @author WangZhuzhu
 	 * @param dateTimeStr 日期字符串 
 	 * @param formatStr 日期格式字符串 
 	 * @return Date
 	 * @throws ParseException 日期格式转化错误 
 	 */
 	public static Date getDate(String dateTimeStr, String formatStr) throws ParseException{
 			if (dateTimeStr == null || dateTimeStr.equals("")) {
 				return null;
 			}
 			DateFormat sdf = getDateFormat(formatStr);
 			java.util.Date d = sdf.parse(dateTimeStr);
 			return d;
 	}

 	/**
 	 * 将YYYYMMDD转换成Date日期
 	 * @author WangZhuzhu
 	 * @param date 日期
 	 * @return Date
 	 * @throws Exception 日期格式错误
 	 */
 	public static Date transferDate(String date) throws Exception {
 		if (date == null || date.length() < 1)
 			return null;

 		if (date.length() != 8)
 			throw new Exception("日期格式错误");
 		String con = "-";

 		String yyyy = date.substring(0, 4);
 		String mm = date.substring(4, 6);
 		String dd = date.substring(6, 8);

 		int month = Integer.parseInt(mm);
 		int day = Integer.parseInt(dd);
 		if (month < 1 || month > 12 || day < 1 || day > 31)
 			throw new Exception("日期格式错误");

 		String str = yyyy + con + mm + con + dd;
 		return DateUtil.getDate(str, DateUtil.DATAFORMAT_STR);
 	}

 	
    /////////////////////////////////////////////////////日期字符串格式化成日期字符串///////////////////////////////////////////////////////////////////////
 	/**
	 * 根据一个日期字符串，返回日期格式，目前支持4种 如果都不是，则返回null
	 * @author WangZhuzhu
	 * @param DateString 日期字符串
	 * @return String
	 */
	public static String getFormateStr(String DateString) {
		String patternStr1 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}"; // "yyyy-MM-dd"
		String patternStr2 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}"; // "yyyy-MM-dd HH:mm:ss";
		String patternStr3 = "[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日";// "yyyy年MM月dd日"
		String patternStr4 = "[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日[0-9]{1,2}时[0-9]{1,2}分[0-9]{1,2}秒";// "yyyy年MM月dd日HH时mm分ss秒"

		Pattern p = Pattern.compile(patternStr1);
		Matcher m = p.matcher(DateString);
		boolean b = m.matches();
		if (b)
			return DATAFORMAT_STR;
		p = Pattern.compile(patternStr2);
		m = p.matcher(DateString);
		b = m.matches();
		if (b)
			return DATATIMEF_STR;

		p = Pattern.compile(patternStr3);
		m = p.matcher(DateString);
		b = m.matches();
		if (b)
			return ZHCN_DATAFORMAT_STR;

		p = Pattern.compile(patternStr4);
		m = p.matcher(DateString);
		b = m.matches();
		if (b)
			return ZHCN_DATATIMEF_STR;
		return null;
	}

	/**
	 * 将yyyy-mm-dd日期字符串转换成yyyymmdd格式字符串
	 * @author WangZhuzhu
	 * @param date 日期
	 * @return String
	 * @throws ParseException  日期格式转化错误  
	 */
	public static String getYYYYMMDDDate(String date) throws ParseException {
		return getYYYYMMDDDate(getDate(date, DATAFORMAT_STR));
	}
 	
 	
 	/**
     * 将“yyyymmdd”格式的日期转化成“yyyy-mm-dd”格式
     * @author WangZhuzhu
     * @param strDate 日期字符串
     * @return String
     */
    public static String getStrHaveAcross(String strDate)
    {
        if(StringUtil.isEmpty(strDate)) {
        	return "";
        }   
    	return strDate.substring(0, 4) + "-" + strDate.substring(4, 6)
                    + "-" + strDate.substring(6, 8);
    }
 
	/**
	 * 返回一个yyyy-MM-dd HH:mm:ss 形式的日期时间字符串中的HH:mm:ss
	 * @author WangZhuzhu
	 * @param dateTime yyyy-MM-dd HH:mm:ss 形式的日期时间
	 * @return String
	 * @throws ParseException  日期格式转化错误  
	 */
	public static String getTimeString(String dateTime) throws ParseException {
		return getTimeString(dateTime, DATATIMEF_STR);
	}

	/**
	 * 返回一个formatStr格式的日期时间字符串中的HH:mm:ss
	 * @author WangZhuzhu
	 * @param dateTime  formatStr格式的日期时间字符串
	 * @param formatStr 日期格式字符串
	 * @return String
	 * @throws ParseException  日期格式转化错误  
	 */
	public static String getTimeString(String dateTime, String formatStr) throws ParseException {
		Date d = getDate(dateTime, formatStr);
		String s = dateToDateString(d);
		return s.substring(DATATIMEF_STR.indexOf('H'));
	}

	
	/**
	 * 将一个"yyyy-MM-dd HH:mm:ss"字符串，转换成"yyyy年MM月dd日HH时mm分ss秒"的字符串
	 * @author WangZhuzhu
	 * @param dateStr 日期字符串
	 * @return String
	 * @throws ParseException  日期格式转化错误  
	 */
	public static String getZhCNDateTime(String dateStr) throws ParseException {
		Date d = getDate(dateStr);
		return dateToDateString(d, ZHCN_DATATIMEF_STR);
	}

	/**
	 * 将一个"yyyy-MM-dd"字符串，转换成"yyyy年MM月dd日"的字符串
	 * @author WangZhuzhu
	 * @param dateStr 日期字符串
	 * @return String
	 * @throws ParseException 日期格式转化错误  
	 */
	public static String getZhCNDate(String dateStr) throws ParseException {
		Date d = getDate(dateStr, DATAFORMAT_STR);
		return dateToDateString(d, ZHCN_DATAFORMAT_STR);
	}

	/**
	 * 将dateStr从fmtFrom转换到fmtTo的格式
	 * @author WangZhuzhu
	 * @param dateStr 日期字符串
	 * @param fmtFrom 原日期格式
	 * @param fmtTo   需要 修改成的日期格式
	 * @return String
	 * @throws ParseException   日期格式转化错误 
	 */
	public static String getDateStr(String dateStr, String fmtFrom, String fmtTo) throws ParseException {
		Date d = getDate(dateStr, fmtFrom);
		return dateToDateString(d, fmtTo);
	}

	/**
	 * 日期中的年、月、日加上某个数值后的日期字符串
	 * @author WangZhuzhu
	 * @param field  需要加的字段 (1、标示  年 2、标示  月 3、标示  日)
	 * @param amount 加数量
	 * @return String
	 * @throws ParseException 日期格式转化错误 
	 */
	public static String addDate(int field, int amount) throws ParseException{
		int temp = 0;
		if (field == 1) {
			temp = Calendar.YEAR;
		}
		if (field == 2) {
			temp = Calendar.MONTH;
		}
		if (field == 3) {
			temp = Calendar.DATE;
		}

		String Time = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.add(temp, amount);
		Time = sdf.format(cal.getTime());
		return Time;

	}

	/**
	 * 自定义当前时间
	 * @author WangZhuzhu
	 * @param hour 小时
	 * @param minute 分钟
	 * @param second 秒
	 * @return Date
	 */
	public static Date getDate(int hour, int minute, int second) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.HOUR_OF_DAY, hour);
		a.set(Calendar.MINUTE, minute);
		a.set(Calendar.SECOND, second);
		Date date = a.getTime();

		return date;
	}
	
	
    public static void main(String[] args) {
    	
    	
    	//时间操作
    	
	}
    

}

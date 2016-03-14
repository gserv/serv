package serv.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @ClassName:MyDateUtils
 * @Description:日期操作类工具。
 * @Author:hankaibo
 * @date:2013-8-18
 * @UpdateUser:hankaibo
 * @UpdateDate:2013-8-18 上午10:36:19
 * @UpdateRemark:What is modified?
 */
public class DateHelper {
    private final static String DATE_FORMAT = "yyyy-MM-dd";
    private final static String DATE_FORMAT_CN = "yyyy年MM月dd日";
    private final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static String TIME_FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";
    private final static String MONTH_FORMAT = "yyyy-MM";
    private final static String DAY_FORMAT = "yyyyMMdd";
    
    static {

    	TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
    
    public static Date getThisWeekMonday() {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(getTodayZero());
    	cal.set(Calendar.DAY_OF_WEEK, 2);
        return cal.getTime();
    }
    
    /**
     * 本周N
     * @param n
     * @return
     */
    public static Date getThisWeek(int n) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(getThisWeekMonday());
    	cal.add(Calendar.DAY_OF_WEEK, n-1);
        return cal.getTime();
    }
    
    public static Date getLastWeek(int n) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(getThisWeek(n));
    	cal.add(Calendar.WEEK_OF_YEAR, -1);
        return cal.getTime();
    }
    
    public static Date getNextWeek(int n) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(getThisWeek(n));
    	cal.add(Calendar.WEEK_OF_YEAR, 1);
        return cal.getTime();
    }
    
    public static Date getTodayLast() {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(getTodayZero());
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }
    
    public static Date getTodayZero() {
    	 Calendar cal = Calendar.getInstance();
         cal.setTime(new Date());
         cal.set(Calendar.HOUR_OF_DAY, 0);
         cal.set(Calendar.MINUTE, 0);
         cal.set(Calendar.SECOND, 0);
         cal.set(Calendar.MILLISECOND, 0);
         return cal.getTime();
    }

    /**
     * @Title:getMonthFirstDay
     * @Description: 得到当前月的第一天.
     * @return
     * @return String
     */
    public static Date getMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTodayZero());
        cal.set(Calendar.DATE, 1);
        return cal.getTime();

    }

    /**
     * @Title:getMonthLastDay
     * @Description: 得到当前月最后一天
     * @return
     * @return String
     */
    public static Date getMonthLastDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTodayZero());
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    /**
     * @Title:getPreviousMonthFirst
     * @Description: 得到上个月的第一天
     * @return
     * @return String
     */
    public static Date getPreviousMonthFirst() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getMonthFirstDay());
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * @Title:getPreviousMonthEnd
     * @Description: 得到上个月最后一天
     * @return
     * @return String
     */
    public static Date getPreviousMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getMonthLastDay());
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * @Title:getNextMonthFirst
     * @Description: 得到下个月的第一天
     * @return
     * @return String
     */
    public static Date getNextMonthFirst() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getMonthFirstDay());
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    /**
     * @Title:getNextMonthEnd
     * @Description: 得到下个月最后一天。
     * @return
     * @return String
     */
    public static Date getNextMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getMonthLastDay());
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    /**
     * @Title:getCurrentMonthDays
     * @Description: 得到当前月的天数
     * @return
     * @return int
     */
    public static int getCurrentMonthDays() {
        Calendar cal = new GregorianCalendar();// Calendar.getInstance();
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days;
    }

    /**
     * @Title:getSpecifiedMonthDays
     * @Description: 得到指定的月份的天数
     * @param date
     * @return
     * @return int
     */
    public static int getSpecifiedMonthDays(String date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(DateUtils.parseDate(date, MONTH_FORMAT));
            int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            return days;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return 0;
    }

    /**
     * @Title:getCurrentDate
     * @Description: 得到当前日期
     * @return
     * @return String
     */
    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        String currentDate = DateFormatUtils.format(cal, DATE_FORMAT);
        return currentDate;
    }

    /**
     * @Title:getCurrentTime
     * @Description: 得到当前的时间
     * @return
     * @return String
     */
    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        String currentDate = DateFormatUtils.format(cal, TIME_FORMAT);
        return currentDate;
    }

    /**
     * @Title:getOffsetDate
     * @Description: 得到与当前日期偏移量为X的日期。
     * @param offset
     * @return
     * @return String
     */
    public static String getOffsetDate(int offset) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, offset);
        String currentDate = DateFormatUtils.format(cal, DATE_FORMAT);
        return currentDate;
    }

    /**
     * @Title:getSpecifiedOffsetDate
     * @Description: 得到与指定日期偏移量为X的日期。
     * @param specifiedDate指定的日期
     *            ,格式为YYYY-MM-DD
     * @param offset
     * @return 返回yyyy-MM-dd格式的字符串日期
     * @return String
     * @throws ParseException
     */
    public static String getSpecifiedOffsetDate(String specifiedDate, int offset) throws ParseException {
        Date date = DateUtils.parseDate(specifiedDate, DATE_FORMAT);
        Calendar cal = DateUtils.toCalendar(date);
        cal.add(Calendar.DAY_OF_MONTH, offset);
        String returnDate = DateFormatUtils.format(cal, DATE_FORMAT);
        return returnDate;
    }

    /**
     * @Title:getSpecifiedOffsetTime
     * @Description: 得到与指定日期时间偏移量为X的时间。
     * @param specifiedTime
     *            指定的时间,格式为yyyy-MM-dd HH:mm:ss
     * @param offset
     *            偏移天数
     * @return 返回yyyy-MM-dd HH:mm:ss格式的字符串时间
     * @throws ParseException
     * @return String
     */
    public static String getSpecifiedOffsetTime(String specifiedTime, int offset) throws ParseException {
        Date date = DateUtils.parseDate(specifiedTime, TIME_FORMAT);
        Calendar cal = DateUtils.toCalendar(date);
        cal.add(Calendar.DAY_OF_MONTH, offset);
        String returnDate = DateFormatUtils.format(cal, TIME_FORMAT);
        return returnDate;
    }

    /**
     * @Title:getOffsetDateTime
     * @Description: 得到与指定日期时间偏移量为X的时间。
     * @param specifiedDateTime
     *            指定的时间,格式为yyyy-MM-dd HH:mm:ss/yyyy-MM-dd
     * @param offset
     *            偏移天数
     * @return
     * @throws ParseException
     * @return String
     */
    public static String getOffsetDateTime(String specifiedDateTime, int offset) throws ParseException {
        String regexStr = "\\d{4}-\\d{2}-\\d{2}";
        if (specifiedDateTime.matches(regexStr)) {
            return getSpecifiedOffsetDate(specifiedDateTime, offset);
        } else {
            return getSpecifiedOffsetTime(specifiedDateTime, offset);
        }
    }

    /**
     * 判断是否为润年
     * 
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * @Title:getWeekDay
     * @Description: 判断是星期几.
     * @param c
     * @return
     * @return String
     */
    public static String getWeekDay(Calendar c) {
        if (c == null) {
            return "星期一";
        }
        switch (c.get(Calendar.DAY_OF_WEEK)) {
        case Calendar.MONDAY:
            return "星期一";
        case Calendar.TUESDAY:
            return "星期二";
        case Calendar.WEDNESDAY:
            return "星期三";
        case Calendar.THURSDAY:
            return "星期四";
        case Calendar.FRIDAY:
            return "星期五";
        case Calendar.SATURDAY:
            return "星期六";
        default:
            return "星期日";
        }
    }

    /**
     * @Title:getDaysListBetweenDates
     * @Description: 获得两个日期之间的连续日期.
     * @param begin
     *            开始日期 .
     * @param end
     *            结束日期 .
     * @return
     * @return List<String>
     */
    public static List<String> getDaysListBetweenDates(String begin, String end) {
        List<String> dateList = new ArrayList<String>();
        Date d1;
        Date d2;
        try {
            d1 = DateUtils.parseDate(begin, DATE_FORMAT);
            d2 = DateUtils.parseDate(end, DATE_FORMAT);
            if (d1.compareTo(d2) > 0) {
                return dateList;
            }
            do {
                dateList.add(DateFormatUtils.format(d1, DATE_FORMAT));
                d1 = DateUtils.addDays(d1, 1);
            } while (d1.compareTo(d2) <= 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateList;
    }

    /**
     * @Title:getMonthsListBetweenDates
     * @Description: 获得连续的月份
     * @param begin
     * @param end
     * @return
     * @return List<String>
     */
    public static List<String> getMonthsListBetweenDates(String begin, String end) {
        List<String> dateList = new ArrayList<String>();
        Date d1;
        Date d2;
        try {
            d1 = DateUtils.parseDate(begin, DATE_FORMAT);
            d2 = DateUtils.parseDate(end, DATE_FORMAT);
            if (d1.compareTo(d2) > 0) {
                return dateList;
            }
            do {
                dateList.add(DateFormatUtils.format(d1, MONTH_FORMAT));
                d1 = DateUtils.addMonths(d1, 1);
            } while (d1.compareTo(d2) <= 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateList;
    }

    /**
     * @Title:long2Time
     * @Description: 将long类型的时间值转换成标准格式的时间（yyyy-MM-dd HH:mm:ss）
     * @param createTime
     * @return
     * @return String
     */
    public static String long2Time(String createTime) {
        long fooTime = Long.parseLong(createTime) * 1000L;
        return DateFormatUtils.format(fooTime, TIME_FORMAT);
    }

    public static void main(String[] args) throws ParseException {
//        System.out.println(getMonthFirstDay());
//        System.out.println(getMonthLastDay());
//        System.out.println(getPreviousMonthFirst());
//        System.out.println(getPreviousMonthEnd());
//        System.out.println(getNextMonthFirst());
//        System.out.println(getNextMonthEnd());
//        System.out.println(getCurrentMonthDays());
//        System.out.println(getSpecifiedMonthDays("1900-02"));
//        System.out.println(getCurrentDate());
//        System.out.println(getOffsetDate(-4));
//        System.out.println(isLeapYear(1900));
//        System.out.println(getWeekDay(Calendar.getInstance()));
//        System.out.println(getDaysListBetweenDates("2012-1-12", "2012-1-21"));
//        System.out.println(getMonthsListBetweenDates("2012-1-12", "2012-3-21"));
//        System.out.println(getSpecifiedOffsetTime("2012-09-09 12:12:12", 12));
//        System.out.println(getOffsetDateTime("2012-09-09", 12));
//        System.out.println(getOffsetDateTime("2012-09-09 12:12:12", 12));
//        System.out.println(long2Time("1234567890"));
//        System.out.println(getTodayZero());
//        System.out.println(getTodayLast());
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		System.out.println("getTodayZero : "+format.format(getTodayZero()));
    		System.out.println("getTodayLast : "+format.format(getTodayLast()));
    		System.out.println("getMonthFirstDay : "+format.format(getMonthFirstDay()));
    		System.out.println("getMonthLastDay : "+format.format(getMonthLastDay()));
    		System.out.println("getPreviousMonthFirst : "+format.format(getPreviousMonthFirst()));
    		System.out.println("getPreviousMonthEnd : "+format.format(getPreviousMonthEnd()));
    		System.out.println("getNextMonthFirst : "+format.format(getNextMonthFirst()));
    		System.out.println("getNextMonthEnd : "+format.format(getNextMonthEnd()));
    		System.out.println("getThisWeekMonday : "+format.format(getThisWeekMonday()));
    		System.out.println("getLastWeek 2 : "+format.format(getLastWeek(2)));
    		System.out.println("getNextWeek 3 : "+format.format(getNextWeek(3)));
    }
    
    
    
    
    
    
}













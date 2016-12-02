package com.gosuncn.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author HWJ
 */
public class DateUtil {

    public final static String FORMAT_YEAR = "yyyy";
    public final static String FORMAT_MONTH_DAY = "MM月dd日";

    public final static String FORMAT_DATE = "yyyy-MM-dd";
    public final static String FORMAT_TIME = "HH:mm";
    public final static String FORMAT_MONTH_DAY_TIME = "MM月dd日  hh:mm";

    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_DATE1_TIME = "yyyy/MM/dd HH:mm:ss";
    public final static String FORMAT_DATE_TIME_SECOND = "yyyy/MM/dd HH:mm:ss";

    private static SimpleDateFormat sdf = new SimpleDateFormat();
    private static final int YEAR = 365 * 24 * 60 * 60;// 年
    private static final int MONTH = 30 * 24 * 60 * 60;// 月
    private static final int DAY = 24 * 60 * 60;// 天
    private static final int HOUR = 60 * 60;// 小时
    private static final int MINUTE = 60;// 分钟

    /**
     * 根据时间戳获取描述性时间，如3分钟前，1天前
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 时间字符串
     */
    public static String getDescriptionTimeFromTimestamp(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
        System.out.println("timeGap: " + timeGap);
        String timeStr = "";
        if (timeGap > YEAR) {
            timeStr = timeGap / YEAR + "年前";
        } else if (timeGap > MONTH) {
            timeStr = timeGap / MONTH + "个月前";
        } else if (timeGap > DAY) {// 1天以上
            timeStr = timeGap / DAY + "天前";
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = timeGap / HOUR + "小时前";
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    /**
     * 根据时间字符串获取描述性时间，如3分钟前，1天前
     *
     * @param dateStr "yyyy-MM-dd HH:mm:ss"
     * @return 时间字符串
     */
    public static String getDescriptionTimeFromTimestamp(String dateStr) {

        long timestamp = dateStrToTimestamp(dateStr);
        if (timestamp != 0) {
            return getDescriptionTimeFromTimestamp(timestamp);
        }
        return "";
    }

    /**
     * 根据时间获取描述性时间，如3分钟前，1天前
     *
     * @param date 日期
     * @return 时间字符串
     */
    public static String getDescriptionTimeFromTimestamp(Date date) {

        long timestamp = dateToTimestamp(date);
        if (timestamp != 0) {
            return getDescriptionTimeFromTimestamp(timestamp);
        }
        return "";
    }


    /**
     * 根据时间戳获取描述性时间，如刚刚，x分钟前，昨天，月-日，年-月-日
     *
     * @param timestamp
     * @return
     */
    public static String getDescriptionTimeFromTimestamp2(long timestamp) {

        if (timestamp == 0) {
            return "";
        }

        Date date = new Date(timestamp);
        int ret = judgeCurrentDate(date);
        if (ret == 2) {
            return "昨天";
        }
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
        System.out.println("timeGap: " + timeGap);
        String timeStr = "";
        if (timeGap > YEAR) {
            timeStr = timestampToDateStr(timestamp, "yyyy-MM-dd");
        } else if (timeGap > MONTH) {
            timeStr = timestampToDateStr(timestamp, "MM-dd");
        } else if (timeGap > DAY) {// 1天以上,显示日期
            timeStr = timestampToDateStr(timestamp, "MM-dd");
        } else if (timeGap > HOUR) {// 1小时-24小时,显示格式为HH:mm
            timeStr = timestampToDateStr(timestamp, "HH:mm");
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    /**
     * 根据时间获取描述性时间，如刚刚，x分钟前，昨天，月-日，月-日-年
     *
     * @param date
     * @return
     */
    public static String getDescriptionTimeFromTimestamp2(Date date) {
        if (date != null) {
            long timestamp = date.getTime();
            return getDescriptionTimeFromTimestamp2(timestamp);
        }
        return "";
    }

    /**
     * 根据时间字符串获取描述性时间，如刚刚，x分钟前，昨天，月-日，月-日-年
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDescriptionTimeFromTimestamp2( String dateStr) {
        long timestamp = dateStrToTimestamp(dateStr);
        if (timestamp != 0) {
            return getDescriptionTimeFromTimestamp2(timestamp);
        }
        return "";

    }

    /**
     * 时间戳转换为字符串(yyyy-MM-dd HH:mm:ss)
     *
     * @param timestamp
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String timestampToDateStr(long timestamp) {
        return timestampToDateStr(timestamp, FORMAT_DATE_TIME);
    }

    /**
     * 时间戳转换自定义格式日期
     *
     * @param timestamp
     * @param pattern   日期格式
     * @return
     */
    public static String timestampToDateStr(long timestamp,  String pattern) {
        String sd = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sd = sdf.format(new Date(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sd;
    }

    /**
     * 将字符串转为时间戳
     *
     * @param dateStr "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static long dateStrToTimestamp(String dateStr) {
        return dateStrToTimestamp(dateStr, FORMAT_DATE_TIME);
    }

    /**
     * 将字符串转为时间戳
     *
     * @param dateStr
     * @param format  日期格式
     * @return
     */
    public static long dateStrToTimestamp(String dateStr, String format) {
        long re_time = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date d = sdf.parse(dateStr);
            re_time = d.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re_time;
    }


    /**
     * 将日期转为时间戳
     *
     * @param date
     * @return
     */
    public static long dateToTimestamp(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return 0;
    }


    /**
     * date类型转换为定义格式日期字符串
     *
     * @param data
     * @param pattern
     * @return
     */
    public static String dateToString(Date data, String pattern) {

        String str = "";
        try {
            str = new SimpleDateFormat(pattern).format(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * date类型转换为标准格式日期字符串
     *
     * @param data
     * @return
     */
    public static String dateToString(Date data) {
        return dateToString(data, FORMAT_DATE_TIME);
    }

    /**
     * string类型转换为date类型
     *
     * @param dateStr dateStr的时间格式必须要与pattern的时间格式相同
     * @param pattern
     * @return
     */
    public static Date stringToDate(String dateStr, String pattern) {
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            date = formatter.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 标准string类型转换为date类型
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date stringToDate(String dateStr) {

        return stringToDate(dateStr, FORMAT_DATE_TIME);
    }


    /**
     * string类型转换为时间戳
     *
     * @param dateStr dateStr的时间格式和pattern的时间格式必须相同
     * @param pattern
     * @return
     */
    public static long stringToTimestamp(String dateStr, String pattern) {
        long timestamp = 0;
        try {
            Date date = stringToDate(dateStr, pattern);
            timestamp = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 标准string类型转换为时间戳
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long stringToTimestamp(String dateStr) {
        return stringToTimestamp(dateStr, FORMAT_DATE_TIME);
    }


    /**
     * 将时间戳转为“yy-MM-dd HH:mm”格式字符串
     *
     * @param time
     * @return
     */
    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 将时间戳转为“HH:mm”格式字符串
     *
     * @param time
     * @return
     */
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 获取时间的相对性
     *
     * @param timesamp 毫秒
     * @return -1 昨天 0 今天 1 明天 ...
     */
    public static int getDetailTime(long timesamp) {
        int temp = Integer.MAX_VALUE;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            Date today = new Date(System.currentTimeMillis());
            Date otherDay = new Date(timesamp);
            temp = Integer.parseInt(sdf.format(otherDay)) - Integer.parseInt(sdf.format(today));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return temp;
    }

    /**
     * 判断日期是今天还是昨天
     *
     * @param time yyyy-MM-dd HH:mm:ss
     * @return 0-格式错误 1-今天 2-昨天 3-日期
     */
    public static int judgeCurrentDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        if (time == null || "".equals(time)) {
            return 0;
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;

        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance(); // 今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        // Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance(); // 昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH,
                current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return 1;
        } else if (current.before(today) && current.after(yesterday)) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * 判断日期是今天还是昨天
     *
     * @param date
     * @return 0-格式错误 1-今天 2-昨天 3-日期
     */
    public static int judgeCurrentDate(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance(); // 今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        // Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance(); // 昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH,
                current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return 1;
        } else if (current.before(today) && current.after(yesterday)) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * 获得日期的下一天
     *
     * @param date
     * @return
     */
    public static Date getNextDay(Date date) {// 接收输入的日期inputDate
        return getSpecifyDay(date, 1);
    }


    /**
     * 获得日期的前一天
     *
     * @param date
     * @return
     */
    public static Date getForeDay(Date date) {// 接收输入的日期inputDate

        return getSpecifyDay(date, -1);
    }

    /**
     * 获得日期的第几天
     *
     * @param date
     * @param num  正数表示后几天 负数表示前几天
     * @return
     */
    public static Date getSpecifyDay(Date date, int num) {// 接收输入的日期inputDate
        if (date == null) {
            return null;
        }
        Calendar nextDate = Calendar.getInstance();
        nextDate.setTime(date);
        nextDate.add(Calendar.DAY_OF_MONTH, num);
        return nextDate.getTime();
    }


    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        return getWeek(date, new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"});
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return -1--未知 0--周日 1--周一 ...
     */
    public static int getWeekNum(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return index;
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @param weeks 日期描述字符串，第一个为周日
     * @return
     */
    public static String getWeek(Date date, String[] weeks) {
        String week = "";

        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int index = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (index >= 0) {
                week = weeks[index];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return week;
    }

    /**
     * 获得指定日期与当前日期相差的天数
     *
     * @param day1
     * @return
     */
    public static int getBetweenDays(Date day1) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            day1 = sf.parse(sf.format(day1));

            Calendar c = Calendar.getInstance();
            Date day2 = sf.parse(sf.format(c.getTime()));

            c.setTime(day1);
            long d1 = c.getTimeInMillis();

            c.setTime(day2);
            long d2 = c.getTimeInMillis();

            long between = (d1 - d2) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;


    }

    /**
     * 获得两个日期之间的天数
     *
     * @param day1
     * @param day2
     * @return 负数表示day1比较新 正数表示day2比较新
     * @throws ParseException
     */
    public static int getBetweenDays(Date day1, Date day2) throws ParseException {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            day1 = sf.parse(sf.format(day1));
            day2 = sf.parse(sf.format(day2));

            Calendar c = Calendar.getInstance();
            c.setTime(day1);
            long d1 = c.getTimeInMillis();
            c.setTime(day2);
            long d2 = c.getTimeInMillis();

            int between = (int) ((d2 - d1) / (1000 * 3600 * 24));

            return between;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断日期是否在当天
     *
     * @param dateStr yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static boolean isCurrentDay(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(dateStr);
            Date current = sdf.parse(sdf.format(new Date()));
            int flag = date.compareTo(current);
            if (flag == 0) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 判断日期是否在当天
     *
     * @param date yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static boolean isCurrentDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(sdf.format(date));
            Date current = sdf.parse(sdf.format(new Date()));
            int flag = date.compareTo(current);
            if (flag == 0) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 获得日期所在的月份
     *
     * @param date
     * @return 月份
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 判断是否在当月
     *
     * @param date
     * @return
     */
    public static boolean isCurrentMonth(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int m1 = c1.get(Calendar.MONTH);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        int m2 = c2.get(Calendar.MONTH);
        if (m1 == m2) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否在当月
     *
     * @param dateStr
     * @return 月份
     */
    public static boolean isCurrentMonth(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c1 = Calendar.getInstance();
            c1.setTime(sdf.parse(dateStr));
            int m1 = c1.get(Calendar.MONTH);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(new Date());
            int m2 = c2.get(Calendar.MONTH);
            if (m1 == m2) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

}
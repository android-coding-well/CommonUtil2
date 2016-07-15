package com.gosuncn.core.utils;

import java.text.DateFormat;
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
        String timeStr = null;
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
     * 根据时间戳获取描述性时间，如刚刚，x分钟前，昨天，月-日，月-日-年
     *
     * @param timestamp
     * @return
     */
    public static String getDescriptionTimeFromTimestamp2(long timestamp) {

        Date date = longToDate(timestamp, FORMAT_DATE_TIME);
        int ret = judgeCurrentDateIsYesterday(date);
        if (ret == 2) {
            return "昨天";
        }
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
        System.out.println("timeGap: " + timeGap);
        String timeStr = null;
        if (timeGap > YEAR) {
            //timeStr = timeGap / YEAR + "年前";
            timeStr = longToString(timestamp, "MM-dd-yyyy");
        } else if (timeGap > MONTH) {
            //timeStr = timeGap / MONTH + "个月前";
            timeStr = longToString(timestamp, "MM-dd");
        } else if (timeGap > DAY) {// 1天以上,显示日期
            //timeStr = timeGap / DAY + "天前";
            timeStr = longToString(timestamp, "MM-dd");
        } else if (timeGap > HOUR) {// 1小时-24小时,显示格式为HH:mm
            //timeStr = timeGap / HOUR + "小时前";
            timeStr = longToString(timestamp, FORMAT_TIME);
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    /**
     * 根据时间戳获取描述性时间，如刚刚，x分钟前，昨天，月-日，月-日-年
     *
     * @param date
     * @return
     */
    public static String getDescriptionTimeFromTimestamp2(Date date) {
        long timestamp = date.getTime();
        return getDescriptionTimeFromTimestamp2(timestamp);
    }

    /**
     * 根据时间戳获取描述性时间，如刚刚，x分钟前，昨天，月-日，月-日-年
     *
     * @param dateStr ""
     * @return
     */
    public static String getDescriptionTimeFromTimestamp2(String dateStr) {
        Date date = stringToDate(dateStr, FORMAT_DATE_TIME);
        if (date != null) {
            long timestamp = date.getTime();
            return getDescriptionTimeFromTimestamp2(timestamp);
        }
        return "";

    }

    /**
     * 时间戳转换为date(yyyy-MM-dd HH:mm:ss)
     *
     * @param timestamp
     * @return
     */
    public static String timestampToDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(timestamp));
        return sd;
    }

    /**
     * 自定义格式时间戳转换
     *
     * @param timestamp
     * @return
     */
    public static String timestampToDate(long timestamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String sd = sdf.format(new Date(timestamp));
        return sd;
    }

    /**
     * 自定义格式时间戳转换
     *
     * @param beginDate
     * @return
     */
    public static String timestampToDate(String beginDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String sd = sdf.format(new Date(Long.parseLong(beginDate)));
        return sd;
    }

    /**
     * 把日期转为字符串
     *
     * @param date
     * @param format "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    @Deprecated
    public static String ConverToString(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }


    /**
     * 把字符串转为日期
     *
     * @param strDate
     * @return
     * @throws Exception
     */
    @Deprecated
    public static Date ConverToDate(String strDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.parse(strDate);
    }


    /**
     * 将字符串转为时间戳
     *
     * @param date "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static long dateToTimestamp(String date) {
        long re_time = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(date);
            re_time = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }


    /**
     * date类型转换为String类型
     *
     * @param data
     * @param formatType
     * @return
     */
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * long类型转换为String类型
     *
     * @param currentTime 要转换的long类型的时间
     * @param formatType
     * @return
     */
    public static String longToString(long currentTime, String formatType) {
        String strTime = "";
        Date date = longToDate(currentTime, formatType);// long类型转成Date类型
        strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    /**
     * string类型转换为date类型
     *
     * @param strTime    strTime的时间格式必须要与formatType的时间格式相同
     * @param formatType
     * @return
     */
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /**
     * long转换为Date类型
     *
     * @param currentTime 要转换的long类型的时间
     * @param formatType
     * @return
     */
    public static Date longToDate(long currentTime, String formatType) {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    /**
     * string类型转换为long类型
     *
     * @param strTime    strTime的时间格式和formatType的时间格式必须相同
     * @param formatType
     * @return
     */
    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    /**
     * date类型转换为long类型
     *
     * @param date
     * @return
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }


    /**
     * 获取当前日期的指定格式的字符串
     *
     * @param format 指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM:ss"
     * @return
     */
    public static String getCurrentTime(String format) {
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE_TIME);
        } else {
            sdf.applyPattern(format);
        }
        return sdf.format(new Date());
    }

    /**
     * 获得指定日期与当前日期相差的天数
     *
     * @param day1
     * @param isOld 指定日期是否早于当前日期
     * @return
     * @throws ParseException
     */
    public static int getBetweenDays(Date day1, boolean isOld) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        day1 = sf.parse(sf.format(day1));

        Calendar c = Calendar.getInstance();
        Date day2 = sf.parse(sf.format(c.getTime()));

        c.setTime(day1);
        long d1 = c.getTimeInMillis();

        c.setTime(day2);
        long d2 = c.getTimeInMillis();
        long between;
        if (isOld) {
            between = (d2 - d1) / (1000 * 3600 * 24);
        } else {
            between = (d1 - d2) / (1000 * 3600 * 24);
        }
        return Integer.parseInt(String.valueOf(between));
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
     * 获取时间的相对性（今天，昨天，前天）
     *
     * @param timesamp 毫秒
     * @return
     */
    public static String getDetailTime(long timesamp) {
        long clearTime = timesamp;
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(clearTime);
        int temp = Integer.parseInt(sdf.format(today))
                - Integer.parseInt(sdf.format(otherDay));

        switch (temp) {
            case 0:
                result = "今天 " + getHourAndMin(clearTime);
                break;
            case 1:
                result = "昨天 " + getHourAndMin(clearTime);
                break;
            case 2:
                result = "前天 " + getHourAndMin(clearTime);
                break;

            default:
                result = getTime(clearTime);
                break;
        }

        return result;
    }

    /**
     * 判断日期是今天还是昨天
     *
     * @param time yyyy-MM-dd HH:mm:ss
     * @return 0-格式错误 1-今天 2-昨天 3-日期
     */
    public static int judgeCurrentDateIsYesterday(String time) {
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
    public static int judgeCurrentDateIsYesterday(Date date) {
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
     * @param inputDate
     * @return
     */
    public static String getNextDayStr(Date inputDate) {// 接收输入的日期inputDate
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar nextDate = Calendar.getInstance();
        nextDate.setTime(inputDate);
        nextDate.add(Calendar.DAY_OF_MONTH, 1);

        String dateStr = sdf.format(nextDate.getTime()); // 把日期格式化
        return dateStr;
    }

    public static Date getNextDay(Date inputDate) {// 接收输入的日期inputDate
        Calendar nextDate = Calendar.getInstance();
        nextDate.setTime(inputDate);
        nextDate.add(Calendar.DAY_OF_MONTH, 1);
        return nextDate.getTime();
    }

    /**
     * 获得日期的前一天
     *
     * @param inputDate
     * @return
     */
    public static String getForeDayStr(Date inputDate) {// 接收输入的日期inputDate
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar nextDate = Calendar.getInstance();
        nextDate.setTime(inputDate);
        nextDate.add(Calendar.DAY_OF_MONTH, -1);

        String dateStr = sdf.format(nextDate.getTime()); // 把日期格式化
        return dateStr;
    }

    /**
     * 获得日期的前一天
     *
     * @param inputDate
     * @return
     */
    public static Date getForeDay(Date inputDate) {// 接收输入的日期inputDate
        Calendar nextDate = Calendar.getInstance();
        nextDate.setTime(inputDate);
        nextDate.add(Calendar.DAY_OF_MONTH, -1);
        return nextDate.getTime();
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (index < 0) {
            index = 0;
        }
        return weeks[index];

    }

    /**
     * 获得指定日期与当前日期相差的天数
     *
     * @param day1
     * @return
     * @throws ParseException
     */
    public static int getBetweenDays(Date day1) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        day1 = sf.parse(sf.format(day1));

        Calendar c = Calendar.getInstance();
        Date day2 = sf.parse(sf.format(c.getTime()));

        c.setTime(day1);
        long d1 = c.getTimeInMillis();

        c.setTime(day2);
        long d2 = c.getTimeInMillis();

        long between = (d2 - d1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between));
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
            // TODO Auto-generated catch block
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
     * 获得日期所在的月份
     *
     * @param date
     * @return 月份
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
     * 获得日期所在的月份
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

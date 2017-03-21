package com.waixing.utils.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * 时间处理工具类
 *
 * @author eric
 * @version v1.0.3
 * @since v1.0.0
 */
public class TimeUtil {

    public static final String yyyy_MMM_dd = "yyyy-MM-dd";
    public static final String yyyy_MMM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将毫秒时间转化为特定格式时间
     * <p>
     * 替换为{@link co.yonglang.util.time.TimeUtil#formatDateString(long, String)}
     *
     * @param time
     * @param pattern 如 "yyyy-MM-dd"
     * @return
     */
    @Deprecated
    public static String forDateStrig(long time, String pattern) {
        return formatDateString(time, pattern);
    }

    /**
     * 将毫秒时间转化为特定格式时间
     *
     * @param time    要格式化的时间
     * @param pattern 如 "yyyy-MM-dd"
     * @return 格式化结果
     */
    public static String formatDate(Object time, String pattern) {
        if (time == null)
            return null;
        else {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(time);
        }
    }

    /**
     * 将毫秒值表示的时间转换为指定格式的字符串
     * <code>
     * yyyy-MM-dd HH:mm         0
     * yyyy年MM月dd日 HH:mm:ss   1
     * yyyy-MM-dd HH:mm:ss      2
     * yyyy-MM-dd               3
     * </code>
     *
     * @param time 时间的毫秒值
     * @param type 要转换的样式
     * @return 转换结果
     */
    public static String formatDate(long time, int type) {
        String timeStr;
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MMM_dd_HH_mm_ss);
        if (type == 1) {
            sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        } else if (type == 2) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (type == 3) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else if (type == 0) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
        timeStr = sdf.format(time);
        return timeStr;
    }

    /**
     * 将毫秒时间转化为特定格式时间
     *
     * @param time
     * @param pattern 如 "yyyy-MM-dd"
     * @return
     */
    public static String formatDateString(long time, String pattern) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(time);
    }

    /**
     * 获取指定日期的当月第一天
     */
    public static long getFirstDayOfMonth(long time) {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MMM_dd);
            String today_time = sdf.format(calendar.getTime());
            return sdf.parse(today_time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 获取指定日期的下一个月第一天
     */
    public static long getFirstDayOfNextMonth(long time) {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MMM_dd);
            String today_time = sdf.format(calendar.getTime());
            return sdf.parse(today_time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 将特定格式的时间转化为毫秒（如：time：201608291011，type：yyyyMMddHHmm，注意：time与type格式应一致！）
     *
     * @return
     */
    public static long getLongTime(String time, String type) {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            SimpleDateFormat sdf = new SimpleDateFormat(type);
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 获取上一天的00:00:00的毫秒值
     */
    public static long getOneDayAgoTime(long time) {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            calendar.add(Calendar.DATE, -1);
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MMM_dd);
            String today_time = sdf.format(calendar.getTime());
            return sdf.parse(today_time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 获取7天前00:00:00的毫秒值
     *
     * @return 7天前00:00:00的毫秒值
     */
    public static long getSevenDayAgoTime() {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -7);
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MMM_dd);
            String today_time = sdf.format(calendar.getTime());
            return sdf.parse(today_time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 获取7天前00:00:00的毫秒值
     *
     * @return 7天前00:00:00的毫秒值
     */
    public static long getSomedayAgoTime(int dayCount) {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1 * dayCount);
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MMM_dd);
            String today_time = sdf.format(calendar.getTime());
            return sdf.parse(today_time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 获取今日00:00:00的毫秒值
     *
     * @return 今日00:00:00的毫秒值
     */
    public static long getTodayTime() {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MMM_dd);
            String today_time = sdf.format(calendar.getTime());
            return sdf.parse(today_time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 获取明天00:00:00的毫秒值
     *
     * @return 明天00:00:00的毫秒值
     */
    public static long getTomorrowTime() {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1);
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MMM_dd);
            String today_time = sdf.format(calendar.getTime());
            return sdf.parse(today_time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 获取昨天00:00:00的毫秒值
     *
     * @return 昨天00:00:00的毫秒值
     */
    public static long getYesterdayTime() {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MMM_dd);
            String today_time = sdf.format(calendar.getTime());
            return sdf.parse(today_time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 判断两个时间是否是同一天
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 判断结果：true：是同一天，false：不是同一天
     */
    public static boolean isSameDay(long time1, long time2) {
        if (time1 <= 0 || time2 <= 0) {
            return false;
        }

        try {
            String date1 = formatDateString(time1, "yyyy-MM-dd");
            String date2 = formatDateString(time2, "yyyy-MM-dd");
            return date1.equals(date2);
        } catch (Exception e) {
            return false;
        }
    }
}

package com.waixing.utils.number;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/* *
 *类名：UtilDate
 *功能：自定义订单类
 *详细：工具类，可以用作获取系统日期、订单编号等
 *版本：3.3
 *日期：2012-08-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class DateUtil {

    /**
     * 年月日时分秒(无下划线) yyyyMMddHHmmss
     */
    public static final String dtVeryLong = "yyyyMMddHHmmssSSS";

    /**
     * 年月日时分秒(无下划线) yyyyMMddHHmmss
     */
    public static final String dtLong = "yyyyMMddHHmmss";

    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss
     */
    public static final String simple = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年月日(无下划线) yyyyMMdd
     */
    public static final String dtShort = "yyyyMMdd";

    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     *
     * @return 以yyyyMMddHHmmssSSS为格式的当前系统时间
     */
    public static String getOrderNum() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(dtVeryLong);
        return df.format(date) + getThree();
    }

    /**
     * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getDateFormatter() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(simple);
        return df.format(date);
    }

    /**
     * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
     *
     * @return
     */
    public static String getDate() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(dtShort);
        return df.format(date);
    }

    /**
     * 根据毫秒值获取时间(精确到天)，格式：yyyyMMdd
     *
     * @return
     */
    public static String getDate(long time) {
        Date date = new Date(time);
        DateFormat df = new SimpleDateFormat(dtShort);
        return df.format(date);
    }

    /**
     * 产生随机的三位数
     *
     * @return
     */
    public static String getThree() {
        Random rad = new Random();
        int random = rad.nextInt(1000);

        if (random < 10) {
            return "00" + random;
        } else if (random < 100) {
            return "0" + random;
        } else {
            return "" + random;
        }
    }

    /**
     * 将时间字符串转换为毫秒值
     *
     * @param timeStr 以yyyyMMddHHmmss为格式的时间
     * @return
     */
    public static long parseDtLong(String timeStr) {
        SimpleDateFormat df = new SimpleDateFormat(dtLong);
        try {
            return df.parse(timeStr).getTime();
        } catch (ParseException e) {
            return 0l;
        }
    }

    /**
     * 将时间字符串转换为毫秒值
     *
     * @param timeStr 以yyyy-MM-dd HH:mm:ss为格式的时间
     * @return
     */
    public static long parseSimple(String timeStr) {
        SimpleDateFormat df = new SimpleDateFormat(simple);
        try {
            return df.parse(timeStr).getTime();
        } catch (ParseException e) {
            return 0l;
        }
    }

    /**
     * @return : String
     * @方法名: fomatDate
     * @描述: yyyy年MM月dd日 HH:mm:ss   1
     * yyyy-MM-dd HH:mm:ss       2
     * yyyy-MM-dd                3
     * @创建时间: 2015-9-21下午5:18:57
     */
    public static Date fomatDate(long time, int type) {
        String timeStr = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (type == 1) {
            sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        } else if (type == 2) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (type == 3) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        timeStr = sdf.format(time);
        try {
            return sdf.parse(timeStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return : String
     * @方法名: fomatDate
     * @描述: yyyy年MM月dd日 HH:mm:ss   1
     * yyyy-MM-dd HH:mm:ss       2
     * yyyy-MM-dd                3
     * @创建时间: 2015-9-21下午5:18:57
     */
    public static String fomatDateStrig(long time, int type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (type == 1) {
            sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        } else if (type == 2) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (type == 3) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        return sdf.format(time);
    }

    /**
     * 当前时间加n天
     *
     * @return
     */
    public static long newExpireTiem(Date d, String units, int n) {
        //Date d = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        if ("d".equals(units))
            now.set(Calendar.DATE, now.get(Calendar.DATE) - n);
        else if ("m".equals(units))
            now.set(Calendar.MONTH, now.get(Calendar.MONTH) - n);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long temp = 0;
        try {
            temp = sdf.parse(sdf.format(now.getTime())).getTime() + (24 * 3600 - 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public static long MonthFirest() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        long first = 0;
        try {
            first = format.parse(format.format(c.getTime())).getTime() + (24 * 3600 - 1);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return first;
    }
}

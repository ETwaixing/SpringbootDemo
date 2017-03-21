package com.waixing.utils.text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * 文本工具类
 *
 * @author eric
 * @version v1.0.0
 * @since v1.0.0
 */
public class TextUtil {
    private static Random random = new Random();
    private static String TABLE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String converFromUnicode(String str) {
        StringBuilder sb = new StringBuilder();
        int i;
        int pos = 0;

        while ((i = str.indexOf("\\u", pos)) != -1) {
            sb.append(str.substring(pos, i));
            if (i + 5 < str.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(str.substring(i + 2, i + 6), 16));
            }
        }

        while (pos < str.length()) {
            sb.append(str.charAt(pos));
            pos++;
        }

        return sb.toString();
    }

    /**
     * 中文转unicode编码
     *
     * @param str
     * @return
     */
    public static String converToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            String temp = "";
            int strInt = str.charAt(i);
            if (strInt > 127) {
                temp += "\\u" + Integer.toHexString(strInt);
            } else {
                temp = String.valueOf(str.charAt(i));
            }
            result += temp;
        }
        return result;
    }

    /**
     * 转义特殊字符
     *
     * @param keyword 要处理的字符串
     * @return
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (!isEmpty(keyword)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    /**
     * @return : String
     * @方法名: getMd5
     * @描述: MD5加密
     * @创建时间: 2015-7-28下午8:11:28
     */
    public static String getMd5(String originString) {
        String result = "";
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(originString.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (byte aB : b) {
                i = aB;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取指定长度的随机字符串
     *
     * @param len 随机字符串的长度
     * @return
     */
    public static String getRandomString(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(TABLE.charAt(random.nextInt(TABLE.length())));
        }
        return builder.toString();
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 判断字符串是否为空值
     *
     * @param str 需要判断的字符串
     * @return true:空，false:非空
     */
    @Deprecated
    private static boolean isEmpty(String str) {
        return null == str || "".equals(str.trim()) || "null".equals(str);
    }

    /**
     * 判断一个对象是否为空，如果该对象是集合，判断集合内是否有元素
     *
     * @param obj 要判断的对象
     * @return true:空，false:非空
     */
    private static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        }

        if (obj instanceof String) {
            String str = (String) obj;
            return "".equals(str.trim()) || "null".equals(str);
        }
        return obj instanceof Collection && ((Collection) obj).isEmpty();
    }

    /**
     * 判断字符串是否为空值
     *
     * @param strs 需要判断的字符串
     * @return true:空，false:非空
     */
    public static boolean isEmpty(String... strs) {
        for (String string : strs) {
            if (isEmpty(string)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否为空值
     *
     * @param objs 对象集合
     * @return true:空，false:非空
     */
    public static boolean isEmpty(Object... objs) {
        for (Object obj : objs) {
            if (isEmpty(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return : String
     * @方法名: toString
     * @描述: 将set集合中的字符串转成：xxx|xxx 格式
     * @创建时间: 2015-7-28下午8:21:58
     */
    @Deprecated
    public static String setToString(Set<String> set) {
        StringBuffer sb = new StringBuffer();
        for (String value : set) {
            sb.append(value);
            sb.append("|");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}

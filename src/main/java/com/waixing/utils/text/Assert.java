package com.waixing.utils.text;

/**
 * 断言工具类
 * <p>
 *
 * @author eric
 * @version v1.0.0
 * @since v1.0.0
 */
public class Assert {
    /**
     * 断言字符串为空值
     *
     * @param str
     * @return
     */
    public static void isEmptyStr(String str, String message) {
        if (null != str && !"".equals(str)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言字符串为空值
     *
     * @param str
     * @return
     */
    public static void isEmptyStr(String str) {
        isEmptyStr(str, "[Assertion failed] - the String argument must be empty");
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    /**
     * 断言字符串不为空值
     *
     * @param str
     * @return
     */
    private static void notEmptyStr(String str) {
        if (null == str || "".equals(str)) {
            String message = "[Assertion failed] - this argument is required; it must not be empty";
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言字符串都不为空值
     *
     * @param str
     * @return
     */
    public static void notEmptyStr(String... strs) {
        for (String str : strs) {
            notEmptyStr(str);
        }
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }
}

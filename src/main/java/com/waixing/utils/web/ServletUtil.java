package com.waixing.utils.web;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet相关工具类
 * <p>
 * Created by eric on 16/12/19.
 */
public class ServletUtil {

    /**
     * 获取发起请求的ip地址
     *
     * @param request 请求对象
     * @return ip地址
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

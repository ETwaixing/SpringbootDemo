package com.waixing.utils.net;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * http请求工具类
 *
 * @author eric
 * @version v1.0.0
 * @since v1.0.0
 */
public class HTTPUtil {
    // post请求头信息
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_X_WWW_FORM = "application/x-www-form-urlencoded";
    public static final String ACCEPT_ALL = "application/json,text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
    private static final Logger logger = LogManager.getLogger(HTTPUtil.class);

    private static void buildParams(Map<String, Object> params, StringBuffer buffer) {
        for (Entry<String, Object> e : params.entrySet()) {
            String key = e.getKey();
            Object value = e.getValue();
            if (value instanceof Collection) {
                buffer.append("&").append(key).append("=");
                ((Collection) value).forEach(str -> {
                    buffer.append(str.toString()).append(",");
                });
                buffer.deleteCharAt(buffer.length() - 1);
            } else {
                buffer.append("&").append(key).append("=").append(value);
            }
        }
    }

    /**
     * get请求
     *
     * @param urlStr get请求url（不带？）
     * @param params get请求参数
     * @return get请求返回数据
     * @throws IOException
     */

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String doGet(String urlStr, Map<String, Object> params) throws IOException {
        long begin = System.currentTimeMillis();
        StringBuffer buffer = new StringBuffer();
        buffer.append("?");
        buildParams(params, buffer);

        return doGet(urlStr + buffer.deleteCharAt(1).toString());
    }

    /**
     * get请求
     *
     * @param urlStr 请求连接
     * @return get请求返回的数据
     * @throws IOException
     */
    public static String doGet(String urlStr) throws IOException {
        long begin = System.currentTimeMillis();
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        StringBuffer sb = new StringBuffer();
        if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = null;
            while (null != (line = reader.readLine())) {
                sb.append(line);
            }
            reader.close();
        }
        long end = System.currentTimeMillis();
        logger.debug(MessageFormat.format("url:{0}, result:{1},time:{2}ms", urlStr, sb.toString(), end - begin));
        return sb.toString();
    }

    /**
     * post请求
     *
     * @param urlStr post请求url
     * @param data   请求数据
     * @return post请求返回的数据
     * @throws IOException
     */
    public static String doPost(String urlStr, String data, String accept, String contentType) throws IOException {
        long begin = System.currentTimeMillis();
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("Accept", accept); // 设置接收数据的格式
        conn.setRequestProperty("Content-Type", contentType); // 设置发送数据的格式
        conn.setRequestProperty("Content-Length", "" + data.length());
        conn.connect();

        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8"); // utf-8编码
        out.append(data);
        out.flush();
        out.close();

        StringBuffer sb = new StringBuffer();
        if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = null;
            while (null != (line = reader.readLine())) {
                sb.append(line);
            }
        }
        long end = System.currentTimeMillis();
        logger.debug(MessageFormat.format("url:{0}, data:{1}, result:{2},time:{3}ms", urlStr, data, sb.toString(), end - begin));
        return sb.toString();
    }

    /**
     * post请求
     *
     * @param urlStr post请求url
     * @param params post请求参数
     * @return post请求返回的数据
     * @throws IOException
     */

    public static String doPost(String urlStr, Map<String, Object> params) throws IOException {
        return doPost(urlStr, params, APPLICATION_JSON, APPLICATION_X_WWW_FORM);
    }

    /**
     * post请求
     *
     * @param urlStr post请求url
     * @param params post请求参数
     * @return post请求返回的数据
     * @throws IOException
     */

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String doPost(String urlStr, Map<String, Object> params, String accept, String contentType) throws IOException {
        long begin = System.currentTimeMillis();
        StringBuffer data = new StringBuffer();
        buildParams(params, data);

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("Accept", accept); // 设置接收数据的格式
        conn.setRequestProperty("Content-Type", contentType); // 设置发送数据的格式
        conn.setRequestProperty("Content-Length", "" + (data.length() - 1));
        conn.connect();

        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8"); // utf-8编码
        out.append(data.deleteCharAt(0));
        out.flush();
        out.close();

        StringBuffer sb = new StringBuffer();
        if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while (null != (line = reader.readLine())) {
                sb.append(line);
            }
        }
        long end = System.currentTimeMillis();
        logger.debug(MessageFormat.format("url:{0}, data:{1}, result:{2},time:{3}ms", urlStr, data, sb.toString(), end - begin));
        return sb.toString();
    }

    /**
     * post请求
     *
     * @param urlStr post请求url
     * @param data   请求数据
     * @return post请求返回的数据
     * @throws IOException
     */
    public static String doPost(String urlStr, String data) throws IOException {
        return doPost(urlStr, data, APPLICATION_JSON, APPLICATION_X_WWW_FORM);
    }
}

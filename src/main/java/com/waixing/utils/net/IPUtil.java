package com.waixing.utils.net;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.waixing.utils.text.TextUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.waixing.utils.net.HTTPUtil.doGet;

/**
 * IP地址相关工具
 * <p>
 * Created by eric on 16/10/20.
 *
 * @author eric
 * @version v1.0.0
 * @since v1.0.0
 */
public class IPUtil {
    private static final Logger logger = LogManager.getLogger(IPUtil.class);
    private static final Gson GSON = new Gson();

    /**
     * 自建地址，返回格式：183.14.250.193
     */
    private static final String ipServer1 = "http://game.wowodx.com/ipsearch/ip.json";

    /**
     * 搜狐提供，返回格式：var returnCitySN = {"cip": "183.14.250.193", "cid": "440300",
     * "cname": "广东省深圳市"};
     */
    private static final String ipServer2 = "http://pv.sohu.com/cityjson?ie=utf-8";

    /**
     * 太平洋电脑网，返回格式：if(window.IPCallBack)
     * {IPCallBack({"ip":"183.37.27.77","pro":"广东省","proCode":"440000","city":
     * "深圳市","cityCode":"440300","region":"","regionCode":"0","addr":"广东省深圳市 电信"
     * ,"regionNames":"","err":""});}
     */
    private static final String ipServer3 = "http://whois.pconline.com.cn/ipJson.jsp";

    /**
     * 1616，返回格式 var WData = ['183.37.27.77','娣卞湷','shenzhen','骞夸笢']; if
     * (typeof(WData_callback) != 'undefined') { WData_callback();}
     */
    private static final String ipServer4 = "http://w.1616.net/chaxun/iptolocal.php";

    /**
     * 新浪提供,返回格式: {"ret":1,"start":-1,"end":-1,"country":"\u4e2d\u56fd","province":"\u56db\u5ddd",
     * "city":"\u96c5\u5b89","district":"","isp":"","type":"","desc":""}
     */
    private static final String ipSearchServer1 = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";

    /**
     * 淘宝提供,返回格式: {"code":0,"data":{"country":"\u4e2d\u56fd","country_id":"CN","area":"\u534e\u5317",
     * "area_id":"100000","region":"\u5317\u4eac\u5e02","region_id":"110000","city":"\u5317\u4eac\u5e02",
     * "city_id":"110100","county":"","county_id":"-1","isp":"\u8054\u901a","isp_id":"100026","ip":"123.123.123.123"}}
     */
    private static final String ipSearchServer2 = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    private static String IP = getIp();

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getIP() {
        return IP;
    }

    /**
     * 获取本机外网ip地址
     *
     * @return
     */
    private static String getIp() {
        String result;
        try {
            result = getIpFromServer1();
            if (TextUtil.isEmpty(result)) {
                result = getIpFromServer2();
                if (TextUtil.isEmpty(result)) {
                    result = getIpFromServer3();
                    if (TextUtil.isEmpty(result)) {
                        result = getIpFromServer4();
                    }
                }
            }

            if (!TextUtil.isEmpty(result) && result.contains(".")) {
                return result;
            }
        } catch (Exception e) {
            logger.error("获取ip地址失败!", e);
        }
        return null;
    }

    /**
     * 自建查询，返回格式：183.37.27.77
     *
     * @return
     */
    private static String getIpFromServer1() {
        try {
            String result = doGet(ipServer1);
            if (!TextUtil.isEmpty(result) && result.contains(".")) {
                return result;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 太平洋电脑网：if(window.IPCallBack)
     * {IPCallBack({"ip":"183.37.27.77","pro":"广东省","proCode":"440000","city":
     * "深圳市","cityCode":"440300","region":"","regionCode":"0","addr":"广东省深圳市 电信"
     * ,"regionNames":"","err":""});}
     *
     * @return
     */
    private static String getIpFromServer2() {
        try {
            String result = doGet(ipServer2);
            if (!TextUtil.isEmpty(result)) {
                String ipJson = result.substring(result.indexOf("{"), result.indexOf("}") + 1);
                JsonObject jsonObject = GSON.fromJson(ipJson, JsonObject.class);
                return jsonObject.get("cip").getAsString();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 太平洋电脑网：if(window.IPCallBack)
     * {IPCallBack({"ip":"183.37.27.77","pro":"广东省","proCode":"440000","city":
     * "深圳市","cityCode":"440300","region":"","regionCode":"0","addr":"广东省深圳市 电信"
     * ,"regionNames":"","err":""});}
     *
     * @return
     */
    private static String getIpFromServer3() {
        try {
            String result = doGet(ipServer3);
            if (!TextUtil.isEmpty(result)) {
                String ipJson = result.substring(result.indexOf("({") + 1, result.indexOf("}") + 1);
                JsonObject jsonObject = GSON.fromJson(ipJson, JsonObject.class);
                return null == jsonObject.get("ip") ? null : jsonObject.get("ip").getAsString();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 1616，返回格式 var WData = ['183.37.27.77','娣卞湷','shenzhen','骞夸笢']; if
     * (typeof(WData_callback) != 'undefined') { WData_callback();}
     *
     * @return
     */
    private static String getIpFromServer4() {
        try {
            String result = doGet(ipServer4);
            if (!TextUtil.isEmpty(result)) {
                String ipArray = result.substring(result.indexOf("[") + 1, result.indexOf("]"));
                String[] strings = ipArray.split(",");
                return strings[0].substring(1, strings[0].length() - 1);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 获取IP信息
     *
     * @param ip ip地址
     * @return ip信息
     */
    public static IPInfo getIpInfo(String ip) {
        try {
            IPInfo ipInfo = getIpInfoFromServer1(ip);
            if (null == ipInfo) {
                ipInfo = getIpInfoFromServer2(ip);
            }
            return ipInfo;
        } catch (Exception e) {
            logger.error("获取ip地址失败!", e);
        }
        return null;
    }

    /**
     * 从新浪获取iP地址信息
     *
     * @param ip ip
     * @return ip地址信息
     */
    private static IPInfo getIpInfoFromServer1(String ip) {
        String url = ipSearchServer1 + ip;
        try {
            String httpResult = doGet(url);
            if (!TextUtil.isEmpty(httpResult)) {
                JsonObject data = GSON.fromJson(httpResult, JsonObject.class);
                IPInfo ipInfo = new IPInfo();
                ipInfo.country = TextUtil.converFromUnicode(data.get("country").getAsString());
                ipInfo.province = TextUtil.converFromUnicode(data.get("province").getAsString());
                ipInfo.city = TextUtil.converFromUnicode(data.get("city").getAsString());
                return ipInfo;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.debug("从新浪获取ip信息失败!");
        }
        return null;
    }

    /**
     * 从淘宝获取iP地址信息
     *
     * @param ip ip
     * @return ip地址信息
     */
    private static IPInfo getIpInfoFromServer2(String ip) {
        String url = ipSearchServer2 + ip;
        try {
            String httpResult = doGet(url);
            if (!TextUtil.isEmpty(httpResult)) {
                JsonObject result = GSON.fromJson(httpResult, JsonObject.class);
                if ("0".equals(result.get("code").getAsString())) {
                    JsonObject data = result.getAsJsonObject("data");
                    IPInfo ipInfo = new IPInfo();
                    ipInfo.country = TextUtil.converFromUnicode(data.get("country").getAsString());
                    ipInfo.province = TextUtil.converFromUnicode(data.get("region").getAsString());
                    ipInfo.city = TextUtil.converFromUnicode(data.get("city").getAsString());
                    return ipInfo;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.debug("从新浪获取ip信息失败!");
        }
        return null;
    }

    /**
     * IP地址信息
     */
    public static class IPInfo {
        /**
         * IP地址
         */
        private String ip;

        /**
         * IP地址所属的国家
         */
        private String country;

        /**
         * IP地址所属的省份
         */
        private String province;

        /**
         * IP地址所属的城市
         */
        private String city;

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        public String getIp() {
            return ip;
        }

        public String getProvince() {
            return province;
        }

        @Override
        public String toString() {
            return GSON.toJson(this);
        }
    }
}

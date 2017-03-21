package com.waixing.utils.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * 文件操作帮助类
 * 类名：FileUtil
 * 功能：下载网络图片并创建一个File对象，得到图片的二进制数据，以二进制封装得到数据，具有通用性
 * Created by yonglang on 2017/3/21.
 */
public class FileUtil {

    /**
     * 下载网络图片并创建一个File对象
     *
     * @param urlstr  图片路径
     * @param imgName 图片名称
     * @return File对象
     * @throws Exception
     */
    public static File getFileFromNet(String urlstr, String imgName) throws Exception {
        //new一个URL对象
        URL url = new URL(urlstr);
        //打开链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求方式为"GET"
        conn.setRequestMethod("GET");
        //超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = readInputStream(inStream);
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File(imgName);
        //创建输出流
        FileOutputStream outStream = new FileOutputStream(imageFile);
        //写入数据
        outStream.write(data);
        //关闭输出流
        outStream.close();
        return imageFile;
    }

    /**
     * 得到图片的二进制数据，以二进制封装得到数据，具有通用性
     *
     * @param inStream 输入流
     * @return 图片的二进制数据
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }
}

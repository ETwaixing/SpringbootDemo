package com.waixing.utils.security;

/**
 * 类名：XOREncryptUtil
 * 功能：异或加密解密
 * Created by yonglang on 2017/3/21.
 */
public class XOREncryptUtil {

    /**
     * 加密
     *
     * @param str             待加密的字符串
     * @param encryption_seed 密钥
     * @return 加密后的字符串
     */
    public static String encrypt(String str, String encryption_seed) {
        int[] snNum = new int[str.length()];
        String result = "";
        String temp = "";

        for (int i = 0, j = 0; i < str.length(); i++, j++) {
            if (j == encryption_seed.length())
                j = 0;
            snNum[i] = str.charAt(i) ^ encryption_seed.charAt(j);
        }

        for (int k = 0; k < str.length(); k++) {

            if (snNum[k] < 10) {
                temp = "00" + snNum[k];
            } else {
                if (snNum[k] < 100) {
                    temp = "0" + snNum[k];
                }
            }
            result += temp;
        }
        return result;
    }

    /**
     * 解密
     *
     * @param str             待解密的字符串
     * @param encryption_seed 密钥
     * @return 解密后的字符串
     */
    public static String decrypt(String str, String encryption_seed) {
        char[] snNum = new char[str.length() / 3];
        String result = "";

        for (int i = 0, j = 0; i < str.length() / 3; i++, j++) {
            if (j == encryption_seed.length())
                j = 0;
            int n = Integer.parseInt(str.substring(i * 3, i * 3 + 3));
            snNum[i] = (char) ((char) n ^ encryption_seed.charAt(j));
        }

        for (int k = 0; k < str.length() / 3; k++) {
            result += snNum[k];
        }
        return result;
    }
}

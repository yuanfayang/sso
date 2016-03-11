package com.changhong.sso.common.utils;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.common.utils
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/27 11:57
 * @discription : 字符串工具类
 */
public class StringUtil {

    /**
     * 生成UUID
     * @return
     */
    public static String generateUuidString() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        str = str.replaceAll("-", "");
        return str;
    }

    /**
     * 生成指定位数的随机数字符串
     */
    public static String getRandomStr(int size) {
        if (size <= 0) {
            return "";
        }
        Random ran = new Random();
        StringBuffer numStr = new StringBuffer();
        for (int i = 0; i < size; i++) {
            int p = ran.nextInt(10);
            numStr.append(p);
        }
        return numStr.toString();
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}

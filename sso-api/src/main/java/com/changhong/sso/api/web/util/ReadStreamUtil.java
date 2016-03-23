package com.changhong.sso.api.web.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.api.web.util
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/3/19 14:47
 * @discription : 读取流的工具类
 */
public class ReadStreamUtil {

    /**
     * 读取流
     * @param inStream
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }
}

package com.changhong.sso.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.util
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/3/20 12:58
 * @discription :用来读取项目common.properties文件中的信息
 */
public class ReadPropertiesUtils {

    private static String FILE_COMMON = "common.properties";

    /**
     * 读取配置
     * @param uri
     * @return
     */
    public static Properties loadSetting(String uri){
        Properties properties = new Properties();
        InputStream inputStream = ReadPropertiesUtils.class.getResourceAsStream(uri);
        if (inputStream != null){
            try {
                properties.load(inputStream);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            return null;
        }
        return properties;
    }

    /**
     * 根据key值从common.properties中读取对应的value值
     * @param key
     * @return
     */
    public static String read(String key){
        String valueStr = null;
        Properties properties = loadSetting(FILE_COMMON);
        if (properties != null){
            valueStr = properties.getProperty(key);
        }
        return valueStr;
    }
}

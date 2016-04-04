package com.changhong.sso.common.utils;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.common.utils
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/27 12:02
 * @discription :
 */
public class StringUtilTest extends TestCase {

    @Test
    public void testGenerateUuidString() throws Exception {
        String uuid = StringUtil.generateUuidString();

        System.out.println("###"+uuid);
        System.out.println("###1111"+uuid.length());
    }

    @Test
    public void testGetRandomStr() throws Exception {
        String key=StringUtil.getRandomStr(16);

        System.out.println("--->"+key);
    }

    @Test
    public void testGetRandomString() throws Exception {
        String key=StringUtil.getRandomString(16);

        System.out.println("--->"+key);
    }

    @Test
    public void testGenerateAppIdAndKey(){
        String appId=StringUtil.generateUuidString();

        String key=StringUtil.getRandomString(24);

        System.out.println("appid-->"+appId);
        System.out.println("key-->"+key);
    }
}
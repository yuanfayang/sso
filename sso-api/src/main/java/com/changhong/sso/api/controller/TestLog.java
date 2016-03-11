package com.changhong.sso.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Package : com.changhong.sso.api.controller
 * @Author : Fayang Yuan
 * @Email : fayang.yuan@changhong.com ,flyyuanfayang@sina.com
 * @Date : 16/3/11
 * @Time : 下午3:48
 * @Description :
 */
public class TestLog {
    private static final Logger logger= LoggerFactory.getLogger(TestLog.class);

    public static void main(String[] args){
        logger.info("测试info");
        logger.debug("测试debug");
        logger.trace("测试trace");
        logger.error("测试error");
    }
}

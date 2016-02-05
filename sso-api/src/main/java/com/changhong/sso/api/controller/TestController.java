package com.changhong.sso.api.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @authr: Fayang Yuan
 * @Date: 2016/2/5
 * @Time: 17:22
 * @Email: flyyuanfayang@sina.com
 * @Description: 测试框架的controller
 */

@Controller
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject test(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("name","yuan");
        jsonObject.accumulate("email","fayang.yuan@changhong.com");
        logger.info("欢迎：{}",jsonObject);
        return jsonObject;
    }
}

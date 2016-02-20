package com.changhong.sso.api.controller;

import com.changhong.sso.common.core.entity.SSOKey;
import com.changhong.sso.common.core.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.api.controller
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/20 10:33
 * @discription : 应用服务秘钥控制器
 */
@Controller
public class KeyController {

    @Autowired
    private KeyService keyService;

    /**
     * 根据应用的ID，查询对应的秘钥信息
     * @param appId 应用的ID
     * @return 对应的秘钥
     */
    @RequestMapping(value = "/fetchKey", method = RequestMethod.POST)
    @ResponseBody
    public SSOKey fetchKry(@RequestParam(value = "appId", required = true) String appId) {
        return this.keyService.findByAppId(appId);
    }
}

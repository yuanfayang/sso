package com.changhong.sso.api.controller;

import com.changhong.sso.common.core.authentication.EncryCredential;
import com.changhong.sso.common.core.authentication.EncryCredentialManager;
import com.changhong.sso.common.core.entity.EncryCredentialInfo;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Package : com.changhong.sso.api.controller
 * @Author : Fayang Yuan
 * @Email : fayang.yuan@changhong.com ,flyyuanfayang@sina.com
 * @Date : 16/3/31
 * @Time : 上午11:02
 * @Description : 用于应用服务解密的控制器
 */

@Controller
public class DecoderController {
    private Logger logger = LoggerFactory.getLogger(DecoderController.class);

    @Autowired
    private EncryCredentialManager encryCredentialManager;

    /**
     * 应用服务解密的接口
     *
     * @param clientEC 应用服务加密凭证
     * @return
     */
    @RequestMapping(value = "rest/decode", method = RequestMethod.POST)
    @ResponseBody
    public Object decode(@RequestParam(value = "clientEC", required = true) String clientEC) {
        logger.info("开始进行clientEC解密");

        if (StringUtils.isEmpty(clientEC)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EncryCredential encryCredential = new EncryCredential(clientEC);

        EncryCredentialInfo encryCredentialInfo = encryCredentialManager.decrypt(encryCredential);

        logger.info("解密之后的用户凭证:{}", JSONObject.fromObject(encryCredentialInfo));
        return encryCredentialInfo;
    }

}

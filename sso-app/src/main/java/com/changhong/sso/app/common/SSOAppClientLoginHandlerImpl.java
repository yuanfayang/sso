package com.changhong.sso.app.common;

import com.changhong.sso.client.handler.AppClientLoginHandler;
import com.changhong.sso.common.core.entity.EncryCredentialInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.app.common
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/20 13:54
 * @discription : 应用服务登录处理器
 */
public class SSOAppClientLoginHandlerImpl implements AppClientLoginHandler {
    private static final Logger logger = Logger.getLogger(SSOAppClientLoginHandlerImpl.class.getName());

    public static final String USER_KEY = "USER_KEY_SESSION";

    @Override
    public void loginClient(EncryCredentialInfo encryCredentialInfo, HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(USER_KEY, encryCredentialInfo);
        logger.info("the user id is " + encryCredentialInfo.getUserId() + " has logined in the app");
    }
}

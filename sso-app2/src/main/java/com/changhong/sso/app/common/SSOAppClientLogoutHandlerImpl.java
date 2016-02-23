package com.changhong.sso.app.common;

import com.changhong.sso.client.handler.AppClientLogoutHandler;
import com.changhong.sso.common.core.entity.EncryCredentialInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.app.common
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/20 14:01
 * @discription : 应用服务登出应用服务处理器
 */
public class SSOAppClientLogoutHandlerImpl implements AppClientLogoutHandler {
    private static final Logger logger = Logger.getLogger(SSOAppClientLoginHandlerImpl.class.getName());

    @Override
    public void logoutClient(HttpServletRequest request, HttpServletResponse response, String userId) {
        //若已经登录，则不做相关处理。
        if (request.getSession().getAttribute(SSOAppClientLoginHandlerImpl.USER_KEY) != null) {
            EncryCredentialInfo encryCredentialInfo = (EncryCredentialInfo) request.getSession().getAttribute(SSOAppClientLoginHandlerImpl.USER_KEY);
            request.getSession().setAttribute(SSOAppClientLoginHandlerImpl.USER_KEY, null);
            logger.info("the user id is " + encryCredentialInfo.getUserId() + " has logined out the app");
        }
    }
}

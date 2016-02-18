package com.changhong.sso.api.resolver;

import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.core.authentication.UsernamePasswordCredential;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.api.resolver
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/18 15:15
 * @discription : 用户名密码解析器，从参数中解析出用户名密码信息
 */
public class UsernamePasswordCredentialResolver extends AbstractParameterCredentialResolver {
    /**
     * 用户名参数名
     */
    public static final String USERNAME_PARAM_NAME = "username";

    /**
     * 密码参数名
     */
    public static final String PASSWORD_PARAM_NAME = "password";

    @Override
    protected Credential doResolveCredential(HttpServletRequest request) {
        if (request != null && request.getParameter(USERNAME_PARAM_NAME) != null
                && request.getParameter(PASSWORD_PARAM_NAME) != null) {
            UsernamePasswordCredential credential = new UsernamePasswordCredential();
            credential.setUsername(request.getParameter(USERNAME_PARAM_NAME));
            credential.setPassword(request.getParameter(PASSWORD_PARAM_NAME));
            return credential;
        }
        return null;
    }
}

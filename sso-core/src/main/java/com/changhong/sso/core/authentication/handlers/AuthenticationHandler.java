package com.changhong.sso.core.authentication.handlers;

import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.core.authentication.status.Authenticated;
import com.changhong.sso.exception.AuthenticationException;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/15
 * @Time: 9:07
 * @Email: flyyuanfayang@sina.com
 * @Description: 认证处理器类，该类检查用户的凭证是否合法
 */
public interface AuthenticationHandler {

    /**
     * 认证方法
     * @param credential 用户凭证
     * @return 是否通过认证，true：通过，false：未通过
     * @throws AuthenticationException
     */
    Authenticated authenticate(Credential credential) throws AuthenticationException;

    /**
     * 是否支持用户凭证credential的认证处理
     * @param credential 用户凭证
     * @return true：表示支持，false：不支持
     */
    boolean supports(Credential credential);
}

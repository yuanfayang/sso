package com.changhong.sso.core.authentication.handlers;

import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.exception.AuthenticationException;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/15
 * @Time: 9:28
 * @Email: flyyuanfayang@sina.com
 * @Description: 抽象的认证管理器实现，提供了具体实现类可以与认证之前和认证之后执行的一些任务
 */
public abstract class AbstractPreAndPostProcessingAuthenticationHandler implements AuthenticationHandler {

    /**
     * 认证前置处理
     * @param credential
     * @return true：认证继续，false：跳出
     */
    protected boolean preAuthenticate(final Credential credential){
        return true;
    }

    /**
     * Method to execute after authentication occurs.
     *
     * @param credentials
     *            the supplied credentials
     * @param authenticated
     *            the result of the authentication attempt.
     * @return true if the handler should return true, false otherwise.
     */
    protected boolean postAuthenticate(final Credential credential,
                                       final boolean authenticated) {
        return authenticated;
    }

    public final boolean authenticate(final Credential credential)
            throws AuthenticationException {

        if (!preAuthenticate(credential)) {
            return false;
        }

        final boolean authenticated = doAuthentication(credential);

        return postAuthenticate(credential, authenticated);
    }


    /**
     * 执行真正的认证方法。
     * @param credential 用户凭据。
     * @return 认证结果。
     * @throws AuthenticationException
     */
    protected abstract boolean doAuthentication(final Credential credential)
            throws AuthenticationException;
}

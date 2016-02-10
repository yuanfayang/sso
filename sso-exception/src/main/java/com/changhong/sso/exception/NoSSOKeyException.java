package com.changhong.sso.exception;

import java.io.Serializable;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/10
 * @Time: 15:39
 * @Email: flyyuanfayang@sina.com
 * @Description: 没有SSO的秘钥异常信息类
 */
public class NoSSOKeyException extends AuthenticationException implements Serializable {
    private static final long serialVersionUID = -4627867115783087252L;

    public static final NoSSOKeyException INSTANCE = new NoSSOKeyException();

    private NoSSOKeyException() {
        super(ExceptionConstants.NO_SSOKEY_CODE, ExceptionConstants.NO_SSOKEY_MSGKEY);
    }
}

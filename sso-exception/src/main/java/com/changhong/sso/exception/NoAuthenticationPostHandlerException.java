package com.changhong.sso.exception;

import java.io.Serializable;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/10
 * @Time: 15:32
 * @Email: flyyuanfayang@sina.com
 * @Description: 无认证处理器异常
 */
public class NoAuthenticationPostHandlerException extends AuthenticationException implements Serializable {
    private static final long serialVersionUID = -8650115044489480168L;

    public static final NoAuthenticationPostHandlerException INSTANCE = new NoAuthenticationPostHandlerException();

    private NoAuthenticationPostHandlerException() {
        super(ExceptionConstants.NO_AUTHENTICATIONPOSTHANDLER_CODE, ExceptionConstants.NO_AUTHENTICATIONPOSTHANDLER_MSGKEY);
    }
}

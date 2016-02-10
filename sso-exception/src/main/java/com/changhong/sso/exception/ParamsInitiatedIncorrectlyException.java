package com.changhong.sso.exception;

import java.io.Serializable;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/10
 * @Time: 15:49
 * @Email: flyyuanfayang@sina.com
 * @Description: 参数初始化异常
 */
public class ParamsInitiatedIncorrectlyException extends AuthenticationException implements Serializable {
    private static final long serialVersionUID = 8217919966818905167L;
    public static final ParamsInitiatedIncorrectlyException INSTANCE = new ParamsInitiatedIncorrectlyException();

    private ParamsInitiatedIncorrectlyException() {
        super(ExceptionConstants.PARAMS_INITIALTED_INCORRECTLY_CODE, ExceptionConstants.PARAMS_INITIALTED_INCORRECTLY_MSGKEY);
    }
}

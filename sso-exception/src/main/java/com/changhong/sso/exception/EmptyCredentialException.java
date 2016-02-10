package com.changhong.sso.exception;

import java.io.Serializable;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/10
 * @Time: 14:40
 * @Email: flyyuanfayang@sina.com
 * @Description: 用户凭证为空异常类
 */
public class EmptyCredentialException extends InvalidCrendentialException implements Serializable {
    private static final long serialVersionUID = 4930193006365350879L;
    public static final EmptyCredentialException INSTANCE = new EmptyCredentialException();

    private static final String code = ExceptionConstants.EMPTY_CREDENTIAL_CODE;

    private static final String msgKey = ExceptionConstants.EMPTY_CREDENTIAL_MSGKEY;

    private EmptyCredentialException() {
        super();
        this.setCode(code);
        this.setMsgKey(msgKey);
    }
}

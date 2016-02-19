package com.changhong.sso.exception;

import java.io.Serializable;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/10
 * @Time: 14:26
 * @Email: flyyuanfayang@sina.com
 * @Description: 用户凭证不合法异常类
 */
public class InvalidCrendentialException extends AuthenticationException implements Serializable {
    public static final InvalidCrendentialException INSTANCE = new InvalidCrendentialException();

    private static final long serialVersionUID = -7412304361440375974L;

    private static final String code = ExceptionConstants.INVALID_CREDENTIAL_CODE;
    private static final String msgKey = ExceptionConstants.INVALID_CREDENTIAL_MSGKEY;

    public InvalidCrendentialException(String code, String msgKey) {
        super(code, msgKey);
    }

    InvalidCrendentialException() {
        super(code, msgKey);
    }
}

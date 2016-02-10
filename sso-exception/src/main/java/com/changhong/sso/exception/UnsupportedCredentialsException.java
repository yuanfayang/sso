package com.changhong.sso.exception;

import java.io.Serializable;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/10
 * @Time: 15:58
 * @Email: flyyuanfayang@sina.com
 * @Description: 不支持的身份凭证异常类
 */
public class UnsupportedCredentialsException extends InvalidCrendentialException implements Serializable {
    private static final long serialVersionUID = -8089117567480597076L;

    UnsupportedCredentialsException INSTANCE = new UnsupportedCredentialsException();

    private UnsupportedCredentialsException() {
        super();
        this.setCode(ExceptionConstants.UNSUPPORTED_CREDENTIALS_CODE);
        this.setMsgKey(ExceptionConstants.UNSUPPORTED_CREDENTIALS_MSGKEY);
    }
}

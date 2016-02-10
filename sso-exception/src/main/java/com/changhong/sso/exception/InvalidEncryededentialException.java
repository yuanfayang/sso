package com.changhong.sso.exception;

import java.io.Serializable;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/10
 * @Time: 15:14
 * @Email: flyyuanfayang@sina.com
 * @Description: 不合法的已认证凭证异常信息类
 */
public class InvalidEncryededentialException extends InvalidCrendentialException implements Serializable {
    private static final long serialVersionUID = 2431798530147572522L;

    public static final InvalidEncryededentialException INSTANCE = new InvalidEncryededentialException();

    private InvalidEncryededentialException() {
        super();
        this.setCode(ExceptionConstants.INVALID_ENCRYCREDENTIAL_CODE);
        this.setMsgKey(ExceptionConstants.INVALID_ENCRYCREDENTIAL_MSGKEY);
    }
}

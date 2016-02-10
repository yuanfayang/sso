package com.changhong.sso.exception;

import java.io.Serializable;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/10
 * @Time: 15:54
 * @Email: flyyuanfayang@sina.com
 * @Description: 密码不合法异常信息类
 */
public class PasswordInvalidException extends InvalidCrendentialException implements Serializable {
    private static final long serialVersionUID = -2649383493424722684L;

    private PasswordInvalidException() {
        super();
        this.setCode(ExceptionConstants.PASSWORD_INVALID_CODE);
        this.setMsgKey(ExceptionConstants.PASSWORD_INVALID_MSGKEY);
    }
}

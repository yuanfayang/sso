package com.changhong.sso.exception;

import java.io.Serializable;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/10
 * @Time: 16:03
 * @Email: flyyuanfayang@sina.com
 * @Description: 用户名不合法异常类
 */
public class UsernameInvalidException extends InvalidCrendentialException implements Serializable {
    private static final long serialVersionUID = 3092167279322347220L;

    public static final UsernameInvalidException INSTANCE = new UsernameInvalidException();

    private UsernameInvalidException() {
        super();
        this.setCode(ExceptionConstants.INVALID_USERNAME_CODE);
        this.setMsgKey(ExceptionConstants.INVALID_USERNAME_MSGKEY);
    }
}

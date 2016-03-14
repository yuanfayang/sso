package com.changhong.sso.exception;

import java.io.Serializable;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/10
 * @Time: 16:12
 * @Email: flyyuanfayang@sina.com
 * @Description: 用户名或密码不合法异常类
 */
public class UsernameOrPasswordInvalidException extends InvalidCrendentialException implements Serializable {
    private static final long serialVersionUID = 1766217268219479798L;
    public static final UsernameOrPasswordInvalidException INSTANCE = new UsernameOrPasswordInvalidException();

    private UsernameOrPasswordInvalidException() {
        super();
        this.setCode(ExceptionConstants.INVALID_USERNAMEORPASSWORD_CODE);
        this.setMsgKey(ExceptionConstants.INVALID_USERNAMEORPASSWORD_MSGKEY);
    }
}

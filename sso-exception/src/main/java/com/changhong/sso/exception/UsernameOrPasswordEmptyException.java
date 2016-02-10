package com.changhong.sso.exception;

import java.io.Serializable;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/10
 * @Time: 16:06
 * @Email: flyyuanfayang@sina.com
 * @Description: 用户名或密码为空异常类
 */
public class UsernameOrPasswordEmptyException extends InvalidCrendentialException implements Serializable {
    private static final long serialVersionUID = 6973570593532478261L;

    public static final UsernameOrPasswordEmptyException INSTANCE = new UsernameOrPasswordEmptyException();

    private UsernameOrPasswordEmptyException() {
        super();
        this.setCode(ExceptionConstants.EMPTY_USERNAMEORPASSWORD_CODE);
        this.setMsgKey(ExceptionConstants.EMPTY_USERNAMEORPASSWORD_MSGKEY);
    }
}

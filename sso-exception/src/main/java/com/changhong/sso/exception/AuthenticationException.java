package com.changhong.sso.exception;

import java.io.Serializable;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/10
 * @Time: 14:06
 * @Email: flyyuanfayang@sina.com
 * @Description: 认证异常基础类，定义根级异常
 */
public class AuthenticationException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 4727396665562496690L;
    //异常码
    private String code;
    //异常关键的详细信息
    private String msgKey;

    public AuthenticationException(String code, String msgKey) {
        super();
        this.code = code;
        this.msgKey = msgKey;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }
}

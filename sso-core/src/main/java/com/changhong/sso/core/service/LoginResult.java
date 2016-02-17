package com.changhong.sso.core.service;

import com.changhong.sso.core.authentication.Authentication;

import java.io.Serializable;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.service
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 14:55
 * @discription : 登录结果对象
 */
public class LoginResult implements Serializable {
    private static final long serialVersionUID = -2860330503169595688L;

    /**
     * 是否登录成功
     */
    private boolean success;

    /**
     * 认证失败的错误代码
     */
    private String code;

    /**
     * 认证错误的提示信息
     */
    private String msgKey;

    /**
     * 认证结果信息对象
     */
    private Authentication authentication;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}

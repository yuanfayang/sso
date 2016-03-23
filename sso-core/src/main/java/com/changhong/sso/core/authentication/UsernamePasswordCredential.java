package com.changhong.sso.core.authentication;

import com.changhong.sso.common.core.authentication.AbstractParameters;
import com.changhong.sso.common.core.authentication.Credential;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/14
 * @Time: 10:46
 * @Email: flyyuanfayang@sina.com
 * @Description: 用户密码和密码形式未经过认证的原始凭证信息
 */
public class UsernamePasswordCredential extends AbstractParameters implements Credential {

    /**
     * 用户登录名
     */
    private String username;

    /**
     * 用户登录密码
     */
    private String password;

    /**
     * 用户在用户体系中的token
     */
    private String token;

    public UsernamePasswordCredential() {
    }

    public UsernamePasswordCredential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean isOriginal() {
        return true;
    }
}

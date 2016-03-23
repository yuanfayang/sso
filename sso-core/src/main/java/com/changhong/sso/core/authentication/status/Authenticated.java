package com.changhong.sso.core.authentication.status;

import com.changhong.sso.common.core.entity.User;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.authentication.status
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/3/20 13:18
 * @discription : 用户登录状态，结果状态
 */
public class Authenticated {
    /**
     * 是否认证成功
     */
    private boolean authenticated;

    /**
     * 用户访问的token，从用户系统中登录是获得
     */
    private String token;

    /**
     * 用户信息
     */
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }


}

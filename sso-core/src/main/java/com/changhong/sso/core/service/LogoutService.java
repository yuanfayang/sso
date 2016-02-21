package com.changhong.sso.core.service;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/21
 * @Time: 17:50
 * @Email: flyyuanfayang@sina.com
 * @Description: 统一登出接口，该接口主要负责服务端统一登出sso登录的所有业务应用。
 */
public interface LogoutService {
    /**
     * 统一退出所有登录的业务应用接口。
     *
     * @param userId  用户ID.
     * @param servcie 登出之后要跳转的URL地址，要同步登出该URL对应的应用。
     */
    void logout(String userId, String servcie);
}

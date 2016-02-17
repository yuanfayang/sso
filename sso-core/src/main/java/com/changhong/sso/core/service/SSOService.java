package com.changhong.sso.core.service;

import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.core.entity.App;

import java.util.List;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.service
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 14:58
 * @discription : 核心服务接口，定义所有的核心方法
 *                  此为SSO统一入口，定义了SSO对位的核心方法
 */
public interface SSOService {

    /**
     * 使用用户凭证登录SSO认证中心
     * @param credential 用户凭证
     * @return 登录结果
     */
    LoginResult login(Credential credential);

    /**
     * 用户登出ＳＳＯ认证中心
     * @param credential 用户凭证
     */
    void logout(Credential credential);

    /**
     * 获取某个用户凭证登录的应用列表
     * @param credential 用户凭证
     * @return 该用户登录的应用列表
     */
    List<App> getAppList(Credential credential);
}

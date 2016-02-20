package com.changhong.sso.core.authentication;

import com.changhong.sso.common.core.entity.App;

import java.util.Date;
import java.util.Map;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/14
 * @Time: 10:40
 * @Email: flyyuanfayang@sina.com
 * @Description: 用户认证结果
 */
public interface Authentication {

    Map<String, Object> getAttributes();

    Date getAuthenticationDate();

    Principal getPrincipal();

    App getApp();
}

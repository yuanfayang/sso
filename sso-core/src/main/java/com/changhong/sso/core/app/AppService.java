package com.changhong.sso.core.app;

import com.changhong.sso.common.core.entity.App;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/12
 * @Time: 20:57
 * @Email: flyyuanfayang@sina.com
 * @Description: 应用service
 */
public interface AppService {

    /**
     * 根据应用ID查找对应的应用信息
     * @param appId 应用的ID
     * @return 应用对象
     */
    App finAppById(String appId);

    /**
     * 查找系统中的ＳＳＯ服务的应用信息
     * @return
     */
    App findSSOServerApp();

    /**
     * 根据主机信息查找对应的应用信息
     * @param host 应用服务器的地址，主机信息
     * @return 对应的唯一的应用信息
     */
    App findAppByHost(String host);
}

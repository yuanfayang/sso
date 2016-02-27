package com.changhong.sso.core.dao;


import com.changhong.sso.common.core.entity.App;

import java.util.List;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.dao.file
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/27 11:35
 * @discription : App数据接入层
 */
public interface AppDao {

    /**
     * 按照App的编号查找App
     * @param appId App的编号 UUID
     * @return
     */
    App finAppById(String appId);

    /**
     * 查照SSO服务本身
     * @return
     */
    App findSSOservie();

    /**
     * 查找所有的App
     * @return
     */
    List<App> findAll();
}

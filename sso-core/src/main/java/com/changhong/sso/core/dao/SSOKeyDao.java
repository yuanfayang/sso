package com.changhong.sso.core.dao;

import com.changhong.sso.common.core.entity.SSOKey;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.dao
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/27 14:15
 * @discription : SSOKey数据接入层
 */
public interface SSOKeyDao {

    /**
     * 按keyId查找SSOKey
     * @param keyId key编号
     * @return
     */
    SSOKey findByKeyId(String keyId);

    /**
     * 按appId 查找ssoKey
     * @param appId app编号
     * @return
     */
    SSOKey findByAppId(String appId);
}

package com.changhong.sso.core.authentication.status;

import java.util.List;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.authentication.status
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 14:16
 * @discription : 用户登录状态存储器，实现用户登录状态的存储
 */
public interface UserLoggedStatusStore {

    /**
     * 增加新的用户登录状态
     * @param userLoggedStatus 用户登录状态
     */
    void addUserLoggedStatus(UserLoggedStatus userLoggedStatus);

    /**
     * 删除用户的登录状态
     * @param userId 用户标识
     * @param appId 应用标识
     */
    void deleteUserLoggedStatus(String userId,String appId);

    /**
     * 清楚某个用户所有的登录状态
     * @param userId 用户标识
     */
    void clearUpUserLoggedStatus(String userId);

    /**
     * 查询用户标识对应的用户所有的登录状态
     * @param userId 用户标识
     * @return 用户登录状态
     */
    List<UserLoggedStatus> findUserLoggedStatus(String userId);

}

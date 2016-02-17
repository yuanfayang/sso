package com.changhong.sso.core.authentication.status;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.authentication.status
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 14:28
 * @discription : 默认的用户登录状态存储管理器类
 */
public class DefaultUserLoggedStatusStore implements UserLoggedStatusStore {
    /**
     * 用户登录状态集合，不允许重复状态存在
     */
    private Set<UserLoggedStatus> userLoggedStatusSet = new HashSet<UserLoggedStatus>();

    /**
     * 用户标识和用户登录状态列表之间的对应的映射表，相当于一个索引，方便用户标识查询所有的登录状态表
     * map的key值是用户的标识，value是用户所有的登录状态列表
     */
    private Map<String, List<UserLoggedStatus>> userIndexMap = new HashMap<>();

    public Set<UserLoggedStatus> getUserLoggedStatusSet() {
        return userLoggedStatusSet;
    }

    public Map<String, List<UserLoggedStatus>> getUserIndexMap() {
        return userIndexMap;
    }

    @Override
    public void addUserLoggedStatus(UserLoggedStatus userLoggedStatus) {
        //检验数据的合法性
        if (userLoggedStatus != null && !StringUtils.isEmpty(userLoggedStatus.getUserId()) && !StringUtils.isEmpty(userLoggedStatus.getAppId())) {
            if (userLoggedStatus.getLoggedDate() == null) {
                userLoggedStatus.setLoggedDate(new Date());
            }

            this.userLoggedStatusSet.add(userLoggedStatus);
            List<UserLoggedStatus> userLoggedStatusList = this.userIndexMap.get(userLoggedStatus.getUserId());
            if (userLoggedStatus == null) {
                userLoggedStatusList = new ArrayList<>();
                this.userIndexMap.put(userLoggedStatus.getUserId(), userLoggedStatusList);
            }
            userLoggedStatusList.add(userLoggedStatus);
        }
    }

    @Override
    public void deleteUserLoggedStatus(String userId, String appId) {
        //检验数据的合法性
        if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(appId)) {
            UserLoggedStatus userLoggedStatus = new UserLoggedStatus(userId, appId, new Date());
            this.userLoggedStatusSet.remove(userLoggedStatus);
            List<UserLoggedStatus> userLoggedStatusList = this.userIndexMap.get(userId);
            if (userLoggedStatusList != null) {
                userLoggedStatusList.remove(userLoggedStatus);
            }
        }
    }

    @Override
    public void clearUpUserLoggedStatus(String userId) {
        //检验数据的合法性
        if (!StringUtils.isEmpty(userId)) {
            List<UserLoggedStatus> userLoggedStatusList = this.userIndexMap.get(userId);
            if (userLoggedStatusList != null) {
                userLoggedStatusList.clear();
                this.userIndexMap.put(userId, null);
            }
        }
    }

    @Override
    public List<UserLoggedStatus> findUserLoggedStatus(String userId) {
        if (!StringUtils.isEmpty(userId)) {
            return this.userIndexMap.get(userId);
        }
        return null;
    }
}

package com.changhong.sso.core.authentication.status;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.authentication.status
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 14:17
 * @discription : 用户登录状态
 */
public class UserLoggedStatus implements Serializable {
    private static final long serialVersionUID = 2367549202306158470L;

    /**
     * 用户唯一标识
     */
    private String userId;

    /**
     * 用户登录的应用标识
     */
    private String appId;

    /**
     * 登录应用的时间
     */
    private Date loggedDate;

    public UserLoggedStatus(String userId, String appId) {
        super();
        this.userId = userId;
        this.appId = appId;
    }

    public UserLoggedStatus(String userId, String appId, Date loggedDate) {
        this.userId = userId;
        this.appId = appId;
        this.loggedDate = loggedDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Date getLoggedDate() {
        return loggedDate;
    }

    public void setLoggedDate(Date loggedDate) {
        this.loggedDate = loggedDate;
    }

    @Override
    public String toString() {
        return "UserLoggedStatus [userId=" + userId + ", appId=" + appId
                + ", loggedDate=" + loggedDate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appId == null) ? 0 : appId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserLoggedStatus other = (UserLoggedStatus) obj;
        if (appId == null) {
            if (other.appId != null)
                return false;
        } else if (!appId.equals(other.appId))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }
}

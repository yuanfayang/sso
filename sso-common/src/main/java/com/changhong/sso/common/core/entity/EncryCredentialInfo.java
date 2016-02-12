package com.changhong.sso.common.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/11
 * @Time: 19:53
 * @Email: flyyuanfayang@sina.com
 * @Description: 加密凭据信息
 */
public class EncryCredentialInfo implements Serializable {
    private static final long serialVersionUID = 1020681929158942026L;

    //应用ID
    private String appId;
    //用户ID
    private String userId;
    //秘钥的唯一标识
    private String keyId;
    //加密凭证的创建时间
    private Date createTime;
    //加密凭证失效时间
    private Date expiredTime;
    //加密凭证的盐
    private String salt;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}

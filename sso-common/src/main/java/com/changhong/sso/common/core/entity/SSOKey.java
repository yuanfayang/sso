package com.changhong.sso.common.core.entity;

import com.changhong.sso.common.DESCoder;

import java.io.Serializable;
import java.security.Key;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/11
 * @Time: 19:16
 * @Email: flyyuanfayang@sina.com
 * @Description: SSO秘钥实体类
 */
public class SSOKey implements Serializable{
    private static final long serialVersionUID = -1667744158137372019L;

    //秘钥ID
    private String keyId;
    //应用ID
    private String appId;
    //秘钥值
    private String value;
    //秘钥文件的存放地址
    private String keyPath;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * 将本对象转换成对应的Key对象
     * @return key对象
     * @throws Exception
     */
    public Key toSecurityKey() throws Exception {
        if (this.getValue()!=null){
            return DESCoder.initSecretKey(this.value);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Ki4soKey [keyId=" + keyId + ", appId=" + appId + ", value="
                + value + ",keyPath=" + keyPath + "]" ;
    }
}

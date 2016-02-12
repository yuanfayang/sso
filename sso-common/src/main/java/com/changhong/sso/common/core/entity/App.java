package com.changhong.sso.common.core.entity;

import java.io.Serializable;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/11
 * @Time: 19:01
 * @Email: flyyuanfayang@sina.com
 * @Description: SSO应用实体类
 */
public class App implements Serializable {
    private static final long serialVersionUID = -8733659329354520613L;
    //应用ID
    private String appId;
    //应用的名称
    private String appName;
    //应用的主机地址
    private String host;
    //应用登出地址
    private String logoutUrl;
    //是否是SSO服务应用本身
    private boolean isSSOServer;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public boolean isSSOServer() {
        return isSSOServer;
    }

    public void setIsSSOServer(boolean isSSOServer) {
        this.isSSOServer = isSSOServer;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "App[AppId="+appId+",appName="+appName+",host="+host+",logoutUrl="+logoutUrl+",isSSOServer="+isSSOServer+
                "]";
    }
}

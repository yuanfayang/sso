package com.changhong.sso.common.core.entity;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.common.core.entity
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/3/20 12:34
 * @discription :  用户实体
 */
public class User {

    /**
     * 用户编号
     */
    private String uid;

    /**
     * 用户名
     */
    private String name;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户邮箱
     */
    private String mail;

    /**
     * 口令
     */
    private String token;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

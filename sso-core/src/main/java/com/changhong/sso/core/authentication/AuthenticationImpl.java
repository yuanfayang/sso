package com.changhong.sso.core.authentication;

import java.util.Date;
import java.util.Map;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/14
 * @Time: 10:42
 * @Email: flyyuanfayang@sina.com
 * @Description: 用户认证结果实现类
 */
public class AuthenticationImpl implements Authentication {
    /**
     * 认证时间
     */
    private Date authenticationDate;

    /**
     * 用户主体的其他属性表
     */
    private Map<String,Object> attributes;

    /**
     * 用户主体
     */
    private Principal principal;


    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Date getAuthenticationDate() {
        return this.authenticationDate;
    }

    @Override
    public Principal getPrincipal() {
        return this.principal;
    }

    public void setAuthenticationDate(Date authenticationDate) {
        this.authenticationDate = authenticationDate;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
}

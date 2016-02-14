package com.changhong.sso.core.authentication;

import java.util.Map;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/14
 * @Time: 10:32
 * @Email: flyyuanfayang@sina.com
 * @Description: 抽象的用户主体实现类
 */
public abstract class AbstractPrincipal implements Principal {

    /**
     * 用户ID
     */
    protected String id;

    /**
     * 用户主体的其他属性表
     */
    protected Map<String,Object> attributes;

    public AbstractPrincipal() {
        super();
    }

    public AbstractPrincipal(String id, Map<String, Object> attributes) {
        super();
        this.id = id;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void setId(String id) {
        this.id = id;
    }
}

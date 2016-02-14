package com.changhong.sso.core.authentication;

import java.util.Map;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/14
 * @Time: 10:38
 * @Email: flyyuanfayang@sina.com
 * @Description: 默认用户主体对象
 */
public class DefaultUserPrincipal extends AbstractPrincipal {
    public DefaultUserPrincipal() {
        super();
    }

    public DefaultUserPrincipal(String id, Map<String, Object> attributes) {
        super(id, attributes);
    }
}

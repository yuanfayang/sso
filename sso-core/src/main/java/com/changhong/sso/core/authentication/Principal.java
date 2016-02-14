package com.changhong.sso.core.authentication;

import java.util.Map;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/14
 * @Time: 10:25
 * @Email: flyyuanfayang@sina.com
 * @Description: 用户主体，代表一个用户
 */
public interface Principal {

    /**
     * 其他的属性
     * @return 键值列表
     */
    Map<String,Object> getAttributes();

    /**
     * 获取用的Id
     * @return
     */
    String getId();
}

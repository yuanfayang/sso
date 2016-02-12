package com.changhong.sso.common.core.authentication;

import java.util.Map;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/11
 * @Time: 19:59
 * @Email: flyyuanfayang@sina.com
 * @Description: 参数，定义获取动态参数列表的接口
 */
public interface Parameter {

    /**
     * 通过参数名称获取参数值的方法
     * @return 对应的参数值
     * @param paramName 参数名
     */
    Object getParemeterValue(String paramName);

    /**
     * 获取所有的参数表
     * @return 所有参数列表
     */
    Map<String,Object> getParameters();

    /**
     * 设置参数列表
     * @param parameters 要设置的参数列表
     */
    void setParameters(Map<String,Object> parameters);
}

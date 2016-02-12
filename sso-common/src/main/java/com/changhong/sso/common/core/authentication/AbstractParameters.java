package com.changhong.sso.common.core.authentication;

import java.util.Map;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/11
 * @Time: 20:35
 * @Email: flyyuanfayang@sina.com
 * @Description: 抽象化的参数接口实现类
 */
public abstract class AbstractParameters implements Parameter {

    //其他参数列表
    protected Map<String, Object> parameters;

    @Override
    public Object getParemeterValue(String paramName) {
        return this.parameters == null ? null : this.parameters.get(paramName);
    }

    @Override
    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    @Override
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}

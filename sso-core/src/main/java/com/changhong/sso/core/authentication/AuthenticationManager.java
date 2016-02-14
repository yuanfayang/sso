package com.changhong.sso.core.authentication;

import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.exception.InvalidCrendentialException;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/14
 * @Time: 10:51
 * @Email: flyyuanfayang@sina.com
 * @Description: 认证管理器，负责对用户凭证进行有效性认证
 */
public interface AuthenticationManager {

    /**
     * 对用户凭证进行认证，若认证失败抛出异常，若成功返回认证结果
     * @param credential 用户凭证
     * @return 认证通过后返回认证结果
     * @throws InvalidCrendentialException 当输入的凭证不合法时抛出该异常
     */
    Authentication authenticat(Credential credential) throws InvalidCrendentialException;
}

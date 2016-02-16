package com.changhong.sso.core.authentication;

import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.exception.InvalidCrendentialException;

import java.util.logging.Logger;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/14
 * @Time: 10:59
 * @Email: flyyuanfayang@sina.com
 * @Description: 认证管理器默认实现类
 */
public class AuthenicationManagerImpl implements AuthenticationManager {
    private static final Logger logger = Logger.getLogger(AuthenicationManagerImpl.class.getName());

    @Override
    public Authentication authenticat(Credential credential) throws InvalidCrendentialException {
        return null;
    }
}

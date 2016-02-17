package com.changhong.sso.core.authentication.handlers;

import com.changhong.sso.core.authentication.UsernamePasswordCredential;
import com.changhong.sso.exception.AuthenticationException;
import com.changhong.sso.exception.UsernameOrPasswordEmptyException;
import org.springframework.util.StringUtils;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.authentication.handlers
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 11:56
 * @discription : 用户名密码认证管理器，认证用户名和密码的合法性
 */
public class UsernamePasswordAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

    /**
     * 模拟的验证用户名密码的凭证验证器。具体的等对接用户体系再确定
     * @param credential
     *            the credentials representing the Username and Password
     *            presented to CAS
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected boolean authenticateUsernamePasswordInternal(UsernamePasswordCredential credential) throws AuthenticationException {
        final String username = credential.getUsername();
        final String password = credential.getPassword();

        if (!StringUtils.hasText(username)||!StringUtils.hasText(password)){
            throw UsernameOrPasswordEmptyException.INSTANCE;
        }

        if (username.equals(getPasswordEncoder().encode(password))) {
            return true;
        } else {
           throw  UsernameOrPasswordEmptyException.INSTANCE;
        }
    }
}

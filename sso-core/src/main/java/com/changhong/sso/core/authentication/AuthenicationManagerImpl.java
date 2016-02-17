package com.changhong.sso.core.authentication;

import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.core.authentication.handlers.AuthenticationHandler;
import com.changhong.sso.core.authentication.resolvers.CredentialToPrincipalResolver;
import com.changhong.sso.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/14
 * @Time: 10:59
 * @Email: flyyuanfayang@sina.com
 * @Description: 认证管理器默认实现类
 */
public class AuthenicationManagerImpl implements AuthenticationManager {
    private static final Logger logger = LoggerFactory.getLogger(AuthenicationManagerImpl.class.getName());

    /**
     * 认证处理器集合，使用多个认证处理器对凭证进行逐一检验，又要有一个通过即可
     */
    private List<AuthenticationHandler> authenticationHandlers;


    /**
     * 用户凭证转化为用户主体的解析器对象
     */
    private List<CredentialToPrincipalResolver> credentialToPrincipalResolvers;

    /**
     * 认证成功后处理对象
     */
    private AuthenticationPostHandler authenticationPostHandler;

    public AuthenicationManagerImpl() {
    }

    /**
     * 对用户凭证进行人生处理，返回认证结果
     * @param credential 用户凭证
     * @return 认证结果信息
     * @throws InvalidCrendentialException
     */
    @Override
    public Authentication authenticat(Credential credential) throws InvalidCrendentialException {
        //是否找到支持的凭据认证处理器。
        boolean foundSupported = false;
        //是否认证通过。
        boolean authenticated = false;
        //若凭据为空，则跑出异常。
        if (credential == null) {
            throw EmptyCredentialException.INSTANCE;
        }

        //初始化的认证异常信息
        AuthenticationException authenticationException = InvalidCrendentialException.INSTANCE;

        //循环调用所有的认证处理器
        if (authenticationHandlers != null && authenticationHandlers.size() > 0) {
            for (AuthenticationHandler authenticationHandler : authenticationHandlers) {
                //认证管理器是否支持该凭据
                if (authenticationHandler.supports(credential)) {
                    foundSupported = true;
                    try {
                        authenticated = authenticationHandler.authenticate(credential);
                        //若认证成功，跳出循环
                        if (authenticated) {
                            break;
                        }
                    } catch (AuthenticationException e) {
                        authenticationException = e;
                    }
                }
            }
        }

        //未找到支持的认证处理器
        if (!foundSupported) {
            throw UnsupportedCredentialsException.INSTANCE;
        }
        //若认证未通过，则跑出最后异常
        if (!authenticated) {
            throw authenticationException;
        }

        Principal principal = null;
        //初始化是否找到合适的凭证转化器
        foundSupported = false;
        //循环所有的用户凭证解析器
        if (credentialToPrincipalResolvers != null && credentialToPrincipalResolvers.size() > 0) {
            for (CredentialToPrincipalResolver resolver : credentialToPrincipalResolvers) {
                //用户凭证解析器是否支持该凭证
                if (resolver.supports(credential)) {
                    foundSupported = true;
                    principal = resolver.resolvePrincipal(credential);

                    //若解析成功，则跳出循环
                    if (principal != null) {
                        break;
                    }
                }
            }
        }

        //未找到支持的用户凭证解析器
        if (!foundSupported) {
            throw UnsupportedCredentialsException.INSTANCE;
        }
        //若认证成功后处理对象为空，则跑出异常
        if (authenticationPostHandler == null) {
            throw NoAuthenticationPostHandlerException.INSTANCE;
        }

        //交由认证后处理器进行处理
        return authenticationPostHandler.postAuthentication(credential, principal);
    }

    public void setAuthenticationHandlers(List<AuthenticationHandler> authenticationHandlers) {
        this.authenticationHandlers = authenticationHandlers;
    }

    public void setCredentialToPrincipalResolvers(List<CredentialToPrincipalResolver> credentialToPrincipalResolvers) {
        this.credentialToPrincipalResolvers = credentialToPrincipalResolvers;
    }

    public void setAuthenticationPostHandler(AuthenticationPostHandler authenticationPostHandler) {
        this.authenticationPostHandler = authenticationPostHandler;
    }
}

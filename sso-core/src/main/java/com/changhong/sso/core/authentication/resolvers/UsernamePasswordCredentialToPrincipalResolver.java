package com.changhong.sso.core.authentication.resolvers;

import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.core.authentication.EncryCredential;
import com.changhong.sso.core.authentication.DefaultUserPrincipal;
import com.changhong.sso.core.authentication.Principal;
import com.changhong.sso.core.authentication.UsernamePasswordCredential;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.authentication.resolvers
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 13:30
 * @discription : 用户名密码组成的凭证转化成用户主体的解析器
 */
public class UsernamePasswordCredentialToPrincipalResolver implements CredentialToPrincipalResolver {
    /**
     * 默认支持的类
     */
    private static final Class<UsernamePasswordCredential> DEFAULT_CLASS = UsernamePasswordCredential.class;

    /**
     * 支持的类实例
     */
    private Class<?> classToSupport = DEFAULT_CLASS;

    /**
     * 是否支持子类
     */
    private boolean supportSubClasses = true;

    public void setSupportSubClasses(boolean supportSubClasses) {
        this.supportSubClasses = supportSubClasses;
    }

    @Override
    public Principal resolvePrincipal(Credential credential) {
        //若匹配，则进行转换
        if (credential != null && this.supports(credential)) {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
            DefaultUserPrincipal defaultUserPrincipal = new DefaultUserPrincipal();
            //设置用户名为唯一标识
            defaultUserPrincipal.setId(usernamePasswordCredential.getUsername());
            //设置参数列表为用户属性
            defaultUserPrincipal.setAttributes(usernamePasswordCredential.getParameters());
            return defaultUserPrincipal;
        }
        return null;
    }

    @Override
    public boolean supports(Credential credential) {
        return credential != null
                && (this.classToSupport.equals(credential.getClass())
                || (this.classToSupport.isAssignableFrom(credential.getClass()))
                && this.supportSubClasses);
    }
}

package com.changhong.sso.core.authentication.resolvers;

import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.core.authentication.EncryCredential;
import com.changhong.sso.common.core.entity.EncryCredentialInfo;
import com.changhong.sso.core.authentication.DefaultUserPrincipal;
import com.changhong.sso.core.authentication.Principal;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.authentication.resolvers
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 13:17
 * @discription :实现加密后的凭证信息转化为用户主体信息对象的解析器
 */
public class EncryCredentialToPrincipalResolver implements CredentialToPrincipalResolver {
    /**
     * 默认支持的类
     */
    private static final Class<EncryCredential> DEFAULT_CLASS = EncryCredential.class;

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
        //若类型匹配,则进行转换
        if (credential != null && this.supports(credential)) {
            EncryCredential encryCredential = (EncryCredential) credential;
            DefaultUserPrincipal principal = new DefaultUserPrincipal();
            //解析加密凭证信息
            EncryCredentialInfo encryCredentialInfo = encryCredential.getEncryCredentialInfo();
            //设置用户名为唯一标识
            if (encryCredentialInfo != null) {
                principal.setId(encryCredentialInfo.getUserId());
                //时候设置参数列表为用户属性
                principal.setAttributes(encryCredential.getParameters());
            }
            return principal;
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

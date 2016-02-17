package com.changhong.sso.core.authentication.handlers;

import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.core.authentication.EncryCredential;
import com.changhong.sso.common.core.authentication.EncryCredentialManager;
import com.changhong.sso.common.core.entity.EncryCredentialInfo;
import com.changhong.sso.exception.AuthenticationException;
import com.changhong.sso.exception.InvalidEncryededentialException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.authentication.handlers
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 11:23
 * @discription :认证后的凭证认证处理器实现类，需要验证认证后的凭证的合法性（验证是否有效，过期等）
 */
public class EncryCredentialAuthenticaionHandler extends AbstractPreAndPostProcessingAuthenticationHandler {

    @Autowired
    private EncryCredentialManager encryCredentialManager;

    /**
     * 默认的支持的类
     */
    private static final Class<EncryCredential> DEFAULT_CLASS = EncryCredential.class;

    @Override
    protected boolean doAuthentication(Credential credential) throws AuthenticationException {
        //不支持凭证直接返回false
        if (!this.supports(credential)) {
            return false;
        }
        //如果是加密过的凭证信息
        if (credential != null && credential instanceof EncryCredential) {
            EncryCredential encryCredential = (EncryCredential) credential;

            //解密信息
            try {
                EncryCredentialInfo encryCredentialInfo = this.encryCredentialManager.decrypt(encryCredential);
                //设置凭证信息的关联性
                if (encryCredentialInfo!=null){
                    encryCredential.setEncryCredentialInfo(encryCredentialInfo);
                    //检查加密凭证的合法性
                    return this.encryCredentialManager.checkEncryCredentialInfo(encryCredentialInfo);
                }
            } catch (InvalidEncryededentialException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 是否支持当前的用户凭证类型
     *
     * @param credential 用户凭证
     * @return true：支持，false：不支持
     */
    @Override
    public boolean supports(final Credential credential) {
        return credential != null
                && (DEFAULT_CLASS.equals(credential.getClass()) || (DEFAULT_CLASS
                .isAssignableFrom(credential.getClass())));
    }
}

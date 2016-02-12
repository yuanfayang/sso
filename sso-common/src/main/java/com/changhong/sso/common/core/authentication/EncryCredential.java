package com.changhong.sso.common.core.authentication;

import com.changhong.sso.common.core.entity.EncryCredentialInfo;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/11
 * @Time: 20:40
 * @Email: flyyuanfayang@sina.com
 * @Description: 认证过的加密过的用户凭证，用户输出给客户端
 */
public class EncryCredential extends AbstractParameters implements Credential {

    //加密后的用户凭证串
    private String credential;
    //加密凭证对应的加密凭证信息对象
    private EncryCredentialInfo encryCredentialInfo;

    public EncryCredential(String credential) {
        super();
        this.credential = credential;
    }

    @Override
    public boolean isOriginal() {
        return false;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public EncryCredentialInfo getEncryCredentialInfo() {
        return encryCredentialInfo;
    }

    public void setEncryCredentialInfo(EncryCredentialInfo encryCredentialInfo) {
        this.encryCredentialInfo = encryCredentialInfo;
    }
}
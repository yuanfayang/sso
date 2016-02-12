package com.changhong.sso.common.core.authentication;

import com.changhong.sso.common.core.entity.EncryCredentialInfo;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/11
 * @Time: 20:45
 * @Email: flyyuanfayang@sina.com
 * @Description: 加密凭证的管理器，对加密凭证进行加密和解密等操作
 */
public interface EncryCredentialManager {

    /**
     * 对编码后的凭证信息进行解码，得到一个凭证对象
     * @param encryCredential 加密和编码后的凭据信息
     * @return 解密和解码后的凭证信息
     */
    EncryCredentialInfo decrypt(EncryCredential encryCredential) throws Exception;

    /**
     * 对凭证信息进行加密和编码处理，得到一个加密和编码后的凭证字符串
     * @param encryCredentialInfo 加密前的用于加密和编码的凭证信息
     * @return 加密后的用户凭证字符串
     */
    String encrypt(EncryCredentialInfo encryCredentialInfo);

    /**
     * 检查用户凭证信息的合法性，凭证是否合法，是否过期和有效等
     * @param encryCredentialInfo 用户的凭证信息
     * @return 凭证信息是否有效，true:有效，false：无效
     */
    boolean checkEncryCredentialInfo(EncryCredentialInfo encryCredentialInfo);
}

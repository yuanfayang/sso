package com.changhong.sso.common.core.authentication;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/11
 * @Time: 19:24
 * @Email: flyyuanfayang@sina.com
 * @Description: 用户凭证，表示一个用户的在系统中的身份信息，是一个抽象概念，
 *                 该凭据可以是一个用户的明文用户名密码对，也可以是一个加密后的信息
 *                 也可以是任何一种约定的可以识别的用户身份识别信息
 */
public interface Credential {

    /**
     * 是否未认证过的原始凭证
     * @return 是否原始凭证，true：原始凭据，false：加密后的凭据
     */
    boolean isOriginal();
}

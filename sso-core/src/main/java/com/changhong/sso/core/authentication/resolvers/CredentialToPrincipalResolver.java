package com.changhong.sso.core.authentication.resolvers;

import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.core.authentication.Principal;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.authentication.resolvers
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 13:13
 * @discription :用户凭证转化为用户主体的解析器接口
 */
public interface CredentialToPrincipalResolver {

    /**
     * 将用户凭证转化为用户主体对象
     * @param credential 用户凭证
     * @return 转换后的用户主体对象
     */
    Principal resolvePrincipal(Credential credential);

    /**
     * 判断是否支持该用户凭证
     * @param credential 待检验的用户凭证
     * @return true:表示支持，false：表示不支持
     */
    boolean supports(Credential credential);
}

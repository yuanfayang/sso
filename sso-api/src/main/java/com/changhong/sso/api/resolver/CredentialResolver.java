package com.changhong.sso.api.resolver;

import com.changhong.sso.common.core.authentication.Credential;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.api.resolver
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/18 14:50
 * @discription :；凭证解析器，从http请求的cookie，参数等值中解析出各类用户凭证，该接口由具体实现类负责具体凭据解析。
 */
public interface CredentialResolver {

    /**
     * 从http cookie或者参数中解析出凭证信息，返回解析后的凭证对象
     * @param httpServletRequest http servlet请求对象
     */
    Credential resolveCredential(HttpServletRequest httpServletRequest);
}

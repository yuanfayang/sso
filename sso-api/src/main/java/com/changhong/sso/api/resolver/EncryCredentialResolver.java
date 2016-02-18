package com.changhong.sso.api.resolver;

import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.core.authentication.EncryCredential;
import com.changhong.sso.common.web.utils.WebConstants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.api.resolver
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/18 15:06
 * @discription : 加密后的凭证解析器，从cookie中解析出凭证
 */
public class EncryCredentialResolver implements CredentialResolver {
    @Override
    public Credential resolveCredential(HttpServletRequest httpServletRequest) {
        if (httpServletRequest != null) {
            //获取cookie
            Cookie cookies[] = httpServletRequest.getCookies();
            if (cookies != null) {
                String value = null;
                for (Cookie cookie : cookies) {
                    //查找由SSO写入的cookie值
                    if (cookie != null && cookie.getName().equalsIgnoreCase(WebConstants.SSO_SERVER_EC_COOKIE_KEY)) {
                        value = cookie.getValue();
                        break;
                    }
                }

                //若未能从cookie中获得凭证，则从请求参数中获取凭证
                if (StringUtils.isEmpty(value)) {
                    value = httpServletRequest.getParameter(WebConstants.SSO_SERVER_EC_COOKIE_KEY);
                }

                //若最终获得加密后的凭证，则返回加密后的凭证
                if (!StringUtils.isEmpty(value)) {
                    return new EncryCredential(value.trim());
                }
            }
        }
        return null;
    }
}

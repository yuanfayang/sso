package com.changhong.sso.api.resolver;

import com.changhong.sso.common.core.authentication.AbstractParameters;
import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.web.utils.WebConstants;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.api.resolver
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/18 15:30
 * @discription : 组合凭据解析器，组合两种解析器，按照优先级顺序，从http请求参数或者cookie中解析出优先级较高的凭据，若无优先级高的凭据，则解析优先级低的凭据。
 */
public class CompositeCredentialResolver implements CredentialResolver {

    /**
     * 加密后的凭证接卸器
     */
    private CredentialResolver encryCredentialResolver;

    /**
     * 原始用户名密码凭据解析器。
     */
    private CredentialResolver usernamePasswordCredentialResolver;

    public CompositeCredentialResolver() {
    }

    @Override
    public Credential resolveCredential(HttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            return null;
        }

        Credential credential = null;
        if (encryCredentialResolver != null) {
            //先获取加密后的凭证
            credential = encryCredentialResolver.resolveCredential(httpServletRequest);
        }

        //若获得的凭证为空，则用原始的凭证解析器获取
        if (credential == null) {
            if (usernamePasswordCredentialResolver != null) {
                credential = usernamePasswordCredentialResolver.resolveCredential(httpServletRequest);
            }
        }

        //若是抽象的参数凭证对象，则解析其他的参数
        if (credential instanceof AbstractParameters) {
            AbstractParameters abstractParameters = (AbstractParameters) credential;
            //将所有的参数设置到参数列表中，方便以后处理使用。
            abstractParameters.setParameters(WebUtils.getParametersStartingWith(httpServletRequest, null));

            //如果参数中无service，则从session中获取
            if (abstractParameters.getParemeterValue(WebConstants.SERVICE_PARAM_NAME) == null) {
                if (httpServletRequest.getSession().getAttribute(WebConstants.SSO_SERVICE_KEY_IN_SESSION) != null) {
                    abstractParameters.getParameters().put(WebConstants.SERVICE_PARAM_NAME,httpServletRequest.getSession().getAttribute(WebConstants.SSO_SERVICE_KEY_IN_SESSION));
                }
            }
        }
        return credential;
    }

    public void setEncryCredentialResolver(CredentialResolver encryCredentialResolver) {
        this.encryCredentialResolver = encryCredentialResolver;
    }

    public void setUsernamePasswordCredentialResolver(CredentialResolver usernamePasswordCredentialResolver) {
        this.usernamePasswordCredentialResolver = usernamePasswordCredentialResolver;
    }
}

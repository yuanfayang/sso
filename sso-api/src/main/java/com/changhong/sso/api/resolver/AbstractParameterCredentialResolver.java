package com.changhong.sso.api.resolver;

import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.core.authentication.Parameter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.api.resolver
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/18 15:17
 * @discription : 提供参数化的凭证解析
 */
public abstract class AbstractParameterCredentialResolver extends
        AbstractPreAndPostProcessingCredentialResolver {
    @Override
    protected Credential postResolveCredential(HttpServletRequest request, Credential credential) {
        if (credential == null) {
            return null;
        }
        if (credential instanceof Parameter) {
            Parameter parameter = (Parameter) credential;
            parameter.setParameters(request.getParameterMap());
        }
        return super.postResolveCredential(request, credential);
    }
}

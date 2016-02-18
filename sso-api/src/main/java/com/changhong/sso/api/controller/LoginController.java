package com.changhong.sso.api.controller;

import com.changhong.sso.api.resolver.CredentialResolver;
import com.changhong.sso.api.result.LoginResultToView;
import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.web.utils.WebConstants;
import com.changhong.sso.core.service.LoginResult;
import com.changhong.sso.core.service.SSOService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.api.controller
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/18 16:44
 * @discription : 登录控制器
 */

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CredentialResolver credentialResolver;

    @Autowired
    private SSOService ssoService;

    @Autowired
    private LoginResultToView loginResultToView;

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request,
                              HttpServletResponse response) {

        logger.info("username:{}",request.getParameter("username"));
        ModelAndView mv = new ModelAndView("login");
        //解析用户凭据。
        Credential credential = credentialResolver.resolveCredential(request);
        //没有提供任何认证凭据。
        if (credential == null) {
            //设置serivce地址到session中。
            String service = request.getParameter(WebConstants.SERVICE_PARAM_NAME);
            if (!StringUtils.isEmpty(service)) {
                request.getSession().setAttribute(WebConstants.SSO_SERVICE_KEY_IN_SESSION, service);
            }
            //返回到登录页面，索取用户凭据。
            return mv;
        } else {
            //提供了用户凭据
            //调用核心结果进行凭证认证
            LoginResult result = ssoService.login(credential);
            //将验证结果转换为视图输出结果。
            mv = loginResultToView.loginResultToView(mv, result, request, response);
        }
        return mv;
    }
}

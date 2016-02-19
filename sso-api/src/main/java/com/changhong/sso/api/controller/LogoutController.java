package com.changhong.sso.api.controller;

import com.changhong.sso.api.resolver.CredentialResolver;
import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.web.utils.WebConstants;
import com.changhong.sso.core.service.SSOService;
import com.changhong.sso.exception.EmptyCredentialException;
import com.changhong.sso.exception.InvalidCrendentialException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.api.controller
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/19 14:10
 * @discription : 登出控制器
 */
@Controller
public class LogoutController {
    private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);

    @Autowired
    private CredentialResolver credentialResolver;

    @Autowired
    private SSOService ssoService;

    /**
     * ＳＳＯ统一登出方法
     *
     * @param appId       　SSO应用服务的唯一标识
     * @param callbackUrl 登出之后的回调地址
     * @param request     http请求
     * @param response    http响应
     * @return 登出成功后，若callbackUrl不为空，则SSO服务默认将请求转发到此地址，
     * 若callbackUrl为空或登出失败，SSO服务将返回json格式响应报文
     */
    @RequestMapping(value = "/logout")
    public ModelAndView loglout(@RequestParam(value = "appId", required = true) String appId,
                                @RequestParam(value = "callbackUrl", required = false) String callbackUrl,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        JSONObject resultJsonobj = new JSONObject();
        //解析并验证用户凭证
        Credential credential = credentialResolver.resolveCredential(request);

        if (credential == null) {
            resultJsonobj.put("code", EmptyCredentialException.INSTANCE.getCode());
            resultJsonobj.put("msgKey", EmptyCredentialException.INSTANCE.getMsgKey());
            try {
                response.getWriter().println(resultJsonobj);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }

        try {
            ssoService.logout(credential);
        } catch (InvalidCrendentialException e) {
            e.printStackTrace();
            logger.error("{}:登出异常:{}", ToStringBuilder.reflectionToString(credential), e);
            resultJsonobj.put("code", e.getCode());
            resultJsonobj.put("msgKey", e.getMsgKey());
            try {
                response.getWriter().println(resultJsonobj);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }

        //清除cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (WebConstants.SSO_SERVER_EC_COOKIE_KEY.equals(cookie.getName())) {
                    //设置cookie过期
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        resultJsonobj.put("code", "0000");
        resultJsonobj.put("msgKey", "logout success");

        //若应用服务已经给定了回调地址则转发到给定的回调地址中
        if (!StringUtils.isEmpty(callbackUrl)) {
            modelAndView.setViewName(callbackUrl);
            return modelAndView;
        } else {
            try {
                response.getWriter().println(resultJsonobj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

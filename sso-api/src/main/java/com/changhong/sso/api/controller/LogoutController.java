package com.changhong.sso.api.controller;

import com.changhong.sso.api.resolver.CredentialResolver;
import com.changhong.sso.api.web.util.ReadStreamUtil;
import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.core.entity.App;
import com.changhong.sso.common.core.service.AppService;
import com.changhong.sso.common.web.utils.WebConstants;
import com.changhong.sso.core.service.SSOService;
import com.changhong.sso.exception.InvalidCrendentialException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @Autowired
    private AppService appService;

    /**
     * ＳＳＯ统一登出方法
     *
     * @param appId    　SSO应用服务的唯一标识
     * @param request  http请求
     * @param response http响应
     * @return 登出成功后，若callbackUrl不为空，则SSO服务默认将请求转发到此地址，
     * 若callbackUrl为空或登出失败，SSO服务将返回json格式响应报文
     */
    @RequestMapping(value = "/logout")
    public void loglout(@RequestParam(value = "appId", required = false) String appId,
                        @RequestParam(value = "service", required = false) String service,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        logger.info("service:{},正在登出:", service);
        //解析并验证用户凭证
        Credential credential = credentialResolver.resolveCredential(request);
        try {
            //调用sso登出
            ssoService.logout(credential);
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


        } catch (InvalidCrendentialException e) {
            logger.error("{}:登出异常:{}", ToStringBuilder.reflectionToString(credential), e);
            e.printStackTrace();
        }

        //TODO 改变之前的SSO登出方式，改用jsonp实现
        String json = "{result:true}";
        //拼接jsonp格式的数据。
        StringBuffer sb = new StringBuffer();
        sb.append(getCallbackName("logoutSsoServer", request))
                .append("(")
                .append(json)
                .append(");");
        //写入jsonp格式的数据。
        try {
            response.setContentType("application/x-javascript");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(sb.toString());
        } catch (IOException e) {
            logger.error("执行登出时，jsonp异常：{}", e.getMessage());
            e.printStackTrace();
        }


        /*if (!org.springframework.util.StringUtils.isEmpty(service)) {
            //跳转到service对应的URL地址
            modelAndView.setView(new RedirectView(service));
            session.setAttribute(WebConstants.USER_STAT_IN_SESSION_KEY, null);
        } else {
            //返回默认的登出成功页面。
            modelAndView.setViewName("logoutSucess");
        }
        return modelAndView;*/
    }

    /**
     * 获取登出的js文件
     *
     * @param appId            app id
     * @param logoutSuccessUrl 登出成功后的跳转地址
     * @param request          请求
     * @param response         响应
     */
    @RequestMapping(value = "/logoutJs", method = RequestMethod.GET)
    @ResponseBody
    public Object getLogoutJs(@RequestParam(value = "appId", required = true) String appId,
                              @RequestParam(value = "logoutSuccessUrl", required = true) String logoutSuccessUrl,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        logger.info("app:'{}',开始获取logout.js", appId);
        App app = appService.finAppById(appId);

        if (app == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        App ssoServer = appService.findSSOServerApp();

        try {
            //读取模板文件
            String javascript = new String(ReadStreamUtil.readStream(LogoutController.class.getResourceAsStream("/common/logout.js")), "UTF-8");

            //替换一些参数值。
            javascript = javascript.replaceAll("\\$\\{currentAppLogoutUrl\\}", app.getLogoutUrl());
            javascript = javascript.replaceAll("\\$\\{logoutSuccessUrl\\}", logoutSuccessUrl);
            javascript = javascript.replaceAll("\\$\\{ssoServerHost\\}", ssoServer.getHost());

            //替换一些参数值。
            try {
                response.setContentType("application/x-javascript");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().println(javascript);
            } catch (IOException e) {
                logger.error("app:'{}',获取logout.js，出现异常：{}", appId, e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            logger.error("app:'{}',获取logout.js，出现异常：{}", appId, e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获得回调函数的名称
     *
     * @param defalutCallbackName 默认的回调函数名称。
     * @param request             请求对象。
     * @return 回调函数的名称
     */
    private String getCallbackName(String defalutCallbackName, HttpServletRequest request) {
        //获得传递的回调函数名。
        String callbackName = request.getParameter("callbackName");
        //如果参数是空，则使用默认的回调函数名。
        if (StringUtils.isEmpty(callbackName)) {
            callbackName = defalutCallbackName;
        }
        return callbackName;
    }
}

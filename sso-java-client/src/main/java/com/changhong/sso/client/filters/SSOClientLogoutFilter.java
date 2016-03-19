package com.changhong.sso.client.filters;

import com.changhong.sso.client.handler.AppClientLogoutHandler;
import com.changhong.sso.client.session.SessionStorage;
import com.changhong.sso.common.web.utils.WebConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.client.filters
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/20 16:07
 * @discription : SSO客户端应用本身登出过滤器
 */
public class SSOClientLogoutFilter extends BaseClientFilter {
    private static final Logger logger = LoggerFactory.getLogger(SSOClientLogoutFilter.class.getName());

    private static final String SESSIONID_IS_NULL = "send userId is null";

    private static final String SESSIONID_IS_NOT_CONTATINS = "send userId is not be included for Client ";
    /**
     * 本应用的登出处理器
     */
    private String appClientLogoutHandlerClass = "com.changhong.sso.app.com.SSOAppClientLogoutHandlerImpl";

    /**
     * 登出处理器
     */
    private AppClientLogoutHandler appClientLogoutHandler;

    @Override
    protected void doInit(FilterConfig filterConfig) throws ServletException {
        appClientLogoutHandlerClass = getInitParameterWithDefalutValue(filterConfig, "appClientLogoutHandlerClass", appClientLogoutHandlerClass);
        //构造等处处理器实例
        if (!StringUtils.isEmpty(appClientLogoutHandlerClass)) {
            try {
                this.appClientLogoutHandler = (AppClientLogoutHandler) Class.forName(appClientLogoutHandlerClass).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session=httpServletRequest.getSession();

/*
        //获取用户的userId参数
        String userId = request.getParameter(WebConstants.USER_ID_PARAM_NAME);
        if (StringUtils.isEmpty(userId)) {
            logger.warn(SESSIONID_IS_NULL);
            sendError(httpServletResponse, SESSIONID_IS_NULL);
            return;
        }

        if (!SessionStorage.containsKey(userId)) {
            logger.warn(SESSIONID_IS_NOT_CONTATINS);
            sendError(httpServletResponse, SESSIONID_IS_NULL);
            return;
        }*/

        //HttpSession session = SessionStorage.get(userId);
        logger.info("##########################:"+session.getAttribute(SSOClientFilter.USER_STAT_IN_SESSION_KEY));
        //本地应用未登出，则进行登出处理
        try {
            if (session != null && session.getAttribute(SSOClientFilter.USER_STAT_IN_SESSION_KEY) != null) {

                //清除session
                if (session.getAttribute(SSOClientFilter.USER_STAT_IN_SESSION_KEY) != null) {
                    session.setAttribute(SSOClientFilter.USER_STAT_IN_SESSION_KEY, null);
                }

                //若本应用登出处理器不为空，则进行相应处理,登出本应用
                if (appClientLogoutHandler != null) {
                    appClientLogoutHandler.logoutClient(httpServletRequest, httpServletResponse);
                }

                //设置session过期
                session.setMaxInactiveInterval(0);

            }
        } catch (Exception e) {
            //响应登录结果。
            sendError(httpServletResponse);
        }
        //相应登录结果
        sendResponse(httpServletResponse);
    }

    @Override
    public void destroy() {
    }

    private void sendResponse(HttpServletResponse response) {
        response.setContentType("text/javascript;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter outhtml;
        try {
            outhtml = response.getWriter();
            outhtml.print("{result:true}");
            outhtml.close();
        } catch (IOException e) {
            logger.error("send sendResponse error", e);
        }
    }

    private void sendError(HttpServletResponse response) {
        try {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            logger.error("send response error :{}", e);
        }
    }
}

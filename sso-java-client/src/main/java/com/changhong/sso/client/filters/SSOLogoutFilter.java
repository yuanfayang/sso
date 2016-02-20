package com.changhong.sso.client.filters;

import com.changhong.sso.client.handler.AppClientLogoutHandler;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.client.filters
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/20 16:07
 * @discription : SSO客户端应用本身登出过滤器
 */
public class SSOLogoutFilter extends BaseClientFilter {
    private static final Logger logger = Logger.getLogger(SSOLogoutFilter.class.getName());

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
        HttpSession session = httpServletRequest.getSession();
    }

    @Override
    public void destroy() {

    }
}

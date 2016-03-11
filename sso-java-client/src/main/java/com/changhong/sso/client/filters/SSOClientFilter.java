package com.changhong.sso.client.filters;

import com.changhong.sso.client.handler.AppClientLoginHandler;
import com.changhong.sso.client.key.DefaultKeyServiceImpl;
import com.changhong.sso.client.session.SessionStorage;
import com.changhong.sso.common.core.authentication.EncryCredential;
import com.changhong.sso.common.core.authentication.EncryCredentialManagerImpl;
import com.changhong.sso.common.core.entity.EncryCredentialInfo;
import com.changhong.sso.common.core.entity.SSOKey;
import com.changhong.sso.common.core.service.KeyService;
import com.changhong.sso.common.web.utils.WebConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.client.filters
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/20 11:20
 * @discription : SSO客户端应用的过滤器，实现SSO单点登录集成
 * 次过滤器必须添加，或自己实现类似过滤功能
 */
public class SSOClientFilter extends BaseClientFilter {
    private static final Logger logger = LoggerFactory.getLogger(SSOClientFilter.class);

    /**
     * 用户信息在客户端应用中session中的用户信息，主要用户客户端应用服务记录登录状态
     */
    public static final String USER_STAT_IN_SESSION_KEY = "sso_user_info_key";

    /**
     * SSO服务登出地址在request中的键值
     */
    public static final String SSO_SERVER_LOGOUT_URL="sso_server_logout_url";
    /**
     * SSO服务的登录地址
     */
    private String ssoLoginURL = ssoServerHost + "api/login";

    /**
     * 应用服务获取秘钥的URL
     */
    private String ssoFetchKeyURL = ssoServerHost + "api/fetchKey";

    /**
     * SSO服务器的登出URL地址
     */
    private String ssoServerLogoutURL = ssoServerHost + "api/logout";

    /**
     * 本应用在SSO中心服务中的id值
     */
    private String ssoClientAppId = "1001";

    /**
     * 本应用中的一个登录处理器
     */
    private String appClintLoginHandlerClass = "com.changhong.sso.app.common.SSOAppClientLoginHandlerImpl";

    /**
     * 本地应用的加密key
     */
    private SSOKey ssoKey;
    /**
     * 秘钥获取服务。
     */
    protected KeyService keyService = null;

    /**
     * 凭据管理器。
     */
    protected EncryCredentialManagerImpl encryCredentialManager;

    /**
     * 登录本应用的处理器。
     */
    protected AppClientLoginHandler appClientLoginHandler;

    @Override
    protected void doInit(FilterConfig filterConfig) throws ServletException {
        ssoClientAppId = getInitParameterWithDefalutValue(filterConfig, "ssoClientAppId", ssoClientAppId);
        ssoLoginURL = getInitParameterWithDefalutValue(filterConfig, "ssoLoginURL", ssoLoginURL);
        ssoFetchKeyURL = getInitParameterWithDefalutValue(filterConfig, "ssoFetchKeyURL", ssoFetchKeyURL);
        appClintLoginHandlerClass = getInitParameterWithDefalutValue(filterConfig, "appClintLoginHandlerClass", appClintLoginHandlerClass);
        ssoServerLogoutURL=getInitParameterWithDefalutValue(filterConfig,"ssoServerLogoutURL",ssoServerLogoutURL);
        //构造本应用的登录处理器对象
        if (!StringUtils.isEmpty(appClintLoginHandlerClass)) {
            try {
                this.appClientLoginHandler = (AppClientLoginHandler) Class.forName(appClintLoginHandlerClass).newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        keyService = new DefaultKeyServiceImpl(ssoFetchKeyURL, ssoClientAppId);
        this.encryCredentialManager = new EncryCredentialManagerImpl();
        this.encryCredentialManager.setKeyService(keyService);
        logger.info("the sso server is:" + this.ssoServerHost + ",please check this service is ok");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        //将SSO的登出地址放入到request参数中
        httpServletRequest.setAttribute(SSO_SERVER_LOGOUT_URL,ssoServerLogoutURL);
        //监测是否在本地应用登录
        //若未在本应用登录，则开始SSO登录流程
        logger.info("用户session---》：{}",session.getAttribute(USER_STAT_IN_SESSION_KEY));
        if (session.getAttribute(USER_STAT_IN_SESSION_KEY) == null) {

            //查找参数中是否存在SSO_CLIENT_EC，若不存在，则重定向到登录页
            String ssoClientEC = getClientEC(httpServletRequest);
            if (StringUtils.isEmpty(ssoClientEC)) {
                httpServletResponse.sendRedirect(buildRedirectToSSOServer(httpServletRequest));
                return;
            }

            //若没有key,则重新获取一次
            if (ssoKey == null) {
                try {
                    ssoKey = keyService.findByAppId(ssoClientAppId);
                } catch (Exception e) {
                    logger.error("fetch sso key error{}", e);
                }
            }

            //解密凭证
            EncryCredentialInfo encryCredentialInfo = this.encryCredentialManager.decrypt(new EncryCredential(ssoClientEC));
            logger.info("用户encryCredentialInfo：--》{}",encryCredentialInfo);
            if (encryCredentialInfo != null) {
                //检验凭证的合法性
                boolean valid = this.encryCredentialManager.checkEncryCredentialInfo(encryCredentialInfo);
                //如果合法，则继续其他处理
                if (valid) {
                    //设置本应用的登录状态
                    session.setAttribute(USER_STAT_IN_SESSION_KEY, encryCredentialInfo);
                    if (appClientLoginHandler != null) {
                        appClientLoginHandler.loginClient(encryCredentialInfo, httpServletRequest, httpServletResponse);
                    }

                    //重新请求原始请求
                    String url = httpServletRequest.getRequestURL().toString();
                    if (!StringUtils.isEmpty(url)) {
                        //如果请求中存在EC参数，则去除这个参数，重定位。
                        if (url.contains(WebConstants.SSO_CLIENT_COOKIE_KEY)) {
                            url = url.substring(0, url.indexOf(WebConstants.SSO_CLIENT_COOKIE_KEY));
                            //去除末尾的问号。
                            if (url.endsWith("?")) {
                                url = url.substring(0, url.length() - 1);
                            }

                            //去除末尾的&符号。
                            if (url.endsWith("&")) {
                                url = url.substring(0, url.length() - 1);
                            }
                        }
                    }
                    //保存用户的信息到session中
                    SessionStorage.put(encryCredentialInfo.getUserId(),session);

                    /*//登录成功后，写入EC到cookie中。
                    writeEC(ssoClientEC, httpServletResponse);*/

                    //重新定位请求，避免尾部出现长参数。
                    httpServletResponse.sendRedirect(url);
                    return;
                }
            }
            httpServletResponse.sendRedirect(buildRedirectToSSOServer(httpServletRequest));
            return;
        } else {
            //若已登录，则接续其他过滤器链
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        this.ssoKey = null;
    }

    /**
     * 构造转发到SSO服务的请求
     *
     * @param servletRequest
     * @return
     */
    private String buildRedirectToSSOServer(HttpServletRequest servletRequest) {
        StringBuffer sb = new StringBuffer(this.ssoLoginURL);
        if (this.ssoLoginURL.contains("?")) {
            sb.append("&");
        } else {
            sb.append("?");
        }
        sb.append("service=").append(servletRequest.getRequestURL().toString());
        return sb.toString();
    }

    /**
     * 从客户端获取cookie中的EC信息
     *
     * @param request http请求
     * @return EC信息
     */
    private String getClientEC(HttpServletRequest request) {
        String ec = null;
        if (request != null) {
            //先从请求参数中获取
            ec = request.getParameter(WebConstants.SSO_CLIENT_COOKIE_KEY);
            logger.info("ec--->{}"+ec);
            //再从cookie中获取
            if (StringUtils.isEmpty(ec)) {
                Cookie cookie = getCookie(request, WebConstants.SSO_CLIENT_COOKIE_KEY);
                if (cookie != null) {
                    ec = cookie.getValue().trim();
                }
            }
        }
        return ec;
    }

    /**
     * 将EC的值写入到服务器的cookie中。
     *
     * @param ec       EC值。
     * @param response Http响应对象。
     */
    protected void writeEC(String ec, HttpServletResponse response) {
        //使用URL进行编码，避免写入cookie错误。
        try {
            ec = URLEncoder.encode(ec, "UTF-8");
            response.addCookie(new Cookie(
                    WebConstants.SSO_CLIENT_COOKIE_KEY, ec));
        } catch (UnsupportedEncodingException e) {
            logger.info( "encode with URL error {}", e);
        }
    }
}

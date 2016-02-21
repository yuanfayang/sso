package com.changhong.sso.client.filters;

import com.changhong.sso.common.core.service.KeyService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/21
 * @Time: 17:18
 * @Email: flyyuanfayang@sina.com
 * @Description: SSO客户端应用生成私钥的过滤器
 */
public class SSOGeneratePrivateKeyFilter extends BaseClientFilter {
    private static final Logger logger = Logger.getLogger(SSOGeneratePrivateKeyFilter.class.getName());

    /**
     * 应用获取秘钥的地址
     */
    private String ssoServerFetchKryURL = "http://localhost:8080/sso/fetchKey";

    /**
     * 应用的id
     */
    private String ssoClientAppId = "1001";

    /**
     * 生成私钥文件的类
     */
    private String ssoGeneratePrivateKeyClass = "com.changhong.sso.client.key.DefaultKeyServiceImpl";

    /**
     * 秘钥服务
     */
    private KeyService keyService = null;

    @Override
    protected void doInit(FilterConfig filterConfig) throws ServletException {
        ssoGeneratePrivateKeyClass = getInitParameterWithDefalutValue(filterConfig, "ssoGeneratePrivateKeyClass", ssoGeneratePrivateKeyClass);
        ssoClientAppId = getInitParameterWithDefalutValue(filterConfig, "ssoClientAppId", ssoClientAppId);
        ssoServerFetchKryURL = getInitParameterWithDefalutValue(filterConfig, "ssoServerFetchKryURL", ssoServerFetchKryURL);
        //构造登录本应用的私钥处理器对象。
        if (!StringUtils.isEmpty(ssoGeneratePrivateKeyClass)) {
            try {
                this.keyService = (KeyService) Class.forName(ssoGeneratePrivateKeyClass).getConstructor(String.class, String.class).newInstance(ssoServerFetchKryURL, ssoClientAppId);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (keyService != null) {
            try {
                keyService.generateKeyFile(ssoClientAppId);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("generate private key failure:{}", e);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

}

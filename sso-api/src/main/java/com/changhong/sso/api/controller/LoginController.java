package com.changhong.sso.api.controller;

import com.changhong.sso.api.resolver.CredentialResolver;
import com.changhong.sso.api.result.LoginResultToView;
import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.web.utils.WebConstants;
import com.changhong.sso.core.authentication.Authentication;
import com.changhong.sso.core.authentication.AuthenticationPostHandler;
import com.changhong.sso.core.service.LoginResult;
import com.changhong.sso.core.service.SSOService;
import com.changhong.sso.exception.EmptyCredentialException;
import com.changhong.sso.exception.UsernameOrPasswordInvalidException;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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

    /**
     * SSO统一登录方法
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/login"})
    public ModelAndView login(HttpServletRequest request,
                              HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("login2");
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
            logger.info("用户开始登录系统,登录凭据为:{}", JSONObject.fromObject(credential));
            //提供了用户凭据
            //调用核心结果进行凭证认证
            LoginResult result = ssoService.login(credential);
            //将验证结果转换为视图输出结果。
            mv = loginResultToView.loginResultToView(mv, result, request, response);
        }
        return mv;
    }

    /**
     * 开放的登录rest api
     *
     * @param request  request请求
     * @param response 请求响应
     * @return 返回值
     */
    @RequestMapping(value = "/rest/login", method = RequestMethod.POST)
    @ResponseBody
    public Object restLogin(@RequestParam(value = "service") String service,
                            @RequestParam(value = "appId") String appId,
                            HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        //解析用户凭据。
        Credential credential = credentialResolver.resolveCredential(request);
        //没有提供登录凭证
        if (credential == null) {
            //TODO
            //设置serivce地址到session中。
            if (!StringUtils.isEmpty(service)) {
                request.getSession().setAttribute(WebConstants.SSO_SERVICE_KEY_IN_SESSION, service);
            }

            JSONObject error=new JSONObject().accumulate("code",EmptyCredentialException.INSTANCE.getCode()).accumulate("msgKey",EmptyCredentialException.INSTANCE.getMsgKey());
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        } else {
            //TODO
            logger.info("用户开始登录系统,登录凭据为:{}", JSONObject.fromObject(credential));
            //提供了用户凭据
            //调用核心结果进行凭证认证
            LoginResult result = ssoService.login(credential);
            if (result.isSuccess()) {
                //登录结果对象
                Authentication authentication = result.getAuthentication();
                //清除session中的状态信息service
                request.getSession().removeAttribute(WebConstants.SSO_SERVICE_KEY_IN_SESSION);

                // 如果有加密凭据信息，则写入加密凭据值到cookie中。
                if (authentication != null) {
                    if (authentication.getAttributes()!=null){
                        Map<String, Object> attributes = authentication.getAttributes();
                        //TODO 写入sso的cookie
                        //SSO服务端加密凭证写cookie中
                        if (attributes.get(AuthenticationPostHandler.SSO_SERVER_EC_KEY) != null) {
                            Cookie cookie = new Cookie(WebConstants.SSO_SERVER_EC_COOKIE_KEY, attributes.get(AuthenticationPostHandler.SSO_SERVER_EC_KEY).toString());
                            //限制Cookie的域
                            //cookie.setDomain(ReadPropertiesUtils.read("sso.domain"));
                            response.addCookie(cookie);
                        }
                        /*//TODO 校验是否存在service
                        //SSO客户端加密凭证和参数service都存在，则跳转到对应的页面中
                        if (attributes.get(AuthenticationPostHandler.SSO_CLIENT_EC_KEY) != null
                                && !StringUtils.isEmpty(attributes.get(WebConstants.SERVICE_PARAM_NAME).toString())) {
                            //TODO 看是否需要进行针对service的跳转操作
                            logger.info("SSO_CLIENT_EC_KEY:{}", attributes.get(AuthenticationPostHandler.SSO_CLIENT_EC_KEY));

                            StringBuffer sb = new StringBuffer(attributes.get(WebConstants.SERVICE_PARAM_NAME).toString());
                            if (attributes.get(WebConstants.SERVICE_PARAM_NAME).toString().contains("?")) {
                                sb.append("&");
                            } else {
                                sb.append("?");
                            }
                            sb.append(WebConstants.SSO_CLIENT_COOKIE_KEY)
                                    .append("=").append(attributes.get(AuthenticationPostHandler.SSO_CLIENT_EC_KEY).toString());

                            response.sendRedirect(sb.toString());
                        }*/

                        JSONObject resultObj = new JSONObject();
                        resultObj.put("code", "200");
                        resultObj.put("msg", "登录成功！");

                        JSONObject dataObj = new JSONObject();
                        dataObj.accumulate("user", authentication.getPrincipal().getAttributes().get("user"));

                        resultObj.put("data", dataObj);
                        return new ResponseEntity<>(resultObj, HttpStatus.OK);
                    }
                    else {
                        JSONObject error=new JSONObject().accumulate("code",UsernameOrPasswordInvalidException.INSTANCE.getCode()).accumulate("msgKey",UsernameOrPasswordInvalidException.INSTANCE.getMsgKey());
                        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
                    }
                }
            } else {
                //若登陆失败
                //删除以前不合法的凭据信息。
                //清除cookie值。
                Cookie[] cookies = request.getCookies();
                if (cookies != null && cookies.length > 0) {
                    for (Cookie cookie : cookies) {
                        if (WebConstants.SSO_SERVER_EC_COOKIE_KEY.equals(cookie.getName())) {
                            //设置过期时间为立即。
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                }

                //登录失败
                JSONObject error=new JSONObject().accumulate("code",UsernameOrPasswordInvalidException.INSTANCE.getCode()).accumulate("msgKey",UsernameOrPasswordInvalidException.INSTANCE.getMsgKey());
                return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
            }
        }
        return null;
    }
}

package com.changhong.sso.api.result;

import com.changhong.sso.common.web.utils.WebConstants;
import com.changhong.sso.core.authentication.Authentication;
import com.changhong.sso.core.authentication.AuthenticationPostHandler;
import com.changhong.sso.core.service.LoginResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.api.result
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/18 15:48
 * @discription : 默认的实现类。
 */
public class DefaultLoginResultToView implements LoginResultToView {
    @Override
    public ModelAndView loginResultToView(ModelAndView mv, LoginResult result, HttpServletRequest request, HttpServletResponse response) {
        if (mv == null) {
            mv = new ModelAndView();
        }

        if (result == null || request == null || response == null) {
            return mv;
        }

        // 若登陆成功，则返回登录成功页
        if (result.isSuccess()) {
            //登录结果对象
            Authentication authentication = result.getAuthentication();

            //清除session中的状态信息service
            request.getSession().removeAttribute(WebConstants.SSO_SERVICE_KEY_IN_SESSION);

            // 如果有加密凭据信息，则写入加密凭据值到cookie中。
            if (authentication != null && authentication.getAttributes() != null) {
                Map<String, Object> attributes = authentication.getAttributes();
                //SSO服务端加密凭证写cookie中
                if (attributes.get(AuthenticationPostHandler.SSO_SERVER_EC_KEY) != null) {
                    Cookie cookie = new Cookie(WebConstants.SSO_SERVER_EC_COOKIE_KEY, attributes.get(AuthenticationPostHandler.SSO_SERVER_EC_KEY).toString());
                    response.addCookie(cookie);
                }

                //SSO客户端加密凭证和参数service都存在，则跳转到对应的页面中
                if (attributes.get(AuthenticationPostHandler.SSO_CLIENT_EC_KEY) != null
                        && !StringUtils.isEmpty(attributes.get(WebConstants.SERVICE_PARAM_NAME).toString())) {
                    mv.getModel().put("authentication", authentication);
                    mv.setView(
                            buildRedirectView(
                                    attributes.get(WebConstants.SERVICE_PARAM_NAME).toString(),
                                    attributes.get(WebConstants.SSO_CLIENT_COOKIE_KEY).toString()
                            )
                    );
                    return mv;
                }
            }
            mv.getModel().put("authentication", authentication);
            mv.setViewName("loginSucess");
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
            mv.getModel().put("code", result.getCode());
            mv.getModel().put("msg", result.getMsgKey());
        }
        return null;
    }

    /**
     * 构造跳转的URL地址。
     *
     * @return
     */
    private RedirectView buildRedirectView(String service,
                                           String encryCredential) {
        StringBuffer sb = new StringBuffer(service);
        if (service.contains("?")) {
            sb.append("&");

        } else {
            sb.append("?");
        }
        sb.append(WebConstants.SSO_CLIENT_COOKIE_KEY)
                .append("=").append(encryCredential);
        return new RedirectView(sb.toString());
    }
}

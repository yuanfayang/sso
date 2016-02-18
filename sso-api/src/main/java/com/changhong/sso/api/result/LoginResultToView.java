package com.changhong.sso.api.result;

import com.changhong.sso.core.service.LoginResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.api.result
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/18 15:47
 * @discription : 该接口实现了将登录结果转换为视图响应的功能。
 */
public interface LoginResultToView {
    /**
     * 将登录结果对象相应到模型和视图中。
     * 所有参数均不允许输入null.
     *
     * @param mv       模型视图对象。
     * @param result   登录结果信息。
     * @param request  http请求对象。
     * @param response http响应对象。
     * @return 更新后的模型视图对象。
     */
    ModelAndView loginResultToView(ModelAndView mv, LoginResult result, HttpServletRequest request, HttpServletResponse response);
}

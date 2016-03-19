package com.changhong.sso.api.controller;

import com.alibaba.fastjson.JSON;
import com.changhong.sso.api.resolver.CredentialResolver;
import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.core.entity.App;
import com.changhong.sso.core.service.SSOService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.api.controller
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/20 10:40
 * @discription : App应用控制器
 */
@Controller
public class AppController {
    private final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private CredentialResolver credentialResolver;

    @Autowired
    private SSOService ssoService;

    /**
     * 获取用户登陆的应用服务列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getAppList", method = RequestMethod.GET)
    @ResponseBody
    public void getApplist(HttpServletRequest request,
                                HttpServletResponse response) {
        //解析用户凭证
        Credential credential = credentialResolver.resolveCredential(request);

        List<App> appList = ssoService.getAppList(credential);

        String json = JSON.toJSONString(appList);
        StringBuffer sb = new StringBuffer();
        sb.append(getCallbackName("fetchAppList", request))
                .append("(")
                .append(json)
                .append(");");
        try {
            response.setContentType("application/x-javascript");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(sb.toString());
        } catch (IOException e) {
            logger.error("获取applist是IO异常，{}",e.getMessage());
            e.printStackTrace();
        }
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

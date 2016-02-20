package com.changhong.sso.api.controller;

import com.changhong.sso.api.resolver.CredentialResolver;
import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.core.entity.App;
import com.changhong.sso.core.service.SSOService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private CredentialResolver credentialResolver;

    @Autowired
    private SSOService ssoService;

    /**
     * 获取用户登陆的应用服务列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getAppList", method = RequestMethod.GET)
    @ResponseBody
    public List<App> getApplist(HttpServletRequest request,
                                 HttpServletResponse response) {
        //解析用户凭证
        Credential credential = credentialResolver.resolveCredential(request);

        List<App> appList = ssoService.getAppList(credential);
        return appList;
    }
}

package com.changhong.sso.app.web.controller;

import com.changhong.sso.app.common.SSOAppClientLoginHandlerImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.app.web.controller
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/20 14:10
 * @discription :
 */
@Controller
public class HomeController {

    @RequestMapping(value = "home",method = RequestMethod.GET)
    public String home(HttpServletRequest request,
                       HttpServletResponse response,
                       HttpSession session,
                       Model model){
        model.addAttribute("user",session.getAttribute(SSOAppClientLoginHandlerImpl.USER_KEY));
        return "home";
    }
}

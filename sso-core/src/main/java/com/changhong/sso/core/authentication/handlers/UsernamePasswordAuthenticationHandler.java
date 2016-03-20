package com.changhong.sso.core.authentication.handlers;

import com.alibaba.fastjson.JSONObject;
import com.changhong.sso.common.core.entity.User;
import com.changhong.sso.common.web.utils.HttpClientUtils;
import com.changhong.sso.core.authentication.UsernamePasswordCredential;
import com.changhong.sso.core.authentication.status.Authenticated;
import com.changhong.sso.core.util.ReadPropertiesUtils;
import com.changhong.sso.exception.AuthenticationException;
import com.changhong.sso.exception.UsernameOrPasswordEmptyException;
import com.changhong.sso.exception.UsernameOrPasswordInvalidException;
import org.springframework.util.StringUtils;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.authentication.handlers
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 11:56
 * @discription : 用户名密码认证管理器，认证用户名和密码的合法性
 */
public class UsernamePasswordAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {
    private final String userCenterHost = "http://wdev.camplus.cn:8003";

    private final String loginApi="/sso/user/login";

    /**
     * 模拟的验证用户名密码的凭证验证器。具体的等对接用户体系再确定
     *
     * @param credential the credentials representing the Username and Password
     *                   presented to CAS
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected Authenticated authenticateUsernamePasswordInternal(UsernamePasswordCredential credential) throws AuthenticationException {
        final String username = credential.getUsername();
        final String password = credential.getPassword();

        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw UsernameOrPasswordEmptyException.INSTANCE;
        }

        /*if (username.trim().equals(getPasswordEncoder().encode(password.trim()))) {
            return true;
        } else {
           throw  UsernameOrPasswordInvalidException.INSTANCE;
        }*/

        //TODO 调用用户体系登录接口实现登录
        JSONObject paramJsonObj = new JSONObject();
        paramJsonObj.put("username", username);
        paramJsonObj.put("password", password);

        //登录用户系统，获取响应
        String result = HttpClientUtils.doPostJSON(userCenterHost + loginApi, paramJsonObj);
        //解析报文
        //报文若为'[]'即出现错误
        if (!StringUtils.isEmpty(result)) {
            if (result.startsWith("[")){
                throw UsernameOrPasswordInvalidException.INSTANCE;
            }
            JSONObject resultJsonObj = JSONObject.parseObject(result);
            //成功得到Token则则证明登录成功
            if (resultJsonObj.containsKey("token")) {
                //TODO 组装正确的返回值
                Authenticated authenticated = new Authenticated();
                //设置认证成功状态为true
                authenticated.setAuthenticated(true);
                //设置token
                authenticated.setToken(resultJsonObj.getString("token"));
                //设置用户信息
                JSONObject userObj=resultJsonObj.getJSONObject("user");
                //初始化用户信息
                User user=new User();
                user.setMail(userObj.getString("mail"));
                user.setName(userObj.getString("name"));
                user.setRealName(userObj.getString("realname"));
                user.setUid(userObj.getString("uid"));
                user.setToken(resultJsonObj.getString("token"));

                authenticated.setUser(user);
                return authenticated;
            } else {
                throw UsernameOrPasswordInvalidException.INSTANCE;
            }
        } else {
            return null;
        }
    }
}

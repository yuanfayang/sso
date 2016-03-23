package com.changhong.sso.core.service;

import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.core.entity.App;
import com.changhong.sso.common.core.service.AppService;
import com.changhong.sso.core.authentication.Authentication;
import com.changhong.sso.core.authentication.AuthenticationManager;
import com.changhong.sso.core.authentication.status.UserLoggedStatus;
import com.changhong.sso.core.authentication.status.UserLoggedStatusStore;
import com.changhong.sso.exception.InvalidCrendentialException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.service
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 15:03
 * @discription :核心服务接口實現類
 */
public class SSOServiceImpl implements SSOService {
    private static final Logger logger = LoggerFactory.getLogger(SSOServiceImpl.class);

    private AuthenticationManager authenticationManager;

    private UserLoggedStatusStore userLoggedStatusStore;

    private AppService appService;

    private LogoutAppService logoutAppService;

    @Override
    public LoginResult login(Credential credential) {
        //没用用户凭证，返回空
        if (credential == null) {
            return null;
        }

        LoginResult loginResult = new LoginResult();
        loginResult.setSuccess(false);

        //调用认证处理器进行认证
        try {
            Authentication authentication = authenticationManager.authenticat(credential);
            //登录成功
            loginResult.setSuccess(true);
            loginResult.setAuthentication(authentication);

            //添加登录的应用记录
        } catch (InvalidCrendentialException e) {
            logger.error("认证用户凭证失败：{}",e.getMessage());
            e.printStackTrace();
            //登录失败
            loginResult.setSuccess(false);
            //设置异常代码和异常信息
            loginResult.setCode(e.getCode());
            loginResult.setMsgKey(e.getMsgKey());
        }

        return loginResult;
    }

    @Override
    public void logout(Credential credential) throws InvalidCrendentialException {
        if (credential == null) {
            return;
        }
        //对凭证进行一次认证
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticat(credential);
        } catch (InvalidCrendentialException e) {
            logger.error("单点登出时，校验用户信息：{},不合法",credential);
            e.printStackTrace();
        }

        //登出所有的应用。
        //logoutAppService.logoutApp(authentication.getPrincipal().getId(), service);

        //清除用户登录记录
        if (authentication != null && authentication.getPrincipal() != null) {
            this.userLoggedStatusStore.clearUpUserLoggedStatus(authentication.getPrincipal().getId());
        }
    }

    @Override
    public List<App> getAppList(Credential credential) {
        List<App> apps = new ArrayList<>();
        if (credential == null) {
            return apps;
        }

        try {
            //对凭证进行一次认证
            Authentication authentication = authenticationManager.authenticat(credential);
            if (authentication != null && authentication.getPrincipal() != null) {
                List<UserLoggedStatus> userLoggedStatusList = this.userLoggedStatusStore.findUserLoggedStatus(authentication.getPrincipal().getId());
                //批量查询对应的应用信息
                if (userLoggedStatusList != null && userLoggedStatusList.size() > 0) {
                    for (UserLoggedStatus status : userLoggedStatusList) {
                        App app = appService.finAppById(status.getAppId());
                        if (app != null) {
                            apps.add(app);
                        }
                    }
                }
            }
        } catch (InvalidCrendentialException e) {
            e.printStackTrace();
        }
        return apps;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setUserLoggedStatusStore(UserLoggedStatusStore userLoggedStatusStore) {
        this.userLoggedStatusStore = userLoggedStatusStore;
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public void setLogoutAppService(LogoutAppService logoutAppService) {
        this.logoutAppService = logoutAppService;
    }
}

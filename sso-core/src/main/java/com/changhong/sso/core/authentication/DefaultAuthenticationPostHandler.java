package com.changhong.sso.core.authentication;

import com.alibaba.fastjson.JSONObject;
import com.changhong.sso.common.core.authentication.AbstractParameters;
import com.changhong.sso.common.core.authentication.Credential;
import com.changhong.sso.common.core.authentication.EncryCredentialManager;
import com.changhong.sso.common.core.entity.App;
import com.changhong.sso.common.core.entity.EncryCredentialInfo;
import com.changhong.sso.common.core.entity.SSOKey;
import com.changhong.sso.common.core.entity.User;
import com.changhong.sso.common.core.service.KeyService;
import com.changhong.sso.common.web.utils.WebConstants;
import com.changhong.sso.common.core.service.AppService;
import com.changhong.sso.core.authentication.status.UserLoggedStatus;
import com.changhong.sso.core.authentication.status.UserLoggedStatusStore;
import com.changhong.sso.exception.NoSSOKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.authentication
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 16:02
 * @discription : 默认的认证后处理其实现类，通过方法的实现
 */
public class DefaultAuthenticationPostHandler implements AuthenticationPostHandler {
    private static final Logger logger = LoggerFactory.getLogger(DefaultAuthenticationPostHandler.class);

    /**
     * 密钥持续时间，默认问3个月
     */
    private static final long DURATION = 3L * 30 * 24 * 60 * 1000;

    private EncryCredentialManager encryCredentialManager;

    private KeyService keyService;

    private AppService appService;

    private UserLoggedStatusStore userLoggedStatusStore;

    @Override
    public Authentication postAuthentication(Credential credential, Principal principal) {
        Date createTime = new Date();
        //若认证通过，则返回认证结果对象
        AuthenticationImpl authentication = new AuthenticationImpl();
        authentication.setAuthenticationDate(createTime);
        authentication.setPrincipal(principal);
        encryCredentialWithSSOKey(authentication,credential,principal);
        encryCredentialWithAppKey(authentication,credential,principal);
        return authentication;
    }

    /**
     * 用SSO本身的Key对凭证进行加密处理
     *
     * @param authentication 授权信息
     * @param credential     凭证
     * @param principal      用户主体
     */
    private void encryCredentialWithSSOKey(AuthenticationImpl authentication, Credential credential, Principal principal) {
        //是原始凭证才进行加密
        if (credential != null && credential.isOriginal()) {
            //查找对应的SSO服务信息
            App ssoApp = appService.findSSOServerApp();
            if (ssoApp == null) {
                logger.error("no SSO key info");
                throw NoSSOKeyException.INSTANCE;
            }

            String encryCredential = encryCredentialManager.encrypt(buildEncryCredentialInfo(ssoApp.getAppId(), authentication, principal));
            //加密后的凭据信息写入到动态属性中。
            Map<String, Object> attributes = authentication.getAttributes();
            if (attributes == null) {
                attributes = new HashMap<String, Object>();
            }
            attributes.put(SSO_SERVER_EC_KEY, encryCredential);
            authentication.setAttributes(attributes);
        }
    }

    private void encryCredentialWithAppKey(AuthenticationImpl authentication, Credential credential, Principal principal) {
        //获得登录的应用信息。
        AbstractParameters abstractParameter = null;
        if (credential != null && credential instanceof AbstractParameters) {
            abstractParameter = (AbstractParameters) credential;
        }
        //若登录对应用的服务参数service的值不为空，则使用该service对应的应用的key进行加密。
        if (authentication != null && abstractParameter != null && abstractParameter.getParemeterValue(WebConstants.SERVICE_PARAM_NAME) != null) {
            String service = abstractParameter.getParemeterValue(WebConstants.SERVICE_PARAM_NAME).toString().trim().toLowerCase();
            //service不为空，且符合Http协议URL格式，则继续加密。
            if (service.length() > 0) {
                //查找ki4so服务对应的应用信息。
                App clientApp = appService.findAppByHost(service);
                if (clientApp != null) {
                    String encryCredential = encryCredentialManager.encrypt(buildEncryCredentialInfo(clientApp.getAppId(), authentication, principal));
                    //加密后的凭据信息写入到动态属性中。
                    Map<String, Object> attributes = authentication.getAttributes();
                    if (attributes == null) {
                        attributes = new HashMap<String, Object>();
                    }
                    attributes.put(SSO_CLIENT_EC_KEY, encryCredential);
                    attributes.put(WebConstants.SERVICE_PARAM_NAME, service);
                    authentication.setAttributes(attributes);

                    //更新用户登录状态到存储器中。
                    UserLoggedStatus status = new UserLoggedStatus(principal.getId(), clientApp.getAppId(), authentication.getAuthenticationDate());
                    userLoggedStatusStore.addUserLoggedStatus(status);
                }
            }
        }
    }

    /**
     * 构造加密基础信息
     *
     * @param appId          应用名称
     * @param authentication 认证信息
     * @param principal      用户主体
     * @return 加密基础啊信息
     */
    private EncryCredentialInfo buildEncryCredentialInfo(String appId, AuthenticationImpl authentication,Principal principal) {
        EncryCredentialInfo encryCredentialInfo = new EncryCredentialInfo();
        if (authentication == null || principal == null) {
            return encryCredentialInfo;
        }
        SSOKey ssoKey = keyService.findByAppId(appId);
        if (ssoKey == null) {
            logger.error("no key for app id :{}", appId);
            throw NoSSOKeyException.INSTANCE;
        }
        encryCredentialInfo.setAppId(appId);
        encryCredentialInfo.setCreateTime(authentication.getAuthenticationDate());
        encryCredentialInfo.setUserId(principal.getId());
        encryCredentialInfo.setKeyId(ssoKey.getKeyId());
        encryCredentialInfo.setUser(principal.getAttributes().containsKey("user")? (User) principal.getAttributes().get("user") :null);

        Date expireDate = new Date((authentication.getAuthenticationDate().getTime() + DURATION));
        encryCredentialInfo.setExpiredTime(expireDate);
        return encryCredentialInfo;
    }

    public EncryCredentialManager getEncryCredentialManager() {
        return encryCredentialManager;
    }

    public void setEncryCredentialManager(EncryCredentialManager encryCredentialManager) {
        this.encryCredentialManager = encryCredentialManager;
    }

    public KeyService getKeyService() {
        return keyService;
    }

    public void setKeyService(KeyService keyService) {
        this.keyService = keyService;
    }

    public AppService getAppService() {
        return appService;
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public UserLoggedStatusStore getUserLoggedStatusStore() {
        return userLoggedStatusStore;
    }

    public void setUserLoggedStatusStore(UserLoggedStatusStore userLoggedStatusStore) {
        this.userLoggedStatusStore = userLoggedStatusStore;
    }
}

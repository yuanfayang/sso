package com.changhong.sso.core.service;

import com.changhong.sso.common.core.entity.App;
import com.changhong.sso.common.web.utils.WebConstants;
import com.changhong.sso.core.app.AppService;
import com.changhong.sso.core.authentication.status.UserLoggedStatus;
import com.changhong.sso.core.authentication.status.UserLoggedStatusStore;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/21
 * @Time: 17:53
 * @Email: flyyuanfayang@sina.com
 * @Description: 登出应用服务实现类
 */
public class LogoutAppServiceImpl implements LogoutAppService {
    private static Logger logger = LoggerFactory.getLogger(LogoutAppServiceImpl.class.getName());

    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    /**
     * 默认请求失败重试次数。
     */
    private static final int RETRY_TIMES = 3;

    private AppService appService;

    private UserLoggedStatusStore userLoggedStatusStore;

    @Override
    public void logoutApp(String userId, String service) {
        if (StringUtils.isEmpty(userId)) {
            return;
        }

        //servie对应的app
        App app = null;
        //先查找service对应的应用登出地址。
        if(!StringUtils.isEmpty(service)){
            app = appService.findAppByHost(service);
            if(app!=null){
                String logoutUrl = app.getLogoutUrl();
                //登出service对应的应用。
                requestLogoutUrl(logoutUrl, userId);
            }
        }
        this.logoutAppsExcludeServiceApp(userId, app);
    }

        /**
         * 请求登出URL地址。若登出失败则会自动重试
         */

    protected void requestLogoutUrl(String url, String userId) {
        if (StringUtils.isEmpty(url)) {
            return;
        }
        for (int i = 0; i < RETRY_TIMES; i++) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair(WebConstants.USER_ID_PARAM_NAME, userId));
            try {
                String content = requestUrl(url, nvps);
                //当登出成功，则不再重试。
                if (!StringUtils.isEmpty(content)) {
                    logger.info("logout sucess ,the url is " + url);
                    break;
                }
                logger.info(content);
            } catch (ClientProtocolException e) {
                logger.warn("request the url error", e);
            } catch (IOException e) {
                logger.warn("request the url error", e);
            }
        }
    }

    /**
     * 请求某个URL，带着参数列表。
     * @param url
     * @param nameValuePairs
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    protected String requestUrl(String url, List<NameValuePair> nameValuePairs) throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(url);
        if(nameValuePairs!=null && nameValuePairs.size()>0){
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity);
                EntityUtils.consume(entity);
                return content;
            }
            else{
                logger.warn("request the url: "+url+" , but return the status code is "+response.getStatusLine().getStatusCode());
                return null;
            }
        }
        finally{
            response.close();
        }
    }

    /**
     * 异步登出用户ID userId登录过的所有应用，排除app.
     * @param userId 用户ID.
     * @param serviceApp 要排除的app，该app已经登出过。
     */
    protected void logoutAppsExcludeServiceApp(String userId, App serviceApp) {
        List<UserLoggedStatus> list = this.userLoggedStatusStore.findUserLoggedStatus(userId);
        //批量查询对应的应用信息。
        if(list!=null&& list.size()>0){
            for(UserLoggedStatus status:list){
                App app = appService.finAppById(status.getAppId());
                if(app!=null){
                    //若该app已经登出过，则跳过。
                    if(serviceApp!=null && serviceApp.getAppId().equals(app.getAppId())){
                        continue;
                    }
                    String logoutUrl = app.getLogoutUrl();
                    //登出service对应的应用。
                    requestLogoutUrl(logoutUrl, userId);
                }
            }
        }
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public void setUserLoggedStatusStore(UserLoggedStatusStore userLoggedStatusStore) {
        this.userLoggedStatusStore = userLoggedStatusStore;
    }
}

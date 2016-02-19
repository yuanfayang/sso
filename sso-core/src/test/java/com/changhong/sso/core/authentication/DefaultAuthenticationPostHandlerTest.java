package com.changhong.sso.core.authentication;

import com.changhong.sso.common.core.authentication.EncryCredentialManager;
import com.changhong.sso.common.core.entity.App;
import com.changhong.sso.common.core.entity.EncryCredentialInfo;
import com.changhong.sso.common.core.entity.SSOKey;
import com.changhong.sso.common.core.service.KeyService;
import com.changhong.sso.common.web.utils.WebConstants;
import com.changhong.sso.core.app.AppService;
import com.changhong.sso.core.authentication.status.UserLoggedStatusStore;
import com.changhong.sso.exception.NoSSOKeyException;
import junit.framework.*;
import org.junit.*;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.authentication
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/19 16:34
 * @discription :
 */
public class DefaultAuthenticationPostHandlerTest extends TestCase {

    private DefaultAuthenticationPostHandler handler;

    public DefaultAuthenticationPostHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        handler = new DefaultAuthenticationPostHandler();
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testPostAuthentication() {

        /**
         * 测试传入错误参数的情况。
         */
        Authentication authentication = this.handler.postAuthentication(null, null);
        assertNotNull(authentication);
        junit.framework.Assert.assertNull(authentication.getPrincipal());

        /**
         * 测试参数正确的情况，但是SSO 服务器的app对象为空的情况。
         */
        String username = "test";
        String password = "pwdsssss";
        UsernamePasswordCredential credential = new UsernamePasswordCredential(username, password);
        DefaultUserPrincipal principal = new DefaultUserPrincipal();
        principal.setId(username);
        //设置模拟服务。
        AppService appService = Mockito.mock(AppService.class);
        Mockito.when(appService.findSSOServerApp()).thenReturn(null);
        this.handler.setAppService(appService);
        try {
            this.handler.postAuthentication(credential, principal);
            fail("应该抛出异常");
        } catch (NoSSOKeyException e) {
            e.printStackTrace();
        }

        /**
         * 测试参数正确的情况，但是ki4so 服务器的app对象不为空的情况。
         * 服务对应的key信息不为的正常情况。
         */
        String appId = "1000";
        String app2Id = "1001";
        String keyId = "1000000";
        String encStringValue = "ssssdafdsafdsafdsafdasfdsafdsa";
        String service = "http://loacahost:8080/ki4so-app/home.do";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(WebConstants.SERVICE_PARAM_NAME, service);
        credential.setParameters(parameters);
        App app = new App();
        app.setAppId(appId);

        App clientApp = new App();
        clientApp.setAppId(app2Id);

        SSOKey key = new SSOKey();
        key.setKeyId(keyId);

        Mockito.when(appService.findSSOServerApp()).thenReturn(app);
        Mockito.when(appService.findAppByHost(service)).thenReturn(clientApp);

        KeyService keyService = Mockito.mock(KeyService.class);
        Mockito.when(keyService.findByAppId(appId)).thenReturn(key);
        Mockito.when(keyService.findByAppId(app2Id)).thenReturn(key);

        this.handler.setKeyService(keyService);
        EncryCredentialManager encryCredentialManager = Mockito.mock(EncryCredentialManager.class);
        Mockito.when(encryCredentialManager.encrypt(Mockito.any(EncryCredentialInfo.class))).thenReturn(encStringValue);
        this.handler.setEncryCredentialManager(encryCredentialManager);

        UserLoggedStatusStore userLoggedStatusStore = Mockito.mock(UserLoggedStatusStore.class);
        this.handler.setUserLoggedStatusStore(userLoggedStatusStore);

        authentication = this.handler.postAuthentication(credential, principal);
        assertNotNull(authentication);
    }
}
package com.changhong.sso.core.app;

import com.changhong.sso.common.core.entity.App;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.logging.Logger;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/13
 * @Time: 19:36
 * @Email: flyyuanfayang@sina.com
 * @Description:
 */
public class AppServiceImplTest extends TestCase {
    private AppServiceImpl appService=new AppServiceImpl();
    private static Logger logger = Logger.getLogger(AppServiceImplTest.class.getName());


    @Test
    public void testFinAppById() throws Exception {
        App app =appService.finAppById("1");

        logger.info("app"+app.toString());
    }

    @Test
    public void testFindSSOServerApp() throws Exception {
        App SSOApp=appService.findSSOServerApp();

        logger.info("SSO app:"+SSOApp);
    }

    @Test
    public void testFindAppByHost() throws Exception {
        App app =appService.findAppByHost("http://www.sso.newb.com");

        logger.info("app"+app.toString());
    }

    @Test
    public void testLoadAppData() throws Exception {
        appService.loadAppData();
    }
}
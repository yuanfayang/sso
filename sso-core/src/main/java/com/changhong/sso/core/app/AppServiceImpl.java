package com.changhong.sso.core.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.changhong.sso.common.core.entity.App;
import com.changhong.sso.common.core.service.AppService;
import com.changhong.sso.core.dao.file.FileSystemDao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/13
 * @Time: 19:03
 * @Email: flyyuanfayang@sina.com
 * @Description: 默认的应用服务管理实现类，默认将应用信息存储在json格式的数据文件中
 */
public class AppServiceImpl extends FileSystemDao implements AppService {
    private static Logger logger = Logger.getLogger(AppServiceImpl.class.getName());

    /**
     * 外部数据文件的地址，优先级最高
     */
    public static final String DEFAULT_EXTERNAL_DATA = "G:\\YuanFayang\\workspace\\idea\\2016-01-30 sso\\app.json";

    /**
     * 默认数据文件的地址在clsspath下
     */
    public static final String DEFAULT_CLASSPATH_DATA = "app.json";

    /**
     * 应用的映射表，key是appId,value是应用的对象信息
     */
    private Map<String, App> appMap = null;

    /**
     * SSO服务器本身的应用配置信息
     */
    private App SSOServerApp = null;

    /**
     * 构造方法
     */
    public AppServiceImpl() {
        this.externalData = DEFAULT_EXTERNAL_DATA;
        this.classPathData = DEFAULT_CLASSPATH_DATA;
        //加载数据
        loadAppData();
    }

    @Override
    public App finAppById(String appId) {
        if (appMap != null) {
            return appMap.get(appId);
        }
        return null;
    }

    @Override
    public App findSSOServerApp() {
        return this.SSOServerApp;
    }

    @Override
    public App findAppByHost(String host) {
        Collection<App> apps = appMap.values();
        //先按照原始地址查找一遍。
        App app = findAppByUrl(apps, host);
        //若没有找到，再按照增加一个斜线"/"之后再查找一遍。
        if (app == null) {
            app = findAppByUrl(apps, host + "/");
        }
        return app;
    }

    @Override
    protected void loadAppData() {
        //先清空原来的数据
        if (appMap != null) {
            appMap.clear();
        }
        appMap = null;

        try {
            //读取数据文件
            String dataStr = this.readDataFromFile();
            //将读取到的应用列表转化为应用map.
            List<App> appList = JSON.parseObject(dataStr, new TypeReference<List<App>>() {
            });

            //为主机增加反斜线地址
            appendSlashToHost(appList);
            if (appList != null) {
                appMap = new HashMap<String, App>(appList.size());
                for (App app : appList) {
                    appMap.put(app.getAppId(), app);
                    //设置SSO应用服务器
                    if (SSOServerApp == null) {
                        if (app.isSSOServer()) {
                            this.SSOServerApp = app;
                        }
                    }
                }
                appList = null;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "load app data file error:", e);
            e.printStackTrace();
        }
    }

    /**
     * 为主机地址最后增加一个斜线“/”，若没有的话则追加一个，若有的话则不追加
     *
     * @param appList 应用列表
     */
    private void appendSlashToHost(List<App> appList) {
        if (appList != null && appList.size() > 0) {
            for (App app : appList) {
                //若应用的主机地址不为空，且不以斜线结尾，则追加一个斜线。
                if (app.getHost() != null && app.getHost().length() > 0 && !app.getHost().endsWith("/")) {
                    app.setHost(app.getHost() + "/");
                }
                //否则不做处理。
            }
        }
    }

    private App findAppByUrl(Collection<App> apps, String url) {
        if (url == null || url.length() == 0) {
            return null;
        }

        for (App app : apps) {
            if (!org.springframework.util.StringUtils.isEmpty(app.getHost()) && url.startsWith(app.getHost())) {
                return app;
            }
        }
        return null;
    }
}

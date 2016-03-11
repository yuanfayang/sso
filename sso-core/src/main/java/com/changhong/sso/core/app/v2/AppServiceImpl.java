package com.changhong.sso.core.app.v2;

import com.changhong.sso.common.core.entity.App;
import com.changhong.sso.common.core.service.AppService;
import com.changhong.sso.core.dao.AppDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.app.v2
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/27 13:07
 * @discription : V2版本中的app服务实现类
 */
@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private AppDao appDao;

    @Override
    public App finAppById(String appId) {
        return appDao.finAppById(appId);
    }

    @Override
    public App findSSOServerApp() {
        return appDao.findSSOservie();
    }

    @Override
    public App findAppByHost(String host) {
        List<App> appList = appDao.findAll();
        for (App app : appList) {
            if (!StringUtils.isEmpty(app.getHost()) && host.startsWith(app.getHost())) {
                return app;
            }
        }
        return null;
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
}

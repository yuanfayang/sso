package com.changhong.sso.core.key.v2;

import com.changhong.sso.common.core.entity.SSOKey;
import com.changhong.sso.common.core.service.KeyService;
import com.changhong.sso.core.dao.SSOKeyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.key.v2
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/27 14:12
 * @discription :
 */
@Service
public class KeyServiceImpl implements KeyService {
    @Autowired
    private SSOKeyDao ssoKeyDao;

    /**
     * 按照秘钥id查找秘钥
     * @param keyId 秘钥ID
     * @return
     */
    @Override
    public SSOKey findByKeyId(String keyId) {
        return ssoKeyDao.findByKeyId(keyId);
    }

    /**
     * 按应用id查找秘钥
     * @param appId 应用ID
     * @return
     */
    @Override
    public SSOKey findByAppId(String appId) {
        return ssoKeyDao.findByAppId(appId);
    }

    @Override
    public boolean checkKeyFileExistByToken(String token) {
        return false;
    }

    @Override
    public Object generateKeyFile(String token) throws Exception {
        return null;
    }
}

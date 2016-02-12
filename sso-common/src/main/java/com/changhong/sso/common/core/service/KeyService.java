package com.changhong.sso.common.core.service;

import com.changhong.sso.common.core.entity.SSOKey;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/12
 * @Time: 14:47
 * @Email: flyyuanfayang@sina.com
 * @Description: 非对称加密秘钥Service，保证加密key的安全性
 */
public interface KeyService {
    /**
     * 根据秘钥的ID查找对应的秘钥信息
     * @param keyId 秘钥ID
     * @return 秘钥信息
     */
    SSOKey findByKeyId(String keyId);

    /**
     * 根据应用ID查找对应的秘钥信息
     * @param appId 应用ID
     * @return 秘钥信息
     */
    SSOKey findByAppId(String appId);

    /**
     * 判断秘钥文件是否生成
     * @param token 判断文件是否存在的标识
     * @return true：私钥文件已存在，false：私钥文件尚未生成
     */
    boolean checkKeyFileExistByToken(String token);

    /**
     * 生成非对称加密文件（客户端生成私钥文件；服务器生成公钥文件）
     * @param token 判断文件是否存在的标识
     * @return 返回信息
     * @throws Exception 异常信息
     */
    Object generateKeyFile(String token) throws Exception;
}

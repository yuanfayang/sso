package com.changhong.sso.common.core.authentication;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.changhong.sso.common.Base64Coder;
import com.changhong.sso.common.DESCoder;
import com.changhong.sso.common.core.entity.EncryCredentialInfo;
import com.changhong.sso.common.core.entity.SSOKey;
import com.changhong.sso.common.core.entity.User;
import com.changhong.sso.common.core.service.KeyService;
import com.changhong.sso.common.utils.StringUtil;
import com.changhong.sso.exception.InvalidEncryededentialException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/12
 * @Time: 17:23
 * @Email: flyyuanfayang@sina.com
 * @Description: EncryCredentialManager实现类
 */
public class EncryCredentialManagerImpl implements EncryCredentialManager {
    private static final Logger logger = LoggerFactory.getLogger(EncryCredentialManagerImpl.class.getName());

    private static final String ENCODE = "UTF-8";

    private KeyService keyService;

    public void setKeyService(KeyService keyService) {
        this.keyService = keyService;
    }

    @Override
    public EncryCredentialInfo decrypt(EncryCredential encryCredential) throws InvalidEncryededentialException {
        //判空
        if (encryCredential != null && !StringUtils.isEmpty(encryCredential.getCredential())) {
            String credential = encryCredential.getCredential();
            return parseEncryCredential(credential);
        }
        return null;
    }

    /**
     * SSO ticket编码流程
     * 1.加入凭证信息的敏感字段：userId，createTime和expireTime等字段
     * 注：createTime和expireTime都为unix时间戳
     * 组合成json格式的数据，然后使用密钥对该字符串进行DES加密，再将加密后的字符串通过Base64编码。
     * 2.再将上述加密串与其他非敏感信息拼接，格式如是:[敏感信息加密串]？appId=1&keyID=2
     * 其中敏感信息加密串为第一步得到的结果，appId为应用标识，keyId为秘钥标识
     * 3.使用URL进行编码。防止tomcat7下报cookie错误
     *
     * @param encryCredentialInfo 加密前的用于加密和编码的凭证信息
     * @return 加密后的综合凭证
     */

    @Override
    public String encrypt(EncryCredentialInfo encryCredentialInfo) {
        logger.info("开始加密敏感信息.");
        StringBuffer stringBuffer = new StringBuffer();
        if (encryCredentialInfo != null) {
            try {
                String data = encryptSensitiveInfo(encryCredentialInfo);
                logger.info("加密后的用户敏感信息是:{}", data);
                stringBuffer
                        .append(data).append("?appId=")
                        .append(encryCredentialInfo.getAppId())
                        .append("&keyId=")
                        .append(encryCredentialInfo.getKeyId());
                //先进行Base64编码，再进行URL编码，避免传输错误
                logger.info("半明文用户认证:{}", stringBuffer.toString());
                logger.info("全密文的用户认证:{}", URLEncoder.encode(Base64Coder.encryptBASE64(stringBuffer.toString().getBytes()), ENCODE));
                return StringUtil.encodeBase64URLSafeString(stringBuffer.toString());
            } catch (Exception e) {
                logger.error("encrypt data exception:{}", e.getMessage());
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }

    @Override
    public boolean checkEncryCredentialInfo(EncryCredentialInfo encryCredentialInfo) {
        if (encryCredentialInfo != null) {
            //无凭据对应的用户标识，则无效。
            if (StringUtils.isEmpty(encryCredentialInfo.getUserId())) {
                return false;
            }
            Date now = new Date();
            if (encryCredentialInfo.getExpiredTime() != null) {
                //将未来过期时间减去当前时间。
                long deta = encryCredentialInfo.getExpiredTime().getTime() - now.getTime();
                //若差值大于0，表示过期时间还没有到，凭据继续可以有效使用。
                if (deta > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private EncryCredentialInfo parseEncryCredential(String credential) throws InvalidEncryededentialException {
        EncryCredentialInfo encryCredentialInfo = new EncryCredentialInfo();
        try {
            /*//先进行URL解码
            credential = URLDecoder.decode(credential, ENCODE);
            //再进行BASE64解码
            credential = new String(Base64Coder.decryptBASE64(credential));*/
            credential = new String(StringUtil.decodeBase64URLSafeString(credential.trim()), ENCODE);

            logger.info("待处理敏感信息的用户凭证:{}", credential);

            //问号分割字符串
            String[] items = credential.split("\\?");
            //如果长度为2，即是敏感信息和不敏感信息拼接的结果
            if (items.length == 2) {
                //第二个字符串不为空，先解析第二个字符串
                if (!StringUtils.isEmpty(items[1])) {
                    //使用&分割
                    String[] params = items[1].split("&");
                    for (String param : params) {
                        //使用=分割
                        String[] values = param.split("=");
                        if (values != null && values.length == 2) {
                            if ("appId".equals(values[0])) {
                                encryCredentialInfo.setAppId(values[1]);
                            } else if ("keyId".equals(values[0])) {
                                encryCredentialInfo.setKeyId(values[1]);
                            }
                        }
                    }
                } else {
                    throw InvalidEncryededentialException.INSTANCE;
                }

                //第一个字符串不为空
                if (!StringUtils.isEmpty(items[0])) {
                    //解码敏感信息
                    //先进行Base64解码
                    //byte[] data = Base64Coder.decryptBASE64(items[0]);
                    byte[] data = StringUtil.decodeBase64URLSafeString(items[0]);
                    //查询秘钥
                    SSOKey ssoKey = keyService.findByKeyId(encryCredentialInfo.getKeyId());
                    if (ssoKey != null) {
                        //使用秘钥进行解密
                        byte[] origin = DESCoder.decrypt(data, ssoKey.toSecurityKey());
                        //将byte数组转化成字符串
                        String jsonStr = new String(origin);
                        Map<String, Object> map = (Map<String, Object>) JSON.parse(jsonStr);
                        if (map != null) {
                            Object userId = map.get("username");
                            Object createTime = map.get("createTime");
                            Object expireTime = map.get("expireTime");
                            Object token = map.get("token");
                            Object user = map.get("user");
                            encryCredentialInfo.setUserId(userId == null ? null : userId.toString());
                            encryCredentialInfo.setCreateTime(createTime == null ? null : new Date(Long.parseLong(createTime.toString())));
                            encryCredentialInfo.setExpiredTime(expireTime == null ? null : new Date(Long.parseLong(expireTime.toString())));
                            encryCredentialInfo.setUser(user == null ? null : JSON.parseObject(user.toString(), User.class));
                            encryCredentialInfo.setToken(token == null ? null : token.toString());
                        }
                    }
                } else {
                    throw InvalidEncryededentialException.INSTANCE;
                }
            } else {
                throw InvalidEncryededentialException.INSTANCE;
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("parse encry credential exception:{}", e);
            e.printStackTrace();
            throw InvalidEncryededentialException.INSTANCE;
        } catch (Exception e) {
            logger.error("parse encry credential exception:{}", e);
            e.printStackTrace();
            throw InvalidEncryededentialException.INSTANCE;
        }
        return encryCredentialInfo;
    }

    /**
     * 加密敏感信息
     *
     * @param encryCredentialInfo 原始加密凭证信息
     * @return 加密后的凭证
     */
    private String encryptSensitiveInfo(EncryCredentialInfo encryCredentialInfo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", encryCredentialInfo.getUserId());
        map.put("token", encryCredentialInfo.getUser().getToken());
        map.put("user", encryCredentialInfo.getUser());
        if (encryCredentialInfo.getCreateTime() != null) {
            map.put("createTime", encryCredentialInfo.getCreateTime());
        }
        if (encryCredentialInfo.getExpiredTime() != null) {
            map.put("expireTime", encryCredentialInfo.getExpiredTime());
        }

        //查询私钥键值
        SSOKey ssoKey = keyService.findByKeyId(encryCredentialInfo.getKeyId());
        if (ssoKey != null) {
            //查询键值
            Key key = ssoKey.toSecurityKey();
            if (key != null) {
                logger.info("加密前的敏感信息:{}", JSONObject.toJSONString(map));

                byte[] data = DESCoder.encrypt(JSONObject.toJSONString(map).getBytes("UTF-8"), key);

                //先用Base64编码，再用URL编码
                return StringUtil.encodeBase64URLSafeString(data);
            }
            return "";
        }
        return "";
    }
}

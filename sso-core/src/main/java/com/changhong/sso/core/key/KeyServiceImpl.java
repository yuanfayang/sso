package com.changhong.sso.core.key;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.changhong.sso.common.core.entity.SSOKey;
import com.changhong.sso.common.core.service.KeyService;
import com.changhong.sso.core.dao.file.FileSystemDao;
import com.changhong.sso.exception.ParamsInitiatedIncorrectlyException;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.*;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/13
 * @Time: 22:03
 * @Email: flyyuanfayang@sina.com
 * @Description: 读取key的管理实现类
 */
public class KeyServiceImpl extends FileSystemDao implements KeyService {
    private static Logger logger = Logger.getLogger(KeyServiceImpl.class.getName());

    /**
     * 外部文件地址，优先级最高
     */
    private static final String DEFAULT_EXTERNAL_DATA = "G:\\YuanFayang\\workspace\\idea\\2016-01-30 sso\\keys.json";

    /**
     * 默认的数据文件地址在classpath下
     */
    private static final String DEFAULT_KEY_PATH = "keys.json";

    /**
     * 指定公钥存放文件路径，默认是classPath
     */
    private static String PUBLIC_KEY_PATH = null;

    /**
     * 指定公钥存放文件名
     */
    private static String PUBLIC_KEY_FILE = null;
    /**
     * 密钥长度，用来初始化
     */
    private static final int KEYSIZE = 1024;
    /**
     * 指定加密算法为RSA
     */
    private static final String ALGORITHM = "RSA";

    /**
     * 秘钥映射表，key是keyId,value是Key对象。
     */
    private Map<String, SSOKey> keyMap = null;

    /**
     * 秘钥映射表，key是appId,value是Key对象。
     */
    private Map<String, SSOKey> appIdMap = null;

    public KeyServiceImpl() {
        this.externalData = DEFAULT_EXTERNAL_DATA;
        this.classPathData = DEFAULT_KEY_PATH;
        //加载数据。
        loadAppData();
    }

    @Override
    protected void loadAppData() {
        try {
            String s = this.readDataFromFile();
            //将读取的应用列表转换为应用map。
            List<SSOKey> keys = JSON.parseObject(s, new TypeReference<List<SSOKey>>() {
            });
            if (keys != null) {
                keyMap = new HashMap<String, SSOKey>(keys.size());
                appIdMap = new HashMap<String, SSOKey>(keys.size());
                for (SSOKey key : keys) {
                    keyMap.put(key.getKeyId(), key);
                    appIdMap.put(key.getAppId(), key);
                }
                keys = null;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "load app data file error.", e);
        }
    }

    @Override
    public SSOKey findByKeyId(String keyId) {
        SSOKey ssoKey = null;
        if (this.keyMap != null) {
            ssoKey = this.keyMap.get(keyId);
            return ssoKey;
        }
        return null;
    }

    @Override
    public SSOKey findByAppId(String appId) {
        SSOKey ssoKey = null;
        if (this.appIdMap != null) {
            ssoKey = this.appIdMap.get(appId);
            return ssoKey;
        }
        return null;
    }

    @Override
    public boolean checkKeyFileExistByToken(String token) {
        //获取当前应用的appId
        SSOKey ssoKey = appIdMap.get(token);
        //获取公钥的存储路径
        PUBLIC_KEY_PATH = ssoKey.getKeyPath();
        //获取公钥文件名
        PUBLIC_KEY_FILE = PUBLIC_KEY_PATH + token;
        File keyFile = new File(PUBLIC_KEY_FILE);
        return keyFile.exists();
    }

    /**
     * @param token 判断文件是否存在的标识
     * @return 私钥文件
     * @throws Exception
     */
    @Override
    public Object generateKeyFile(String token) throws Exception {
        //判断应用ＩＤ列表是否为空
        if (appIdMap == null || StringUtils.isEmpty(token)) {
            throw ParamsInitiatedIncorrectlyException.INSTANCE;
        }

        //RSA算法要求有一个可信任的随机数据源
        SecureRandom secureRandom = new SecureRandom();
        //为RSA算法创建一个KeyPairGenertator对象
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        //利用上面的数据源初始化这个KeyPairGenerator对象
        keyPairGenerator.initialize(KEYSIZE, secureRandom);
        keyPairGenerator.initialize(KEYSIZE);

        //生成秘钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        //得到私钥
        Key privateKey = keyPair.getPrivate();

        ObjectOutputStream objectOutputStream = null;
        try {
            //用对象流将生成的公钥生成文件
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));
            objectOutputStream.writeObject(publicKey);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //清空缓存，关闭输出流
            objectOutputStream.close();
        }
        return privateKey;
    }

    /**
     * 使用公钥将key加密
     *
     * @param token    公钥文件标识
     * @param keyValue 需要加密的key
     * @return 加密后的key
     */
    public String encryptKey(String token, String keyValue) throws Exception {
        String encryptKey = null;
        if (checkKeyFileExistByToken(token)) {    //判断公钥文件是否存在
            Key publicKey = loadPublicKey(); //加载公钥文件
            /** 得到Cipher对象来实现对源数据的RSA加密 */
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = keyValue.getBytes();
            /** 执行公钥加密操作 */
            byte[] encryptValue = cipher.doFinal(bytes);
            //使用Base64加密
            BASE64Encoder encoder = new BASE64Encoder();
            encryptKey = encoder.encode(encryptValue);
        } else {
            throw ParamsInitiatedIncorrectlyException.INSTANCE;
        }
        //返回加密后的key
        return encryptKey;
    }

    /**
     * @return 用Base64加密后的私钥文件
     * @throws Exception
     */
    public Key loadPublicKey() {
        Key publicKey = null;
        ObjectInputStream ois = null;
        try {
            /** 将文件中的公钥对象读出 */
            ois = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
            publicKey = (Key) ois.readObject();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return publicKey;
    }
}

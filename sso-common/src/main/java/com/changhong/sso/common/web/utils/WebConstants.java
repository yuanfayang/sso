package com.changhong.sso.common.web.utils;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.common.web.utils
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 16:35
 * @discription :web相关的常量类，定义了与web相关的所有常量值。
 */
public interface WebConstants {
    /**
     * SSO中心认证服务器写入到用户web客户端cookie中的加密平局的键值
     */
    public static final String SSO_SERVER_EC_COOKIE_KEY="SSO_SERVER_EC";

    /**
     * SSO客户端应用服务器写入到web客户端cookie中的认证加密后的凭证的键值
     */
    public static final String SSO_CLIENT_COOKIE_KEY="SSO_CLIENT_EC";

    /**
     * 目的服务地址service的参数名
     */
    public static final String SERVICE_PARAM_NAME="service";

    /**
     * 目的服务器地址存储在SESSION中的key值
     */
    public static final String SSO_SERVICE_KEY_IN_SESSION="SSO_SERVICE_KEY";

}

package com.changhong.sso.core.authentication;

import com.changhong.sso.common.core.authentication.Credential;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.core.authentication
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/2/17 15:53
 * @discription :认证成功后的处理器，该接口的职责是将认证主体、用户凭证转化成一个合适的认证结果对象。根据用户凭证中的信息，参数进行合适转换
 */
public interface AuthenticationPostHandler {

    /**
     * sso服务本身加密凭证信息存储在验证结果对象authentication的动态的属性attributes的key
     */
    public static final String SSO_SERVER_EC_KEY="sso_server_ec_key";

    /**
     * sso服务本身的加密信息存储在验证结果对象authentication的动态属性attributes中的key值
     */
    public static final String SSO_CLIENT_EC_KEY="sso_client_ec_key";

    /**
     * 认证后的处理方法，将用户的凭证和主体转化为一个认证结果对象
     * @param credential 用户凭证
     * @param principal 用户主体
     * @return 认证结果对象
     */
    Authentication postAuthentication(Credential credential,Principal principal);
}

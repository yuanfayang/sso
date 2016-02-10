package com.changhong.sso.exception;

/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/10
 * @Time: 14:29
 * @Email: flyyuanfayang@sina.com
 * @Description: 异常的常量类
 */
public class ExceptionConstants {
    /**
     * 用户凭证不合法的异常信息
     */
    public static final String INVALID_CREDENTIAL_CODE="1000";
    public static final String INVALID_CREDENTIAL_MSGKEY="invalid user credential";

    /**
     * 用户凭证为空的异常信息
     */
    public static final String EMPTY_CREDENTIAL_CODE="1001";
    public static final String EMPTY_CREDENTIAL_MSGKEY="user credential is null";

    /**
     * 不合法的已认证凭证异常信息
     */
    public static final String INVALID_ENCRYCREDENTIAL_CODE="1002";
    public static final String INVALID_ENCRYCREDENTIAL_MSGKEY="invalid encry user credential";

    /**
     * 无认证处理器异常信息
     */
    public static final String NO_AUTHENTICATIONPOSTHANDLER_CODE="1003";
    public static final String NO_AUTHENTICATIONPOSTHANDLER_MSGKEY="no authentication posthandler";

    /**
     * 无SSO秘钥异常信息
     */
    public static final String NO_SSOKEY_CODE="1004";
    public static final String NO_SSOKEY_MSGKEY="no SSO key";

    /**
     * 参数初始化异常信息
     */
    public static final String PARAMS_INITIALTED_INCORRECTLY_CODE="1005";
    public static final String PARAMS_INITIALTED_INCORRECTLY_MSGKEY="parameters initial incorrectly";

    /**
     * 密码不合法异常信息
     */
    public static final String PASSWORD_INVALID_CODE="1006";
    public static final String PASSWORD_INVALID_MSGKEY="parameters initial incorrectly";

    /**
     * 不支持的身份凭证异常信息
     */
    public static final String UNSUPPORTED_CREDENTIALS_CODE="1007";
    public static final String UNSUPPORTED_CREDENTIALS_MSGKEY="unsupported credentials";

    /**
     * 用户名非法异常信息
     */
    public static final String INVALID_USERNAME_CODE="1008";
    public static final String INVALID_USERNAME_MSGKEY="invalid username";

    /**
     * 用户名或密码为空异常信息
     */
    public static final String EMPTY_USERNAMEORPASSWORD_CODE="1009";
    public static final String EMPTY_USERNAMEORPASSWORD_MSGKEY="username or password is empty";

    /**
     * 用户名或密码不合法异常信息
     */
    public static final String INVALID_USERNAMEORPASSWORD_CODE="1010";
    public static final String INVALID_USERNAMEORPASSWORD_MSGKEY="username or password is invalid";
}

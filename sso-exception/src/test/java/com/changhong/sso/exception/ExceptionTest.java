package com.changhong.sso.exception;

import com.sun.deploy.uitoolkit.ui.LoggerConsole;
import org.junit.Test;

import java.util.logging.Logger;


/**
 * @Author: Fayang Yuan
 * @Date: 2016/2/10
 * @Time: 14:50
 * @Email: flyyuanfayang@sina.com
 * @Description:
 */
public class ExceptionTest {
    private static final Logger logger = Logger.getLogger(ExceptionTest.class.toString());

    @Test
    public void testEmptyCredential(){
        EmptyCredentialException emptyCredentialException = EmptyCredentialException.INSTANCE;
        throw emptyCredentialException;
    }

    @Test
    public void testInvalidEncryededential(){
        InvalidEncryededentialException invalidEncryededentialException = InvalidEncryededentialException.INSTANCE;
        logger.info(invalidEncryededentialException.getCode()+"  "+invalidEncryededentialException.getMsgKey());
        throw invalidEncryededentialException;
    }
}

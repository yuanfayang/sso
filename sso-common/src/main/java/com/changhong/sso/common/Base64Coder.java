package com.changhong.sso.common;

import java.io.IOException;

public class Base64Coder {
	  /**  
     * 编码  
     * @param filecontent  
     * @return String  
     */ 
	public static String encryptBASE64(byte[] bstr){
		if(bstr==null || bstr.length==0){
			return null;
		}
		return new sun.misc.BASE64Encoder().encode(bstr);  
    }  
 
    /**  
     * 解码  
     * @param
     * @return string  
     */ 
	public static byte[] decryptBASE64(String str){  
		if(str==null || str.length()==0){
			return null;
		}
	    byte[] bt = null;  
	    sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();  
	    try {
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			throw new RuntimeException("decrypt base64 error.", e);
		}  
        return bt;  
    }  
}

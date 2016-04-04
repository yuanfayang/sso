package com.changhong.sso.common.web.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：Yuan Fayang
 * @package ; com.changhong.sso.common.web.utils
 * @email : fayang.yuan@changhong.com
 * @dateTime : 2016/3/20 12:44
 * @discription : http请求工具类
 */
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private static final String ENCODE = "UTF-8";

    private static CloseableHttpClient httpClient = HttpClients.createDefault();


    /**
     * http post 请求基础方法
     *
     * @param url    请求的接口地址
     * @param params 请求的参数表
     * @return 响应实体
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nameValuePairList = new ArrayList<>();

        //组装参数列表
        if (params != null) {
            for (String key : params.keySet()) {
                NameValuePair nameValuePair = new BasicNameValuePair(key, params.get(key));
                nameValuePairList.add(nameValuePair);
            }
        }

        //向httppost中添加参数
        if (nameValuePairList.size() > 0) {
            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList, ENCODE);
                httpPost.setEntity(urlEncodedFormEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        //执行http请求
        HttpResponse response = null;
        try {
            Date startTime=new Date();
            logger.info("请求开始时间:{}",startTime.getTime());
            response = httpClient.execute(httpPost);
            Date endTime=new Date();
            logger.info("请求结束时间;{}",endTime.getTime());
            logger.info("请求耗时;{}ms",endTime.getTime()-startTime.getTime());

            return resolveResponseHttpEntityAsString(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //最后释放连接,防止形成死连接
            httpPost.releaseConnection();
        }
        return null;
    }

    /**
     * http get请求基础方法
     *
     * @param url    地址
     * @param params 参数
     * @return 响应实体
     * @throws IOException
     */
    public static String doGet(String url, Map<String, String> params) {
        //组装参数列表
        if (params != null) {
            StringBuilder stringBuilder = new StringBuilder();
            // 判断URL中是否已经包含了参数
            if (url.indexOf("?") != -1) {
                stringBuilder.append("&");
            } else {
                stringBuilder.append("?");
            }
            for (String key : params.keySet()) {
                stringBuilder.append(key)
                        .append("=")
                        .append(params.get(key))
                        .append("&");
            }
            //去除末尾的"&"
            if (stringBuilder.lastIndexOf("&") == stringBuilder.length() - 1) {
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
            }

            url = url + stringBuilder;
        }

        //执行get请求
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            return resolveResponseHttpEntityAsString(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
        return null;
    }

    /**
     * http post json 请求
     *
     * @param url        地址
     * @param jsonObject json参数
     * @return 响应报文
     */
    public static String doPostJSON(String url, JSONObject jsonObject) {
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        try {
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
            httpPost.addHeader(HTTP.CONTENT_ENCODING, HTTP.UTF_8);

            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            stringEntity.setContentEncoding(HTTP.UTF_8);
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));

            httpPost.setEntity(stringEntity);

            Date startTime=new Date();
            logger.info("http请求开始时间:{}", startTime.getTime());
            response = httpClient.execute(httpPost);

            Date endTime=new Date();
            logger.info("请求结束时间;{}",endTime.getTime());
            logger.info("请求耗时;{}ms",endTime.getTime()-startTime.getTime());
            return resolveResponseHttpEntityAsString(response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return null;
    }

    /**
     * 请求rest接口,返回response
     *
     * @param url        请求地址
     * @param jsonObject json参数
     * @return
     */
    public static HttpResponse postJson(String url, JSONObject jsonObject) {
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;

        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
        httpPost.addHeader(HTTP.CONTENT_ENCODING, HTTP.UTF_8);

        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(jsonObject.toString());

            stringEntity.setContentEncoding(HTTP.UTF_8);
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));

            httpPost.setEntity(stringEntity);

            response = httpClient.execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return response;
    }


    /**
     * 按字符串形式获取响应实体
     *
     * @param response http response
     * @return
     */
    public static String resolveResponseHttpEntityAsString(HttpResponse response) {
        if (response == null) {
            return null;
        }

        HttpEntity httpEntity = response.getEntity();
        try {
            //按UTF-8获取报文
            String resutlStr = EntityUtils.toString(httpEntity, ENCODE);
            logger.info("response 报文-->{}", resutlStr);
            return resutlStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

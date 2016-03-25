#SSO接入规范

###1.服务地址
服务地址：http://111.9.116.181:20160/sso

###2.接口规范
####2.1使用SSO默认登录视图单点登录
地址： /api/login

功能说明：调用SSO中心服务的登录功能，进行登录鉴权和获取访问SSO注册应用服务的ticket


请求方式：request请求重定向（即应用服务将用户请求重定向到SSO中心服务）

请求参数说明：

|参数名称|参数类型|是否必须|描述|
|-----|:----:|:----:|:----|
|appId|String|是|SSO中心服务为应用颁发的应用编号|
|service|String|是|用户原始请求的URL，通常为应用服务中受保护资源的地址|

返回说明：

用户在SSO登录成功后，SSO中心服务会颁发SSO中心的授权ticket（Cookie），下发用户访问当前应用服务的加密凭证(SSO_CLIENT_EC)。SSO服务完成用户授权后，会转发用户请求到service指定的资源，并将加密后的用户凭证信息交由应用服务处理，已判断用户的合法性。如下是SSO服务最终生成的请求URL示例：

```
http://111.9.116.181:20161/app1/sso-app/home?SSO_CLIENT_EC=bGI0NlVzU3d1Q2VZSURSVHZKRFkrTXh5R1phYXJtYmpJT05VajA4UU5FQ1NhZmV2UWY3RDZrWVI0%0AVTZmajhDVGlpeFBNRGxhM1MwbApuNlhhMHJaUE0rZ0xqUjhVbHZGYz9hcHBJZD0xMDAxJmtleUlk%0APTI%3D
```

返回参数说：

|参数名称|参数类型|是否必须|描述|
|-----|:----:|:----:|:----|
|SSO_CLIENT_EC|String|是|SSO颁发的用户访问应用服务的加密凭证，应用服务需解密以判断凭证的合法性。|

####2.2自定义视图单点登录(跨域登录)

地址：/api/rest/login

功能说明：用于自定义登录视图使用SSO中心服务的单点的登录功能.为避免用户重复输入用户名和密码，应用服务可在在向用户展示登录界面前，带上必要参数探询本接口，已确认用户是否已在SSO中心服务器登录，可在确认未登录时展示登录UI。

请求方式：Http POST

Content-type：x-www-form-urlencoded

请求参数说明：

|参数名称|参数类型|是否必须|描述|
|-----|:----:|:----:|:----|
|appId|String|是|SSO中心服务为应用颁发的应用编号|
|service|String|是|用户原始请求的URL，通常为应用服务中受保护资源的地址|
|username|String|否|用户名|
|password|String|否|密码|

成功响应:200 OK

应答报文：  

```  
{  
  "code": "200",         								//状态码
  "msg": "登录成功！",    							  	//执行信息
  "data": {												//返回数据项
    "user": {											//用户基本信息
      "mail": "api-test@proedge.hk",
      "name": "api-test",
      "realName": "api-test",
      "token": "k3CH2XQ4_c5wqFBp_DVyIHk02mtIwSM26QsXB6G88v4",//用户本次访问的token，对应用户中心的token
      "uid": "6"
    },
    "SSO_CLIENT_EC":"dGJDYndPM2x4ZWVrY1hKMkdsY1NtdllhcjhVZUVMelBLWnZSRUZaMUhtZDZzZE9LU1hkV0cvWUZs%0AM2JKNUxGdGlyTXNWRFg3aUtoTQprR1NNOVluYzNLYkMxSTR1V05LZWRuWmVjMlNHK041U1VzS1NM%0ASm4wbjJEMFhLVFc1Z3VHYTFPU1o4YVNkem5pbVNIb1ZYQlVWUmlZCkpxNWs2dkQ1dC9zWjBkSkRm%0Ac2hheU80TUtGanl4K1dFTHg5Q1RZVmdqWGF4REhMZWwvTGFrUTB0VUErMEZham1hN2krbm53ckN0%0AZXgKaG1iK28vbjVPQldhM3NZbzhVcHNwNE9LQ1JtcXBOUTRqb2ltODc4bm1sYzJJT054c1Rqd01p%0AOTB2VHdTUWpNUnEvL3E4QmlXTXZDWApZbW01Rm1KTCtiN0VvTjZya3A0TExkNTFVaXA1TEQ1WW90%0AdHJrT2g3blJkUlpSZVFtSDZNYW5yRHB5U1g3dE82MkgwamdnPT0%2FYXBwSWQ9M2IwZDU1NmQzYWRk%0ANDIwNTk2YTlhZmRkNThmMWJjMTAma2V5SWQ9Mg%3D%3D"          //加密凭证
  }
}
```
返回参数说明：

SSO_CLIENT_EC是交由请求参数中service对应的应用服务平台验证的用户的加密凭证。若service对应的不是本应用程序，而是SSO体系中的其他兄弟应用服务，需要在转发请求到service时，带上SSO_CLIENT_EC，形如  

```
service?SSO_CLIENT_EC=xxxxxxxx
```

若service对应本应用，可不做处理。  
  
异常响应：401 Unauthorized

异常应答报文：
```  
{
  "code"：错误状态码，
  "msgKey"：异常信息
}
```
异常这说明:

|code|msgKey|描述|
|:----:|:----|:----|
|1001|user credential is null|用户凭证为空|
|1010|username or password is invalid|用户名或密码错误|

####2.3获取应用解密秘钥

地址：/api/fetchKey

功能说明：应用服务获取解密私钥

请求方式：Http POST

请求参数说明：

|参数名称|参数类型|是否必须|描述|
|-----|:----:|:----:|:----|
|appId|String|是|应用服务的编号|

返回形式：json

返回参数说明：

|参数名称|参数类型|是否必须|描述|
|-----|:----:|:----:|:----|
|keyId|int||秘钥编号||
|appId|String|是|应用服务编号|
|value|String|是|私钥值|

####2.4统一注销（引用统一注销js文件）

地址：/api/logoutJs

请求参数说明：

|参数名称|参数类型|是否必须|描述|
|-----|:----:|:----:|:----|
|appId|String|是|应用服务的编号|
|logoutSuccessUrl|String|是|注销后跳转的地址|

接口使用说明：此接口为视图文件中引入js文件，以实现统一注销之用

HTML获取文件示例：
```
<script type="application/javascript" src="http://127.0.0.1:8080/sso/api/logoutJs?appId=3b0d556d3add420596a9afdd58f1bc10&logoutSuccessUrl=http://www.baidu.com"></script>
```

HTML中实现统一注销功能示例：

```
<a href="javascript:logout()">统一注销</a>
```

### 3接入指南
####3.1对应用服务的要求
>1. 应用服务需实现登录拦截
>2. 应用服务与用户间的会话状态用Session实现
>1. 应用服务平台需提供客户端（浏览器）注销退出，操作当前Session失效的API

####3.2单点登录集成（需要使用SSO登录界面）
下面将通过J2EE应用服务的集成单点登录的实例阐述应用服务平台集成单点登录时需要实现的操作。

一下是实现登录拦截过滤器的核心代码：SSOClientFilter.java
```
@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();

        //监测是否在本地应用登录
        //若未在本应用登录，则开始SSO登录流程
        if (session.getAttribute(USER_STAT_IN_SESSION_KEY) == null) {

            //查找参数中是否存在SSO_CLIENT_EC，若不存在，则重定向到登录页
            //SO_CLIENT_EC是SSO服务器回调时传给应用服务的参数，http:url?SO_CLIENT_EC=xxxxx
            String ssoClientEC = request.getParameter("SO_CLIENT_EC");
            if (StringUtils.isEmpty(ssoClientEC)) {
				//若为获取到加密的身份信息则重定向请求到SSO服务器
                httpServletResponse.sendRedirect(buildRedirectToSSOServer(httpServletRequest));
                return;
            }

            //从SSO服务获取当前应用服务的秘钥
            if (ssoKey == null) {
                ssoKey = keyService.findByAppId(应用服务的AppId);
            }

            //用获取到的私钥解密凭证
            EncryCredentialInfo encryCredentialInfo = this.encryCredentialManager.decrypt(new EncryCredential(ssoClientEC));

            if (encryCredentialInfo != null) {
                //检验凭证的合法性
                boolean valid = this.encryCredentialManager.checkEncryCredentialInfo(encryCredentialInfo);

                if (valid) {
				
                    //如果合法，建立与本应用服务的session会话
                    session.setAttribute(USER_STAT_IN_SESSION_KEY, encryCredentialInfo);
                    if (appClientLoginHandler != null) {
                        appClientLoginHandler.loginClient(encryCredentialInfo, httpServletRequest, httpServletResponse);
                    }

                    //重新请求原始请求
                    String url = httpServletRequest.getRequestURL().toString();
                    if (!StringUtils.isEmpty(url)) {
                        //如果请求中存在EC参数，则去除这个参数，重定位。
                        if (url.contains(WebConstants.SSO_CLIENT_COOKIE_KEY)) {
								//TODO 去除请求URL中的EC参数
							}
                    }


                    //重新定位请求，避免尾部出现长参数。
                    httpServletResponse.sendRedirect(url);
                    return;
                }
            }
            httpServletResponse.sendRedirect(buildRedirectToSSOServer(httpServletRequest));
        } else {
            //若已登录，则接续其他过滤器链
            chain.doFilter(request, response);
        }
    }
```

- 解密用户加密凭证：EncryCredentialManagerImpl.java

```
public EncryCredentialInfo decrypt(EncryCredential encryCredential) throws InvalidEncryededentialException {
        //判空
        if (encryCredential != null && !StringUtils.isEmpty(encryCredential.getCredential())) {
            String credential = encryCredential.getCredential();
            return parseEncryCredential(credential);
        }
        return null;
    }

private EncryCredentialInfo parseEncryCredential(String credential) throws InvalidEncryededentialException {

        EncryCredentialInfo encryCredentialInfo = new EncryCredentialInfo();
        try {
            //先进行URL解码
            credential = URLDecoder.decode(credential, ENCODE);
            //再进行BASE64解码
            credential = new String(Base64Coder.decryptBASE64(credential));

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
                    byte[] data = Base64Coder.decryptBASE64(items[0]);
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
                            encryCredentialInfo.setUser(user == null ? null : JSON.parseObject(user.toString(),User.class));
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
```

####3.3统一注销集成
- 应用服务提供给用户注销会话的接口过滤器(需支持jsonp)：SSOClientLogoutFilter.java

```
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();

        //本地应用未登出，则进行登出处理
        try {
            if (session != null && session.getAttribute(SSOClientFilter.USER_STAT_IN_SESSION_KEY) != null) {

                //清除session
                if (session.getAttribute(SSOClientFilter.USER_STAT_IN_SESSION_KEY) != null) {
                    session.setAttribute(SSOClientFilter.USER_STAT_IN_SESSION_KEY, null);
                }
				//TODO 其他处理

                //设置session过期
                session.setMaxInactiveInterval(0);

            }
        } catch (Exception e) {
            //响应登录结果。
            sendError(httpServletResponse);
        }
        //相应登录结果
        sendResponse(httpServletResponse);
    }


	//执行成功后的返回格式必须是{result:true},以实现前端jsonp统一退出用户已登录的全部应用服务
    private void sendResponse(HttpServletResponse response) {
        response.setContentType("text/javascript;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter outhtml;
        try {
            outhtml = response.getWriter();
            outhtml.print("{result:true}");
            outhtml.close();
        } catch (IOException e) {
            logger.error("send sendResponse error", e);
        }
    }

    private void sendError(HttpServletResponse response) {
        try {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            logger.error("send response error :{}", e);
        }
    }
```

- 应用服务实现统一注销的前端处理

```
//引入SSO提供的统一注销js API 
<script type="application/javascript" src="http://111.9.116.181:20160/sso/api/logoutJs?appId=xxxx&logoutSuccessUrl=xxxx"></script>

//DOM中调用统一注销方法
<a href="javascript:logout()">统一注销</a>

```
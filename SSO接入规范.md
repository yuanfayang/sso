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

功能说明：用于自定义登录视图使用SSO中心服务的单点的登录功能

请求方式：Http POST

请求参数说明：

|参数名称|参数类型|是否必须|描述|
|-----|:----:|:----:|:----|
|appId|String|是|SSO中心服务为应用颁发的应用编号|
|service|String|是|用户原始请求的URL，通常为应用服务中受保护资源的地址|
|username|String|是|用户名|
|password|String|是|密码|

返回说明：

用户在SSO登录成功后，SSO中心服务会颁发SSO中心的授权ticket（Cookie），下发用户访问当前应用服务的加密凭证(SSO_CLIENT_EC)。SSO服务完成用户授权后，会转发用户请求到service指定的资源，并将加密后的用户凭证信息交由应用服务处理，已判断用户的合法性。如下是SSO服务最终生成的请求URL示例：

```
http://111.9.116.181:20161/app1/sso-app/home?SSO_CLIENT_EC=bGI0NlVzU3d1Q2VZSURSVHZKRFkrTXh5R1phYXJtYmpJT05VajA4UU5FQ1NhZmV2UWY3RDZrWVI0%0AVTZmajhDVGlpeFBNRGxhM1MwbApuNlhhMHJaUE0rZ0xqUjhVbHZGYz9hcHBJZD0xMDAxJmtleUlk%0APTI%3D
```

返回参数说：

|参数名称|参数类型|是否必须|描述|
|-----|:----:|:----:|:----|
|SSO_CLIENT_EC|String|是|SSO颁发的用户访问应用服务的加密凭证，应用服务需解密以判断凭证的合法性。|

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
|keyId|int||秘钥编号|
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
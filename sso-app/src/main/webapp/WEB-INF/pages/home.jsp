<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SSO单点登录系统集成示例</title>
</head>
<body>
<p>这是SSO集成单点登录系统的示例一个，演示了如何集成单点登录系统SSO.</p>
<p style="color: red;">
	单点登录成功，当前登录的用户是：${user.userId}.登录的应用ID是：${user.appId}
</p>

<p>
<a href="javascript:logout()">统一注销</a>
</p>

<script type="application/javascript" src="http://127.0.0.1:8080/sso/api/logoutJs?appId=3b0d556d3add420596a9afdd58f1bc10&logoutSuccessUrl=www.baidu.com"></script>
</body>
</html>
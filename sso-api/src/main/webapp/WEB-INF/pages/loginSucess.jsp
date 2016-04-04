<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录成功</title>
</head>
<body>
<h1>恭喜您，登录成功！</h1>

<p>
    认证用户ID: ${authentication.principal.id}
</p>

<p>
    认证时间：${authentication.authenticationDate}
</p>

<p>
    <a href="javascript:logout()">统一注销</a>&nbsp;&nbsp;&nbsp;
</p>
<script type="application/javascript" src="${ctx}/api/logoutJs?appId=3b0d556d3add420596a9afdd58f1bc10&logoutSuccessUrl=http://111.9.116.181:20160/sso/api/login"></script>
</body>
</html>
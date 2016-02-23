<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>默认登录界面</title>
</head>
<body>
<p>这是sso默认的登录界面，请替换为自己的页面 </p>
<p style="color: red;">默认的demo示例是若用户名和密码完全相同则登录成功，比如用户名是test,密码是test则能够登录成功。</p>
<form action="${ctx}/api/login" method="post">
<fieldset>
	<legend>登录</legend>
	<label>用户名：<input type="text" name="username" value="<%=request.getParameter("username")==null?"":request.getParameter("username")%>"/></label><br/>
	<label>密&nbsp;码：<input type="password" name="password"/></label><br/>
	<c:if test="${code!=null}">
	<label style="color: red;">错误信息：${msg}</label><br/>
	</c:if>
	<label><input type="submit" name="submit" value="提交"/></label>
</fieldset>
</form>
</body>
</html>
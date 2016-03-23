<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglib.jsp" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>微服驿站</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/css/typica-login.css">
    <link rel="stylesheet" href="${ctx}/css/jquery.validate.css">
    <style type="text/css">
        .control-group {
            border-bottom: 0px;
        }

        label {
            display: inline-block;
            margin-bottom: 0;
        }
    </style>

    <script type="text/javascript" src="${ctx}/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${ctx}/js/additional-methods.js"></script>
    <script src="${ctx}/js/backstretch.min.js"></script>
    <script type="text/javascript">
        $(document)
                .ready(
                function () {
                    $.backstretch(["${ctx}/images/bg1.jpg",
                        "${ctx}/images/bg2.jpg",
                        "${ctx}/images/bg3.jpg"], {
                        duration: 5000,
                        fade: 2000
                    });
                    $("#loginForm")
                            .validate(
                            {
                                rules: {
                                    validateCode: {
                                        remote: "${ctx}/servlet/validateCodeServlet"
                                    }
                                },
                                messages: {
                                    username: {
                                        required: "请填写用户名."
                                    },
                                    password: {
                                        required: "请填写密码."
                                    },
                                    validateCode: {
                                        remote: "验证码不正确.",
                                        required: "请填写验证码."
                                    }
                                },
                                errorLabelContainer: "#messageBox",
                                errorPlacement: function (error,
                                                          element) {
                                    error.appendTo($("#loginError")
                                            .parent());
                                }
                            });
                    $(".close").on('click', function () {
                        $(".alert-error").hide();
                    })
                });
    </script>
</head>
<body>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="brand" href="${ctx}"><img src="${ctx}/images/logo.png"
                                                alt="Park SSO" style="height: 45px;"></a>
        </div>
    </div>
</div>

<div class="container">
    <div id="messageBox" class="alert alert-error " style="display: none">
        <button id="closeBtn" class="close">×</button>
        <label id="loginError" class="error"></label>
    </div>

    <c:if test="${code!=null}">
        <div id="messageBox" class="alert alert-error ">
            <button id="closeBtn" class="close">×</button>
            <label id="loginError" class="error">错误信息：${msg}</label>
        </div>
    </c:if>
    <div id="login-wraper">
        <form id="loginForm" class="form login-form"
              action="${ctx}/api/login" method="post">
            <legend style="width:100%;text-align:center">
                <span style="color: #08c; font-weight: 400;">系统登陆</span>
            </legend>
            <div class="body">
                <div class="control-group">
                    <div class="controls">
                        <input type="text" id="username" name="username" class="required"
                               value="api-test" placeholder="登录名">
                    </div>
                </div>

                <div class="control-group">
                    <div class="controls">
                        <input type="password" id="password" name="password" value="apiTest"
                               class="required" placeholder="密码"/>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="validateCode">
                            <tags:validateCode name="validateCode"
                                               inputCssStyle="margin-bottom:0;"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="footer">
                <input class="btn btn-primary" type="submit" value="登 录"/>
            </div>
        </form>
    </div>
</div>
<footer class="white navbar-fixed-bottom"> Copyright &copy;
    2016 Powered By 软件与服务中心
</footer>
</body>
</html>
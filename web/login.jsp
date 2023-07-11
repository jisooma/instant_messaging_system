
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="host" value="localhost:8080/wechat"/>
<%--设置主机名--%>
<html>
<head>
    <meta charset="utf-8">
    <title>ours im</title>
   <!-- <link rel="shortcut icon" type=image/x-icon href=https://res.wx.qq.com/a/wx_fed/assets/res/NTI4MWU5.ico>-->
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.4.1.js"></script>
    <!--BEGIN——发送请求脚本-->
    <!--END——发送请求脚本-->
</head>
<body>
<div class="background">
    <%-- 页面头部--%>
    <div class="login-head" style="height: 100px">
        <div class="jumbotron" style="padding-bottom: 20px;padding-top:20px;margin:0px">
            <div class="logo">
                <a href="${pageContext.request.contextPath}/index.jsp"
                   style="color: #999;font-size: 44px;text-decoration: none"><img
                     src="${pageContext.request.contextPath}/static/img/logo.png" alt="logo"
                        style="width: 100px;margin: 10px">OURS WeChat IS READY</h2>
                </a>
            </div>
        </div>
    </div>
    <script>
        <c:if test="${message!=null}">
        alert("系统提示：${message}");
        </c:if>
    </script>
    <div class="input-box">
        <div class="color-input-field">
            <form  action="http://${host}/wechat/user?method=login.do" method="post">
                <input id="index" type="submit" style="display: none">
            <h2 class="input-box-title">邮箱登陆</h2>
            <input type="text" required="required" class="form-control" id="email"
                   value="${param.email}" name="email" placeholder="请输入登陆邮箱" >
            <br/>
            <input id="password" type="password" required="required" class="form-control" name="password"
                   placeholder="请输入密码">
            <div class="remember-me">
                <input id="option" name="auto_login" type="checkbox" value="true">记住登陆
            </div>
            <input type="submit" class="submit-button" value="登陆">
            <br>
            <div class="switch-button">
                <a href="${pageContext.request.contextPath}/register.jsp">立即注册</a>
            </div>
            </form>
        </div>
    </div>
</div>

</body>
<style type="text/css">
    .background {
        /*height: /*webkit-fill-available;*/
        min-height: 750px;
        text-align: center;
        font-size: 14px;
        background-color: #f1f1f1;
        z-index: -1;
    }

    .logo {
        position: absolute;
        top: 56px;
        margin-left: 50px;
    }

    .form-control {
        padding: 10px;
        min-height: 55px;
        max-height: 70px;
        font-size: 22px;
    }

    .input-box-title {
        text-align: center;
        margin: 0 auto 50px;
        padding: 10px;
        font-weight: 400;
        color: #969696
    }

    .color-input-field {
        padding: 50px;
        font-size: 22px;
        height: 625px;
        width: 500px
    }

    .input-box {
        width: fit-content;
        margin: 104px auto;
        background-color: #fff;
        border-radius: 4px;
        box-shadow: 0 0 8px rgba(0, 0, 0, .1);
        vertical-align: middle;
    }

    .submit-button {
        margin-top: 20px;
        background-color: #1AAD19;
        color: #FFFFFF;
        padding: 9px 18px;
        border-radius: 5px;
        outline: none;
        border: none;
        width: 100%;
    }

    .remember-me {
        float: left;
        font-weight: 400;
        color: #969696;
        margin-top: 20px;
    }

    .switch-button {
        text-align: left;
    }

</style>
</html>

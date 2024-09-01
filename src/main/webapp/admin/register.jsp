<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/JavaScript;charset=utf-8"/>
    <title>云借阅-图书管理系统</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/webbase.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages-login-manage.css"/>
    <script charset="UTF-8" type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <link rel="icon" type="image/x-ico" href="${pageContext.request.contextPath}/img/logo.jpg"/>
</head>
<body>
<script type="text/javascript">
    $(document).ready(function () {
        $("#registerButton").click(function (event) {
            var name = $("input[name='name']").val().trim();
            var password = $("input[name='password']").val().trim();
            var email = $("input[name='email']").val().trim();
            var nameRegex = /^[a-zA-Z0-9_\u4e00-\u9fa5\uF900-\uFA2D]{2,15}$/; // 大小写字母加数字2-15位包括中文
            var emailRegex = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
            var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;// 大小写字母加数字至少8位
            if (name.length === 0 || password.length === 0 || email.length === 0) {
                alert("请完善信息");
                return false;
            }
            if (!nameRegex.test(name)) {
                alert("用户名格式不正确，请输入2-15位的字母、数字、下划线或者中文组合。");
                return false;
            }

            // 这里可以添加更多密码强度的验证规则
            if (!passwordRegex.test(password)) {
                alert("密码长度要为8位，至少包含大小写字母数字的组合");
                return false;
            }
            if (!emailRegex.test(email)) {
                alert("邮箱格式不正确，请检查您的输入。");
                return false;
            }
            // 如果所有验证通过，则提交表单
            $("#registerform").submit();
        });
    });
</script>
<div class="loginmanage">
    <div class="py-container">
        <h4 class="manage-title">云借阅-图书管理系统</h4>
        <div class="loginform">
            <ul class="sui-nav nav-tabs tab-wraped">
                <li class="active">
                    <h3>用户注册</h3>
                </li>
            </ul>
            <div class="tab-content tab-wraped">
                <%--注册提示信息--%>
                <span style="color: red">${msg}</span>
                <div class="tab-pane active">
                    <form id="registerform" class="sui-form" action="${pageContext.request.contextPath}/user/register"
                          method="post">
                        <div class="input-prepend">
                            <span class="add-on loginname">用户名</span>
                            <input type="text" placeholder="请输入用户名" class="span2 input-xfat" name="name">
                        </div>
                        <div class="input-prepend">
                            <span class="add-on loginpwd">密码</span>
                            <input type="password" placeholder="请输入密码" class="span2 input-xfat" name="password">
                        </div>
                        <div class="input-prepend">
                            <span class="add-on logineml">邮箱</span>
                            <input type="text" placeholder="请输入邮箱" class="span2 input-xfat" name="email">
                        </div>
                        <div class="logined">
                            <%--                            <input type="submit" value="注册">--%>
                            <%--                            <a class="sui-btn btn-block btn-xlarge btn-danger"--%>
                            <%--                               href="${pageContext.request.contextPath}/#">--%>
                            <%--                                <span class="hidden-xs">登录</span>--%>
                            <%--                            </a>--%>
                            <a class="sui-btn btn-block btn-xlarge btn-danger"
                               id="registerButton" target="_self">注&nbsp;&nbsp;册</a>
                            <br/>
                            已有账户？点击跳转&nbsp;
                            <a href="${pageContext.request.contextPath}/#">登录</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

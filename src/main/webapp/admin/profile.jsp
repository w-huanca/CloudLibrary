<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/JavaScript;charset=utf-8"/>
    <script  charset="UTF-8"  type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script  charset="UTF-8"  type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script  charset="UTF-8"  type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script  charset="UTF-8"  type="text/javascript" src="${pageContext.request.contextPath}/js/my.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/eprofile.css">
    <title>个人信息</title>
</head>
<body>
<script>
    $(document).ready(function () {
        $(".btn").click(function (event) {
            var name = $("input[name='name']").val().trim();
            var email = $("input[name='email']").val().trim();
            var nameRegex = /^[a-zA-Z0-9_\u4e00-\u9fa5\uF900-\uFA2D]{2,15}$/; // 大小写字母加数字2-15位包括中文
            var emailRegex = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
            if (name.length === 0 || email.length === 0) {
                alert("请完善信息");
                return false;
            }
            if (!nameRegex.test(name)) {
                alert("用户名格式不正确，请输入2-15位的字母、数字、下划线或者中文组合");
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
<div class="box-header with-border">
    <h3 class="box-title">个人信息</h3>
</div>
<span style="color: red" id="message">${result.message}</span>
<div class="box-body">
    <form action="${pageContext.request.contextPath}/user/editProfile" method="post">
        <div class="form-group">
            <label for="name" class="label">用户名称：</label>
            <input type="text" id="name" name="name" class="input-text" value="${UserInformation.name}" required>
        </div>
        <div class="form-group">
            <label for="email" class="label">用户邮箱：</label>
            <input type="email" id="email" name="email" class="input-text" value="${UserInformation.email}" required>
        </div>
        <div class="form-group">
            <label for="role" class="label">用户角色：</label>
            <input type="text" id="role" name="role" class="input-text" value="${UserInformation.role}" readonly>
        </div>
        <div class="button-container">
            <button type="submit" class="btn">保存修改</button>
        </div>
    </form>
</div>
</body>
</html>
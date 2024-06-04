<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script charset="UTF-8" type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script charset="UTF-8" type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script charset="UTF-8" type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script charset="UTF-8" type="text/javascript" src="${pageContext.request.contextPath}/js/my.js"></script>
    <title>修改密码</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
        }

        form {
            margin: 50px auto;
            padding: 20px;
            width: 300px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 10px;
        }

        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 3px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #00a65a;
            color: #fff;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
    </style>
    <script type="text/javascript">
        function validatePassword() {
            var oldPassword = document.getElementById("old_pwd").value;
            var newPassword = document.getElementById("new_pwd").value;
            var confirmNewPassword = document.getElementById("re_pwd").value;
            var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
            if (!newPassword || !confirmNewPassword || !oldPassword) {
                alert("所有密码字段都必须填写！");
                return false;
            }

            if (newPassword !== confirmNewPassword) {
                alert("两次输入的新密码不一致！");
                return false;
            }
            // 这里可以添加更多密码强度的验证规则
            if (!passwordRegex.test(newPassword)) {
                alert("密码长度要为8位,至少包含大小写字母数字的组合");
                return false;
            }
            // 更多的密码强度验证可以在这里实现...
            return true;
        }
    </script>
</head>
<body>
<span style="color: red">${result.message}</span>
<form onsubmit="return validatePassword()" action="${pageContext.request.contextPath}/user/cpwd" method="post">
    <label for="old_pwd">旧密码:</label>
    <input type="password" id="old_pwd" name="old_pwd"><br>
    <label for="new_pwd">新密码:</label>
    <input type="password" id="new_pwd" name="new_pwd"><br>
    <label for="re_pwd">确认密码:</label>
    <input type="password" id="re_pwd" name="re_pwd"><br>
    <input type="submit" value="提交">
</form>
</body>
</html>
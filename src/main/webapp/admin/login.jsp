<%@ page contentType="text/html;charset=UTF-8"  language="java" isELIgnored="false" %>

<html>
<head>
    <meta http-equiv="content-type" content="text/JavaScript;charset=utf-8" />
    <title>云借阅-图书管理系统</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/webbase.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages-login-manage.css"/>
    <script  charset="UTF-8"  type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <link rel="icon" type="image/x-ico" href="${pageContext.request.contextPath}/img/logo.jpg"/>
</head>
<body>
    <div class="loginmanage">
        <div class="py-container">
            <h4 class="manage-title">云借阅-图书管理系统</h4>
            <div class="loginform">
                <ul class="sui-nav nav-tabs tab-wraped">
                    <li class="active">
                        <h3>账户登录</h3>
                    </li>
                </ul>
                <div class="tab-content tab-wraped">
                    <%--登录提示信息--%>
                    <span style="color: red">${msg}</span>
                    <div id="profile" class="tab-pane active">
                        <form id="loginform" class="sui-form" action="${pageContext.request.contextPath}/user/login"
                              method="post">
                            <div class="input-prepend"><span class="add-on loginname">用户名</span>
                                <input type="text" placeholder="企业邮箱" class="span2 input-xfat" name="email">
                            </div>
                            <div class="input-prepend"><span class="add-on loginpwd">密码</span>
                                <input type="password" placeholder="请输入密码" class="span2 input-xfat" name="password">
                            </div>
                            <div class="logined">
                                <a class="sui-btn btn-block btn-xlarge btn-danger"
                                   href='javascript:document:loginform.submit();' target="_self">登&nbsp;&nbsp;录</a>
                                <br />
                                还没有账户？请点击跳转&nbsp;
                                <a href="${pageContext.request.contextPath}/user/toRegister">注册</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
    /**
     * 登录超时 展示区跳出iframe
     */
    var _topWin = window;
    while (_topWin != _topWin.parent.window) {
        _topWin = _topWin.parent.window;
    }
    if (window != _topWin)
        _topWin.document.location.href = 'login.jsp';

    // 获取 URL 中的参数值
    function getUrlParameter(name) {
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(window.location.href);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    // 显示消息
    window.onload = function() {
        var msg = getUrlParameter('msg');
        if (msg) {
            alert(msg);
        }
    };
</script>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <meta http-equiv="content-type" content="text/JavaScript;charset=utf-8"/>
    <title>云借阅-图书管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/_all-skins.min.css">
    <script charset="utf-8" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script charset="UTF-8" type="text/javascript" src="${pageContext.request.contextPath}/js/my.js"></script>
    <link rel="icon" type="image/x-ico" href="${pageContext.request.contextPath}/img/logo.jpg"/>
    <script type="text/javascript">
        function init() {//先隐藏二级菜单
            document.querySelector('.submenu1').style.display = 'none'
        }

        // 点击其他地方隐藏子菜单
        document.addEventListener('click', function (event) {
            var submenu = document.querySelector('.submenu1');
            if (submenu.style.display === 'block' &&
                !event.target.closest('.userinfomation') &&
                !event.target.closest('.submenu1')) {
                submenu.style.display = 'none';
            }
        });

        // 使用JavaScript
        function f(str) {
            var submenu = document.querySelector(str); // 获取子菜单元素
            if (submenu.style.display === 'none') {
                submenu.style.display = 'block'; // 显示子菜单
            } else {
                submenu.style.display = 'none'; // 隐藏子菜单
            }
        }

        function SetIFrameHeight() {
            var iframeid = document.getElementById("iframe");
            if (document.getElementById) {
                /*设置 内容展示区的高度等于页面可视区的高度*/
                iframeid.height = document.documentElement.clientHeight;
            }
        }
    </script>
</head>

<body class="hold-transition skin-green sidebar-mini" onload="init()">
<div class="wrapper">
    <!-- 页面头部 -->
    <header class="main-header">
        <!-- Logo -->
        <a href="${pageContext.request.contextPath}/toMainPage" class="logo">
            <span class="logo-lg"><b>云借阅-图书管理系统</b></span>
        </a>
        <!-- 头部导航 -->
        <nav class="navbar navbar-static-top">
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <li class="dropdown user user-menu">
                        <a>
                            <c:if test="${USER_SESSION.role=='ADMIN'}">
                                <img src="${pageContext.request.contextPath}/img/admin.jpg" class="user-image"
                                     alt="User Image">
                            </c:if>
                            <c:if test="${USER_SESSION.role=='USER'}">
                                <img src="${pageContext.request.contextPath}/img/user.png" class="user-image"
                                     alt="User Image">
                            </c:if>
                            <c:if test="${USER_SESSION.role==''}">
                                <img src="${pageContext.request.contextPath}/img/default.jpg" class="user-image"
                                     alt="User Image">
                            </c:if>
                            <span class="hidden-xs">${USER_SESSION.name}</span>
                        </a>
                    </li>
                    <li class="dropdown user user-menu">
                        <a href="${pageContext.request.contextPath}/user/logOut">
                            <span class="hidden-xs">注销</span>
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <!-- 页面头部 /-->

    <!-- 导航侧栏 -->
    <aside class="main-sidebar">
        <section class="sidebar">
            <ul class="sidebar-menu">
                <c:if test="${USER_SESSION.role == 'ADMIN'}">
                    <li>
                        <a href="${pageContext.request.contextPath}/report/toRecordStatistics" target="iframe">
                            <i class="fa fa-circle-o"></i>展示板/仪表盘
                        </a>
                    </li>
                </c:if>
                <li class="userinfomation" onclick="f('.submenu1')">
                    <a href="#">
                        <i class="fa fa-dashboard"></i> <span>个人信息</span>
                    </a>
                </li>
                <ul class="submenu1">
                    <li><a href="${pageContext.request.contextPath}/user/tochangePassword" target="iframe">修改密码</a></li>
                    <li><a href="${pageContext.request.contextPath}/user/userInfo" target="iframe">修改个人信息</a></li>
                </ul>
                <c:if test="${USER_SESSION.role == 'ADMIN'}">
                    <li>
                        <a href="${pageContext.request.contextPath}/user/userList" target="iframe">
                            <i class="fa fa-circle-o"></i>读者管理
                        </a>
                    </li>
                </c:if>
                <li>
                    <a href="${pageContext.request.contextPath}/user/toMainPage">
                        <i class="fa fa-dashboard"></i> <span>首页</span>
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/book/search" target="iframe">
                        <c:choose>
                            <c:when test="${USER_SESSION.role == 'ADMIN'}">
                                <i class="fa fa-circle-o"></i>图书管理
                            </c:when>
                            <c:when test="${USER_SESSION.role == 'USER'}">
                                <i class="fa fa-circle-o"></i>图书借阅
                            </c:when>
                        </c:choose>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/book/searchBorrowed" target="iframe">
                        <i class="fa fa-circle-o"></i>当前借阅
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/record/searchRecords" target="iframe">
                        <i class="fa fa-circle-o"></i>借阅记录
                    </a>
                </li>

            </ul>
        </section>
        <!-- /.sidebar -->
    </aside>
    <!-- 导航侧栏 /-->
    <!-- 内容展示区域 -->
    <div class="content-wrapper">
        <iframe width="100%" id="iframe" name="iframe" onload="SetIFrameHeight()"
                frameborder="0" src="${pageContext.request.contextPath}/book/selectNewBooks"></iframe>
    </div>
</div>
</body>
</html>
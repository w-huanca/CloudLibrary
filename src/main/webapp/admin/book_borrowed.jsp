<%@ page contentType="text/html;charset=UTF-8"  language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.time.LocalTime" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/JavaScript;charset=utf-8" />
    <title>云借阅-图书管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <script  charset="UTF-8"  type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script  charset="UTF-8"  type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script  charset="UTF-8"  type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script  charset="UTF-8"  type="text/javascript" src="${pageContext.request.contextPath}/js/my.js"></script>
    <link rel="icon" type="image/x-ico" href="${pageContext.request.contextPath}/img/logo.jpg"/>

</head>

<body class="hold-transition skin-red sidebar-mini">
<!-- .box-body -->
<div class="box-header with-border">
    <h3 class="box-title">当前借阅</h3>
</div>
<div class="box-body">
    <!--工具栏 数据搜索 -->
    <div class="box-tools pull-right">
        <div class="has-feedback">
            <form action="${pageContext.request.contextPath}/book/searchBorrowed" method="post">
                <c:choose>
                    <c:when test="${USER_SESSION.role == 'ADMIN'}">
                        借阅人：<input name="borrower" value="${search.borrower}">&nbsp&nbsp&nbsp&nbsp
                        图书名称：<input name="name" value="${search.bookname}">&nbsp&nbsp&nbsp&nbsp
                    </c:when>
                    <c:when test="${USER_SESSION.role == 'USER'}">
                        图书名称：<input name="name" value="${search.bookname}">&nbsp&nbsp&nbsp&nbsp
                    </c:when>
                </c:choose>
<%--                图书作者：<input name="author" value="${search.author}">&nbsp&nbsp&nbsp&nbsp--%>
<%--                出版社：<input name="press" value="${search.press}">&nbsp&nbsp&nbsp&nbsp--%>
                <input class="btn btn-default" type="submit" value="查询">
            </form>
        </div>
    </div>
    <!--工具栏 数据搜索 /-->
    <!--数据列表-->
    <div class="table-box">
        <!-- 数据表格 -->
        <table id="dataList" class="table table-bordered table-striped table-hover dataTable text-center">
            <thead>
            <tr>
                <th class="sorting_asc">图书名称</th>
<%--                <th class="sorting">图书作者</th>--%>
<%--                <th class="sorting">出版社</th>--%>
                <th class="sorting">标准ISBN</th>
<%--                <th class="sorting">书籍状态</th>--%>
                <th class="sorting">借阅人</th>
                <th class="sorting">借阅时间</th>
                <th class="sorting">应归还时间</th>
                <th class="sorting">借阅状态</th>
                <th class="text-center">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageResult.rows}" var="record">
                <tr>
                    <td>${record.bookname}</td>
                    <td>${record.bookisbn}</td>
                    <td>${record.borrower}</td>
                    <td>${record.borrowTime}</td>
                    <td>${record.remandTime}</td>
                    <td>
                        <c:if test="${record.status ==0}">借阅中</c:if>
                        <c:if test="${record.status ==1}">归还中</c:if>
                    </td>
<%--                    <td>${book.borrower}</td>--%>
<%--                    <td>${book.borrowTime}</td>--%>
<%--                    <td>${book.returnTime}</td>--%>
                    <td class="text-center">
<%--                        <c:if test="${record.status ==0}">--%>
<%--                            <fmt:parseDate value="${record.returnTime}" pattern="yyyy-MM-dd" var="returnTime" />--%>
<%--                            <fmt:parseDate value="<%=LocalDate.now().toString()%>" pattern="yyyy-MM-dd" var="now" />--%>
<%--                            <c:if test="${not empty returnTime and not empty now and now.after(returnTime)}">--%>
<%--                                <button type="button" class="btn btn-xs" style="background-color: red;color: white" disabled="true">已超时</button>--%>
<%--                            </c:if>--%>
<%--                            <button type="button" class="btn bg-olive btn-xs" onclick="returnBook(${record.id})">归还--%>
<%--                            </button>--%>
<%--                        </c:if>--%>
                        <c:if test="${record.status ==0}">
                            <fmt:parseDate value="${record.remandTime}" pattern="yyyy-MM-dd" var="remandTime" />
                            <fmt:parseDate value="<%=LocalDate.now().toString()%>" pattern="yyyy-MM-dd" var="now" />
                            <c:if test="${not empty remandTime and not empty now and now.after(remandTime)}">
                                <button type="button" class="btn btn-xs" style="background-color: red;color: white" disabled="true">已超时</button>
                            </c:if>
                            <button type="button" class="btn bg-olive btn-xs" onclick="returnBook(${record.id})">归还
                            </button>
                        </c:if>
                        <c:if test="${record.status ==1}">
                            <button type="button" class="btn bg-olive btn-xs" disabled="true">归还中</button>
                            <c:if test="${USER_SESSION.role =='ADMIN'}">
                                <button type="button" class="btn bg-olive btn-xs" onclick="returnConfirm(${record.id})">
                                    归还确认
                                </button>
                            </c:if>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!-- 数据表格 /-->
        <%--分页插件--%>
        <div id="pagination" class="pagination"></div>
    </div>
    <!-- 数据表格 /-->
</div>
<!-- /.box-body -->
</body>
<script>
	/*分页插件展示的总页数*/
    pageargs.total = Math.ceil(${pageResult.total}/pageargs.pagesize);
	/*分页插件当前的页码*/
    pageargs.cur = ${pageNum}
	/*分页插件页码变化时将跳转到的服务器端的路径*/
	pageargs.gourl = "${gourl}"
    recordVO.bookname = "${search.bookname}"
	/*分页效果*/
    pagination(pageargs);
</script>
</html>
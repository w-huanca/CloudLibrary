<%@ page contentType="text/html;charset=UTF-8"  language="java" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/JavaScript;charset=utf-8" />
    <title>云借阅-图书管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script  charset="UTF-8"  type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script  charset="UTF-8" type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script  charset="UTF-8" type="text/javascript" src="${pageContext.request.contextPath}/js/my.js"></script>
    <link rel="icon" type="image/x-ico" href="${pageContext.request.contextPath}/img/logo.jpg"/>

</head>
<body class="hold-transition skin-red sidebar-mini">
<!--数据展示头部-->
<div class="box-header with-border">
    <h3 class="box-title">新书推荐</h3>
</div>
<!--数据展示头部-->
<!--数据展示内容区-->
<div class="box-body">
    <!-- 数据表格 -->
    <table id="dataList" class="table table-bordered table-striped table-hover dataTable text-center">
        <thead>
        <tr>
            <th class="sorting_asc">图书名称</th>
            <th class="sorting">图书作者</th>
            <th class="sorting">出版社</th>
            <th class="sorting">标准ISBN</th>
            <th class="sorting">上架时间</th>
            <th class="sorting">库存量</th>
            <th class="sorting">书籍状态</th>
            <th class="text-center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageResult.rows}" var="book">

            <tr>
                <td>${book.name}</td>
                <td>${book.author}</td>
                <td>${book.press}</td>
                <td>${book.isbn}</td>
                <td>${book.uploadTime}</td>
                <td>${book.stockpile}</td>
                <td>
                    <c:if test="${book.status ==0}">可借阅</c:if>
                    <c:if test="${book.status ==1}">无库存</c:if>
                    <c:if test="${book.status ==2}">已下架</c:if>
                </td>
                <td class="text-center">
                    <c:if test="${book.status ==0}">
                        <c:choose>
                            <c:when test="${USER_SESSION.role == 'ADMIN'}">
                                <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                        data-target="#addOrEditModal" onclick="findBookById(${book.id},'edit')"> 编辑
                                </button>
                            </c:when>
                            <c:when test="${USER_SESSION.role == 'USER'}">
                                <button type="button" class="btn bg-olive btn-xs" data-toggle="modal" data-target="#borrowModal"
                                        onclick="findBookById(${book.id},'borrow')"> 借阅
                                </button>
                            </c:when>
                        </c:choose>

                    </c:if>
                    <c:if test="${book.status ==1 ||book.status ==2}">
                        <button type="button" class="btn bg-olive btn-xs" disabled="true">借阅</button>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <!-- 数据表格 /-->
</div>
<!-- 添加和编辑图书的模态窗口 -->
<div class="modal fade" id="addOrEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="myModalLabel">图书信息</h3>
            </div>
            <div class="modal-body">
                <form id="addOrEditBook">
                    <span><input type="hidden" id="ebid" name="id"></span>
                    <table id="addOrEditTab" class="table table-bordered table-striped" width="800px">
                        <%--图书的id,不展示在页面--%>
                        <tr>
                            <td>图书名称</td>
                            <td><input class="form-control" placeholder="图书名称" name="name" id="ebname"></td>
                            <td>标准ISBN</td>
                            <td><input class="form-control" placeholder="标准ISBN" name="isbn" id="ebisbn"></td>
                        </tr>
                        <tr>
                            <td>出版社</td>
                            <td><input class="form-control" placeholder="出版社" name="press" id="ebpress"></td>
                            <td>作者</td>
                            <td><input class="form-control" placeholder="作者" name="author" id="ebauthor"></td>
                        </tr>
                        <tr>
                            <td>书籍页数</td>
                            <td><input class="form-control" placeholder="书籍页数" name="pagination" id="ebpagination"></td>
                            <td>书籍价格<br/></td>
                            <td><input class="form-control" placeholder="书籍价格" name="price" id="ebprice"></td>
                        </tr>
                        <tr>
                            <td>库存量</td>
                            <td><input class="form-control" placeholder="书籍库存量" name="stockpile" id="estockpile"></td>
                            <td>上架状态</td>
                            <td>
                                <select class="form-control" id="ebstatus" name="status" >
                                    <option value="0">上架</option>
                                    <option value="3">下架</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" id="aoe" disabled onclick="addOrEdit()">保存
                </button>
                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
    </div>
</div>.
<!-- 数据展示内容区/ -->
<%--引入存放模态窗口的页面--%>
<jsp:include page="/admin/book_modal.jsp"/>
</body>
</html>

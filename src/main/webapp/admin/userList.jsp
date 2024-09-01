<%@ page contentType="text/html;charset=UTF-8"  language="java" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/JavaScript;charset=utf-8" />
    <title>云借阅-图书管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <script  charset="UTF-8"  type="text/javascript"  src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script   charset="UTF-8"  type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script   charset="UTF-8"  type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script   charset="UTF-8"  type="text/javascript" src="${pageContext.request.contextPath}/js/my.js"></script>
    <link rel="icon" type="image/x-ico" href="${pageContext.request.contextPath}/img/logo.jpg"/>
</head>

<body class="hold-transition skin-red sidebar-mini">
<!-- .box-body -->
<div class="box-header with-border">
    <h3 class="box-title">读者管理</h3>
</div>
<div class="box-body">
    <%--新增按钮：如果当前登录用户是管理员，页面展示新增按钮--%>
    <c:if test="${USER_SESSION.role =='ADMIN'}">
        <div class="pull-left">
            <div class="form-group form-inline">
                <div class="btn-group">
                    <button type="button" class="btn btn-default" title="新建" data-toggle="modal"
                            data-target="#addOrEditModal" onclick="resetFrom1()"> 新增
                    </button>
                </div>
            </div>
        </div>
    </c:if>
    <%--新增按钮 /--%>
    <!--工具栏 数据搜索 -->
    <div class="box-tools pull-right">
        <div class="has-feedback">
            <form action="${pageContext.request.contextPath}/user/search" method="post">
                用户名称：<input name="name" value="${search.name}">&nbsp&nbsp&nbsp&nbsp
                用户邮箱：<input name="email" value="${search.email}">&nbsp&nbsp&nbsp&nbsp
                用户角色：<input name="role" value="${search.role}">&nbsp&nbsp&nbsp&nbsp
                <input class="btn btn-default" type="submit" value="查询">
            </form>
        </div>
    </div>
    <!--工具栏 数据搜索 /-->
    <!-- 数据列表 -->
    <div class="table-box">
        <!-- 数据表格 -->
        <table id="dataList" class="table table-bordered table-striped table-hover dataTable text-center">
            <thead>
            <tr>
                <th class="sorting_asc">用户名称</th>
                <th class="sorting">用户邮箱</th>
                <th class="sorting">用户角色</th>
                <th class="sorting">用户状态</th>
                <th class="text-center">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageResult.rows}" var="user">
                <tr>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>${user.role}</td>
                    <td>
                        <c:if test="${user.status ==0}">正常</c:if>
                        <c:if test="${user.status ==1}">禁用</c:if>
                    </td>
                    <td class="text-center">
                        <c:if test="${USER_SESSION.role =='ADMIN'}">
                            <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                    data-target="#addOrEditModal" onclick="findUserById(${user.id},'edit')"> 编辑
                            </button>
                            <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                    onclick="findUserById(${user.id},'delete')"> 删除
                            </button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!--数据表格/-->
        <%--分页插件--%>
        <div id="pagination" class="pagination"></div>
    </div>
    <!--数据列表/-->
</div>
<!-- /.box-body -->


<!-- 添加和编辑读者的模态窗口 -->
<div class="modal fade" id="addOrEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="myModalLabel">用户信息</h3>
            </div>
            <div class="modal-body">
                <form id="addOrEditUser">
                    <span><input type="hidden" id="euid" name="id" value="${user.id}"></span>
                    <table id="addOrEditTab" class="table table-bordered table-striped" width="800px">
                        <%--用户的id,不展示在页面--%>
                        <tr>
                            <td>用户名称</td>
                            <td><input class="form-control" placeholder="用户名称" name="name" id="euname"></td>
                            <td>用户邮箱</td>
                            <td><input class="form-control" placeholder="用户邮箱" name="email" id="euemail"></td>
                        </tr>
                        <tr>
                            <td>用户密码</td>
                            <td><input class="form-control" placeholder="用户密码" type="password" name="password" id="eupassword"></td>
                            <td>用户角色</td>
                            <td>
                                <select class="form-control" id="eurole" name="role" >
                                    <option value="USER">USER</option>
                                    <option value="ADMIN">ADMIN</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>用户状态</td>
                            <td>
                                <select class="form-control" id="eustatus" name="status" >
                                    <option value="0">正常</option>
                                    <option value="1">禁用</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" id="aoe" disabled onclick="addOrEdit1()">保存
                </button>
                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
    </div>
</div>

</body>
<script>
    /*分页插件展示的总页数*/
    pageargs.total = Math.ceil(${pageResult.total}/pageargs.pagesize);
    /*分页插件当前的页码*/
    pageargs.cur = ${pageNum}
        /*分页插件页码变化时将跳转到的服务器端的路径*/
        pageargs.gourl = "${gourl}"
    /*保存搜索框中的搜索条件，页码变化时携带之前的搜索条件*/
    userVO.name = "${search.name}"
    userVO.email = "${search.email}"
    userVO.role = "${search.role}"
    /*分页效果*/
    pagination(pageargs);
</script>
</html>
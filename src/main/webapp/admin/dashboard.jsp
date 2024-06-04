<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <script charset="UTF-8" type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script charset="UTF-8" type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.js"></script>
    <script charset="UTF-8" type="text/javascript" src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script charset="UTF-8" type="text/javascript" src="${pageContext.request.contextPath}/js/my.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <style>
        body {
            background-color: #f8f9fa;
        }

        .card {
            margin: 20px;
        }

        .chart {
            height: 400px;
        }

        .activity-list {
            max-height: 400px;
            overflow-y: auto;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="text-center my-4">展示板/仪表盘</h1>
    <div class="row">
        <div class="col-md-3">
            <div class="card text-white bg-primary">
                <div class="card-body">
                    <h5 class="card-title">总图书数量</h5>
                    <p class="card-text" id="totalBooks"></p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-white bg-success">
                <div class="card-body">
                    <h5 class="card-title">总读者数量</h5>
                    <p class="card-text" id="totalReaders"></p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-white bg-warning">
                <div class="card-body">
                    <h5 class="card-title">当前借阅数量</h5>
                    <p class="card-text" id="currentBorrowedBooks"></p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-white bg-danger">
                <div class="card-body">
                    <h5 class="card-title">历史借阅数量</h5>
                    <p class="card-text" id="totalBorrowedBooks"></p>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <!-- 借阅量和月份选择器 -->
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <div class="form-group">
                        <h5><label for="monthPicker">选择月份:</label></h5>
                        <input type="month" id="monthPicker" class="form-control"/>
                    </div>
                    <h5 class="card-title">借阅量</h5>
                    <div id="dailyBorrowChart" class="chart"></div>
                </div>
            </div>
        </div>
        <!-- 最受欢迎的图书 -->
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">最受欢迎的图书</h5>
                    <div id="popularBooks" class="chart"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">最新动态</h5>
                    <ul id="activityList" class="list-group activity-list"></ul>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>

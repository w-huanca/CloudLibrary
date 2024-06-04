//借阅窗口中时间标签的内容改变时执行
function cg() {
    $("#savemsg").attr("disabled", false);
    var rt = $("#time").val().split("-");
    var ny = new Date().getFullYear();
    var nm = new Date().getMonth() + 1;
    var nd = new Date().getDate();
    if (rt[0] < ny) {
        alert("借阅图书的日期不能早于今天")
        $("#savemsg").attr("disabled", true);
    } else if (rt[0] == ny) {
        if (rt[1] < nm) {
            alert("借阅图书的日期不能早于今天")
            $("#savemsg").attr("disabled", true);
        } else if (rt[1] == nm) {
            if (rt[2] < nd) {
                alert("借阅图书的日期不能早于今天")
                $("#savemsg").attr("disabled", true);
            } else {
                $("#savemsg").attr("disabled", false);
            }
        }
    }
}

//点击借阅图书时执行
function borrow() {
    var url = getProjectPath() + "/book/borrowBook";
    $.post(url, $("#borrowBook").serialize(), function (response) {
        alert(response.message);
        if (response.success == true) {
            window.location.href = getProjectPath() + "/book/search";
        }
    })
}

//重置添加和编辑窗口中输入框的内容
function resetFrom() {
    $("#aoe").attr("disabled", true)
    var $vals = $("#addOrEditBook input");
    $vals.each(function () {
        $(this).attr("style", "").val("")
    });
}

//重置添加和编辑窗口中输入框的样式
function resetStyle() {
    $("#aoe").attr("disabled", false)
    var $vals = $("#addOrEditBook input");
    $vals.each(function () {
        $(this).attr("style", "")
    });
}

//重置添加和编辑窗口中输入框的内容
function resetFrom1() {
    $("#aoe").attr("disabled", true)
    var $vals = $("#addOrEditUser input");
    $vals.each(function () {
        $(this).attr("style", "").val("")
    });
}

//查询id对应的图书信息，并将图书信息回显到编辑或借阅的窗口中
function findBookById(id, doname) {
    resetStyle()
    var url = getProjectPath() + "/book/findById?id=" + id;
    console.log(url)
    $.get(url, function (response) {
        //如果是编辑图书，将获取的图书信息回显到编辑的窗口中
        if (doname == 'edit') {
            $("#ebid").val(response.data.id);
            $("#ebname").val(response.data.name);
            $("#ebisbn").val(response.data.isbn);
            $("#ebpress").val(response.data.press);
            $("#ebauthor").val(response.data.author);
            $("#ebpagination").val(response.data.pagination);
            $("#ebprice").val(response.data.price);
            $("#estockpile").val(response.data.stockpile);
            $("#ebstatus").val(response.data.status);
        }
        //如果是借阅图书，将获取的图书信息回显到借阅的窗口中
        if (doname == 'borrow') {
            $("#savemsg").attr("disabled", true)
            $("#time").val("");
            $("#bid").val(response.data.id);
            $("#bname").val(response.data.name);
            $("#bisbn").val(response.data.isbn);
            $("#bpress").val(response.data.press);
            $("#bauthor").val(response.data.author);
            $("#bpagination").val(response.data.pagination);
        }
    })
}

function findUserById(id, doname) {
    var url = getProjectPath() + "/user/findByUserId?id=" + id;
    console.log(url)
    $.get(url, function (response) {
        //如果是编辑用户，将获取的用户信息回显到编辑的窗口中
        if (doname == 'edit') {
            $("#euid").val(response.data.id);
            $("#euname").val(response.data.name);
            $("#eupassword").val(response.data.password);
            $("#euemail").val(response.data.email);
            $("#eurole").val(response.data.role);
            $("#eustatus").val(response.data.status);
        }
        //如果是删除读者，执行删除操作
        if (doname == 'delete') {
            var r = confirm("确定删除读者吗?");
            if (r) {
                var url = getProjectPath() + "/user/deleteUserById?id=" + id;
                $.get(url, function (response) {
                    alert(response.message)
                    //还书成功时，刷新当前借阅的列表数据
                    if (response.success == true) {
                        window.location.href = getProjectPath() + "/user/search";
                    }
                })
            }
        }
    })
}

//点击添加或编辑的窗口的确定按钮时，提交图书信息
function addOrEdit() {
    //获取表单中图书id的内容
    var ebid = $("#ebid").val();
    //如果表单中有图书id的内容，说明本次为编辑操作
    if (ebid > 0) {
        var url = getProjectPath() + "/book/editBook";
        $.post(url, $("#addOrEditBook").serialize(), function (response) {
            alert(response.message)
            if (response.success == true) {
                window.location.href = getProjectPath() + "/book/search";
            }
        })
    }
    //如果表单中没有图书id，说明本次为添加操作
    else {
        var url = getProjectPath() + "/book/addBook";
        $.post(url, $("#addOrEditBook").serialize(), function (response) {
            alert(response.message)
            if (response.success == true) {
                window.location.href = getProjectPath() + "/book/search";
            }
        })
    }
}

function DeleteBookById(id) {
    var url = getProjectPath() + "/book/deleteBook?id=" + id;
    var r = confirm("确定删除图书?");
    if (r) {
        $.get(url, function (response) {
            alert(response.message);
            if (response.success == true) {
                window.location.href = getProjectPath() + "/book/search";
            }
        })
    }

}

//点击添加或编辑的窗口的确定按钮时，提交用户信息
function addOrEdit1() {
    //获取表单中用户id的内容
    var euid = $("#euid").val();
    //如果表单中有用户id的内容，说明本次为编辑操作
    if (euid > 0) {
        var url = getProjectPath() + "/user/editUser";
        $.post(url, $("#addOrEditUser").serialize(), function (response) {
            alert(response.message)
            if (response.success == true) {
                window.location.href = getProjectPath() + "/user/search";
            }
        })
    }
    //如果表单中没有用户id，说明本次为添加操作
    else {
        var url = getProjectPath() + "/user/addUser";
        $.post(url, $("#addOrEditUser").serialize(), function (response) {
            alert(response.message)
            if (response.success == true) {
                window.location.href = getProjectPath() + "/user/search";
            }
        })
    }
}
//归还图书时执行
function returnBook(bid) {
    var r = confirm("确定归还图书?");
    if (r) {
        var url = getProjectPath() + "/book/returnBook?id=" + bid;
        $.get(url, function (response) {
            alert(response.message)
            //还书成功时，刷新当前借阅的列表数据
            if (response.success == true) {
                window.location.href = getProjectPath() + "/book/searchBorrowed";
            }
        })
    }
}

//确认图书已经归还
function returnConfirm(bid) {
    var r = confirm("确定图书已归还?");
    if (r) {
        var url = getProjectPath() + "/book/returnConfirm?id=" + bid;
        $.get(url, function (response) {
            alert(response.message)
            //还书确认成功时，刷新当前借阅的列表数据
            if (response.success == true) {
                window.location.href = getProjectPath() + "/book/searchBorrowed";
            }
        })
    }
}


//检查图书信息的窗口中，图书信息填写是否完整
function checkval() {
    var $inputs = $("#addOrEditTab input")
    var count = 0;
    $inputs.each(function () {
        if ($(this).val() == '' || $(this).val() == "不能为空！") {
            count += 1;
        }
    })
    //如果全部输入框都填写完整，解除确认按钮的禁用状态
    if (count == 0) {
        $("#aoe").attr("disabled", false)
    }
}

$(function () {
    var $inputs = $("#addOrEditBook input");
    var isbnRegex = /^[0-9]{13}$/;
    var ebisbn = "";

    $inputs.each(function () {
        var $input = $(this);

        // 解绑之前的blur事件，防止多次绑定
        $input.off('blur').on('blur', function () {
            var ebid = $("#ebid").val();
            var value = $input.val();
            var attrName = $input.attr("name");

            if (value === '') {
                $input.css("color", "red").val("不能为空！");
                $("#aoe").prop("disabled", true);
            } else {
                if (attrName === "isbn") {
                    if (!isbnRegex.test(value)) {
                        alert("请输入标准的13位isbn编码");
                        $input.val("");
                        $("#aoe").prop("disabled", true);
                        return;
                    }

                    var url = getProjectPath() + "/book/test1";
                    var book = {}; // 创建一个对象来存储用户数据
                    book[attrName] = value; // 将输入框的值设置到book对象的相应属性中
                    book["id"] = ebid;

                    // 使用$.ajax发送请求
                    $.ajax({
                        url: url,
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify(book),
                        dataType: "json",
                        success: function (response) {
                            // 检查后端返回的占用状态
                            if (response.success) {
                                alert(response.message);
                                $input.val("");
                                $("#aoe").prop("disabled", true);
                            }
                        },
                        error: function (xhr, status, error) {
                            // 处理请求错误
                            console.error("请求失败: " + error);
                            alert("请求失败，请检查网络或联系管理员");
                        }
                    });
                } else {
                    // 这里假设checkval是一个已定义的函数，用于进一步的验证逻辑
                    checkval();
                }
            }
        });

        $input.off('focus').on('focus', function () {
            if ($input.val() === "不能为空！") {
                $input.css("color", "").val("");
            }
            // 更新当前输入框的值
            if ($input.attr("name") === "isbn") {
                ebisbn = $input.val();
            }
        });
    });
});



// 页面加载完成后，给读者模态窗口的输入框绑定失去焦点和获取焦点事件
$(function () {
    var $inputs = $("#addOrEditUser input");
    var nameRegex = /^[a-zA-Z0-9_\u4e00-\u9fa5\uF900-\uFA2D]{2,15}$/;
    var emailRegex = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
    var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
    var euname = "";
    var euemail = "";
    var eupassword = "";

    $inputs.each(function () {
        var $input = $(this);

        // 解绑之前的blur事件，防止多次绑定
        $input.off('blur').on('blur', function () {
            var euidValue = $("#euid").val();
            var value = $input.val();
            var attrName = $input.attr("name");

            if (value === '') {
                $input.css("color", "red").val("不能为空！");
                $("#aoe").prop("disabled", true);
            } else {
                if (attrName === "name" || attrName === "email") {
                    var url = getProjectPath() + "/user/test1";
                    var user = {}; // 创建一个对象来存储用户数据
                    user[attrName] = value; // 将输入框的值设置到user对象的相应属性中
                    user["id"] = euidValue;

                    // 使用$.ajax发送请求
                    $.ajax({
                        url: url,
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify(user),
                        dataType: "json",
                        success: function (response) {
                            // 检查后端返回的占用状态
                            if (response.success) {
                                $input.val("");
                                // 如果占用，显示警告信息
                                alert(response.message);
                                $("#aoe").prop("disabled", true);
                            }
                        },
                        error: function (xhr, status, error) {
                            // 处理请求错误
                            console.error("请求失败: " + error);
                            alert("请求失败，请检查网络或联系管理员");
                        }
                    });
                }

                if (attrName === "name" && euname !== value) {
                    if (!nameRegex.test(value)) {
                        alert("用户名格式不正确，请输入2-15位的字母、数字、下划线和中文组合。");
                        $input.val("");
                        $("#aoe").prop("disabled", true);
                    }
                } else if (attrName === "email" && euemail !== value) {
                    if (!emailRegex.test(value)) {
                        alert("邮箱格式不正确，请检查您的输入。");
                        $input.val("");
                        $("#aoe").prop("disabled", true);
                    }
                } else if (attrName === "password" && eupassword !== value) {
                    if (!passwordRegex.test(value)) {
                        alert("密码长度要为8位，至少包含大小写字母和数字的组合。");
                        $input.val("");
                        $("#aoe").prop("disabled", true);
                    }
                } else {
                    // 这里假设checkval是一个已定义的函数，用于进一步的验证逻辑
                    checkval();
                }
            }
        });

        // 解绑之前的focus事件，防止多次绑定
        $input.off('focus').on('focus', function () {
            if ($input.val() === "不能为空！") {
                $input.css("color", "").val("");
            }
            // 更新当前输入框的值
            switch ($input.attr("name")) {
                case "name":
                    euname = $input.val();
                    break;
                case "email":
                    euemail = $input.val();
                    break;
                case "password":
                    eupassword = $input.val();
                    break;
            }
        });
    });
});



//获取当前项目的名称
function getProjectPath() {
    //获取主机地址之后的目录，如： CloudLibraryManagementSystem/admin/books.jsp
    var pathName = window.document.location.pathname;
    //获取带"/"的项目名，如：//CloudLibraryManagementSystem
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return projectName;
}

$(document).ready(function() {
    var url = getProjectPath()+'/report/stats';
    fetch(url)
        .then(response => response.json())
        .then(data => {
            // 确认元素存在
            var totalBooksElement = document.getElementById('totalBooks');
            var totalReadersElement = document.getElementById('totalReaders');
            var currentBorrowedBooksElement = document.getElementById('currentBorrowedBooks');
            var totalBorrowedBooksElement = document.getElementById('totalBorrowedBooks');

            if (totalBooksElement) totalBooksElement.innerText = data.totalBooks;
            if (totalReadersElement) totalReadersElement.innerText = data.totalReaders;
            if (currentBorrowedBooksElement) currentBorrowedBooksElement.innerText = data.currentBorrowedBooks;
            if (totalBorrowedBooksElement) totalBorrowedBooksElement.innerText = data.totalBorrowedBooks;

            $('#monthPicker').on('change', function() {
                var selectedMonth = $(this).val();
                fetchDailyStats(selectedMonth);
            });

            function fetchDailyStats(month) {
                $.ajax({
                    url: getProjectPath()+'/report/dailyStats',
                    data: { month: month },
                    success: function(data) {
                        var dailyChart = echarts.init(document.getElementById('dailyBorrowChart'));
                        var dailyOption = {
                            title: { text: '每天借阅量' },
                            tooltip: {},
                            xAxis: { type: 'category', data: data.map(stat => stat.day) },
                            yAxis: { type: 'value' },
                            series: [{ data: data.map(stat => stat.count), type: 'line' }]
                        };
                        dailyChart.setOption(dailyOption);
                    },
                    error: function(err) {
                        console.error('请求每日数据错误', err);
                    }
                });
            }

            var popularBooksChart = echarts.init(document.getElementById('popularBooks'));
            var popularBooksOption = {
                title: { text: '最受欢迎的图书' },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                xAxis: {
                    type: 'category',
                    data: data.mostPopularBooks.map(book => book.bookname),
                    axisLabel: {
                        interval: 0,
                        rotate: 30,  // 旋转角度
                        textStyle: {
                            fontSize: 10
                        }
                    }
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: data.mostPopularBooks.map(book => book.count),
                    type: 'bar',
                    label: {
                        show: true,
                        position: 'top'
                    }
                }]
            };
            popularBooksChart.setOption(popularBooksOption);
            var activityList = document.getElementById('activityList');
            if (activityList) {
                activityList.innerHTML = ''; // 清空列表
                data.latestActivities.forEach(activity => {
                    var li = document.createElement('li');
                    li.className = 'list-group-item'; // 添加 Bootstrap 样式类
                    li.innerHTML = `
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1">${activity.bookname}</h5>
                            <small>${activity.borrowTime}</small>
                        </div>
                        <p class="mb-1">借阅人: ${activity.borrower}</p>
                    `;
                    activityList.appendChild(li);
                });
            }
        }).catch(error => console.error('获取数据失败:', error));
});


/**
 * 数据展示页面分页插件的参数
 * cur 当前页
 * total 总页数
 * len   显示多少页数
 * pagesize 1页显示多少条数据
 * gourl 页码变化时 跳转的路径
 * targetId 分页插件作用的id
 */
var pageargs = {
    cur: 1, total: 0, len: 5, pagesize: 10, gourl: "", targetId: 'pagination', callback: function (total) {
        var oPages = document.getElementsByClassName('page-index');
        for (var i = 0; i < oPages.length; i++) {
            oPages[i].onclick = function () {
                changePage(this.getAttribute('data-index'), pageargs.pagesize);
            }
        }
        var goPage = document.getElementById('go-search');
        goPage.onclick = function () {
            var index = document.getElementById('yeshu').value;
            if (!index || (+index > total) || (+index < 1)) {
                return;
            }
            changePage(index, pageargs.pagesize);
        }
    }
}
/**
 *图书查询栏的查询参数
 * name 图书名称
 * author 图书作者
 * press 图书出版社
 */
var bookVO = {
    name: '', author: '', press: ''
}
/**
 *借阅记录查询栏的查询参数
 * bookname 图书名称
 * borrower 借阅人
 */
var recordVO = {
    bookname: '', borrower: ''
}
var userVO = {
    name: '', email: '', role: ''
}

//数据展示页面分页插件的页码发送变化时执行
function changePage(pageNo, pageSize) {
    pageargs.cur = pageNo;
    pageargs.pagesize = pageSize;
    document.write("<form action=" + pageargs.gourl + " method=post name=form1 style='display:none'>");
    document.write("<input type=hidden name='pageNum' value=" + pageargs.cur + " >");
    document.write("<input type=hidden name='pageSize' value=" + pageargs.pagesize + " >");
    //如果跳转的是图书信息查询的相关链接，提交图书查询栏中的参数
    if (pageargs.gourl.indexOf("book") >= 0) {
        document.write("<input type=hidden name='name' value=" + bookVO.name + " >");
        document.write("<input type=hidden name='author' value=" + bookVO.author + " >");
        document.write("<input type=hidden name='press' value=" + bookVO.press + " >");
    }
    //如果跳转的是图书记录查询的相关链接，提交图书记录查询栏中的参数
    if (pageargs.gourl.indexOf("record") >= 0) {
        document.write("<input type=hidden name='bookname' value=" + recordVO.bookname + " >");
        document.write("<input type=hidden name='borrower' value=" + recordVO.borrower + " >");
    }
    if (pageargs.gourl.indexOf("user") >= 0) {
        document.write("<input type=hidden name='name' value=" + userVO.name + " >");
        document.write("<input type=hidden name='email' value=" + userVO.email + " >");
        document.write("<input type=hidden name='role' value=" + userVO.role + " >");
    }
    document.write("</form>");
    document.form1.submit();
    pagination(pageargs);
}


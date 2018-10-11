<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>laypage 分页</title>
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/layui/css/layui.css">
</head>
 
<body>
<div class="layui-container" style="margin-top: 50px">
    <table class="layui-table">
        <thead>
            <tr>
                <th>id</th>
                <th>number</th>
                <th>name</th>
                <th>sex</th>
                <th>age</th>
                <th>操作</th>
            </tr>
        </thead>
        <!--分页数据盛放器-->
        <tbody></tbody>
        <!---------------->
    </table>
    <!--分页容器-->
    <div id="page" style="text-align:right"></div>
    <!----------->
</div>
 
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/layui/layui.js"></script>
<script>
    let curr = 1;// 当前页，初始值设为 1
    let limit = 10;// 每页条数，初始值设为 10
    let total;// 总记录数
 
    $(document).ready(function () {
        getInfo();// 获取数据
        toPage();// 进行分页
    });
 
    // 获取数据
    function getInfo() {
        $.ajax({
            type: "post",
            url: "/simple/stu/listAllPage",
            async: false,// 设置同步请求，加载数据前锁定浏览器
            dataType: 'json',
            data: {
                "curr": curr, // 查询页码
                "limit": limit, // 每页条数
            },
            success: successFu
        });
    }
 
    // 数据请求成功
    function successFu(pager) {
        //console.log(data);
        // 1.清空原数据
        $("tbody").html("");
 
        // 2.重新赋值页码、条数、总记录数
        curr = pager.start;
        limit = pager.maxresult;
        total = pager.totalrecord;
 
        // 3.渲染数据
        // 当前查询无数据时
        if (pager.totalrecord == 0) {
            $("tbody").html("<tr><td colspan='7' class='text-center'>暂无数据</td></tr>");
            return;
        }
 
        let text = "";
        $.each(pager.records, (i, item) => {
            text += `
            <tr>
                <th>${item.did}</th>
                <th>${item.dname}</th>
                <th>${item.cname}</th>
                <th>${item.pname}</th>
                <th>${item.postcode}</th>
                <th>${item.areacode}</th>
                <th>${item.orderid}</th>
            </tr>
             `;
        });
        $("tbody").html(text);
    }
 
    // 进行分页
    function toPage() {
        layui.use('laypage', function () {
            let laypage = layui.laypage;
            // 调用分页
            laypage.render({
                // 分页容器的id
                elem: 'page',// *必选参数
                // 数据总数，从服务端得到
                count: total,// *必选参数
                // 每页显示的条数。laypage将会借助 count 和 limit 计算出分页数。
                //limit:limit,
                // 起始页
                //curr:Pager,
                // 连续出现的页码个数
                //groups:5,
                // 自定义每页条数的选择项
                limits: [10, 25, 50, 100],
                // 自定义首页、尾页、上一页、下一页文本
                first: '首页',
                last: '尾页',
                prev: '<em><<</em>',
                next: '<em>>></em>',
                // 自定义主题
                theme: "#FF5722",
                // 自定义排版
                layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'],
                // 渲染数据
                jump: function (data, first) {
                    // data包含了当前分页的所有参数
                    curr = data.curr;
                    limit = data.limit;
 
                    // 首次不执行
                    if (!first) {
                        getInfo();
                    }
                }
            });
        })
    }
</script>
</body>
</html>

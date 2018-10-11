var ctx = $("#ctx").val();
function create() {
	var projectId = $("#pid").val();
	layer.open({
		type : 2,
		title : '创建人员',
		// skin : 'layui-layer-molv',// 皮肤
		shift : -1,// flash模糊问题
		scrollbar : false, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '800px', '790px' ],
		scrolling : 'yes',
		content : ctx + '/s/labour/employee/create?projectId=' + projectId,
	});
}
function edit(uk) {
	layer.open({
		type : 2,
		title : '修改人员',
		// skin : 'layui-layer-molv',// 皮肤
		shift : -1,// flash模糊问题
		scrollbar : false, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '800px', '740px' ],
		content : ctx + '/s/labour/employee/edit?id=' + uk,
	});
}
function show(uk) {
	layer.open({
		type : 2,
		title : '人员详细',
		// skin : 'layui-layer-molv',// 皮肤
		shift : -1,// flash模糊问题
		scrollbar : false, // 屏蔽浏览器滚动条
		maxmin : false, // 开启最大化最小化按钮
		area : [ '800px', '720px' ],
		content : ctx + '/s/labour/employee/show?id=' + uk,
	});
}
function del(uk) {
	// 信息框-例2
	layer.msg('你确定要删除吗', {
		time : 0, // 不自动关闭
		btn : [ '确定', '取消' ],
		yes : function(index) {
			layer.close(index);
			// 执行删除操作
			$.post(ctx + "/s/labour/employee/delete", {
				id : uk,
			}, function(data) {
				if (data == 'success') {
					$("#tr_" + uk).css("display", "none");
				}
			});
		}
	});
}

//证书
function cert(eid) {
	layer.open({
		type : 2,
		title : '证书',
		// skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '800px', '580px' ],
		content : ctx + '/s/labour/employee/info/cert/list?employeeId=' + eid,
	});
}
//安全教育
function edu(eid) {
	layer.open({
		type : 2,
		title : '安全教育',
		// skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '800px', '580px' ],
		content : ctx + '/s/labour/employee/info/edu/list?employeeId=' + eid,
	});
}
//信用评价
function credit(eid) {
	layer.open({
		type : 2,
		title : '信用评价',
		// skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '800px', '580px' ],
		content : ctx + '/s/labour/employee/info/credit/list?employeeId=' + eid,
	});
}
//健康检查 
function health(eid) {
	layer.open({
		type : 2,
		title : '健康检查',
		// skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '800px', '580px' ],
		content : ctx + '/s/labour/employee/info/health/list?employeeId=' + eid,
	});
}
//保险 
function insurance(eid) {
	layer.open({
		type : 2,
		title : '保险',
		// skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '800px', '580px' ],
		content : ctx + '/s/labour/employee/info/insurance/list?employeeId=' + eid,
	});
}
//工资
function salary(eid) {
	layer.open({
		type : 2,
		title : '工资',
		// skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '800px', '580px' ],
		content : ctx + '/s/labour/employee/info/salary/list?employeeId=' + eid,
	});
}
//黑名单
function blackList(eid) {
	layer.open({
		type : 2,
		title : '黑名单',
		// skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '800px', '580px' ],
		content : ctx + '/s/labour/employee/info/blacklist/list?employeeId=' + eid,
	});
}
function change() {
	var sel = document.getElementById("sel");
	var selValue = sel.value;
	if (selValue == 7) {
		document.getElementById("date").style.display = "";
	}
	if (selValue != 7) {
		document.getElementById("date").style.display = "none";
	}
}

// ***************使用Layer层加载页面相关代码*****************/
var width = '900px';
if (document.body.clientWidth < 900) {
	width = (document.body.clientWidth - 10) + "px";
}
// 统计
function loadAlarmCount(sid) {
	var queryBeginDate = document.getElementById("queryBeginDate").value;
	var queryEndDate = document.getElementById("queryEndDate").value;
	var sel = document.getElementById("sel");
	var selValue = sel.value;
	layer.open({
		type : 2,
		title : '报警信息',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '400px' ],
		content : 'api/count/alarm/' + sid+"?queryDateType="+selValue+"&queryBeginDate="
				+ queryBeginDate + "&queryEndDate=" + queryEndDate,
	});
}

// 报警
function loadAlarm(sid) {
	var queryBeginDate = document.getElementById("queryBeginDate").value;
	var queryEndDate = document.getElementById("queryEndDate").value;
	layer.open({
		type : 2,
		title : '设备报警数据详情',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '590px' ],
		content : 'api/data/alarm/' + sid + "?queryAlarmType=0&queryBeginDate="
				+ queryBeginDate + "&queryEndDate=" + queryEndDate,
	});
}

$(function() {
	var ids = $(".box-body");
	for (var i = 0; i < ids.length; i++) {
		var pid = ids[i].id.split("_")[1];
		//loadDevice(pid, true);
		loadDevice(pid, false);
	}

});


function loadDevice(pid, hidden) {
	$("#box_" + pid)
			.block(
					{
						message : '<img src="resource/template/img/loaders/12.gif" align="absmiddle">',
						css : {
							border : 'none',
							padding : '2px',
							backgroundColor : 'none'
						},
						overlayCSS : {
							backgroundColor : '#000',
							opacity : 0.05,
							cursor : 'wait'
						}
					});

	$.post("manage/run/point/device", {
		pid : pid,
	}, function(data) {
		setTimeout(function() {
			$("#box_" + pid).unblock();
		}, 200);
		$("#data_" + pid).html(data);
		console.log(hidden)
		if (hidden) {
			$("#data_" + pid).css("display", "none");
		}
	});
}

// ***************使用Layer层加载页面相关代码*****************/
var width = '900px';
if (document.body.clientWidth < 900) {
	width = (document.body.clientWidth - 10) + "px";
}



// 视频监控：调用协议
function callVideo(sid) {
	if (window.confirm('确定发起视频调用？')) {
		// alert("确定");
		return true;
	} else {
		// alert("取消");
		return false;
	}
}

// 视频监控
function loadVideo(sid) {
	layer.open({
		type : 2,
		title : '视频通道列表',
		skin : 'layui-layer-molv',// 皮肤
		shift : -1,// flash模糊问题
		scrollbar : false, // 屏蔽浏览器滚动条
		maxmin : false, // 开启最大化最小化按钮
		area : [ '1000px', '630px' ],
		//content : 'api/data/video/' + sid,
		content : 'server/project/video/page?projectId=' + sid, //乐橙云 视频
	});
}

// 视频监控 截图
function loadImage(sid) {
	layer.open({
		type : 2,
		title : '视频监控图片',
		skin : 'layui-layer-molv',// 皮肤
		shift : -1,// flash模糊问题
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '1000px', '650px' ],
		content : 'server/project/video/image/page?projectId=' + sid,
	});
}
// 人脸识别 考勤率
function loadFace(sid) {
	layer.open({
		type : 2,
		title : '人脸识别 考勤数据',
		skin : 'layui-layer-molv',// 皮肤
		shift : -1,// flash模糊问题
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '900px', '650px' ],
		content : 'server/project/face/list/page?projectId=' + sid,
	});
}
// 人脸识别 月报
function loadFaceMonth(sid) {
	layer.open({
		type : 2,
		title : '人脸识别基础数据',
		skin : 'layui-layer-molv',// 皮肤
		shift : -1,// flash模糊问题
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '1280px', '650px' ],
		content : 'cas/page/face/record/month?projectId=' + sid,
	});
}
// 俯视图
function loadOverlook(sid) {
	layer.open({
		type : 2,
		title : '俯视图',
		skin : 'layui-layer-molv',// 皮肤
		shift : -1,// flash模糊问题
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '750px', '520px' ],
		content : 'api/data/overlook/' + sid,
	});
}

// 俯视图3D
function loadOverlook3D(sid) {
	layer.open({
		type : 2,
		title : '俯视图',
		skin : 'layui-layer-molv', // 'layui-layer-lan' , // 皮肤
		shift : -1,// flash模糊问题
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '1000px', '600px' ],
		content : 'server/project/overlook/page/3D?projectId=' + sid,
	});
}

/** ***基本信息* */
// 塔机基本信息
function loadBaseInfo(sid) {
	layer.open({
		type : 2,
		title : '设备基本信息',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '590px' ],
		content : 'api/data/baseInfo/' + sid,
	});
}

// 施工升降机基本信息
function loadBaseInfoHoist(sid) {
	layer.open({
		type : 2,
		title : '设备基本信息',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '590px' ],
		content : 'api/data/baseInfo/hoist/' + sid,
	});
}

// 人员信息:指纹
function loadPersonRecord(sid, personId) {
	layer.open({
		type : 2,
		title : '身份认证信息',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '590px' ],
		content : 'cas/page/equipment/person/record/list?sid=' + sid
				+ "&personId=" + personId,
	});
}

// 吊重
function loadWeight(sid) {
	var queryBeginDate = document.getElementById("queryBeginDate").value;
	var queryEndDate = document.getElementById("queryEndDate").value;
	layer.open({
		type : 2,
		title : '塔机工作循环吊重数据',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '600px' ],
		content : 'api/data/weight/' + sid + "?queryBeginDate="
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
		area : [ width, '600px' ],
		content : 'api/data/alarm/' + sid + "?queryAlarmType=0&queryBeginDate="
				+ queryBeginDate + "&queryEndDate=" + queryEndDate,
	});
}

// 报警
function loadAlarmHoist(sid) {
	var queryBeginDate = document.getElementById("queryBeginDate").value;
	var queryEndDate = document.getElementById("queryEndDate").value;
	layer.open({
		type : 2,
		title : '设备报警数据详情',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '600px' ],
		content : 'api/data/alarm/hoist/' + sid
				+ "?queryAlarmType=0&queryBeginDate=" + queryBeginDate
				+ "&queryEndDate=" + queryEndDate,
	});
}

// 违章
function loadVio(sid) {
	var queryBeginDate = document.getElementById("queryBeginDate").value;
	var queryEndDate = document.getElementById("queryEndDate").value;
	layer.open({
		type : 2,
		title : '设备违章数据详情',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '600px' ],
		content : 'api/data/vio/' + sid + "?queryAlarmType=0&queryBeginDate="
				+ queryBeginDate + "&queryEndDate=" + queryEndDate,
	});
}

// 违章
function loadVioHoist(sid) {
	var queryBeginDate = document.getElementById("queryBeginDate").value;
	var queryEndDate = document.getElementById("queryEndDate").value;
	layer.open({
		type : 2,
		title : '设备违章数据详情',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '600px' ],
		content : 'api/data/vio/hoist/' + sid
				+ "?queryAlarmType=0&queryBeginDate=" + queryBeginDate
				+ "&queryEndDate=" + queryEndDate,
	});
}

// 实时数据
function loadNewData(sid) {
	layer.open({
		type : 2,
		title : '设备运行实时数据',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '590px' ],
		content : 'api/data/newData/' + sid,
	});
}
// 实时数据
function loadNewDataHoist(sid) {
	layer.open({
		type : 2,
		title : '设备运行实时数据',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '590px' ],
		content : 'api/data/newData/hoist/' + sid,
	});
}

// 塔机历史数据
function loadHistoryData(sid) {
	var queryBeginDate = document.getElementById("queryBeginDate").value;
	var queryEndDate = document.getElementById("queryEndDate").value;
	layer.open({
		type : 2,
		title : '设备运行历史数据',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '590px' ],
		content : 'api/data/historyData/' + sid + "?queryBeginDate="
				+ queryBeginDate + "&queryEndDate=" + queryEndDate,
	});
}
// 施工升降机历史数据
function loadHistoryDataHoist(sid) {
	var queryBeginDate = document.getElementById("queryBeginDate").value;
	var queryEndDate = document.getElementById("queryEndDate").value;
	layer.open({
		type : 2,
		title : '设备运行历史数据',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '590px' ],
		content : 'api/data/historyData/hoist/' + sid + "?queryBeginDate="
				+ queryBeginDate + "&queryEndDate=" + queryEndDate,
	});
}
// 塔机运行图
function loadRunData(sid) {
	layer.open({
		type : 2,
		title : '设备运行图',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '780px', '590px' ],
		content : 'api/data/runData/' + sid,
	});
}

// 施工升降机运行图
function loadRunDataHoist(sid) {
	layer.open({
		type : 2,
		title : '设备运行图',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '780px', '500px' ],
		content : 'api/data/runData/hoist/' + sid,
	});
}

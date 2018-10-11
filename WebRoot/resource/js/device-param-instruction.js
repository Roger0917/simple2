var width = '1050px';
if (document.body.clientWidth < 900) {
	width = (document.body.clientWidth - 10) + "px";
}
// 设备参数配置页面
function loadConfigPage(sid) {
	layer.open({
		type : 2,
		title : '设备参数配置',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '600px' ],
		content : 'api/param/config/' + sid,
	});
}
// 指令管理界面
function loadInstruction(sid) {
	layer.open({
		type : 2,
		title : '设备指令管理',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '600px' ],
		content : 'api/instruction/manage/' + sid,
	});
}


function currentInstruction(){
	var sid = $("#sid").val();
	console.log(sid);
	$.post("api/instruction/current", {
		sid : sid,
	}, function(data) {
		var jsonObj = eval('(' + data + ')');
		//读命令发送成功，发送时实现插队机制
		if(jsonObj.result=='success'){
			console.log(data);
			//指令原始内容
			$("#content").html("<span style='color:green;'>指令内容："+jsonObj.content+"</span>");
			//解析后的指令内容
			$("#content-Analysis").html("<span style='color:green;'>指令分析："+jsonObj.contentAnalysis+"</span>");
		}else{
			$("#content").html("<span style='color:green;'>无指令</span>");
			$("#content-Analysis").html("<span style='color:green;'></span>");
		}
	});
}


function deleteInstruction(lineId,instruction,typeOrfileId){
	var sid = $("#sid").val();
	console.log(sid);
	$.post("api/instruction/delete", {
		sid : sid,
		instruction:instruction,
		typeOrFileId:typeOrfileId,
	}, function(data) {
		console.log(data);
		if(data=='success'){
			//解析后的指令内容
			$("#"+lineId).remove();
		}else{
			alert("网络异常");
		}
	});
}

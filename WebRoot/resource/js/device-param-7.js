
//系统配置信息读指令发出
$('#tab-message-7_read').on('click', function() {
	var sid = $("#sid").val();
	$.post("api/param/config/send/readM7", {
		sid : sid,
	}, function(data) {
		console.log(data);
		if(data=='success'){
			//读命令发送成功，发送时实现插队机制
			$("#tab-message-7_tip").html("<span style='color:green;'>【系统配置信息】读命令发送成功，等待数据回传</span>");
			setTimeout("checkM7Read()", 1000);
		}else{
			$("#tab-message-7_tip").html("<span style='color:red;'>【系统配置信息】指令发送时，网络或程序异常！</span>");
		}
	});
});

//系统配置信息读指令检查
function checkM7Read(){
	var sid = $("#sid").val();
	console.log(sid);
	$.post("api/param/config/check/readM7", {
		sid : sid,
	}, function(data) {
		console.log(data);
		var jsonObj = eval('(' + data + ')');
		//读命令发送成功，发送时实现插队机制
		if(jsonObj.result=='success'){
			console.log("success:"+jsonObj);
			$("#tab-message-7_tip").html("【系统配置信息】数据回传成功，可以开始写入");
			$("#tab-message-7 input[name='version']").val(jsonObj.version); 
			$("#tab-message-7 input[name='stateInterval']").val(jsonObj.stateInterval); 
			$("#tab-message-7 input[name='eventInterval']").val(jsonObj.eventInterval); 
			$("#tab-message-7 input[name='distance']").val(jsonObj.distance); 
			$("#tab-message-7 input[name='limitFaut']").val(jsonObj.limitFaut); 
			$("#tab-message-7 input[name='weightFault']").val(jsonObj.weightFault); 
			$("#tab-message-7 input[name='angleFault']").val(jsonObj.angleFault); 
			$("#tab-message-7 input[name='weight']").val(jsonObj.weight); 
			$("#tab-message-7 input[name='height']").val(jsonObj.height); 
			$("#tab-message-7 input[name='angleWarn']").val(jsonObj.angleWarn); 
			$("#tab-message-7 input[name='angleAlarm']").val(jsonObj.angleAlarm); 
			$("#tab-message-7 input[name='distanceWarn']").val(jsonObj.distanceWarn); 
			$("#tab-message-7 input[name='distanceAlarm']").val(jsonObj.distanceAlarm); 
			$("#tab-message-7 input[name='masterVersion']").val(jsonObj.masterVersion); 
			$("#tab-message-7 input[name='dbVersion']").val(jsonObj.dbVersion); 
		}else{
			console.log("wait:"+jsonObj);
			$("#tab-message-7_tip").html("<span style='color:green;'>等待【系统配置信息】数据回传，检查时间："+jsonObj.checkDate+"</span>");
			setTimeout("checkM7Read()", 2000);
		}
	});
}

//系统配置信息写指令发出
$('#tab-message-7_write').on('click', function() {
	var sid = $("#sid").val();
	var formData = $('#tab-message-form7').serialize()+"&sid="+sid;
	$.ajax({
        type: "POST",
        dataType: "html",
        url:"api/param/config/send/writeM7",
        data:formData,
        success: function (data) {
    		if(data=='success'){
    			//写命令发送成功，发送时实现插队机制
    			$("#tab-message-7_tip").html("<span style='color:green;'>写命令发送成功，等待终端回应</span>");
    			setTimeout("checkM7Write()", 1000);
    		}else if(data=="NotWrite"){
    			$("#tab-message-7_tip").html("<span style='color:red;'>【系统配置信息】写入前，请先读取数据</span>");
    		}else{
    			$("#tab-message-7_tip").html("<span style='color:red;'>【系统配置信息】写入时，网络或程序异常！</span>");
    		}
        },
        error: function(data) {
        	$("#tab-message-7_tip").html("<span style='color:red;'>【系统配置信息】写入前，请先读取数据</span>");
        }
    });
	
});

//系统配置信息写指令检查
function checkM7Write(){
	var sid = $("#sid").val();
	$.post("api/param/config/check/writeM7", {
		sid : sid,
	}, function(data) {
		var jsonObj = eval('(' + data + ')');
		if(jsonObj.result=='success'){
			console.log(data);
			$("#tab-message-7_tip").html("<span style='color:green;'>【系统配置信息】数据写入成功，检查时间："+jsonObj.checkDate+"</span>");
		}else{
			$("#tab-message-7_tip").html("<span style='color:green;'>等待终端回应【系统配置信息】写入结果，检查时间："+jsonObj.checkDate+"</span>");
			setTimeout("checkM7Write()", 2000);
		}
	});
}

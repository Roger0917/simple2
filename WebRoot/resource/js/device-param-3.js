
//限位信息读指令发出
$('#tab-message-3_read').on('click', function() {
	var sid = $("#sid").val();
	$.post("api/param/config/send/readM3", {
		sid : sid,
	}, function(data) {
		console.log(data);
		if(data=='success'){
			//读命令发送成功，发送时实现插队机制
			$("#tab-message-3_tip").html("<span style='color:green;'>【限位信息】读命令发送成功，等待数据回传</span>");
			setTimeout("checkM3Read()", 1000);
		}else{
			$("#tab-message-3_tip").html("<span style='color:red;'>【限位信息】指令发送时，网络或程序异常！</span>");
		}
	});
});

//限位信息读指令检查
function checkM3Read(){
	var sid = $("#sid").val();
	$.post("api/param/config/check/readM3", {
		sid : sid,
	}, function(data) {
		console.log("data:"+data);
		var jsonObj = eval('(' + data + ')');
		//读命令发送成功，发送时实现插队机制
		if(jsonObj.result=='success'){
			$("#tab-message-3_tip").html("【限位信息】数据回传成功，可以开始写入");
			$("#tab-message-3 input[name='limitVersion']").val(jsonObj.limitVersion);  
			$("#tab-message-3 input[name='leftLimit']").val(jsonObj.leftLimit);  
			$("#tab-message-3 input[name='rightLimit']").val(jsonObj.rightLimit);  
			$("#tab-message-3 input[name='farLimit']").val(jsonObj.farLimit);  
			$("#tab-message-3 input[name='nearLimit']").val(jsonObj.nearLimit);  
			$("#tab-message-3 input[name='hightLimit']").val(jsonObj.hightLimit);  
			$("#tab-message-3 input[name='liftLimit']").val(jsonObj.liftLimit); 
			$("#tab-message-3 input[name='torqueLimit']").val(jsonObj.torqueLimit); 
			var sensor =jsonObj.xdipangleSensor+jsonObj.fieldWindSensor+
						jsonObj.walkSensor+jsonObj.weightSensor+
						jsonObj.tallSensor+jsonObj.variableSensor+jsonObj.towerRotationSensor;
			/**
			var sensor =jsonObj.towerRotationSensor+jsonObj.variableSensor+
						jsonObj.tallSensor+jsonObj.weightSensor+
						jsonObj.walkSensor+jsonObj.fieldWindSensor+jsonObj.xdipangleSensor;
						*/
			$("#tab-message-3 input[name='sensor']").val(sensor);  
		}else{
			console.log("wait:"+jsonObj);
			$("#tab-message-3_tip").html("<span style='color:green;'>等待【限位信息】数据回传，检查时间："+jsonObj.checkDate+"</span>");
			setTimeout("checkM3Read()", 2000);
		}
	});
}

//限位信息写指令发出
$('#tab-message-3_write').on('click', function() {
	var sid = $("#sid").val();
	var formData = $('#tab-message-form3').serialize()+"&sid="+sid;
	$.ajax({
        type: "POST",
        dataType: "html",
        url:"api/param/config/send/writeM3",
        data:formData,
        success: function (data) {
    		if(data=='success'){
    			//写命令发送成功，发送时实现插队机制
    			$("#tab-message-3_tip").html("<span style='color:green;'>写命令发送成功，等待终端回应</span>");
    			setTimeout("checkM3Write()", 1000);
    		}else if(data=="NotWrite"){
    			$("#tab-message-3_tip").html("<span style='color:red;'>【限位信息】写入前，请先读取数据</span>");
    		}else{
    			$("#tab-message-3_tip").html("<span style='color:red;'>【限位信息】写入时，网络或程序异常！</span>");
    		}
        },
        error: function(data) {
        	$("#tab-message-1_tip").html("<span style='color:red;'>【限位信息】写入前，请先读取数据</span>");
        }
    });
	
});

//限位信息写指令检查
function checkM3Write(){
	var sid = $("#sid").val();
	$.post("api/param/config/check/writeM3", {
		sid : sid,
	}, function(data) {
		var jsonObj = eval('(' + data + ')');
		if(jsonObj.result=='success'){
			console.log(data);
			$("#tab-message-1_tip").html("<span style='color:green;'>【限位信息】数据写入成功，检查时间："+jsonObj.checkDate+"</span>");
		}else{
			$("#tab-message-1_tip").html("<span style='color:green;'>等待终端回应【限位信息】写入结果，检查时间："+jsonObj.checkDate+"</span>");
			setTimeout("checkM1Write()", 2000);
		}
	});
}

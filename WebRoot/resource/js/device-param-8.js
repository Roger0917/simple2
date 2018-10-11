
//安装标定信息读指令发出
$('#tab-message-8_read').on('click', function() {
	var sid = $("#sid").val();
	$.post("api/param/config/send/readM8", {
		sid : sid,
	}, function(data) {
		console.log(data);
		if(data=='success'){
			//读命令发送成功，发送时实现插队机制
			$("#tab-message-8_tip").html("<span style='color:green;'>【安装标定信息】读命令发送成功，等待数据回传</span>");
			setTimeout("checkM8Read()", 1000);
		}else{
			$("#tab-message-8_tip").html("<span style='color:red;'>【安装标定信息】指令发送时，网络或程序异常！</span>");
		}
	});
});

//安装标定信息读指令检查
function checkM8Read(){
	var sid = $("#sid").val();
	console.log(sid);
	$.post("api/param/config/check/readM8", {
		sid : sid,
	}, function(data) {
		console.log(data);
		var jsonObj = eval('(' + data + ')');
		//读命令发送成功，发送时实现插队机制
		if(jsonObj.result=='success'){
			console.log("success:"+jsonObj);
			$("#tab-message-8_tip").html("【安装标定信息】数据回传成功，可以开始写入");
			$("#tab-message-8 input[name='version']").val(jsonObj.version); 
			$("#tab-message-8 input[name='amplitudeFirstSampling']").val(jsonObj.amplitudeFirstSampling); 
			$("#tab-message-8 input[name='amplitudeFirstReal']").val(jsonObj.amplitudeFirstReal); 
			$("#tab-message-8 input[name='amplitudeSecondSampling']").val(jsonObj.amplitudeSecondSampling); 
			$("#tab-message-8 input[name='amplitudeSecondReal']").val(jsonObj.amplitudeSecondReal); 
			$("#tab-message-8 input[name='amplitudeK']").val(jsonObj.amplitudeK); 
			$("#tab-message-8 input[name='amplitudeD']").val(jsonObj.amplitudeD); 
			
			$("#tab-message-8 input[name='heightFirstSampling']").val(jsonObj.heightFirstSampling); 
			$("#tab-message-8 input[name='heightFirstReal']").val(jsonObj.heightFirstReal); 
			$("#tab-message-8 input[name='heightSecondSampling']").val(jsonObj.heightSecondSampling); 
			$("#tab-message-8 input[name='heightSecondReal']").val(jsonObj.heightSecondReal); 
			$("#tab-message-8 input[name='heightK']").val(jsonObj.heightK); 
			$("#tab-message-8 input[name='heightD']").val(jsonObj.heightD); 
			
			$("#tab-message-8 input[name='weightFirstSampling']").val(jsonObj.weightFirstSampling); 
			$("#tab-message-8 input[name='weightFirstReal']").val(jsonObj.weightFirstReal); 
			$("#tab-message-8 input[name='weightSecondSampling']").val(jsonObj.weightSecondSampling); 
			$("#tab-message-8 input[name='weightSecondReal']").val(jsonObj.weightSecondReal); 
			$("#tab-message-8 input[name='weightK']").val(jsonObj.weightK); 
			$("#tab-message-8 input[name='weightD']").val(jsonObj.weightD); 
			
			$("#tab-message-8 input[name='masterGear']").val(jsonObj.masterGear); 
			$("#tab-message-8 input[name='gyratoryDirection']").val(jsonObj.gyratoryDirection); 
			$("#tab-message-8 input[name='followGear']").val(jsonObj.followGear); 
			$("#tab-message-8 input[name='gear']").val(jsonObj.gear); 
			$("#tab-message-8 input[name='zeroAngleSampling']").val(jsonObj.zeroAngleSampling); 
			$("#tab-message-8 input[name='insallZeroPoint']").val(jsonObj.insallZeroPoint); 
			$("#tab-message-8 input[name='pointSampling']").val(jsonObj.pointSampling); 
			$("#tab-message-8 input[name='pointReal']").val(jsonObj.pointReal); 
		}else{
			console.log("wait:"+jsonObj);
			$("#tab-message-8_tip").html("<span style='color:green;'>等待【安装标定信息】数据回传，检查时间："+jsonObj.checkDate+"</span>");
			setTimeout("checkM8Read()", 2000);
		}
	});
}

//安装标定信息写指令发出
$('#tab-message-8_write').on('click', function() {
	var sid = $("#sid").val();
	var formData = $('#tab-message-form8').serialize()+"&sid="+sid;
	$.ajax({
        type: "POST",
        dataType: "html",
        url:"api/param/config/send/writeM8",
        data:formData,
        success: function (data) {
    		if(data=='success'){
    			//写命令发送成功，发送时实现插队机制
    			$("#tab-message-8_tip").html("<span style='color:green;'>写命令发送成功，等待终端回应</span>");
    			setTimeout("checkM8Write()", 1000);
    		}else if(data=="NotWrite"){
    			$("#tab-message-8_tip").html("<span style='color:red;'>【安装标定信息】写入前，请先读取数据</span>");
    		}else{
    			$("#tab-message-8_tip").html("<span style='color:red;'>【安装标定信息】写入时，网络或程序异常！</span>");
    		}
        },
        error: function(data) {
        	$("#tab-message-8_tip").html("<span style='color:red;'>【安装标定信息】写入前，请先读取数据</span>");
        }
    });
	
});

//安装标定信息写指令检查
function checkM8Write(){
	var sid = $("#sid").val();
	$.post("api/param/config/check/writeM8", {
		sid : sid,
	}, function(data) {
		var jsonObj = eval('(' + data + ')');
		if(jsonObj.result=='success'){
			console.log(data);
			$("#tab-message-8_tip").html("<span style='color:green;'>【安装标定信息】数据写入成功，检查时间："+jsonObj.checkDate+"</span>");
		}else{
			$("#tab-message-8_tip").html("<span style='color:green;'>等待终端回应【安装标定信息】写入结果，检查时间："+jsonObj.checkDate+"</span>");
			setTimeout("checkM8Write()", 2000);
		}
	});
}

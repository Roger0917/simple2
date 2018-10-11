
//无线配置信息读指令发出
$('#tab-message-4_read').on('click', function() {
	var sid = $("#sid").val();
	$.post("api/param/config/send/readM4", {
		sid : sid,
	}, function(data) {
		console.log(data);
		if(data=='success'){
			//读命令发送成功，发送时实现插队机制
			$("#tab-message-4_tip").html("<span style='color:green;'>【无线配置信息】读命令发送成功，等待数据回传</span>");
			setTimeout("checkM4Read()", 1000);
		}else{
			$("#tab-message-4_tip").html("<span style='color:red;'>【无线配置信息】指令发送时，网络或程序异常！</span>");
		}
	});
});

//无线配置信息读指令检查
function checkM4Read(){
	var sid = $("#sid").val();
	console.log(sid);
	$.post("api/param/config/check/readM4", {
		sid : sid,
	}, function(data) {
		console.log(data);
		var jsonObj = eval('(' + data + ')');
		//读命令发送成功，发送时实现插队机制
		if(jsonObj.result=='success'){
			console.log("success:"+jsonObj);
			$("#tab-message-4_tip").html("【无线配置信息】数据回传成功，可以开始写入");
			
			$("#tab-message-4 input[name='version']").val(jsonObj.version); 
			$("#tab-message-4 input[name='serverIP']").val(jsonObj.serverIP); 
			$("#tab-message-4 input[name='serverPort']").val(jsonObj.serverPort);
			
			$("#tab-message-4 input[name='module1Address']").val(jsonObj.module1Address); 
			$("#tab-message-4 input[name='module2Address']").val(jsonObj.module2Address); 
			$("#tab-message-4 input[name='networkId']").val(jsonObj.networkId); 
			$("#tab-message-4 input[name='networkType']").val(jsonObj.networkType); 
			$("#tab-message-4 input[name='nodeType']").val(jsonObj.nodeType); 
			$("#tab-message-4 input[name='sendMode']").val(jsonObj.sendMode); 
			$("#tab-message-4 input[name='baudRate']").val(jsonObj.baudRate); 
			$("#tab-message-4 input[name='checkBit']").val(jsonObj.checkBit); 
			$("#tab-message-4 input[name='dataBit']").val(jsonObj.dataBit); 
			$("#tab-message-4 input[name='dataMode']").val(jsonObj.dataMode); 
			$("#tab-message-4 input[name='serialPort']").val(jsonObj.serialPort); 
			$("#tab-message-4 input[name='channel']").val(jsonObj.channel); 
			$("#tab-message-4 input[name='emitRate']").val(jsonObj.emitRate); 
			$("#tab-message-4 input[name='sourceAddress']").val(jsonObj.sourceAddress); 
			
			$("#tab-message-4 input[name='imsi']").val(jsonObj.imsi); 
			$("#tab-message-4 input[name='backupDNS']").val(jsonObj.backupDNS); 
		}else{
			console.log("wait:"+jsonObj);
			$("#tab-message-4_tip").html("<span style='color:green;'>等待【无线配置信息】数据回传，检查时间："+jsonObj.checkDate+"</span>");
			setTimeout("checkM4Read()", 2000);
		}
	});
}

//无线配置信息写指令发出
$('#tab-message-4_write').on('click', function() {
	var sid = $("#sid").val();
	var formData = $('#tab-message-form4').serialize()+"&sid="+sid;
	$.ajax({
        type: "POST",
        dataType: "html",
        url:"api/param/config/send/writeM4",
        data:formData,
        success: function (data) {
    		if(data=='success'){
    			//写命令发送成功，发送时实现插队机制
    			$("#tab-message-4_tip").html("<span style='color:green;'>写命令发送成功，等待终端回应</span>");
    			setTimeout("checkM4Write()", 1000);
    		}else if(data=="NotWrite"){
    			$("#tab-message-4_tip").html("<span style='color:red;'>【无线配置信息】写入前，请先读取数据</span>");
    		}else{
    			$("#tab-message-4_tip").html("<span style='color:red;'>【无线配置信息】写入时，网络或程序异常！</span>");
    		}
        },
        error: function(data) {
        	$("#tab-message-4_tip").html("<span style='color:red;'>【无线配置信息】写入前，请先读取数据</span>");
        }
    });
	
});

//无线配置信息写指令检查
function checkM4Write(){
	var sid = $("#sid").val();
	$.post("api/param/config/check/writeM4", {
		sid : sid,
	}, function(data) {
		var jsonObj = eval('(' + data + ')');
		if(jsonObj.result=='success'){
			console.log(data);
			$("#tab-message-4_tip").html("<span style='color:green;'>【无线配置信息】数据写入成功，检查时间："+jsonObj.checkDate+"</span>");
		}else{
			$("#tab-message-4_tip").html("<span style='color:green;'>等待终端回应【无线配置信息】写入结果，检查时间："+jsonObj.checkDate+"</span>");
			setTimeout("checkM4Write()", 2000);
		}
	});
}

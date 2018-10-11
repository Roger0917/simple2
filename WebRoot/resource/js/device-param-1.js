//基本信息读指令发出
$('#tab-message-1_read').on('click', function() {
	var sid = $("#sid").val();
	$.post("api/param/config/send/readM1", {
		sid : sid,
	}, function(data) {
		console.log(data);
		if(data=='success'){
			//读命令发送成功，发送时实现插队机制
			$("#tab-message-1_tip").html("<span style='color:green;'>读命令发送成功，等待数据回传</span>");
			setTimeout("checkM1Read()", 1000);
		}else{
			$("#tab-message-1_tip").html("<span style='color:red;'>网络或程序异常！</span>");
		}
	});
});

//基本信息读指令检查
function checkM1Read(){
	var sid = $("#sid").val();
	console.log(sid);
	$.post("api/param/config/check/readM1", {
		sid : sid,
	}, function(data) {
		var jsonObj = eval('(' + data + ')');
		//读命令发送成功，发送时实现插队机制
		if(jsonObj.result=='success'){
			console.log(data);
			$("#tab-message-1_tip").html("数据回传成功，可以开始写入");
			$("#tab-message-1 input[name='baseVersion']").val(jsonObj.baseVersion);  
			$("#tab-message-1 input[name='dnumber']").val(jsonObj.dnumber);  
			$("#tab-message-1 input[name='sweight']").val(jsonObj.sweight);  
			$("#tab-message-1 input[name='towerCraneId']").val(jsonObj.towerCraneId);  
			$("#tab-message-1 input[name='towerCraneGroupId']").val(jsonObj.towerCraneGroupId);  
			$("#tab-message-1 input[name='towerCraneType']").val(jsonObj.towerCraneType);  
			$("#tab-message-1 input[name='xcoord']").val(jsonObj.xcoord);  
			$("#tab-message-1 input[name='ycoord']").val(jsonObj.ycoord);  
			$("#tab-message-1 input[name='magnification']").val(jsonObj.magnification);  
			$("#tab-message-1 input[name='armLengthFront']").val(jsonObj.armLengthFront);  
			$("#tab-message-1 input[name='armLengthBack']").val(jsonObj.armLengthBack);  
			$("#tab-message-1 input[name='armHeight']").val(jsonObj.armHeight);  
			$("#tab-message-1 input[name='towerTop']").val(jsonObj.towerTop);  
		}else{
			$("#tab-message-1_tip").html("<span style='color:green;'>等待数据回传，检查时间："+jsonObj.checkDate+"</span>");
			setTimeout("checkM1Read()", 2000);
		}
	});
}

//基本信息写指令发出
$('#tab-message-1_write').on('click', function() {
	var sid = $("#sid").val();
	var formData = $('#tab-message-form1').serialize()+"&sid="+sid;
	$.ajax({
        type: "POST",
        dataType: "html",
        url:"api/param/config/send/writeM1",
        data:formData,
        success: function (data) {
    		if(data=='success'){
    			//写命令发送成功，发送时实现插队机制
    			$("#tab-message-1_tip").html("<span style='color:green;'>写命令发送成功，等待终端回应</span>");
    			setTimeout("checkM1Write()", 1000);
    		}else if(data=="NotWrite"){
    			$("#tab-message-1_tip").html("<span style='color:red;'>写入前，请先读取数据</span>");
    		}else{
    			$("#tab-message-1_tip").html("<span style='color:red;'>网络或程序异常！</span>");
    		}
        },
        error: function(data) {
        	$("#tab-message-1_tip").html("<span style='color:red;'>写入前，请先读取数据</span>");
        }
    });
	
});

//基本信息写指令发出
function checkM1Write(){
	var sid = $("#sid").val();
	$.post("api/param/config/check/writeM1", {
		sid : sid,
	}, function(data) {
		var jsonObj = eval('(' + data + ')');
		if(jsonObj.result=='success'){
			console.log(data);
			$("#tab-message-1_tip").html("<span style='color:green;'>数据写入成功，检查时间："+jsonObj.checkDate+"</span>");
		}else{
			$("#tab-message-1_tip").html("<span style='color:green;'>等待终端回应，检查时间："+jsonObj.checkDate+"</span>");
			setTimeout("checkM1Write()", 2000);
		}
	});
}

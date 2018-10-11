
//保护区信息读指令发出
$('#tab-message-2_read').on('click', function() {
	var sid = $("#sid").val();
	$.post("api/param/config/send/readM2", {
		sid : sid,
	}, function(data) {
		if(data=='success'){
			//读命令发送成功，发送时实现插队机制
			$("#tab-message-2_tip").html("<span style='color:green;'>【保护区信息】读命令发送成功，等待数据回传</span>");
			setTimeout("checkM2Read()", 1000);
		}else{
			$("#tab-message-2_tip").html("<span style='color:red;'>【保护区信息】读命令发送时，网络或程序异常！</span>");
		}
	});
});

//保护区信息读指令检查
function checkM2Read(){
	var sid = $("#sid").val();
	$.post("api/param/config/check/readM2", {
		sid : sid,
	}, function(data) {
		var jsonObj = eval('(' + data + ')');
		//读命令发送成功，发送时实现插队机制
		if(jsonObj.result=='success'){
			$("#tab-message-2_tip").html("【保护区信息】数据回传成功，可以开始写入");
			var reservedAreaCount =jsonObj.reservedAreaCount;
			setReservedArea(reservedAreaCount); //设置保护区个数
			$("#areaCount").val(reservedAreaCount); // 设置select选中值
			$("#tab-message-2 input[name='messageVersion']").val(jsonObj.version); //设置信息版本 
			//console.log(data);
			//console.log(jsonObj.list[0].version);
			//console.log(jsonObj.list.length);
			for(var ra in jsonObj.list){
				var num = jsonObj.list[ra].reservedAreaNumber-1; //保护区序号-1
				$("#tab-message-2 input[name='reservedAreas["+num+"].reservedAreaNumber']").val(jsonObj.list[ra].reservedAreaNumber);  
				$("#tab-message-2 input[name='reservedAreas["+num+"].reservedAreaType']").val(jsonObj.list[ra].reservedAreaType);  
				$("#tab-message-2 input[name='reservedAreas["+num+"].reservedAreaUnityCount']").val(jsonObj.list[ra].reservedAreaUnityCount);  
				$("#tab-message-2 input[name='reservedAreas["+num+"].reservedAreaID']").val(jsonObj.list[ra].reservedAreaID);  
				$("#tab-message-2 input[name='reservedAreas["+num+"].reservedAreaConstrutionType']").val(jsonObj.list[ra].reservedAreaConstrutionType);  
				$("#tab-message-2 input[name='reservedAreas["+num+"].reservedAreaHeight']").val(jsonObj.list[ra].reservedAreaHeight);  
				$("#tab-message-2 input[name='reservedAreas["+num+"].reservedAreaName']").val(jsonObj.list[ra].reservedAreaName);  
				var elementCount = jsonObj.list[ra].reservedAreaUnityCount;
				$("#tab-message-2 select[name='reservedAreas["+num+"].reservedAreaUnityCount']").val(elementCount); // 设置元素个数
				setElement(elementCount, "table-ra-"+num);
				
				/**设置每个元素信息*/
				$("#tab-message-2 select[name='reservedAreas["+num+"].elementType1']").val(jsonObj.list[ra].elementType1);  //设置元素类型
				//$("#tab-message-2 select[name='reservedAreas["+num+"].elementType1']").val(jsonObj.list[ra].elementType1); //设置元素类型
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointX1']").val(jsonObj.list[ra].pointX1); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointY1']").val(jsonObj.list[ra].pointY1); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleR1']").val(jsonObj.list[ra].circleR1); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleStartAngle1']").val(jsonObj.list[ra].circleStartAngle1); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleEndAngle1']").val(jsonObj.list[ra].circleEndAngle1); 
				
				$("#tab-message-2 select[name='reservedAreas["+num+"].elementType2']").val(jsonObj.list[ra].elementType2); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointX2']").val(jsonObj.list[ra].pointX2); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointY2']").val(jsonObj.list[ra].pointY2); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleR2']").val(jsonObj.list[ra].circleR2); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleStartAngle2']").val(jsonObj.list[ra].circleStartAngle2); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleEndAngle2']").val(jsonObj.list[ra].circleEndAngle2); 
				
				
				$("#tab-message-2 select[name='reservedAreas["+num+"].elementType3']").val(jsonObj.list[ra].elementType3); //设置元素类型
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointX3']").val(jsonObj.list[ra].pointX3); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointY3']").val(jsonObj.list[ra].pointY3); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleR3']").val(jsonObj.list[ra].circleR3); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleStartAngle3']").val(jsonObj.list[ra].circleStartAngle3); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleEndAngle3']").val(jsonObj.list[ra].circleEndAngle3); 
				
				$("#tab-message-2 select[name='reservedAreas["+num+"].elementType4']").val(jsonObj.list[ra].elementType4); //设置元素类型
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointX4']").val(jsonObj.list[ra].pointX4); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointY4']").val(jsonObj.list[ra].pointY4); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleR4']").val(jsonObj.list[ra].circleR4); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleStartAngle4']").val(jsonObj.list[ra].circleStartAngle4); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleEndAngle4']").val(jsonObj.list[ra].circleEndAngle4); 
				
				$("#tab-message-2 select[name='elementType5']").val(jsonObj.list[ra].elementType5); //设置元素类型
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointX5']").val(jsonObj.list[ra].pointX5); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointY5']").val(jsonObj.list[ra].pointY5); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleR5']").val(jsonObj.list[ra].circleR5); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleStartAngle5']").val(jsonObj.list[ra].circleStartAngle5); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleEndAngle5']").val(jsonObj.list[ra].circleEndAngle5); 
				
				$("#tab-message-2 select[name='elementType6']").val(jsonObj.list[ra].elementType6); //设置元素类型
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointX6']").val(jsonObj.list[ra].pointX6); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointY6']").val(jsonObj.list[ra].pointY6); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleR6']").val(jsonObj.list[ra].circleR1); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleStartAngle6']").val(jsonObj.list[ra].circleStartAngle6); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleEndAngle6']").val(jsonObj.list[ra].circleEndAngle6); 
				
				$("#tab-message-2 select[name='reservedAreas["+num+"].elementType7']").val(jsonObj.list[ra].elementType7); //设置元素类型
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointX7']").val(jsonObj.list[ra].pointX7); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointY7']").val(jsonObj.list[ra].pointY7); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleR7']").val(jsonObj.list[ra].circleR7); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleStartAngle7']").val(jsonObj.list[ra].circleStartAngle7); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleEndAngle7']").val(jsonObj.list[ra].circleEndAngle7); 
				
				$("#tab-message-2 select[name='reservedAreas["+num+"].elementType8']").val(jsonObj.list[ra].elementType8); //设置元素类型
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointX8']").val(jsonObj.list[ra].pointX8); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointY8']").val(jsonObj.list[ra].pointY8); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleR8']").val(jsonObj.list[ra].circleR1); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleStartAngle8']").val(jsonObj.list[ra].circleStartAngle8); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleEndAngle8']").val(jsonObj.list[ra].circleEndAngle8); 
				
				$("#tab-message-2 select[name='reservedAreas["+num+"].elementType9']").val(jsonObj.list[ra].elementType9); //设置元素类型
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointX9']").val(jsonObj.list[ra].pointX9); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointY9']").val(jsonObj.list[ra].pointY9); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleR9']").val(jsonObj.list[ra].circleR1); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleStartAngle9']").val(jsonObj.list[ra].circleStartAngle9); 
				$("#tab-message-2 input[name='reservedAreas["+num+"]circleEndAngle9']").val(jsonObj.list[ra].circleEndAngle9); 
				
				$("#tab-message-2 select[name='reservedAreas["+num+"].elementType10']").val(jsonObj.list[ra].elementType10); //设置元素类型
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointX10']").val(jsonObj.list[ra].pointX10); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].pointY10']").val(jsonObj.list[ra].pointY10); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleR10']").val(jsonObj.list[ra].circleR10); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleStartAngle10']").val(jsonObj.list[ra].circleStartAngle10); 
				$("#tab-message-2 input[name='reservedAreas["+num+"].circleEndAngle10']").val(jsonObj.list[ra].circleEndAngle10); 
			}
			
		}else{
			$("#tab-message-2_tip").html("<span style='color:green;'>等待【保护区信息】数据回传，检查时间："+jsonObj.checkDate+"</span>");
			setTimeout("checkM2Read()", 2000);
		}
	});
}

//保护区信息写指令发出
$('#tab-message-2_write').on('click', function() {
	var sid = $("#sid").val();
	var formData = $('#tab-message-form2').serialize()+"&sid="+sid;
	$.ajax({
        type: "POST",
        dataType: "html",
        url:"api/param/config/send/writeM2",
        data:formData,
        success: function (data) {
    		if(data=='success'){
    			//写命令发送成功，发送时实现插队机制
    			$("#tab-message-2_tip").html("<span style='color:green;'>【保护区信息】写命令发送成功，等待终端回应</span>");
    			setTimeout("checkM2Write()", 1000);
    		}else if(data=="NotWrite"){
    			$("#tab-message-2_tip").html("<span style='color:red;'>【保护区信息】写入前，请先读取数据</span>");
    		}else{
    			$("#tab-message-2_tip").html("<span style='color:red;'>【保护区信息】写命令发送时，网络或程序异常！</span>");
    		}
        },
        error: function(data) {
        	$("#tab-message-2_tip").html("<span style='color:red;'>【保护区信息】写入前，请先读取数据</span>");
        }
    });
	
});

//保护区信息写指令发出
function checkM2Write(){
	var sid = $("#sid").val();
	$.post("api/param/config/check/writeM2", {
		sid : sid,
	}, function(data) {
		var jsonObj = eval('(' + data + ')');
		if(jsonObj.result=='success'){
			console.log(data);
			$("#tab-message-2_tip").html("<span style='color:green;'>【保护区信息】数据写入成功，检查时间："+jsonObj.checkDate+"</span>");
		}else{
			$("#tab-message-2_tip").html("<span style='color:green;'>等待终端回应【保护区信息】写入结果，检查时间："+jsonObj.checkDate+"</span>");
			setTimeout("checkM2Write()", 2000);
		}
	});
}

function setReservedArea(val){
	for(var i=0;i<val;i++){
		$("#ra-"+i).css("display","");
		$("#ra-"+i).removeClass("ra");
	}
	for(var i=5;i>=val;i--){
		$("#ra-"+i).css("display","none");
		//console.log("#ra-"+i);
	}
}

function setElement(val,tableId){
	var tab = document.getElementById(tableId);  
	var rnum=tab.rows.length-1;   //减去标题后的总行数
	while(rnum<val){
		addRow(tableId);
		rnum=tab.rows.length-1;   //减去标题后的总行数
	}
	while(rnum>val){
		deleteRow(tableId);
		rnum=tab.rows.length-1;   //减去标题后的总行数
	}
}

function addRow(tableId){
	var tab = document.getElementById(tableId);
	var tid = tableId.substring(tableId.lastIndexOf('-')+1);
	var row = tab.insertRow(-1);
	var rnum=tab.rows.length-1;   //总行数-1
	var cell1 = row.insertCell(-1);
	var cell2 = row.insertCell(-1);
	var cell3 = row.insertCell(-1);
	var cell4 = row.insertCell(-1);
	var cell5 = row.insertCell(-1);
	var cell6 = row.insertCell(-1);
	cell1.innerHTML = "<select class='form-control' name='reservedAreas["+tid+"].elementType"+rnum+"'><option value='0'>点</option><option value='1'>圆</option></select>";
	cell2.innerHTML = "<input type='text' name='reservedAreas["+tid+"].pointX"+rnum+"' class='form-control' style='width: 80px;'>" ;
	cell3.innerHTML = "<input type='text' name='reservedAreas["+tid+"].pointY"+rnum+"' class='form-control' style='width: 80px;'>" ;
	cell4.innerHTML = "<input type='text' name='reservedAreas["+tid+"].circleR"+rnum+"' class='form-control' style='width: 80px;'>" ;
	cell5.innerHTML = "<input type='text' name='reservedAreas["+tid+"].circleStartAngle"+rnum+"' class='form-control' style='width: 80px;'>" ;
	cell6.innerHTML = "<input type='text' name='reservedAreas["+tid+"].circleEndAngle"+rnum+"' class='form-control' style='width: 80px;'>" ;
}

function deleteRow(tableId){
	var tab = document.getElementById(tableId);  
	var rnum=tab.rows.length;   //总行数
	console.log(rnum);
	if(rnum>4){//标题行
		tab.deleteRow(rnum-1);  
	}else{
		alert("元素总数不能小于3");
	}
}

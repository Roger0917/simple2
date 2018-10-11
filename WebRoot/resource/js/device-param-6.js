var checkM6=true;  //在强制写时，停止检查是否读成功 
//起重特性曲线信息读指令发出
$('#tab-message-6_read').on('click', function() {
	var sid = $("#sid").val();
	$.post("api/param/config/send/readM6", {
		sid : sid,
	}, function(data) {
		console.log(data);
		if(data=='success'){
			//读命令发送成功，发送时实现插队机制
			$("#tab-message-6_tip").html("<span style='color:green;'>【起重特性曲线信息】读命令发送成功，等待数据回传</span>");
			setTimeout("checkM6Read()", 1000);
		}else{
			$("#tab-message-6_tip").html("<span style='color:red;'>【起重特性曲线信息】指令发送时，网络或程序异常！</span>");
		}
	});
});

//起重特性曲线信息读指令检查
function checkM6Read(){
	var sid = $("#sid").val();
	console.log(sid);
	$.post("api/param/config/check/readM6", {
		sid : sid,
	}, function(data) {
		console.log(data);
		var jsonObj = eval('(' + data + ')');
		//读命令发送成功，发送时实现插队机制
		if(jsonObj.result=='success'){
			console.log("success:"+jsonObj);
			$("#tab-message-6_tip").html("【起重特性曲线信息】数据回传成功，可以开始写入");
			var samples =jsonObj.samples; //起重特性曲线塔机数目
			loadSamples(samples);
			$("#tab-message-6 input[name='version']").val(jsonObj.version); //设置信息版本 
			$("#tab-message-6 input[name='towerModel']").val(jsonObj.towerModel); //设置塔机型号
			$("#tab-message-6 input[name='type']").val(jsonObj.type); //设置采样点数
			$("#tab-message-6 input[name='rate']").val(jsonObj.rate); //设置采样点数
			$("#tab-message-6 input[name='samples']").val(jsonObj.samples); //设置采样点数
			//console.log(data);
			//console.log(jsonObj.list[0].version);
			//console.log(jsonObj.list.length);
			var i = 0 ;
			for(var cc in jsonObj.list){
				$("#tab-message-6 input[name='curves["+i+"].amplitude']").val(jsonObj.list[cc].amplitude);  
				$("#tab-message-6 input[name='curves["+i+"].weight']").val(jsonObj.list[cc].weight);  
				i++;
			}
		}else{
			console.log("wait:"+jsonObj);
			$("#tab-message-6_tip").html("<span style='color:green;'>等待【起重特性曲线信息】数据回传，检查时间："+jsonObj.checkDate+"</span>"
					+"<a href='javascript:sendReadM6OK()'>强制写入</a>");
			if(checkM6){
				setTimeout("checkM6Read()", 2000);
			}else{
				$("#tab-message-6_tip").html("<span style='color:red;'>采取强制写方式</span>");
			}
		}
	});
}

// 发送强制写命令
function sendReadM6OK(){
	var sid = $("#sid").val();
	console.log(sid);
	$.post("api/param/config/send/readM6OK", {
		sid : sid,
	}, function(data) {
		console.log(data);
		if(data=='success'){
			checkM6=false;
		}
	});
}

//起重特性曲线信息写指令发出
$('#tab-message-6_write').on('click', function() {
	checkM6=true;//设置发送读命令后要重新检测是否读取成功
	var sid = $("#sid").val();
	var formData = $('#tab-message-form6').serialize()+"&sid="+sid;
	$.ajax({
        type: "POST",
        dataType: "html",
        url:"api/param/config/send/writeM6",
        data:formData,
        success: function (data) {
    		if(data=='success'){
    			//写命令发送成功，发送时实现插队机制
    			$("#tab-message-6_tip").html("<span style='color:green;'>写命令发送成功，等待终端回应</span>");
    			setTimeout("checkM6Write()", 1000);
    		}else if(data=="NotWrite"){
    			$("#tab-message-6_tip").html("<span style='color:red;'>【起重特性曲线信息】写入前，请先读取数据</span>");
    		}else{
    			$("#tab-message-6_tip").html("<span style='color:red;'>【起重特性曲线信息】写入时，网络或程序异常！</span>");
    		}
        },
        error: function(data) {
        	$("#tab-message-6_tip").html("<span style='color:red;'>【起重特性曲线信息】写入时，服务端异常</span>");
        }
    });
	
});

//起重特性曲线信息写指令检查
function checkM6Write(){
	var sid = $("#sid").val();
	$.post("api/param/config/check/writeM6", {
		sid : sid,
	}, function(data) {
		var jsonObj = eval('(' + data + ')');
		if(jsonObj.result=='success'){
			console.log(data);
			$("#tab-message-6_tip").html("<span style='color:green;'>【起重特性曲线信息】数据写入成功，检查时间："+jsonObj.checkDate+"</span>");
		}else{
			$("#tab-message-6_tip").html("<span style='color:green;'>等待终端回应【起重特性曲线信息】写入结果，检查时间："+jsonObj.checkDate+"</span>");
			setTimeout("checkM6Write()", 2000);
		}
	});
}

/*-----------------------------------*/
function loadSamples(num){
	//$("#ccs").empty();
	if(num>100){
		return ;
	}
	var has = $('#ccs').children('div').length;//实际获取<dcc class="cc">元素的个数
	console.log(num+"~~"+has);
	if(has>num){//删除多余的<dcc class="cc">
		for(var i=0;i<has-num;i++){
			var id= parseInt(num) + i;
			console.log("id="+id);
			$("#cc-"+id).remove();
		}
	}
	var has = $('#ccs').children('div').length;//再次获取<dcc class="cc">元素的个数
	console.log(num+"~~"+has);
	for(var i=has;i<num; i++){
		var ccDiv=$('<div ></div>');      
		ccDiv.attr('id','cc-'+i);       
		ccDiv.addClass('cc');    
		
		var mytitle=$('<h4>采样点'+(i+1)+'</h4>');      
		mytitle.appendTo(ccDiv);
		
		//第一行
		var row1Div=$('<div></div>');      
		row1Div.addClass('row');    
		row1Div.appendTo(ccDiv);
		
		var xcbfLabel=$('<label class="col-sm-2 control-label">小车变幅</label>');      
		xcbfLabel.appendTo(row1Div);
		var xcbfDiv=$('<div class="col-sm-2"><input type="text" name="curves['+i +'].amplitude" class="form-control"  placeholder=""></div>');      
		xcbfDiv.appendTo(row1Div);
		var qzlLabel=$('<label class="col-sm-2 control-label">起重量</label>');      
		qzlLabel.appendTo(row1Div);
		var qzlDiv=$('<div class="col-sm-2"><input type="text" name="curves['+i +'].weight" class="form-control"  placeholder=""></div>');      
		qzlDiv.appendTo(row1Div);
		
		$("#ccs").append(ccDiv); 
	}
}



/*
<div id="cc-1" class="cc">
<div class="row">
    <label class="col-sm-2 control-label">塔机ID</label>
    <div class="col-sm-2">
      <input type="text" name="towerId" class="form-control"  placeholder="">
    </div>
    <label class="col-sm-2 control-label">X坐标</label>
    <div class="col-sm-2">
      <input type="text" name="x" onblur="setTowerTabel" class="form-control"  placeholder="">
    </div>
    <label class="col-sm-2 control-label">Y坐标</label>
    <div class="col-sm-2">
      <input type="text" name="y" class="form-control"  placeholder="">
    </div>
</div>
<div class="row">
    <label class="col-sm-2 control-label">前臂长度</label>
    <div class="col-sm-2">
      <input type="text" name="forearm" class="form-control"  placeholder="">
    </div>
    <label class="col-sm-2 control-label">后臂长度</label>
    <div class="col-sm-2">
      <input type="text" name="backarm" onblur="setTowerTabel" class="form-control"  placeholder="">
    </div>
</div>
<div class="row">
    <label class="col-sm-2 control-label">塔臂高度</label>
    <div class="col-sm-2">
      <input type="text" name="armHeight" class="form-control"  placeholder="">
    </div>
    <label class="col-sm-2 control-label">塔帽高度</label>
    <div class="col-sm-2">
      <input type="text" name="cap" onblur="setTowerTabel" class="form-control"  placeholder="">
    </div>
</div>
</div><!-- class="cc" -->

*/
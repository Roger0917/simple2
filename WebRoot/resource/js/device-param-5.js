var checkM5=true;  //在强制写时，停止检查是否读成功 
//相干涉信息读指令发出
$('#tab-message-5_read').on('click', function() {
	var sid = $("#sid").val();
	$.post("api/param/config/send/readM5", {
		sid : sid,
	}, function(data) {
		console.log(data);
		if(data=='success'){
			//读命令发送成功，发送时实现插队机制
			$("#tab-message-5_tip").html("<span style='color:green;'>【相干涉信息】读命令发送成功，等待数据回传</span>");
			setTimeout("checkM5Read()", 1000);
		}else{
			$("#tab-message-5_tip").html("<span style='color:red;'>【相干涉信息】指令发送时，网络或程序异常！</span>");
		}
	});
});

//相干涉信息读指令检查
function checkM5Read(){
	var sid = $("#sid").val();
	console.log(sid);
	$.post("api/param/config/check/readM5", {
		sid : sid,
	}, function(data) {
		console.log(data);
		var jsonObj = eval('(' + data + ')');
		//读命令发送成功，发送时实现插队机制
		if(jsonObj.result=='success'){
			console.log("success:"+jsonObj);
			$("#tab-message-5_tip").html("【相干涉信息】数据回传成功，可以开始写入");
			var towerNumber =jsonObj.towerNumber; //相干涉塔机数目
			loadInteraction(towerNumber);
			$("#tab-message-5 input[name='version']").val(jsonObj.version); //设置信息版本 
			$("#tab-message-5 input[name='towerNumber']").val(jsonObj.towerNumber); //设置相干涉塔机数目
			$("#tab-message-5 input[name='towerGroupId']").val(jsonObj.towerGroupId); //设置塔群ID
			//console.log(data);
			//console.log(jsonObj.list[0].version);
			//console.log(jsonObj.list.length);
			var i = 0 ;
			for(var ir in jsonObj.list){
				$("#tab-message-5 input[name='interactions["+i+"].towerId']").val(jsonObj.list[ir].towerId);  
				$("#tab-message-5 input[name='interactions["+i+"].x']").val(jsonObj.list[ir].x);  
				$("#tab-message-5 input[name='interactions["+i+"].y']").val(jsonObj.list[ir].y);  
				$("#tab-message-5 input[name='interactions["+i+"].forearm']").val(jsonObj.list[ir].forearm);  
				$("#tab-message-5 input[name='interactions["+i+"].backarm']").val(jsonObj.list[ir].backarm);  
				$("#tab-message-5 input[name='interactions["+i+"].armHeight']").val(jsonObj.list[ir].armHeight);  
				$("#tab-message-5 input[name='interactions["+i+"].cap']").val(jsonObj.list[ir].cap);  
				i++;
			}
		}else{
			console.log("wait:"+jsonObj);
			$("#tab-message-5_tip").html("<span style='color:green;'>等待【相干涉信息】数据回传，检查时间："+jsonObj.checkDate+"</span>"
					+"<a href='javascript:sendReadM5OK()'>强制写入</a>");
			if(checkM5){
				setTimeout("checkM5Read()", 2000);
			}else{
				$("#tab-message-5_tip").html("<span style='color:red;'>采取强制写方式</span>");
			}
		}
	});
}

// 发送强制写命令
function sendReadM5OK(){
	var sid = $("#sid").val();
	console.log(sid);
	$.post("api/param/config/send/readM5OK", {
		sid : sid,
	}, function(data) {
		console.log(data);
		if(data=='success'){
			checkM5=false;
		}
	});
}

//相干涉信息写指令发出
$('#tab-message-5_write').on('click', function() {
	checkM5=true;//设置发送读命令后要重新检测是否读取成功
	var sid = $("#sid").val();
	var formData = $('#tab-message-form5').serialize()+"&sid="+sid;
	$.ajax({
        type: "POST",
        dataType: "html",
        url:"api/param/config/send/writeM5",
        data:formData,
        success: function (data) {
    		if(data=='success'){
    			//写命令发送成功，发送时实现插队机制
    			$("#tab-message-5_tip").html("<span style='color:green;'>写命令发送成功，等待终端回应</span>");
    			setTimeout("checkM5Write()", 1000);
    		}else if(data=="NotWrite"){
    			$("#tab-message-5_tip").html("<span style='color:red;'>【相干涉信息】写入前，请先读取数据</span>");
    		}else{
    			$("#tab-message-5_tip").html("<span style='color:red;'>【相干涉信息】写入时，网络或程序异常！</span>");
    		}
        },
        error: function(data) {
        	$("#tab-message-5_tip").html("<span style='color:red;'>【相干涉信息】写入时，服务端异常</span>");
        }
    });
	
});

//相干涉信息写指令检查
function checkM5Write(){
	var sid = $("#sid").val();
	$.post("api/param/config/check/writeM5", {
		sid : sid,
	}, function(data) {
		var jsonObj = eval('(' + data + ')');
		if(jsonObj.result=='success'){
			console.log(data);
			$("#tab-message-5_tip").html("<span style='color:green;'>【相干涉信息】数据写入成功，检查时间："+jsonObj.checkDate+"</span>");
		}else{
			$("#tab-message-5_tip").html("<span style='color:green;'>等待终端回应【相干涉信息】写入结果，检查时间："+jsonObj.checkDate+"</span>");
			setTimeout("checkM5Write()", 2000);
		}
	});
}

/*-----------------------------------*/
function loadInteraction(num){
	//$("#irs").empty();
	if(num>100){
		return ;
	}
	var has = $('#irs').children('div').length;//实际获取<dir class="ir">元素的个数
	console.log(num+"~~"+has);
	if(has>num){//删除多余的<dir class="ir">
		for(var i=0;i<has-num;i++){
			var id= parseInt(num) + i;
			console.log("id="+id);
			$("#ir-"+id).remove();
		}
	}
	var has = $('#irs').children('div').length;//再次获取<dir class="ir">元素的个数
	console.log(num+"~~"+has);
	for(var i=has;i<num; i++){
		var irDiv=$('<div ></div>');      
		irDiv.attr('id','ir-'+i);       
		irDiv.addClass('ir');    
		
		var mytitle=$('<h4>相干涉塔机'+(i+1)+'</h4>');      
		mytitle.appendTo(irDiv);
		
		//第一行
		var row1Div=$('<div></div>');      
		row1Div.addClass('row');    
		row1Div.appendTo(irDiv);
		
		var tjIdLabel=$('<label class="col-sm-2 control-label">塔机ID</label>');      
		tjIdLabel.appendTo(row1Div);
		var tjIdDiv=$('<div class="col-sm-2"><input type="text" name="interactions['+i +'].towerId" class="form-control"  placeholder=""></div>');      
		tjIdDiv.appendTo(row1Div);
		var xLabel=$('<label class="col-sm-2 control-label">X坐标</label>');      
		xLabel.appendTo(row1Div);
		var xDiv=$('<div class="col-sm-2"><input type="text" name="interactions['+i +'].x" class="form-control"  placeholder=""></div>');      
		xDiv.appendTo(row1Div);
		var yLabel=$('<label class="col-sm-2 control-label">Y坐标</label>');      
		yLabel.appendTo(row1Div);
		var yDiv=$('<div class="col-sm-2"><input type="text" name="interactions['+i +'].y" class="form-control"  placeholder=""></div>');      
		yDiv.appendTo(row1Div);
		
		//第二行
		var row2Div=$('<div></div>');      
		row2Div.addClass('row');    
		row2Div.appendTo(irDiv);
		
		var qbLabel=$('<label class="col-sm-2 control-label">前臂长度</label>');      
		qbLabel.appendTo(row2Div);
		var qbDiv=$('<div class="col-sm-2"><input type="text" name="interactions['+i +'].forearm" class="form-control"  placeholder=""></div>');      
		qbDiv.appendTo(row2Div);
		var hbLabel=$('<label class="col-sm-2 control-label">后臂长度</label>');      
		hbLabel.appendTo(row2Div);
		var hbDiv=$('<div class="col-sm-2"><input type="text" name="interactions['+i +'].backarm" class="form-control"  placeholder=""></div>');      
		hbDiv.appendTo(row2Div);
		
		//第三行
		var row3Div=$('<div></div>');      
		row3Div.addClass('row');    
		row3Div.appendTo(irDiv);
		
		var tbLabel=$('<label class="col-sm-2 control-label">塔臂高度</label>');      
		tbLabel.appendTo(row3Div);
		var tbDiv=$('<div class="col-sm-2"><input type="text" name="interactions['+i +'].armHeight" class="form-control"  placeholder=""></div>');      
		tbDiv.appendTo(row3Div);
		var tmLabel=$('<label class="col-sm-2 control-label">塔帽高度</label>');      
		tmLabel.appendTo(row3Div);
		var tmDiv=$('<div class="col-sm-2"><input type="text" name="interactions['+i +'].cap" class="form-control"  placeholder=""></div>');      
		tmDiv.appendTo(row3Div);
		
		$("#irs").append(irDiv); 
	}
}



/*
<div id="ir-1" class="ir">
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
</div><!-- class="ir" -->

*/
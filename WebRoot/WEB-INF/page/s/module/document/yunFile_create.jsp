<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="padding-top: 0">
<head>

<%@ include file="/WEB-INF/page/s/common/pack-css.jsp" %>
<style type="text/css">
 	.progress { position:relative; width:100%; border: 1px solid #ddd; padding: 1px; border-radius: 3px; }
    .bar { background-color: #B4F5B4; width:0%; height:20px; border-radius: 3px; }
    .percent { position:absolute; display:inline-block; top:3px; left:48%; }
</style>
</head>

<body class="bg-1">
	<div id="content" style="margin-top: 30px;padding-right: 50px;">
	<section class="">

        <!-- tile body -->
        <div class="tile-body col-md-8">
          
          <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/s/yunfile/save" id="pageForm" method="post" enctype="multipart/form-data">
          	<input type="hidden" name="uk" value="${uk}" id="uk"/>
          	<input type="hidden" name="fk" value="${fk}" id="fk"/>
            <div class="form-group">
              <label for="input01" class="col-sm-3 control-label">所属分类</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" value="${category.name}" readonly="readonly" >
              </div>
            </div>
            <div class="form-group">
              <label for="input01" class="col-sm-3 control-label">上级目录</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" value='${parentFile==null?"根目录":parentFile.name}' readonly="readonly" >
              </div>
            </div>
            <div class="form-group">
              <label for="input01" class="col-sm-3 control-label">文件编号</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="number" id="number">
                <span class="errorStyle" id="error-number"></span>
              </div>
            </div>
            <div class="form-group">
              <label for="input01" class="col-sm-3 control-label">文件</label>
              <div class="col-sm-7">
                <input type="file" class="form-control" name="yunFile" id="file">
                <span class="errorStyle" id="error-file"></span>
              </div>
            </div>
            <div class="form-group">
              <label for="input01" class="col-sm-3 control-label">上传进度</label>
              <div class="col-sm-7">
                <div class="progress">
				    <div class="bar"></div >
				    <div class="percent">0%</div >
				</div>
	            <div id="status" >
	            </div>
              </div>
            </div>
            <div class="">
              <div class="col-sm-12 text-center">
              	<div id="opselect" style="display: none;">
              		<a href="javascript:reupload()" class="btn btn-primary">继续上传</a>
	            	<a href="javascript:closeLayer()" class="btn btn-primary">关闭</a>
              	</div>
              	<div id="save">
	                <button type="submit" class="btn btn-primary" >保存数据</button>
	                <button type="button" class="btn btn-primary hidden" onclick="javascript:submitForm(1)">只保存</button>
	                <button type="button" class="btn btn-danger hidden" onclick="javascript:submitForm(2)" >保存并关闭</button>
              	</div>
              </div>
            </div>

          </form>

        </div>
        <!-- /tile body -->
       </section>
	
	</div>
	
	<%@ include file="/WEB-INF/page/s/common/pack-js.jsp" %>
	<script type="text/javascript">
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		
		$(function(){
	        var bar = $('.bar');
	        var percent = $('.percent');
	        var status = $('#status');
	        $('form').ajaxForm({
	            beforeSerialize:function(){
	                //alert("表单数据序列化前执行的操作！");
	                //$("#txt2").val("java");//如：改变元素的值
	            },
	            beforeSubmit:function(){//"表单提交前的操作"
	                var number = $("#number").val();
	                if(number==''){
	            		$("#error-number").text("文件编号不能为空");
	                    return false;
	                }else{
	            		$("#error-number").text("");
	                }
	                var filepath = $("#file").val();
	                if(filepath==''){
	            		$("#error-file").text("请选择要上传的文件");
	                    return false;
	                }else{
	            		$("#error-file").text("");
	                }
	                var filesize = $("input[type='file']")[0].files[0].size/1024/1024;
	                if(filesize > 1024){
	                    $("#error-file").text("文件大小超过限制，最多1024M");
	                    return false;
	                }else{
	                    $("#error-file").text("");
	                }
	                //if($("#txt1").val()==""){return false;}//如：验证表单数据是否为空
	            },
	            beforeSend: function() {
	                status.empty();
	                var percentVal = '0%';
	                bar.width(percentVal)
	                percent.html(percentVal);
	            },
	            uploadProgress: function(event, position, total, percentComplete) {//上传的过程
	                //position 已上传了多少
	                //total 总大小
	                //已上传的百分数
	                var percentVal = percentComplete + '%';
	                bar.width(percentVal)
	                percent.html(percentVal);
	                //console.log(percentVal, position, total);
	            },
	            success: function(data) {//成功
                	status.html("<div class='notification notification-success'><h4>上传成功</h4></div>");
	            	$("#opselect").css("display","block");
	            	$("#save").css("display","none");
	            },
	            error:function(err){//失败
	                alert("表单提交异常！"+err.msg);
	            },
	            complete: function(xhr) {//完成
	            	if('success'==xhr.responseText){
	            		var percentVal = '100%';
	 	                bar.width(percentVal)
	 	                percent.html(percentVal);
	 	                //alert(data);
	            	}
	            }
	        });

	    });
		
		//重置表单，重新上传
		function reupload(){
			$('form').resetForm();
	    	$("#opselect").css("display","none");
        	$("#save").css("display","block");
        	$('.bar').width("0%");//清理进度条
        	$('.percent').html("");//清理进度条
        	$('#status').html("");//清理提示框
		}
		
		//关闭Layer
		function closeLayer(){
			var form =  parent.$('#pageList');
			form.submit();
			parent.layer.close(index);//要先执行刷新表单，再执行这里的关闭Layer
		}
		
		function submitForm_simple(mode) {
			var action = $("#pageForm").attr("action");
			var filename = $("#file").val();
			var uk = $("#uk").val();
			var fk = $("#fk").val();
			var number = $("#number").val();
			var options = {
	                url: action,
	                enctype: 'multipart/form-data',
	                type: 'post',
	                dataType: 'text',
	                cache: false,
	                processData: false,
	                contentType: false,
	                data: new FormData($('#pageForm')[0]),
	                success: function (data) {
	                    parent.layer.close(index);
	                }
	         };
	         $.ajax(options);
		}
	</script>
</body>
</html>

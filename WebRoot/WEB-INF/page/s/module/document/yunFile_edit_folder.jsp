<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="padding-top: 0">
<head>

<%@ include file="/WEB-INF/page/s/common/pack-css.jsp" %>

</head>

<body class="bg-1">
	<div id="content" style="margin-top: 30px;padding-right: 50px;">
	<section class="">

        <!-- tile body -->
        <div class="tile-body col-md-8">
          
          <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/s/yunfile/update/folder" id="pageForm">
          	<input type="hidden" name="uk" value="${yunFile.uk}"  />
            <div class="form-group">
              <label for="input01" class="col-sm-3 control-label">所属分类</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" value="${yunFile.category.name}" readonly="readonly" >
              </div>
            </div>
            <div class="form-group">
              <label for="input01" class="col-sm-3 control-label">上级目录</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" value='${yunFile.parentFile==null?"根目录":yunFile.parentFile.name}' readonly="readonly" >
              </div>
            </div>
            <div class="form-group">
              <label for="input01" class="col-sm-3 control-label">文件夹名称</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="name" value="${yunFile.name}">
                <span class="error"></span>
              </div>
            </div>
            <div class="">
              <div class="col-sm-12 text-center">
                <button type="submit" class="btn btn-primary validate" >保存并关闭</button>
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
		/*页面校验*/
		$(function() {
			var validator = $("#pageForm").validate( {
				debug : true,
				ignore: "",
				errorClass : "errorStyle",
				submitHandler : function(form) {
					submitForm();
				},
				rules : {
					"name" : {
						required : true
					}
				},
				messages : {
					"name" : {
						required : "文件夹名称不能为空"
					}
				},
				errorPlacement : function(error, element) {
					error.appendTo(element.next("span.error"));
				},
				highlight : function(element, errorClass) {
					$(element).addClass(errorClass);
				}
			});
		});
		
		function submitForm() {
			var action = $("#pageForm").attr("action");
			var options = {
	                url: action,
	                type: 'post',
	                dataType: 'text',
	                data: $("#pageForm").serialize(),
	                success: function (data) {
	                	var form =  parent.$('#pageList');
	        			form.submit();
	        			parent.layer.close(index);//要先执行刷新表单，再执行这里的关闭Layer
	                }
	         };
	         $.ajax(options);
			
		}
	</script>
</body>
</html>

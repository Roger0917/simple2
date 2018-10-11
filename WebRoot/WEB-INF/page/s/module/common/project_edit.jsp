<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="padding-top: 0">
<head>
<%@ include file="/WEB-INF/page/s/common/pack-css.jsp" %>
<style type="text/css">
	.tt-menu{
		width: 200px;
	}
	.typeahead{
		width: 200px;
	}
	.errorStyle{
		color: red;
		font-size: 13px;
	}
</style>
</head>

<body class="bg-1">
	<div id="content" style="margin-top: 30px;padding-right: 50px;">
	<section class="">

        <!-- tile body -->
        <div class="tile-body col-md-8">
          
          <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/s/project/update" id="pageForm">
          	<input type="hidden" name="id" value="${project.id}">
            <div class="form-group">
              <label  class="col-sm-2 control-label">所属辖区</label>
              <div class="col-sm-4">
                <input type="text" id="parea" class="typeahead parea" autocomplete="off" placeholder="输入关键字进行模糊查询" value="${project.area.name}">
				<input type="hidden" name="areaId" id="areaId" value="${project.area.id}"/>
				<span class="error" id="error-aid"></span>
              </div>
              <label  class="col-sm-2 control-label">官方认证</label>
              <div class="col-sm-4">
              	<select class="form-control" name="auth">
              		<option value="true" ${project.auth?'selected="selected"':''} >已认证</option>
              		<option value="false" ${!project.auth?'selected="selected"':''} >未认证</option>
              	</select>
              </div>
            </div>
            
            <div class="form-group">
              <label  class="col-sm-2 control-label">工程名称</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="name" value="${project.name}">
              	<span class="error"></span>
              </div>
              <label  class="col-sm-2 control-label">详细地址</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="address" value="${project.address}">
              	<span class="error"></span>
              </div>
            </div>
            <div class="form-group">
              <label  class="col-sm-2 control-label">项目经理</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="manager" value="${project.manager}">
              	<span class="error"></span>
              </div>
              <label  class="col-sm-2 control-label">经理电话</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="managerPhone" value="${project.managerPhone}">
              	<span class="error"></span>
              </div>
            </div>
            <div class="form-group">
              <label  class="col-sm-2 control-label">开工时间</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="beginDate" value='<fmt:formatDate value="${project.startDate}" pattern="yyyy-MM-dd"/>'  >
              	<span class="error"></span>
              </div>
              <label  class="col-sm-2 control-label">完工时间</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="endDate"  value='<fmt:formatDate value="${project.completeDate}" pattern="yyyy-MM-dd"/>' >
              	<span class="error"></span>
              </div>
            </div>
            <div class="form-group">
              <label  class="col-sm-2 control-label">施工单位</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="buildUnit" value="${project.buildUnit}">
              	<span class="error"></span>
              </div>
              <label  class="col-sm-2 control-label">建设单位</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="constructUnit" value="${project.constructUnit}">
              	<span class="error"></span>
              </div>
            </div>
            <div class="form-group">
              <label  class="col-sm-2 control-label">终端帐号</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="username" value="${project.username}">
              	<span class="error"></span>
              </div>
              <label  class="col-sm-2 control-label">终端密码</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="password" value="${project.password}">
              	<span class="error"></span>
              </div>
            </div>
            <div class="form-group">
              <label  class="col-sm-2 control-label">经度</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="longitude" value="${project.longitude}">
              	<span class="error"></span>
              </div>
              <label  class="col-sm-2 control-label">纬度</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" name="latitude" value="${project.latitude}">
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
	<script src="${pageContext.request.contextPath}/resource/typeahead/handlebars.js"></script>
	<script src="${pageContext.request.contextPath}/resource/typeahead/typeahead.bundle.js"></script>
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
						required : "工程名称不能为空"
					}
				},
				errorPlacement : function(error, element) {
					error.appendTo(element.next("span.error"));
				},
				highlight : function(element, errorClass) {
					$(element).addClass(errorClass);
				}
			});
			/************地区模糊查询*********************/
			var ctx = parent.$("#ctx").val();//访问父页面元素值  
			var areaData = new Bloodhound({
				datumTokenizer : Bloodhound.tokenizers.obj.whitespace('label'),
				queryTokenizer : Bloodhound.tokenizers.whitespace,
				prefetch : {
					url : ctx+'/s/module/common/area/query/s',
					cache : false
				},
				remote : {
					url : ctx+'/s/module/common/area/query/ajax/%QUERY.json',
					wildcard : '%QUERY'
				}
			});

			$('.parea')
					.typeahead(
							{
								minLength : 2,// 字符长度>=4时才进行数据查询匹配
								hint : true, // 启用提示
							},
							{
								name : 'area-Data',
								display : 'label',
								source : areaData,
								limit : 20,
								templates : {
									empty : [ '<div class="empty-message">',
											'您的输入未能匹配到行政属地', '</div>' ].join('\n'),
									suggestion : Handlebars
											.compile('<div><strong>{{label}}</strong>({{parentName}})</div>')
								},
								classNames : {
									input : 'Typeahead-input',
									hint : 'Typeahead-hint',
									selectable : 'Typeahead-selectable'
								}
							});

			$('.parea').bind('typeahead:select', function(ev, suggestion) {
				$("#areaId").val(suggestion.id);
				// console.log('Selection: ' + suggestion.id);
				$("#error-aid").html("");//清除错误提示
			});
			/************地区模糊查询*********************/
			
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

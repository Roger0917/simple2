<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<!DOCTYPE html>
<html style="padding-top: 0">
  <head>
    <title></title>
	<%@ include file="/WEB-INF/page/s/common/pack-css.jsp" %>
  </head>
  <body class="bg-3" style="position: relative;">
    
    <!-- Wrap all page content here -->
    <div id="wrap">
    	<!-- Make page fluid -->
    	<div class="row">
    		<!-- Fixed navbar -->
        	<div class="navbar navbar-default navbar-fixed-top navbar-transparent-black mm-fixed-top hidden" role="navigation" id="navbar">
    			
    			<%@ include file="/WEB-INF/page/s/common/brand.jsp" %>
    			
    			<!-- .nav-collapse -->
            	<div class="navbar-collapse ">
            	
    				<%@ include file="/WEB-INF/page/s/common/top.jsp" %>
    				<%@ include file="/WEB-INF/page/s/common/sidebar.jsp" %>
            	
            	</div>
    			<!-- /.nav-collapse -->
    		
    		</div>
    		<!-- /Fixed navbar -->
    		
    		<!-- Page content -->
       		<div id="content" class="col-md-12">
       			<!-- page header -->
	  
	            <!-- /page header -->
	            
	            <!-- content main container -->
            	<div class="main">
            		<!-- fill real content ...................... -->
            		<div class="row">
            			<div class="col-md-12">
	            		<section class="tile ">
		                  <!-- tile header -->
		                  <div class="tile-header">
	                    	<h3>只保留最近<b>200</b>个历史版本</h3>
		                    <div class="controls">
		                    </div>
		                  </div>
		                  <!-- /tile header -->
		
		                  <!-- tile body -->
		                  <div class="tile-body nopadding">
		                    <table class="table table-bordered">
		                      <thead>
							    <tr>
					              <th style="width:5%">#</th>
					              <th style="width:40%">文件名</th>
					              <th style="width:10%">编号</th>
					              <th style="width:15%">大小</th>
					              <th style="width:30%">创建日期</th>
					            </tr>
		                      </thead>
		                      <tbody>
		                      	<c:forEach items="${pageView.records}" var="entry" varStatus="s"> 
			                        <tr id="tr_${entry.uk}">
			                          <td>${s.index+1}</td>
			                          <td>
				                         <a href="${pageContext.request.contextPath}/s/yunfile/history/download/${entry.uk}">
				                         	${entry.name}
				                         </a>
			                          </td>
			                          <td>${entry.number}</td>
			                          <td>${entry.showSize}</td>
			                          <td>
			                          	<fmt:formatDate value="${entry.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			                          </td>
			                        </tr>
		                      	</c:forEach>
		                      	<c:if test="${pageView.totalrecord<=0}" >
		                      	<tr>
		                      		<td colspan="5" class="text-center">
		                      		<div class='notification notification-danger'><h4>暂无历史版本</h4></div>
		                      		</td>
		                      	</tr>
		                      	</c:if>
		                      </tbody>
		                    </table>
		
		                  </div>
		                  <!-- /tile body -->
	
	                </section>
           			</div>
            		</div>
            		<!-- /fill real content ...................... -->
            	</div>
	            <!-- content main container -->
	            
       		</div>
    		<!--/ Page content -->
    		
    	
    	</div>
    	<!-- /Make page fluid -->
    </div>
    <!-- /Wrap all page content here -->
  
  <section class="videocontent" id="video"></section>
	<%@ include file="/WEB-INF/page/s/common/pack-js.jsp" %>
	
	<script type="text/javascript">
	function createFolder() {
		var basePath = $("#basePath").val();
		var uk = $("#uk").val();
		var fk = $("#fk").val();
		layer.open({
			type : 2,
			title : '新建文件夹',
			//skin : 'layui-layer-molv',// 皮肤
			shift : -1,// flash模糊问题
			scrollbar : false, // 屏蔽浏览器滚动条
			maxmin : true, // 开启最大化最小化按钮
			area : [ '800px', '500px' ],
			content : basePath + '/s/yunfile/create/folder?uk='+uk+"&fk="+fk, 
		});
	}
	function editFolder(uk) {
		var basePath = $("#basePath").val();
		layer.open({
			type : 2,
			title : '修改文件夹',
			//skin : 'layui-layer-molv',// 皮肤
			shift : -1,// flash模糊问题
			scrollbar : false, // 屏蔽浏览器滚动条
			maxmin : true, // 开启最大化最小化按钮
			area : [ '800px', '500px' ],
			content : basePath + '/s/yunfile/edit/folder?uk='+uk,
		});
	}
	function create() {
		var basePath = $("#basePath").val();
		var uk = $("#uk").val();
		var fk = $("#fk").val();
		layer.open({
			type : 2,
			title : '上传文件',
			//skin : 'layui-layer-molv',// 皮肤
			shift : -1,// flash模糊问题
			scrollbar : false, // 屏蔽浏览器滚动条
			maxmin : true, // 开启最大化最小化按钮
			area : [ '800px', '500px' ],
			cancel: function(index){ 
				  if(confirm('确定要关闭么')){
				    layer.close(index)
				    var form = document.getElementById("pageList");
					form.submit();
				  }
				  return false; 
			} ,
		    success: function(layero, index){
			    var body = layer.getChildFrame('body', index);
			    
			},
			content : basePath + '/s/yunfile/create?uk='+uk+"&fk="+fk, 
		});
	}
	function edit(uk) {
		var basePath = $("#basePath").val();
		layer.open({
			type : 2,
			title : '修改文件',
			//skin : 'layui-layer-molv',// 皮肤
			shift : -1,// flash模糊问题
			scrollbar : false, // 屏蔽浏览器滚动条
			maxmin : true, // 开启最大化最小化按钮
			area : [ '800px', '500px' ],
			content : basePath + '/s/yunfile/edit?uk='+uk, 
		});
	}
	
	function history(uk) {
		var basePath = $("#basePath").val();
		layer.open({
			type : 2,
			title : '历史版本',
			//skin : 'layui-layer-molv',// 皮肤
			shift : -1,// flash模糊问题
			scrollbar : false, // 屏蔽浏览器滚动条
			maxmin : true, // 开启最大化最小化按钮
			area : [ '800px', '500px' ],
			content : basePath + '/s/yunfile/history/list?uk='+uk, 
		});
	}
	
	function del(uk){
		//信息框-例2
		layer.msg('你确定要删除吗', {
		  time: 0 //不自动关闭
		  ,btn: ['确定', '取消']
		  ,yes: function(index){
		    layer.close(index);
		    //执行删除操作
		    var basePath = $("#basePath").val();
		    $.post(basePath+ "/s/yunfile/delete", {
				uk : uk,
			}, function(data) {
				if(data=='success'){
					$("#tr_"+uk).css("display","none");
				}
			});
		  }
		});
	}
	</script>
  </body>
</html>

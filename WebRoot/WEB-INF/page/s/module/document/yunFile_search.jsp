<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <title>技术资料 - 全部文件 - 搜索</title>
	<%@ include file="/WEB-INF/page/s/common/pack-css.jsp" %>
  </head>
  <body class="${bg}" style="position: relative;">
    
    <!-- Wrap all page content here -->
    <div id="wrap">
    	<!-- Make page fluid -->
    	<div class="row">
    		<!-- Fixed navbar -->
        	<div class="navbar navbar-default navbar-fixed-top navbar-transparent-black mm-fixed-top" role="navigation" id="navbar">
    			
    			<%@ include file="/WEB-INF/page/s/common/brand.jsp" %>
    			
    			<!-- .nav-collapse -->
            	<div class="navbar-collapse">
            	
    				<%@ include file="/WEB-INF/page/s/common/top.jsp" %>
    				<%@ include file="/WEB-INF/page/s/common/sidebar.jsp" %>
            	
            	</div>
    			<!-- /.nav-collapse -->
    		
    		</div>
    		<!-- /Fixed navbar -->
    		
    		<!-- Page content -->
       		<div id="content" class="col-md-12">
       			<!-- page header -->
	            <div class="pageheader">
	                <h2><i class="fa fa-th" style="line-height: 45px;padding-left: 0;"></i> 
	                	全部文件
	               		<span></span>
	                </h2>
	
	            </div>
	            <!-- /page header -->
	            
	            <!-- content main container -->
            	<div class="main">
            		<!-- fill real content ...................... -->
            		<div class="row">
            			<div class="col-md-12">
	            		<section class="tile ">
		                  <!-- tile header -->
		                  <div class="tile-header">
	                    	
		                    <div class="controls">
		                    </div>
		                  </div>
		                  <!-- /tile header -->
		
		                  <!-- tile body -->
		                  <div class="tile-body nopadding">
		                  	<form id="pageList" action="${pageContext.request.contextPath}/s/yunfile/search" method="post">
            					<input id="basePath" value="${pageContext.request.contextPath}" type="hidden"> 
				      			<input type="hidden" name="page" value="${page}" id="page">
				      			<input type="hidden" name="uk" value="${uk}" id="uk">
				      			<input type="hidden" name="fk" value="${fk}" id="fk">
				      			<input type="hidden" name="projectId" value="${project.id}" id="projectId">
				      		 	<div class="form-group col-md-6" > 
		                        	</div>
				      		 	<div class="form-group col-md-3" > 
		                         	<input type="text"  name="query" value="${query}" class="form-control col-md-3"  placeholder="对文件名称进行模糊查询"  >
		                        	</div>
				      		 	<div class="form-group col-md-3" > 
		                       		<input type="button"  class="btn btn-primary "   value="查 询" onclick="confirmQuery()"  >
		                        </div>
						    </form>
		                    <table class="table table-bordered">
		                      <thead>
		                        <tr>
		                          <th style="width:5%">#</th>
		                          <th style="width:35%">文件名</th>
		                          <th style="width:8%">编号</th>
		                          <th style="width:8%">大小</th>
		                          <th style="width:20%">修改日期</th>
		                          <th style="width:15%">所在目录</th>
		                          <th style="width:8%">历史版本</th>
		                        </tr>
		                      </thead>
		                      <tbody>
		                      	<c:forEach items="${pageView.records}" var="entry" varStatus="s"> 
			                        <tr id="tr_${entry.uk}">
			                          <td>${s.index+1}</td>
			                          <td>
				                         <c:if test="${entry.isDir}">
				                         	<i class="fa fa-folder fa-4"></i>
				                         	<a href="${pageContext.request.contextPath}/s/yunfile/list?uk=${uk}&fk=${entry.uk}">
				                          		${entry.name}
				                         	</a>
				                         </c:if>
				                         <c:if test="${entry.isFile}">
				                         	<a href="${pageContext.request.contextPath}/s/yunfile/download/${entry.uk}">
				                          		${entry.name}
				                         	</a>
				                         </c:if>
			                          </td>
			                          <td>${entry.number}</td>
			                          <td>${entry.showSize}</td>
			                          <td>
			                          	<fmt:formatDate value="${entry.modifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			                          </td>
			                          <td>
			                          	 <c:if test="${entry.parentFile!=null}">
				                         	<a href="${pageContext.request.contextPath}/s/yunfile/list?uk=${entry.category.uk}&fk=${entry.parentFile.uk}&projectId=${project.id}">
				                          		<b>${entry.category.name}</b> ${entry.parentFile.name}
				                         	</a>
				                         </c:if>
			                          	 <c:if test="${entry.parentFile==null}">
				                         	<a href="${pageContext.request.contextPath}/s/yunfile/list?uk=${entry.category.uk}&fk=${entry.parentFile.uk}&projectId=${project.id}">
				                          		<b>${entry.category.name}</b> 根目录
				                         	</a>
				                         </c:if>
			                          </td>
			                          <td>
				                         <a href="javascript:history('${entry.uk}')" class="btn btn-primary btn-xs"><i class="fa fa-history fa-1" aria-hidden="true"></i>历史</a>
			                          </td>
			                        </tr>
		                      	</c:forEach>
		                      </tbody>
		                    </table>
		
		                  </div>
		                  <!-- /tile body -->
	               		 <%@ include file="/WEB-INF/page/s/common/fenye.jsp" %>
	
	                </section>
           			</div>
            		</div>
            		<!-- /fill real content ...................... -->
            	</div>
	            <!-- content main container -->
	            
       		</div>
    		<!--/ Page content -->
    		
    		<%@ include file="/WEB-INF/page/s/common/right.jsp" %>
    	
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

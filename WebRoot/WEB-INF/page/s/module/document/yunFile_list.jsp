<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <title>技术资料 - ${category.name}</title>
	<%@ include file="/WEB-INF/page/s/common/pack-css.jsp" %>
  </head>
  <body class="${bg}" style="position: relative;" onload="initTable()">
    
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
					
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-6">
							<button type="button" onclick="javascript:create()" class="btn btn-danger">
								<i class="fa fa-upload" style="margin-right: 10px;"></i>上传
							</button>
							<button type="button" onclick="javascript:createFolder()" class="btn btn-primary">
								<i class="fa fa-folder" style="margin-right: 5px;"></i>新建文件夹
							</button>
						</div>
					</div>
					<div class="row" style="margin-top: 10px;">
		                <div class="breadcrumbs">
		                    <ol class="breadcrumb">
		                    	<c:if test="${yunFile!=null}">
		                        	<li><a href="${pageContext.request.contextPath}/s/yunfile/list?uk=${uk}&fk=${yunFile.parentFile.uk}">返回上一级</a></li>	
		                    	</c:if>
		                    	
		                        <li>
		                        	<i class="fa fa-navicon fa-4">
		                        	<a href="${pageContext.request.contextPath}/s/yunfile/list?uk=${uk}">${category.name }</a>
		                        	</i>
		                        </li>
		                        <c:forEach items="${navFileList}" var="entry">
		                        	<li><a href="${pageContext.request.contextPath}/s/yunfile/list?uk=${uk}&fk=${entry.uk}">${entry.name }</a></li>	
		                        </c:forEach>
		                    </ol>
		                </div>
					</div>
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
		                  	<form action="${pageContext.request.contextPath}/s/yunfile/search" method="post">
				      		 	<input type="hidden" name="projectId" value="${project.id}" id="projectId">
				      		 	<div class="form-group col-md-6" > 
		                        	</div>
				      		 	<div class="form-group col-md-3" > 
		                         	<input type="text"  name="query" class="form-control col-md-3"  placeholder="对文件名称进行模糊查询"  >
		                        	</div>
				      		 	<div class="form-group col-md-3" > 
		                       		<input type="submit"  class="btn btn-primary "   value="查 询"  >
		                        </div>
						    </form>
		                  	<form id="pageList" action="${pageContext.request.contextPath}/s/yunfile/list" method="post">
            					<input id="basePath" value="${pageContext.request.contextPath}" type="hidden"> 
				      			<input type="hidden" name="page" value="${page}" id="page">
				      			<input type="hidden" name="uk" value="${uk}" id="uk">
				      			<input type="hidden" name="fk" value="${fk}" id="fk">
				      			<input type="hidden" name="projectId" value="${project.id}" id="projectId">
						    </form>
						    &nbsp;
						    
						    <div class="">
						    	<a href="javascript:;" id="table-view" style=""><i  class="fa fa-bars fa-lg"></i></a>
						    </div>
		                    <table class="table" data-toggle="table">
		                      <thead>
		                        <tr>
		                          <th style="width:5%">#</th>
		                          <th style="width:35%">文件名</th>
		                          <th style="width:10%">编号</th>
		                          <th style="width:10%">大小</th>
		                          <th style="width:15%">修改日期</th>
		                          <th style="width:25%">操作</th>
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
				                         	<i class="fa fa-${entry.icon} fa-8 hidden"></i>
				                         	<a href="${pageContext.request.contextPath}/s/yunfile/download/${entry.uk}">
				                          		${entry.name}
				                         	</a>
				                         </c:if>
			                          </td>
			                          <td>
			                          	<c:if test="${entry.isDir}">-</c:if>
			                          	${entry.number}
			                          </td>
			                          <td>
			                          	<c:if test="${entry.isDir}">-</c:if>
			                          	${entry.showSize}
			                          </td>
			                          <td>
			                          	<fmt:formatDate value="${entry.modifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			                          </td>
			                          <td>
			                          	<c:if test="${entry.isDir }">
			                          		<a href="javascript:editFolder('${entry.uk}')" class="btn btn-primary btn-xs"><i class="fa fa-edit fa-1" aria-hidden="true"></i>修改</a>
			                          	</c:if>
			                          	<c:if test="${entry.isFile }">
			                          		<a href="javascript:edit('${entry.uk}')" class="btn btn-primary btn-xs"><i class="fa fa-edit fa-1" aria-hidden="true"></i>修改</a>
			                          		<a href="javascript:history('${entry.uk}')" class="btn btn-primary btn-xs"><i class="fa fa-history fa-1" aria-hidden="true"></i>历史</a>
			                          	</c:if>
			                          	<a href="javascript:del('${entry.uk}')" class="btn btn-danger btn-xs"><i class="fa fa-trash fa-1" aria-hidden="true"></i>删除</a>
			                          </td>
			                        </tr>
		                      	</c:forEach>
		                      	<c:if test="${pageView.totalrecord<=0}" >
			                      	<div class="text-center col-md-12">
			                      		<div class='notification notification-danger'>
			                      			<h4>此目录下暂无文件</h4>
			                      			<p>点击下方按钮上传文件</p>
			                      			<button type="button" onclick="javascript:create()" class="btn btn-red">
												<i class="fa fa-upload" style="margin-right: 10px;"></i>上传
											</button>
										</div>
			                      	</div>
		                      	</c:if>
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
			area : [ '800px', '450px' ],
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
			area : [ '800px', '450px' ],
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

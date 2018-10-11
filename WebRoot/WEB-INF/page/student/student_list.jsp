<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <title></title>
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
	                <h2><i class="fa fa-compass" style="line-height: 48px;padding-left: 0;"></i> 
	                	学生管理
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
		                  	<form id="pageList" action="${pageContext.request.contextPath}/stu/listAllPage" method="post">
            					<input id="basePath" value="${pageContext.request.contextPath}" type="hidden"> 
				      			<input type="hidden" name="page" value="${page}">
				      		 	<div class="form-group col-md-6" > 
		                        	</div>
				      		 	<div class="form-group col-md-3" > 
		                         	<input id="search" type="text"  name="query" value="${query}" class="form-control col-md-3"  placeholder="对学生姓名进行模糊查询"  >
		                        	</div>
				      		 	<div class="form-group col-md-3" > 
		                       		<input type="button"  class="btn btn-primary "   value="查 询" onclick="javascript:confirmQuery()"  >
		                   			<button type="button" onclick="javascript:create()" class="btn btn-primary">新 建</button>
		                        </div>
						    </form>
		                    <table id="all" class="table table-bordered">
		                      <thead>
		                        <tr>
		                          <th style="width:20%">#</th>
		                          <th style="width:40%">姓名</th>
		                          <th style="width:40%">年龄</th>
		                          <!-- <th style="width:25%">修改时间</th>
		                          <th style="width:25%">操作</th> -->
		                        </tr>
		                      </thead>
		                      <tbody>
		                      	<c:forEach items="${pageView.records}" var="entry" varStatus="s"> 
			                        <tr id="tr_${entry.id}">
			                    	  <td>${entry.id}</td>	
			                          <td>${entry.name}</td>
			                          <td>${entry.age}</td>
			                          <td>
			                         
			                          	<a href="javascript:edit('${entry.id}')" class="btn btn-primary btn-xs"><i class="fa fa-edit fa-1" aria-hidden="true"></i>修改</a>
			                          	<a href="javascript:del('${entry.id}')" class="btn btn-danger btn-xs"><i class="fa fa-trash fa-1" aria-hidden="true"></i>删除</a>
			                          </td>
			                        </tr>
		                      	</c:forEach>
		                      	<c:if test="${pageView.totalrecord<=0}" >
		                      		<tr>
		                      			<td colspan="5">
			                      			<div class="text-center col-md-12">
												<div class='notification notification-danger'>
													<h4>NO DATA</h4>
													<p>暂无相关数据</p>
												</div>
											</div>
		                      			</td>
		                      		</tr>
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
	function create() {
		var basePath = $("#basePath").val();

		layer.open({
			type : 2,
			title : '新增学生',
			//skin : 'layui-layer-molv',// 皮肤
			shift : -1,// flash模糊问题
			scrollbar : false, // 屏蔽浏览器滚动条
			maxmin : true, // 开启最大化最小化按钮
			area : [ '600px', '400px' ],
			content : basePath + '/stu/create',
			//content : basePath + '/s/category/create?projectId='+projectId, 
		});
	}
	function edit(id) {
		var basePath = $("#basePath").val();
		layer.open({
			type : 2,
			title : '修改学生',
			//skin : 'layui-layer-molv',// 皮肤
			shift : -1,// flash模糊问题
			scrollbar : false, // 屏蔽浏览器滚动条
			maxmin : true, // 开启最大化最小化按钮
			area : [ '600px', '400px' ],
			content : basePath + '/stu/edit?id='+id,
			//content : basePath + '/s/category/edit?uk='+uk, 
		});
	}
	function del(id){
		//信息框-例2
		layer.msg('你确定要删除吗', {
		  time: 0 //不自动关闭
		  ,btn: ['确定', '取消']
		  ,yes: function(index){
		    layer.close(index);
		    //执行删除操作
		    var basePath = $("#basePath").val();
		    $.post(basePath+ "/stu/delete", {
				id : id,
			}, function(data) {
				if(data=='success'){
					$("#tr_"+id).css("display","none");
				}
			});
		  }
		});
	}
	
	function confirmQuery(){
		var name = $("#search").val();
		//console.log(name);
		var action = $("#pageList").attr("action");
		//alert(action);
		var action2 = action+'?'+'name='+name;
		//alert(action2);
		$("#pageList").attr("action",action2).submit();
	}
	</script>
  </body>
</html>

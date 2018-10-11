<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <title></title>
	<%@ include file="/WEB-INF/page/s/common/pack-css.jsp" %>
	<style type="text/css">
	a.pname{
		color: #333333;
		text-decoration: none;
		font-size: 25px;
	}
	#module a{
		font-size: 14px;
	}
	#module li{
		margin: 3px 0;
	}
	.module{
		height: 135px;
	}
	.module .title{
		height: 115px;
	}
	</style>
  </head>
  <body class="${bg}" style="position: relative;">
    <!-- Preloader -->
    <div class="mask"><div id="loader"></div></div>
    <!--/Preloader -->
    
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
	                	工程信息
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
		                  	<form id="pageList" action="${pageContext.request.contextPath}/s/project/list" method="post">
            					<input id="basePath" value="${pageContext.request.contextPath}" type="hidden"> 
				      			<input type="hidden" name="page" value="${page}">
				      		 	<div class="form-group col-md-6" > 
		                        	</div>
				      		 	<div class="form-group col-md-3" > 
		                         	<input type="text"  name="keyword" value="${keyword}" class="form-control col-md-3"  placeholder="对工程名称地址进行模糊查询"  >
		                        	</div>
				      		 	<div class="form-group col-md-3" > 
		                       		<input type="button"  class="btn btn-primary "   value="查 询" onclick="confirmQuery()"  >
		                       		<c:if test="${loginUser.mobile==ADMINMOBILE}">
		                   			<button type="button" onclick="javascript:create()" class="btn btn-primary">新 建</button>
		                   			</c:if>
		                        </div>
						    </form>
		                    <div>
		                    <c:if test="${pageView.totalrecord<=0}" >
		                    		<div class="row">
								 		<div class="text-center col-md-12">
				                      		<div class='notification notification-danger'>
				                      			<h3>NO DATA</h3>
				                      			<p>当前登录帐号暂时无相关工程数据</p>
				                      			<img alt="" src="${pageContext.request.contextPath}/resource/image/s/no_data.png" >
											</div>
				                      	</div>
							 		</div>
		                     </c:if>
		                    </div>
	                      	<c:forEach items="${pageView.records}" var="entry" varStatus="s"> 
		                        <div class="col-md-12" id="item" style="margin-bottom: 15px;">
		                        	
		                        	<div class="col-md-12"  style="background: #efefef;padding: 10px;margin-bottom: 0px;">
		                        		<a href="#" class="pname">
		                        			<span>${entry.name } </span>
		                        			<span>
		                        				<a class="" href="javascript:detail('${entry.id}')"><i id="fai_${entry.id}" class="fa fa-caret-down fa-2x" aria-hidden="true"></i></a>
		                        			</span>
		                        		</a>
		                        		<c:if test="${currentProject.id!=entry.id}">
		                        		<a class="btn btn-default" href="${pageContext.request.contextPath}/s/project/default/${entry.id}" style="float: right;" >进入</a>
		                        		</c:if>
		                        		<c:if test="${currentProject.id==entry.id}">
		                        		<button class="btn btn-danger"  style="float: right;" disabled="disabled" >默认</button>
		                        		</c:if>
		                        		<c:if test="${loginUser.mobile==ADMINMOBILE}">
			                        		<a class="btn btn-default" href="javascript:edit('${entry.id}')" style="float: right;" >修改</a>
			                        		<a class="btn btn-default" href="javascript:attendanceMachine('${entry.id}')" style="float: right;" >考勤机状态</a>
		                        		</c:if>
		                        	</div>
		                        	
		                        	<!-- 工程信息 START -->
		                        	<div class="col-md-12 hidden" id="detail_${entry.id}" style="background:#efefef; border:1px solid #dedede; padding: 5px 20px;">
		                        		<div class="row" style="border-bottom: 1px solid #d8d8d8;margin: 5px;">
		                        			<div class="col-md-2"><b>工程地址</b></div>
		                        			<div class="col-md-4">${entry.address}</div>
		                        			<div class="col-md-2"><b>属地</b></div>
		                        			<div class="col-md-4">
		                        				${entry.area.fullName}
		                        			</div>
		                        		</div>
		                        		<div class="row" style="border-bottom: 1px solid #d8d8d8;margin: 5px;">
		                        			<div class="col-md-2"><b>施工时间</b></div>
		                        			<div class="col-md-4">
		                        				<fmt:formatDate value="${entry.startDate}" pattern="yyyy-MM-dd"/>
		                        				至 <fmt:formatDate value="${entry.completeDate}" pattern="yyyy-MM-dd"/>
		                        			</div>
		                        			<div class="col-md-2"><b>施工单位</b></div>
		                        			<div class="col-md-4">${entry.buildUnit }</div>
		                        		</div>
		                        		<div class="row" style="border-bottom: 1px solid #d8d8d8;margin: 5px;">
		                        			<div class="col-md-2"><b>项目经理</b></div>
		                        			<div class="col-md-4">${entry.manager}</div>
		                        			<div class="col-md-2"><b>项目经理电话</b></div>
		                        			<div class="col-md-4">${entry.managerPhone}</div>
		                        		</div>
		                        	</div>
		                        	<!-- 工程信息 END -->
		                        	
		                        	<!-- 模块 STRAT -->
		                        	<div class="col-md-12" id="module" style="margin-top: 10px;">
		                        		<div class="col-md-3 col-sm-4 col-xs-6 module" style="border:1px solid #d3d3d3;padding: 10px 0;">
		                        			<div class="col-md-6 text-center title" style="border-right: 1px solid #d3d3d3;">
		                        				<div class="col-md-12" ">
		                        					<img class="img-thumbnail" style="height: 60px;width: 60px;" src="${pageContext.request.contextPath}/resource/image/s/module_tower.png" />
		                        				</div>
		                        				<div class="col-md-12 text-center" style="margin-top: 5px;">
		                        					<h4>塔式起重机</h4>
		                        				</div>
		                        			</div>
		                        			<div class="col-md-6 content"  >
		                        				<ul style="vertical-align: middle;">
		                        					<li><a href="${pageContext.request.contextPath}/s/hardware/tower/list?projectId=${entry.id}" target="_blank">运行监控</a></li>
		                        					<li><a href="${pageContext.request.contextPath}/s/hardware/tower/weight?projectId=${entry.id}" target="_blank">工作循环</a></li>
		                        					<li><a href="${pageContext.request.contextPath}/s/hardware/tower/alarm?projectId=${entry.id}" target="_blank">报警统计</a></li>
		                        					<li><a href="${pageContext.request.contextPath}/s/hardware/tower/violation?projectId=${entry.id}" target="_blank">违章统计</a></li>
		                        				</ul>
		                        			</div>
		                        		</div>
		                        		<div class="col-md-3 col-sm-4 col-xs-6 module" style="border:1px solid #d3d3d3;padding: 10px 0;">
		                        			<div class="col-md-6 text-center title" style="border-right: 1px solid #d3d3d3;">
		                        				<div class="col-md-12" ">
		                        					<img class="img-thumbnail" style="height: 60px;width: 60px;" src="${pageContext.request.contextPath}/resource/image/s/module_hoist.png" />
		                        				</div>
		                        				<div class="col-md-12 text-center" style="margin-top: 5px;">
		                        					<h4>施工升降机</h4>
		                        				</div>
		                        			</div>
		                        			<div class="col-md-6 content"  >
		                        				<ul style="vertical-align: middle;">
		                        					<li><a href="${pageContext.request.contextPath}/s/hardware/hoist/list?projectId=${entry.id}" target="_blank">运行监控</a></li>
		                        				</ul>
		                        			</div>
		                        		</div>
		                        		<div class="col-md-3 col-sm-4 col-xs-6 module" style="border:1px solid #d3d3d3;padding: 10px 0;">
		                        			<div class="col-md-6 text-center title" style="border-right: 1px solid #d3d3d3;">
		                        				<div class="col-md-12" ">
		                        					<img class="img-thumbnail" style="height: 60px;width: 60px;" src="${pageContext.request.contextPath}/resource/image/s/module_dust.png" />
		                        				</div>
		                        				<div class="col-md-12 text-center" style="margin-top: 5px;">
		                        					<h4>扬尘噪声</h4>
		                        				</div>
		                        			</div>
		                        			<div class="col-md-6 conent"  >
		                        				<ul style="vertical-align: middle;">
		                        					<li>扬尘</li>
		                        					<li>
		                        						<a href="${pageContext.request.contextPath}/s/hardware/dust/nowData?projectId=${entry.id}" target="_blank" >实时</a>
		                        						<a href="${pageContext.request.contextPath}/s/hardware/dust/historyData?projectId=${entry.id}" target="_blank" >历史</a>
		                        						<a href="${pageContext.request.contextPath}/s/hardware/dust/alarmData?projectId=${entry.id}" target="_blank" >报警</a>
		                        						<a href="${pageContext.request.contextPath}/s/hardware/dust/capture?projectId=${entry.id}" target="_blank" >抓图</a>
		                        						<br>
		                        						<a href="${pageContext.request.contextPath}/s/hardware/dust/video?projectId=${entry.id}" target="_blank" >视频</a>
		                        						<a href="${pageContext.request.contextPath}/s/hardware/dust/heatMap?projectId=${entry.id}" target="_blank" >热力图</a>
		                        					</li>
		                        					<li>
		                        					</li>
		                        					
		                        					<li>噪声</li>
		                        					<li>
		                        						<a href="${pageContext.request.contextPath}/s/hardware/noise/nowData?projectId=${entry.id}" target="_blank" >实时</a>	
		                        						<a href="${pageContext.request.contextPath}/s/hardware/noise/historyData?projectId=${entry.id}" target="_blank" >历史</a>
		                        						<a href="${pageContext.request.contextPath}/s/hardware/noise/alarmData?projectId=${entry.id}" target="_blank" >报警</a>
		                        					</li>
		                        				</ul>
		                        			</div>
		                        		</div>
		                        		<div class="col-md-3 col-sm-4 col-xs-6 module" style="border:1px solid #d3d3d3;padding: 10px 0;">
		                        			<div class="col-md-6 text-center" style="border-right: 1px solid #d3d3d3;">
		                        				<div class="col-md-12" ">
		                        					<img class="img-thumbnail" style="height: 60px;width: 60px;" src="${pageContext.request.contextPath}/resource/image/s/module_video.png" />
		                        				</div>
		                        				<div class="col-md-12 text-center" style="margin-top: 5px;">
		                        					<h4>工地视频</h4>
		                        				</div>
		                        			</div>
		                        			<div class="col-md-6"  >
		                        				<ul style="vertical-align: middle;">
		                        					
		                        				</ul>
		                        			</div>
		                        		</div>
		                        		<div class="col-md-3 col-sm-4 col-xs-6 module" style="border:1px solid #d3d3d3;padding: 10px 0;">
		                        			<div class="col-md-6 text-center" style="border-right: 1px solid #d3d3d3;">
		                        				<div class="col-md-12" ">
		                        					<img class="img-thumbnail" style="height: 60px;width: 60px;" src="${pageContext.request.contextPath}/resource/image/s/module_labour.png" />
		                        				</div>
		                        				<div class="col-md-12 text-center" style="margin-top: 5px;">
		                        					<h4>劳务管理</h4>
		                        				</div>
		                        			</div>
		                        			<div class="col-md-6"  >
		                        				<ul style="vertical-align: middle;">
		                        					<li>
		                        						<a href="${pageContext.request.contextPath}/s/labour/employee/list?projectId=${entry.id}" target="_blank" >人员管理</a>
		                        						<a href="${pageContext.request.contextPath}/s/labour/blacklist/list?projectId=${entry.id}" target="_blank" >黑名单</a>
		                        					</li>
		                        					<li>
		                        						<a href="${pageContext.request.contextPath}/s/labour/workerType/list?projectId=${entry.id}" target="_blank" >工种管理</a>
		                        						<a href="${pageContext.request.contextPath}/s/labour/workerGroup/list?projectId=${entry.id}" target="_blank" >班组管理</a>
		                        					</li>
		                        					<li><a href="${pageContext.request.contextPath}/s/labour/attendance/report?projectId=${entry.id}" target="_blank" >考勤报表</a></li>
		                        				</ul>
		                        			</div>
		                        		</div>
		                        		<div class="col-md-3 col-sm-4 col-xs-6 module" style="border:1px solid #d3d3d3;padding: 10px 0;">
		                        			<div class="col-md-6 text-center" style="border-right: 1px solid #d3d3d3;">
		                        				<div class="col-md-12" ">
		                        					<img class="img-thumbnail" style="height: 60px;width: 60px;" src="${pageContext.request.contextPath}/resource/image/s/module_education.png" />
		                        				</div>
		                        				<div class="col-md-12 text-center" style="margin-top: 5px;">
		                        					<h4>安全教育</h4>
		                        				</div>
		                        			</div>
		                        			<div class="col-md-6"  >
		                        				<ul style="vertical-align: middle;">
		                        				</ul>
		                        			</div>
		                        		</div>
		                        		<div class="col-md-3 col-sm-4 col-xs-6 module" style="border:1px solid #d3d3d3;padding: 10px 0;">
		                        			<div class="col-md-6 text-center" style="border-right: 1px solid #d3d3d3;">
		                        				<div class="col-md-12" ">
		                        					<img class="img-thumbnail" style="height: 60px;width: 60px;" src="${pageContext.request.contextPath}/resource/image/s/module_document.png" />
		                        				</div>
		                        				<div class="col-md-12 text-center" style="margin-top: 5px;">
		                        					<h4>技术资料</h4>
		                        				</div>
		                        			</div>
		                        			<div class="col-md-6"  >
		                        				<ul style="vertical-align: middle;">
		                        					<li><a href="${pageContext.request.contextPath}/s/category/list?projectId=${entry.id}" target="_blank" >分类</a></li>
		                        					<li><a href="${pageContext.request.contextPath}/s/yunfile/search?projectId=${entry.id}" target="_blank" >全部文件</a></li>
		                        				</ul>
		                        			</div>
		                        		</div>
		                        		<div class="col-md-3 col-sm-4 col-xs-6 module" style="border:1px solid #d3d3d3;padding: 10px 0;">
		                        			<div class="col-md-6 text-center" style="border-right: 1px solid #d3d3d3;">
		                        				<div class="col-md-12" ">
		                        					<img class="img-thumbnail" style="height: 60px;width: 60px;" src="${pageContext.request.contextPath}/resource/image/s/module_device.png" />
		                        				</div>
		                        				<div class="col-md-12 text-center" style="margin-top: 5px;">
		                        					<h4>设备管理</h4>
		                        				</div>
		                        			</div>
		                        			<div class="col-md-6"  >
		                        				<ul style="vertical-align: middle;">
		                        				</ul>
		                        			</div>
		                        		</div>
		                        	</div>
		                        	<!-- 模块END -->
		                        	
		                        	
		                        </div>
		                        
	                      	</c:forEach>
							 <%@ include file="/WEB-INF/page/s/common/fenye.jsp" %>
							
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
    		
    		<%@ include file="/WEB-INF/page/s/common/right.jsp" %>
    	
    	</div>
    	<!-- /Make page fluid -->
    </div>
    <!-- /Wrap all page content here -->
  
  <section class="videocontent" id="video"></section>
	<%@ include file="/WEB-INF/page/s/common/pack-js.jsp" %>
	
	<script type="text/javascript">
		function detail(pid){
			var hidden= $("#detail_"+pid).hasClass("hidden");
			if(hidden){
				$("#detail_"+pid).removeClass("hidden");
				$("#fai_"+pid).removeClass("fa-caret-down");
				$("#fai_"+pid).addClass("fa-caret-up");
				
			}else{
				$("#detail_"+pid).addClass("hidden");
				$("#fai_"+pid).removeClass("fa-caret-up");
				$("#fai_"+pid).addClass("fa-caret-down");
			}
		}
		
		function create() {
			layer.open({
				type : 2,
				title : '创建工程',
				//skin : 'layui-layer-molv',// 皮肤
				shift : -1,// flash模糊问题
				scrollbar : false, // 屏蔽浏览器滚动条
				maxmin : true, // 开启最大化最小化按钮
				area : [ '800px', '550px' ],
				content : ctx + '/s/project/create', 
			});
		}
		function edit(projectId) {
			layer.open({
				type : 2,
				title : '修改工程',
				//skin : 'layui-layer-molv',// 皮肤
				shift : -1,// flash模糊问题
				scrollbar : false, // 屏蔽浏览器滚动条
				maxmin : true, // 开启最大化最小化按钮
				area : [ '800px', '500px' ],
				content : ctx + '/s/project/edit?projectId='+projectId,
			});
		}
		function attendanceMachine(projectId) {
			layer.open({
				type : 2,
				title : false,
				//skin : 'layui-layer-molv',// 皮肤
				shift : -1,// flash模糊问题
				scrollbar : true, // 屏蔽浏览器滚动条
				maxmin : false, // 开启最大化最小化按钮
				area : [ '500px', '200px' ],
				content : ctx + '/s/project/workerTerminal/state/'+projectId,
			});
		}
	</script>
  </body>
</html>

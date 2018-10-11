<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!-- Sidebar -->
<ul class="nav navbar-nav side-nav" id="sidebar">

    <li class="collapsed-content">
        <ul>
            <li class="search"><!-- Collapsed search pasting here at 768px --></li>
        </ul>
    </li>

    <li class="navigation" id="navigation">
        <a href="#" class="sidebar-toggle" data-toggle="#navigation">导航菜单 <i class="fa fa-angle-up"></i></a>

        <ul class="menu">
            <li>
            	<c:if test="${not empty param.projectId}">
                   	<a href="${pageContext.request.contextPath}/s/index?projectId=${param.projectId}">
                </c:if>
               	<c:if test="${empty param.projectId}">
                   	<a href="${pageContext.request.contextPath}/s/index">
               	</c:if>
                    <i class="fa fa-tachometer"></i> 首页
                </a>
            </li>
            
           <li>
           		<c:if test="${not empty param.projectId}">
                	<a href="${pageContext.request.contextPath}/s/project/list?projectId=${param.projectId}">
           		</c:if>
           		<c:if test="${empty param.projectId}">
                	<a href="${pageContext.request.contextPath}/s/project/list">
           		</c:if>
                    <i class="fa fa-exchange"></i> 工程切换
                </a>
           </li>

           <li class="dropdown ${menu=='doc'?"open":"" }">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-list"></i> 技术资料 <b class="fa fa-plus dropdown-plus"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="${child=='categoryList'?"active":"" }">
                    	<c:if test="${not empty param.projectId}">
                        	<a href="${pageContext.request.contextPath}/s/category/list?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                        	<a href="${pageContext.request.contextPath}/s/category/list">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 分类
                        </a>
                    </li>
                    
                    <li class="${child=='yunFileSearch'?"active":"" }">
                    	<c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/yunfile/search?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/yunfile/search">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 全部文件
                        </a>
                    </li>
                </ul>
           </li>
            
            <li class="dropdown ${menu=='labour'?"open":"" }">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-calendar-check-o"></i> 劳务管理 <b class="fa fa-plus dropdown-plus"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="${child=='employee'?"active":"" }">
                        <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/employee/list?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/employee/list">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 人员
                        </a>
                    </li>
                    <li class="${child=='workerType'?"active":"" }">
                        <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/workerType/list?projectId=${param.projectId}">
                    	</c:if> 
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/workerType/list">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 工种
                        </a>
                    </li>
                    <li class="${child=='workerGroup'?"active":"" }">
                        <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/workerGroup/list?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/workerGroup/list">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 班组
                        </a>
                    </li>
                    
                    <li class="${child=='report'?"active":"" }">
                        <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/attendance/report?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/attendance/report">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 考勤报表
                        </a>
                    </li>
                    <li class="${child=='blacklist'?"active":"" }">
                        <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/blacklist/list?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/blacklist/list">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 黑名单
                        </a>
                    </li>
                </ul>
            </li>
            
            <li class="dropdown ${menu=='salary'?"open":"" }">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-money"></i> 工资管理 <b class="fa fa-plus dropdown-plus"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="${child=='bankInfo'?"active":"" }">
                        <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/bankInfo/list?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/bankInfo/list">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 专户银行信息
                        </a>
                    </li>
                    <li class="${child=='bankCard'?"active":"" }">
                        <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/employee/bankCard/list?projectId=${param.projectId}">
                    	</c:if> 
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/employee/bankCard/list">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 银行卡管理
                        </a>
                    </li>
                    <li class="${child=='salary'?"active":"" }">
                        <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/salary/list?projectId=${param.projectId}">
                    	</c:if> 
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/labour/salary/list">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 工资发放
                        </a>
                    </li>
                    
                </ul>
            </li>
            
            <li class="dropdown ${menu=='attendacne'?"open":"" } hidden">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-train"></i> 安全教育 <b class="fa fa-plus dropdown-plus"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="${child=='today'?"active":"" }">
                        <a href="#">
                            <i class="fa fa-caret-right"></i> 题库管理
                        </a>
                    </li>
                    
                    <li class="${child=='report'?"active":"" }">
                        <a href="#">
                            <i class="fa fa-caret-right"></i> 培训记录
                        </a>
                    </li>
                    
                    <li class="${child=='report'?"active":"" }">
                        <a href="#">
                            <i class="fa fa-caret-right"></i> 发起培训
                        </a>
                    </li>
                    <li class="${child=='report'?"active":"" }">
                        <a href="#">
                            <i class="fa fa-caret-right"></i> 教案管理
                        </a>
                    </li>
                </ul>
            </li>
            <li class="dropdown ${menu=='tower'?"open":"" }">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-tv"></i> 塔式起重机 <b class="fa fa-plus dropdown-plus"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="${child=='list'?"active":"" }">
                         <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/tower/list?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/tower/list">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 运行监控
                        </a>
                    </li>
                    
                    <li class="${child=='weight'?"active":"" }">
                        <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/tower/weight?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/tower/weight">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 工作循环
                        </a>
                    </li>
                    
                    <li class="${child=='alarm'?"active":"" }">
                        <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/tower/alarm?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/tower/alarm">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 报警统计
                        </a>
                    </li>
                    
                    <li class="${child=='violation'?"active":"" }">
                        <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/tower/violation?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/tower/violation">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 违章统计
                        </a>
                    </li>
                </ul>
            </li>
            <li class="dropdown ${menu=='hoist'?"open":"" }">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-tv"></i> 施工升降机 <b class="fa fa-plus dropdown-plus"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="${child=='list'?"active":"" }">
                         <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/hoist/list?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/hoist/list">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 运行监控
                        </a>
                    </li>
                    
                    <li class="hidden ${child=='alarm'?"active":"" }">
                         <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/hoist/alarm?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/hoist/alarm">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 报警统计
                        </a>
                    </li>
                    
                    <li class="hidden ${child=='violation'?"active":"" }">
                        <c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/hoist/violation?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/hoist/violation">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 违章统计
                        </a>
                    </li>
                </ul>
            </li>
            <li class="dropdown ${menu=='dustnoise'?"open":"" }">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-tv"></i> 扬尘噪声 <b class="fa fa-plus dropdown-plus"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="${child=='dustNowData'?"active":"" }">
                    	<c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/nowData?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/nowData">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 扬尘实时数据
                        </a>
                    </li>
                    
                    <li class="${child=='dustHistoryData'?"active":"" }">
                    	<c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/historyData?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/historyData">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 扬尘历史数据
                        </a>
                    </li>

                    <li class="${child=='dustAlarmData'?"active":"" }">
                    	<c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/alarmData?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/alarmData">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 扬尘报警数据
                        </a>
                    </li>
                    
                    <li class="${child=='dustHeatMap'?"active":"" }">
                    	<c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/heatMap?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/heatMap">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 扬尘热力图
                        </a>
                    </li>
                    <li class="${child=='dustVideo'?"active":"" }">
                    	<c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/video?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/video">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 扬尘实时视频
                        </a>
                    </li>
                    <li class="${child=='dustCapture'?"active":"" }">
                    	<c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/capture?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/capture">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 扬尘抓图数据
                        </a>
                    </li>

                    <li class="${child=='noiseNowData'?"active":"" }">
                    	<c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/noise/nowData?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/noise/nowData">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 噪声实时数据
                        </a>
                    </li>
                    
                    
                    <li class="${child=='noiseHistoryData'?"active":"" }">
                    	<c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/noise/historyData?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/noise/historyData">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 噪声历史数据
                        </a>
                    </li>
                    
                    <li class="${child=='noiseAlarmData'?"active":"" }">
                    	<c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/noise/alarmData?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/noise/alarmData">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 噪声报警数据
                        </a>
                    </li>
                    <li class="${child=='control'?"active":"" }">
                    	<c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dustNoise/control?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dustNoise/control">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 设备控制
                        </a>
                    </li>
                </ul>
            </li>
            <li class="hidden dropdown ${menu=='attendacne'?"open":"" }">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-database"></i> 分析统计 <b class="fa fa-plus dropdown-plus"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="${child=='today'?"active":"" }">
                        <a href="#">
                            <i class="fa fa-caret-right"></i> 塔式起重机
                        </a>
                    </li>
                    
                    <li class="${child=='report'?"active":"" }">
                        <a href="#">
                            <i class="fa fa-caret-right"></i> 施工升降机
                        </a>
                    </li>
                    
                    <li class="${child=='report'?"active":"" }">
                        <a href="#">
                            <i class="fa fa-caret-right"></i> 扬尘噪声
                        </a>
                    </li>
                </ul>
            </li>
            <li class="dropdown ${menu=='attendacne'?"open":"" }">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-tty"></i> 设备管理 <b class="fa fa-plus dropdown-plus"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="${child=='today'?"active":"" }">
                        <a href="#">
                            <i class="fa fa-caret-right"></i> 设备分类
                        </a>
                    </li>
                    
                    <li class="${child=='report'?"active":"" }">
                        <a href="#">
                            <i class="fa fa-caret-right"></i> 设备管理
                        </a>
                    </li>
                    
                    <li class="${child=='report'?"active":"" }">
                        <a href="#">
                            <i class="fa fa-caret-right"></i> 设备巡检
                        </a>
                    </li>
                </ul>
            </li>
           	<li>
                <c:if test="${not empty param.projectId}">
                    <a href="${pageContext.request.contextPath}/s/hardware/video/device/list?projectId=${param.projectId}">
               	</c:if>
               	<c:if test="${empty param.projectId}">
                  	<a href="${pageContext.request.contextPath}/s/hardware/video/device/list">
               	</c:if>
                    <i class="fa fa-video-camera"></i> 视频通道
                </a>
           	</li>
            <li class=" hidden dropdown ${menu=='attendacne'?"open":"" }">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-tty"></i> 系统管理 <b class="fa fa-plus dropdown-plus"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="${child=='xx'?"active":"" }">
                        <a href="#">
                            <i class="fa fa-caret-right"></i> 监测点管理
                        </a>
                    </li>
                    
                    <li class="${child=='report'?"active":"" }">
                        <a href="#">
                            <i class="fa fa-caret-right"></i> 设备管理
                        </a>
                    </li>
                    
                    <li class="${child=='report'?"active":"" }">
                        <a href="#">
                            <i class="fa fa-caret-right"></i> 设备巡检
                        </a>
                    </li>
                </ul>
            </li>
        </ul>

    </li>



<li class="navigation" id="navAJ">
        <a href="#" class="sidebar-toggle" data-toggle="#navAJ">安监统计 <i class="fa fa-angle-up"></i></a>

        <ul class="menu">
            <li>
            	<c:if test="${not empty param.projectId}">
                   	<a href="${pageContext.request.contextPath}/s/index?projectId=${param.projectId}">
                </c:if>
               	<c:if test="${empty param.projectId}">
                   	<a href="${pageContext.request.contextPath}/s/index">
               	</c:if>
                    <i class="fa fa-tachometer"></i> 首页
                </a>
            </li>
            
            <li class="dropdown ${menu=='dustnoise'?"open":"" }">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-tv"></i> 扬尘噪声统计排名 <b class="fa fa-plus dropdown-plus"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="${child=='dustNowData'?"active":"" }">
                    	<c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/nowData?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/nowData">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 扬尘日排名
                        </a>
                    </li>
                    
                    <li class="${child=='dustHistoryData'?"active":"" }">
                    	<c:if test="${not empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/historyData?projectId=${param.projectId}">
                    	</c:if>
                    	<c:if test="${empty param.projectId}">
                       		<a href="${pageContext.request.contextPath}/s/hardware/dust/historyData">
                    	</c:if>
                            <i class="fa fa-caret-right"></i> 扬尘周排名
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </li>
    
    
    <li class="navigation hidden" id="order-summary">
        <a href="#" class="sidebar-toggle underline" data-toggle="#order-summary">Orders Summary <i class="fa fa-angle-up"></i></a>

        <div class="media">
            <a class="pull-right" href="#">
                <span id="sales-chart"></span>
            </a>
            <div class="media-body">
                This week sales
                <h3 class="media-heading">26, 149</h3>
            </div>
        </div>

        <div class="media">
            <a class="pull-right" href="#">
                <span id="balance-chart"></span>
            </a>
            <div class="media-body">
                This week balance
                <h3 class="media-heading">318, 651</h3>
            </div>
        </div>

    </li>

    <li class="settings hidden" id="general-settings">
        <a href="#" class="sidebar-toggle underline" data-toggle="#general-settings">General Settings <i class="fa fa-angle-up"></i></a>

        <div class="form-group">
            <label class="col-xs-8 control-label">Switch ON</label>
            <div class="col-xs-4 control-label">
                <div class="onoffswitch greensea">
                    <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="switch-on" checked="">
                    <label class="onoffswitch-label" for="switch-on">
                        <span class="onoffswitch-inner"></span>
                        <span class="onoffswitch-switch"></span>
                    </label>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-8 control-label">Switch OFF</label>
            <div class="col-xs-4 control-label">
                <div class="onoffswitch greensea">
                    <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="switch-off">
                    <label class="onoffswitch-label" for="switch-off">
                        <span class="onoffswitch-inner"></span>
                        <span class="onoffswitch-switch"></span>
                    </label>
                </div>
            </div>
        </div>

    </li>

</ul>
<!-- Sidebar end -->
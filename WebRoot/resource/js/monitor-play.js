var obj = "";
var szIp = "";
var nPort = "";
var szUsername = "";
var szPassword = "";
var platId = ""; // MonitorPlat ID
var basePath = ""; // 提交数据相对地址
var saveDeviceUrl = "";
var saveChannelUrl = "";
var gWndId = ""; // 主创建控件ID

function clear_console() {
	$("#myconsole").empty();
}

function ocx_init() {
	ButtonLogin_onclick();
	ButtonCreateSmartWnd_onclick();
}

function ButtonLogin_onclick() {
	obj = document.getElementById("DPSDK_OCX");
	szIp = document.getElementById("ip").value;
	nPort = document.getElementById("port").value;
	szUsername = document.getElementById("username").value;
	szPassword = document.getElementById("password").value;
	platId = document.getElementById("platId").value;
	basePath = document.getElementById("basePath").value;
	saveDeviceUrl = basePath + "/manage/monitor/device/save";
	saveChannelUrl = basePath + "/manage/monitor/channel/save";
	result = obj.DPSDK_Login(szIp, nPort, szUsername, szPassword);
	if (result == 0) {
		$("#myconsole").append("<div>登录成功</div>");
	} else {
		$("#myconsole").append("<div class='error'>登录失败</div>");
	}
}

function ButtonCreateSmartWnd_onclick() {
	// 获取控件实例
	var obj = document.getElementById("DPSDK_OCX");
	// 左坐标
	var nLeft = 0;
	// 上坐标
	var nTop = 0;
	// 右坐标
	var nRight = 100;
	// 下坐标
	var nBottom = 100;
	nWndCount = document.getElementById("chns").value;
	gWndId = obj.DPSDK_CreateSmartWnd(nLeft, nTop, nRight, nBottom);
	if (obj.DPSDK_GetLastError() == 0) {
		$("#myconsole").append("<div>创建主窗口成功</div>");
	} else {
		$("#myconsole").append("<div>创建主窗口失败</div>");
	}
	// 设置窗口数量
	var result = obj.DPSDK_SetWndCount(gWndId, nWndCount);
	if (result == 0){
		$("#myconsole").append("<div class='divide'>设置窗口数成功，总计："+nWndCount+"个窗口</div>");
	}else{
		$("#myconsole").append("<div class='divide'>设置窗口数失败，错误码："+result+"</div>");
	}

}

function play(strCameraId, index) {
	//$("#myconsole").append("<div>正在向服务器发送播放请求...</div>");
	// 获取ocx实例
	var obj = document.getElementById("DPSDK_OCX");
	// 选择索引号指定窗口
	var result = obj.DPSDK_SetSelWnd(gWndId, index);
	if (result == 0){
		$("#myconsole").append("<div>选中窗口成功，当前选中 "+(index+1)+" 号窗口</div>");
	}else{
		$("#myconsole").append("<div>选中窗口失败，错误码："+result+"</div>");
	}
	
	// 获取当前活动窗口号,其中gWndId是由DPSDK_CreateSmartWnd创建的窗口控件id
	var nWndNo = obj.DPSDK_GetSelWnd(gWndId);
	// 开始预览:主窗口ID，窗口序号，通道ID；码流类型，媒体类型，传输类型
	var nResult = obj.DPSDK_DirectRealplayByWndNo(gWndId, nWndNo, strCameraId,
			1, 1, 1);
	if (nResult == 0) {
		$("#myconsole").append("<div class='divide'>实时播放成功，当前在  "+(nWndNo+1)+" 号窗口播放</div>");
	} else {
		$("#myconsole").append("<div class='divide error'>实时播放失败，错误码："+nResult+"</div>");
	}
}

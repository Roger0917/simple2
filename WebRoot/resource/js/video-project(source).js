/**
 * 
 * 描述：js实现的map方法
 * 
 * @returns {Map}
 */
function Map() {
	var struct = function(key, value) {
		this.key = key;
		this.value = value;
	};
	// 添加map键值对
	var put = function(key, value) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				this.arr[i].value = value;
				return;
			}
		}
		;
		this.arr[this.arr.length] = new struct(key, value);
	};
	// 根据key获取value
	var get = function(key) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				return this.arr[i].value;
			}
		}
		return null;
	};
	// 根据key删除
	var remove = function(key) {
		var v;
		for (var i = 0; i < this.arr.length; i++) {
			v = this.arr.pop();
			if (v.key === key) {
				continue;
			}
			this.arr.unshift(v);
		}
	};
	// 获取map键值对个数
	var size = function() {
		return this.arr.length;
	};
	// 判断map是否为空
	var isEmpty = function() {
		return this.arr.length <= 0;
	};
	this.arr = new Array();
	this.get = get;
	this.put = put;
	this.remove = remove;
	this.size = size;
	this.isEmpty = isEmpty;
}


//---树形表生成 
var setting = {
	view : {
		showIcon : showIconForTree
	},
	data : {
		simpleData : {
			enable : true
		}
	}
};
function showIconForTree(treeId, treeNode) {
	return !treeNode.isParent;
};
function loadTree() {
	var myNodes = eval($("#nodeData").html());
	//console.log(myNodes);
	$.fn.zTree.init($("#mytree"), setting, myNodes);
}

// ----视频流处理相关
var obj ;
var szIp = "";
var nPort = "";
var szUsername = "";
var szPassword = "";
var platId = ""; // MonitorPlat ID
var basePath = ""; // 提交数据相对地址
var saveDeviceUrl = "";
var saveChannelUrl = "";
var gWndId = ""; // 创建控件ID
var dataMap = new Map(); // 存储窗口号与对应的通道ID
var myconsole ;

function clear_console() {
	$("#myconsole").empty();
}
function hidden_console() {
	$("#myconsole").addClass("hidden");
	$("#hc").addClass("hidden");
	$("#sc").removeClass("hidden");
}
function shown_console() {
	$("#myconsole").removeClass("hidden");
	$("#hc").removeClass("hidden");
	$("#sc").addClass("hidden");
}

function all_init() {
	loadTree();
	ButtonLogin_onclick();
	ButtonCreateSmartWnd_onclick();
}

function ButtonLogin_onclick() {
	myconsole=document.getElementById("#myconsole");
	obj = document.getElementById("DPSDK_OCX");
	
	szIp = document.getElementById("ip").value;
	nPort = document.getElementById("port").value;
	szUsername = document.getElementById("username").value;
	szPassword = document.getElementById("password").value;
	basePath = document.getElementById("basePath").value;
	
	result =  obj.DPSDK_Login(szIp, nPort, szUsername, szPassword);
	if (result == 0) {
		$("#myconsole").append("<div>登录成功</div>");
	} else {
		$("#myconsole").append("<div class='error'>登录失败，错误码："+result+"</div>");
	}
	var groupResult= obj.DPSDK_LoadDGroupInfo();
	if (groupResult == 0) {
		$("#myconsole").append("<div>加载组织结构成功</div>");
	} else {
		$("#myconsole").append("<div class='error'>加载组织结构失败，错误码："+groupResult+"</div>");
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
	gWndId = obj.DPSDK_CreateSmartWnd(nLeft, nTop, nRight, nBottom);
	if (obj.DPSDK_GetLastError() == 0) {
		$("#myconsole").append("<div>创建主窗口成功</div>");
	} else {
		$("#myconsole").append("<div>创建主窗口失败</div>");
	}
	// 设置窗口数量
	var result = obj.DPSDK_SetWndCount(gWndId, 1);
	if (result == 0) {
		$("#myconsole").append("<div class='divide'>初始化窗口成功</div>");
	} else {
		$("#myconsole").append(
				"<div class='divide'>初始化窗口数失败，错误码：" + result + "</div>");
	}

}

// 设置窗口数量
function SetWinCount(WinCount) {
	var obj = document.getElementById("DPSDK_OCX");
	obj.DPSDK_SetWndCount(gWndId, WinCount);
}

function play(strCameraId, index) {
	//console.log("参数：" + strCameraId + "--" + index);
	// 获取ocx实例
	var obj = document.getElementById("DPSDK_OCX");

	// 获取当前活动窗口号,其中gWndId是由DPSDK_CreateSmartWnd创建的窗口控件id
	var nWndNo = obj.DPSDK_GetSelWnd(gWndId);
	//console.log("首次获取窗口号：" + nWndNo);
	if (nWndNo == -1) {
		//console.log("进入：" + nWndNo);
		// 没有活动窗口，选择第一个窗口
		var result = obj.DPSDK_SetSelWnd(gWndId, 0);
		if (result == 0) {
			$("#myconsole").append("<div>选中窗口成功，当前选中 1号窗口</div>");
			nWndNo = obj.DPSDK_GetSelWnd(gWndId);// 重新设置窗口序号
		} else {
			$("#myconsole").append("<div>选中窗口失败，错误码：" + result + "</div>");
		}

	}
	//console.log("获取选择窗口号：" + nWndNo);
	dataMap.put(nWndNo, strCameraId);
	// 开始预览:主窗口ID，窗口序号，通道ID；码流类型，媒体类型，传输类型
	var nResult = obj.DPSDK_DirectRealplayByWndNo(gWndId, nWndNo, strCameraId,
			1, 1, 1);
	if (nResult == 0) {
		$("#myconsole").append("<div class='divide'>实时播放成功</div>");
	} else {
		alert("设备离线，连接超时");
		$("#myconsole").append(
				"<div class='divide error'>实时播放失败，错误码：" + nResult + "</div>");
	}
}

// --控制球机相关代码
/**
 * 球机方向控制：鼠标按下事件
 */
function control_diect(nDirect) {
	// 获取ocx实例
	var obj = document.getElementById("DPSDK_OCX");

	var nWndNo = obj.DPSDK_GetSelWnd(gWndId);
	//console.log("方向控制，窗口号：" + nWndNo);
	
	// 球机通道id
	var strCameralId = dataMap.get(nWndNo);
	
	// 云台方向控制:通道ID、方向、步长、是否停止
	var nResult = obj.DPSDK_PtzDirection(strCameralId, nDirect, 2, false);
	if (nResult == 0) {
		$("#myconsole").append("<div class='divide'>方向控制成功</div>");
	} else {
		$("#myconsole").append(
				"<div class='divide error'>方向控制失败，错误码：" + nResult + "</div>");
	}
}

/**
 * 球机方向控制：鼠标松开事件
 */
function stop_diect(nDirect) {
	var obj = document.getElementById("DPSDK_OCX");
	var nWndNo = obj.DPSDK_GetSelWnd(gWndId);
	var strCameralId = dataMap.get(nWndNo);
	var nResult = obj.DPSDK_PtzDirection(strCameralId, nDirect, 4, true);
	if (nResult == 0) {
		$("#myconsole").append("<div class='divide'>(停止)方向控制成功</div>");
	} else {
		$("#myconsole").append(
				"<div class='divide error'>(停止)方向控制失败，错误码：" + nResult + "</div>");
	}
}

/**
 * 球机镜头控制：鼠标按下
 */
function control_camera(nOperation){
	// 获取ocx实例
	var obj = document.getElementById("DPSDK_OCX");
	var nWndNo = obj.DPSDK_GetSelWnd(gWndId);
	var strCameralId = dataMap.get(nWndNo);
	// 云台镜头控制：通道Id、镜头控制、步长
	var nResult = obj.DPSDK_PtzCameraOperation(strCameralId,nOperation, 1,false);
    if (nResult == 0) {
   		$("#myconsole").append("<div class='divide'>镜头控制成功</div>");
   	} else {
   		$("#myconsole").append(
   				"<div class='divide error'>镜头控制失败，错误码：" + nResult + "</div>");
   	}
}
/**
 * 球机镜头控制：鼠标松开
 */
function stop_camera(nOperation){
	// 获取ocx实例
	var obj = document.getElementById("DPSDK_OCX");
	var nWndNo = obj.DPSDK_GetSelWnd(gWndId);
	// 球机通道id
	var strCameralId = dataMap.get(nWndNo);
	// 云台镜头控制：通道Id、镜头控制、步长
	var nResult = obj.DPSDK_PtzCameraOperation(strCameralId,nOperation,1,true);
	if (nResult == 0) {
		$("#myconsole").append("<div class='divide'>(停止)镜头控制成功</div>");
	} else {
		$("#myconsole").append(
				"<div class='divide error'>(停止)镜头控制失败，错误码：" + nResult + "</div>");
	}
}

/**
 * 抓图
 */
function screenshot(){
	// 获取ocx实例
	var obj = document.getElementById("DPSDK_OCX");
	// 保存路径
	var strPicPathName =$("#shot").val() ;// getFullPath(document.getElementById("shot"));// document.getElementById("shot").value; // "D:\\pic.png";
	if(strPicPathName==''){
		alert("请先选择一张本地缓存图片");
		return ;
	}
	if(strPicPathName.indexOf("fakepath")>0){
		alert("为了实现智能存图，必须进行如下设置 \n Internet选项 -> 安全 -> 自定义级别 -> 将本地文件上载至服务器时包含本地目录路径 -> 选“启动” -> 确定");
		return ;
		//Internet选项 -> 安全 -> 自定义级别 -> 将本地文件上载至服务器时包含本地目录路径 -> 选“启动” -> 确定  
	}
	// 当前窗口号
	var nWndNo = obj.DPSDK_GetSelWnd(gWndId);      
    // 抓图
    var nResult = obj.DPSDK_CapturePictureByWndNo(gWndId, nWndNo,strPicPathName);
	if (nResult == 0) {
		$("#myconsole").append("<div class='divide'>抓图成功：</div>");
		uploadImage();
	} else {
		alert("请先选择一个视频画面");
		$("#myconsole").append(
				"<div class='divide error'>抓图失败，错误码：" + nResult + "</div>");
	}
}

function uploadImage() {
    var formData = new FormData($( "#uploadForm" )[0]);
    $.ajax({
         url: basePath+'/cas/page/project/video/image/upload',
         type: 'POST',
         data: formData,
         async: false,
         cache: false,
         contentType: "multipart/form-data;",
         processData: false,
         success: function (returndata) {
             if(returndata=='success'){
            	 $("#myconsole").append("<div class='divide'>上传成功：</div>");
            	 document.getElementById('myconsole').scrollTop=document.getElementById('myconsole').scrollHeight;
             }else{
            	 alert("上传出现异常："+returndata);
             }
         },
         error: function (returndata) {
        	 alert("上传出现错误："+returndata);
         }
    });
}

//------de-
function getFullPath(obj) {
	obj.select();
	alert(document.getSelection());
	alert(document.getSelection().text);
	alert(document.getSelection().createRange().text);
	var r ;
	if (window.getSelection) {
		  r=window.getSelection();
	} else if (document.getSelection) {
		  r=document.getSelection();
	} else if (document.selection) {
		  r=document.selection.createRange();
	}
	alert(r+"--r");
	alert(r.text+"-.r.text");
}

function getFullPath1() {
	var file_upl = document.getElementById('shot');
	file_upl.select();
	//document.getElementById("shot").focus(); //让焦点处于别的控件上，必须加上这句
	document.getElementById("shotImage").focus(); //让焦点处于别的控件上，必须加上这句
	//alert(document.selection);
	//alert(document.selection.createRange());
	//ie11 getSelection()
	//var realpath = document.selection.createRange().text;
	var realpath = document.getSelection();
	return realpath;
}

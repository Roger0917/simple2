var obj = "";
var szIp = "";
var nPort = "";
var szUsername = "";
var szPassword = "";
var platId =""; //MonitorPlat ID
var basePath =""; //提交数据相对地址
var saveDeviceUrl ="" ;
var saveChannelUrl ="";

function clear_console(){
	$("#myconsole").empty();
}

function ocx_init() {
	ButtonLogin_onclick(); // 调用登录
	ButtonLoadDGroupInfo_onclick();// 加载组织结构
}
function load_device() {
	//ButtonLogin_onclick(); // 调用登录
	//ButtonLoadDGroupInfo_onclick();// 加载组织结构
	ButtonGetDGroupStr_onclick();//处理数据并提交数据
}


function ButtonLogin_onclick() {
	 obj = document.getElementById("DPSDK_OCX");
	 szIp = document.getElementById("ip").value;
	 nPort = document.getElementById("port").value;
	 szUsername = document.getElementById("username").value;
	 szPassword = document.getElementById("password").value;
	 platId = document.getElementById("platId").value;
	 basePath = document.getElementById("basePath").value;
	 saveDeviceUrl = basePath+"/manage/monitor/device/save";
	 saveChannelUrl = basePath+"/manage/monitor/channel/save";
	 result = obj.DPSDK_Login(szIp, nPort, szUsername, szPassword);
	if (result == 0) {
		$("#myconsole").append("<div>登录成功</div>");
	} else {
		$("#myconsole").append("<div class='error'>登录失败</div>");
	}
}
function ButtonLoadDGroupInfo_onclick() {
	var obj = document.getElementById("DPSDK_OCX");
	var result = obj.DPSDK_LoadDGroupInfo();
	if (result == 0) {
		$("#myconsole").append("<div>加载组织结构成功</div>");
	} else {
		$("#myconsole").append("<div class='error'>加载组织结构失败</div>");
	}
}

/**获取组织设备信息并上传*/
function ButtonGetDGroupStr_onclick() {
	var obj = document.getElementById("DPSDK_OCX");
	//$("#xmlDATA").html(obj.DPSDK_GetDGroupStr());
	//console.log(obj.DPSDK_GetDGroupStr());
	/** 解析xml* */
	$(obj.DPSDK_GetDGroupStr()).find("Devices").find("Device").each(
			function(i) {
				var did = $(this).attr("id");
				var type = $(this).attr("type");
				var name = $(this).attr("name");
				var manufacturer = $(this).attr("manufacturer");
				var ip = $(this).attr("ip");
				var port = $(this).attr("port");
				var username = $(this).attr("user");
				var password = $(this).attr("password");
				var detail = $(this).attr("desc");
				var status = $(this).attr("status");
				var logintype = $(this).attr("logintype");
				var regid = $(this).attr("regid");
				var proxyport = $(this).attr("proxyport");
				var model = $(this).attr("model");
				var unitnum = $(this).attr("unitnum");
				var deviceCN = $(this).attr("deviceCN");
				var deviceSN = $(this).attr("deviceSN");
				var deviceIp = $(this).attr("deviceIp");
				var devicePort = $(this).attr("devicePort");
				var devMaintainer = $(this).attr("devMaintainer");
				var devMaintainerPh = $(this).attr("devMaintainerPh");
				var rights = $(this).attr("rights");
				var device = new Device(did, type, name, manufacturer, model, ip, port, username, password, detail, status, logintype, regid, proxyport, unitnum, deviceCN, deviceSN, deviceIp, devicePort, devMaintainer, devMaintainerPh, rights);
				
				$(this).find("UnitNodes").each(function() {
					/*
					var un_index = $(this).attr("index");
					var un_channelnum = $(this).attr("channelnum");
					var un_streamType = $(this).attr("streamType");
					var un_subType = $(this).attr("subType");
					var un_type = $(this).attr("type");
					*/
					$(this).find("Channel").each(function() {
						var cid = $(this).attr("id");
						var name = $(this).attr("name");
						var detail = $(this).attr("desc");
						var status = $(this).attr("status");
						var channelType = $(this).attr("channelType");
						var channelSN = $(this).attr("channelSN");
						var rights = $(this).attr("rights");
						var cameralType = $(this).attr("cameralType");
						var ctrlId = $(this).attr("CtrlId");
						var latitude = $(this).attr("latitude");
						var longitude = $(this).attr("longitude");
						var viewDomain = $(this).attr("viewDomain");
						var chn = new Channel(cid, name, detail, status, channelType, channelSN, rights, cameralType, ctrlId, latitude, longitude, viewDomain)
						device.addChannel(chn);
					});
				});
				save(device);
			
		});/**----find("Devices").find("Device").each----*/
}

function save(device){
	/**-------------提交设备数据--------------*/
	$.post(saveDeviceUrl, {
		platId : platId,
		did:device.did,
		type:device.type,
		name:device.name,
		manufacturer:device.manufacturer,
		model:device.model,
		ip:device.ip,
		port:device.port,
		username:device.username,
		password:device.password,
		detail:device.detail,
		status:device.status,
		logintype:device.logintype,
		regid:device.regid,
		proxyport:device.proxyport,
		unitnum:device.unitnum,
		deviceCN:device.deviceCN,
		deviceSN:device.deviceSN,
		deviceIp:device.deviceIp,
		devicePort:device.devicePort,
		devMaintainer:device.devMaintainer,
		devMaintainerPh:device.devMaintainerPh,
		rights:device.rights,
	}, function(data) {
		if(data!='error'){//获取设备ID
			var deviceId = data;
			$("#myconsole").append("<div>设备(ID:"+deviceId+")已更新至服务端</div>");
			
			/***-------处理通道数据----*/
			var chnArray = device.chnList;
			for(var i=0;i<chnArray.length;i++){
				/**----提交通道数据-------*/
				$.post(saveChannelUrl, {
						deviceId:deviceId,
						cid:chnArray[i].cid,
						name:chnArray[i].name,
						detail:chnArray[i].detail,
						status:chnArray[i].status,
						channelType:chnArray[i].channelType,
						channelSN:chnArray[i].channelSN,
						rights:chnArray[i].rights,
						cameralType:chnArray[i].cameralType,
						ctrlId:chnArray[i].ctrlId,
						latitude:chnArray[i].latitude,
						longitude:chnArray[i].longitude,
						viewDomain:chnArray[i].viewDomain,
					}, function(data) {
						if(data!='error'){//获取设备ID             
							$("#myconsole").append("<div>通道(ID:"+data+")已更新至服务端</div>");
							$("#myconsole").scrollTop($('#myconsole')[0].scrollHeight );//操作滚动条到底部
						}
				});
				/**------提交通道数据------*/
			}/**-------for 循环结束 -----*/
		}else{
			$("#myconsole").append("<div class='error'>异常设备名称:"+device_name+"</div>");
		}
	});
	/**-------------提交设备数据--------------*/
}

function Device(did,type,name,manufacturer,model,ip,port,username,password,detail,status,logintype,regid,proxyport,
		unitnum,deviceCN,deviceSN,deviceIp,devicePort,devMaintainer,devMaintainerPh,rights){
	this.did = did;
	this.type = type;
	this.name = name;
	this.manufacturer = manufacturer;
	this.model = model;
	this.ip = ip;
	this.port = port;
	this.username = username;
	this.password = password;
	this.status = status;
	this.logintype = logintype;
	this.regid = regid;
	this.proxyport = proxyport;
	this.unitnum = unitnum;
	this.deviceCN = deviceCN;
	this.deviceSN = deviceSN;
	this.deviceIp = deviceIp;
	this.devicePort = devicePort;
	this.devMaintainer = devMaintainer;
	this.devMaintainerPh = devMaintainerPh;
	this.rights = rights;

	this.chnList = new Array();
	this.addChannel = function(channel){
		this.chnList.push(channel);
	};
};


function Channel(cid,name,detail,status,channelType,channelSN,rights,cameralType,ctrlId,latitude,longitude,viewDomain){
	this.cid = cid;
	this.name = name;
	this.detail = detail;
	this.status = status;
	this.channelType = channelType;
	this.channelSN = channelSN;
	this.rights = rights;
	this.cameralType = cameralType;
	this.ctrlId = ctrlId;
	this.latitude = latitude;
	this.longitude = longitude;
	this.viewDomain = viewDomain;
};

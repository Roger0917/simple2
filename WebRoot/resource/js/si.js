$(".act-atag").click(function() {
	$("#myModal-active").modal({
		backdrop : false,// 点击空白处模态框消失
	});
});

$('#myModal-active').on('show.bs.modal', function(event) {
	var element_a = $(event.relatedTarget); // Button that triggered the modal
	var sid = element_a.data('whatever');
	$('#tip').html("");// 清除提示框内容
	if (sid > 0) {
		$("#sid").val(sid);
		var t = new Date().getTime();
		// 对页面数据进行还原
		$.post("manage/device/query/find/" + sid, {
			t : t,
		}, function(data) {
			console.log(data);
			var jsonObj = eval('(' + data + ')');
			$("#pid").val(jsonObj.pid);
			$('.pname').typeahead('val', jsonObj.pname);
			$("#enable").val(jsonObj.enable);
			$("#mid").val(jsonObj.mid);
			$("#imei").val(jsonObj.imeiASCII + "(" + jsonObj.imei + ")");
			$("#lnglat").val(jsonObj.lnglat);
			$("#scenenum").val(jsonObj.scenenum);
			$("#fiid").val(jsonObj.fiid);
			$('.fnum').typeahead('val', jsonObj.fnum);
			$("#fitext").html(jsonObj.fitext);
			$("#category").val(jsonObj.category);
			// 扬尘噪声报警参数
			$("#msid").val(jsonObj.msid);
			$('.msname').typeahead('val', jsonObj.msname);
			$("#pm2p5").val(jsonObj.pm2p5);
			$("#pm10").val(jsonObj.pm10);
			$("#dbDay").val(jsonObj.dbDay);
			$("#dbNight").val(jsonObj.dbNight);
			$("#spray").val(jsonObj.spray);

			if (jsonObj.category == 7) {// 塔式起重机
				$("#pn").removeClass("hidden");
				$("#lr").addClass("hidden");
				$("#dustnoise").addClass("hidden");
			} else if (jsonObj.category == 8) {// 施工电梯
				$("#direction").val(jsonObj.direction);
				$("#pn").removeClass("hidden");
				$("#lr").removeClass("hidden");
				$("#dustnoise").addClass("hidden");
			} else if (jsonObj.category == 9) { // 扬尘噪声
				$("#pn").addClass("hidden");
				$("#lr").addClass("hidden");
				$("#dustnoise").removeClass("hidden");
			}

			// console.log(obj.sid);
		});
	}
});
// 自动补全
$(function() {
	// 俯视点数据自动补全
	var pointData = new Bloodhound({
		datumTokenizer : Bloodhound.tokenizers.obj.whitespace('label'),
		queryTokenizer : Bloodhound.tokenizers.whitespace,
		prefetch : {
			url : 'manage/point/query/s',
			cache : false
		},
		remote : {
			url : 'manage/point/query/ajax/%QUERY.json',
			wildcard : '%QUERY'
		}
	});

	$('.pname')
			.typeahead(
					null,
					{
						name : 'point-Data',
						display : 'label',
						source : pointData,
						templates : {
							empty : [ '<div class="empty-message">',
									'您的输入未能匹配到工程', '</div>' ].join('\n'),
							suggestion : Handlebars
									.compile('<div><strong>{{label}}</strong>({{address}})</div>')
						},
						limit : 20,
						classNames : {
							input : 'Typeahead-input',
							hint : 'Typeahead-hint',
							selectable : 'Typeahead-selectable'
						}
					});

	$('.pname').bind('typeahead:select', function(ev, suggestion) {
		$("#pid").val(suggestion.id);
		// console.log('Selection: ' + suggestion.id);
		console.log('pid: ' + $("#pid").val());
	});

	// 扬尘监测点数据补全
	var monitorSiteData = new Bloodhound({
		datumTokenizer : Bloodhound.tokenizers.obj.whitespace('label'),
		queryTokenizer : Bloodhound.tokenizers.whitespace,
		prefetch : {
			url : 'manage/device/monitor/site/query/s',
			cache : false
		},
		remote : {
			url : 'manage/device/monitor/site/query/ajax/%QUERY.json',
			wildcard : '%QUERY'
		}
	});
	$('.msname')
			.typeahead(
					null,
					{
						name : 'monitor-site-Data',
						display : 'label',
						source : monitorSiteData,
						templates : {
							empty : [ '<div class="empty-message">',
									'您的输入未能监测点', '</div>' ].join('\n'),
							suggestion : Handlebars
									.compile('<div style="color:#69a3d5;">{{i}}.<b>{{label}}</b>({{areaName}})</div>')
						},
						limit : 20,
						classNames : {
							input : 'Typeahead-input',
							hint : 'Typeahead-hint',
							selectable : 'Typeahead-selectable'
						}
					});

	$('.msname').bind('typeahead:select', function(ev, suggestion) {
		$("#msid").val(suggestion.id);
		// console.log('Selection: ' + suggestion.id);
	});

	// 使用备案编号数据自动补全
	var installData = new Bloodhound({

		datumTokenizer : Bloodhound.tokenizers.obj.whitespace('label'),
		queryTokenizer : Bloodhound.tokenizers.whitespace,
		prefetch : {
			url : 'manage/device/usenumber/query/s',
			cache : false
		},
		remote : {
			url : 'manage/device/usenumber/query/ajax/%QUERY.json?',
			wildcard : '%QUERY',
		}
	});

	$('.fnum')
			.typeahead(
					{
						minLength : 4,// 字符长度>=4时才进行数据查询匹配
						hint : true, // 启用提示
					},
					{
						name : 'install-Data',
						display : 'label',
						// source : installData,
						source : function(query, syncResults, asyncResults) {
							var category = $("#category").val();
							var parameter = {
								category : category
							};
							$.post('manage/device/usenumber/query/ajax/'
									+ query + '.json', parameter,
									function(data) {
										// syncResults(data);
										asyncResults(data);
									});
						},
						/**
						 */
						limit : 20,
						templates : {
							empty : [ '<div class="empty-message">',
									'您的输入未能匹配使用备案编号', '</div>' ].join('\n'),
							suggestion : Handlebars
									.compile('<div style="border-bottom:1px solid blue;"><span style="color:green;">{{label}}</span><br>{{project}}</div>')
						},
						classNames : {
							input : 'Typeahead-input',
							hint : 'Typeahead-hint',
							selectable : 'Typeahead-selectable'
						}
					});

	$('.fnum').bind('typeahead:select', function(ev, suggestion) {
		$("#fiid").val(suggestion.id);
		$("#fitext").html(suggestion.project);
		// console.log('Selection: ' + suggestion.id);
	});
});

function act_save() {
	// $("#act-save").addClass("disabled");// 禁用保存按钮
	var sid = $("#sid").val();
	var mid = $("#mid").val();
	var pid = $("#pid").val();
	var fiid = $("#fiid").val();
	var lnglat = $("#lnglat").val();
	var scenenum = $("#scenenum").val();
	var enable = $("#enable").val();
	var category = $("#category").val();
	var direction = $("#direction").val();
	var msid = $("#msid").val();
	var pm2p5 = $("#pm2p5").val();
	var pm10 = $("#pm10").val();
	var dbDay = $("#dbDay").val();
	var dbNight = $("#dbNight").val();
	var spray = $("#spray").val();
	$.post("manage/device/save/data", {
		sid : sid,
		mid : mid,
		pid : pid,
		fiid : fiid,
		lnglat : lnglat,
		scenenum : scenenum,
		enable : enable,
		direction : direction,
		msid : msid,
		pm2p5 : pm2p5,
		pm10 : pm10,
		dbDay : dbDay,
		dbNight : dbNight,
		spray : spray,
	}, function(data) {
		if (data == 'success') {
			$('#tip').html("<span style='color:green;'>保存成功<span>");
		} else if (data == 'NOLR') {
			$('#tip').html("<span style='color:red;'>请选择左右笼 <span>");
		} else if (data == 'DUST-NOISE-DATA-ERROR') {
			$('#tip').html("<span style='color:red;'>数据类型错误 <span>");
		} else {
			$('#tip').html("<span style='color:red;'>" + data + "<span>");
		}
	});
}

function deleteDevice(siid) {
	if (confirm("此功能主要用于设备软件安装错误进行删除，确认是否删除？")) {
		// 执行删除操作
		$.post("manage/device/reginfo/delete", {
			siid : siid,
		}, function(data) {
			if (data == 'success') {
				document.getElementById("tr_" + siid).style.display = "none";
			} else {
				alert("删除异常，请稍后再试");
			}
		});
	}
}
function removeDevice(siid) {
	if (confirm("该设备在该项目已经被拆除，确定移机？")) {
		// 执行删除操作
		$
				.post(
						"manage/device/remove",
						{
							siid : siid,
						},
						function(data) {
							if (data == 'success') {
								document.getElementById("remove_" + siid).style.display = "none";
								alert("移机成功");
							} else {
								alert("删除异常，请稍后再试");
							}
						});
	}
}

// 弹出地图 设置经纬度
function loadMap() {
	layer.open({
		type : 2,
		title : '设置经纬度',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ '880px', '700px' ],
		content : 'manage/device/lngLat/set',
	});
}

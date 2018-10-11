var width = '900px';
if (document.body.clientWidth < 900) {
	width = (document.body.clientWidth - 10) + "px";
}
// 文件下载记录
function loadDownRecord(fileId) {
	layer.open({
		type : 2,
		title : '设备参数配置',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '600px' ],
		content : 'manage/file/download/list/' + fileId,
	});
}

/** 设备配置：文件下发 */
$(".act-atag").click(function() {
	$("#myModal-active").modal({
		backdrop : false,// 点击空白处模态框消失
	});
});

$('#myModal-active').on('show.bs.modal', function(event) {
	var element_a = $(event.relatedTarget); // Button that triggered the modal
	var dfid = element_a.data('whatever');
	$("#dfid").val(dfid);
	$('#tip').html("");//清除提示框内容
});

function changeWay(){
	var sel = $('#model').val(); 
	if(sel==3){
		$("#funum").css("display","");
	}else{
		$("#funum").css("display","none");
	}
}

//自动补全
$(function() {
	// 使用备案编号数据自动补全
	var usenumberData = new Bloodhound({
		datumTokenizer : Bloodhound.tokenizers.obj.whitespace('label'),
		queryTokenizer : Bloodhound.tokenizers.whitespace,
		prefetch : {
			url : 'manage/device/file/number/query/s',
			cache : false
		},
		remote : {
			url : 'manage/device/file/number/query/ajax/%QUERY.json',
			wildcard : '%QUERY',
			cache : false
		}
	});

	$('.funum')
			.typeahead(
					{
						minLength : 4,// 字符长度>=3时才进行数据查询匹配
						hint : true, // 启用提示
					},
					{
						name : 'usenumber-Data',
						display : 'label',
						source : usenumberData,
						limit : 8,
						templates : {
							empty : [ '<div class="empty-message">',
									'您的输入未能匹配使用备案编号', '</div>' ].join('\n'),
							suggestion : Handlebars
									.compile('<div style="border-bottom:1px solid blue;">'+
											'<span style="color:green;">{{label}}</span><br>'+
											'<span style="color:green;">{{project}}({{category}})</span>'+
											'</div>')
						},
						classNames : {
							input : 'Typeahead-input',
							hint : 'Typeahead-hint',
							selectable : 'Typeahead-selectable'
						}
					});

	$('.funum').bind('typeahead:select', function(ev, suggestion) {
		$("#siid").val(suggestion.id);
		// console.log('Selection: ' + suggestion.id);
	});
});

function act_save() {
	// $("#act-save").addClass("disabled");// 禁用保存按钮
	var siid = $("#siid").val();
	var dfid = $("#dfid").val();
	var model = $("#model").val();
	if(model==3 && siid==""){
		$('#tip').html("<span style='color:red;'>请选择设备</span>");
		return ;
	}
	$.post("manage/device/file/save/data", {
		siid : siid,
		model : model,
		dfid : dfid,
	}, function(data) {
		if (data==-500) {
			$('#tip').html("<span style='color:red;'>程序或网络异常</span>");
		} else if(data==-1) {
			$('#tip').html("<span style='color:red;'>指定设备与文件类型不匹配</span>");
		}else{
			$('#tip').html("共计产生下发指令：<span style='color:green;'><strong>" + data + "</strong></span>"+" 条");
		}
	});
}

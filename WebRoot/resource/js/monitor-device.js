$(".act-atag").click(function() {
	$("#myModal-point").modal({
		backdrop : false,// 点击空白处模态框消失
	});
});

$('#myModal-point').on('show.bs.modal', function(event) {
	var element_a = $(event.relatedTarget); // Button that triggered the modal
	var mid = element_a.data('whatever');
	$('#tip').html("");//清除提示框内容
	$("#mid").val(mid);
	var t = new Date().getTime();
	if (typeof(mid) == "undefined") { 
		console.log("未定义mid");
	}else{
		// 对页面数据进行还原
		$.post("manage/monitor/device/query/find/" + mid, {
			t : t,
		}, function(data) {
			console.log(data);
			var jsonObj = eval('(' + data + ')');
			$('.pname').typeahead('val', jsonObj.pname);
			$("#name").val(jsonObj.name);
			$("#did").val(jsonObj.did);
			$("#ip").val(jsonObj.ip);
			$("#port").val(jsonObj.port);
			$("#username").val(jsonObj.username);
			$("#password").val(jsonObj.password);
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
});

function act_save() {
	// $("#act-save").addClass("disabled");// 禁用保存按钮
	var mid = $("#mid").val();
	var pid = $("#pid").val();
	$.post("manage/monitor/device/bind/point", {
		mid : mid,
		pid : pid,
	}, function(data) {
		if (data == 'success') {
			$('#tip').html("<span style='color:green;'>保存成功<span>");
		} else if(data == 'NOPOINT'){
			$('#tip').html("<span style='color:red;'>未选择工程<span>");
		} else{
			$('#tip').html("<span style='color:red;'>" + data + "<span>");
		}
	});
}

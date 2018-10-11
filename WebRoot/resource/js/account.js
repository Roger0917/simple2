$(function() {
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
						limit : 8,
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
		console.log($("#hasPoint input[value="+suggestion.id+"]"));
		if ($("#hasPoint input[value="+suggestion.id+"]").length > 0 ) { 
			console.log("存在");
			$("#hasPoint input[value="+suggestion.id+"]").parent().fadeOut().fadeIn();
		}else{
			var sinput='<div class="col-md-3">'
				+'<input type="checkbox" checked="checked" name="pids" value="'
				+suggestion.id+'">'
				+suggestion.label+'</div>';
			$("#hasPoint").append(sinput);// 追加新元素
		}
	});
	
	//地区数据获取
	var areaData = new Bloodhound({
		datumTokenizer : Bloodhound.tokenizers.obj.whitespace('label'),
		queryTokenizer : Bloodhound.tokenizers.whitespace,
		prefetch : {
			url : 'manage/area/query/s',
			cache : false
		},
		remote : {
			url : 'manage/area/query/ajax/%QUERY.json',
			wildcard : '%QUERY'
		}
	});

	$('.parea')
			.typeahead(
					null,
					{
						name : 'point-Data',
						display : 'label',
						source : areaData,
						templates : {
							empty : [ '<div class="empty-message">',
									'您的输入未能匹配到行政属地', '</div>' ].join('\n'),
							suggestion : Handlebars
									.compile('<div><strong>{{label}}</strong>({{parentName}})</div>')
						},
						classNames : {
							input : 'Typeahead-input',
							hint : 'Typeahead-hint',
							selectable : 'Typeahead-selectable'
						}
					});

	$('.parea').bind('typeahead:select', function(ev, suggestion) {
		$("#aid").val(suggestion.id);
		// console.log('Selection: ' + suggestion.id);
		$("#error-aid").html("");//清除错误提示
	});
});

function setArea(){
	var ro = $("#role").val();
	if(ro==2){
		$("#areaDiv").removeClass("hidden");
	}else{
		$("#areaDiv").addClass("hidden");
	}
}
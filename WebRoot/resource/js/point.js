$(function() {
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
						name : 'area-Data',
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
	
	
	var pointData = new Bloodhound({
		datumTokenizer : Bloodhound.tokenizers.obj.whitespace('label'),
		queryTokenizer : Bloodhound.tokenizers.whitespace,
		prefetch : {
			url : 'manage/point/query/remote/s',
			cache : false
		},
		remote : {
			url : 'manage/point/query/remote/ajax/%QUERY.json',
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
									.compile('<div><strong>{{label}}</strong>({{gname}})<br>{{address}}</div>')
						},
						classNames : {
							input : 'Typeahead-input',
							hint : 'Typeahead-hint',
							selectable : 'Typeahead-selectable'
						}
					});

	$('.pname').bind('typeahead:select', function(ev, suggestion) {
		$("#pinfoId").val(suggestion.id);
		$("#gid").val(suggestion.gid);
		$("#gname").val(suggestion.gname);
		$("#phone").val(suggestion.phone);
		$("#manager").val(suggestion.manager);
		console.log('Selection: ' + suggestion.address);
		$("#address").val(suggestion.address);
		$("#unit").val(suggestion.unit);
		$("#longitude").val(suggestion.longitude);
		$("#latitude").val(suggestion.latitude);
		// console.log('Selection: ' + suggestion.id);
	});
});
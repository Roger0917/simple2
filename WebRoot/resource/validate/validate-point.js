/*页面校验*/
$(function() {
	var validator = $("#myform").validate( {
		debug : true,
		ignore: "",
		errorClass : "errorStyle",
		submitHandler : function(form) {
			form.submit();
		},
		rules : {
			"pid" : {
				required : true
			},
			"name" : {
				required : true
			},
			"address" : {
				required : true
			},
			"manager" : {
				required : true
			}
		},
		messages : {
			"aid" : {
				required : "行政属地不能为空"
			},
			"name" : {
				required : "工程名称不能为空"
			},
			"address" : {
				required : "工程地址不能为空"
			},
			"manager" : {
				required : "项目经理不能为空"
			}
		},
		errorPlacement : function(error, element) {
			error.appendTo(element.next("span.error"));
			error.appendTo(element.parent().next("span.error"));//#解决工程名称输入框被包裹问题
		},
		highlight : function(element, errorClass) {
			$(element).addClass(errorClass);
		}
	});
});

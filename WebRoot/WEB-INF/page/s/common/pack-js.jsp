<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/mmenu/js/jquery.mmenu.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/sparkline/jquery.sparkline.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/nicescroll/jquery.nicescroll.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/animate-numbers/jquery.animateNumbers.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/videobackground/jquery.videobackground.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/blockui/jquery.blockUI.js"></script>
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/flot/jquery.flot.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/flot/jquery.flot.time.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/flot/jquery.flot.selection.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/flot/jquery.flot.animator.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/flot/jquery.flot.orderBars.js"></script>
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/easypiechart/jquery.easypiechart.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/rickshaw/raphael-min.js"></script> 
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/rickshaw/d3.v2.js"></script>
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/rickshaw/rickshaw.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/morris/morris.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/tabdrop/bootstrap-tabdrop.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/summernote/summernote.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/chosen/chosen.jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/vendor/parsley/parsley.min.js"></script>


<script src="${pageContext.request.contextPath}/resource/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/s/common-form.js"></script>
<script src="${pageContext.request.contextPath}/resource/form-4.2.0/jquery.form.js"></script>
<!-- 校验框架 -->
<script src="${pageContext.request.contextPath}/resource/validate/jquery.validate.min.js" type="text/javascript"></script>

<!-- Latest compiled and minified JavaScript -->
<script src="${pageContext.request.contextPath}/resource/bootstarp/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/bootstarp/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>

<script type="text/javascript">
	/**
	*根据屏幕大小绝定表格显示方式
	*/
	var ctx = $("#ctx").val();
	function initTable(){
		if(document.body.clientWidth<768){
			$("table[data-toggle=table]").bootstrapTable('toggleView');
		}
	}
	function editPwd(){
		layer.open({
			type : 2,
			title : '修改密码',
			skin : 'layui-layer-molv',// 皮肤
			scrollbar : true, // 屏蔽浏览器滚动条
			maxmin : false, // 开启最大化最小化按钮
			area : [ '500px', '300px' ],
			content : ctx + '/s/account/my/password/edit',
		});
	}
	
	function switchProject(ctx){
		var t = new Date().getTime();
		layer.msg('切换中', {
			  icon: 16
			  ,shade: 0.01
		});
		
		$.post(ctx+"/s/project/switch/update", {
			t : t,
		}, function(data) {
			layer.closeAll();
			if (data != 'fail') {
				$("#currentProject").html(data);					
				layer.msg('切换成功', function(){
					//关闭后的操作
				});
			} else{
				layer.msg('切换失败', function(){
					//关闭后的操作
				});
			}
		});
	}
</script>
<script src="${pageContext.request.contextPath}/resource/template_s/assets/js/minimal.min.js"></script>
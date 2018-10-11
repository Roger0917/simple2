$(".category").change(function(){
	var id = $(this).attr('id');
	console.log(id);
	var checked = $(this).is(':checked');
	console.log(checked);
	
	//$("#row-"+id+ " [name = privilegeIds]:checkbox").attr("checked",checked);
	/**在jquery2.0版本使用以上代码不能达到效果，使用以下代码替换*/
	$("#row-"+id+ " [name = privilegeIds]:checkbox").prop("checked",checked);
	
});
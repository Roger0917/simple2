<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <title></title>
	<%@ include file="/WEB-INF/page/s/common/pack-css.jsp" %>
  </head>
  <body class="${bg}" style="position: relative;">
   	<a href="#" onclick="subgo()">点我</a>
   	<table id="kingTable"></table>
   	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.kingTable.js"></script>
	<script type="text/javascript">
    	function subgo(){
    	 $.ajax({
       url:"/simple/stu/list2",
       type:"post",
       data:{
          /*  "deviceName":deviceName,
           "startDate": "2000-01-01 00:00:00",
           "endDate": "2018-01-01 00:00:00" */
       },
       dataType:"json",
       success: function (data) {
    	   alert(data);//---->弹出[object Object],[object Object],[object Object][object Object],[object Object],[object Object]……后台传过来几条singleHistoryData对象就打印几个json对象：[object Object]
           for(var i = 0; i < data.length; i++){	                      
	       	        var id  = data[i]['id'];
	    	        var name = data[i]['name'];
	    	        var age  = data[i]['age'];
	    	        /* var xd    = data[i]['xd'];
	    	        var yd    = data[i]['yd'];
	    	        var zd    = data[i]['zd'];
	    	        var xf    = data[i]['xf'];
	    	        var yf    = data[i]['yf'];
	    	        var zf    = data[i]['zf']; */
                    data1[i] = {id:id,name:name,age:age};
           }
		   if(data.length != 3){
			   for(var j=0;j<(3-((data.length)%3));j++){               //补全最后一页的空白行，使表格的长度保持不变
				   data1[j+data.length] = {id:" ",name:"",age:""}; 
			   }
		   }
           var userOptions = {
                   "id":"kingTable",                                				//必须 表格id
                   "head":["id","姓名","年龄"],  //必须 thead表头
                   "body":data1,                                    				//必须 tbody 后台返回的数据展示
                   "foot":true,                                    					// true/false  是否显示tfoot --- 默认false
                   "displayNum": 3,                               					//必须   默认 10  每页显示行数
                   "groupDataNum":6,                             					//可选    默认 10  组数
                   sort:false,                                    					// 点击表头是否排序 true/false  --- 默认false
                   search:false,                                  					// 默认为false 没有搜索
                   lang:{
                       gopageButtonSearchText:"搜索"
                   }
           }
           var cs = new KingTable(null,userOptions);
           $("#kingTable").KingTable(userOptions);
       }
   }); 
    
}
   
		
</script>
  </body>
</html>

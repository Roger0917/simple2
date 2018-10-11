<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>

<!DOCTYPE html>
<html>
  <head>
    <title>My JSP 'index.jsp' starting page</title>
 	<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/layui/css/layui.css">
  </head>
  
  <body>
    	<div id="log-list">1</div>
		<div id="page-list">2</div>
			
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/layui/layui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/layui/lay/modules/laypage.js"></script>
	<script type="text/javascript">
    	function paging(curr){
    	console.log("执行");
      $.getJSON('/simple/stu/listAllPage', {
        page: curr || 1 //向服务端传的参数
      }, function(res){
          //此处输出内容
          var table = $("<table></table>");
          table.attr({class:"layui-table admin-table",id:"page"});

          var thead = $("<thead><tr><th>编号</th><th>学号</th><th>姓名</th><th>性别</th><th>年龄</th><th>操作</th></tr></thead>");
          table.append(thead);
          var tbody = $("<tbody></tbody>");
          tbody.attr({id:"content"});

          $(function(){
                var datas = res;
                 $.each(datas,function(index,value){
                    var tr = $("<tr></tr>");
                    tr.append("<td>"+ value.id + "</td>");
                      tbody.append(tr);
                      tr.append("<td>"+ value.number + "</td>");
                      tbody.append(tr);
                      tr.append("<td>"+ value.name + "</td>");
                      tbody.append(tr);
                      tr.append("<td>"+ value.sex + "</td>");
                      tbody.append(tr);
                      tr.append("<td>"+ value.age + "</td>");
                      tbody.append(tr);
                      var td = $("<td></td>");
                      var div = $("<div></div>");
                      div.attr({class:"layui-btn-group"});
                      var button1 = $("<button detailId=" + value.weeklyId +">详情</button>");
                      button1.attr({class:"layui-btn detail"});
                      var button2 = $("<button recoveryId=" + value.weeklyId +">恢复</button>");
                      button2.attr({class:"layui-btn recovery"});
                      div.append(button1);div.append(button2);
                      td.append(div);
                      tr.append(td);
                      tbody.append(tr);
                  }); 
              });
          table.append(tbody);
         $("#log-list").append(table);
        // $("#log-list").innerHTML = table;

        //显示分页
        laypage({
          cont: 'page-list', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
          pages: res[0].pageTotal, //通过后台拿到的总页数
          curr: curr || 1, //当前页
          skip: true,
          jump: function(obj, first){ //触发分页后的回调
            if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                $("#log-list").text('');
              paging(obj.curr);
            }
          }
        });
      });
    };
    //运行
    paging();
    </script>
  </body>
</html>

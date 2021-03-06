<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<c:if test="${pageView.totalrecord>0}" >
<div class="row" style="margin-top: 5px;">
	<div class="col-md-9 col-sm-8 text-center">
		<nav>
			<ul class="pagination pagination-md"> 
				<c:if test="${(pageView.currentPage-1)<1}" >
					<li  class="disabled" > <a href='javascript:void(0)' ><i class="fa fa-chevron-left text-center"></i></a> </li>
				</c:if>
				<c:if test="${(pageView.currentPage-1)>=1}" >
					<li> <a href='javascript:topage(${pageView.currentPage-1})' ><i class="fa fa-chevron-left text-center"></i></a> </li>
				</c:if>
				<li> <a href='javascript:topage(1)' class="re" ${pageView.currentPage==1?'style="background-color: #efefef;"':''} >1</a> </li>
				<c:if test="${pageView.pageIndex.startindex>2}" >
					<li> <a href='javascript:void(0)' >...</a> </li> 
				</c:if>
		 		<c:forEach begin="${pageView.pageIndex.startindex}" end="${pageView.pageIndex.endindex}" var="per">
					<li> <a href='javascript:topage(${per})' ${pageView.currentPage==per?'style="background-color: #efefef;"':''}>${per}</a> </li>  
				</c:forEach>
				
				<c:if test="${pageView.pageIndex.endindex<pageView.totalPage-1}" >
					<li> <a href='javascript:void(0)' >...</a> </li>
				</c:if>
				
				<c:if test="${pageView.totalPage>=2}">
					<li> <a href='javascript:topage(${pageView.totalPage})' ${pageView.currentPage==pageView.totalPage?'style="background-color: #efefef;"':''} >${pageView.totalPage}</a> </li>
				</c:if>
				
				<c:if test="${(pageView.currentPage+1)>pageView.totalPage}" >
					<li class="disabled"> <a href='javascript:void(0)' ><i class="fa fa-chevron-right text-center"></i></a> </li>
				</c:if>
				<c:if test="${(pageView.currentPage+1)<=pageView.totalPage}" >
					<li> <a href='javascript:topage(${pageView.currentPage+1})' ><i class="fa fa-chevron-right text-center"></i></a> </li>
				</c:if>
			</ul>
		</nav>
	</div>
	<div class="col-md-3 col-sm-4">
		共计<b style="color: blue"> ${pageView.totalrecord } </b>条数据
		到 <input type="text" value="${pageView.currentPage}" id="inputPage" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  maxlength="5" style="width:40px;height: 25px !important;;vertical-align:baseline;padding: 3px !important;min-height: 25px !important;"> 页
		<input type="button" onclick="javascript:go(${pageView.totalPage})" class="btn btn-primary btn-xs" value="确定" style="vertical-align:baseline;"> 
	</div>
</div>

</c:if>




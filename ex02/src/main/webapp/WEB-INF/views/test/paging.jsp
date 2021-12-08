<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/resources/vendor/jquery/jquery.min.js"></script>
</head>
<body>
	<p>${pageMaker.startPage}</p>
	<p>${pageMaker.endPage}</p>
	<p>${pageMaker.total}</p>
	<p>${pageMaker.prev}</p>
	<p>${pageMaker.next}</p>
	<p>${pageMaker.cri.pageNum}</p>
	<p>${pageMaker.cri.amount}</p>
	<p>${pageMaker.cri.type}</p>
	<p>${pageMaker.cri.keyword}</p>
	
	
	<form id="searchForm" action="/board/list" method="get">
		<input type="hidden" name="pageNum" value="1"/>
		<input type="hidden" name="amount" value="${pageMaker.cri.amount}"/>
		<select name="type">
			<option value="T" 
			<c:out value="${pageMaker.cri.type == 'T' ? 'selected' : ''}"></c:out>>제목</option>
			<option value="C" 
			<c:out value="${pageMaker.cri.type == 'C' ? 'selected' : ''}"></c:out>>내용</option>
			<option value="W" 
			<c:out value="${pageMaker.cri.type == 'W' ? 'selected' : ''}"></c:out>>작성자</option>
			<option value="TC" 
			<c:out value="${pageMaker.cri.type == 'TC' ? 'selected' : ''}"></c:out>>제목 + 내용</option>
			<option value="TW" 
			<c:out value="${pageMaker.cri.type == 'TW' ? 'selected' : ''}"/> >제목 + 작성자</option>
			<option value="TWC" 
			<c:out value="${pageMaker.cri.type == 'TWC' ? 'selected' : ''}"></c:out>>제목 + 내용 + 작성자</option>
		</select>
		<input type="text" name="keyword" value="${pageMaker.cri.keyword}">
		<button class="btn btn-default">Search</button>
	</form>
	
	
	<form id="actionForm" action="/board/list" method="get">
		<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}"/>
		<input type="hidden" name="amount" value="${pageMaker.cri.amount}"/>
		<input type="hidden" name="type" value=<c:out value="${pageMaker.cri.type}"/>/>
		<input type="hidden" name="keyword" value=<c:out value="${pageMaker.cri.keyword}"/>/>
	</form>
	<script type="text/javascript">
	//script for paging
	var actionForm = $("#actionForm");	
	$(".paginate_button a").on("click",function(e) {
		console.log("click");
		e.preventDefault();
		actionForm.find("input[name='pageNum']").val($(this).attr("href"));
		actionForm.submit();
	});
	
	$(".move").on("click",function(e) {
		console.log("click");
		e.preventDefault();
		actionForm.append('<input type="hidden" name="bno" value=""/>');
		actionForm.find("input[name='bno']").val($(this).attr("href"));
		actionForm.attr("action","/board/get");
		actionForm.submit();
	});
	
	$(".paginate_button a").on("click",function(e) {
		console.log("click");
		e.preventDefault();
		actionForm.find("input[name='pageNum']").val($(this).attr("href"));
		actionForm.submit();
	});
	
	</script>
	<div class="pull-right">
		<ul class = "pagination">
			<c:if test="${pageMaker.prev}">
				<li class="paginate_button previous"><a href="${pageMaker.startPage - 10}">prev</a></li>
			</c:if>
			<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
				<li class="paginate_button"><a href="${num}">${num}</a></li>
			</c:forEach>
			<c:if test="${pageMaker.next}">
				<li class="paginate_button next"><a href="${pageMaker.startPage + 10}">next</a></li>
			</c:if>
		</ul>
	</div>
</body>
</html>
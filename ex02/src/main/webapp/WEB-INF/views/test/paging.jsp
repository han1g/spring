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
	
	
	
	
	
	<!-- onclick event test -->
	<form id="actionForm" action="/test/paging" method="get">
		<input type="hidden" name="pageNum" value=""/>
		<input type="hidden" name="amount" value="${pageMaker.cri.amount}"/>
	</form>
	
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
	
	<script type="text/javascript">
	var actionForm = $("#actionForm");	
	$(".paginate_button a").on("click",function(e) {
		console.log("click");
		e.preventDefault();
		actionForm.find("input[name='pageNum']").val($(this).attr("href"));
		actionForm.submit();
	});
	</script>
</body>
</html>
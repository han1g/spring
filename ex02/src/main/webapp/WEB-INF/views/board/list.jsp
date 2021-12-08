<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>SB Admin 2 - Bootstrap Admin Theme</title>
	<%@ include file="../includes/import.jsp" %>
</head>

<body>
	<div id="wrapper">
	<%@ include file="../includes/nav.jsp" %>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Board List</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Board List
						<a href="/board/register" class="btn btn-xs pull-right">reigister</a>
						</div>
						<!-- /.panel-heading -->
						
						<div class="panel-body">
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>#번호</th>
										<th>제목</th>
										<th>작성자</th>
										<th>작성일</th>
										<th>수정일</th>
									</tr>
								</thead>
								<c:forEach items="${list}" var="board">
									<tr>
									 <td><c:out value="${board.bno}"></c:out></td>
									 <td><a class="move" href="<c:out value="${board.bno}"/>">
									 <!-- 링크로 넘길 파라미터가 많아지면 링크가 복잡 -->
									 <c:out value="${board.title}"></c:out></a></td>
									 <td><c:out value="${board.writer}"></c:out></td>
									 <td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate}"/></td>
									 <td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updatedate}"/></td>
									 <!-- cout 을 쓰면 자동으로escape처리되기 때문에 특수문자 오류나 xss에 대응가능 -->
									</tr>
								</c:forEach>
							</table>
							<!-- /.table-responsive -->
							
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
							<script type="text/javascript">
							//script for search
							$("#searchForm button").on("click",function(e) {
								console.log("search");
								e.preventDefault();

								$("#searchForm").submit();
							});
							</script>
							
							<div class="pull-right">
								<ul class = "pagination">
									<c:if test="${pageMaker.prev}">
										<li class="paginate_button previous"><a href="${pageMaker.startPage - 10}">prev</a></li>
									</c:if>
									<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
										<c:choose>
											<c:when test="${pageMaker.cri.pageNum == num}">
												<li style="pointer-events : none;" class="paginate_button active"><a href="${num}">${num}</a></li>
											</c:when>
											<c:otherwise>
												<li class="paginate_button"><a href="${num}">${num}</a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<c:if test="${pageMaker.next}">
										<li class="paginate_button next"><a href="${pageMaker.startPage + 10}">next</a></li>
									</c:if>
								</ul>
							</div>
							
							<form id="actionForm" action="/board/list" method="get">
								<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}"/>
								<input type="hidden" name="amount" value="${pageMaker.cri.amount}"/>
								<input type="hidden" name="type" value="<c:out value="${pageMaker.cri.type}"/>"/>
								<input type="hidden" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>"/>
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
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->
	
	
	 <!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1"  role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-bs-backdrop="static">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <!-- times : 'x' -->
	                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
	            </div>
	            <div class="modal-body">
	                
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
	            </div>
	        </div>
	        <!-- /.modal-content -->
	    </div>
	    <!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<script type="text/javascript">
		$(document).ready(function() {
			var result ="<c:out value="${result}"/>";
			
			checkModal(result);
			
			history.replaceState({},null,null)//param : stateobj,title,url
			//글 등록 수정 후 뒤로가기 했을 때 모달 창 뜨는거 방지용
			
			function checkModal(result){
				if(result === '' || history.state) {
					//parseInt가 NaN일 때 true
					return;
				}
				if(parseInt(result) > 0) {
					$(".modal-body").html("게시글" + result + "번이 등록 되었습니다");
					$("#myModal").modal("show");
				} else if(result === "success") {
					$(".modal-body").html("처리 완료");
					$("#myModal").modal("show");
				}
			}
			
		})
	</script>
	
	<%@ include file="../includes/footer.jsp" %>
</body>

</html>

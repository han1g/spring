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
					<h1 class="page-header">Board Detail</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Board Detail</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<form >
								<div class="form-group">
									<label for="title">Title</label>
									<input id="title" class="form-control" name="title" 
									value='<c:out value="${board.title}"></c:out>' readonly="readonly"/>
								</div>
								<div class="form-group">
									<label for="content">Content</label>
									<textArea id="content" class="form-control" rows="3" name="content" 
									 readonly="readonly"><c:out value="${board.content}"></c:out></textArea>
								</div>
								<div class="form-group">
									<label for="writer">Writer</label>
									<input id="writer" class="form-control" name="writer"
									value='<c:out value="${board.writer}"></c:out>' readonly="readonly"/>
								</div>
							</form>
							<form id="actionForm" action="" method="get">
								
								<input type="hidden" name="pageNum" value="${cri.pageNum}"/>
								<input type="hidden" name="amount" value="${cri.amount}"/>
								<input type="hidden" name="type" value="<c:out value="${cri.type}"/>"/>
								<input type="hidden" name="keyword" value="<c:out value="${cri.keyword}"/>"/>
								<div class="form-group">
									<button class="btn btn-default" data-oper="modify">Modify</button>
									<button class="btn btn-default" data-oper="list">List</button>
								</div>
							</form>
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
	<script>
		$(document).ready(function() {
			var form = $("#actionForm");
			
			$('button').on("click",function(event) {
				event.preventDefault();
				var operation = $(this).data("oper");
				console.log(operation);
				switch(operation) {
					case "modify":
						form.append('<input type="hidden" name="bno" value="${board.bno}"/>');
						form.attr("action","/board/modify");
						break;
					case "list":
						form.attr("action","/board/list");
						break;
						
					default : return;
				
				}
				form.submit();
			})
		})
	</script>
	
	<%@ include file="../includes/footer.jsp" %>
</body>

</html>

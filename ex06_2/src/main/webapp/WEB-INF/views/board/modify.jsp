<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>SB Admin 2 - Bootstrap Admin Theme</title>
	<%@ include file="../includes/import.jsp" %>
	<link rel="stylesheet" type="text/css" href="/resources/css/attachment.css">
</head>

<body>
	<div id="wrapper">
	<%@ include file="../includes/nav.jsp" %>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Board Modify</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Board Modify</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<form id="modify-main" role="form" action="" method="post">
							<input type="hidden" class="form-control" name="bno" 
									value='<c:out value="${board.bno}"></c:out>'/>
								<div class="form-group">
									<label for="title">Title</label>
									<input id="title" class="form-control" name="title" 
									value='<c:out value="${board.title}"></c:out>'/>
								</div>
								<div class="form-group">
									<label for="content">Content</label>
									<textArea id="content" class="form-control" rows="3" name="content" ><c:out value="${board.content}"></c:out></textArea>
								</div>
								<div class="form-group">
									<label for="writer">Writer</label>
									<input id="writer" class="form-control" name="writer"
									value='<c:out value="${board.writer}"></c:out>'/>
								</div>
								<input type="hidden" name="pageNum" value="${cri.pageNum}"/>
								<input type="hidden" name="amount" value="${cri.amount}"/>
								<input type="hidden" name="type" value="<c:out value="${cri.type}"/>"/>
								<input type="hidden" name="keyword" value="<c:out value="${cri.keyword}"/>"/>
								<div class="form-group">
									<button class="btn btn-default" data-oper="modify">Modify</button>
									<button class="btn btn-default" data-oper="remove">Remove</button>
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
			<!-- /.row -->
			
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Files</div>
						<div class="panel-body">
							<div class="form-group uploadDiv">
								<input id="file-input" type="file" name="uploadFile" multiple="multiple">
							</div>
							<div class="uploadResult">
								<ul>
								</ul>
							</div>
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->
	<!-- attachment logics -->
	<script type="text/javascript" src="/resources/js/attachmentModule.js"></script>
	<script>
		var bnoValue = bnoValue = '<c:out value="${board.bno}"/>';
		$(document).ready(function() {
			attachmentService.getAttachment(bnoValue,true);
			
		});
	</script>
	<script>
		$(document).ready(function() {
			var form = $("#modify-main");
			
			$('button').on("click",function(event) {
				event.preventDefault();
				var operation = $(this).data("oper");
				console.log(operation);
				switch(operation) {
					case "modify":
						form.attr("action","/board/modify");
						attachmentService.addAttachmentDataToForm(form);
						break;
					case "remove":
						form.attr("action","/board/remove");
						break;
					case "list":
						var pageNum = form.append($("input[name='pageNum']").clone());
						var amount = form.append($("input[name='amount']").clone());
						var type = form.append($("input[name='type']").clone());
						var keyword = form.append($("input[name='keyword']").clone());
						form.empty();
						form.append(pageNum);
						form.append(amount);
						form.append(type);
						form.append(keyword);
						form.attr("action","/board/list");
						form.attr("method","get");
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

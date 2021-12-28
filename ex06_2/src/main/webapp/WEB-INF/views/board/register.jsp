<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>SB Admin 2 - Bootstrap Admin Theme</title>
	<%@ include file="../includes/import.jsp" %>
	<link rel="stylesheet" type="text/css" href="/resources/css/attachment.css">
</head>

<body>
	<div id="wrapper">
	<div class="bigPictureWrapper">
			<div class="bigPicture">
			</div>
	</div>
	<%@ include file="../includes/nav.jsp" %>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Board Register</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Board Register</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<form role="form" action="/board/register" method="post">
								<div class="form-group">
									<label for="title">Title</label>
									<input id="title" class="form-control" name="title"/>
								</div>
								<div class="form-group">
									<label for="content">Content</label>
									<textArea id="content" class="form-control" rows="3" name="content"></textArea>
								</div>
								<div class="form-group">
									<label for="writer">Writer</label>
									<input id="writer" class="form-control" name="writer" readonly="readonly" value='<sec:authentication property="principal.username"/>'/>
								</div>
								<div class="form-group">
									<button id="registerBtn" type="submit" class="btn btn-default">Submit</button>
									<button type="reset" class="btn btn-default">Reset</button>
								</div>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">File Attach</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="form-group uploadDiv">
								<input id="file-input" type="file" name="uploadFile" multiple="multiple">
							</div>
							<div class="uploadResult">
								<ul></ul>
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
	<script type="text/javascript" src="/resources/js/attachmentModule.js"></script>
	<script>
	$(document).ready(function () {
		/**
		 * 게시글 등록버튼 클릭 리스너 등록
		 */
		var formObj = $("form[role='form']");
		$("#registerBtn").on("click", function(e) {
			e.preventDefault();
			console.log("register");
			var str = "";
			attachmentService.addAttachmentDataToForm(formObj);
			formObj.submit();
		});
	});
	</script>
	<%@ include file="../includes/footer.jsp" %>
</body>

</html>

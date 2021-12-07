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
									<input id="writer" class="form-control" name="writer"/>
								</div>
								<div class="form-group">
									<button type="submit" class="btn btn-default">Submit</button>
									<button type="reset" class="btn btn-default">Reset</button>
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
	<%@ include file="../includes/footer.jsp" %>
</body>

</html>

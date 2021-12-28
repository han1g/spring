<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<sec:authentication property="principal" var="pinfo"/>
<!DOCTYPE html>
<html lang="en">

<head>
<title>SB Admin 2 - Bootstrap Admin Theme</title>
	<%@ include file="../includes/import.jsp" %>
	<script type="text/javascript" src="/resources/js/replyModule.js"></script>
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
								<sec:authorize access="isAuthenticated()"><!-- 이거 안하면 pinfo에 username없어서 개지랄남 -->
								<c:if test="${pinfo.username eq board.writer}">
									<button class="btn btn-default" data-oper="modify">Modify</button>
								</c:if>
								</sec:authorize>
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
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading"><i class="fa fa-comments fa-fw"></i>Reply
						<sec:authorize access="isAuthenticated()">
							<button id="addReplyBtn" class="btn btn-xs pull-right">New reply</button>
						</sec:authorize>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<ul id="replyUL" class="chat">
								<li class="left clearfix" data-rno='rno'>
									<div class="header">
										<strong class="primary-font">replyer</strong>
									</div>
									<p>reply</p>
								</li>
							</ul>
						</div>
						<div id="replyPageFooter" class="panel-footer">
							<ul class="pagination pull-right">
								<li class ="page-item"><a class='page-link' href="startnum">Previous</a></li>
								<li class ="page-item"><a class='page-link' href="1">1</a></li>
								<li class ="page-item"><a class='page-link' href="endNum">Next</a></li>
							</ul>
						</div>
					</div>
					<!-- /.panel panel-default -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
				
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
	                <div class="form-group">
	                	<label>Reply</label>
	                	<input class="form-control" name="reply" value="">
	                </div>
	                <div class="form-group">
	                	<label>Replyer</label>
	                	<input class="form-control" name="replyer" value="">
	                </div>
	                <div class="form-group">
	                	<label>Reply Date</label>
	                	<input class="form-control" name="replydate" value="">
	                </div>
	            </div>
	            <div class="modal-footer">
	            	<button id="modalModBtn" type="button" class="btn btn-default" data-dismiss="modal">modify</button>
	            	<button id="modalRemoveBtn" type="button" class="btn btn-default" data-dismiss="modal">remove</button>
	                <button id="modalRegisterBtn" type="button" class="btn btn-default" data-dismiss="modal">register</button>
	           		<button id="modalCloseBtn" type="button" class="btn btn-default" data-dismiss="modal">close</button>
	            </div>
	        </div>
	        <!-- /.modal-content -->
	    </div>
	    <!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	
	<!-- reply logics -->
	<script type="text/javascript" src="/resources/js/replyModule.js"></script>
	<script type="text/javascript" src="/resources/js/reply.js"></script>
	<script>
		var bnoValue = '<c:out value="${board.bno}"/>';
		loadReply({bnoValue : bnoValue, replyService : replyService});
	</script>
	<!-- reply logics end-->
	<!-- attachment logics -->
	<script type="text/javascript" src="/resources/js/attachmentModule.js"></script>
	<script>
		var showImage = attachmentService.showImage;
		$(document).ready(function() {
			attachmentService.setCloseImageListener($(".bigPictureWrapper"),$(".bigPicture"));
			attachmentService.getAttachment(bnoValue,false);
			
		});
	</script>
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
	<script>
		/*console.log("ajax test");
		
		replyService.add({reply:"JS Test",replyer : "tester", bno:bnoValue}//data,
				function(result) {alert(result);}//callback 
				);*/
				
		/*replyService.getList({bno:bnoValue,page : 1},function(result) { alert(JSON.stringify(result));});*/
		/*replyService.remove(5,function(result) { alert(result);},function(err) {alert(err);});*/
		/*replyService.update({rno : 12, reply:"JS Test modfiy",replyer : "tester", bno:bnoValue});*/
		
	</script>
	
	<%@ include file="../includes/footer.jsp" %>
</body>

</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>SB Admin 2 - Bootstrap Admin Theme</title>
	<%@ include file="../includes/import.jsp" %>
	<script type="text/javascript" src="/resources/js/reply.js"></script>
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
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading"><i class="fa fa-comments fa-fw"></i>Reply
						<button id="addReplyBtn" class="btn btn-xs pull-right">New reply</button>
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
	
	
	
	<script>
		$(document).ready(function() {
			//리스트, 페이징 처리
			var bnoValue = '<c:out value="${board.bno}"/>'
			var replyUL = $('#replyUL');
			
			var pageNum = 1;
			var replyPageFooter = $("#replyPageFooter");
			showList(1);
			
			function showReplyPage(replyCnt) {
				console.log(replyCnt);
				var endNum = Math.ceil(pageNum / 10)*10;
				var startNum = endNum - 9;
				
				var realEnd = Math.ceil(replyCnt/10);
				if(endNum >= realEnd) {
					endNum = realEnd;
				}
				console.log(startNum);
				console.log(endNum);
				
				var prev = startNum != 1;
				var next = endNum < realEnd;
				
				
				replyPageFooter.html("");
				var str = "";
				str += `<ul class="pagination pull-right">`
				if(prev)
					str += `<li class ="page-item"><a class='page-link' href="${'${startNum - 1}'}">Previous</a></li>`;
				for(var i = startNum ; i <=endNum ; i++) {
					var active = pageNum == i ? "active" : "";
					str += `<li class ="page-item  ${'${active}'}"><a class='page-link' href="${'${i}'}" >${'${i}'}</a></li>`;
					}
				if(next)
					str += `<li class ="page-item"><a class='page-link' href="${'${endNum + 1}'}">Next</a></li>`;
				str += `</ul>`;
							
				replyPageFooter.html(str);
				
				
			}
			
			function showList(page) {
				replyService.getList({bno:bnoValue ,page : page || 1},function(replyCnt,list) { 
					var str="";
					console.log(replyCnt);
					console.log(list);
					if(page == -1) { // 댓글 등록 후 -1이 들어옴
						pageNum = Math.ceil(replyCnt/10);
						showList(pageNum);
						return;
					}
					
					if(list == null || list.length == 0) {
						replyUL.html("");
						return;
					}
					for(var i = 0 , len = list.length; i < len;i++) {
						str += `<li class="left clearfix" data-rno='${'${list[i].rno}'}'>
									<div class="header">
										<strong class="primary-font">${'${list[i].replyer}'}</strong>
										<small class="pull-right text-muted">${'${replyService.displayTime(list[i].replydate)}'}</small>
									</div>
									<p>${'${list[i].reply}'}</p>
								</li>`;
					}
					replyUL.html(str);
					showReplyPage(replyCnt);
				});
				
			}

			replyPageFooter.on("click","li a",function(e) {
				console.log("click");
				e.preventDefault();
				pageNum = $(this).attr("href");
				showList(pageNum);
			});
			
			
			//모달 처리
			var modal = $(".modal");
			var modalInputReply = modal.find("input[name='reply']");
			var modalInputReplyer = modal.find("input[name='replyer']");
			var modalInputReplyDate = modal.find("input[name='replydate']");
			
			var modalModBtn = $("#modalModBtn");
			var modalRemoveBtn = $("#modalRemoveBtn");
			var modalRegisterBtn = $("#modalRegisterBtn");
			
			$("#addReplyBtn").on("click",function(e) {
				modal.find("input").val("");
				modal.find("input").closest("div").hide();
				
				modalInputReply.closest("div").show();
				modalInputReplyer.closest("div").show();
				
				modal.find("button[id !='modalCloseBtn']").hide();
				modalRegisterBtn.show();
				$(".modal").modal("show");
			});
			
			modalRegisterBtn.on("click",function(e){
				var reply = {reply : modalInputReply.val(),
							replyer : modalInputReplyer.val(),
							bno : bnoValue};
				replyService.add(reply, function(result) {
					alert(result);
					
					modal.find("input").val("");
					modal.modal("hide");
					showList(-1);
				});
			});
			modalModBtn.on("click",function(e){
				var reply = {reply : modalInputReply.val(),rno : modal.data("rno")};
				replyService.update(reply, function(result) {
					alert(result);
					
					modal.find("input").val("");
					modal.modal("hide");
					showList(pageNum);
				});
			});
			
			modalRemoveBtn.on("click",function(e){
				replyService.remove(modal.data("rno"), function(result) {
					alert(result);
					
					modal.find("input").val("");
					modal.modal("hide");
					showList(pageNum);
				});
			});
			
			$(".chat").on("click","li",function(e) {
				var rno = $(this).data("rno");
				console.log(rno);
				replyService.get(rno,function(result) {
					modal.find("input").val("");
					
					modal.find("input").closest("div").hide();
					
					
					modalInputReply.val(result.reply);
					modalInputReplyer.val(result.replyer);
					modalInputReplyDate.val(replyService.displayTime(result.replydate)).attr("readonly","readonly");
					modal.data("rno",result.rno);
					
					modalInputReply.closest("div").show();
					modalInputReplyer.closest("div").show();
					modalInputReplyDate.closest("div").show();
					
					modal.find("button[id != 'modalCloseBtn']").hide();
					modalModBtn.show();
					modalRemoveBtn.show();
					
					$(".modal").modal("show");	
				});
			});
			
		});
	</script>
	<script>
		console.log("ajax test");
		var bnoValue = '<c:out value="${board.bno}"/>';
		/*replyService.add({reply:"JS Test",replyer : "tester", bno:bnoValue}//data,
				function(result) {alert(result);}//callback 
				);*/
				
		/*replyService.getList({bno:bnoValue,page : 1},function(result) { alert(JSON.stringify(result));});*/
		/*replyService.remove(5,function(result) { alert(result);},function(err) {alert(err);});*/
		/*replyService.update({rno : 12, reply:"JS Test modfiy",replyer : "tester", bno:bnoValue});*/
		
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
	
	<%@ include file="../includes/footer.jsp" %>
</body>

</html>

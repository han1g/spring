/**
 * needs replyModule.js
 */

function loadReply(bnoValue,replyService) {
$(document).ready(function() {
			//리스트, 페이징 처리

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
					str += `<li class ="page-item"><a class='page-link' href="${startNum - 1}">Previous</a></li>`;
				for(var i = startNum ; i <=endNum ; i++) {
					var active = pageNum == i ? "active" : "";
					str += `<li class ="page-item  ${active}"><a class='page-link' href="${i}" >${i}</a></li>`;
					}
				if(next)
					str += `<li class ="page-item"><a class='page-link' href="${endNum + 1}">Next</a></li>`;
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
						str += `<li class="left clearfix" data-rno='${list[i].rno}'>
									<div class="header">
										<strong class="primary-font">${list[i].replyer}</strong>
										<small class="pull-right text-muted">${replyService.displayTime(list[i].replydate)}</small>
									</div>
									<p>${list[i].reply}</p>
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
				console.log("register");
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
}
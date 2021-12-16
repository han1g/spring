<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.bigPictureWrapper {
	position:absolute;
	display:none;
	justify-content: center;
	align-items: center;
	top:0%;
	width:100%;
	height:100%;
	background-color: gray;
	z-index:100;
	background:rgba(255,255,255,0.5);
}
.bigPicture {
	position: relative;
	display: flex;
	justify-content: center;
	align-items: center;
}

.bigPicture img {
	width:600px;
}
</style>
</head>
	<script src="/resources/jquery/jquery.min.js"></script>
	<body>
		<h1>Upload With Ajax</h1>
		<div id="uploadForm">
			<form action="uploadFormAction" method="post" enctype="multipart/form-data">
				<input type='file' name='uploadFile' multiple="multiple">
			</form>
		</div>
		<button id="uploadBtn">Submit</button>
		<ul class="uploadResult">
		</ul>
		
		<div class="bigPictureWrapper">
			<div class="bigPicture">
			</div>
		</div>
		<script>
		function showImage(fileCallPath) {
			console.log(fileCallPath);
			$(".bigPictureWrapper").css("display","flex").show();
			
			$(".bigPicture").html(`<img src="/thumbnail?fileName=${'${encodeURIComponent(fileCallPath)}'}">`)
			.animate({width:'100%', height:'100%'},1000);
		}
		
		$(document).ready(function () {
			$(".bigPictureWrapper").on("click", function(e) {
				$(".bigPicture").animate({width:'0%', height:'0%'},1000);
				setTimeout(()=>{$(this).hide()},1000);
			});
			var initForm = $("#uploadForm").clone();
			var regex = /\.(exe|sh|zip|alz)$/;
			var maxSize = 5242880;//5mb
			function checkExtension(fileName,fileSize) {
				console.log(fileName);
				console.log(fileSize);
				if(fileSize >= maxSize) {
					alert("파일사이즈 초과");
					return false;
				}
				if(regex.test(fileName)) {
					alert("업로드 불가능한 확장자")
					return false;
				}
				return true;
			}
			
			function afterUpload(result) {
				var str ="";
				$(result).each(function(i,obj) {
					var pathAndUuid = encodeURIComponent(obj.uploadPath + "\\" + obj.uuid);
					var fileName = encodeURIComponent(obj.fileName);
					var type = "";
					if(!obj.image) {
						str += `<li>
								<img src="/resources/img/attach.png">`;
						type = "file";
					} else {
						var fileCallPath = encodeURIComponent(obj.uploadPath + "\\s_" + obj.uuid + "_" +obj.fileName);
						var originPath = obj.uploadPath + "\\" + obj.uuid + "_" +obj.fileName;
						originPath = originPath.replaceAll("\\","\\\\");
						// "2021\\12" -(html)-> "2021\12" -(js)-> "2021???" 
						// "2021\\\\12" -(html)-> "2021\\12" -(js)-> "2021\12"
						console.log(originPath);
						str += `<li>
								<a href="javascript:showImage('${'${originPath}'}')"><img src="/thumbnail?fileName=${'${fileCallPath}'}"></a>`;
						type = "image";
					}
					str += `<a href="/download?pathAndUuid=${'${pathAndUuid}'}&fileName=${'${fileName}'}">
						${'${obj.fileName}'}
						</a>
						<span data-file="${'${obj.uploadPath + "\\\\" + obj.uuid + "_" +obj.fileName}'}" data-type="${'${type}'}"> x </span>
						</li>`;
						//${'${"\\\\s"}'} -(jsp)-> ${"\\s"}
				});
				$(".uploadResult").append(str);
			}//업로드 이후의 처리
			
			$(".uploadResult").on("click","span",function(e) {
				var targetFile = $(this).data("file");
				var type = $(this).data("type");
				console.log(targetFile);
				var el = $(this).closest("li");
				$.ajax({
					url: '/deleteFile',
					data: {fileName: targetFile, type:type},
					dataType : 'text',
					type : 'POST',
					success : function(result) {
						alert(result);
						console.log(el);
						el.remove();
					}
				});
			});
			
			$("#uploadBtn").on("click", function(e) {
				var formData = new FormData();
				var inputFile = $("input[name='uploadFile']");
				var files = inputFile[0].files;
				console.log(files);
				
				//add File to formData
				for(var i = 0 ; i< files.length; i++) {
					if(!checkExtension(files[i].name,files[i].size)) {
						return false;
					}
					
					formData.append("uploadFile",files[i]);
				}
				
				$.ajax({
					url: '/uploadFormAction',
					processData: false, // 이거 
					contentType: false, // 둘다 false로 해야됨
					data: formData,
					type: 'POST',
					datatype: 'json',//response datatype
					success: function(result) {
						console.log(result);
						afterUpload(result);
						alert(result);
					},
					complete: function() {
						console.log("complete");
						$("#uploadForm").html(initForm.html());
					}
				});

			});
		});
		</script>
	</body>
</html>
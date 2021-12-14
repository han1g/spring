<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
	<script src="/resources/jquery/jquery.min.js"></script>
	<body>
		<h1>Upload With Ajax</h1>
		<form action="uploadFormAction" method="post" enctype="multipart/form-data">
			<input type='file' name='uploadFile' multiple="multiple">
		</form>
		<button id="uploadBtn">Submit</button>
		<script>

		
		$(document).ready(function () {
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
					success: function(result) {
						alert("success");
					}
				});
			});
		});
		</script>
	</body>
</html>
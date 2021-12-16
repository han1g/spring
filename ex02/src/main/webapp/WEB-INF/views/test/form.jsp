<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script>
	 function func(a,b) {
		 console.log("ab");
	 }
	 function func(a) {
		 console.log("a");
	 }
	 func(1);
	 func(1,2);
	 //오버라이딩 따위 없음
	</script>
</body>
</html>
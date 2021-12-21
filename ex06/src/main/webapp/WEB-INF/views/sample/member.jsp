<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
member page
<form method="post" action="/controller/customLogout"> <!-- method:post, action:/login 고정 -->
	<div>
		<input type="submit" value="logout">
	</div>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" >
</form>
</body>
</html>
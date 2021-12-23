<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Login Page</h1>
<h2><c:out value="${error}"></c:out></h2>
<h2><c:out value="${logout}"></c:out></h2>
<form method="post" action="/controller/login"> <!-- method:post, action:/login 고정 -->
	<div>
		<input type="text" name="username" value="admin">
	</div>
	<div>
		<input type="text" name="password" value="admin">
	</div>
	<div>
		<input type="checkbox" name="remember-me"> 자동로그인
	</div>
	<div>
		<input type="submit">
	</div>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" >
</form>

</body>
</html>
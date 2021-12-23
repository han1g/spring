<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
all page
<p> principal : <sec:authentication property="principal"/></p>
<sec:authorize access="isAuthenticated()">
	<p> MemberVO : <sec:authentication property="principal.member"/></p>
</sec:authorize>
<sec:authorize access="isAnonymous()">
	<a href="/controller/customLogin">login</a>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
	<form method="post" action="/controller/customLogout">
	<div>
		<input type="submit" value="logout">
	</div>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" >
</form>
</sec:authorize>

</body>
</html>
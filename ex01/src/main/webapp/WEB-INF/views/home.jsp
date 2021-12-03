<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  duedate : ${sampleDto.dueDate}. </P>
<P>  page : ${page}. </P>
에러페이지 처리 방식 3개<br>
1.web.xml errorpage, dispatcher servlet에서 작동 -> 예외타입,응답코드 별 에러페이지 설정 가능<br>
2.@ExceptionAdvice 컨트롤러에서 작동<br>
</body>
</html>

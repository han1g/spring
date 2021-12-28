<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<%@ include file="includes/import.jsp" %>
</head>
<body>
<script>
	location.href = "/board/list";
</script>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
</body>

<script>
	$('#asd').html("aaa");
</script>
<div id="asd"></div>
<script>
	$('#asd').html("bbb");
</script>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix ="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<jsp:include page="../menu/menu.jsp"/>
<body>
<div align= "center"> 
	<div>회원목록</div>
	<div>
		<c:forEach items = "${members }" var = "m"> <!-- for문 -->
			${m.memberId } : ${m.memberName }<br>
		</c:forEach>
	</div>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix ="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
<div align= "center"> 
	<div>회원목록</div>
	<div>
		<table border ="1">
			<thead>
				<tr>
					<td width="150" align="center">아이디</td>
					<td width="150" align="center">이 름</td>
					<td width="150" align="center">나 이</td>
					<td width="150" align="center">전화번호</td>
					<td width="250" align="center">주 소</td>
					<td width="150" align="center">권 한</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items ="${members }" var ="m">
					<tr>
						<td width="150" align="center">${m.memberId }</td>
						<td width="150" align="center">${m.memberName }</td>
						<td width="150" align="center">${m.memberAge }</td>
						<td width="150" align="center">${m.memberTel }</td>
						<td width="250" align="center">${m.memberAddress }</td>
						<td width="150" align="center">${m.memberAuthor }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div><br>
</div>
</body>
</html>
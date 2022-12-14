<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>noticeAjax.jsp</title>
<script src="Js/table.js"></script>
<script src="Js/noticeAjax.js"></script>
</head>

<body>
	<h3>Ajax연습.</h3>
	<div>
		<form name="ajaxFrm" action="" enctype="multi">
			<table>
				<tr>
					<th><label for="writer">작성자</label></th>
					<td><input type="text" name="writer" id="writer" value="홍길동"></td>
				</tr>
				<tr>
					<th><label for="title">제목</label></th>
					<td><input type="text" name="title" id="title" value="Ajax"></td>
				</tr>
				<tr>
					<th><label for="subject">내용</label></th>
					<td><textarea cols="30" rows="3" name="subject" id="subject">
							Ajax연습
						</textarea></td>
				</tr>
				<tr>
					<th><label for="nfile">첨부파일</label></th>
					<td><input type="file" name="nfile" id="nfile"></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="저장">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="show"></div>
	<script>
		
	</script>
</body>

</html>
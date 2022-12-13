<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>noticeAjax.jsp</title>
	<script src="Js/table.js"></script>
</head>

<body>
	<h3>Ajax연습.</h3>
	<div>
		<table>			
				<form name="ajaxFrm" action="">
					<tr>
						<th><label for="writer">작성자</label></th>
						<td><input type="text" name="writer" id="writer"></td>
					</tr>
					<tr>
						<th><label for="title">제목</label></th>
						<td><input type="text" name="title" id="title"></td>
					</tr>
					<tr>
						<th><label for="subject">내용</label></th>
						<td><textarea cols="30" rows="3" name="subject" id="subject"></textarea></td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" value="저장">
						</td>
					</tr>
				</form>
		</table>
	</div>
	<div id="show"></div>
	<script>
		console.log('바뀜')
		const xhtp = new XMLHttpRequest();
		xhtp.open('GET', 'noticeListAjax.do');
		xhtp.send();
		xhtp.onload = function () {
			let data = JSON.parse(xhtp.response); // json -> javascript object.
			console.log(data);

			table.initData = data;
			table.showField = ['noticeId', 'noticeWriter', 'noticeTitle', 'noticeDate', 'noticeHit', 'noticeFile']
			let tbl = table.makeTable(); //table , thead ,tbody를 테이블을 만들어준 다음에 추가를 해야한다.
			//thead -> 타이틀추가.
			table.addTitle('삭제')

			//tbody -> 버튼추가.
			document.getElementById('show').append(tbl);
		}

		//입력처리.
		document.querySelector('form[name=ajaxFrm]').addEventListener('submit',addNotice);
		function addNotice(e){
			e.preventDefault(); //submit 의 기능차단.
			let writer = document.getElementById('writer').value;
			let title = document.getElementById('title').value;
			let subject = document.getElementById('subject').value;
			const xhtp = new XMLHttpRequest();
			xhtp.open('GET','noticeAddAjax.do?writer='+writer+'&title='+title+'&subject='+subject+'&noticeDate=2022-12-13'); // get방식은 
			xhtp.send();
			xhtp.onload = function(){
				let data = JSON.parse(xhtp.response);
				console.log(data);
				
			}
		}
	</script>
</body>

</html>
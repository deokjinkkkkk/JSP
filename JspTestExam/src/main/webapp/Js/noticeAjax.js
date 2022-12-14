/**
 * 
 */
// console.log('abcde'.slice(-2)) //slice문자열을 잘라준다. (시작값,마지막값), -? 뒤에 값제거
Date.prototype.yyyymmdd = function() {
	let y = this.getFullYear();
	let m = this.getMonth() + 1;
	let d = this.getDate();
	let ymd = y + '-' + ('0' + m).slice(-2) + '-' + ('0' + d).slice(-2);
	return ymd;
}
let today = new Date();

document.addEventListener('DOMContentLoaded', init);

function init() {
	// 데이터 조회
	const xhtp = new XMLHttpRequest();
	xhtp.open('GET', 'noticeListAjax.do');
	xhtp.send();
	xhtp.onload = function() {
		let data = JSON.parse(xhtp.response); // json -> javascript object.
		console.log(data);

		table.initData = data;
		table.showField = ['noticeId', 'noticeWriter', 'noticeTitle', 'noticeDate', 'noticeHit', 'noticeFile']
		let tbl = table.makeTable(); //table , thead ,tbody를 테이블을 만들어준 다음에 추가를 해야한다.
		document.getElementById('show').append(tbl);
		//thead -> 타이틀추가.
		// table.addTitle('삭제')

		let th = document.createElement('th')
		th.innerText = '삭제';
		let th1 = document.createElement('th')
		th1.innerText = '수정';
		document.querySelector('#show thead tr').append(th, th1);
		//tbody -> 버튼추가.

		document.querySelectorAll('#show tbody tr').forEach(item => {

			item.append(addDelBtn());	//삭제			
			item.append(addModBtn());	//수정
		})
	}
	//입력처리.
	document.querySelector('form[name=ajaxFrm]')
	.addEventListener('submit', addMultiNotice);
} // end of init


function addDelBtn() {
	let td = document.createElement('td')
	let btn = document.createElement('button')
	btn.addEventListener('click', delNotice)
	btn.innerText = '삭제';
	td.append(btn);
	return td;
}
function addModBtn() {
	td = document.createElement('td')
	btn = document.createElement('button')
	btn.innerText = '수정';
	btn.addEventListener('click', modNotice);
	td.append(btn);
	return td;
}

function addMultiNotice(e){
	e.preventDefault()
	let formData = new FormData(); //multipart/form-data
	let writer = document.getElementById('writer').value;
	let title = document.getElementById('title').value;
	let subject = document.getElementById('subject').value;
	let nfile = document.getElementById('nfile'); //file이기때문에 value X
	
	formData.append('noticeWriter',writer);
	formData.append('noticeTitle',title);
	formData.append('noticeSubject',subject);
	formData.append('noticeDate',today.yyyymmdd());
	formData.append('nfile',nfile.files[0]);
	
	const xhtp = new XMLHttpRequest();
	xhtp.open('POST', 'noticeAddAjax.do');
	xhtp.send(formData);
	xhtp.onload = function (){
		console.log(xhtp.response);
	}
}

//key:val 입력.
function addNotice(e) {
	e.preventDefault(); //submit 의 기능차단.
	let writer = document.getElementById('writer').value;
	let title = document.getElementById('title').value;
	let subject = document.getElementById('subject').value;

	const xhtp = new XMLHttpRequest();
	xhtp.open('POST', 'noticeAddAjax.do'); // get방식은 
	xhtp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded')
	xhtp.send('writer=' + writer + '&title=' + title + '&subject=' + subject + '&noticeDate=' + today.yyyymmdd());

	xhtp.onload = function() {
		let data = JSON.parse(xhtp.response);
		console.log(data); //{noticeId:?,noticeWriter:?,....}
		//반환된 값을 활용해서 화면출력.
		let tr = document.createElement('tr')
		table.showField.forEach(item => {
			let td = document.createElement('td')
			td.innerText = data[item];
			tr.append(td);
		});
		//삭제버튼
		tr.append(addDelBtn());
		//수정버튼.
		tr.append(addModBtn());

		document.querySelector('#show tbody').prepend(tr);
	}
}
// 삭제버튼 이벤트핸들러.
function delNotice() {
	console.log(this.parentElement.parentElement.firstChild.innerText) //this = btn
	let id = this.parentElement.parentElement.firstChild.innerText

	const xhtp = new XMLHttpRequest();
	xhtp.open('post', 'noticeDelAjax.do')
	xhtp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded')
	xhtp.send('id=' + id);
	xhtp.onload = delCallback;
}

// 삭제콜백함수.
function delCallback(e) {
	console.log(e)
	let result = JSON.parse(this.response);
	if (result.retCode == 'Success') {
		console.log(result.id);
		document.querySelectorAll('#show tbody tr').forEach(item => {
			if (item.firstChild.innerText == result.id) {
				item.remove();
			}
		})
	} else if (result.retCode == 'Fail') {
		alert('처리 중 에러 발생.');
	}
}

//수정 이벤트 핸들러
function modNotice() {
	let oldTr = this.parentElement.parentElement;
	let clone = this.parentElement.parentElement.cloneNode(true);

	let writer = clone.children[1].innerText;
	let title = clone.children[2].innerText;
	let date = clone.children[3].innerText;


	let td1 = document.createElement('td') //<td><input value=?></td>
	let inp1 = document.createElement('input');
	inp1.value = writer;
	td1.append(inp1);
	clone.children[1].replaceWith(td1);

	let td2 = document.createElement('td') //<td><input value=?></td>
	let inp2 = document.createElement('input');
	inp2.value = title;
	td2.append(inp2)
	clone.children[2].replaceWith(td2);

	let td3 = document.createElement('td');
	let inp3 = document.createElement('input');
	inp3.setAttribute('type', 'date');
	inp3.value = date;
	td3.append(inp3);
	clone.children[3].replaceWith(td3);

	// clone의 삭제버튼 disable
	clone.children[6].firstChild.disabled = true;

	//clone의 수정 -> 변경
	clone.children[7].firstChild.innerText = '변경';

	//변경버튼에 이벤트 : DB수정, 화면변경.
	clone.children[7].firstChild.addEventListener('click', updateNotice)

	oldTr.replaceWith(clone);
}

//변경처리
function updateNotice() {
	let id = this.parentElement.parentElement.firstChild.innerText;
	let writer = this.parentElement.parentElement.children[1].firstChild.value
	let title = this.parentElement.parentElement.children[2].firstChild.value
	let date = this.parentElement.parentElement.children[3].firstChild.value
	let param = 'id=' + id + '&writer=' + writer + '&title=' + title + '&date=' + date;

	const xhtp = new XMLHttpRequest();
	xhtp.open('post', 'noticeUpdateAjax.do');
	xhtp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded')
	xhtp.send(param);
	xhtp.onload = updateCallBack;
}

function updateCallBack() {
	console.log(this.response); // 새로운 tr생성
	let data = JSON.parse(this.response);
	// 새로 tr
	let tr = document.createElement('tr')
	table.showField.forEach(item => {
		let td = document.createElement('td')
		td.innerText = data[item];
		tr.append(td);
	});
	//삭제버튼
	tr.append(addDelBtn());
	//수정버튼.
	tr.append(addModBtn());

	//대체
	document.querySelectorAll('#show tbody tr').forEach(item => {
		if (item.firstChild.innerText == data.noticeId) {
			item.replaceWith(tr);
		}
	})
}
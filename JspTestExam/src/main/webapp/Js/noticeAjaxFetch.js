/**
 * noticeAjaxFetch.js fetch api활용
 */
Date.prototype.yyyymmdd = function() {
	let y = this.getFullYear();
	let m = this.getMonth() + 1;
	let d = this.getDate();
	let ymd = y + '-' + ('0' + m).slice(-2) + '-' + ('0' + d).slice(-2);
	return ymd;
}
let today = new Date();

console.log('noticeAjaxFetch Start')
const showFields = {
	noticeId: "공지번호",
	noticeWriter: "작성자",
	noticeTitle: "제목",
	noticeDate: "작성일자",
	noticeHit: "조회수"
}

const addColumn = {
	col1: ['button', '삭제'],
	col2: ['button', '수정'],
	col3: ['input', 'checkbox'] //element, type속성
}

function makeTable(aryData = [], parent) {
	let tbl = document.createElement('table')
	tbl.setAttribute('border', '1')
	let thd = document.createElement('thead')
	let tbd = document.createElement('tbody')
	let tr = document.createElement('tr')

	let fields = showFields; //보여줄 항목을 지정(전체항목);
	// head영역.
	console.log(fields)
	for (const field in fields) {
		let th = document.createElement('th')
		th.innerText = fields[field];
		tr.append(th);
	}
	// 추가항목.
	for (const col in addColumn) {
		let th = document.createElement('th')
		th.innerHTML = addColumn[col][0] == 'button' ? addColumn[col][1] : '<input type="checkbox">'; //'button'
		tr.append(th)
	}
	thd.append(tr);
	tbl.append(thd);

	//body 영역
	for (const data of aryData) {
		let tr = makeTr(data);
		tbd.append(tr);
	}
	tbl.append(tbd);

	parent.append(tbl); //생성된 테이블요소 매개값으로 사용된 위치 append.
}

function makeTr(data) {
	tr = document.createElement('tr')
	for (const field in showFields) {
		let td = document.createElement('td')
		td.innerText = data[field]
		tr.append(td);
	}
	//추가항목.
	for (const col in addColumn) {
		let td = document.createElement('td')
		let elem = document.createElement(addColumn[col][0])
		elem.setAttribute('class','cancelbtn')
		elem.innerText = addColumn[col][1];
		if (addColumn[col][0] == 'input' ){
			elem.setAttribute('type', addColumn[col][1])
		}
		td.append(elem);
		tr.append(td);
	}
	return tr;
}
//삭제 Ajax호출,

function delAjaxFnc() {
	let id = this.parentElement.parentElement.getAttribute('id'); // notice_1
	id = id.substring(7); //notice_
	fetch('noticeDelAjax.do', {
		method: 'post',//페이지 요청 방식 지정,get,post요청(추가,수정,삭제)
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded'
			//application/json,mulipart/form-data
		},
		body: 'id=' + id
	})
		.then(result => result.json())
		.then(result => {
			if (result.retCode == 'Success') {
				document.querySelector('#notice_' + result.id).remove() //id값으로 element 찾아서 제거
			} else if (result.retCode == 'Fail') {
				alert('처리 중 오류.')
			}
		})
		.catch(err => console.log(err))
}

//리스트 출력.
fetch('noticeListAjax.do')
	.then((result) => result.json()) // stream -> object.
	.then((data) => {
		console.log(data);
		const parentEl = document.getElementById('show');
		//table형식.
		makeTable(data, parentEl);
		//삭제버튼 이벤트.
		let trs = document.querySelectorAll('#show tbody tr');
		for (const trElem of trs) {
			trElem.addEventListener('click',showModal)
			trElem.setAttribute('id', 'notice_' + trElem.firstChild.innerText)
			//삭제버튼
			trElem.querySelector('td:nth-child(6)>button').addEventListener('click', delAjaxFnc)
		}
	})
	.catch(function(err) {
		console.log(err);
	})
	
document.addEventListener('DOMContentLoaded', function() {
	//저장 버튼 이벤트.
	document.querySelector('form[name=ajaxFrm]').addEventListener('submit', saveNoticeFnc);
	//수정 버튼 이벤트.
	document.querySelector('#id01 form.modal-content.animate').addEventListener('submit',changeNoticeFnc)
})



function saveNoticeFnc(e) {
	e.preventDefault(); //form에 있는 이벤트가 작동하지 않게 해주고 saveNoticeFnc()이벤트만 작동하게해준다.
	console.log('submit')
	let writer = document.getElementById('writer').value;
	let title = document.getElementById('title').value
	let subject = document.getElementById('subject').value
	
	if(!writer || !title || !subject){
		alert('값을 입력해주세요')
		return;
	}
	
	let frm = document.querySelector('form[name=ajaxFrm]')
	let formData = new FormData(frm);
	
	fetch('noticeAddAjax.do',{
		method: 'post',
		body: formData
	})
	.then(result => result.json())
	.then(result => {
		let tr = makeTr(result)
		document.querySelector('#show tbody').prepend(tr)
	})
	.catch(err => console.log(err))
	
}

function changeNoticeFnc(e){
	e.preventDefault();
	let writer= document.querySelector('#id01 input[name="writer"]').value;
	let title = document.querySelector('#id01 input[name="title"]').value;
	let subject = document.querySelector('#id01 textarea[name="subject"]').value
	let noticeDate = document.querySelector('#id01 input[name="noticeDate"]').value;
	let id = document.querySelector('#id01 input[name="noticeId"]').value;
	fetch('noticeUpdateAjax.do',{
		method: 'post',
		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		body: 'id=' + id + '&title=' + title + '&date=' + noticeDate +'&writer=' + writer +'&subject'+subject
	})
	.then(result => result.json())
	.then(result => {
		console.log(result)
		
		//화면닫고 서버에서 가져온 json데이터를 활용해서 목록에서 값을 변경
		document.querySelector('#id01').style.display ='none';
		document.getElementById('notice_' + result.noticeId).replaceWith(makeTr(result));
		
	})
	.catch(err => console.log(err))
}

function showModal(){
	let id = this.getAttribute('id');
	id = id.substring(7)
	document.getElementById('id01').style.display = 'block'
	fetch("noticeSearchAjax.do?noticeId=" + id) //서버에 요청
	.then(result => result.json())
	.then(result =>{
		console.log(result)
		document.querySelector('#id01 input[name="noticeId"]').value=result.noticeId;//작성자정보
		document.querySelector('#id01 input[name="writer"]').value=result.noticeWriter;//작성자정보
		document.querySelector('#id01 input[name="title"]').value=result.noticeTitle;
		document.querySelector('#id01 input[name="noticeDate"]').value=result.noticeDate;
		document.querySelector('#id01 textarea[name="subject"]').value=result.noticeSubject;
		document.querySelector('#id01 img').setAttribute('src','attech/'+result.noticeFile)
	})
	.catch(err => console.log(err))
	
}

//변경 버튼 ajax 호출.


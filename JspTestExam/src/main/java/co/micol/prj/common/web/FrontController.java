package co.micol.prj.common.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.MainCommand;
import co.micol.prj.common.Command;
import co.micol.prj.member.command.AjaxMemberIdCheck;
import co.micol.prj.member.command.MemberDelete;
import co.micol.prj.member.command.MemberEdit;
import co.micol.prj.member.command.MemberJoin;
import co.micol.prj.member.command.MemberJoinForm;
import co.micol.prj.member.command.MemberList;
import co.micol.prj.member.command.MemberLogin;
import co.micol.prj.member.command.MemberLoginForm;
import co.micol.prj.member.command.MemberLogout;
import co.micol.prj.member.command.MemberUpdate;
import co.micol.prj.member.command.memberSelect;
import co.micol.prj.notice.command.NoticeAddAjax;
import co.micol.prj.notice.command.NoticeAjax;
import co.micol.prj.notice.command.NoticeDelAjax;
import co.micol.prj.notice.command.NoticeDelete;
import co.micol.prj.notice.command.NoticeEdit;
import co.micol.prj.notice.command.NoticeInsert;
import co.micol.prj.notice.command.NoticeInsertForm;
import co.micol.prj.notice.command.NoticeList;
import co.micol.prj.notice.command.NoticeListAjax;
import co.micol.prj.notice.command.NoticeSelect;
import co.micol.prj.notice.command.NoticeUpdate;
import co.micol.prj.notice.command.NoticeUpdateAjax;

/**
 * Servlet implementation class FrontController
 */
//@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HashMap<String,Command> map = new HashMap<String,Command>();
    
   
    public FrontController() {
        super();
     
    }

	
	public void init(ServletConfig config) throws ServletException {
		// 명령집단 map.put(K,V)
		map.put("/main.do", new MainCommand()); //처음실행하는 페이지
		map.put("/memberList.do", new MemberList()); //멤버목록 보기
		map.put("/memberJoinForm.do", new MemberJoinForm());
		map.put("/AjaxMemberIdCheck.do", new AjaxMemberIdCheck()); 
		map.put("/memberJoin.do", new MemberJoin());
		map.put("/memberLoginForm.do", new MemberLoginForm());
		map.put("/memberLogin.do", new MemberLogin());
		map.put("/memberLogout.do", new MemberLogout());
		map.put("/memberSelect.do", new memberSelect());
		map.put("/memberEdit.do", new MemberEdit()); //멤버 수정
		map.put("/memberDelete.do", new MemberDelete()); //멤버 삭제
		map.put("/memberUpdate.do", new MemberUpdate()); //멤버 수정
		map.put("/noticeInsertForm.do", new NoticeInsertForm()); //공지사항 등록 폼
		map.put("/noticeList.do", new NoticeList()); //공지사항 목록
		map.put("/noticeSelect.do", new NoticeSelect()); //공지사항 상세보기
		map.put("/noticeInsert.do", new NoticeInsert()); //공지사항 등록
		map.put("/noticeDelete.do", new NoticeDelete()); //작성글 삭제
		map.put("/noticeUpdate.do", new NoticeUpdate());
		map.put("/noticeEdit.do", new NoticeEdit());
		
		//ajax 연습용
		map.put("/noticeAjax.do", new NoticeAjax());
		map.put("/noticeListAjax.do", new NoticeListAjax());
		map.put("/noticeAddAjax.do", new NoticeAddAjax());
		map.put("/noticeDelAjax.do", new NoticeDelAjax());
		map.put("/noticeUpdateAjax.do", new NoticeUpdateAjax());
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청을 분석, 실행, 결과를 돌려주는곳
		//한글 깨짐 방지
		//여기가 이해가 잘안간다!!!!!!!!!!!!!!!!!!!
		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String page = uri.substring(contextPath.length());
		
		Command command = map.get(page);
		String viewPage = command.exec(request, response);
		
		if(!viewPage.endsWith(".do")) {
			
			if(viewPage.startsWith("Ajax:")) {
				//ajax
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().print(viewPage.substring(5));
				return;
			}else if(!viewPage.endsWith(".tiles")){
				viewPage = "WEB-INF/views/" + viewPage + ".jsp"; //jsp 파일들을 실행 하는 경로 	
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}else {
			response.sendRedirect(viewPage);
		}
		//view Resolve end
	}

}

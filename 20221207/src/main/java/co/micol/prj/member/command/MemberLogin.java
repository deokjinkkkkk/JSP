package co.micol.prj.member.command;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.micol.prj.common.AES256Util;
import co.micol.prj.common.Command;
import co.micol.prj.member.service.MemberService;
import co.micol.prj.member.service.MemberVO;
import co.micol.prj.member.service.impl.MemberServiceImpl;

public class MemberLogin implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		//로그인 처리
		//member DAO에 갔다오기
		MemberService dao = new MemberServiceImpl();
		MemberVO vo = new MemberVO();
		HttpSession session = request.getSession(); //서버가 만들어 보관하고 있는 세션객체를 호출
		
		String password = request.getParameter("memberPassword");
			
			try {
				AES256Util aes = new AES256Util();
				try {
					password = aes.encrypt(password);
				}catch ( NoSuchAlgorithmException e){
					e.printStackTrace();
				}catch(GeneralSecurityException e ){
					e.printStackTrace();
				}
				
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			}
		String message = null;
		//VO로 값을 받아온다.
		vo.setMemberId(request.getParameter("memberId"));
		vo.setMemberPassword(password);
		//vo에 값을 담아둔다.
		vo = dao.memberSelect(vo);
		if(vo != null) { 
			//id에 vo가 가지고있는 memberId를 담아준다.
			session.setAttribute("id", vo.getMemberId());
			session.setAttribute("author", vo.getMemberAuthor());
			session.setAttribute("name", vo.getMemberName());
			
			message =vo.getMemberName() + "님 환영합니다.";
			//성공하면 환영합니다.라는 message 값이 담긴다
			request.setAttribute("message", message);
			//존재하면 member에 값이 담긴다.
//			request.setAttribute("member", vo);
		}else {
			message = "아이디 또는 패스워드가 틀립니다.";
			request.setAttribute("message", message);
			
		}
		return "member/memberLogin.tiles";
	}

}

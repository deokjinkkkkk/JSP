package co.micol.prj.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.micol.prj.common.Command;

public class MemberLogout implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		//로그아웃 처리(세션을 없에면 된다.
		HttpSession session = request.getSession();
		String message = (String) session.getAttribute("name"); //object로 담겨있는걸 String으로 변환.
		message += "님 정상적으로 로그아웃 처리되었습니다.";
		session.invalidate(); //세션을 통째로 없에 버린다.
		
		request.setAttribute("message", message);
		return "member/memberLogin.tiles";
	}

}

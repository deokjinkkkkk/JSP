package co.micol.prj.notice.command;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.micol.prj.common.Command;
import co.micol.prj.notice.service.NoticeService;
import co.micol.prj.notice.service.NoticeVO;
import co.micol.prj.notice.serviceImpl.NoticeServiceImpl;

public class NoticeUpdateAjax implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		// 파라미터 다 읽어드리고 update 변수. => json 반환.
		NoticeVO vo = new NoticeVO();
		vo.setNoticeId(Integer.parseInt(request.getParameter("id")));
		vo.setNoticeWriter(request.getParameter("writer"));
		vo.setNoticeTitle(request.getParameter("title"));
		vo.setNoticeDate(Date.valueOf(request.getParameter("date")));
		vo.setNoticeSubject(request.getParameter("subject"));
		
		NoticeService service = new NoticeServiceImpl();
		int cnt = service.noticeUpdate(vo);		
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		if(cnt > 0) {
			vo = service.noticeSelect(vo); // 수정처리 후 ajax 응답에 id와 관련된 모든 정보를 넘겨줌.
			try {
				json = mapper.writeValueAsString(vo);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return "Ajax:" + json;
		}
		
		
		
		return "Ajax:" + "{\"retCode\":\"Fail\"}";
	}

}

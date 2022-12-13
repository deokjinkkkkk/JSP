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

public class NoticeAddAjax implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		
		NoticeVO vo = new NoticeVO();
		vo.setNoticeWriter(request.getParameter("writer"));
		vo.setNoticeTitle(request.getParameter("title"));
		vo.setNoticeSubject(request.getParameter("subject"));
		vo.setNoticeDate(Date.valueOf(request.getParameter("noticeDate")));
		
		NoticeService service = new NoticeServiceImpl();
		service.noticeInsert(vo);
		
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(vo);
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		
		//{"retCode" : "OK"}
		return "Ajax:" + json;
	}

}

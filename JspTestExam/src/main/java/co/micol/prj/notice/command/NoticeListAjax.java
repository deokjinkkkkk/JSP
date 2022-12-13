package co.micol.prj.notice.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.micol.prj.common.Command;
import co.micol.prj.notice.service.NoticeService;
import co.micol.prj.notice.service.NoticeVO;
import co.micol.prj.notice.serviceImpl.NoticeServiceImpl;

public class NoticeListAjax implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		// "{"name":"홍길동", "age": 20} : json포맷.
 		NoticeService service = new NoticeServiceImpl();
 		List<NoticeVO> list = service.noticeSelectList();
 		ObjectMapper mapper = new ObjectMapper();
 		
 		String json = null;
		try {
			json = mapper.writeValueAsString(list);// list => json string.
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "Ajax:" +json;
	}

}

package co.micol.prj.notice.command;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import co.micol.prj.common.Command;
import co.micol.prj.notice.service.NoticeService;
import co.micol.prj.notice.service.NoticeVO;
import co.micol.prj.notice.serviceImpl.NoticeServiceImpl;

public class NoticeAddAjax implements Command {
	public boolean isMultiRequest(HttpServletRequest request) {
		String contentType = request.getHeader("Content-Type");
		System.out.println(contentType);
		if(contentType != null && contentType.indexOf("multipart/form-data") != -1) {
			return true;
		}
		return false;
	}
	
	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		NoticeVO vo = new NoticeVO();
		
		if(isMultiRequest(request)) {
			//multipart 요청이면
			NoticeService dao = new NoticeServiceImpl();
			
			String saveDir = request.getServletContext().getRealPath("/attech/");
			int maxSize = 1024*1024*1024; //최대 10M까지 업로드 
			String json = null;
			try {
				MultipartRequest multi = new MultipartRequest( //파일을 업로드시 request객체를 대체한다.
							request, saveDir , maxSize, "utf-8",
						new DefaultFileRenamePolicy());
				
				vo.setNoticeWriter(multi.getParameter("noticeWriter"));
				vo.setNoticeDate(Date.valueOf(multi.getParameter("noticeDate")));
				vo.setNoticeTitle(multi.getParameter("noticeTitle"));
				vo.setNoticeSubject(multi.getParameter("noticeSubject"));
				
				String ofileName = multi.getOriginalFileName("nfile"); //원본이름
				String pfileName = multi.getFilesystemName("nfile"); //실제저장되는 이름
				
				if(ofileName != "") {
					vo.setNoticeFile(ofileName);
					pfileName = saveDir + pfileName; //저장directory 와 저장명
					vo.setNoticeFileDir(pfileName);
				}
				
				int n = dao.noticeInsert(vo);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else {
			//multipart요청이 아닌경우
			vo.setNoticeWriter(request.getParameter("writer"));
			vo.setNoticeTitle(request.getParameter("title"));
			vo.setNoticeSubject(request.getParameter("subject"));
			vo.setNoticeDate(Date.valueOf(request.getParameter("noticeDate")));
			
			NoticeService service = new NoticeServiceImpl();
			service.noticeInsert(vo);
		}//e
		
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

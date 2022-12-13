package co.micol.prj.notice.command;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import co.micol.prj.common.Command;
import co.micol.prj.notice.service.NoticeService;
import co.micol.prj.notice.service.NoticeVO;
import co.micol.prj.notice.serviceImpl.NoticeServiceImpl;

public class NoticeInsert implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		// 공지사항 글등록 하기 MutipartRequest를 이용해야한다.(cos.jar)
		
		NoticeService dao = new NoticeServiceImpl();
		NoticeVO vo = new NoticeVO();
		
		String realPath = request.getServletContext().getRealPath("/");
		String saveDir =realPath + "/" + "attech" + "/";
		int maxSize = 1024*1024*1024; //최대 10M까지 업로드 
		
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
			if(n != 0) {
				request.setAttribute("message", "공지사항이 등록되었습니다.");
			} else {
				request.setAttribute("message", "공지사항이 등록실패했습니다.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "notice/noticeMessage.tiles";
	}

}

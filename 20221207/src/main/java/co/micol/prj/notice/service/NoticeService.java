package co.micol.prj.notice.service;

import java.util.List;

public interface NoticeService {
	List<NoticeVO> noticeSelectList();
	List<NoticeVO> noticeSelect(NoticeVO vo); //첨부파일이 여러개 있을수 있기때문에 리스트로 받음
	
	int noticeInsert(NoticeVO vo); //게시글 저장
	int noticeUpdate(NoticeVO vo); //게시글 수정
	int noticeDelete(NoticeVO vo); //게시글 삭제
	
	int noticeAttechInsert(NoticeAttechVO vo);//첨부파일 저장
	int noticeAttechDelete(NoticeAttechVO vo); //첨부파일 삭제
	
	List<NoticeVO> noticeSearchList(String key, String val); //게시글 내 검색을 위해 
	
}

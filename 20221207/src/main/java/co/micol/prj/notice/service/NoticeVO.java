package co.micol.prj.notice.service;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeVO {
	private int noticeId;
	private String noticeWriter;
	private Date noticeDater;
	private String noticeTitle;
	private String noticeSubject;
	private int noticeHit;
	
	 //join을 위한 확장
	private int attechId;
	private String noticeFile;
	private String noticeFileDir;
}

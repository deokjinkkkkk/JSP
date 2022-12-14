package co.micol.prj.notice.service;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeVO {
	private int noticeId;
	private String noticeWriter;
	@JsonFormat(pattern="yyyy-MM-dd", timezone="Asia/Seoul")
	private Date noticeDate;
	private String noticeTitle;
	private String noticeSubject;
	private int noticeHit;
	
	 //join을 위한 확장
	private int attechId;
	private String noticeFile;
	private String noticeFileDir;
}

package com.study.security_junhyeong.web.dto.notice;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddNoticeReqDto {
	private String noticeTitle;
	private int userCode;
	private String ir1; //콘텐츠 내용
	private List<MultipartFile> file; //스프링에서 지원해주는 파일을 받을 수 있는 인터페이스
}

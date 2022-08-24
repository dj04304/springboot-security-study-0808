package com.study.security_junhyeong.service.notice;

import com.study.security_junhyeong.web.dto.notice.AddNoticeReqDto;
import com.study.security_junhyeong.web.dto.notice.GetNoticeResponseDto;

public interface NoticeService {
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception;
	public  GetNoticeResponseDto getNotice(String flag, int noticeCode) throws Exception;
}

package com.study.security_junhyeong.service.notice;

import com.study.security_junhyeong.web.dto.notice.AddNoticeReqDto;

public interface NoticeService {
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception;
}

package com.study.security_junhyeong.domain.notice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.study.security_junhyeong.web.dto.notice.GetNoticeListResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notice {
	private int notice_code;
	private String notice_title;
	private int user_code;
	private String user_id;
	private String notice_content;
	private int notice_count;
	private int file_code;
	private String file_name;
	private LocalDateTime create_date;
	
	//xml에서 notice의 전체 갯수를 받는 역할(총 게시글이 있어야 메인에서 번호대로 나눌 수 있다)
	private int total_notice_count;
	
	public GetNoticeListResponseDto toListDto() {
		return GetNoticeListResponseDto.builder()
						.noticeCode(notice_code)
						.noticeTitle(notice_title)
						.userId(user_id)
						.noticeCount(notice_count)
						.createDate(create_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
						.totalNoticeCount(total_notice_count)
						.build();
	}
}

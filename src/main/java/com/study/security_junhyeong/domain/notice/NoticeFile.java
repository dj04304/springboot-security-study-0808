package com.study.security_junhyeong.domain.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoticeFile {
	private int file_code;
	private int notice_code;
	private String file_name;
}

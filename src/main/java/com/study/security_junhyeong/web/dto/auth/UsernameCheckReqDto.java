package com.study.security_junhyeong.web.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UsernameCheckReqDto {
	
	@NotBlank
	@Size(max = 16, min = 4, message = "4에서 16사이에요 바꿔주세요")
	private String username;
}

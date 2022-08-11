package com.study.security_junhyeong.service.auth;

import com.study.security_junhyeong.web.dto.auth.UsernameCheckReqDto;

public interface AuthService {
	public boolean checkUsername(UsernameCheckReqDto usernameCheckReqDto) throws Exception;
	public boolean signup();
}

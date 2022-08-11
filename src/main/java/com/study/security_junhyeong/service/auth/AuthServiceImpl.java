package com.study.security_junhyeong.service.auth;

import org.springframework.stereotype.Service;

import com.study.security_junhyeong.domain.user.UserRepository;
import com.study.security_junhyeong.web.dto.auth.UsernameCheckReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

	private final UserRepository userRepository;
	
	@Override
	public boolean checkUsername(UsernameCheckReqDto usernameCheckReqDto) throws Exception{
		
		return userRepository.findUserByUsername(usernameCheckReqDto.getUsername()) == null;
	}

	@Override
	public boolean signup() {
		return false;
	}
	
}

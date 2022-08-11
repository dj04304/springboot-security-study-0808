package com.study.security_junhyeong.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.security_junhyeong.domain.user.User;
import com.study.security_junhyeong.domain.user.UserRepository;
import com.study.security_junhyeong.web.dto.auth.SignupReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = null;
		
		try {
			 userEntity = userRepository.findUserByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(username); //아이디를 못찾았으니 exception을 보내준다.
		}
		
		//System.out.println("username: " + username);
		
		if(userEntity == null) {
			throw new UsernameNotFoundException(username + "사용자 이름은 사용할 수 없습니다.");
		}
		
			return new PrincipalDetails(userEntity);
		
	}

	public boolean addUser(SignupReqDto signupReqDto) throws Exception{
		
		return userRepository.save(signupReqDto.toEntity()) > 0;
	}
	
}

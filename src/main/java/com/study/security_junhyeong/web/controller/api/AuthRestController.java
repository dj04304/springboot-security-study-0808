package com.study.security_junhyeong.web.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.security_junhyeong.handler.aop.annotation.Log;
import com.study.security_junhyeong.handler.aop.annotation.Timer;
import com.study.security_junhyeong.handler.aop.annotation.ValidCheck;
import com.study.security_junhyeong.handler.exception.CustomValidationApiException;
import com.study.security_junhyeong.service.auth.AuthService;
import com.study.security_junhyeong.service.auth.PrincipalDetailsService;
import com.study.security_junhyeong.web.dto.CMRespDto;
import com.study.security_junhyeong.web.dto.auth.SignupReqDto;
import com.study.security_junhyeong.web.dto.auth.UsernameCheckReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {

	private final PrincipalDetailsService principalDetailsService;
	private final AuthService authService;
	
	//@Valid 를 쓸때는 BindingResult 는 항상 뒤에 따라와줘야 한다.
	@Timer
	@Log
	@ValidCheck
	@GetMapping("/signup/validation/username")
	public ResponseEntity<?> checkUsername(@Valid UsernameCheckReqDto usernameCheckReqDto, BindingResult bindingResult) { 
	
		//위 if절에 걸리지 않으면 
		boolean status = false;
		try {
			status = authService.checkUsername(usernameCheckReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "서버 오류", status));
		}
		
		return ResponseEntity.ok(new CMRespDto<>(1, "회원가입 가능여부", status));
	}
	
	@Log
	@ValidCheck
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody @Valid SignupReqDto signupReqDto, BindingResult bindingResult) {
		boolean status = false;
		
		try {
			status = principalDetailsService.addUser(signupReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "회원가입 실패", status));
			
		}
		
		return ResponseEntity.ok(new CMRespDto<>(1, "회원가입 성공", status));
	}
	
}

package com.study.security_junhyeong.web.dto.auth;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.study.security_junhyeong.domain.user.User;

import lombok.Data;

/*
 *  ^ -> 문자열의 시작
 *  $ -> 문자열의 끝
 *  ? -> 변수라고 생각하면된다. 임의의 값
 *  * -> 문자들이 무수히 많을 수 있다. 다 포함할 수 있다.
 *  \d -> 숫자를 포함할 수 있다.
 *   - -> ~ 앞에 젤 먼저 써준다.
 *  소괄호는 묶음이다.
 *  중괄호는 사이즈 이다.
 *  대괄호는 문자들의 집합을 의미한다. 
 */

@Data
public class SignupReqDto {
	@NotBlank
	@Pattern(regexp = "^[가-힇]*$", message = "한글만 입력 가능합니다.") //가 ~ 힇 까지의 한글만 허용 / 
	private String name;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,12}$")//첫글자는 무조건 영문이어야 한다. a ~ z, A ~ Z, 0 ~ 9 까지 가능하며, 크기는 4자이상 12자 이하이다.
	private String username;
	
	//  영어와 특수문자와 숫자를 함께 쓸 수 있으며, 8자이상 16자 이하이다.
	@NotBlank
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[-~!@#$%^&*_+=])[a-zA-Z\\d-~!@#$%^&*_+=]{8,16}$")
	private String password;
	
	@AssertTrue(message = "아이디 중복 확인이 되지 않았습니다.") // true 인가? 아니면 오류 true가 아니면 아이디가 중복이다.
	private boolean checkUsernameFlag;
	
	public User toEntity() {
		return User.builder()
				.user_name(name)
				.user_email(email)
				.user_id(username)
				.user_password(new BCryptPasswordEncoder().encode(password))
				.user_roles("ROLE_USER")
				.build();
	}
}

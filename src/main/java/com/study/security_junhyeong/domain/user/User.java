package com.study.security_junhyeong.domain.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	private int user_code;
	private String user_name;
	private String user_email;
	private String user_id;
	private String oauth2_id;
	private String user_password;
	private String user_roles; 				//ROLE_USER, ROLE_ADMIN, ROLE_MANAGER
	private String user_provider;
	private String user_profile_img;
	private String user_address;
	private String user_phone;
	private int user_gender;
	
	public List<String> getUserRoles() {
		
		if(user_roles == null || user_roles.isBlank()) { //isBlank() -> 비어있다.
			return new ArrayList<String>();
		}
		return Arrays.asList(user_roles.replaceAll(" ", "").split(",")); //쉼표단위로 끊어서 배열을 만들어준다. 그 배열을 list로 만들겠다. // replaceAll -> 공백 제거
		
	}
}

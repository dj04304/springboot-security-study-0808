package com.study.security_junhyeong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.filter.CorsFilter;

import com.study.security_junhyeong.config.auth.AuthFailureHandler;
import com.study.security_junhyeong.service.auth.PrincipalOauth2UserService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity // 기존의 WebSecurityConfigurerAdapter를 비활성화 시키고, 현재 Security 설정을 따르겠다.
@Configuration // IoC에 등록
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter { //SecurityConfig 는 스프링 부트 서버의 설정이다.
	
	private final CorsFilter corsFilter;
	private final PrincipalOauth2UserService principalOauth2UserService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() { //BCryptPasswordEncoder 는 암호화이다.
		return new BCryptPasswordEncoder();
	}
	
	
	@Override // super.configure(http); ->  기존 security 방법 //http 는 builder형식임
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // csrf 를 사용하는 것을 권장하지만, 지금은 사용하면 문제가 생긴다. input 마다 다 지급을 해줘야 하기 때문에
		http.headers().frameOptions().disable().addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS", "ALLOW-FROM"+ "/**"));
		http.addFilter(corsFilter); //cors 인증을 하지 않겠다는 의미
		http.authorizeRequests() // 요청이 들어왔을때의 인증 세팅
				.antMatchers("/api/v1/grant/test/user/**")
				.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				
				.antMatchers("/api/v1/grant/test/manager/**")
				.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				
				.antMatchers("/api/v1/grant/test/admin/**")
				.access("hasRole('ROLE_ADMIN')")
				
				.antMatchers("/", "/index", "/mypage/**") //우리가 지정한 요청
				.authenticated() 					// 우리가 지정한 요청은 인증을 거쳐라
				
				.anyRequest() 						// 다른 모든 요청들에게는 
				.permitAll()							// 모든 접근 권한(permitAll)을 부여해라(인증을 거칠 필요가 없다.)
				
				.and() //여기까지 한 묶음
				
				.formLogin() // 로그인 방법 3가지 중 하나 (form로그인, 베이직 로그인, JWT)
				.loginPage("/auth/signin") // 로그인 페이지는 해당 get요청을 통해 접근한다.
				.loginProcessingUrl("/auth/signin") // 로그인 요청(post요청)
				.failureHandler(new AuthFailureHandler()) //직접 커스텀한 handler 를 넣어준다.
				.and()
				
				.oauth2Login() //oauth2로그인 사용 및 이후 나오는 하위항목
				.userInfoEndpoint()
				/*
				 * 1. google, naver, kakao 로그인 요청 -> 코드를 발급해줌.
				 * 2. 발급받은 코드를 가진 상태로 권한요청(토큰 발급요청)을 함.
				 * 3. 스코프에 등록된 프로필 정보를 가져올 수 있게 된다.
				 * 4. 해당 정보를 시큐리티의 객체로 전달받음.
				 */
				.userService(principalOauth2UserService) //전달받은 객체를 principalOauth2UserService에 던져주면, loadUser가 실행된다.
				
				.and()
				.failureHandler(null) //로그인 실패
				.defaultSuccessUrl("/index"); //처음 로그인 페이지로 들어가서 로그인을 했을 때, 보내주는 경로, 단 예를들어 mypage로 들어가서 로그인을 할 경우, mypage로 보내준다.
				//즉 처음 시작한 페이지가 로그인 이외의 페이지라면, 그 곳으로 보내주지만, login 페이지부터 시작할 경우 defaultSuccessUrl에서 정해준 경로로 보내준다.

	}
	
}

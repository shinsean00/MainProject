package com.mainproject.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // CSRF protection을 비활성화. AJAX를 사용할 때 필요한 설정
            .authorizeRequests()
            	.antMatchers("/css/**", "/js/**", "/img/**").permitAll() // CSS, JS, 이미지 파일에 대한 요청을 허용
            	.antMatchers("/login.do", "/join.do").permitAll() // 회원가입, 로그인 페이지 허용
                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_PRIVACY_ADMIN") // /admin은 관리자만 가능하도록 설정
                .antMatchers("/privacy-admin/**").hasAuthority("ROLE_PRIVACY_ADMIN") // /privacy_admin은 개인정보 관리자만 가능하도록 설정
                .antMatchers("/mypage/**").authenticated() // /my는 인증이 되어야 접속 가능
                .antMatchers("/event/listEvents.do").authenticated() // 일정 목록은 인증이 되어야 접속 가능
                .antMatchers("/event/adminlistEvents.do").authenticated() 
                .anyRequest().permitAll() 
            .and()

            

            .formLogin() // form 을 통한 login 활성화
            	.loginPage("/user/login.do") // 로그인 페이지 URL 설정, 미설정 시 Default 로그인 화면
            	// .loginProcessingUrl("/api/login") // 로그인 처리할 URL (왜인지 모르겠음 작동안함;)
            	.defaultSuccessUrl("/index.do") // 로그인 성공시 기본 URL
            	.failureForwardUrl("/fail") // 로그인 실패 URL 설정
            .and()
            
	        .sessionManagement()
	            .invalidSessionUrl("/session-expired") // 세션 만료시
	        .and()

            	.logout()
            	.logoutUrl("/api/logout") // 로그아웃 URL 설정
            	.logoutSuccessUrl("/index.do") // 로그아웃 성공 시 이동할 URL 지정
                .invalidateHttpSession(true)
                .clearAuthentication(true)
            .and()
            .exceptionHandling()
        		.accessDeniedPage("/error/403"); // 접근 권한 없음 : error 403 페이지 
        return http.build();  
    }
    
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호를 안전하게 암호화하기 위해 BCryptPasswordEncoder 사용
    }





	@Bean
	public SimpleUrlLogoutSuccessHandler logoutSuccessHandler() {
	    SimpleUrlLogoutSuccessHandler handler = new SimpleUrlLogoutSuccessHandler();
	    handler.setDefaultTargetUrl("/index.do");
	    return handler;
	}
	

}


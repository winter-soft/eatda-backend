package com.proceed.swhackathon.config.security;

import com.proceed.swhackathon.config.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Bean //해당 메서드의 리턴되는 오브젝트를 IoC로 등록해 준다
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()// WebMvcConfig에서 이미 설정했으므로 기본 cors 설정
                .and()
                    .csrf() // csrf는 현재 사용하지 않으므로 disable
                    .disable()
                    .httpBasic() // token을 사용하므로 basic 인증 disable
                    .disable()
                    .sessionManagement() // session 기반이 아님을 선언
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests() // "/", "/auth/**" 경로는 인증 안해도 됨
                    .antMatchers("/**").permitAll()
                    .anyRequest() // "/" , "/auth/**" 이외의 모든 경로는 인증 해야함
                    .authenticated();
//                .and()
//                    .oauth2Login()
//                    .loginPage("/naver") //구글로그인 완료후 후처리가 필요함 엑세스토큰 + 사용자프로필정보
//                    .userInfoEndpoint()
//                    .userService(principalOauth2UserService);
        // filter 등록
        // 매 요청마다
        // CorsFilter 실행한 후에
        // jwtAuthenticationFilter 실행한다.
        http.addFilterAfter(
                jwtAuthenticationFilter,
                CorsFilter.class
        );
    }

}

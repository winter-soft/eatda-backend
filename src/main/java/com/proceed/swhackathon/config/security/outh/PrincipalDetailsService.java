package com.proceed.swhackathon.config.security.outh;

import com.proceed.swhackathon.model.User;
import com.proceed.swhackathon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepository;

    //시큐리티 session 에 들어갈수 있는 것을 Authentication 타입 => 안에 UserDetails 타입이 들어가야 한다
    //함수 종료시 @AuthenticationPrincipal 만들어 진다
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);

        if(userEntity != null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}

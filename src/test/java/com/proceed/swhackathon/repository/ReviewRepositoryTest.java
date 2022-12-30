//package com.proceed.swhackathon.repository;
//
//import com.proceed.swhackathon.config.security.jwt.TokenProvider;
//import com.proceed.swhackathon.model.Review;
//import com.proceed.swhackathon.model.Role;
//import com.proceed.swhackathon.model.User;
//import io.jsonwebtoken.Claims;
//import org.aspectj.lang.annotation.Before;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpServletRequest;
//
//import java.util.Date;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class ReviewRepositoryTest {
//
//    @Autowired UserRepository userRepository;
//    @Autowired ReviewRepository reviewRepository;
//    @Autowired TokenProvider tokenProvider;
//
//    private String token;
//    private User user;
//
//    @PostConstruct
//    public void authentication(HttpServletRequest request){
//        user = User.builder()
//                .username("ghdcksgml")
//                .platformType("KAKAO")
//                .platformId("12344")
//                .email("ghdcksgml2@naver.com")
//                .phoneNumber("010-4696-4574")
//                .role(Role.USER)
//                .build();
//        token = tokenProvider.create(user);
//        userRepository.save(user);
//    }
//
//    @Test
//    public void createdByTest() throws Exception {
//        // given
//        Review review = Review.builder()
//                .imageUrl(null)
//                .star(5)
//                .content("맛있네요")
//                .build();
//
//        // when
//        Review result = reviewRepository.save(review);
//
//        //then
//        assertThat(result.getCreatedBy()).isEqualTo(user.getId());
//    }
//}
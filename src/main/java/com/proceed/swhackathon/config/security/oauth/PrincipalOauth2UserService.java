//package com.proceed.swhackathon.config.security.oauth;
//
//import com.proceed.swhackathon.config.security.oauth.provider.KakaoUserInfo;
//import com.proceed.swhackathon.config.security.oauth.provider.NaverUserInfo;
//import com.proceed.swhackathon.config.security.oauth.provider.OAuth2UserInfo;
//import com.proceed.swhackathon.config.security.oauth.provider.OAuth2UserInfo1;
//import com.proceed.swhackathon.config.security.outh.PrincipalDetails;
//import com.proceed.swhackathon.model.Role;
//import com.proceed.swhackathon.model.User;
//import com.proceed.swhackathon.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service
//public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    //구글로 부터 받은 userRequest 데이터에 대한 후처리 되는 함수
//    //함수 종료시 @AuthenticationPrincipal 만들어 진다
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        System.out.println("ClientRegistration \n" + userRequest.getClientRegistration()); //registrationId 를 통해 어떤 oauth 로 로그인했는지 확인 가능
//        System.out.println("AccessToken \n" + userRequest.getAccessToken());
//
//        OAuth2User oauth2User = super.loadUser(userRequest);
//        //구글로그인 버튼 -> 로그인 완료 -> code 리턴(OAuth-client 라이브러리) -> Access Token 요청
//        //userRequest 정보 -> loadUser 함수호출 -> 회원프로필 받음
//        System.out.println("Attributes \n" + oauth2User.getAttributes());
//
//        OAuth2UserInfo oAuth2UserInfo = null;
//        OAuth2UserInfo1 oAuth2UserInfo1 = null;
//
//        if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
//            oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
//        } else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
//            oAuth2UserInfo = new KakaoUserInfo((Map)oauth2User.getAttributes().get("kakao_account"));
//            oAuth2UserInfo1 = new KakaoUserInfo((Map)oauth2User.getAttributes().get("properties"));
//        }
//
//        String provider = oAuth2UserInfo.getProvider();
//
//        String providerName = null;
//        if (provider.equals("naver")){
//            providerName= oAuth2UserInfo.getName();
//        }else{
//            providerName= oAuth2UserInfo1.getName();
//        }
//        //String providerName= oAuth2UserInfo1.getName();
//        String username = provider + "_" + providerName; //google_118394802562392465097
//        String password = bCryptPasswordEncoder.encode("서지현");
//        String email = oAuth2UserInfo.getEmail();
//        //String role = "USER";
//
//        User userEntity = userRepository.findByUsername(username);
//
//        if(userEntity == null){
//            userEntity = User.builder()
//                    .username(username)
//                    .password(password)
//                    .email(email)
//                    .role(Role.USER)
//                    .build();
//            userRepository.save(userEntity);
//        }
//        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
//    }
//}
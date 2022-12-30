package com.proceed.swhackathon.config.security.jwt;

import com.proceed.swhackathon.exception.SwhackathonException;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.exception.user.UserTokenExpiredException;
import com.proceed.swhackathon.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.proceed.swhackathon.model.User;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    @Value("${jwt.tokenProvider.secretKey}")
    private String SECRET_KEY;

    public String create(User user){
        // 기한은 지금부터 3시간으로 설정
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(3, ChronoUnit.HOURS));
        log.info("token expiryDate : {}", expiryDate);

        /*
        { // header
            "alg":"HS512"
        },
        { // payload
            "sub":userEntity.getId(),
            "iss":"demo app",
            "iat":현재 날짜,
            "exp":만료기한
        }
        */

        return Jwts.builder()
                // header에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                // payload에 들어갈 내용
                .setSubject(user.getId()) // sub
                .claim("ROLE", user.getRole().name())
                .setIssuer("eat-da") // iss
                .setIssuedAt(new Date()) // iat
                .setExpiration(expiryDate) // exp
                .compact();
    }

    public Claims validateAndGetUserId(String token){
        // parseClaimsJws 메서드가 Base64로 디코딩 및 파싱
        // 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
        // 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
        // 그중 우리는 userId가 필요하므로 getBody를 부른다.
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims; // 사용자의 아이디 리턴
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return true;
        }catch (Exception e){
            return false;
        }
    }
}

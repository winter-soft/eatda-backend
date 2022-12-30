package com.proceed.swhackathon.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.model.Role;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            // 요청에서 토큰 가져오기
            String token = parseBearerToken(request);
            log.info("Filter is running...");

            // 토큰 검사하기. JWT이므로 인가 서버에 요청하지 않고도 검증 가능
            if(token != null && !token.equalsIgnoreCase("null")){
                // userId 가져오기. 위조된 경우 예외 처리된다.
                Claims claims = tokenProvider.validateAndGetUserId(token);
                String userId = claims.getSubject();
                String userRole = (String)claims.get("ROLE");
                Date expiration = claims.getExpiration();
                log.info("Authenticated user ID : "+userId);
                log.info("Authenticated expiration : "+expiration);

                // 인증 완료. SecurityContextHolder에 등록해야 인증된 사용자라고 생각한다.
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, // 인증된 사용자의 정보. 문자열이 아니어도 아무것이나 넣을 수 있다. 보통 UserDetails 오브젝트를 넣음
                        null,
                        AuthorityUtils.createAuthorityList(userRole));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 비어있는 SecurityContext 생성
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                // SecurityContext에 authentication을 추가
                securityContext.setAuthentication(authentication);
                // SecurityContextHolder에 SecurityContext 설정
                SecurityContextHolder.setContext(securityContext);
            }

            filterChain.doFilter(request,response);
        }catch (ExpiredJwtException e){
            logger.error("Could not set user authentication in security context {}" , e);
            jwtExceptionHandler(response, Message.JWT_TOKEN_EXPIRED);
        }catch (UnsupportedJwtException e){
            logger.error("Could not set user authentication in security context {}" , e);
            jwtExceptionHandler(response, Message.JWT_UNSUPPORTED);
        }catch (MalformedJwtException e){
            logger.error("Could not set user authentication in security context {}" , e);
            jwtExceptionHandler(response, Message.JWT_MALFORMED);
        }catch (SignatureException e){
            logger.error("Could not set user authentication in security context {}" , e);
            jwtExceptionHandler(response, Message.JWT_SIGNATURE);
        }catch (IllegalArgumentException e){
            logger.error("Could not set user authentication in security context {}" , e);
            jwtExceptionHandler(response, Message.JWT_ILLEGAL_ARGUMENT);
        }

    }

    private String parseBearerToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    private void jwtExceptionHandler(HttpServletResponse response, String message) throws IOException{
        ResponseDTO<String> dto = new ResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), message);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getWriter(), dto);

    }
}

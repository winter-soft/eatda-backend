package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.config.security.jwt.TokenProvider;
import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.user.UserDTO;
import com.proceed.swhackathon.dto.user.UserLoginDTO;
import com.proceed.swhackathon.dto.user.UserRegisterDTO;
import com.proceed.swhackathon.dto.user.UserTokenDTO;
import com.proceed.swhackathon.exception.IllegalArgumentException;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.model.Role;
import com.proceed.swhackathon.model.User;
import com.proceed.swhackathon.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping(value = "/signup")
    @ApiOperation(value = "회원가입", notes = "UserDTO 객체로 받아 유저를 등록합니다.")
    public ResponseDTO<?> registerUser(@RequestBody UserRegisterDTO userDTO){
        try {
            User user = User.builder()
                    .platformType(userDTO.getPlatformType())
                    .platformId(userDTO.getPlatformId())
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .profileImageUrl(userDTO.getProfileImageUrl())
                    .role(Role.USER)
                    .phoneNumber(userDTO.getPhoneNumber())
                    .build();

            User registerUser = userService.create(user);
            UserDTO dto = userService.getByCredentials(registerUser.getPlatformType(), registerUser.getPlatformId());
            Map<String, String> map = new HashMap<>();
            map.put("token", dto.getToken());
            return new ResponseDTO<>(HttpStatus.OK.value(), map);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException();
        }
    }

    @PostMapping("/signin")
    @ApiOperation(value = "로그인", notes = "UserDTO 객체로 email과 password를 받아 로그인을 진행하고, token을 리턴합니다.")
    public ResponseDTO<?> authenticate(@RequestBody UserLoginDTO userDTO){
        UserDTO user = userService.getByCredentials(userDTO.getPlatformType(), userDTO.getPlatformId());
        Map<String, String> map = new HashMap<>();
        map.put("token", user.getToken());
        return new ResponseDTO<>(HttpStatus.OK.value(), map);
    }

    @GetMapping("/infor")
    @ApiOperation(value = "유저 정보", notes = "token을 입력받아 유저 정보를 리턴한다.")
    public ResponseDTO<?> userInformation(@AuthenticationPrincipal String userId){
        return new ResponseDTO<>(HttpStatus.OK.value(), userService.findById(userId));
    }

    @PostMapping("/refresh")
    @ApiOperation(value = "토큰 재생성", notes = "token을 받아 만약 만료된 토큰일 경우 재발급해준다. 만료 기한이 남아있으면 입력한 token값 리턴")
    public ResponseDTO<?> refreshToken(@RequestBody UserTokenDTO token){
        if(tokenProvider.isValidToken(token.getToken())) { // 토큰이 유효하다면 보낸 token 값을 리턴한다.
            return new ResponseDTO<>(HttpStatus.OK.value(), token);
        }else{ // 존재하지 않을 경우 해당 토큰이 가장 최근에 재발급받은 토큰이였을 경우, 재발급을 해주고 그렇지 않은경우는 에러를 발생시킨다.
            return new ResponseDTO<>(HttpStatus.OK.value(), userService.refreshToken(token.getToken()));
        }
    }
}

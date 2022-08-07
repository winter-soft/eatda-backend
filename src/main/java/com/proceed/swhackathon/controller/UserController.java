package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.config.security.jwt.TokenProvider;
import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.user.UserDTO;
import com.proceed.swhackathon.dto.user.UserLoginDTO;
import com.proceed.swhackathon.dto.user.UserRegisterDTO;
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
public class UserController {

    private final UserService userService;

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
}

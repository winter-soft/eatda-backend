package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.config.security.jwt.TokenProvider;
import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.UserDTO;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.model.Role;
import com.proceed.swhackathon.model.User;
import com.proceed.swhackathon.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.service.MediaTypes;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping(value = "/signup")
    @ApiOperation(value = "회원가입", notes = "UserDTO 객체로 받아 유저를 등록합니다.")
    public ResponseDTO<?> registerUser(@RequestBody UserDTO userDTO){
        try {
            User user = User.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .password(encoder.encode(userDTO.getPassword()))
                    .role(Role.USER)
                    .build();

            User registerUser = userService.create(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .email(registerUser.getEmail())
                    .username(registerUser.getUsername())
                    .role(registerUser.getRole())
                    .id(registerUser.getId())
                    .build();

            return new ResponseDTO<>(HttpStatus.OK.value(), responseUserDTO);
        }catch (IllegalArgumentException e){
            return new ResponseDTO<>(500, e.getMessage());
        }
    }

    @PostMapping("/signin")
    @ApiOperation(value = "로그인", notes = "UserDTO 객체로 email과 password를 받아 로그인을 진행하고, token을 리턴합니다.")
    public ResponseDTO<?> authenticate(@RequestBody UserDTO userDTO){
        User user = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword(), encoder);

        if(user != null){
            final String token = tokenProvider.create(user);
            final UserDTO responseUserDTO = UserDTO.builder()
                    .token(token)
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .role(user.getRole())
                    .id(user.getId())
                    .build();
            return new ResponseDTO<>(HttpStatus.OK.value(), responseUserDTO);
        }else{
            throw new UserNotFoundException();
        }
    }
}

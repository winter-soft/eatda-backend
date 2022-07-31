package com.proceed.swhackathon.dto;

import com.proceed.swhackathon.model.Role;
import com.proceed.swhackathon.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String token;
    private String id;
    private String email;
    private String username;
    private String password;
    private Role role = Role.USER;

    public static UserDTO entityToDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}

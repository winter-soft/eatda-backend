package com.proceed.swhackathon.dto;

import com.proceed.swhackathon.model.Role;
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
    private Role role;
}

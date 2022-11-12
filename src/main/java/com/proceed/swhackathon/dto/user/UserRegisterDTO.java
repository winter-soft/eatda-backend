package com.proceed.swhackathon.dto.user;

import com.proceed.swhackathon.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    private String platformType;
    private String platformId;
    private String email;
    private String username;
    private String profileImageUrl;
    private String phoneNumber;
    private Role role = Role.USER;
}

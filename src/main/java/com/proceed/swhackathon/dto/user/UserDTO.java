package com.proceed.swhackathon.dto.user;

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
    private String platformType;
    private String platformId;
    private String email;
    private String profileImageUrl;
    private String username;
    private String phoneNumber;
    private Role role = Role.USER;

    public static UserDTO entityToDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .platformType(user.getPlatformType())
                .platformId(user.getPlatformId())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .username(user.getUsername())
                .role(user.getRole())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}

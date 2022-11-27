package com.proceed.swhackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proceed.swhackathon.exception.user.UserUnAuthorizedException;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User extends TimeZone {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="user_id")
    private String id;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    private String platformType;

    @Column(nullable = false)
    @JsonIgnore
    private String platformId;

    @Column(nullable = false)
    private String email;

    private String profileImageUrl;

    @Setter
    @JsonIgnore
    private String token;

    @JsonIgnore
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonIgnore
    private Role role;

    // 사장인지 체크
    public void isBoss(){
        if(this.role == Role.USER)
            throw new UserUnAuthorizedException();
    }
}

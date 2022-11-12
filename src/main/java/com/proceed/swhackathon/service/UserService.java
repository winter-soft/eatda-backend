package com.proceed.swhackathon.service;

import com.proceed.swhackathon.config.security.jwt.TokenProvider;
import com.proceed.swhackathon.dto.user.UserDTO;
import com.proceed.swhackathon.exception.user.UserDuplicatedException;
import com.proceed.swhackathon.exception.user.UserInformationEmptyException;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.exception.user.UserUnAuthorizedException;
import com.proceed.swhackathon.model.Role;
import com.proceed.swhackathon.model.User;
import com.proceed.swhackathon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.proceed.swhackathon.dto.user.UserDTO.entityToDTO;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    public User create(final User userEntity){
        if(userEntity == null || userEntity.getPlatformId() == null || userEntity.getPhoneNumber() == null){
            throw new UserInformationEmptyException();
        }

        if(userRepository.existsByPlatformTypeAndPlatformIdAndPhoneNumber(
                userEntity.getPlatformType(),
                userEntity.getPlatformId(),
                userEntity.getPhoneNumber())){
            throw new UserDuplicatedException(); // 유저 중복 Exception
        }

        return userRepository.save(userEntity);
    }

    public UserDTO findById(final String userId){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        return entityToDTO(user);
    }

    public UserDTO getByCredentials(final String platformType, final String platformId){
        final User originalUser = userRepository.findByPlatformIdAndAndPlatformType(platformId, platformType)
                .orElseThrow(()->{
                    throw new UserNotFoundException();
                });
        UserDTO userDTO = UserDTO.entityToDTO(originalUser);
        final String token = tokenProvider.create(originalUser);
        userDTO.setToken(token);
        return userDTO;
    }

    // 사장인지 체크
    public static void isBoss(User user){
        if(user.getRole() != Role.BOSS)
            throw new UserUnAuthorizedException();
    }
}

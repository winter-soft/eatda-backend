package com.proceed.swhackathon.service;

import com.proceed.swhackathon.exception.user.UserDuplicatedException;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.model.User;
import com.proceed.swhackathon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User create(final User userEntity){
        if(userEntity == null || userEntity.getEmail()==null){
            throw new UserNotFoundException();
        }
        final String email = userEntity.getEmail();
        if(userRepository.existsByEmail(email)){
            log.warn("Email already exists {}",email);
            throw new UserDuplicatedException();
        }

        return userRepository.save(userEntity);
    }

    public User getByCredentials(final String email, final String password,final BCryptPasswordEncoder encoder){
        final User originalUser = userRepository.findByEmail(email);

        if(originalUser!=null && encoder.matches(password,originalUser.getPassword())) {
            return originalUser;
        }
        return null;
    }
}

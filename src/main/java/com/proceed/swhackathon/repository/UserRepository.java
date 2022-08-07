package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);
    @Override
    Optional<User> findById(String s);

    Boolean existsByEmail(String email);

    public User findByUsername(String username);

    Boolean existsByPlatformTypeAndPlatformId(String platformType, String platformId);

    Optional<User> findByPlatformIdAndAndPlatformType(String platformId, String platformType);
}

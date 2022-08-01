package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.Likes;
import com.proceed.swhackathon.model.Store;
import com.proceed.swhackathon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Likes findByStoreAndUser(Store store, User user);
}

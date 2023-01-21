package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

}

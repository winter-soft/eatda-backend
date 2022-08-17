package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select m from Menu m join fetch m.store s where m.id = :id")
    Optional<Menu> findByMenuIdWithStore(Long id);
}

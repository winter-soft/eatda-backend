package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Override
    Optional<Store> findById(Long id);

    @Query("select s from Store s join fetch s.menus m where m.store.id = :id")
    Optional<Store> findByIdWithMenus(Long id);
}

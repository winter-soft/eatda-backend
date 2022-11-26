package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.Menu;
import com.proceed.swhackathon.model.MenuConnect;
import com.proceed.swhackathon.model.MenuOptionTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuConnectRepository extends JpaRepository<MenuConnect, Integer> {

    @Query("select mc.menuOptionTitle from MenuConnect mc where mc.menu = :menu")
    List<MenuOptionTitle> findByMenu(Menu menu);
}

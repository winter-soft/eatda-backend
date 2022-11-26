package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.MenuOption;
import com.proceed.swhackathon.model.MenuOptionTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {

//    @Query("select m from MenuOption m where m = :menu_id")
//    public List<MenuOption> findAllByMenu_id(Long menu_id);

    public List<MenuOption> findAllByMenuOptionTitle(MenuOptionTitle menuOptionTitle);
}

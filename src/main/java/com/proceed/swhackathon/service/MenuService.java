package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.menu.MenuDTO;
import com.proceed.swhackathon.dto.menu.MenuInsertDTO;
import com.proceed.swhackathon.dto.menu.MenuUpdateDTO;
import com.proceed.swhackathon.dto.store.StoreDTO;
import com.proceed.swhackathon.exception.IllegalArgumentException;
import com.proceed.swhackathon.exception.menu.MenuNotFoundException;
import com.proceed.swhackathon.exception.store.StoreNotFoundException;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.model.*;
import com.proceed.swhackathon.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuConnectRepository menuConnectRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ResponseDTO<?> selectMenu(Long menuId){

        Map<String, Object> m = new HashMap<>();

        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            throw new MenuNotFoundException();
        });

        List<MenuOptionTitle> motList = menuConnectRepository.findByMenu(menu);
        for(MenuOptionTitle mot : motList) {
            mot.setMenuOptionList(menuOptionRepository.findAllByMenuOptionTitle(mot));
        }

        m.put("menu", MenuDTO.entityToDTO(menu));
        m.put("menuOptionTitle", motList);

        return new ResponseDTO<>(HttpStatus.OK.value(), m);
    }

    @Transactional
    public StoreDTO addMenu(String userId, MenuInsertDTO menuDTO){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        // 사장인지 체크
        user.isBoss();

        Store store = storeRepository.findByUser(userId).orElseThrow(() -> {
            throw new StoreNotFoundException();
        });

        Menu menu = menuDTO.dtoToEntity();
        menu.setStore(store);

        menuRepository.save(menu);

        return StoreDTO.entityToDTO(store);
    }

    @Transactional
    public StoreDTO updateMenu(String userId, Long menuId, MenuUpdateDTO menuDTO){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        // 사장인지 체크
        user.isBoss();

        Store store = storeRepository.findByUser(userId).orElseThrow(() -> {
            throw new StoreNotFoundException();
        });

        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            throw new MenuNotFoundException();
        });

        if(store != menu.getStore()){
            throw new IllegalArgumentException();
        }

        menu.setName(menuDTO.getName());
        menu.setPrice(menuDTO.getPrice());
        menu.setImageUrl(menuDTO.getImageUrl());

        return StoreDTO.entityToDTO(store);
    }

    @Transactional
    public String deleteMenu(String userId, Long menuId){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        // 사장인지 체크
        user.isBoss();

        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            throw new MenuNotFoundException();
        });

        menu.getStore().deleteMenu(menu);
        orderDetailRepository.findByMenu(menu)
                .stream().forEach(od -> od.setMenu(null));

        try {
            menuRepository.delete(menu);
        }catch (java.lang.IllegalArgumentException e){
            throw new IllegalArgumentException();
        }

        return "메뉴가 삭제되었습니다.";
    }
}

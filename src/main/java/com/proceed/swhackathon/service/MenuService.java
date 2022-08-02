package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.menu.MenuDTO;
import com.proceed.swhackathon.dto.menu.MenuInsertDTO;
import com.proceed.swhackathon.dto.menu.MenuUpdateDTO;
import com.proceed.swhackathon.dto.store.StoreDTO;
import com.proceed.swhackathon.exception.IllegalArgumentException;
import com.proceed.swhackathon.exception.menu.MenuNotFoundException;
import com.proceed.swhackathon.exception.store.StoreNotFoundException;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.model.Menu;
import com.proceed.swhackathon.model.Store;
import com.proceed.swhackathon.model.User;
import com.proceed.swhackathon.repository.MenuRepository;
import com.proceed.swhackathon.repository.OrderDetailRepository;
import com.proceed.swhackathon.repository.StoreRepository;
import com.proceed.swhackathon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.proceed.swhackathon.service.UserService.isBoss;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OrderDetailRepository orderDetailRepository;

    public MenuDTO selectMenu(Long menuId){
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            throw new MenuNotFoundException();
        });
        return MenuDTO.entityToDTO(menu);
    }

    @Transactional
    public StoreDTO addMenu(String userId, Long storeId, MenuInsertDTO menuDTO){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        // 사장인지 체크
        isBoss(user);

        Store store = storeRepository.findById(storeId).orElseThrow(() -> {
            throw new StoreNotFoundException();
        });

        Menu menu = menuDTO.dtoToEntity();
        menu.setStore(store);

        menuRepository.save(menu);

        return StoreDTO.entityToDTO(store);
    }

    @Transactional
    public StoreDTO updateMenu(String userId, Long storeId, Long menuId, MenuUpdateDTO menuDTO){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        // 사장인지 체크
        isBoss(user);

        Store store = storeRepository.findById(storeId).orElseThrow(() -> {
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
        isBoss(user);

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

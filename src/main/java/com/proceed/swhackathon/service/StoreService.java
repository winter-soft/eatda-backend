package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.menu.MenuDTO;
import com.proceed.swhackathon.dto.store.*;
import com.proceed.swhackathon.dto.userOrderDetail.UserOrderDetailDTO;
import com.proceed.swhackathon.exception.IllegalArgumentException;
import com.proceed.swhackathon.exception.order.OrderNotFoundException;
import com.proceed.swhackathon.exception.store.StoreNotFoundException;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.model.*;
import com.proceed.swhackathon.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.proceed.swhackathon.service.UserService.isBoss;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final UserRepository userRepository;
    private final DestinationRepository destinationRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserOrderDetailRepository userOrderDetailRepository;

    // 손봐야함
    public StoreDetailDTO storeDetail(Long orderId){
        Order order = orderRepository.findOrderByIdWithStore(orderId).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });
        Store store = storeRepository.findByIdWithMenus(order.getStore().getId()).orElseThrow(()->{
            throw new StoreNotFoundException();
        });
        List<UserOrderDetailDTO> uods = userOrderDetailRepository.findByOrderAll(order)
                .stream()
                .map(UserOrderDetailDTO::entityToDTO)
                .collect(Collectors.toList());


        List<MenuDTO> menuDTOList = MenuDTO.entityToDTO(store.getMenus());

        StoreDTO storeDTO = StoreDTO.entityToDTO(store);
        storeDTO.setMenus(menuDTOList);

        StoreDetailDTO storeDetailDTO = StoreDetailDTO.entityToDTO(order);
        storeDetailDTO.setStore(storeDTO);
        storeDetailDTO.setOrderDetails(uods);

        return storeDetailDTO;
    }

    @Transactional
    public StoreDTO insert(String userId, StoreInsertDTO storeDTO){

        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        isBoss(user); // 사장인지 체크

        Store store = Store.builder()
                .name(storeDTO.getName())
                .minOrderPrice(storeDTO.getMinOrderPrice())
                .backgroundImageUrl(storeDTO.getBackgroundImageUrl())
                .category(storeDTO.getCategory())
                .infor(storeDTO.getInfor())
                .user(user)
                .build();

        try {
            return StoreDTO.entityToDTO(storeRepository.save(store));
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException();
        }
    }

    public Page<StoreDTO> selectAll(Pageable pageable){
        return storeRepository
                .findAll((org.springframework.data.domain.Pageable) pageable)
                .map(StoreDTO::entityToDTO);
    }

    public List<StoreDTO> selectCategory(CategoryDTO categoryDTO){
        return storeRepository.findByCategory(categoryDTO.getCategory())
                .stream().map(StoreDTO::entityToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public StoreDTO update(String userId, StoreInsertDTO storeDTO){
        // 사장인지 체크
        isBoss(userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        }));

        Store store = storeRepository.findByUser(userId).orElseThrow(() -> {
            throw new StoreNotFoundException();
        });

        store.setName(storeDTO.getName());
        store.setMinOrderPrice(storeDTO.getMinOrderPrice());
        store.setBackgroundImageUrl(storeDTO.getBackgroundImageUrl());
        store.setCategory(storeDTO.getCategory());
        store.setInfor(storeDTO.getInfor());

        return StoreDTO.entityToDTO(store);
    }

    @Transactional
    public String delete(String userId){
        try {
            Store store = storeRepository.findByUser(userId).orElseThrow(() -> {
                throw new StoreNotFoundException();
            });

            String name = store.getName();
            store.removeMenu();
            for(Order o : orderRepository.findByStore(store)){ o.removeStore(); }
            storeRepository.deleteById(store.getId());

            return name;
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException();
        }
    }

    public List<StoreSearchResponseDTO> search(StoreSearchReqeustDTO ssrDTO){
        return storeRepository.findByNameContainingIgnoreCase(ssrDTO.getKeyword())
                .stream()
                .map(StoreSearchResponseDTO::entityToDTO)
                .collect(Collectors.toList());
    }

}

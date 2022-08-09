package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.menu.MenuDTO;
import com.proceed.swhackathon.dto.store.StoreDTO;
import com.proceed.swhackathon.dto.store.StoreDetailDTO;
import com.proceed.swhackathon.dto.store.StoreInsertDTO;
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

    // dummy data
    @Transactional
    public void initialize(){
        Destination d1 = Destination.builder()
                .name("단국대학교 기숙사 진리관 로비")
                .build();
        Store s1 = Store.builder()
                .name("단대앞분식")
                .minOrderPrice(12000)
                .backgroundImageUrl("https://naver.com/")
                .category(Category.HAMBURGER)
                .infor("안녕하세요. 오전 10시부터 오후 5시까지 운영합니다.")
                .build();
        Menu m1 = Menu.builder()
                .name("엄마 손맛 떡볶이")
                .price(3500)
                .store(s1)
                .imageUrl("https://daum.net/")
                .build();
        Menu m2 = Menu.builder()
                .name("오징어 튀김")
                .price(3500)
                .store(s1)
                .imageUrl("https://daum.net/")
                .build();
        Menu m3 = Menu.builder()
                .name("잔치국수")
                .price(3500)
                .store(s1)
                .imageUrl("https://daum.net/")
                .build();

        Order o1 = Order.builder()
                .orderStatus(OrderStatus.WAITING)
                .currentAmount(30000)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .store(s1)
                .destination(d1)
                .build();
        OrderDetail od1 = OrderDetail.builder()
                .quantity(10)
                .menu(m3)
                .menuCheck(true)
                .build();
        od1.calTotalPrice();

        s1.addMenu(m1);
        s1.addMenu(m2);
        s1.addMenu(m3);

        destinationRepository.save(d1);
        storeRepository.save(s1);
        orderRepository.save(o1);
        orderDetailRepository.save(od1);
    }

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

}

package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.userOrderDetail.BossUserOrderDetailDTO;
import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.order.OrderNotFoundException;
import com.proceed.swhackathon.exception.store.StoreNotFoundException;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.model.*;
import com.proceed.swhackathon.repository.OrderRepository;
import com.proceed.swhackathon.repository.StoreRepository;
import com.proceed.swhackathon.repository.UserOrderDetailRepository;
import com.proceed.swhackathon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BossService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final UserOrderDetailRepository userOrderDetailRepository;

    public ResponseDTO<?> selectBossOrder(String userId, Long orderId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("{}", Message.USER_NOT_FOUND);
            throw new UserNotFoundException();
        });

        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            log.warn("{}", Message.ORDER_NOT_FOUND );
            throw new OrderNotFoundException();
        });

        user.isBoss(); // 권한 체크

        List<UserOrderDetail> uods = userOrderDetailRepository.findAllByOrderWithOrderDetail(order);

        // 데이터 전처리
        Map<String, Object> m = new HashMap<>();

        int sumPrice = 0;
        int num = 0;
        String message = "";
        for(UserOrderDetail uod : uods) {
            num++;
            message += "[주문번호 " + num + "] " + uod.getUser().getUsername() + ": " + uod.getUser().getPhoneNumber() + "\n";
            List<BossUserOrderDetailDTO> buod = new ArrayList<>();
            for(OrderDetail od : uod.getOrderDetails()){
                BossUserOrderDetailDTO b = BossUserOrderDetailDTO.builder()
                        .menuName(od.getMenu().getName())
                        .quantity(od.getQuantity())
                        .totalPrice(od.getTotalPrice())
                        .orderDetailOptions(od.getOrderDetailOptions())
                        .build();
                message += b.getMenuName();
                message += " " + b.getQuantity() + "개 " + b.getTotalPrice() + "\n";
                for(OrderDetailOption odp : b.getOrderDetailOptions()) {
                    message += " -" + odp.getMenuOption().getOptionName() + "\n";
                }
                buod.add(b);
            }
            message += "\n";
            sumPrice += uod.getTotalPrice();
            m.put(String.valueOf(num), buod);
        }

        m.put("sumPrice", sumPrice);
        m.put("message", message);

       return new ResponseDTO<>(HttpStatus.OK.value(), m);
    }

    public ResponseDTO<?> selectDateOrder(String userId, String date) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("{}", Message.USER_NOT_FOUND);
            throw new UserNotFoundException();
        });

        user.isBoss();

        List<Order> orders;

        if(user.getRole().equals(Role.ADMIN)){
            orders = orderRepository.findAllByEndTime(date);
        }else {
            Store store = storeRepository.findByUser(userId).orElseThrow(() -> {
                log.warn("{}", Message.STORE_NOT_FOUND);
                throw new StoreNotFoundException();
            });

            orders = orderRepository.findAllByEndTimeAndStore(date, store.getId());
        }
        return new ResponseDTO<>(HttpStatus.OK.value(), orders);
    }
}

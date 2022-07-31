package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.OrderDTO;
import com.proceed.swhackathon.dto.OrderInsertDTO;
import com.proceed.swhackathon.exception.IllegalArgumentException;
import com.proceed.swhackathon.exception.destination.DestinationNotFoundException;
import com.proceed.swhackathon.exception.order.OrderNotFoundException;
import com.proceed.swhackathon.exception.store.StoreNotFoundException;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.exception.user.UserUnAuthorizedException;
import com.proceed.swhackathon.model.*;
import com.proceed.swhackathon.repository.DestinationRepository;
import com.proceed.swhackathon.repository.OrderRepository;
import com.proceed.swhackathon.repository.StoreRepository;
import com.proceed.swhackathon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final DestinationRepository destinationRepository;

    @Transactional
    public OrderDTO insert(OrderInsertDTO orderDTO, Long storeId){
        Store store = storeRepository.findById(storeId).orElseThrow(() -> {
            throw new StoreNotFoundException();
        });
        Order order = orderDTO.dtoToEntity();
        order.setDestination(destinationRepository
                .findById(orderDTO.getId())
                .orElseThrow(()->{
            throw new DestinationNotFoundException();
        }));
        order.setStore(store);
        return OrderDTO.entityToDTO(orderRepository.save(order));
    }

    @Transactional
    public OrderDTO updateStatus(String userId,
                                 Long orderId,
                                 OrderStatus orderStatus){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        if(user.getRole() != Role.BOSS){ // 사장이 아니라면 변경할 수 없다.
            throw new UserUnAuthorizedException();
        }

        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });

        // 주문상태 변경
        order.setOrderStatus(orderStatus);

        // Order -> OrderDTO
        return OrderDTO.entityToDTO(order);
    }
}

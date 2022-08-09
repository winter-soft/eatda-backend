package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.order.OrderDTO;
import com.proceed.swhackathon.dto.order.OrderInsertDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.proceed.swhackathon.service.UserService.isBoss;

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
                .findById(orderDTO.getDestination_id())
                .orElseThrow(()->{
            throw new DestinationNotFoundException();
        }));
        order.setStore(store);
        return OrderDTO.entityToDTO(orderRepository.save(order));
    }

    public OrderDTO select(Long orderId){
        return OrderDTO.entityToDTO(orderRepository.findById(orderId).orElseThrow(()->{
            throw new OrderNotFoundException();
        }));
    }

    public Page<OrderDTO> selectAll(Pageable pageable){
        return orderRepository.findAll(pageable).map(OrderDTO::entityToDTO);
    }

    @Transactional
    public OrderDTO updateStatus(String userId,
                                 Long orderId,
                                 OrderStatus orderStatus){
        // 사장인지 체크
        isBoss(userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        }));

        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });

        // 주문상태 변경
        order.setOrderStatus(orderStatus);

        // Order -> OrderDTO
        return OrderDTO.entityToDTO(order);
    }


}

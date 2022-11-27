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
import com.proceed.swhackathon.utils.LocalDateTimeFormatUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final DestinationRepository destinationRepository;

    @Transactional
    public OrderDTO insert(String userId, OrderInsertDTO orderDTO, Long storeId){

        // 사장인지 체크
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        user.isBoss();

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
        store.setRecentlyOrder(order);
        return OrderDTO.entityToDTO(orderRepository.save(order));
    }

    @Transactional
    public OrderDTO select(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });
        order.updateCurrentAmount();
        return OrderDTO.entityToDTO(order);
    }

    public Page<OrderDTO> selectAll(Pageable pageable){
        return orderRepository.findAll(pageable).map(OrderDTO::entityToDTO);
    }

    public List<OrderDTO> selectOrderByTimeIndex(Long timeIndex){
        LocalDateTime dealTime = LocalDateTimeFormatUtils.calcTime(timeIndex);
        String fmt_dealTime = LocalDateTimeFormatUtils.dateHour(dealTime);

        List<Order> orders = orderRepository.findAllByEndTime(fmt_dealTime);
        return orders.stream().map(OrderDTO::entityToDTO).collect(Collectors.toList());
    }

    public List<OrderDTO> selectOrderByDestinationAndTimeIndex(Long destinationId, Long timeIndex){
        // Destination 예외처리
        destinationRepository.findById(destinationId).orElseThrow(() -> {
            throw new DestinationNotFoundException();
        });

        // TimeIndex를 통해 인덱스에 맞는 날짜와 시간을 계산한다.
        LocalDateTime dealTime = LocalDateTimeFormatUtils.calcTime(timeIndex);
        String fmt_dealTime = LocalDateTimeFormatUtils.dateHour(dealTime);

        List<Order> orders = orderRepository.findAllByDestinationAndEndTime(destinationId, fmt_dealTime);
        return orders.stream().map(OrderDTO::entityToDTO).collect(Collectors.toList());
    }

    public Page<OrderDTO> selectAllOrderByOrderStatus(Pageable pageable, OrderStatus orderStatus){
        return orderRepository.findAllByOrderStatus(pageable, orderStatus).map(OrderDTO::entityToDTO);
    }

    public List<OrderDTO> selectRank(){
        return orderRepository.findByOrderStatusWithStoreOrderByCurrentAmountDesc(OrderStatus.WAITING)
                .stream().map(OrderDTO::entityToDTO)
                .collect(Collectors.toList());

    }

    @Transactional
    public OrderDTO updateStatus(String userId,
                                 Long orderId,
                                 OrderStatus orderStatus){
        // 사장인지 체크
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        user.isBoss();

        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });

        // 주문상태 변경
        order.setOrderStatus(orderStatus);

        // Order -> OrderDTO
        return OrderDTO.entityToDTO(order);
    }


}

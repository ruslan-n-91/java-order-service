package com.example.javaorderservice.service.impl;

import com.example.javaorderservice.controller.dto.request.CreateOrderRequestDto;
import com.example.javaorderservice.controller.dto.request.UpdateOrderRequestDto;
import com.example.javaorderservice.controller.dto.response.CreateOrderResponseDto;
import com.example.javaorderservice.controller.dto.response.GetOrderResponseDto;
import com.example.javaorderservice.controller.dto.response.UpdateOrderResponseDto;
import com.example.javaorderservice.controller.exceptionhandler.exception.OrderNotFoundException;
import com.example.javaorderservice.controller.mapper.OrderMapper;
import com.example.javaorderservice.model.Order;
import com.example.javaorderservice.repository.OrderRepository;
import com.example.javaorderservice.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<GetOrderResponseDto> findAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::mapToGetOrderResponseDto)
                .toList();
    }

    @Override
    public GetOrderResponseDto findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found by id " + orderId));
        return orderMapper.mapToGetOrderResponseDto(order);
    }

    @Override
    public CreateOrderResponseDto createOrder(CreateOrderRequestDto requestDto) {
        Order order = createNewOrder(requestDto);

        order = orderRepository.save(order);

        return orderMapper.mapToCreateOrderResponseDto(order);
    }

    @Override
    public UpdateOrderResponseDto updateOrder(Long orderId, UpdateOrderRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found by id " + orderId));

        updateOrderFields(order, requestDto);

        order = orderRepository.save(order);

        return orderMapper.mapToUpdateOrderResponseDto(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found by id " + orderId));

        orderRepository.delete(order);
    }

    private Order createNewOrder(CreateOrderRequestDto requestDto) {
        Order order = new Order();

        if (requestDto.getId() != null) {
            order.setId(requestDto.getId());
        }
        order.setOrderNumber(requestDto.getOrderNumber());
        order.setStatus(requestDto.getStatus());
        order.setCreatedAt(Instant.now());

        return order;
    }

    private void updateOrderFields(Order order, UpdateOrderRequestDto requestDto) {
        order.setOrderNumber(requestDto.getOrderNumber());
        order.setStatus(requestDto.getStatus());
    }
}

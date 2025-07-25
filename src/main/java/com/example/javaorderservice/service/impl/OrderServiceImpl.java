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
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

//    private final Counter orderCounter;
    private final Gauge activeOrdersGauge;
//    private final Timer orderProcessingTimer;

    private final AtomicInteger activeOrders = new AtomicInteger(0);

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, MeterRegistry registry) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;

        // Счётчик заказов
//        this.orderCounter = Counter.builder("orders.total")
//                .description("Total number of orders")
//                .tag("application", "java-order-service")
//                .register(registry);

        // Gauge активных заказов
        this.activeOrdersGauge = Gauge.builder("orders.active", activeOrders, AtomicInteger::get)
                .description("Currently active orders")
                .register(registry);

//        // Таймер обработки заказов
//        this.orderProcessingTimer = Timer.builder("orders.processing.time")
//                .description("Time taken to process order")
//                .register(registry);
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

    @Counted(value = "orders.created.count", description = "Total orders created")
    @Timed(value = "orders.processing.time", description = "Order processing time",
            histogram = true, percentiles = {0.95, 0.99})
    @Override
    public CreateOrderResponseDto createOrder(CreateOrderRequestDto requestDto) {
        Order order = createNewOrder(requestDto);

        order = orderRepository.save(order);

//        orderCounter.increment();
        activeOrders.getAndIncrement();

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

        activeOrders.getAndDecrement();

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

package com.example.javaorderservice.service;

import com.example.javaorderservice.controller.dto.request.CreateOrderRequestDto;
import com.example.javaorderservice.controller.dto.request.UpdateOrderRequestDto;
import com.example.javaorderservice.controller.dto.response.CreateOrderResponseDto;
import com.example.javaorderservice.controller.dto.response.GetOrderResponseDto;
import com.example.javaorderservice.controller.dto.response.UpdateOrderResponseDto;

import java.util.List;

public interface OrderService {
    List<GetOrderResponseDto> findAllOrders();

    GetOrderResponseDto findOrderById(Long orderId);

    CreateOrderResponseDto createOrder(CreateOrderRequestDto requestDto);

    UpdateOrderResponseDto updateOrder(Long orderId, UpdateOrderRequestDto requestDto);

    void deleteOrder(Long orderId);
}

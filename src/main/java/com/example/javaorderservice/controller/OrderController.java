package com.example.javaorderservice.controller;

import com.example.javaorderservice.controller.dto.request.CreateOrderRequestDto;
import com.example.javaorderservice.controller.dto.request.UpdateOrderRequestDto;
import com.example.javaorderservice.controller.dto.response.CreateOrderResponseDto;
import com.example.javaorderservice.controller.dto.response.GetOrderResponseDto;
import com.example.javaorderservice.controller.dto.response.UpdateOrderResponseDto;
import com.example.javaorderservice.openfeign.CreateProductRequestDto;
import com.example.javaorderservice.openfeign.CreateProductResponseDto;
import com.example.javaorderservice.openfeign.GetProductResponseDto;
import com.example.javaorderservice.openfeign.ProductServiceClient;
import com.example.javaorderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
//    private final ProductServiceApi productServiceApi;
    private final ProductServiceClient productServiceClient;

    @GetMapping()
    public ResponseEntity<List<GetOrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GetOrderResponseDto> getOrderById(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(orderService.findOrderById(orderId));
    }

    @PostMapping()
    public ResponseEntity<CreateOrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto requestDto) {
        return ResponseEntity.ok(orderService.createOrder(requestDto));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<UpdateOrderResponseDto> updateOrder(@PathVariable("orderId") Long orderId,
                                                              @RequestBody UpdateOrderRequestDto requestDto) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, requestDto));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/createProductOpenApi")
//    public ResponseEntity<CreateProductResponseDto> createProductOpenApi(@RequestBody CreateProductRequestDto requestDto) {
//        return ResponseEntity.ok(productServiceApi.createProduct(requestDto));
//    }

    @GetMapping("/getAllProductsOpenFeign")
    public ResponseEntity<List<GetProductResponseDto>> getAllProductsOpenFeign() {
        return productServiceClient.getAllProducts();
    }

    @GetMapping("/getProductByIdOpenFeign/{productId}")
    public ResponseEntity<GetProductResponseDto> getProductByIdOpenFeign(@PathVariable("productId") Long productId) {
        return productServiceClient.getProductById(productId);
    }

    @PostMapping("/createProductOpenFeign")
    public ResponseEntity<CreateProductResponseDto> createProductOpenFeign(@RequestBody CreateProductRequestDto requestDto) {
        return productServiceClient.createProduct(requestDto);
    }
}

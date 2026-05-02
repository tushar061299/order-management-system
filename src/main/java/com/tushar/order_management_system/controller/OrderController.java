package com.tushar.order_management_system.controller;

import com.tushar.order_management_system.dto.OrderRequestDto;
import com.tushar.order_management_system.dto.OrderResponseDto;
import com.tushar.order_management_system.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Security;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto){

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(orderService.createOrder(orderRequestDto,userEmail));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getMyOrders(){

        String userEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(orderService.getOrdersByUser(userEmail));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> findOrderById(@PathVariable Long id){
        String userEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(orderService.findOrderById(id,userEmail));
    }
}

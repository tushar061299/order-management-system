package com.tushar.order_management_system.service;

import com.tushar.order_management_system.dto.OrderRequestDto;
import com.tushar.order_management_system.dto.OrderResponseDto;
import com.tushar.order_management_system.entity.Order;
import com.tushar.order_management_system.entity.Product;
import com.tushar.order_management_system.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, String userEmail){

        Product product = productService.getProductEntityById(orderRequestDto.getProductId());

        if (orderRequestDto.getQuantity() <= 0) {
            throw new RuntimeException("Invalid quantity");
        }

        if (product.getStock() < orderRequestDto.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }
        Order order = modelMapper.map(orderRequestDto, Order.class);

        order.setId(null);
        order.setUserEmail(userEmail);
        order.setPrice(product.getPrice());
        order.setProductId(product.getId());
        order.setStatus("CREATED");
        order.setCreatedAt(new Date());

        product.setStock(product.getStock() - orderRequestDto.getQuantity());

        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDto(savedOrder.getId(),savedOrder.getStatus(),savedOrder.getPrice());
    }

    public List<OrderResponseDto> getOrdersByUser(String userEmail){

        List<Order> orders = orderRepository.findByUserEmail(userEmail);

        return orders.stream()
                .map(order -> new OrderResponseDto(
                        order.getId(),
                        order.getStatus(),
                        order.getPrice()
                )).toList();
    }

    public OrderResponseDto findOrderById(Long orderId, String userEmail){
        Order order =  orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(!order.getUserEmail().equals(userEmail)){
            throw new RuntimeException("Unauthorized access");
        }

        return new OrderResponseDto(
                order.getId(),
                order.getStatus(),
                order.getPrice()
        );
    }

}

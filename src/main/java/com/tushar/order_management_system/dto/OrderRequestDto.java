package com.tushar.order_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequestDto {

    public Long productId;
    public Integer quantity;
    public Double price;
}

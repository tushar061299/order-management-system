package com.tushar.order_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    public Long orderId;
    public String Status;
    public Double price;
}

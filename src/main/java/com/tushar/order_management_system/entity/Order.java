package com.tushar.order_management_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private Long productId;

    private Double price;

    private String Status;

    private Integer quantity;

    private Date createdAt;
}

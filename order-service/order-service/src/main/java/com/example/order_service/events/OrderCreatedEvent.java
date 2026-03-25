package com.example.order_service.events;


import lombok.Data;

@Data
public class OrderCreatedEvent {

    private Long orderId;

    private Long userId;

    private Double amount;


}

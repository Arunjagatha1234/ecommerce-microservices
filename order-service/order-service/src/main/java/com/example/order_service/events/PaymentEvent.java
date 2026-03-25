package com.example.order_service.events;

import lombok.Data;

@Data
public class PaymentEvent {

    private Long orderId;

    private String status;
}

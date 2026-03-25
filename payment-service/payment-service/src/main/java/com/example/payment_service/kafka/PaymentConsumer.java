package com.example.payment_service.kafka;

import com.example.payment_service.events.OrderCreatedEvent;
import com.example.payment_service.events.PaymentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public PaymentConsumer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order-created",groupId = "payment-group")
    public void processPayment(OrderCreatedEvent orderCreatedEvent){

        System.out.println("Processing Payment for the order: " + orderCreatedEvent.getOrderId());

        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setOrderId(orderCreatedEvent.getOrderId());
        paymentEvent.setStatus("SUCCESS");

        kafkaTemplate.send("payment-success",paymentEvent);

    }
}

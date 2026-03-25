package com.example.order_service.kafka;

import com.example.order_service.entity.Order;
import com.example.order_service.events.PaymentEvent;
import com.example.order_service.repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentConsumer {
    private final OrderRepository orderRepository;

    public PaymentConsumer(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "payment-success",groupId = "order-group")
    public void updateOrderStatus(PaymentEvent paymentEvent){

        Order order = orderRepository.findById(paymentEvent.getOrderId()).orElseThrow(()->new RuntimeException("Order Not Found"));

        order.setStatus("PAID");

        orderRepository.save(order);

        System.out.println("Order Status updated to paid");

    }
}

package com.example.order_service.controller;

import com.example.order_service.entity.Order;
import com.example.order_service.events.OrderCreatedEvent;
import com.example.order_service.kafka.OrderProducer;
import com.example.order_service.repository.OrderRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    private final OrderProducer orderProducer;

    public OrderController(OrderRepository orderRepository, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order){

        order.setStatus("CREATED");
        Order savedOrder = orderRepository.save(order);
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setOrderId(savedOrder.getId());
        orderCreatedEvent.setAmount(savedOrder.getAmount());
        orderCreatedEvent.setUserId(savedOrder.getUserId());

        orderProducer.sendOrderCreatedEvent(orderCreatedEvent);


        return savedOrder;

    }
}

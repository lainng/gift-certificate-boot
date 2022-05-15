package com.piatnitsa.controller;

import com.piatnitsa.entity.Order;
import com.piatnitsa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> ordersByUserId(@PathVariable long userId) {
        return orderService.getOrdersByUserId(userId);
    }
}

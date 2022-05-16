package com.piatnitsa.controller;

import com.piatnitsa.dto.OrderDto;
import com.piatnitsa.dto.converter.DtoConverter;
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
    private final DtoConverter<OrderDto, Order> orderDtoConverter;

    @Autowired
    public OrderController(OrderService orderService, DtoConverter<OrderDto, Order> orderDtoConverter) {
        this.orderService = orderService;
        this.orderDtoConverter = orderDtoConverter;
    }

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> ordersByUserId(@PathVariable long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody OrderDto orderDto) {
        return orderService.insert(orderDtoConverter.toEntity(orderDto));
    }
}

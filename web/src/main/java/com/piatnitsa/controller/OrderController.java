package com.piatnitsa.controller;

import com.piatnitsa.dto.OrderCreationDto;
import com.piatnitsa.dto.OrderDto;
import com.piatnitsa.dto.converter.DtoConverter;
import com.piatnitsa.entity.Order;
import com.piatnitsa.hateoas.LinkBuilder;
import com.piatnitsa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final DtoConverter<OrderDto, Order> orderDtoConverter;
    private final LinkBuilder<OrderDto> orderLinkBuilder;

    @Autowired
    public OrderController(OrderService orderService,
                           DtoConverter<OrderDto, Order> orderDtoConverter,
                           LinkBuilder<OrderDto> orderLinkBuilder) {
        this.orderService = orderService;
        this.orderDtoConverter = orderDtoConverter;
        this.orderLinkBuilder = orderLinkBuilder;
    }

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> ordersByUserId(@PathVariable long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return orders.stream()
                .map(orderDtoConverter::toDto)
                .peek(orderLinkBuilder::buildLinks)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody OrderCreationDto orderDto) {
        return orderService.insert(orderDtoConverter.toEntity(orderDto));
    }
}

package com.piatnitsa.controller;

import com.piatnitsa.dto.OrderCreationDto;
import com.piatnitsa.dto.OrderDto;
import com.piatnitsa.dto.converter.DtoConverter;
import com.piatnitsa.entity.Order;
import com.piatnitsa.hateoas.LinkBuilder;
import com.piatnitsa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final DtoConverter<OrderDto, Order> orderDtoConverter;
    private final LinkBuilder<OrderDto> orderLinkBuilder;
    private static final Class<OrderController> ORDER_CONTROLLER_CLASS = OrderController.class;

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
    public CollectionModel<OrderDto> ordersByUserId(
            @PathVariable long userId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size) {
        List<OrderDto> orders = orderService.getOrdersByUserId(userId, page, size).stream()
                .map(orderDtoConverter::toDto)
                .peek(orderLinkBuilder::buildLinks)
                .collect(Collectors.toList());
        Link link = linkTo(methodOn(ORDER_CONTROLLER_CLASS).ordersByUserId(userId, page, size)).withSelfRel();
        return CollectionModel.of(orders, link);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestBody OrderCreationDto orderCreationDto) {
        Order order = orderService.insert(orderDtoConverter.toEntity(orderCreationDto));
        OrderDto orderDto = orderDtoConverter.toDto(order);
        orderLinkBuilder.buildLinks(orderDto);
        return orderDto;
    }
}

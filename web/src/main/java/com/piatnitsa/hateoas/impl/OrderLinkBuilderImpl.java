package com.piatnitsa.hateoas.impl;

import com.piatnitsa.controller.OrderController;
import com.piatnitsa.dto.GiftCertificateDto;
import com.piatnitsa.dto.OrderDto;
import com.piatnitsa.hateoas.LinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderLinkBuilderImpl implements LinkBuilder<OrderDto> {
    private static final Class<OrderController> CONTROLLER_CLASS = OrderController.class;
    private final LinkBuilder<GiftCertificateDto> certificateDtoLinkBuilder;

    @Autowired
    public OrderLinkBuilderImpl(LinkBuilder<GiftCertificateDto> certificateDtoLinkBuilder) {
        this.certificateDtoLinkBuilder = certificateDtoLinkBuilder;
    }

    @Override
    public void buildLinks(OrderDto object) {
        object.add(linkTo(methodOn(CONTROLLER_CLASS).ordersByUserId(object.getUser().getId())).withSelfRel());
        certificateDtoLinkBuilder.buildLinks(object.getCertificate());
    }
}

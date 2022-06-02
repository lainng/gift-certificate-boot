package com.piatnitsa.hateoas.impl;

import com.piatnitsa.dto.GiftCertificateDto;
import com.piatnitsa.dto.OrderDto;
import com.piatnitsa.dto.UserDto;
import com.piatnitsa.hateoas.LinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderLinkBuilderImpl implements LinkBuilder<OrderDto> {
    private final LinkBuilder<GiftCertificateDto> certificateDtoLinkBuilder;
    private final LinkBuilder<UserDto> userLinkBuilder;

    @Autowired
    public OrderLinkBuilderImpl(LinkBuilder<GiftCertificateDto> certificateDtoLinkBuilder,
                                LinkBuilder<UserDto> userLinkBuilder) {
        this.certificateDtoLinkBuilder = certificateDtoLinkBuilder;
        this.userLinkBuilder = userLinkBuilder;
    }

    @Override
    public void buildLinks(OrderDto object) {
        certificateDtoLinkBuilder.buildLinks(object.getCertificate());
        userLinkBuilder.buildLinks(object.getUser());
    }
}

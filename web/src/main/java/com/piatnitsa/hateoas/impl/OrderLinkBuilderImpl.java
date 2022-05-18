package com.piatnitsa.hateoas.impl;

import com.piatnitsa.dto.GiftCertificateDto;
import com.piatnitsa.dto.OrderDto;
import com.piatnitsa.hateoas.LinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderLinkBuilderImpl implements LinkBuilder<OrderDto> {
    private final LinkBuilder<GiftCertificateDto> certificateDtoLinkBuilder;

    @Autowired
    public OrderLinkBuilderImpl(LinkBuilder<GiftCertificateDto> certificateDtoLinkBuilder) {
        this.certificateDtoLinkBuilder = certificateDtoLinkBuilder;
    }

    @Override
    public void buildLinks(OrderDto object) {
        certificateDtoLinkBuilder.buildLinks(object.getCertificate());
    }
}

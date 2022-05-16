package com.piatnitsa.dto.converter.impl;

import com.piatnitsa.dto.OrderDto;
import com.piatnitsa.dto.converter.DtoConverter;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Order;
import com.piatnitsa.entity.User;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoConverterImpl implements DtoConverter<OrderDto, Order> {

    @Override
    public Order toEntity(OrderDto object) {
        Order entity = new Order();

        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(object.getGiftCertificateId());
        entity.setCertificate(certificate);

        User user = new User();
        user.setId(object.getUserId());
        entity.setUser(user);

        return entity;
    }

    @Override
    public OrderDto toDto(Order entity) {
        return null;
    }
}

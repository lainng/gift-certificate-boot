package com.piatnitsa.dto.converter.impl;

import com.piatnitsa.dto.GiftCertificateDto;
import com.piatnitsa.dto.OrderCreationDto;
import com.piatnitsa.dto.OrderDto;
import com.piatnitsa.dto.UserDto;
import com.piatnitsa.dto.converter.DtoConverter;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Order;
import com.piatnitsa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoConverterImpl implements DtoConverter<OrderDto, Order> {
    private final DtoConverter<GiftCertificateDto, GiftCertificate> certificateDtoConverter;
    private final DtoConverter<UserDto, User> userDtoConverter;

    @Autowired
    public OrderDtoConverterImpl(DtoConverter<GiftCertificateDto, GiftCertificate> certificateDtoConverter,
                                 DtoConverter<UserDto, User> userDtoConverter) {
        this.certificateDtoConverter = certificateDtoConverter;
        this.userDtoConverter = userDtoConverter;
    }

    @Override
    public Order toEntity(OrderDto dto) {
        Order entity = new Order();
        OrderCreationDto creationDto = (OrderCreationDto) dto;

        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(creationDto.getGiftCertificateId());
        entity.setCertificate(certificate);

        User user = new User();
        user.setId(creationDto.getUserId());
        entity.setUser(user);

        return entity;
    }

    @Override
    public OrderDto toDto(Order entity) {
        OrderDto dto = new OrderDto();

        dto.setId(entity.getId());
        dto.setCost(entity.getCost());
        dto.setPurchaseTime(entity.getPurchaseTime());
        dto.setCertificate(certificateDtoConverter.toDto(entity.getCertificate()));
        dto.setUser(userDtoConverter.toDto(entity.getUser()));

        return dto;
    }
}

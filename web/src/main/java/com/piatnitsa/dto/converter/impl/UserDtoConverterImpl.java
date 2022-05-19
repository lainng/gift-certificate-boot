package com.piatnitsa.dto.converter.impl;

import com.piatnitsa.dto.UserDto;
import com.piatnitsa.dto.converter.DtoConverter;
import com.piatnitsa.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverterImpl implements DtoConverter<UserDto, User> {

    @Override
    public User toEntity(UserDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setName(entity.getName());
        return entity;
    }

    @Override
    public UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}

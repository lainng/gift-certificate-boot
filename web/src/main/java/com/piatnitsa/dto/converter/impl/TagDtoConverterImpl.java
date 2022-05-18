package com.piatnitsa.dto.converter.impl;

import com.piatnitsa.dto.TagDto;
import com.piatnitsa.dto.converter.DtoConverter;
import com.piatnitsa.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagDtoConverterImpl implements DtoConverter<TagDto, Tag> {

    @Override
    public Tag toEntity(TagDto dto) {
        return null;
    }

    @Override
    public TagDto toDto(Tag entity) {
        TagDto dto = new TagDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}

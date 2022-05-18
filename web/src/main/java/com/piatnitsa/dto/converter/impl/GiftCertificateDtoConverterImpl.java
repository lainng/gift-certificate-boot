package com.piatnitsa.dto.converter.impl;

import com.piatnitsa.dto.GiftCertificateDto;
import com.piatnitsa.dto.TagDto;
import com.piatnitsa.dto.converter.DtoConverter;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GiftCertificateDtoConverterImpl implements DtoConverter<GiftCertificateDto, GiftCertificate> {
    private final DtoConverter<TagDto, Tag> tagDtoConverter;

    @Autowired
    public GiftCertificateDtoConverterImpl(DtoConverter<TagDto, Tag> tagDtoConverter) {
        this.tagDtoConverter = tagDtoConverter;
    }

    @Override
    public GiftCertificate toEntity(GiftCertificateDto dto) {
        return null;
    }

    @Override
    public GiftCertificateDto toDto(GiftCertificate entity) {
        GiftCertificateDto dto = new GiftCertificateDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setDuration(entity.getDuration());
        dto.setPrice(entity.getPrice());
        dto.setCreateDate(entity.getCreateDate());
        dto.setLastUpdateDate(entity.getLastUpdateDate());
        dto.setTags(
                entity.getTags()
                        .stream()
                        .map(tagDtoConverter::toDto)
                        .collect(Collectors.toList())
        );

        return dto;
    }
}

package com.piatnitsa.hateoas.impl;

import com.piatnitsa.controller.GiftCertificateController;
import com.piatnitsa.dto.GiftCertificateDto;
import com.piatnitsa.dto.TagDto;
import com.piatnitsa.hateoas.LinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateLinkBuilderImpl implements LinkBuilder<GiftCertificateDto> {
    private final LinkBuilder<TagDto> tagDtoLinkBuilder;
    private static final Class<GiftCertificateController> CERTIFICATE_CONTROLLER_CLASS = GiftCertificateController.class;

    @Autowired
    public GiftCertificateLinkBuilderImpl(LinkBuilder<TagDto> tagDtoLinkBuilder) {
        this.tagDtoLinkBuilder = tagDtoLinkBuilder;
    }

    @Override
    public void buildLinks(GiftCertificateDto object) {
        object.add(linkTo(methodOn(CERTIFICATE_CONTROLLER_CLASS).certificateById(object.getId())).withSelfRel());
        object.getTags().forEach(tagDtoLinkBuilder::buildLinks);
    }
}

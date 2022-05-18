package com.piatnitsa.hateoas.impl;

import com.piatnitsa.controller.TagController;
import com.piatnitsa.dto.TagDto;
import com.piatnitsa.hateoas.LinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagLinkBuilderImpl implements LinkBuilder<TagDto> {
    private static final Class<TagController> TAG_CONTROLLER_CLASS = TagController.class;

    @Override
    public void buildLinks(TagDto object) {
        object.add(linkTo(methodOn(TAG_CONTROLLER_CLASS).tagById(object.getId())).withSelfRel());
    }
}

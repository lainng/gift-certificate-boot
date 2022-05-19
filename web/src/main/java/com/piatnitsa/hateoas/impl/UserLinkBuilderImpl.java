package com.piatnitsa.hateoas.impl;

import com.piatnitsa.controller.UserController;
import com.piatnitsa.dto.UserDto;
import com.piatnitsa.hateoas.LinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserLinkBuilderImpl implements LinkBuilder<UserDto> {
    private static final Class<UserController> USER_CONTROLLER_CLASS = UserController.class;

    @Override
    public void buildLinks(UserDto object) {
        object.add(linkTo(methodOn(USER_CONTROLLER_CLASS).userById(object.getId())).withSelfRel());
    }
}

package com.piatnitsa.controller;

import com.piatnitsa.dto.UserDto;
import com.piatnitsa.dto.converter.DtoConverter;
import com.piatnitsa.entity.User;
import com.piatnitsa.hateoas.LinkBuilder;
import com.piatnitsa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Class<UserController> USER_CONTROLLER_CLASS = UserController.class;
    private final UserService userService;
    private final DtoConverter<UserDto, User> userDtoConverter;
    private final LinkBuilder<UserDto> userDtoLinkBuilder;

    @Autowired
    public UserController(UserService userService,
                          DtoConverter<UserDto, User> userDtoConverter,
                          LinkBuilder<UserDto> userDtoLinkBuilder) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.userDtoLinkBuilder = userDtoLinkBuilder;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UserDto> allUsers() {
        List<User> users = userService.getAll();
        List<UserDto> dtoList = users.stream()
                .map(userDtoConverter::toDto)
                .peek(userDtoLinkBuilder::buildLinks)
                .collect(Collectors.toList());
        Link selfLink = linkTo(methodOn(USER_CONTROLLER_CLASS).allUsers()).withSelfRel();
        return CollectionModel.of(dtoList, selfLink);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto userById(@PathVariable long id) {
        User user = userService.getById(id);
        UserDto dto = userDtoConverter.toDto(user);
        userDtoLinkBuilder.buildLinks(dto);
        return dto;
    }
}

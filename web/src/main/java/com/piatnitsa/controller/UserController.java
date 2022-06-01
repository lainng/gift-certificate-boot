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

/**
 * This class is an endpoint of the API which allows to perform READ operations on users.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/users".
 * So that {@code UserController} is accessed by sending request to /users.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
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

    /**
     * Returns all {@link User} entities from data source.
     * @param page page index. Default value - 0.
     * @param size the size of the page to be returned. Default value - 5.
     * @return a {@link List} of {@link User} entities with HATEOAS.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UserDto> allUsers(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<User> users = userService.getAll(page, size);
        List<UserDto> dtoList = users.stream()
                .map(userDtoConverter::toDto)
                .peek(userDtoLinkBuilder::buildLinks)
                .collect(Collectors.toList());
        Link selfLink = linkTo(methodOn(USER_CONTROLLER_CLASS).allUsers(page, size)).withSelfRel();
        return CollectionModel.of(dtoList, selfLink);
    }

    /**
     * Returns {@link User} entity by specified ID.
     * @param id ID of user.
     * @return {@link User} entity with HATEOAS.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto userById(@PathVariable long id) {
        User user = userService.getById(id);
        UserDto dto = userDtoConverter.toDto(user);
        userDtoLinkBuilder.buildLinks(dto);
        return dto;
    }
}

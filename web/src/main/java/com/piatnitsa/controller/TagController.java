package com.piatnitsa.controller;

import com.piatnitsa.dto.TagDto;
import com.piatnitsa.dto.converter.DtoConverter;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.hateoas.LinkBuilder;
import com.piatnitsa.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This class is an endpoint of the API which allows to perform CRD operations on tags.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/tags".
 * So that {@code TagController} is accessed by sending request to /tags.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    private static final Class<TagController> TAG_CONTROLLER_CLASS = TagController.class;
    private final TagService tagService;
    private final DtoConverter<TagDto, Tag> tagDtoConverter;
    private final LinkBuilder<TagDto> tagDtoLinkBuilder;

    @Autowired
    public TagController(TagService tagService,
                         DtoConverter<TagDto, Tag> tagDtoConverter,
                         LinkBuilder<TagDto> tagDtoLinkBuilder) {
        this.tagService = tagService;
        this.tagDtoConverter = tagDtoConverter;
        this.tagDtoLinkBuilder = tagDtoLinkBuilder;
    }

    /**
     * Returns all {@link Tag} entities from data source.
     * @param page page index. Default value - 0.
     * @param size the size of the page to be returned. Default value - 5.
     * @return a {@link List} of {@link Tag} entities with HATEOAS.
     */
    @GetMapping
    public CollectionModel<TagDto> allTags(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<Tag> tags = tagService.getAll(page, size);
        List<TagDto> dtoList = tags.stream()
                .map(tagDtoConverter::toDto)
                .peek(tagDtoLinkBuilder::buildLinks)
                .collect(Collectors.toList());
        Link selfLink = linkTo(methodOn(TAG_CONTROLLER_CLASS).allTags(page, size)).withSelfRel();
        return CollectionModel.of(dtoList, selfLink);
    }

    /**
     * Returns a {@link Tag} by its ID from data source.
     * @param id a {@link Tag} ID.
     * @return a {@link Tag} entity with HATEOAS.
     */
    @GetMapping("/{id}")
    public TagDto tagById(@PathVariable long id) {
        Tag tag = tagService.getById(id);
        TagDto tagDto = tagDtoConverter.toDto(tag);
        tagDtoLinkBuilder.buildLinks(tagDto);
        return tagDto;
    }

    /**
     * Creates a new {@link Tag} entity in data source.
     * @param tag a new {@link Tag} entity for saving.
     * @return a new {@link Tag} entity with HATEOAS.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(@RequestBody Tag tag) {
        Tag savedTag = tagService.insert(tag);
        TagDto tagDto = tagDtoConverter.toDto(savedTag);
        tagDtoLinkBuilder.buildLinks(tagDto);
        return tagDto;
    }

    /**
     * Removes from data source a {@link Tag} by specified ID.
     * @param id a {@link Tag} ID.
     * @return NO_CONTENT HttpStatus
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable long id) {
        tagService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Returns a {@link List} of {@link Tag} from data source by special filter.
     * @param params request parameters which include the information needed for the search.
     * @param page page index. Default value - 0.
     * @param size the size of the page to be returned. Default value - 5.
     * @return a {@link List} of found {@link Tag} entities with HATEOAS.
     */
    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> tagByFilter(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size) {
        List<Tag> tags = tagService.doFilter(params, page, size);
        List<TagDto> dtoList = tags.stream()
                .map(tagDtoConverter::toDto)
                .peek(tagDtoLinkBuilder::buildLinks)
                .collect(Collectors.toList());
        Link selfLink = linkTo(methodOn(TAG_CONTROLLER_CLASS).tagByFilter(params, page, size)).withSelfRel();
        return CollectionModel.of(dtoList, selfLink);
    }

    /**
     * Returns the most popular user {@link Tag} entity with the highest cost
     * of all {@link com.piatnitsa.entity.Order} entities.
     * @return the most popular user {@link Tag} entity with HATEOAS.
     */
    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public TagDto mostPopularUserTagWithHighestCostOfAllOrders() {
        Tag popularTag = tagService.getMostPopularTagWithHighestCostOfAllOrders();
        TagDto tagDto = tagDtoConverter.toDto(popularTag);
        tagDtoLinkBuilder.buildLinks(tagDto);
        return tagDto;
    }
}

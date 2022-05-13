package com.piatnitsa.controller;

import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Returns all {@link Tag} entities from data source.
     * @return a {@link List} of {@link Tag} entities.
     */
    @GetMapping
    public List<Tag> allTags() {
        return tagService.getAll();
    }

    /**
     * Returns a {@link Tag} by its ID from data source.
     * @param id a {@link Tag} ID.
     * @return a {@link Tag} entity.
     * @throws IncorrectParameterException if specified ID is not valid.
     */
    @GetMapping("/{id}")
    public Tag tagById(@PathVariable long id) {
        return tagService.getById(id);
    }

    /**
     * Creates a new {@link Tag} entity in data source.
     * @param tag a new {@link Tag} entity for saving.
     * @return CREATED HttpStatus.
     * @throws IncorrectParameterException if the {@link Tag} entity contains incorrect information.
     */
    @PostMapping
    public ResponseEntity<String> createTag(@RequestBody Tag tag) {
        tagService.insert(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    /**
     * Removes from data source a {@link Tag} by specified ID.
     * @param id a {@link Tag} ID.
     * @return NO_CONTENT HttpStatus
     * @throws IncorrectParameterException if specified ID is not valid.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        tagService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Returns a {@link List} of {@link Tag} from data source by special filter.
     * @param params request parameters which include the information needed for the search.
     * @return a {@link List} of found {@link Tag} entities.
     * @throws IncorrectParameterException if request parameters contains incorrect parameter values.
     */
    @GetMapping("/filter")
    public List<Tag> tagByFilter(@RequestParam MultiValueMap<String, String> params) {
        return tagService.doFilter(params);
    }
}

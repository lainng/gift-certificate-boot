package com.piatnitsa.controller;

import com.piatnitsa.dto.GiftCertificateDto;
import com.piatnitsa.dto.converter.DtoConverter;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.hateoas.LinkBuilder;
import com.piatnitsa.service.GiftCertificateService;
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
 * This class is an endpoint of the API which allows to perform CRUD operations on {@link GiftCertificate}.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/certificates".
 * So that {@code GiftCertificateController} is accessed by sending request to /certificates.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private static final Class<GiftCertificateController> CERTIFICATE_CONTROLLER_CLASS = GiftCertificateController.class;
    private final GiftCertificateService certificateService;
    private final DtoConverter<GiftCertificateDto, GiftCertificate> certificateDtoConverter;
    private final LinkBuilder<GiftCertificateDto> certificateDtoLinkBuilder;

    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService,
                                     DtoConverter<GiftCertificateDto, GiftCertificate> certificateDtoConverter,
                                     LinkBuilder<GiftCertificateDto> certificateDtoLinkBuilder) {
        this.certificateService = certificateService;
        this.certificateDtoConverter = certificateDtoConverter;
        this.certificateDtoLinkBuilder = certificateDtoLinkBuilder;
    }

    /**
     * Returns a {@link GiftCertificate} by its ID from data source.
     * @param id a {@link GiftCertificate} ID.
     * @return a {@link GiftCertificate} entity with HATEOAS.
     */
    @GetMapping("/{id}")
    public GiftCertificateDto certificateById(@PathVariable long id) {
       GiftCertificate certificate = certificateService.getById(id);
       GiftCertificateDto dto = certificateDtoConverter.toDto(certificate);
       certificateDtoLinkBuilder.buildLinks(dto);
       return dto;
    }

    /**
     * Returns all {@link GiftCertificate} entities from data source.
     * @param page page index. Default value - 0.
     * @param size the size of the page to be returned. Default value - 5.
     * @return a {@link List} of {@link GiftCertificate} entities with HATEOAS.
     */
    @GetMapping
    public CollectionModel<GiftCertificateDto> allCertificates(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<GiftCertificate> certificates = certificateService.getAll(page, size);
        List<GiftCertificateDto> dtoList = certificates.stream()
                .map(certificateDtoConverter::toDto)
                .peek(certificateDtoLinkBuilder::buildLinks)
                .collect(Collectors.toList());
        Link selfLink = linkTo(methodOn(CERTIFICATE_CONTROLLER_CLASS).allCertificates(page, size)).withSelfRel();
        return CollectionModel.of(dtoList, selfLink);
    }

    /**
     * Creates a new {@link GiftCertificate} entity in the data source.
     * @param certificate a new {@link GiftCertificate} entity for saving.
     * @return a new {@link GiftCertificate} entity with HATEOAS.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto createCertificate(@RequestBody GiftCertificate certificate) {
        GiftCertificate savedCertificate = certificateService.insert(certificate);
        GiftCertificateDto dto = certificateDtoConverter.toDto(savedCertificate);
        certificateDtoLinkBuilder.buildLinks(dto);
        return dto;
    }

    /**
     * Removes from data source a {@link GiftCertificate} by specified ID.
     * @param id a {@link GiftCertificate} ID.
     * @return NO_CONTENT HttpStatus
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCertificateById(@PathVariable long id) {
        certificateService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates a {@link GiftCertificate} by specified ID.
     * @param id a {@link GiftCertificate} ID.
     * @param certificate a {@link GiftCertificate} that contains information for updating.
     * @return an updated {@link GiftCertificate} entity with HATEOAS.
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateCertificate(@PathVariable long id,
                                                @RequestBody GiftCertificate certificate) {
        GiftCertificate updatedCertificate = certificateService.update(id, certificate);
        GiftCertificateDto dto = certificateDtoConverter.toDto(updatedCertificate);
        certificateDtoLinkBuilder.buildLinks(dto);
        return dto;
    }

    /**
     * Returns a {@link List} of {@link GiftCertificate} from data source by special filter.
     * @param params request parameters which include the information needed for the search.
     * @return a {@link List} of found {@link GiftCertificate} entities with HATEOAS.
     */
    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<GiftCertificateDto> certificateByFilter(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size) {
        List<GiftCertificate> filteredCertificates = certificateService.doFilter(params, page, size);
        List<GiftCertificateDto> dtoList = filteredCertificates.stream()
                .map(certificateDtoConverter::toDto)
                .peek(certificateDtoLinkBuilder::buildLinks)
                .collect(Collectors.toList());
        Link selfLink = linkTo(methodOn(CERTIFICATE_CONTROLLER_CLASS).certificateByFilter(params, page, size)).withSelfRel();
        return CollectionModel.of(dtoList, selfLink);
    }
}

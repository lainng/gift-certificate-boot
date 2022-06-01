package com.piatnitsa.hateoas;

/**
 * This interface is designed for creating and adding HATEOAS for entities.
 * @param <T> entity type.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface LinkBuilder<T> {

    /**
     * Builds links for {@link T} entity.
     * @param object entity for which HATEOAS are created.
     */
    void buildLinks(T object);
}

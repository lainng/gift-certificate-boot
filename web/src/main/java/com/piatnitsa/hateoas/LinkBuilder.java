package com.piatnitsa.hateoas;

public interface LinkBuilder<T> {

    void buildLinks(T object);
}

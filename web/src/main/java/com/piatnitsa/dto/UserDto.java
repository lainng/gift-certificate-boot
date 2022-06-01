package com.piatnitsa.dto;

import org.springframework.hateoas.CollectionModel;

import java.util.Objects;

/**
 * This class represents data transfer object of {@link com.piatnitsa.entity.User} entity.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class UserDto extends CollectionModel<UserDto> {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDto dto = (UserDto) o;
        return id == dto.id && Objects.equals(name, dto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("User{");
        builder.append("id=").append(id)
                .append(", name='").append(name)
                .append('}');
        return builder.toString();
    }
}

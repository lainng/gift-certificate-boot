package com.piatnitsa.dto;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * This class represents data transfer object of {@link com.piatnitsa.entity.GiftCertificate} entity.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<TagDto> tags;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GiftCertificateDto dto = (GiftCertificateDto) o;
        return id == dto.id
                && duration == dto.duration
                && Objects.equals(name, dto.name)
                && Objects.equals(description, dto.description)
                && Objects.equals(price, dto.price)
                && Objects.equals(createDate, dto.createDate)
                && Objects.equals(lastUpdateDate, dto.lastUpdateDate)
                && Objects.equals(tags, dto.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, description, price, duration, createDate, lastUpdateDate, tags);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("GiftCertificateDto{");
        builder.append("id=").append(id)
                .append(", name='").append(name)
                .append(", description='").append(description)
                .append(", price=").append(price)
                .append(", duration=").append(duration)
                .append(", createDate=").append(createDate)
                .append(", lastUpdateDate=").append(lastUpdateDate)
                .append(", tags=").append(tags)
                .append('}');
        return builder.toString();
    }
}

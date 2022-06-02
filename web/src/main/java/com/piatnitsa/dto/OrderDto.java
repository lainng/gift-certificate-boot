package com.piatnitsa.dto;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class represents data transfer object of {@link com.piatnitsa.entity.Order} entity.
 * Intended to be returned to the client as a response.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class OrderDto extends RepresentationModel<OrderDto> {
    private long id;
    private BigDecimal cost;
    private LocalDateTime purchaseTime;
    private GiftCertificateDto certificate;
    private UserDto user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public GiftCertificateDto getCertificate() {
        return certificate;
    }

    public void setCertificate(GiftCertificateDto certificate) {
        this.certificate = certificate;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderDto dto = (OrderDto) o;
        return id == dto.id
                && Objects.equals(cost, dto.cost)
                && Objects.equals(purchaseTime, dto.purchaseTime)
                && Objects.equals(certificate, dto.certificate)
                && Objects.equals(user, dto.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, cost, purchaseTime, certificate, user);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Order{");
        result.append("id=").append(id);
        result.append(", cost=").append(cost);
        result.append(", purchaseTime=").append(purchaseTime);
        result.append(", user=").append(user);
        result.append(", certificate=").append(certificate);
        result.append('}');
        return result.toString();
    }
}

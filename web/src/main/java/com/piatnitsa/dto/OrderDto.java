package com.piatnitsa.dto;

import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.User;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDto extends RepresentationModel<OrderDto> {
    private long id;
    private BigDecimal cost;
    private LocalDateTime purchaseTime;
    private GiftCertificate certificate;
    private User user;

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

    public GiftCertificate getCertificate() {
        return certificate;
    }

    public void setCertificate(GiftCertificate certificate) {
        this.certificate = certificate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

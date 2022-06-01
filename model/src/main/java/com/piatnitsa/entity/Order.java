package com.piatnitsa.entity;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class represents order entity.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
@Entity
@Table(name = "`order`")
@Audited
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;

    @ManyToOne
    @JoinColumn(name = "gift_certificate_id")
    private GiftCertificate certificate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {}

    public Order(long id,
                 BigDecimal cost,
                 LocalDateTime purchaseTime,
                 GiftCertificate certificate,
                 User user) {
        this.id = id;
        this.cost = cost;
        this.purchaseTime = purchaseTime;
        this.certificate = certificate;
        this.user = user;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id
                && Objects.equals(cost, order.cost)
                && Objects.equals(purchaseTime, order.purchaseTime)
                && Objects.equals(certificate, order.certificate)
                && Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cost, purchaseTime, certificate, user);
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

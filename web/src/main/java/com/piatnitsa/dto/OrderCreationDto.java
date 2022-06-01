package com.piatnitsa.dto;

import java.util.Objects;

/**
 * This class represents data transfer object of {@link com.piatnitsa.entity.Order} entity.
 * Intended for next creating order entity.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class OrderCreationDto extends OrderDto {
    private long giftCertificateId;
    private long userId;

    public long getGiftCertificateId() {
        return giftCertificateId;
    }

    public void setGiftCertificateId(long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderCreationDto that = (OrderCreationDto) o;
        return giftCertificateId == that.giftCertificateId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), giftCertificateId, userId);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("OrderCreationDto{");
        builder.append("giftCertificateId=").append(giftCertificateId)
                .append(", userId=").append(userId)
                .append('}');
        return builder.toString();
    }
}

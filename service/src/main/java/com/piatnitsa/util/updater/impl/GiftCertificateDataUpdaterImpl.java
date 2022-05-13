package com.piatnitsa.util.updater.impl;

import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.util.TimestampHandler;
import com.piatnitsa.util.updater.DataUpdater;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class GiftCertificateDataUpdaterImpl implements DataUpdater<GiftCertificate> {

    @Override
    public void updateData(GiftCertificate oldInstance, GiftCertificate newInstance) {
        String name = newInstance.getName();
        if (!Objects.isNull(name)) {
            oldInstance.setName(name);
        }

        String description = newInstance.getDescription();
        if (!Objects.isNull(description)) {
            oldInstance.setDescription(description);
        }

        BigDecimal price = newInstance.getPrice();
        if (!Objects.isNull(price)) {
            oldInstance.setPrice(price);
        }

        int duration = newInstance.getDuration();
        if (duration != 0) {
            oldInstance.setDuration(duration);
        }

        List<Tag> tags = newInstance.getTags();
        if (!Objects.isNull(tags)) {
            oldInstance.setTags(tags);
        }

        String currentTimestamp = TimestampHandler.getCurrentTimestamp();
        oldInstance.setLastUpdateDate(currentTimestamp);
    }

    @Override
    public List<GiftCertificate> updateDataList(List<GiftCertificate> oldDataList) {
        throw new UnsupportedOperationException();
    }
}

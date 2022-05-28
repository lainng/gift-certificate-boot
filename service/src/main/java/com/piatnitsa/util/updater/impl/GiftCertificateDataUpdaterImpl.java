package com.piatnitsa.util.updater.impl;

import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.util.TimestampHandler;
import com.piatnitsa.util.updater.DataUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class GiftCertificateDataUpdaterImpl implements DataUpdater<GiftCertificate> {
    private final DataUpdater<Tag> tagDataUpdater;
    private final TimestampHandler timestampHandler;

    @Autowired
    public GiftCertificateDataUpdaterImpl(DataUpdater<Tag> tagDataUpdater, TimestampHandler timestampHandler) {
        this.tagDataUpdater = tagDataUpdater;
        this.timestampHandler = timestampHandler;
    }

    @Override
    public GiftCertificate updateData(GiftCertificate updatableObject, GiftCertificate dataObject) {
        String name = dataObject.getName();
        if (!Objects.isNull(name)) {
            updatableObject.setName(name);
        }

        String description = dataObject.getDescription();
        if (!Objects.isNull(description)) {
            updatableObject.setDescription(description);
        }

        BigDecimal price = dataObject.getPrice();
        if (!Objects.isNull(price)) {
            updatableObject.setPrice(price);
        }

        int duration = dataObject.getDuration();
        if (duration != 0) {
            updatableObject.setDuration(duration);
        }

        List<Tag> tags = dataObject.getTags();
        if (!Objects.isNull(tags)) {
            List<Tag> newTags = tagDataUpdater.updateDataList(tags);
            updatableObject.setTags(newTags);
        }

        updatableObject.setLastUpdateDate(timestampHandler.getCurrentTimestamp());
        return updatableObject;
    }

    @Override
    public List<GiftCertificate> updateDataList(List<GiftCertificate> dataList) {
        throw new UnsupportedOperationException();
    }
}

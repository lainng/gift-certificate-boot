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

    @Autowired
    public GiftCertificateDataUpdaterImpl(DataUpdater<Tag> tagDataUpdater) {
        this.tagDataUpdater = tagDataUpdater;
    }

    @Override
    public void updateData(GiftCertificate updatableObject, GiftCertificate dataObject) {
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
            tagDataUpdater.updateDataList(updatableObject.getTags(), dataObject.getTags());
        }

        String currentTimestamp = TimestampHandler.getCurrentTimestamp();
        updatableObject.setLastUpdateDate(currentTimestamp);
    }

    @Override
    public void updateDataList(List<Tag> updatableList, List<Tag> dataList) {
        throw new UnsupportedOperationException();
    }
}

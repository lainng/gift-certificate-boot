package com.piatnitsa.service.impl;

import com.piatnitsa.dao.GiftCertificateDao;
import com.piatnitsa.dao.TagDao;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.service.AbstractService;
import com.piatnitsa.service.GiftCertificateService;
import com.piatnitsa.service.TimestampHandler;
import com.piatnitsa.validator.GiftCertificateValidator;
import com.piatnitsa.validator.IdentifiableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class GiftCertificateServiceImpl extends AbstractService<GiftCertificate> implements GiftCertificateService {
    private final GiftCertificateDao certificateDao;
    private final TagDao tagDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao certificateDao, TagDao tagDao) {
        super(certificateDao);
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
    }

    @Override
    public GiftCertificate insert(GiftCertificate item) {
        GiftCertificateValidator.validate(item);

        String currentTimestamp = TimestampHandler.getCurrentTimestamp();
        item.setCreateDate(currentTimestamp);
        item.setLastUpdateDate(currentTimestamp);

        List<Tag> newTags = buildTagList(item);
        item.setTags(newTags);
        certificateDao.insert(item);
        return item;
    }

    @Override
    public void update(long id, GiftCertificate item) {
        IdentifiableValidator.validateId(id);
        GiftCertificateValidator.validateForUpdate(item);

        if (!certificateDao.getById(id).isPresent()) {
            throw new IncorrectParameterException();
        }
        GiftCertificate currentCertificate = certificateDao.getById(id).get();
        currentCertificate.setId(id);

        updateObject(item, currentCertificate);
        List<Tag> newTags = buildTagList(item);
        currentCertificate.setTags(newTags);
        certificateDao.update(currentCertificate);
    }

    private void updateObject(GiftCertificate newGiftCertificate,
                              GiftCertificate oldGiftCertificate) {
        String name = newGiftCertificate.getName();
        if (!Objects.isNull(name)) {
            oldGiftCertificate.setName(name);
        }

        String description = newGiftCertificate.getDescription();
        if (!Objects.isNull(description)) {
            oldGiftCertificate.setDescription(description);
        }

        BigDecimal price = newGiftCertificate.getPrice();
        if (!Objects.isNull(price)) {
            oldGiftCertificate.setPrice(price);
        }

        int duration = newGiftCertificate.getDuration();
        if (duration != 0) {
            oldGiftCertificate.setDuration(duration);
        }

        List<Tag> tags = newGiftCertificate.getTags();
        if (!Objects.isNull(tags)) {
            oldGiftCertificate.setTags(tags);
        }

        String currentTimestamp = TimestampHandler.getCurrentTimestamp();
        oldGiftCertificate.setLastUpdateDate(currentTimestamp);
    }

    @Override
    public List<GiftCertificate> doFilter(Map<String, String> params) {
        return certificateDao.getWithFilter(params);
    }

    private List<Tag> buildTagList(GiftCertificate item) {
        List<Tag> requestTags = item.getTags();
        List<Tag> savingTags = new ArrayList<>();
        if (requestTags == null || requestTags.size() == 0) {
            return savingTags;
        }
        for (Tag tag : requestTags) {
            Optional<Tag> tagFromDb = tagDao.getByName(tag.getName());
            if (tagFromDb.isPresent()) {
                savingTags.add(tagFromDb.get());
            } else {
                savingTags.add(tag);
            }
        }
        return savingTags;
    }
}

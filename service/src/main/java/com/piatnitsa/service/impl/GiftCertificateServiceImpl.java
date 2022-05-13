package com.piatnitsa.service.impl;

import com.piatnitsa.dao.GiftCertificateDao;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.ExceptionMessageHolder;
import com.piatnitsa.exception.ExceptionMessageKey;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.exception.NoSuchEntityException;
import com.piatnitsa.service.AbstractService;
import com.piatnitsa.service.GiftCertificateService;
import com.piatnitsa.util.TimestampHandler;
import com.piatnitsa.util.updater.DataUpdater;
import com.piatnitsa.validator.FilterParameterValidator;
import com.piatnitsa.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl extends AbstractService<GiftCertificate> implements GiftCertificateService {
    private final GiftCertificateDao certificateDao;
    private final DataUpdater<GiftCertificate> certificateDataUpdater;
    private final DataUpdater<Tag> tagDataUpdater;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao certificateDao,
                                      DataUpdater<GiftCertificate> certificateDataUpdater,
                                      DataUpdater<Tag> tagDataUpdater) {
        super(certificateDao);
        this.certificateDao = certificateDao;
        this.certificateDataUpdater = certificateDataUpdater;
        this.tagDataUpdater = tagDataUpdater;
    }

    @Override
    public GiftCertificate insert(GiftCertificate item) {
        ExceptionMessageHolder exceptionMessageHolder = GiftCertificateValidator.validate(item);
        if (exceptionMessageHolder.hasMessages()) {
            throw new IncorrectParameterException(exceptionMessageHolder);
        }

        String currentTimestamp = TimestampHandler.getCurrentTimestamp();
        item.setCreateDate(currentTimestamp);
        item.setLastUpdateDate(currentTimestamp);

        List<Tag> newTags = new ArrayList<>(item.getTags().size());
        tagDataUpdater.updateDataList(newTags, item.getTags());
        item.setTags(newTags);
        certificateDao.insert(item);
        return item;
    }

    @Override
    public void update(long id, GiftCertificate newDataCertificate) {
        newDataCertificate.setId(id);
        ExceptionMessageHolder exceptionMessageHolder = GiftCertificateValidator.validateForUpdate(newDataCertificate);
        if (exceptionMessageHolder.hasMessages()) {
            throw new IncorrectParameterException(exceptionMessageHolder);
        }

        Optional<GiftCertificate> optionalGiftCertificate = certificateDao.getById(id);
        if (!optionalGiftCertificate.isPresent()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }
        GiftCertificate currentCertificate = optionalGiftCertificate.get();
        currentCertificate.setId(id);
        certificateDataUpdater.updateData(currentCertificate, newDataCertificate);
        certificateDao.update(currentCertificate);
    }

    @Override
    public List<GiftCertificate> doFilter(MultiValueMap<String, String> params) {
        FilterParameterValidator.validateSortType(params);
        return certificateDao.getWithFilter(params);
    }
}

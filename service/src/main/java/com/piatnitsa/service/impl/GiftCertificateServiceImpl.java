package com.piatnitsa.service.impl;

import com.piatnitsa.dao.GiftCertificateDao;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.*;
import com.piatnitsa.service.AbstractService;
import com.piatnitsa.service.GiftCertificateService;
import com.piatnitsa.util.TimestampHandler;
import com.piatnitsa.util.updater.DataUpdater;
import com.piatnitsa.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl
        extends AbstractService<GiftCertificate>
        implements GiftCertificateService {
    private final GiftCertificateDao certificateDao;
    private final DataUpdater<GiftCertificate> certificateDataUpdater;
    private final DataUpdater<Tag> tagDataUpdater;
    private final TimestampHandler timestampHandler;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao certificateDao,
                                      DataUpdater<GiftCertificate> certificateDataUpdater,
                                      DataUpdater<Tag> tagDataUpdater,
                                      TimestampHandler timestampHandler) {
        super(certificateDao);
        this.certificateDao = certificateDao;
        this.certificateDataUpdater = certificateDataUpdater;
        this.tagDataUpdater = tagDataUpdater;
        this.timestampHandler = timestampHandler;
    }

    @Override
    public GiftCertificate insert(GiftCertificate entity) {
        ExceptionMessageHolder exceptionMessageHolder = GiftCertificateValidator.validate(entity);
        if (exceptionMessageHolder.hasMessages()) {
            throw new IncorrectParameterException(exceptionMessageHolder);
        }

        boolean isGiftCertificateExist = certificateDao.findByName(entity.getName()).isPresent();
        if (isGiftCertificateExist) {
            throw new DuplicateEntityException(ExceptionMessageKey.GIFT_CERTIFICATE_EXIST);
        }

        LocalDateTime currentTimestamp = timestampHandler.getCurrentTimestamp();
        entity.setCreateDate(currentTimestamp);
        entity.setLastUpdateDate(currentTimestamp);
        entity.setTags(tagDataUpdater.updateDataList(entity.getTags()));
        return certificateDao.insert(entity);
    }

    @Override
    public GiftCertificate update(long id, GiftCertificate newDataCertificate) {
        newDataCertificate.setId(id);
        ExceptionMessageHolder exceptionMessageHolder = GiftCertificateValidator.validateForUpdate(newDataCertificate);
        if (exceptionMessageHolder.hasMessages()) {
            throw new IncorrectParameterException(exceptionMessageHolder);
        }

        Optional<GiftCertificate> optionalGiftCertificate = certificateDao.findById(id);
        if (!optionalGiftCertificate.isPresent()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }
        GiftCertificate currentCertificate = optionalGiftCertificate.get();
        currentCertificate.setId(id);
        currentCertificate = certificateDataUpdater.updateData(currentCertificate, newDataCertificate);
        return certificateDao.update(currentCertificate);
    }

    @Override
    public GiftCertificate getByName(String name) {
        ExceptionMessageHolder holder = new ExceptionMessageHolder();
        GiftCertificateValidator.validateName(name, holder);
        if (holder.hasMessages()) {
            throw new IncorrectParameterException(holder);
        }

        Optional<GiftCertificate> optionalGiftCertificate = certificateDao.findByName(name);
        if (!optionalGiftCertificate.isPresent()) {
            throw new NoSuchEntityException(ExceptionMessageKey.GIFT_CERTIFICATE_NOT_FOUND);
        }
        return optionalGiftCertificate.get();
    }
}

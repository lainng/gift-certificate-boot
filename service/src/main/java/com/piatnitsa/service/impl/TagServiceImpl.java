package com.piatnitsa.service.impl;

import com.piatnitsa.dao.TagDao;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.*;
import com.piatnitsa.service.AbstractService;
import com.piatnitsa.service.TagService;
import com.piatnitsa.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagServiceImpl extends AbstractService<Tag> implements TagService {
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        super(tagDao);
        this.tagDao = tagDao;
    }

    @Override
    public Tag insert(Tag entity) {
        ExceptionMessageHolder exceptionMessageHolder = TagValidator.validate(entity);
        if (exceptionMessageHolder.hasMessages()) {
            throw new IncorrectParameterException(exceptionMessageHolder);
        }
        boolean isTagExist = tagDao.findByName(entity.getName()).isPresent();
        if (isTagExist) {
            throw new DuplicateEntityException(ExceptionMessageKey.TAG_EXIST);
        }
        entity.setName(capitalizeTagName(entity.getName()));
        return tagDao.insert(entity);
    }

    @Override
    public Tag getMostPopularTagWithHighestCostOfAllOrders() {
        Optional<Tag> optionalTag = tagDao.findMostPopularTagWithHighestCostOfAllOrders();
        if (!optionalTag.isPresent()) {
            throw new NoSuchEntityException(ExceptionMessageKey.TAG_NOT_FOUND);
        }
        return optionalTag.get();
    }

    private String capitalizeTagName(String tagName) {
        return tagName.substring(0, 1).toUpperCase() + tagName.substring(1);
    }
}

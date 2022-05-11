package com.piatnitsa.service.impl;

import com.piatnitsa.dao.TagDao;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DuplicateEntityException;
import com.piatnitsa.exception.ExceptionMessageHolder;
import com.piatnitsa.exception.ExceptionMessageKey;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.service.AbstractService;
import com.piatnitsa.service.TagService;
import com.piatnitsa.validator.FilterParameterValidator;
import com.piatnitsa.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl extends AbstractService<Tag> implements TagService {
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        super(tagDao);
        this.tagDao = tagDao;
    }

    @Override
    public Tag insert(Tag item) {
        ExceptionMessageHolder exceptionMessageHolder = TagValidator.validate(item);
        if (exceptionMessageHolder.hasMessages()) {
            throw new IncorrectParameterException(exceptionMessageHolder);
        }
        boolean isTagExist = tagDao.getByName(item.getName()).isPresent();
        if (isTagExist) {
            throw new DuplicateEntityException(ExceptionMessageKey.TAG_EXIST);
        }
        tagDao.insert(item);
        return item;
    }

    @Override
    public List<Tag> doFilter(Map<String, String> params) {
        FilterParameterValidator.validateSortType(params);
        return tagDao.getWithFilter(params);
    }
}

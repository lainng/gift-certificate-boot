package com.piatnitsa.util.updater.impl;

import com.piatnitsa.dao.TagDao;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.util.updater.DataUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TagDataUpdaterImpl implements DataUpdater<Tag> {
    private final TagDao tagDao;

    @Autowired
    public TagDataUpdaterImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public void updateData(Tag updatableObject, Tag dataObject) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateDataList(List<Tag> updatableList, List<Tag> dataList) {
        if (dataList == null) {
            return;
        }
        updatableList.clear();
        for (Tag tag : dataList) {
            Optional<Tag> tagFromDb = tagDao.getByName(tag.getName());
            if (tagFromDb.isPresent()) {
                updatableList.add(tagFromDb.get());
            } else {
                updatableList.add(tag);
            }
        }
    }
}

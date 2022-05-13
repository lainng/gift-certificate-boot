package com.piatnitsa.util.updater.impl;

import com.piatnitsa.dao.TagDao;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.util.updater.DataUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    public void updateData(Tag oldInstance, Tag newInstance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Tag> updateDataList(List<Tag> oldDataList) {
        List<Tag> newDataList = new ArrayList<>();
        if (oldDataList == null || oldDataList.size() == 0) {
            return newDataList;
        }
        for (Tag tag : oldDataList) {
            Optional<Tag> tagFromDb = tagDao.getByName(tag.getName());
            if (tagFromDb.isPresent()) {
                newDataList.add(tagFromDb.get());
            } else {
                newDataList.add(tag);
            }
        }
        return newDataList;
    }
}

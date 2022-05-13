package com.piatnitsa.util.updater;

import com.piatnitsa.entity.Tag;

import java.util.List;

public interface DataUpdater<T> {
    void updateData(T updatableObject, T dataObject);
    void updateDataList(List<Tag> updatableList, List<Tag> dataList);
}

package com.piatnitsa.util.updater;

import java.util.List;

public interface DataUpdater<T> {
    void updateData(T oldInstance, T newInstance);
    List<T> updateDataList(List<T> oldDataList);
}

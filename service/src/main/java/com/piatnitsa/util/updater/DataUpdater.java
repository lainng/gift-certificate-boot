package com.piatnitsa.util.updater;

import java.util.List;

public interface DataUpdater<T> {
    T updateData(T updatableObject, T dataObject);
    List<T> updateDataList(List<T> dataList);
}

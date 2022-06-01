package com.piatnitsa.util.updater;

import java.util.List;

/**
 * This interface provides tools for updating data in objects.
 * @param <T> type of updatable objects.
 */
public interface DataUpdater<T> {

    /**
     * Updates data fields in the {@link T} object.
     * @param updatableObject the underlying {@link T} object whose fields will be updated.
     * @param dataObject the object which contains new data.
     * @return the underlying {@link T} object with updated fields.
     */
    T updateData(T updatableObject, T dataObject);

    /**
     * Populates the {@link List} with updated {@link T} objects.
     * @param dataList the {@link List} that contains old {@link T} objects.
     * @return new {@link List} with updated {@link T} objects.
     */
    List<T> updateDataList(List<T> dataList);
}

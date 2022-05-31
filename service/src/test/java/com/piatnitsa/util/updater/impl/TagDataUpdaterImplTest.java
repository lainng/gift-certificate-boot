package com.piatnitsa.util.updater.impl;

import com.piatnitsa.dao.impl.TagDaoImpl;
import com.piatnitsa.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TagDataUpdaterImplTest {

    @Mock
    TagDaoImpl tagDao;

    @InjectMocks
    TagDataUpdaterImpl tagDataUpdater;

    private final Tag NEW_TAG = new Tag(0, "newTag");
    private final Tag NEW_CAPITALIZED_NAME_TAG = new Tag(0, "NewTag");
    private final Tag OLD_TAG = new Tag(0, "OldTag");
    private final Tag OLD_TAG_FROM_DB = new Tag(4, "OldTag");
    private final List<Tag> NEW_TAG_LIST = Arrays.asList(NEW_TAG, OLD_TAG);
    private final List<Tag> UPDATED_TAG_LIST = Arrays.asList(NEW_CAPITALIZED_NAME_TAG, OLD_TAG_FROM_DB);

    @Test
    void updateData_thenThrowEx() {
        assertThrows(UnsupportedOperationException.class, () -> tagDataUpdater.updateData(new Tag(), new Tag()));
    }

    @Test
    void updateDataList_thenOk() {
        Mockito.when(tagDao.findByName(NEW_TAG.getName())).thenReturn(Optional.empty());
        Mockito.when(tagDao.findByName(OLD_TAG.getName())).thenReturn(Optional.of(OLD_TAG_FROM_DB));
        List<Tag> actual = tagDataUpdater.updateDataList(NEW_TAG_LIST);
        assertEquals(UPDATED_TAG_LIST, actual);
    }
}
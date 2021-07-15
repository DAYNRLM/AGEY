package com.nrlm.agey.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nrlm.agey.database.entity.CategoryEntity;
import com.nrlm.agey.database.entity.YesNoEntity;

import java.util.List;

@Dao
public interface YesNoDao {

    @Insert
    void insertAll(YesNoEntity yesNoEntity);

    @Query("select * from YesNoEntity")
    List<YesNoEntity> getAllData();

    @Query("DELETE FROM YesNoEntity")
    void deleteTable();
}

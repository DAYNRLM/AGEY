package com.nrlm.agey.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nrlm.agey.database.entity.AssessmentEntity;
import com.nrlm.agey.database.entity.NotOperationalEntity;

import java.util.List;

@Dao
public interface NotOperationalDao {
    @Insert
    void insertAll(NotOperationalEntity notOperationalEntity);

    @Query("select * from NotOperationalEntity")
    List<NotOperationalEntity> getAllData();

    @Query("DELETE FROM NotOperationalEntity")
    void deleteTable();
}

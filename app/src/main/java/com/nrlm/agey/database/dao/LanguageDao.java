package com.nrlm.agey.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nrlm.agey.database.entity.LanguageEntity;
import com.nrlm.agey.database.entity.UserDetailEntity;

import java.util.List;

@Dao
public interface LanguageDao {

    @Insert
    void insertAll(LanguageEntity languageEntity);

    @Query("select * from LanguageEntity ")
    List<LanguageEntity> getAllData();

    @Query("DELETE FROM LanguageEntity")
    void deleteTable();
}

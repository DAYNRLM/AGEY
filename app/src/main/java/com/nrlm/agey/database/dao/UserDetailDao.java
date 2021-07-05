package com.nrlm.agey.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nrlm.agey.database.entity.UserDetailEntity;

import java.util.List;

@Dao
public interface UserDetailDao {
    @Insert
    void insertAll(UserDetailEntity userDetail);

    @Query("select * from UserDetailEntity ")
    LiveData<List<UserDetailEntity>> getAllData();
}

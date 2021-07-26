package com.nrlm.agey.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nrlm.agey.database.entity.AssessmentEntity;
import com.nrlm.agey.database.entity.CategoryEntity;
import com.nrlm.agey.database.entity.MonthlyTrackingDataEntity;

import java.util.List;

@Dao
public interface MonthlyTrackingDao {
    @Insert
    void insertAll(MonthlyTrackingDataEntity entity);


    @Query("select * from MonthlyTrackingDataEntity where syncStatus=:status")
    List<MonthlyTrackingDataEntity> getAllData(String status);

    @Query("select * from MonthlyTrackingDataEntity where id=:pid")
    MonthlyTrackingDataEntity getSelectedData(int pid);

    @Query("DELETE  FROM MonthlyTrackingDataEntity  where id=:pid")
    void deleteSelectedData(int pid);
}

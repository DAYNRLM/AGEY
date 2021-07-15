package com.nrlm.agey.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nrlm.agey.database.entity.AssessmentEntity;
import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.BlockEntity;

import java.util.List;

@Dao
public interface AssessmentDao {
    @Insert
    void insertAll(AssessmentEntity assessmentEntity);

    @Query("select * from AssessmentEntity")
    List<AssessmentEntity> getAllData();

    @Query("DELETE FROM AssessmentEntity")
    public void deleteTable();
}

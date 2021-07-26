package com.nrlm.agey.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nrlm.agey.database.entity.AssessmentEntity;
import com.nrlm.agey.database.entity.LastMonthDetailEntity;

import java.util.List;

@Dao
public interface LastMonthDetailDao {

    @Insert
    void insertAll(LastMonthDetailEntity lastMonthDetailEntity);

    @Query("select * from LastMonthDetailEntity where vehicle_reg_number=:vehRegNum ")
    List<LastMonthDetailEntity> getAllData(String vehRegNum);

    @Query("DELETE FROM LastMonthDetailEntity")
    void deleteTable();

}

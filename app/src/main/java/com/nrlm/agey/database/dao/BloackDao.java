package com.nrlm.agey.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.BlockEntity;

@Dao
public interface BloackDao {

    @Insert
    void insertAll(BlockEntity blockEntity);

    @Query("DELETE FROM BlockEntity")
     void deleteTable();

}


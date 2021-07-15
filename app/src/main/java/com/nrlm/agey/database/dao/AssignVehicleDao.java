package com.nrlm.agey.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.UserDetailEntity;

import java.util.List;

@Dao
public interface AssignVehicleDao {

    @Insert
    void insertAll(AssignVehicleDataEntity userDetail);

    @Query("select * from AssignVehicleDataEntity")
    List<AssignVehicleDataEntity> getAllData();

    @Query("select * from AssignVehicleDataEntity WHERE vehicle_reg_number =:vehicleRegNum")
    AssignVehicleDataEntity getAllData(String vehicleRegNum);

    @Query("DELETE FROM AssignVehicleDataEntity")
    public void deleteTable();


}

package com.nrlm.agey.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.ManfactureModelEntity;
import com.nrlm.agey.database.entity.VehicleTypeEntity;

import java.util.List;

@Dao
public interface VehicleTypedao {
    @Insert
    void insertAll(VehicleTypeEntity vehicleTypeEntity);

    @Query("select * from VehicleTypeEntity WHERE VehicleTypeEntity.type_id =:vehicleId ")
    VehicleTypeEntity getVehicleType(String vehicleId);

    @Query("DELETE FROM VehicleTypeEntity")
    void deleteTable();
}

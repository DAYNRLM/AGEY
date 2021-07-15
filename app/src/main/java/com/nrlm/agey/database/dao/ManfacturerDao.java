package com.nrlm.agey.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.ManufactureEntity;
import com.nrlm.agey.database.entity.VehicleTypeEntity;

@Dao
public interface ManfacturerDao {
    @Insert
    void insertAll(ManufactureEntity manufactureEntity);

    @Query("select * from ManufactureEntity WHERE ManufactureEntity.manufacturer_id =:vehicle_manufacture_id ")
    ManufactureEntity getManufacturename(String vehicle_manufacture_id);

    @Query("DELETE FROM ManufactureEntity")
    void deleteTable();
}

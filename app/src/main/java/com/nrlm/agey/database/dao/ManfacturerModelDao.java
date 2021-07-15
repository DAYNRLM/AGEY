package com.nrlm.agey.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nrlm.agey.database.entity.ManfactureModelEntity;
import com.nrlm.agey.database.entity.ManufactureEntity;

@Dao
public interface ManfacturerModelDao {

    @Insert
    void insertAll(ManfactureModelEntity manfactureModelEntity);

    @Query("select * from ManfactureModelEntity WHERE ManfactureModelEntity.manufacturer_id =:vehicle_manufacture_id and ManfactureModelEntity.veh_model_id=:modelId")
    ManfactureModelEntity getModelname(String vehicle_manufacture_id,String modelId);

    @Query("DELETE FROM ManfactureModelEntity")
    void deleteTable();
}

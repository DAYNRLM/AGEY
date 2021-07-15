package com.nrlm.agey.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.CategoryEntity;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void insertAll(CategoryEntity categoryEntity);

    @Query("select * from CategoryEntity")
    List<CategoryEntity> getAllData();

    @Query("DELETE FROM CategoryEntity")
    void deleteTable();
}

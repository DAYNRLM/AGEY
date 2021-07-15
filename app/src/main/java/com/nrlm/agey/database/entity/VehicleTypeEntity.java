package com.nrlm.agey.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VehicleTypeEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String type_name;
    public String type_id;


}

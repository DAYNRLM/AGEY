package com.nrlm.agey.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ManfactureModelEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String manufacturer_id;
    public String veh_model_id;
    public String veh_model_name;
}

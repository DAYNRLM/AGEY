package com.nrlm.agey.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ManufactureEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String manufacturer_id;
    public String manufacturer_name;
}

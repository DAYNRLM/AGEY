package com.nrlm.agey.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String categoruId;
    public String categoryName;
}

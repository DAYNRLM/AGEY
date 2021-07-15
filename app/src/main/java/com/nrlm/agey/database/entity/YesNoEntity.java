package com.nrlm.agey.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class YesNoEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String yesNoId;
    public String yesNoName;
}

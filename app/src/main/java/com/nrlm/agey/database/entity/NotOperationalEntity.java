package com.nrlm.agey.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NotOperationalEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String reasonId;
    public String reasonName;
}

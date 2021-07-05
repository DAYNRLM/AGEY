package com.nrlm.agey.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LanguageEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String languageId;
    public String languageCode;
    public String name;
    public String localName;
}

package com.nrlm.agey.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LastMonthDetailEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String vehicle_reg_number;
    public String year_of_tracking;
    public String month_of_tracking;
    public String opening_kilometer;
    public String closing_killometer;
}

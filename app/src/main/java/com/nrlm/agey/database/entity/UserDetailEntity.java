package com.nrlm.agey.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserDetailEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String user_id;
    public String state_short_name;
    public String state_code;
    public String app_version;
    public String server_date_time;
    public String language_id;
    public String mobile_number;
    public String login_attempt;
    public String logout_days;
}

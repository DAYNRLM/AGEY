package com.nrlm.agey.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nrlm.agey.database.dao.AssignVehicleDao;
import com.nrlm.agey.database.dao.LanguageDao;
import com.nrlm.agey.database.dao.UserDetailDao;
import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.LanguageEntity;
import com.nrlm.agey.database.entity.UserDetailEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {UserDetailEntity.class,
        AssignVehicleDataEntity.class,
        LanguageEntity.class}
        , version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "ageydatabase.db";
    public static volatile AppDatabase instance;
    private static final int NUMBER_OF_THREADS = 5;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public abstract UserDetailDao userDetailDao();
    public abstract AssignVehicleDao assignVehiclelDao();
    public abstract LanguageDao languageDao();


    public static AppDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return instance;
    }
}

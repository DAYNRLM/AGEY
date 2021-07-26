package com.nrlm.agey.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nrlm.agey.database.dao.AssessmentDao;
import com.nrlm.agey.database.dao.AssignVehicleDao;
import com.nrlm.agey.database.dao.BloackDao;
import com.nrlm.agey.database.dao.CategoryDao;
import com.nrlm.agey.database.dao.LanguageDao;
import com.nrlm.agey.database.dao.LastMonthDetailDao;
import com.nrlm.agey.database.dao.ManfacturerDao;
import com.nrlm.agey.database.dao.ManfacturerModelDao;
import com.nrlm.agey.database.dao.MonthlyTrackingDao;
import com.nrlm.agey.database.dao.NotOperationalDao;
import com.nrlm.agey.database.dao.UserDetailDao;
import com.nrlm.agey.database.dao.VehicleTypedao;
import com.nrlm.agey.database.dao.YesNoDao;
import com.nrlm.agey.database.entity.AssessmentEntity;
import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.BlockEntity;
import com.nrlm.agey.database.entity.CategoryEntity;
import com.nrlm.agey.database.entity.LanguageEntity;
import com.nrlm.agey.database.entity.LastMonthDetailEntity;
import com.nrlm.agey.database.entity.ManfactureModelEntity;
import com.nrlm.agey.database.entity.ManufactureEntity;
import com.nrlm.agey.database.entity.MonthlyTrackingDataEntity;
import com.nrlm.agey.database.entity.NotOperationalEntity;
import com.nrlm.agey.database.entity.UserDetailEntity;
import com.nrlm.agey.database.entity.VehicleTypeEntity;
import com.nrlm.agey.database.entity.YesNoEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {UserDetailEntity.class,
        AssignVehicleDataEntity.class,
        LanguageEntity.class,
        YesNoEntity.class,
        CategoryEntity.class,
        MonthlyTrackingDataEntity.class,
        BlockEntity.class,
        ManfactureModelEntity.class,
        ManufactureEntity.class,
        VehicleTypeEntity.class,
        AssessmentEntity.class,
        NotOperationalEntity.class,
        LastMonthDetailEntity.class}
        , version = 2, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "ageydatabase.db";
    public static volatile AppDatabase instance;
    private static final int NUMBER_OF_THREADS = 5;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public abstract UserDetailDao userDetailDao();
    public abstract AssignVehicleDao assignVehiclelDao();
    public abstract LanguageDao languageDao();
    public abstract CategoryDao categoryDao();
    public abstract YesNoDao yesNoDao();
    public abstract MonthlyTrackingDao monthlyTrackingDao();
    public abstract BloackDao bloackDao();
    public abstract ManfacturerModelDao manfacturerModelDao();
    public abstract ManfacturerDao manfacturerDao();
    public abstract VehicleTypedao vehicleTypedao();
    public abstract NotOperationalDao notOperationalDao();
    public abstract AssessmentDao assessmentDao();
    public abstract LastMonthDetailDao lastMonthDao();


    public static AppDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}

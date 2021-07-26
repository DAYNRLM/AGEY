package com.nrlm.agey.repository;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.nrlm.agey.database.AppDatabase;
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
import com.nrlm.agey.network.vollyCall.VolleyService;
import com.nrlm.agey.utils.AppSharedPreferences;
import com.nrlm.agey.utils.AppUtils;
import com.nrlm.agey.utils.GetAllInstance;

public abstract class BaseRepository {

    public Application application;
    public AppDatabase appDatabase;
    public UserDetailDao userDetailDao;
    public BloackDao bloackDao;
    public AssignVehicleDao assignVehicleDao;
    public CategoryDao categoryDao;
    public LanguageDao languageDao;
    public ManfacturerDao manfacturerDao;
    public ManfacturerModelDao manfacturerModelDao;
    public VehicleTypedao vehicleTypedao;
    public YesNoDao yesNoDao;
    public MonthlyTrackingDao monthlyTrackingDao;
    public NotOperationalDao notOperationalDao;
    public LastMonthDetailDao lastMonthDetailDao;
    public AssessmentDao assessmentDao;
    public AppSharedPreferences appSharedPreferences;
    public GetAllInstance getAllInstance;
   // public  VolleyService volleyService;


    public AppUtils appUtils;

    public BaseRepository(Application application) {
        this.application = application;
    }

    public void getAllInstance() {
        appDatabase = AppDatabase.getDatabase(application.getApplicationContext());

        userDetailDao = appDatabase.userDetailDao();
        bloackDao = appDatabase.bloackDao();
        assignVehicleDao = appDatabase.assignVehiclelDao();
        categoryDao = appDatabase.categoryDao();
        languageDao = appDatabase.languageDao();
        manfacturerDao = appDatabase.manfacturerDao();
        manfacturerModelDao = appDatabase.manfacturerModelDao();
        vehicleTypedao = appDatabase.vehicleTypedao();
        yesNoDao = appDatabase.yesNoDao();
        monthlyTrackingDao = appDatabase.monthlyTrackingDao();
        notOperationalDao=appDatabase.notOperationalDao();
        assessmentDao =appDatabase.assessmentDao();
        lastMonthDetailDao =appDatabase.lastMonthDao();

        appUtils =AppUtils.getInstance();
        appSharedPreferences =AppSharedPreferences.getInstance(application.getApplicationContext());

        getAllInstance =GetAllInstance.getInstance(application.getApplicationContext());
       // volleyService =VolleyService.getInstance(application.getApplicationContext());


    }

    public void deleteTables(){

        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDetailDao.deleteTable();
            bloackDao.deleteTable();
            assignVehicleDao.deleteTable();
            categoryDao.deleteTable();
            manfacturerDao.deleteTable();
            manfacturerModelDao.deleteTable();
            vehicleTypedao.deleteTable();
            lastMonthDetailDao.deleteTable();
        });


        /*****assessment ,yesNo, noOperationReason,langdao,monthlyTrackingDao*****/
    }


}

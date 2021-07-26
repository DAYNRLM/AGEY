package com.nrlm.agey;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.nrlm.agey.database.dao.YesNoDao;
import com.nrlm.agey.database.entity.AssessmentEntity;
import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.BlockEntity;
import com.nrlm.agey.database.entity.CategoryEntity;
import com.nrlm.agey.database.entity.LanguageEntity;
import com.nrlm.agey.database.entity.ManfactureModelEntity;
import com.nrlm.agey.database.entity.ManufactureEntity;
import com.nrlm.agey.database.entity.MonthlyTrackingDataEntity;
import com.nrlm.agey.database.entity.UserDetailEntity;
import com.nrlm.agey.database.entity.VehicleTypeEntity;
import com.nrlm.agey.database.entity.YesNoEntity;
import com.nrlm.agey.model.response.MainDataResponse;
import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.repository.HomeRepository;
import com.nrlm.agey.ui.login.AuthActivity;
import com.nrlm.agey.utils.AppSharedPreferences;
import com.nrlm.agey.utils.AppUtils;
import com.nrlm.agey.utils.GetAllInstance;
import com.nrlm.agey.utils.SampleData;

import java.util.List;

public class MainApplication extends Application {
    HomeRepository homeRepository;
    AppRepository appRepository;
    AppUtils appUtils;
   // AppSharedPreferences appSharedPreferences;


    @Override
    public void onCreate() {
        super.onCreate();
        homeRepository =HomeRepository.getInstance(this);
        appRepository =AppRepository.getInstance(this);
        appUtils =AppUtils.getInstance();
      //  appSharedPreferences = AppSharedPreferences.getInstance(getApplicationContext());




        List<YesNoEntity> yesNoDaos =homeRepository.getYesEntityData();
        if (yesNoDaos.isEmpty()){
            homeRepository.insertYesNoData();
        }

        List<LanguageEntity> langDataItem =homeRepository.getLanguagedata();
        if(langDataItem.isEmpty()) {
            homeRepository.inseartAllLanguage();
        }

        List<AssessmentEntity> assesmentList = homeRepository.getAssessmentList();
        if(assesmentList.isEmpty()){
            homeRepository.insertReasonAssesment();
        }

    }
}

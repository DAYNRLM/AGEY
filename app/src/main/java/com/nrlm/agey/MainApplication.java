package com.nrlm.agey;

import android.app.Application;

import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.LanguageEntity;
import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.repository.HomeRepository;

import java.util.List;

public class MainApplication extends Application {
    HomeRepository homeRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        homeRepository =HomeRepository.getInstance(this);
        List<LanguageEntity> langDataItem =homeRepository.getLanguagedata();
        if(langDataItem.isEmpty()) {
            homeRepository.inseartAllLanguage();
        }

        List<AssignVehicleDataEntity> vehicleData =homeRepository.getVehicleData();
        if(vehicleData.isEmpty()) {
            homeRepository.inseartVehicleData();
        }
    }
}

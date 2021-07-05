package com.nrlm.agey.ui.home;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.LanguageEntity;
import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.repository.HomeRepository;
import com.nrlm.agey.utils.ViewUtilsKt;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel  extends ViewModel {
    public HomeRepository homeRepository;

    public HomeViewModel(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    public List<String> getRegistredNumber(){
        List<String> numList = new ArrayList<>();
        numList.add("HR-12U-2871");
        numList.add("HR-13U-2334");
        numList.add("HR-13U-5678");
        numList.add("HR-12U-1432");
        numList.add("HR-11U-5643");
        numList.add("HR-52U-7865");
        numList.add("HR-17U-3453");

        return numList;
    }

    public List<LanguageEntity> getLanguage(){
        List<LanguageEntity> dataList = homeRepository.getLanguagedata();
        return dataList;
    }

    public AssignVehicleDataEntity getVehicleData(String vehicleNumber){
        AssignVehicleDataEntity assignVehicleDataEntity =homeRepository.getVehicleData(vehicleNumber);
        return assignVehicleDataEntity;
    }

}

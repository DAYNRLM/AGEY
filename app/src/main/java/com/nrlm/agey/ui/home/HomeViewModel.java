package com.nrlm.agey.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nrlm.agey.R;
import com.nrlm.agey.database.entity.AssessmentEntity;
import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.CategoryEntity;
import com.nrlm.agey.database.entity.LanguageEntity;
import com.nrlm.agey.database.entity.NotOperationalEntity;
import com.nrlm.agey.database.entity.YesNoEntity;
import com.nrlm.agey.databinding.DialogErrorMessageBinding;
import com.nrlm.agey.model.LoginError;
import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.repository.HomeRepository;
import com.nrlm.agey.utils.SampleData;
import com.nrlm.agey.utils.ViewUtilsKt;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel  extends ViewModel {
    public HomeRepository homeRepository;
    public HomeViewModel(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    public List<String> getRegistredNumber(){
        return homeRepository.getVehicleRegNum();
    }

    public List<LanguageEntity> getLanguage(){
        List<LanguageEntity> dataList = homeRepository.getLanguagedata();
        return dataList;
    }

    public AssignVehicleDataEntity getVehicleData(String vehicleNumber){
        AssignVehicleDataEntity assignVehicleDataEntity =homeRepository.getVehicleData(vehicleNumber);
        return assignVehicleDataEntity;
    }


    public String vechileType(String vechileType){
        return homeRepository.getVehicleType(vechileType);
    }

    public String getManufacturer(String vehicle_manufacture_id){
        String name ="";

        if(vehicle_manufacture_id==null){
            name ="";
        }else {
            name =homeRepository.getManufacturerName(vehicle_manufacture_id);
        }

        return name;
    }

    public String getModel(String vehicle_manufacture_id,String vehicle_model_id){
        String name ="";
        if(vehicle_manufacture_id==null||vehicle_model_id==null){
            name ="";
        }else {
            name =homeRepository.getModelName(vehicle_manufacture_id,vehicle_model_id);
        }

        return name;
    }

    public List<String> getAllcategory(){
        List<String>  catList = new ArrayList<>();
       List<CategoryEntity> categoryEntities = homeRepository.getAllCategory();
       for(CategoryEntity ct : categoryEntities){
           catList.add(ct.categoryName);
       }
        return catList;
    }

    public List<String> getDaysList(){
        List<String> days = new ArrayList<>();
        for(int i=0;i<30;i++){
            int result =i+1;
            days.add(""+result);
        }
        return days;
    }

    public List<String> getYesNoList(){
        List<String> yesNodata =new ArrayList<>();

        for (YesNoEntity yn: homeRepository.getYesEntityData()){
            yesNodata.add(yn.yesNoName);
        }
        return yesNodata;
    }

    public List<String> getAssessmentList(){
        List<String> dataList = new ArrayList<>() ;
        for(AssessmentEntity asssList: homeRepository.getAssessmentList()){
            dataList.add(asssList.assessmentName);
        }
        return dataList;
    }

    public List<String> getReasonList(){
        List<String> dataList = new ArrayList<>() ;
        for(NotOperationalEntity notOperationalEntity:homeRepository.getReasonList()){
            dataList.add(notOperationalEntity.reasonName);
        }
        return dataList;

    }

    public String getYesorNoInitial(String yesno){
        String data="";
        if(yesno.equalsIgnoreCase("Yes")){
            data ="Y";
        }else {
            data ="N";

        }
        return data;
    }


    public void noInterNetConnection(Context context){
        new MaterialAlertDialogBuilder(context).setTitle("Network/Connection error").setIcon(R.drawable.ic_baseline_signal_wifi_connected_no_internet)
                .setMessage("Check your data connection and refresh")
                .setPositiveButton("setting",(dialogInterface, i) -> {
                    dialogInterface.dismiss();
                }).setNegativeButton("cancel",(dialogInterface, i) -> {
            dialogInterface.dismiss();
        }).show();
    }

    public MutableLiveData<Boolean> showErrorDialog(LoginError loginError, Context context, LayoutInflater inflater) {
        MutableLiveData<Boolean> resetData = new MutableLiveData<>();
        MaterialAlertDialogBuilder materialAlertDialogBuilder =
                new MaterialAlertDialogBuilder(context);
        // View customLayout = inflater.inflate(R.layout.dialog_error_message, null);
        //materialAlertDialogBuilder.setView(customLayout);
        DialogErrorMessageBinding dialogErrorMessageBinding = DialogErrorMessageBinding.inflate(inflater);
        dialogErrorMessageBinding.setLogInError(loginError);
        materialAlertDialogBuilder.setView(dialogErrorMessageBinding.getRoot());
        materialAlertDialogBuilder.setCancelable(false);
        AlertDialog cusDialog = materialAlertDialogBuilder.show();
        dialogErrorMessageBinding.btnClose.setOnClickListener(view -> {
            cusDialog.cancel();
            resetData.setValue(true);
        });
        dialogErrorMessageBinding.btnOk.setOnClickListener(view -> {
            cusDialog.dismiss();
            resetData.setValue(true);
        });
        return resetData;
    }

}

package com.nrlm.agey.repository;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.nrlm.agey.database.AppDatabase;
import com.nrlm.agey.database.dao.AssignVehicleDao;
import com.nrlm.agey.database.dao.LanguageDao;
import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.LanguageEntity;
import com.nrlm.agey.utils.AppConstant;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HomeRepository extends BaseRepository{

    private static HomeRepository mInstance ;
    LanguageDao languageDao;
    AssignVehicleDao assignVehicleDao;

    public static synchronized HomeRepository getInstance(Application application){
        if(mInstance==null){
            mInstance =new HomeRepository(application);
        }
        return mInstance;
    }

    private HomeRepository(@NonNull Application application){
        languageDao =AppDatabase.getDatabase(application).languageDao();
        assignVehicleDao =AppDatabase.getDatabase(application).assignVehiclelDao();
    }
    public void inseartAllLanguage(){
        String[] langId = AppConstant.ConstantObject.getLanguage_id();
        String[] langCode =AppConstant.ConstantObject.getLanguage_code();
        String[] langEng =AppConstant.ConstantObject.getLanguage_english();
        String[] langLocal =AppConstant.ConstantObject.getLocal_language();

        for(int i=0 ;i< AppConstant.ConstantObject.getLanguage_english().length;i++){
            LanguageEntity languageEntity =new LanguageEntity();
            languageEntity.languageId =langId[i];
            languageEntity.languageCode =langCode[i];
            languageEntity.name =langEng[i];
            languageEntity.localName =langLocal[i];

            AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    languageDao.insertAll(languageEntity);
                }
            });
        }

    }

    public List<LanguageEntity> getLanguagedata(){

        List<LanguageEntity> languageData = null;
        try {
            Callable<List<LanguageEntity>> callable = new Callable<List<LanguageEntity>>() {
                @Override
                public List<LanguageEntity> call() throws Exception {
                    return languageDao.getAllData();
                }
            };
            Future<List<LanguageEntity>> future = Executors.newSingleThreadExecutor().submit(callable);
            languageData = future.get();

        } catch (Exception e) {

        }
        return languageData;

    }

    public void inseartVehicleData(){
        String[] vehNum = AppConstant.ConstantObject.getVehicleNumber();
        String[] ownBy = AppConstant.ConstantObject.getVehicleOwnedBy();
        String[] cat = AppConstant.ConstantObject.getVehicleCat();
        String[] type = AppConstant.ConstantObject.getVehicleType();
        String[] manf = AppConstant.ConstantObject.getVehicleManf();
        String[] model = AppConstant.ConstantObject.getVehicleModel();
        String[] totalCose = AppConstant.ConstantObject.getVehicleCost();
        String[] loanAmount = AppConstant.ConstantObject.getVehicleLoanAmount();
        String[] noOfEmi = AppConstant.ConstantObject.getVehicleNoEmi();
        String[] perEmiAMount = AppConstant.ConstantObject.getVehiclePerEmi();
        String[] amountPaid = AppConstant.ConstantObject.getVehicleAnountPaid();
        String[] painYear = AppConstant.ConstantObject.getVehiclePaidYear();
        String[] paidEmi = AppConstant.ConstantObject.getVehiclePaidEmi();

        for(int i=0;i<AppConstant.ConstantObject.getVehicleNumber().length;i++){
            AssignVehicleDataEntity assignVehicleDataEntity= new AssignVehicleDataEntity();
            assignVehicleDataEntity.vehicleRegistrationNo=vehNum[i];
            assignVehicleDataEntity.vehicleType=type[i];
            assignVehicleDataEntity.vehicleManufacturer=manf[i];
            assignVehicleDataEntity.vehicleCategory=cat[i];
            assignVehicleDataEntity.vehicleModel=model[i];
            assignVehicleDataEntity.vehicleOwnBy=ownBy[i];
            assignVehicleDataEntity.vehicleTotalAmount=totalCose[i];
            assignVehicleDataEntity.vehicleNewPaymentMonth=painYear[i];
            assignVehicleDataEntity.vehicleAmountRepaid=amountPaid[i];
            assignVehicleDataEntity.vehicleBalanceLoanAmount=loanAmount[i];
            assignVehicleDataEntity.vehicleNoEmi=noOfEmi[i];
            assignVehicleDataEntity.vehicleEmiPrice=perEmiAMount[i];
            assignVehicleDataEntity.vehicleEmiPaid=paidEmi[i];

            AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    assignVehicleDao.insertAll(assignVehicleDataEntity);
                }
            });

        }
    }


    public AssignVehicleDataEntity getVehicleData(String vehicleRegNum){

        AssignVehicleDataEntity assignVehicleDataEntity = null;
        try {
            Callable<AssignVehicleDataEntity> callAble =new Callable<AssignVehicleDataEntity>() {
                @Override
                public AssignVehicleDataEntity call() throws Exception {
                    return assignVehicleDao.getAllData(vehicleRegNum);
                }
            };

            Future<AssignVehicleDataEntity> future = Executors.newSingleThreadExecutor().submit(callAble);
            assignVehicleDataEntity = future.get();

        } catch (Exception e) {

        }
        return assignVehicleDataEntity;
    }

    public List<AssignVehicleDataEntity> getVehicleData(){

        List<AssignVehicleDataEntity> assignVehicleDataEntity = null;
        try {
            Callable<List<AssignVehicleDataEntity>> callable = new Callable<List<AssignVehicleDataEntity>>() {
                @Override
                public List<AssignVehicleDataEntity> call() throws Exception {
                    return assignVehicleDao.getAllData();
                }
            };
            Future<List<AssignVehicleDataEntity>> future  = Executors.newSingleThreadExecutor().submit(callable);
            assignVehicleDataEntity = future.get();

        } catch (Exception e) {

        }
        return assignVehicleDataEntity;
    }
}

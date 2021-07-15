package com.nrlm.agey.repository;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.nrlm.agey.database.AppDatabase;
import com.nrlm.agey.database.dao.AssignVehicleDao;
import com.nrlm.agey.database.dao.LanguageDao;
import com.nrlm.agey.database.dao.YesNoDao;
import com.nrlm.agey.database.entity.AssessmentEntity;
import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.CategoryEntity;
import com.nrlm.agey.database.entity.LanguageEntity;
import com.nrlm.agey.database.entity.NotOperationalEntity;
import com.nrlm.agey.database.entity.YesNoEntity;
import com.nrlm.agey.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HomeRepository extends BaseRepository{

    private static HomeRepository mInstance ;

    public static synchronized HomeRepository getInstance(Application application){
        if(mInstance==null){
            mInstance =new HomeRepository(application);
        }
        return mInstance;
    }

    private HomeRepository(@NonNull Application application){
        super(application);
        getAllInstance();
    }

    /*********inseart dummy data for language ***********/
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

    public void insertReasonAssesment(){
        String[] reason = AppConstant.ConstantObject.getReasonNotOperational();
        String[] assessement =AppConstant.ConstantObject.getAssesmentCLF();


        for(int i=0;i<reason.length;i++){
            NotOperationalEntity notOperationalEntity = new NotOperationalEntity();
            notOperationalEntity.reasonId =""+(i+1);
            notOperationalEntity.reasonName=reason[i];

            AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    notOperationalDao.insertAll(notOperationalEntity);
                }
            });
        }

        for(int i=0;i<assessement.length;i++){
            AssessmentEntity assessmentEntity = new AssessmentEntity();
            assessmentEntity.assessmentId =""+(i+1);
            assessmentEntity.assessmentName= assessement[i];


            AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    assessmentDao.insertAll(assessmentEntity);
                }
            });
        }

    }

    public List<AssessmentEntity> getAssessmentList(){
        List<AssessmentEntity> assessmentEntitiesItem =null;

        try {
            Callable<List<AssessmentEntity>> callable =new Callable<List<AssessmentEntity>>() {
                @Override
                public List<AssessmentEntity> call() throws Exception {
                    return assessmentDao.getAllData();
                }
            };
            Future<List<AssessmentEntity>> future = Executors.newSingleThreadExecutor().submit(callable);
            assessmentEntitiesItem = future.get();

        }catch (Exception e){

        }
        return assessmentEntitiesItem;
    }

    public List<NotOperationalEntity> getReasonList(){
        List<NotOperationalEntity> dataList = null;
        try {
            Callable<List<NotOperationalEntity>> callable = new Callable<List<NotOperationalEntity>>() {
                @Override
                public List<NotOperationalEntity> call() throws Exception {
                    return notOperationalDao.getAllData();
                }
            };
            Future<List<NotOperationalEntity>> future = Executors.newSingleThreadExecutor().submit(callable);
            dataList = future.get();

        }catch (Exception e){

        }
        return dataList;
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

        /*for(int i=0;i<AppConstant.ConstantObject.getVehicleNumber().length;i++){
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

        }*/
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

    public List<String> getVehicleRegNum(){
        List<String> regNumList = new ArrayList<>();
        List<AssignVehicleDataEntity> assingList =getVehicleData();

        for(AssignVehicleDataEntity data:assingList){
            regNumList.add(data.vehicleRegNumber);
        }
        return regNumList;
    }

    /************yesNoDao**************/
    public void insertYesNoData(){
        YesNoEntity yesEntity = new YesNoEntity();
        yesEntity.yesNoId="Y";
        yesEntity.yesNoName="Yes";

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                yesNoDao.insertAll(yesEntity);
            }
        });

        YesNoEntity noEntity = new YesNoEntity();
        noEntity.yesNoId="N";
        noEntity.yesNoName="No";

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                yesNoDao.insertAll(noEntity);
            }
        });
    }

    public List<YesNoEntity> getYesEntityData(){
        List<YesNoEntity> yesNoEntities =null;
        try {
            Callable<List<YesNoEntity>> callable = new Callable<List<YesNoEntity>>() {
                @Override
                public List<YesNoEntity> call() throws Exception {
                    return yesNoDao.getAllData();
                }
            };
            Future<List<YesNoEntity>> future  = Executors.newSingleThreadExecutor().submit(callable);
            yesNoEntities = future.get();

        } catch (Exception e) {

        }
        return yesNoEntities;
    }

    public String getVehicleType(String vehicle){
        String vehicleType ="";
        try {
            Callable<String> callable = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return   vehicleTypedao.getVehicleType(vehicle).type_name;
                }
            };

            Future<String> future = Executors.newSingleThreadExecutor().submit(callable);
            vehicleType =future.get();

        } catch (Exception e) {
            appUtils.showLog("expection : "+e,HomeRepository.class);

        }
        return vehicleType;

    }

    public String getManufacturerName(String vehicle_manufacture_id ){
        String manfacturerName ="";
        try {
            Callable<String> callable = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return   manfacturerDao.getManufacturename(vehicle_manufacture_id).manufacturer_name;
                }
            };

            Future<String> future = Executors.newSingleThreadExecutor().submit(callable);
            manfacturerName =future.get();

        } catch (Exception e) {
            appUtils.showLog("expection : "+e,HomeRepository.class);

        }
        return manfacturerName;
    }

    public String getModelName(String vehicle_manufacture_id ,String vehicle_model_id){
        String modelName ="";
        try {
            Callable<String> callable = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return   manfacturerModelDao.getModelname(vehicle_manufacture_id,vehicle_model_id).veh_model_name;
                }
            };

            Future<String> future = Executors.newSingleThreadExecutor().submit(callable);
            modelName =future.get();

        } catch (Exception e) {
            appUtils.showLog("expection : "+e,HomeRepository.class);

        }
        return modelName;
    }


    public List<CategoryEntity> getAllCategory(){
        List<CategoryEntity> categoryEntities =null;
        try {
            Callable<List<CategoryEntity>> callable = new Callable<List<CategoryEntity>>() {
                @Override
                public List<CategoryEntity> call() throws Exception {
                    return categoryDao.getAllData();
                }
            };

            Future<List<CategoryEntity>> future = Executors.newSingleThreadExecutor().submit(callable);
            categoryEntities =future.get();

        }catch (Exception e){

        }
        return categoryEntities;
    }

    public  void deleteDataBaseTable(){
        deleteTables();
    }

}
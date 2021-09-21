package com.nrlm.agey.repository;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.VolleyError;
import com.nrlm.agey.database.AppDatabase;
import com.nrlm.agey.database.dao.AssignVehicleDao;
import com.nrlm.agey.database.dao.LanguageDao;
import com.nrlm.agey.database.dao.YesNoDao;
import com.nrlm.agey.database.entity.AssessmentEntity;
import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.CategoryEntity;
import com.nrlm.agey.database.entity.LanguageEntity;
import com.nrlm.agey.database.entity.LastMonthDetailEntity;
import com.nrlm.agey.database.entity.MonthlyTrackingDataEntity;
import com.nrlm.agey.database.entity.NotOperationalEntity;
import com.nrlm.agey.database.entity.UserDetailEntity;
import com.nrlm.agey.database.entity.YesNoEntity;
import com.nrlm.agey.model.LoginError;
import com.nrlm.agey.model.TestObject;
import com.nrlm.agey.model.response.MainDataResponse;
import com.nrlm.agey.network.vollyCall.VolleyResult;
import com.nrlm.agey.utils.AppConstant;
import com.nrlm.agey.utils.SampleData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public List<CategoryEntity> getSelectedCategory(){
        List<CategoryEntity> categoryEntities =null;
        try {
            Callable<List<CategoryEntity>> callable = new Callable<List<CategoryEntity>>() {
                @Override
                public List<CategoryEntity> call() throws Exception {

                    AssignVehicleDataEntity vehicleObject = assignVehicleDao.getAllData(appSharedPreferences.getVehicleRegNum());
                    return categoryDao.getSelectedAllData(vehicleObject.vehicleCategory);
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


    public void inseartTrackingData(TestObject testObject){
        MonthlyTrackingDataEntity trackEntity = new MonthlyTrackingDataEntity();
        trackEntity.syncStatus ="0";
        trackEntity.imageString =testObject.imageString;
        trackEntity.assesmentForClfVo =testObject.assesmentForClfVo;
        trackEntity.ifNotReason =testObject.ifNotReason;
        trackEntity.isvehicleOperational =testObject.isvehicleOperational;
        trackEntity.insuranceRenewed =testObject.insuranceRenewed;
        trackEntity.taxAmount =testObject.taxAmount;
        trackEntity.renewalOfRoadTax =testObject.renewalOfRoadTax;
        trackEntity.numberOfVillage =testObject.numberOfVillage;
        trackEntity.numberOfTripPerDay =testObject.numberOfTripPerDay;
        trackEntity.numberOfDaysRun =testObject.numberOfDaysRun;
        trackEntity.vehicleRunningPredefiendRoute =testObject.vehicleRunningPredefiendRoute;
        trackEntity.numberOftripForStudent =testObject.numberOftripForStudent;
        trackEntity.netIncomeFromAegy =testObject.netIncomeFromAegy;
        trackEntity.amountOverDue =testObject.amountOverDue;
        trackEntity.noOfEmiDue =testObject.noOfEmiDue;
        trackEntity.balancedLoanAmount =testObject.blancedLoanAmount;
        trackEntity.amountRepaidInCurrentMonth =testObject.amountRepaidInCurrentMonth;
        trackEntity.totalKm =testObject.totall_km;
        trackEntity.closingKm =testObject.clossingKm;
        trackEntity.openingKm =testObject.openingKm;
        trackEntity.tracking_month =testObject.tracking_month;
        trackEntity.tracking_year =testObject.tracking_year;
        trackEntity.cat_of_vehicle =testObject.cat_of_vehicle;
        trackEntity.userID =appSharedPreferences.getValidUserId();
        trackEntity.blockCode =appSharedPreferences.getBlockCode();
        trackEntity.vehicleRegistrationNumber=appSharedPreferences.getVehicleRegNum();

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                monthlyTrackingDao.insertAll(trackEntity);
            }
        });

        List<MonthlyTrackingDataEntity> dataList=getTrackingData();
        MutableLiveData getKm =new MutableLiveData<Integer>(dataList.size()+1);
        SampleData.Companion.setNotificationCount(getKm);
    }

    public List<MonthlyTrackingDataEntity> getTrackingData(){
        List<MonthlyTrackingDataEntity> trakingDataList = null;

        try{
            Callable<List<MonthlyTrackingDataEntity>> callable = new Callable<List<MonthlyTrackingDataEntity>>() {
                @Override
                public List<MonthlyTrackingDataEntity> call() throws Exception {
                    return monthlyTrackingDao.getAllData("0");
                }
            };

            Future<List<MonthlyTrackingDataEntity>> future = Executors.newSingleThreadExecutor().submit(callable);
            trakingDataList =future.get();

        }catch (Exception e){

        }
        return trakingDataList;
    }


    /************get tracking data based on primary id ****************/
    public MonthlyTrackingDataEntity getSyncObject(int primaryId){

        MonthlyTrackingDataEntity trakingDataObject = null;

        try{
            Callable<MonthlyTrackingDataEntity> callable = new Callable<MonthlyTrackingDataEntity>() {
                @Override
                public MonthlyTrackingDataEntity call() throws Exception {
                    return monthlyTrackingDao.getSelectedData(primaryId);
                }
            };
            Future<MonthlyTrackingDataEntity> future = Executors.newSingleThreadExecutor().submit(callable);
            trakingDataObject = future.get();
        }catch (Exception e){

        }
        return trakingDataObject;

    }

    public JSONObject getJsonObject(int primaryId){
        MonthlyTrackingDataEntity trakingDataObject = getSyncObject(primaryId);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("user_id",appSharedPreferences.getValidUserId());
            jsonObject.accumulate("state_short_name",appSharedPreferences.getStateShortName());
            jsonObject.accumulate("block_code",appSharedPreferences.getBlockCode());
            jsonObject.accumulate("vehicle_data",getVehicleArray(trakingDataObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }
    private JSONArray getVehicleArray(MonthlyTrackingDataEntity trakingDataObject) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("vehicle_registration_number",trakingDataObject.vehicleRegistrationNumber);
            jsonObject.accumulate("vehicle_category",trakingDataObject.cat_of_vehicle);
            jsonObject.accumulate("total_no_of_trips_for_student",trakingDataObject.numberOftripForStudent);
            jsonObject.accumulate("total_no_of_days_run",trakingDataObject.numberOfDaysRun);
            jsonObject.accumulate("total_no_of_run_per_day_avg",trakingDataObject.numberOfTripPerDay);
            jsonObject.accumulate("total_no_of_village_run",trakingDataObject.numberOfVillage);
            jsonObject.accumulate("track_year",trakingDataObject.tracking_year);
            jsonObject.accumulate("track_month",trakingDataObject.tracking_month);
            jsonObject.accumulate("track_date_by_mobile",getAllInstance.dateFactroy.getDateTime());
            jsonObject.accumulate("opening_kilometer",trakingDataObject.openingKm);
            jsonObject.accumulate("clossing_kilometer",trakingDataObject.closingKm);
            jsonObject.accumulate("amount_repaid_in_this_month",trakingDataObject.amountRepaidInCurrentMonth);
            jsonObject.accumulate("blanced_loan_amount",trakingDataObject.balancedLoanAmount);
            jsonObject.accumulate("no_of_emi_overdue",trakingDataObject.noOfEmiDue);
            jsonObject.accumulate("amount_overdue",trakingDataObject.amountOverDue);
            jsonObject.accumulate("monthly_net_income",trakingDataObject.netIncomeFromAegy);
            jsonObject.accumulate("vehicle_run_predefiend_route",trakingDataObject.vehicleRunningPredefiendRoute);
            jsonObject.accumulate("renewal_of_road_tax",trakingDataObject.renewalOfRoadTax);
            jsonObject.accumulate("tax_deposit_amount",trakingDataObject.taxAmount);
            jsonObject.accumulate("insurance_renewed",trakingDataObject.insuranceRenewed);
            jsonObject.accumulate("upload_insurance_docs","");
            jsonObject.accumulate("vehicle_operational",trakingDataObject.isvehicleOperational);
            jsonObject.accumulate("reason",trakingDataObject.ifNotReason);
            jsonObject.accumulate("assesment_by_clf_vo",trakingDataObject.assesmentForClfVo);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void deleteTrackingData(int pid){
        AppDatabase.databaseWriteExecutor.execute(() -> {
           monthlyTrackingDao.deleteSelectedData(pid);
        });

    }

   /* public MutableLiveData<String> callVollyNetworkApi(JSONObject jsonObject){
        VolleyResult mResultCallBack = null;
        MutableLiveData<String> resetData = new MutableLiveData<>();

        try{
            mResultCallBack =new VolleyResult() {
                @Override
                public void notifySuccess(String requestType, JSONObject response) {
                    try {
                        if(response.has("data_Sync")){
                            String data_Sync = response.getString("data_Sync");
                            if(data_Sync.equalsIgnoreCase("Success")){
                                resetData.setValue("sucess");
                            }else {
                                resetData.setValue("failur");

                            }
                        }else {
                            resetData.setValue("wrong");
                        }

                    }catch (Exception e){

                    }
                }
                @Override
                public void notifyError(String requestType, VolleyError error) {
                    resetData.setValue("error");
                }
            };
            volleyService.postDataVolley("synData","https://nrlm.gov.in/nrlmwebservicedemo/services/agey/login",jsonObject,mResultCallBack);
        }catch (Exception e){

        }

        return resetData;
    }*/

    public List<UserDetailEntity> getUserdetail(){
        List<UserDetailEntity> userData=null;
        try {
            Callable<List<UserDetailEntity>> listCallable = new Callable<List<UserDetailEntity>>() {
                @Override
                public List<UserDetailEntity> call() throws Exception {
                    return userDetailDao.getAllData();
                }
            };
            Future<List<UserDetailEntity>> future = Executors.newSingleThreadExecutor().submit(listCallable);
            userData = future.get();

        }catch (Exception e){

        }
        return userData;
    }

    public List<LastMonthDetailEntity> getLastMonthData(String regNum){

        List<LastMonthDetailEntity> lastMonthDetailEntities =null;
        try{
           Callable<List<LastMonthDetailEntity>> callable = new Callable<List<LastMonthDetailEntity>>() {
               @Override
               public List<LastMonthDetailEntity> call() throws Exception {
                   return lastMonthDetailDao.getAllData(regNum);
               }
           };
            Future<List<LastMonthDetailEntity>> future = Executors.newSingleThreadExecutor().submit(callable);
            lastMonthDetailEntities = future.get();
        }catch (Exception e){

        }

        return lastMonthDetailEntities;
    }

}
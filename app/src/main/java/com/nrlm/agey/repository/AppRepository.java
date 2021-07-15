package com.nrlm.agey.repository;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nrlm.agey.database.AppDatabase;
import com.nrlm.agey.database.dao.BloackDao;
import com.nrlm.agey.database.dao.LanguageDao;
import com.nrlm.agey.database.dao.UserDetailDao;
import com.nrlm.agey.database.entity.AssignVehicleDataEntity;
import com.nrlm.agey.database.entity.BlockEntity;
import com.nrlm.agey.database.entity.CategoryEntity;
import com.nrlm.agey.database.entity.LanguageEntity;
import com.nrlm.agey.database.entity.ManfactureModelEntity;
import com.nrlm.agey.database.entity.ManufactureEntity;
import com.nrlm.agey.database.entity.UserDetailEntity;
import com.nrlm.agey.database.entity.VehicleTypeEntity;
import com.nrlm.agey.model.JplaceholderTest;
import com.nrlm.agey.model.response.Example;
import com.nrlm.agey.model.response.MainDataResponse;
import com.nrlm.agey.network.retrofitCall.CallApi;
import com.nrlm.agey.network.retrofitCall.RetrofitClient;
import com.nrlm.agey.ui.login.AuthFragment;
import com.nrlm.agey.utils.AppConstant;
import com.nrlm.agey.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository extends BaseRepository {
    private static AppRepository mInstance;

    public static final String TAG = "AppRepository";
    List<Example> dataItem;


    private MutableLiveData<Boolean> todoLiveData = new MutableLiveData<>();
    private MutableLiveData<String> retrofitData = new MutableLiveData<>();

    private Executor mExecutor = Executors.newSingleThreadExecutor();



    public static synchronized AppRepository getInstance(Application application) {
        if (mInstance == null) {
            mInstance = new AppRepository(application);
        }
        return mInstance;
    }

    private AppRepository(@NonNull Application application) {
        super(application);
        getAllInstance();

    }




    public CallApi getSafeApiCall() {
        return RetrofitClient.getInstance().getApi();
    }

    public MutableLiveData<Boolean> showP() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                todoLiveData.setValue(true);
            }
        }, 4000);

        return todoLiveData;

    }


    public List<Example> callAuthApi(String name, String password) {
        Call<List<Example>> call = RetrofitClient.getInstance().getApi().post();
        call.enqueue(new Callback<List<Example>>() {
            @Override
            public void onResponse(Call<List<Example>> call, Response<List<Example>> response) {
                Log.d(TAG, "onResponse: " + response.toString());
                dataItem = response.body();
                for (Example e : response.body()) {
                    Log.d(TAG, "onResponse: " + e.getTitle());
                }

            }

            @Override
            public void onFailure(Call<List<Example>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });
        return dataItem;
    }


    public void saveDate(UserDetailEntity userDetailEntity) {
        userDetailDao.insertAll(userDetailEntity);
    }

   /* public LiveData<List<UserDetailEntity>> getAllUser() {
        LiveData<List<UserDetailEntity>> listLiveData;
        listLiveData = userDetailDao.getAllData();
        return listLiveData;
    }*/


    public MutableLiveData<String> getTestApiCall(JSONObject jsonObject) {
        appUtils.showLog("object is    :  " + jsonObject.toString(), AppRepository.class);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().getDummyTestResponse(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        retrofitData.setValue(response.body().string());
                        appUtils.showLog("response<<<<<  :-" + response.body().string(), AppRepository.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    appUtils.showLog("response<<<<<  :-" + response.toString(), AppRepository.class);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        return retrofitData;
    }

    public void authApiCall(JSONObject jsonObject){
        appUtils.showLog("inside Auth calling method",AppRepository.class);

        Call<MainDataResponse> call =RetrofitClient.getInstance().getApi().getAgeyAuthResponse(jsonObject);
        call.enqueue(new Callback<MainDataResponse>() {
            @Override
            public void onResponse(Call<MainDataResponse> call, Response<MainDataResponse> response) {
                appUtils.showLog("response comming is ::"+response.body().toString(),AppRepository.class);
                MainDataResponse mainDataResponse = new MainDataResponse();
                mainDataResponse =response.body();
                MainDataResponse.Data d =mainDataResponse.getData();
                String str  =  mainDataResponse.javaToJson();
                appUtils.showLog("response comming is ::"+str,AppRepository.class);

                appUtils.showLog("DATA comming is ::"+d,AppRepository.class);

            }

            @Override
            public void onFailure(Call<MainDataResponse> call, Throwable t) {
                appUtils.showLog("login error is  ::"+t.toString(),AppRepository.class);
            }
        });

    }


    /****************user data  details DB*****************************/
    public void insertUserData(UserDetailEntity userData) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDetailDao.insertAll(userData);
            }
        });
    }


    /****************Assingn data  details DB*****************************/

    public void insertBlockEntity(BlockEntity blockEntity) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                 bloackDao.insertAll(blockEntity);
            }
        });
    }


    /****************Assingn data  details DB*****************************/

    public void insertVehicleEntity(AssignVehicleDataEntity vehicleDataEntity) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                assignVehicleDao.insertAll(vehicleDataEntity);
            }
        });
    }

    /****************Manfcturer data  details DB*****************************/

    public void insertManufactureEntity(ManufactureEntity manufactureEntity) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                manfacturerDao.insertAll(manufactureEntity);
            }
        });
    }

    /****************ModelInfo data  details DB*****************************/

    public void insertModelEntity(ManfactureModelEntity modelEntity) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                manfacturerModelDao.insertAll(modelEntity);
            }
        });
    }

    /****************VehicleType data  details DB*****************************/

    public void insertVehicleTypeEntity(VehicleTypeEntity vehicleEntity) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                vehicleTypedao.insertAll(vehicleEntity);
            }
        });
    }



    /****************VehicleType data  details DB*****************************/

    public void insertCategoryEntity(CategoryEntity categoryEntity) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDao.insertAll(categoryEntity);
            }
        });
    }


    /*******************volly*************call**********************/

    public void inseartData( MainDataResponse monthlyMainResponse){
        MainDataResponse.UserData  data = monthlyMainResponse.getData().getUserData();
        UserDetailEntity userEntity =new UserDetailEntity();
        userEntity.user_id =data.getUserId();
        userEntity.language_id = String.valueOf(data.getLanguageId());
        userEntity.logout_days =data.getLogoutDays();
        userEntity.login_attempt = String.valueOf(data.getLoginAttempt());
        userEntity.mobile_number = data.getMobileNumber();
        userEntity.server_date_time = data.getServerDateTime();
        userEntity.app_version =data.getAppVersion();
       // appRepository.insertUserData(userEntity);
        insertUserData(userEntity);


        List<MainDataResponse.AssignDatum> assignData = monthlyMainResponse.getData().getAssignData();
        for(MainDataResponse.AssignDatum assignObject:assignData){
            BlockEntity blockEntity = new BlockEntity();
            blockEntity.block_code = assignObject.getBlockCode();
            blockEntity.block_name = assignObject.getBlockName();
            //appRepository.insertBlockEntity(blockEntity);
            insertBlockEntity(blockEntity);



            List<MainDataResponse.VehicleDatum> vehicleData =assignObject.getVehicleData();
            for(MainDataResponse.VehicleDatum vehicleObject:vehicleData){
                AssignVehicleDataEntity assignVehicleDataEntity = new AssignVehicleDataEntity();
                assignVehicleDataEntity.blockCode =assignObject.getBlockCode();
                assignVehicleDataEntity.vehicleRegNumber =vehicleObject.getVehicleRegNumber();
                assignVehicleDataEntity.ownerContribution =vehicleObject.getOwnerContribution();
                assignVehicleDataEntity.grantAmountReceived =vehicleObject.getGrantAmountReceived();
                assignVehicleDataEntity.numberOfEmiPaid =vehicleObject.getNumberOfEmiPaid();
                assignVehicleDataEntity.totalNumberOfEmi =vehicleObject.getTotalNumberOfEmi();
                assignVehicleDataEntity.vehicleCategory =vehicleObject.getVehicleCategory();
                assignVehicleDataEntity.vehicleDateOfRegistration =vehicleObject.getVehicleDateOfRegistration();

                assignVehicleDataEntity.amountPaidAsOn =vehicleObject.getAmountPaidAsOn();
                assignVehicleDataEntity.valuePerEmi =vehicleObject.getValuePerEmi();
                assignVehicleDataEntity.vehicleModel =vehicleObject.getVehicleModel();
                assignVehicleDataEntity.vehicleOwnedBy =vehicleObject.getVehicleOwnedBy();
                assignVehicleDataEntity.vehicleType =vehicleObject.getVehicleType();

                assignVehicleDataEntity.vehicleManufacture =vehicleObject.getVehicleManufacture();
                assignVehicleDataEntity.totalAmountPaid =vehicleObject.getTotalAmountPaid();
                assignVehicleDataEntity.vehicleRunningInFixedRoute =vehicleObject.getVehicleRunningInFixedRoute();
                assignVehicleDataEntity.vehicleTotalCost =vehicleObject.getVehicleTotalCost();
                assignVehicleDataEntity.vehicleInsuranceType =vehicleObject.getVehicleInsuranceType();

                assignVehicleDataEntity.vehicleLoanAmountFromClf =vehicleObject.getVehicleLoanAmountFromClf();
                assignVehicleDataEntity.departmentFromGrantAmountRecived =vehicleObject.getDepartmentFromGrantAmountRecived();
                assignVehicleDataEntity.vehicleRegNumber =vehicleObject.getVehicleRegNumber();
                assignVehicleDataEntity.vehicleLoanAmountFromOther =vehicleObject.getVehicleLoanAmountFromOther();
                assignVehicleDataEntity.insuranceRenewalData =vehicleObject.getInsuranceRenewalData();

                assignVehicleDataEntity.blockCode =assignObject.getBlockCode();

                //appRepository.insertVehicleEntity(assignVehicleDataEntity);
                insertVehicleEntity(assignVehicleDataEntity);

            }
        }

        MainDataResponse.MasterData masterData =monthlyMainResponse.getData().getMasterData();
        List<MainDataResponse.CategoryDatum> categoryList =masterData.getCategoryData();
        for(MainDataResponse.CategoryDatum categoryObject:categoryList){
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.categoruId =categoryObject.getCategoryId();
            categoryEntity.categoryName = categoryObject.getCategoryName();
           // appRepository.insertCategoryEntity(categoryEntity);
            insertCategoryEntity(categoryEntity);
        }

        List<MainDataResponse.VehicleType> vehicleTypeList = masterData.getVehicleType();
        for(MainDataResponse.VehicleType vehicleTypeObject: vehicleTypeList){
            VehicleTypeEntity vehicleTypeEntity = new VehicleTypeEntity();
            vehicleTypeEntity.type_id =vehicleTypeObject.getTypeId();
            vehicleTypeEntity.type_name=vehicleTypeObject.getTypeName();
           // appRepository.insertVehicleTypeEntity(vehicleTypeEntity);
            insertVehicleTypeEntity(vehicleTypeEntity);
        }

        List<MainDataResponse.VehicleManufacturer> vehicleManufacturersList =masterData.getVehicleManufacturer();
        for(MainDataResponse.VehicleManufacturer vehicleManufactureObject :vehicleManufacturersList){
            ManufactureEntity manufactureEntity = new ManufactureEntity();
            manufactureEntity.manufacturer_id =vehicleManufactureObject.getManufacturerId();
            manufactureEntity.manufacturer_name =vehicleManufactureObject.getManufacturerName();
           // appRepository.insertManufactureEntity(manufactureEntity);
            insertManufactureEntity(manufactureEntity);

            List<MainDataResponse.VechModelInfo> modelList = vehicleManufactureObject.getVechModelInfo();
            for(MainDataResponse.VechModelInfo modelObject:modelList){

                ManfactureModelEntity manfactureModelEntity = new ManfactureModelEntity();
                manfactureModelEntity.manufacturer_id =vehicleManufactureObject.getManufacturerId();
                manfactureModelEntity.veh_model_id = modelObject.getVehModelId();
                manfactureModelEntity.veh_model_name=modelObject.getVehModelName();
                //appRepository.insertModelEntity(manfactureModelEntity);
                insertModelEntity(manfactureModelEntity);
            }
        }
    }

    public  void deleteDataBaseTable(){
        deleteTables();
    }
}

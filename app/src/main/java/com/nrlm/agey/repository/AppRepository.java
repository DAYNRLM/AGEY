package com.nrlm.agey.repository;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nrlm.agey.database.AppDatabase;
import com.nrlm.agey.database.dao.LanguageDao;
import com.nrlm.agey.database.dao.UserDetailDao;
import com.nrlm.agey.database.entity.LanguageEntity;
import com.nrlm.agey.database.entity.UserDetailEntity;
import com.nrlm.agey.model.response.Example;
import com.nrlm.agey.network.retrofitCall.CallApi;
import com.nrlm.agey.network.retrofitCall.RetrofitClient;
import com.nrlm.agey.utils.AppConstant;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository extends BaseRepository  {

    public static final String TAG = "AppRepository";
    List<Example> dataItem;
    UserDetailDao userDetailDao;


    private MutableLiveData<Boolean> todoLiveData = new MutableLiveData<>();

    //public static final AppRepository APP_REPOSITORY =new AppRepository();
   private Executor mExecutor = Executors.newSingleThreadExecutor();

    private static AppRepository mInstance ;

    public static synchronized AppRepository getInstance(Application application){
        if(mInstance==null){
            mInstance =new AppRepository(application);
        }
        return mInstance;
    }

    public AppRepository(@NonNull Application application){
        userDetailDao = AppDatabase.getDatabase(application).userDetailDao();


    }

    public LiveData<List<UserDetailEntity>> getAllUserData(){
        return userDetailDao.getAllData();
    }


    public CallApi getSafeApiCall(){
        return RetrofitClient.getInstance().getApi();
    }

    public MutableLiveData<Boolean> showP(){
        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                todoLiveData.setValue(true);
            }
        },4000);

        return todoLiveData;

    }

    public List<Example> callAuthApi(String name,String password){
        Call<List<Example>> call = RetrofitClient.getInstance().getApi().post();
        call.enqueue(new Callback<List<Example>>() {
            @Override
            public void onResponse(Call<List<Example>> call, Response<List<Example>> response) {
                Log.d(TAG, "onResponse: " + response.toString());
                dataItem = response.body();
                for(Example e:response.body()){
                    Log.d(TAG, "onResponse: " + e.getTitle());
                }

                /*****save data in db for verifi***************/
                UserDetailEntity userDetailEntity =new UserDetailEntity();
                userDetailEntity.name="lincon";
                AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        userDetailDao.insertAll(userDetailEntity);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Example>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });
        return dataItem;
    }


    public void saveDate(UserDetailEntity userDetailEntity){
        userDetailDao.insertAll(userDetailEntity);
    }

    public LiveData<List<UserDetailEntity>> getAllUser(){
        LiveData<List<UserDetailEntity>> listLiveData;
        listLiveData = userDetailDao.getAllData();
        return listLiveData;
    }


}

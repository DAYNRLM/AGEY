package com.nrlm.agey.repository;

import android.app.Application;

import androidx.annotation.NonNull;

import com.nrlm.agey.database.AppDatabase;
import com.nrlm.agey.database.dao.AssignVehicleDao;
import com.nrlm.agey.database.dao.LanguageDao;
import com.nrlm.agey.database.entity.NotOperationalEntity;
import com.nrlm.agey.database.entity.UserDetailEntity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MpinRepository extends BaseRepository {

    private static MpinRepository mInstance ;


    public static synchronized MpinRepository getInstance(Application application){
        if(mInstance==null){
            mInstance =new MpinRepository(application);
        }
        return mInstance;
    }

    private MpinRepository(@NonNull Application application){
        super(application);
        getAllInstance();
    }

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
}

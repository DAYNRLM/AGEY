package com.nrlm.agey.repository;

import android.util.Log;

import com.nrlm.agey.database.AppDatabase;
import com.nrlm.agey.database.entity.UserDetailEntity;
import com.nrlm.agey.model.response.Example;
import com.nrlm.agey.network.Result;
import com.nrlm.agey.network.retrofitCall.CallApi;
import com.nrlm.agey.network.retrofitCall.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseAllRepository {

    Response getResponse;

    public <T> Result<T> safeCallApi(Call<T> apicall){

       apicall.enqueue(new Callback<T>() {
           @Override
           public void onResponse(Call<T> call, Response<T> response) {
               if(response.isSuccessful()){
                   getResponse =response;
               }
           }

           @Override
           public void onFailure(Call<T> call, Throwable t) {

           }
       });

       return new Result.Success(getResponse);

    }




    /*suspend fun <T> safeApiCall(
            apiCall: suspend () -> T
    ):Resource<T>{
        return withContext(Dispatchers.IO){
            try{
                Resource.Success(apiCall.invoke())

            }catch (throwable:Throwable){
                when(throwable){
                    is HttpException ->{
                        Resource.Failur(false,throwable.code(),throwable.response()?.errorBody())
                    }
                    else ->{
                        Resource.Failur(true,null,null)
                    }
                }

            }
        }
    }*/






}

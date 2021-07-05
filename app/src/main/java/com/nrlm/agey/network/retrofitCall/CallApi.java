package com.nrlm.agey.network.retrofitCall;

import com.nrlm.agey.model.response.Example;
import com.nrlm.agey.model.response.LoginResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CallApi {

   /* @GET("changes/")
    Call<List<UserDetailEntity>> loadChanges(@Query("q") String status);*/

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> verifyUser(
            @Field("user_id") String userId
    );

    @FormUrlEncoded
    @POST("login")
      Call<Example> login(
            @Field("user_id") String userId,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("login")
    LoginResponse getLogin( @Field("user_id") String userId,
                            @Field("password") String password);


    @GET("posts")
    Call<List<Example>> post();

    @GET("posts")
    Response<List<Example>> getP();




    /*@GET("/users/{user}")
    suspend fun getUser(@Path("user") userId: String): User*/



}
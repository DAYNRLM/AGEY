package com.nrlm.agey.network.retrofitCall;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //private static final String BASE_URL ="https://nrlm.gov.in/cboServices/cbo/";
    private static final String BASE_URL ="https://jsonplaceholder.typicode.com/";
    private static RetrofitClient mInstance ;
    private Retrofit retrofit;

    public static synchronized RetrofitClient getInstance(){
        if(mInstance==null){
            mInstance =new RetrofitClient();
        }
        return mInstance;
    }

    private RetrofitClient() {
         retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public CallApi getApi(){
        return retrofit.create(CallApi.class);
    }


   public <Api> Api buildApi(Class<Api> api){
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build().create(api);

   }
}

package com.nrlm.agey.model.request;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    @SerializedName("user_id")
    public  String userId;
    @SerializedName("user_password")
    public  String password;
    @SerializedName("IMEI")
    public  String deviceImei;
    @SerializedName("device_name")
    public  String deviceName;
    @SerializedName("app_version")
    public  String appVersion;
    @SerializedName("date")
    public  String todayDate;
    @SerializedName("android_version")
    public  String androidVersion;
    @SerializedName("location_coordinate")
    public  String locCoordinate;
    @SerializedName("android_api_version")
    public  String androidApiVersion;
    @SerializedName("logout_time")
    public  String logoutTime;
    @SerializedName("app_login_time")
    public  String appLoginTime;
    @SerializedName("app_request")
    public  String appRequest;

    public static LoginRequest jsonToJava(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, LoginRequest.class);
    }

    public String javaToJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}

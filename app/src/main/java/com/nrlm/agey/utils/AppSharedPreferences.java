package com.nrlm.agey.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreferences {
    private static AppSharedPreferences appSharedPrefrences;
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private Context context;

    public AppSharedPreferences(Context context) {
        this.appSharedPrefs = context.getSharedPreferences("sharedpref", Context.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public static AppSharedPreferences getInstance(Context con) {
        if (appSharedPrefrences == null)
            appSharedPrefrences = new AppSharedPreferences(con);
        return appSharedPrefrences;
    }


    public void clearAllData() {
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public void removeKeyData(String key) {
        prefsEditor.remove(key);
        prefsEditor.commit();
    }

    public String getShgCode() {
        return appSharedPrefs.getString(PrefrenceManager.PREF_KEY_SHG_CODE, "");
    }

    public void setShgCode(String value)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putString(PrefrenceManager.PREF_KEY_SHG_CODE,value);
        prefsEditor.apply();
    }

    public String getImeiNumber() {
        return appSharedPrefs.getString(PrefrenceManager.PREF_KEY_DEVICE_IMEI, "");
    }

    public void setImeiNumber(String value)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putString(PrefrenceManager.PREF_KEY_DEVICE_IMEI,value);
        prefsEditor.apply();
    }

    public String getDeviceInfo() {
        return appSharedPrefs.getString(PrefrenceManager.PREF_KEY_DEVICE_INFO, "");
    }

    public void setDeviceInfo(String value)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putString(PrefrenceManager.PREF_KEY_DEVICE_INFO,value);
        prefsEditor.apply();
    }


    public String getLoginStatus() {
        return appSharedPrefs.getString(PrefrenceManager.PREF_KEY_LOGIN_STATUS, "");
    }

    public void setLoginStatus(String value)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putString(PrefrenceManager.PREF_KEY_LOGIN_STATUS,value);
        prefsEditor.apply();
    }

    public String getMpin() {
        return appSharedPrefs.getString(PrefrenceManager.PREF_KEY_APP_MPIN, "");
    }

    public void setMpin(String value)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putString(PrefrenceManager.PREF_KEY_APP_MPIN,value);
        prefsEditor.apply();
    }


    public String getVehicleRegNum() {
        return appSharedPrefs.getString(PrefrenceManager.PREF_KEY_VEHICLE_REG_NUM, "");
    }

    public void setVehicleRegNum(String value)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putString(PrefrenceManager.PREF_KEY_VEHICLE_REG_NUM,value);
        prefsEditor.apply();
    }

    public String getValidUserId() {
        return appSharedPrefs.getString(PrefrenceManager.PREF_KEY_VALID_USER_ID, "");
    }

    public void setValidUserId(String value)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putString(PrefrenceManager.PREF_KEY_VALID_USER_ID,value);
        prefsEditor.apply();
    }

    public String getBlockCode() {
        return appSharedPrefs.getString(PrefrenceManager.PREF_KEY_BLOCK_CODE, "");
    }

    public void setBlockCode(String value)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putString(PrefrenceManager.PREF_KEY_BLOCK_CODE,value);
        prefsEditor.apply();
    }

    public String getStateShortName() {
        return appSharedPrefs.getString(PrefrenceManager.PREF_KEY_STATE_SHORT_NAME, "");
    }

    public void setStateShortName(String value)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putString(PrefrenceManager.PREF_KEY_STATE_SHORT_NAME,value);
        prefsEditor.apply();
    }

    public String getStateShortCode() {
        return appSharedPrefs.getString(PrefrenceManager.PREF_KEY_STATE_SHORT_CODE, "");
    }

    public void setStateShortCode(String value)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putString(PrefrenceManager.PREF_KEY_STATE_SHORT_CODE,value);
        prefsEditor.apply();
    }

    public String getLogOutTime() {
        return appSharedPrefs.getString(PrefrenceManager.PREF_KEY_LOGOUT_TIME, "");
    }

    public void setLogOutTime(String value)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putString(PrefrenceManager.PREF_KEY_LOGOUT_TIME,value);
        prefsEditor.apply();
    }


    public String getLanguageCode() {
        return appSharedPrefs.getString(PrefrenceManager.PREF_KEY_CHANG_LANGUAGE, "");
    }

    public void setLanguageCode(String value)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putString(PrefrenceManager.PREF_KEY_CHANG_LANGUAGE,value);
        prefsEditor.apply();
    }

    public void removeDataAtLogout(){

        removeKeyData(PrefrenceManager.PREF_KEY_SHG_CODE);
        removeKeyData(PrefrenceManager.PREF_KEY_LOGIN_STATUS);
        removeKeyData(PrefrenceManager.PREF_KEY_DEVICE_IMEI);
        removeKeyData(PrefrenceManager.PREF_KEY_DEVICE_INFO);
        removeKeyData(PrefrenceManager.PREF_KEY_VEHICLE_REG_NUM);
        removeKeyData(PrefrenceManager.PREF_KEY_VALID_USER_ID);
        removeKeyData(PrefrenceManager.PREF_KEY_BLOCK_CODE);
        removeKeyData(PrefrenceManager.PREF_KEY_STATE_SHORT_CODE);
        removeKeyData(PrefrenceManager.PREF_KEY_STATE_SHORT_NAME);

    }
}

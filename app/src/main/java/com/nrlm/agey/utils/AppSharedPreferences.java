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


    public void removeData(){
        removeKeyData(PrefrenceManager.PREF_KEY_SHG_CODE);
    }
}

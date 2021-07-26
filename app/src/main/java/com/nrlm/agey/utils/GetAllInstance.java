package com.nrlm.agey.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class GetAllInstance {
    private static GetAllInstance getAllInstance;
    public  AppSharedPreferences appSharedPreferences;
    public  AppDeviceInfoUtils deviceUtils;
    public  AppDateFactory dateFactroy;

    public static GetAllInstance getInstance(Context con) {
        if (getAllInstance == null)
            getAllInstance = new GetAllInstance(con);
        return getAllInstance;
    }

    private GetAllInstance(Context context) {
        appSharedPreferences =AppSharedPreferences.getInstance(context);
        deviceUtils =AppDeviceInfoUtils.getInstance(context);
        dateFactroy =AppDateFactory.getInstance();
    }
}

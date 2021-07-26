package com.nrlm.agey.utils;

public class PrefrenceManager {
     static final String PREF_KEY_SHG_CODE = "shgCode";
     static final String PREF_KEY_LOGIN_STATUS = "loginDone";
     static final String PREF_KEY_DEVICE_IMEI = "imeiNumber";
     static final String PREF_KEY_DEVICE_INFO = "deviceName";
     static final String PREF_KEY_APP_MPIN = "appMpin";
     static final String PREF_KEY_VEHICLE_REG_NUM = "vehicleregNum";
     static final String PREF_KEY_VALID_USER_ID = "validUserId";
     static final String PREF_KEY_BLOCK_CODE = "blockCode";
     static final String PREF_KEY_STATE_SHORT_NAME = "stateShortName";
     static final String PREF_KEY_STATE_SHORT_CODE = "stateShortCODE";
     static final String PREF_KEY_LOGOUT_TIME = "logOutTime";
     static final String PREF_KEY_CHANG_LANGUAGE = "changeLanguage";



     public static final String KEY_SAVE_DATA_LOCAL_DB = "savedataLocalDb";
     public static final String KEY_REST_DONE = "resetDone";

     public static String getRsSymbol(String amount){

          return amount+" Rs.";

     }
}

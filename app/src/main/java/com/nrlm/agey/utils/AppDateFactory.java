package com.nrlm.agey.utils;

import android.content.Context;

import com.nrlm.agey.MainApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppDateFactory {
    private static AppDateFactory dateFactory;
    private Locale locale;
    AppUtils appUtils;

    public static AppDateFactory getInstance() {
        if (dateFactory == null) {
            dateFactory = new AppDateFactory();
        }
        return dateFactory;
    }

    private AppDateFactory(){
        appUtils = AppUtils.getInstance();
    }

    /****get time stamp in yyyy-MM-dd HH:mm:ss*******/
    public String getDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        return date;
    }

    /********get date in yyyy-MM-dd**********/
    public String getTodayDate() {
        String dateoftodayis="";
        try {
            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);
            dateoftodayis = year+"-"+(month+1)+"-"+day;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d = df.parse(dateoftodayis);
            dateoftodayis =df.format(d);

        }catch (Exception e){
            appUtils.showLog("Expection in Today date: "+e, MainApplication.class);
        }
        return dateoftodayis;
    }

    /*********change date formate yyyy-MM-dd to dd-MM-yyyy*******/
    public String changeFormate(String inputDate) {
        String outputPattern = "dd-MM-yyyy";
        String inputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = "";

        try {
            date = inputFormat.parse(inputDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;

    }



}

package com.nrlm.agey.model;

import android.content.Context;

import com.nrlm.agey.utils.GetAllInstance;

public class TestObject {
    private static TestObject getAllInstance;

    public static TestObject getInstance() {
        if (getAllInstance == null)
            getAllInstance = new TestObject();
        return getAllInstance;
    }

    public  static TestObject resetTheInstance(){
        if (getAllInstance != null)
            getAllInstance =null;
        return getAllInstance;
    }

    /*****frag1 data**********/

    public String cat_of_vehicle="";
    public String tracking_year="";
    public String tracking_month="";
    public  String openingKm = "";
    public  String clossingKm = "";
    public  String totall_km = "1000";
    public  String amountRepaidInCurrentMonth = "";
    public  String blancedLoanAmount = "";
    public  String noOfEmiDue = "";
    public  String amountOverDue = "";
    public  String netIncomeFromAegy = "";

    /****fragment2 data************/

    public  String numberOftripForStudent = "";
    public  String vehicleRunningPredefiendRoute = "";
    public  String numberOfDaysRun = "";
    public  String numberOfTripPerDay = "";
    public  String numberOfVillage = "";
    public  String renewalOfRoadTax = "";
    public  String taxAmount = "";
    public  String insuranceRenewed = "";
    public  String isvehicleOperational = "";
    public  String ifNotReason = "";
    public  String assesmentForClfVo = "";

    /****************frag3 data***********/

    public String imageString = "";
}

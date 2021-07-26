package com.nrlm.agey.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MonthlyTrackingDataEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String vehicleRegistrationNumber;
    public String blockCode;
    public String userID;
    public String cat_of_vehicle;
    public String tracking_year;
    public String tracking_month;
    public String openingKm;
    public String closingKm;
    public String totalKm;
    public String amountRepaidInCurrentMonth;
    public String balancedLoanAmount;
    public String noOfEmiDue;
    public String amountOverDue;
    public String netIncomeFromAegy;
    public String numberOftripForStudent;
    public String vehicleRunningPredefiendRoute;
    public String numberOfDaysRun;
    public String numberOfTripPerDay;
    public String numberOfVillage;
    public String renewalOfRoadTax;
    public String taxAmount;
    public String insuranceRenewed;
    public String isvehicleOperational;
    public String ifNotReason;
    public String assesmentForClfVo;
    public String imageString;
    public String syncStatus;
}

package com.nrlm.agey.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AssignVehicleDataEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "vehicle_RegistrationNo")
    public String vehicleRegistrationNo;
    @ColumnInfo(name = "vehicle_Category")
    public String vehicleCategory;
    @ColumnInfo(name = "vehicle_Type")
    public String vehicleType;
    @ColumnInfo(name = "vehicle_Manufacturer")
    public String vehicleManufacturer;
    @ColumnInfo(name = "vehicle_Model")
    public String vehicleModel;
    @ColumnInfo(name = "vehicle_PreviousPaymentDate")
    public String vehiclePreviousPaymentDate;
    @ColumnInfo(name = "vehicle_NewPaymentYear")
    public String vehicleNewPaymentYear;
    @ColumnInfo(name = "vehicle_NewPaymentMonth")
    public String vehicleNewPaymentMonth;
    @ColumnInfo(name = "vehicle_OpenKMReading")
    public String vehicleOpenKMReading;
    @ColumnInfo(name = "vehicle_ClosingKMReading")
    public String vehicleClosingKMReading;
    @ColumnInfo(name = "vehicle_AmountRepaid")
    public String vehicleAmountRepaid;
    @ColumnInfo(name = "vehicle_BalanceLoanAmount")
    public String vehicleBalanceLoanAmount;
    @ColumnInfo(name = "vehicle_NoOfEMIOverdue")
    public String vehicleNoOfEMIOverdue;
    @ColumnInfo(name = "vehicle_NetIncomeFromAGEY")
    public String vehicleNetIncomeFromAGEY;
    @ColumnInfo(name = "vehicle_NoOfSpecialTrips")
    public String vehicleNoOfSpecialTrips;
    @ColumnInfo(name = "isVehicleRunsOnPredefinedRoute")
    public boolean isVehicleRunsOnPredefinedRoute;
    @ColumnInfo(name = "vehicle_TotalRunningDays")
    public String vehicleTotalRunningDays;
    @ColumnInfo(name = "vehicle_NoOfTripsOnPredefinedRoute")
    public String vehicleNoOfTripsOnPredefinedRoute;
    @ColumnInfo(name = "vehicle_NoOfTripsFromVillage")
    public String vehicleNoOfTripsFromVillage;
    @ColumnInfo(name = "isVehicleRenewalMonthForRoadTaxDeposit")
    public boolean isVehicleRenewalMonthForRoadTaxDeposit;
    @ColumnInfo(name = "vehicle_TaxDepositedAmount")
    public String vehicleTaxDepositedAmount;
    @ColumnInfo(name = "IsVehicleInsuranceRenewed")
    public String IsVehicleInsuranceRenewed;
    @ColumnInfo(name = "vehicle_UploadedDocument")
    public byte[] vehicleUploadedDocument;
    @ColumnInfo(name = "isVehicleOperational")
    public boolean isVehicleOperational;
    @ColumnInfo(name = "vehicle_ReasonofNotBeingOperational")
    public String vehicleReasonofNotBeingOperational;
    @ColumnInfo(name = "vehicle_Assessment")
    public String vehicleAssessment;
    @ColumnInfo(name = "vehicle_OwnBy")
    public String vehicleOwnBy;
    @ColumnInfo(name = "vehicle_TotalAMount")
    public String vehicleTotalAmount;
    @ColumnInfo(name = "vehicle_NoEmi")
    public String vehicleNoEmi;
    @ColumnInfo(name = "vehicle_Emi_price_month")
    public String vehicleEmiPrice;
    @ColumnInfo(name = "vehicle_Emi_paid")
    public String vehicleEmiPaid;
}

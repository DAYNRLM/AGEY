package com.nrlm.agey.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nrlm.agey.model.response.MainDataResponse;

import java.util.List;

@Entity
public class AssignVehicleDataEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "block_code")
    public String blockCode;
    @ColumnInfo(name = "owner_contribution")
    public String ownerContribution;
    @ColumnInfo(name = "grant_amount_received")
    public String grantAmountReceived;
    @ColumnInfo(name = "number_of_emi_paid")
    public String numberOfEmiPaid;
    @ColumnInfo(name = "total_number_of_emi")
    public String totalNumberOfEmi;
    @ColumnInfo(name = "vehicle_Category")
    public String vehicleCategory;
    @ColumnInfo(name = "vehicle_Type")
    public String vehicleType;
    @ColumnInfo(name = "vehicle_Model")
    public String vehicleModel;
    @ColumnInfo(name = "vehicle_date_of_registration")
    public String vehicleDateOfRegistration;
    @ColumnInfo(name = "vehicle_owned_by")
    public String vehicleOwnedBy;
    @ColumnInfo(name = "vehicle_reg_number")
    public String vehicleRegNumber;
    @ColumnInfo(name = "vehicle_loan_amount_from_other")
    public String vehicleLoanAmountFromOther;
    @ColumnInfo(name = "department_from_grant_amount_recived")
    public String departmentFromGrantAmountRecived;
    @ColumnInfo(name = "vehicle_loan_amount_from_clf")
    public String vehicleLoanAmountFromClf;
    @ColumnInfo(name = "vehicle_insurance_type")
    public String vehicleInsuranceType;
    @ColumnInfo(name = "vehicle_total_cost")
    public String vehicleTotalCost;
    @ColumnInfo(name = "total_amount_paid")
    public String totalAmountPaid;
    @ColumnInfo(name = "vehicle_running_in_fixed_route")
    public String vehicleRunningInFixedRoute;
    @ColumnInfo(name = "vehicle_manufacture")
    public String vehicleManufacture;
    @ColumnInfo(name = "value_per_emi")
    public String valuePerEmi;
    @ColumnInfo(name = "amount_paid_as_on")
    public String amountPaidAsOn;
    @ColumnInfo(name = "insurance_renewal_data")
    public String insuranceRenewalData;

}

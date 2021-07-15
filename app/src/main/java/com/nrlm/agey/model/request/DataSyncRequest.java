package com.nrlm.agey.model.request;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class DataSyncRequest {


    public static DataSyncRequest jsonToJava(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, DataSyncRequest.class);
    }

    public String javaToJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data {

        private String userId;
        private String stateShortName;
        private String blockCode;
        private List<VehicleDatum> vehicleData = null;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStateShortName() {
            return stateShortName;
        }

        public void setStateShortName(String stateShortName) {
            this.stateShortName = stateShortName;
        }

        public String getBlockCode() {
            return blockCode;
        }

        public void setBlockCode(String blockCode) {
            this.blockCode = blockCode;
        }

        public List<VehicleDatum> getVehicleData() {
            return vehicleData;
        }

        public void setVehicleData(List<VehicleDatum> vehicleData) {
            this.vehicleData = vehicleData;
        }

    }


    public class VehicleDatum {

        private String vehicleRegistrationNumber;
        private String vehicleCategory;
        private String totalNoOfTripsForStudent;
        private String totalNoOfDaysRun;
        private String totalNoOfRunPerDayAvg;
        private String totalNoOfVillageRun;
        private String trackYear;
        private String trackMonth;
        private String trackDateByMobile;
        private String openingKilometer;
        private String clossingKilometer;
        private String amountRepaidInThisMonth;
        private String blancedLoanAmount;
        private String noOfEmiOverdue;
        private String amountOverdue;
        private String monthlyNetIncome;
        private String vehicleRunPredefiendRoute;
        private String renewalOfRoadTax;
        private String taxDepositAmount;
        private String insuranceRenewed;
        private String uploadInsuranceDocs;
        private String vehicleOperational;
        private String reason;
        private String assesmentByClfVo;

        public String getVehicleRegistrationNumber() {
            return vehicleRegistrationNumber;
        }

        public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
            this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        }

        public String getVehicleCategory() {
            return vehicleCategory;
        }

        public void setVehicleCategory(String vehicleCategory) {
            this.vehicleCategory = vehicleCategory;
        }

        public String getTotalNoOfTripsForStudent() {
            return totalNoOfTripsForStudent;
        }

        public void setTotalNoOfTripsForStudent(String totalNoOfTripsForStudent) {
            this.totalNoOfTripsForStudent = totalNoOfTripsForStudent;
        }

        public String getTotalNoOfDaysRun() {
            return totalNoOfDaysRun;
        }

        public void setTotalNoOfDaysRun(String totalNoOfDaysRun) {
            this.totalNoOfDaysRun = totalNoOfDaysRun;
        }

        public String getTotalNoOfRunPerDayAvg() {
            return totalNoOfRunPerDayAvg;
        }

        public void setTotalNoOfRunPerDayAvg(String totalNoOfRunPerDayAvg) {
            this.totalNoOfRunPerDayAvg = totalNoOfRunPerDayAvg;
        }

        public String getTotalNoOfVillageRun() {
            return totalNoOfVillageRun;
        }

        public void setTotalNoOfVillageRun(String totalNoOfVillageRun) {
            this.totalNoOfVillageRun = totalNoOfVillageRun;
        }

        public String getTrackYear() {
            return trackYear;
        }

        public void setTrackYear(String trackYear) {
            this.trackYear = trackYear;
        }

        public String getTrackMonth() {
            return trackMonth;
        }

        public void setTrackMonth(String trackMonth) {
            this.trackMonth = trackMonth;
        }

        public String getTrackDateByMobile() {
            return trackDateByMobile;
        }

        public void setTrackDateByMobile(String trackDateByMobile) {
            this.trackDateByMobile = trackDateByMobile;
        }

        public String getOpeningKilometer() {
            return openingKilometer;
        }

        public void setOpeningKilometer(String openingKilometer) {
            this.openingKilometer = openingKilometer;
        }

        public String getClossingKilometer() {
            return clossingKilometer;
        }

        public void setClossingKilometer(String clossingKilometer) {
            this.clossingKilometer = clossingKilometer;
        }

        public String getAmountRepaidInThisMonth() {
            return amountRepaidInThisMonth;
        }

        public void setAmountRepaidInThisMonth(String amountRepaidInThisMonth) {
            this.amountRepaidInThisMonth = amountRepaidInThisMonth;
        }

        public String getBlancedLoanAmount() {
            return blancedLoanAmount;
        }

        public void setBlancedLoanAmount(String blancedLoanAmount) {
            this.blancedLoanAmount = blancedLoanAmount;
        }

        public String getNoOfEmiOverdue() {
            return noOfEmiOverdue;
        }

        public void setNoOfEmiOverdue(String noOfEmiOverdue) {
            this.noOfEmiOverdue = noOfEmiOverdue;
        }

        public String getAmountOverdue() {
            return amountOverdue;
        }

        public void setAmountOverdue(String amountOverdue) {
            this.amountOverdue = amountOverdue;
        }

        public String getMonthlyNetIncome() {
            return monthlyNetIncome;
        }

        public void setMonthlyNetIncome(String monthlyNetIncome) {
            this.monthlyNetIncome = monthlyNetIncome;
        }

        public String getVehicleRunPredefiendRoute() {
            return vehicleRunPredefiendRoute;
        }

        public void setVehicleRunPredefiendRoute(String vehicleRunPredefiendRoute) {
            this.vehicleRunPredefiendRoute = vehicleRunPredefiendRoute;
        }

        public String getRenewalOfRoadTax() {
            return renewalOfRoadTax;
        }

        public void setRenewalOfRoadTax(String renewalOfRoadTax) {
            this.renewalOfRoadTax = renewalOfRoadTax;
        }

        public String getTaxDepositAmount() {
            return taxDepositAmount;
        }

        public void setTaxDepositAmount(String taxDepositAmount) {
            this.taxDepositAmount = taxDepositAmount;
        }

        public String getInsuranceRenewed() {
            return insuranceRenewed;
        }

        public void setInsuranceRenewed(String insuranceRenewed) {
            this.insuranceRenewed = insuranceRenewed;
        }

        public String getUploadInsuranceDocs() {
            return uploadInsuranceDocs;
        }

        public void setUploadInsuranceDocs(String uploadInsuranceDocs) {
            this.uploadInsuranceDocs = uploadInsuranceDocs;
        }

        public String getVehicleOperational() {
            return vehicleOperational;
        }

        public void setVehicleOperational(String vehicleOperational) {
            this.vehicleOperational = vehicleOperational;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getAssesmentByClfVo() {
            return assesmentByClfVo;
        }

        public void setAssesmentByClfVo(String assesmentByClfVo) {
            this.assesmentByClfVo = assesmentByClfVo;
        }

    }

}

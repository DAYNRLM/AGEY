package com.nrlm.agey.model.response;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainDataResponse {


    public static MainDataResponse jsonToJava(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, MainDataResponse.class);
    }

    public String javaToJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public class Data {

        @SerializedName("assign_data")
        @Expose
        private List<AssignDatum> assignData = null;
        @SerializedName("master_data")
        @Expose
        private MasterData masterData;
        @SerializedName("user_data")
        @Expose
        private UserData userData;

        public List<AssignDatum> getAssignData() {
            return assignData;
        }

        public void setAssignData(List<AssignDatum> assignData) {
            this.assignData = assignData;
        }

        public MasterData getMasterData() {
            return masterData;
        }

        public void setMasterData(MasterData masterData) {
            this.masterData = masterData;
        }

        public UserData getUserData() {
            return userData;
        }

        public void setUserData(UserData userData) {
            this.userData = userData;
        }

    }


    public class UserData {

        @SerializedName("app_version")
        @Expose
        private String appVersion;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("server_date_time")
        @Expose
        private String serverDateTime;
        @SerializedName("language_id")
        @Expose
        private Integer languageId;
        @SerializedName("mobile_number")
        @Expose
        private String mobileNumber;
        @SerializedName("login_attempt")
        @Expose
        private Integer loginAttempt;
        @SerializedName("logout_days")
        @Expose
        private String logoutDays;
        @SerializedName("Errorstatus")
        @Expose
        private String errorstatus;

        public String getErrorstatus() {
            return errorstatus;
        }

        public void setErrorstatus(String errorstatus) {
            this.errorstatus = errorstatus;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getServerDateTime() {
            return serverDateTime;
        }

        public void setServerDateTime(String serverDateTime) {
            this.serverDateTime = serverDateTime;
        }

        public Integer getLanguageId() {
            return languageId;
        }

        public void setLanguageId(Integer languageId) {
            this.languageId = languageId;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public Integer getLoginAttempt() {
            return loginAttempt;
        }

        public void setLoginAttempt(Integer loginAttempt) {
            this.loginAttempt = loginAttempt;
        }

        public String getLogoutDays() {
            return logoutDays;
        }

        public void setLogoutDays(String logoutDays) {
            this.logoutDays = logoutDays;
        }

    }

    public class MasterData {

        @SerializedName("category_data")
        @Expose
        private List<CategoryDatum> categoryData = null;
        @SerializedName("vehicle_type")
        @Expose
        private List<VehicleType> vehicleType = null;
        @SerializedName("vehicle_manufacturer")
        @Expose
        private List<VehicleManufacturer> vehicleManufacturer = null;

        public List<CategoryDatum> getCategoryData() {
            return categoryData;
        }

        public void setCategoryData(List<CategoryDatum> categoryData) {
            this.categoryData = categoryData;
        }

        public List<VehicleType> getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(List<VehicleType> vehicleType) {
            this.vehicleType = vehicleType;
        }

        public List<VehicleManufacturer> getVehicleManufacturer() {
            return vehicleManufacturer;
        }

        public void setVehicleManufacturer(List<VehicleManufacturer> vehicleManufacturer) {
            this.vehicleManufacturer = vehicleManufacturer;
        }

    }

    public class VehicleType {

        @SerializedName("type_name")
        @Expose
        private String typeName;
        @SerializedName("type_id")
        @Expose
        private String typeId;

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

    }

    public class VehicleManufacturer {

        @SerializedName("manufacturer_id")
        @Expose
        private String manufacturerId;
        @SerializedName("vech_model_info")
        @Expose
        private List<VechModelInfo> vechModelInfo = null;
        @SerializedName("manufacturer_name")
        @Expose
        private String manufacturerName;

        public String getManufacturerId() {
            return manufacturerId;
        }

        public void setManufacturerId(String manufacturerId) {
            this.manufacturerId = manufacturerId;
        }

        public List<VechModelInfo> getVechModelInfo() {
            return vechModelInfo;
        }

        public void setVechModelInfo(List<VechModelInfo> vechModelInfo) {
            this.vechModelInfo = vechModelInfo;
        }

        public String getManufacturerName() {
            return manufacturerName;
        }

        public void setManufacturerName(String manufacturerName) {
            this.manufacturerName = manufacturerName;
        }

    }

    public class MonthlyReport {

        @SerializedName("Errorstatus")
        @Expose
        private String errorstatus;

        public String getErrorstatus() {
            return errorstatus;
        }

        public void setErrorstatus(String errorstatus) {
            this.errorstatus = errorstatus;
        }

    }

    public class VehicleDatum {
        @SerializedName("owner_contribution")
        @Expose
        private String ownerContribution;
        @SerializedName("grant_amount_received")
        @Expose
        private String grantAmountReceived;
        @SerializedName("number_of_emi_paid")
        @Expose
        private String numberOfEmiPaid;
        @SerializedName("total_number_of_emi")
        @Expose
        private String totalNumberOfEmi;
        @SerializedName("vehicle_category")
        @Expose
        private String vehicleCategory;
        @SerializedName("vehicle_date_of_registration")
        @Expose
        private String vehicleDateOfRegistration;
        @SerializedName("amount_paid_as_on")
        @Expose
        private String amountPaidAsOn;
        @SerializedName("value_per_emi")
        @Expose
        private String valuePerEmi;
        @SerializedName("vehicle_model")
        @Expose
        private String vehicleModel;
        @SerializedName("vehicle_owned_by")
        @Expose
        private String vehicleOwnedBy;
        @SerializedName("vehicle_type")
        @Expose
        private String vehicleType;
        @SerializedName("vehicle_manufacture")
        @Expose
        private String vehicleManufacture;
        @SerializedName("total_amount_paid")
        @Expose
        private String totalAmountPaid;
        @SerializedName("vehicle_running_in_fixed_route")
        @Expose
        private String vehicleRunningInFixedRoute;
        @SerializedName("vehicle_total_cost")
        @Expose
        private String vehicleTotalCost;
        @SerializedName("vehicle_insurance_type")
        @Expose
        private String vehicleInsuranceType;
        @SerializedName("vehicle_loan_amount_from_clf")
        @Expose
        private String vehicleLoanAmountFromClf;
        @SerializedName("monthly_report")
        @Expose
        private List<MonthlyReport> monthlyReport = null;
        @SerializedName("insurance_renewal_data")
        @Expose
        private String insuranceRenewalData;
        @SerializedName("department_from_grant_amount_recived")
        @Expose
        private String departmentFromGrantAmountRecived;
        @SerializedName("vehicle_reg_number")
        @Expose
        private String vehicleRegNumber;
        @SerializedName("vehicle_loan_amount_from_other")
        @Expose
        private String vehicleLoanAmountFromOther;

        public String getOwnerContribution() {
            return ownerContribution;
        }

        public void setOwnerContribution(String ownerContribution) {
            this.ownerContribution = ownerContribution;
        }

        public String getGrantAmountReceived() {
            return grantAmountReceived;
        }

        public void setGrantAmountReceived(String grantAmountReceived) {
            this.grantAmountReceived = grantAmountReceived;
        }

        public String getNumberOfEmiPaid() {
            return numberOfEmiPaid;
        }

        public void setNumberOfEmiPaid(String numberOfEmiPaid) {
            this.numberOfEmiPaid = numberOfEmiPaid;
        }

        public String getTotalNumberOfEmi() {
            return totalNumberOfEmi;
        }

        public void setTotalNumberOfEmi(String totalNumberOfEmi) {
            this.totalNumberOfEmi = totalNumberOfEmi;
        }

        public String getVehicleCategory() {
            return vehicleCategory;
        }

        public void setVehicleCategory(String vehicleCategory) {
            this.vehicleCategory = vehicleCategory;
        }

        public String getVehicleDateOfRegistration() {
            return vehicleDateOfRegistration;
        }

        public void setVehicleDateOfRegistration(String vehicleDateOfRegistration) {
            this.vehicleDateOfRegistration = vehicleDateOfRegistration;
        }

        public String getAmountPaidAsOn() {
            return amountPaidAsOn;
        }

        public void setAmountPaidAsOn(String amountPaidAsOn) {
            this.amountPaidAsOn = amountPaidAsOn;
        }

        public String getValuePerEmi() {
            return valuePerEmi;
        }

        public void setValuePerEmi(String valuePerEmi) {
            this.valuePerEmi = valuePerEmi;
        }

        public String getVehicleModel() {
            return vehicleModel;
        }

        public void setVehicleModel(String vehicleModel) {
            this.vehicleModel = vehicleModel;
        }

        public String getVehicleOwnedBy() {
            return vehicleOwnedBy;
        }

        public void setVehicleOwnedBy(String vehicleOwnedBy) {
            this.vehicleOwnedBy = vehicleOwnedBy;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public String getVehicleManufacture() {
            return vehicleManufacture;
        }

        public void setVehicleManufacture(String vehicleManufacture) {
            this.vehicleManufacture = vehicleManufacture;
        }

        public String getTotalAmountPaid() {
            return totalAmountPaid;
        }

        public void setTotalAmountPaid(String totalAmountPaid) {
            this.totalAmountPaid = totalAmountPaid;
        }

        public String getVehicleRunningInFixedRoute() {
            return vehicleRunningInFixedRoute;
        }

        public void setVehicleRunningInFixedRoute(String vehicleRunningInFixedRoute) {
            this.vehicleRunningInFixedRoute = vehicleRunningInFixedRoute;
        }

        public String getVehicleTotalCost() {
            return vehicleTotalCost;
        }

        public void setVehicleTotalCost(String vehicleTotalCost) {
            this.vehicleTotalCost = vehicleTotalCost;
        }

        public String getVehicleInsuranceType() {
            return vehicleInsuranceType;
        }

        public void setVehicleInsuranceType(String vehicleInsuranceType) {
            this.vehicleInsuranceType = vehicleInsuranceType;
        }

        public String getVehicleLoanAmountFromClf() {
            return vehicleLoanAmountFromClf;
        }

        public void setVehicleLoanAmountFromClf(String vehicleLoanAmountFromClf) {
            this.vehicleLoanAmountFromClf = vehicleLoanAmountFromClf;
        }

        public List<MonthlyReport> getMonthlyReport() {
            return monthlyReport;
        }

        public void setMonthlyReport(List<MonthlyReport> monthlyReport) {
            this.monthlyReport = monthlyReport;
        }

        public String getInsuranceRenewalData() {
            return insuranceRenewalData;
        }

        public void setInsuranceRenewalData(String insuranceRenewalData) {
            this.insuranceRenewalData = insuranceRenewalData;
        }

        public String getDepartmentFromGrantAmountRecived() {
            return departmentFromGrantAmountRecived;
        }

        public void setDepartmentFromGrantAmountRecived(String departmentFromGrantAmountRecived) {
            this.departmentFromGrantAmountRecived = departmentFromGrantAmountRecived;
        }

        public String getVehicleRegNumber() {
            return vehicleRegNumber;
        }

        public void setVehicleRegNumber(String vehicleRegNumber) {
            this.vehicleRegNumber = vehicleRegNumber;
        }

        public String getVehicleLoanAmountFromOther() {
            return vehicleLoanAmountFromOther;
        }

        public void setVehicleLoanAmountFromOther(String vehicleLoanAmountFromOther) {
            this.vehicleLoanAmountFromOther = vehicleLoanAmountFromOther;
        }

    }

    public class VechModelInfo {

        @SerializedName("veh_model_id")
        @Expose
        private String vehModelId;
        @SerializedName("veh_model_name")
        @Expose
        private String vehModelName;

        public String getVehModelId() {
            return vehModelId;
        }

        public void setVehModelId(String vehModelId) {
            this.vehModelId = vehModelId;
        }

        public String getVehModelName() {
            return vehModelName;
        }

        public void setVehModelName(String vehModelName) {
            this.vehModelName = vehModelName;
        }

    }

    public class AssignDatum {

        @SerializedName("block_name")
        @Expose
        private String blockName;
        @SerializedName("vehicle_data")
        @Expose
        private List<VehicleDatum> vehicleData = null;
        @SerializedName("block_code")
        @Expose
        private String blockCode;

        public String getBlockName() {
            return blockName;
        }

        public void setBlockName(String blockName) {
            this.blockName = blockName;
        }

        public List<VehicleDatum> getVehicleData() {
            return vehicleData;
        }

        public void setVehicleData(List<VehicleDatum> vehicleData) {
            this.vehicleData = vehicleData;
        }

        public String getBlockCode() {
            return blockCode;
        }

        public void setBlockCode(String blockCode) {
            this.blockCode = blockCode;
        }

    }

    public class CategoryDatum {


        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("category_id")
        @Expose
        private String categoryId;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

    }
}

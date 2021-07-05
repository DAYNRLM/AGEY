package com.nrlm.agey.utils

import android.view.View
import com.nrlm.agey.R

public class AppConstant {
    companion object ConstantObject {
        const val PREF_KEY_SHG_CODE = "shgCode"
         val language_english = arrayOf("English", "Hindi", "Bengali", "Tamil", "Gujarati", "Khasi", "Malayalam", "Odia", "Punjabi", "Assamese", "Mizo", "Marathi")
         val local_language = arrayOf("English", "हिन्दी", "বাঙালি", "Tamil", "ગુજરાતી", "Khasi", "മലയാളം", "ଓଡ଼ିଆ", "ਪੰਜਾਬੀ", " অসমীয়া", "Mizo", "Marathi")
         val language_id = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")
         val language_code = arrayOf("en", "hi", "bn", "ta", "gu", "kha", "ml", "or", "pa", "as", "mi", "mr")



       /* fun getRsSymbol(str: String): String? {
           // return str + " " + view.context.resources.getString(R.string.Rs_new)
            // android:text='@{appConstant.getRsSymbol(assignVehicleModel.vehicleTotalAmount !=null ? assignVehicleModel.vehicleTotalAmount : "" )'
            return "$str Rs.";
        }*/


        //test data for vehicle registration

        val vehicleNumber = arrayOf("HR-12U-2871", "HR-13U-2334", "HR-13U-5678", "HR-12U-1432", "HR-11U-5643", "HR-52U-7865", "HR-17U-3453");
        val vehicleOwnedBy = arrayOf("CLF", "VO", "CLF", "CLF", "VO", "CLF", "VO");
        val vehicleCat = arrayOf(" Passenger", "Goods", "Passenger ", "Goods", "Passenger ", "Goods", "Passenger");
        val vehicleType = arrayOf("E Rickshaw", "3 wheeler Aut", "4 wheeler - LMV", "4 wheeler HMV", "E Rickshaw", "4 wheeler HTV", "Two Wheeler");
        val vehicleManf = arrayOf("Tata", "Ashok Leyland", "Eicher", "Mahindra", "Bajaj", "Mahindra", "Tata");
        val vehicleModel = arrayOf("2012", "2019", "2017", "2015", "2016", "2020", "2017");
        val vehicleCost = arrayOf("120000", "234000", "453000", "546670", "140000", "762000", "320300");
        val vehicleLoanAmount = arrayOf("80000", "190000", "420000", "510000", "90000", "500000", "295000");
        val vehicleNoEmi = arrayOf("10", "15", "10", "20", "8", "14", "10");
        val vehiclePerEmi = arrayOf("8000", "20000", "1000", "3000", "4300", "2300", "2190");
        val vehicleAnountPaid = arrayOf("50000", "100000", "320000", "100000", "300000", "230000", "40000");
        val vehiclePaidYear = arrayOf("June 2021", "Mar 2020", "April 2020", "Sep 2012", "Oct 2017", "Dec 2020", "Jan 2019");
        val vehiclePaidEmi = arrayOf("2", "8", "11", "4", "6", "10", "6");


    }
}
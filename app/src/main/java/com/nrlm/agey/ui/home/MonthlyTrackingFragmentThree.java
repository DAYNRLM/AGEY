package com.nrlm.agey.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SyncRequest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;

import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.nrlm.agey.databinding.FragmentMonthlyTrackingThreeBinding;
import com.nrlm.agey.databinding.FragmentMonthlyTrackingTwoBinding;
import com.nrlm.agey.model.LoginError;
import com.nrlm.agey.model.TestObject;
import com.nrlm.agey.model.request.DataSyncRequest;
import com.nrlm.agey.network.vollyCall.VolleyResult;
import com.nrlm.agey.repository.HomeRepository;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.ui.login.AuthFragment;
import com.nrlm.agey.utils.NetworkUtils;
import com.nrlm.agey.utils.ViewUtilsKt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class MonthlyTrackingFragmentThree extends BaseFragment<HomeViewModel, FragmentMonthlyTrackingThreeBinding, HomeRepository,HomeViewModelFactory> {

    TestObject testObject;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private byte[] imageByteArray;

    LayoutInflater layoutInflater;

    @Override
    public Class<HomeViewModel> getViewModel() {
            return HomeViewModel.class;
    }

    @Override
    public FragmentMonthlyTrackingThreeBinding getFragmentBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        layoutInflater = inflater;
        return FragmentMonthlyTrackingThreeBinding.inflate(inflater, container, false);
    }

    @Override
    public HomeRepository getFragmentRepository() {
        return HomeRepository.getInstance(getActivity().getApplication());
    }

    @Override
    public Context getCurrentContext() {
        return getContext();
    }

    @Override
    public HomeViewModelFactory getFactory() {
        return  new HomeViewModelFactory(getFragmentRepository());
    }

    @Override
    public void onFragmentReady() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        testObject =TestObject.getInstance();
        binding.tvRegNumber.setText(appSharedPreferences.getVehicleRegNum());

        Observer<Boolean> resetObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean newName) {
                NavDirections action =MonthlyTrackingFragmentThreeDirections.actionMonthlyTrackingFragmentThreeToAssignVehicleFragment();
                navController.navigate(action);

            }
        };

        binding.btnSaveData.setOnClickListener(view1 -> {

            if(NetworkUtils.isInternetOn(getContext())){
                customProgressDialog.showProgress("loading", false);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("user_id",appSharedPreferences.getValidUserId());
                    jsonObject.accumulate("state_short_name","HR");
                    jsonObject.accumulate("block_code","234567");
                    jsonObject.accumulate("vehicle_data",getVehicleArray());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mResultCallBack =new VolleyResult() {
                    @Override
                    public void notifySuccess(String requestType, JSONObject response) {
                        customProgressDialog.hideProgress();
                        LoginError loginError = new LoginError();
                        try {
                            if(response.has("data_Sync")){
                                String data_Sync = response.getString("data_Sync");
                                if(data_Sync.equalsIgnoreCase("Success")){
                                    loginError.imageId="1";
                                    loginError.errorMessage="Data Saved Successfully";
                                    loginError.errorDetail="Data saved for "+(testObject.tracking_month+1)+"/"+testObject.tracking_year+" is successfully";
                                    viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                            .observe(getViewLifecycleOwner(), resetObserver);
                                }else {
                                    loginError.imageId="0";
                                    loginError.errorMessage="Data Not Saved";
                                    loginError.errorDetail="Data not save due to duplicate entry for "+(testObject.tracking_month+1)+"/"+testObject.tracking_year;
                                    viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                            .observe(getViewLifecycleOwner(), resetObserver);

                                }
                            }else {
                                //if data sync tag is not avalable show dilog data not saved
                                loginError.imageId="0";
                                loginError.errorMessage="Some thing wrong";
                                loginError.errorDetail="Please try after some time";
                                viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                        .observe(getViewLifecycleOwner(), resetObserver);

                            }
                        }catch (Exception e){

                        }


                    }

                    @Override
                    public void notifyError(String requestType, VolleyError error) {
                        customProgressDialog.hideProgress();
                        LoginError loginError = new LoginError();
                        loginError.imageId="0";
                        loginError.errorMessage="Server Error!!";
                        loginError.errorDetail="Check your internet connectivity/Please try after some time";
                        viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                .observe(getViewLifecycleOwner(), resetObserver);

                    }
                };
                volleyService.postDataVolley("syncData","https://nrlm.gov.in/nrlmwebservicedemo/services/ageysync/data",jsonObject,mResultCallBack);

                //{"data_Sync":"Success"}
            }else {
                appUtils.showLog("*****NetWork is OFFF****", AuthFragment.class);
                viewModel.noInterNetConnection(getCurrentContext());
            }


        });
        binding.btnEdit.setOnClickListener(view1 -> {
            ViewUtilsKt.tost(getCurrentContext(),"Currently not working..");
        });

        binding.btnConfirm.setOnClickListener(view1 -> {
            String encodedimage =  Base64.encodeToString(imageByteArray, Base64.DEFAULT);
            JSONObject imag = new JSONObject();
            try {
                imag.accumulate("image",encodedimage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            appUtils.showLog("image is :>>>>:   "+imag.toString(),MonthlyTrackingFragmentThree.class);
            ViewUtilsKt.tost(getCurrentContext(),"Currently not working..");
        });

        binding.btnTakePhoto.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
            else
            {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    private JSONArray getVehicleArray() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("vehicle_registration_number",appSharedPreferences.getVehicleRegNum());
            jsonObject.accumulate("vehicle_category",testObject.cat_of_vehicle);
            jsonObject.accumulate("total_no_of_trips_for_student",testObject.numberOftripForStudent);
            jsonObject.accumulate("total_no_of_days_run",testObject.numberOfDaysRun);
            jsonObject.accumulate("total_no_of_run_per_day_avg",testObject.numberOfTripPerDay);
            jsonObject.accumulate("total_no_of_village_run",testObject.numberOfVillage);
            jsonObject.accumulate("track_year",testObject.tracking_year);
            jsonObject.accumulate("track_month",testObject.tracking_month);
            jsonObject.accumulate("track_date_by_mobile",testObject.cat_of_vehicle);
            jsonObject.accumulate("opening_kilometer",testObject.openingKm);
            jsonObject.accumulate("clossing_kilometer",testObject.clossingKm);
            jsonObject.accumulate("amount_repaid_in_this_month",testObject.amountRepaidInCurrentMonth);
            jsonObject.accumulate("blanced_loan_amount",testObject.blancedLoanAmount);
            jsonObject.accumulate("no_of_emi_overdue",testObject.noOfEmiDue);
            jsonObject.accumulate("amount_overdue",testObject.amountOverDue);
            jsonObject.accumulate("monthly_net_income",testObject.netIncomeFromAegy);
            jsonObject.accumulate("vehicle_run_predefiend_route",testObject.vehicleRunningPredefiendRoute);
            jsonObject.accumulate("renewal_of_road_tax",testObject.renewalOfRoadTax);
            jsonObject.accumulate("tax_deposit_amount",testObject.taxAmount);
            jsonObject.accumulate("insurance_renewed",testObject.insuranceRenewed);
            jsonObject.accumulate("upload_insurance_docs","");
            jsonObject.accumulate("vehicle_operational",testObject.isvehicleOperational);
            jsonObject.accumulate("reason",testObject.ifNotReason);
            jsonObject.accumulate("assesment_by_clf_vo",testObject.assesmentForClfVo);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                ViewUtilsKt.tost(getCurrentContext(),"camera permission denied");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        binding.imageView.setImageBitmap(photo);


                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.PNG, 100 , baos);

                        imageByteArray = baos.toByteArray();

                    }
                }, 1000);
            }

        }

    }
}

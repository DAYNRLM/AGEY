package com.nrlm.agey.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.VolleyError;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nrlm.agey.BuildConfig;
import com.nrlm.agey.R;
import com.nrlm.agey.database.AppDatabase;
import com.nrlm.agey.database.entity.UserDetailEntity;
import com.nrlm.agey.databinding.DialogErrorMessageBinding;
import com.nrlm.agey.databinding.FragmentAuthBinding;
import com.nrlm.agey.model.JplaceholderTest;
import com.nrlm.agey.model.LoginError;
import com.nrlm.agey.model.request.LoginRequest;
import com.nrlm.agey.model.response.Example;
import com.nrlm.agey.model.response.MainDataResponse;
import com.nrlm.agey.network.retrofitCall.RetrofitClient;
import com.nrlm.agey.network.vollyCall.VolleyResult;
import com.nrlm.agey.network.vollyCall.VolleyService;
import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.repository.BaseAllRepository;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.ui.home.HomeViewModelFactory;
import com.nrlm.agey.ui.mpin.MpinActivity;
import com.nrlm.agey.utils.CustomProgressDialog;
import com.nrlm.agey.utils.NetworkUtils;
import com.nrlm.agey.utils.ViewUtilsKt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthFragment extends BaseFragment<AuthViewModel, FragmentAuthBinding, AppRepository, AuthViewModelfactory> {
    String userId = "";
    String password = "";

    LayoutInflater layoutInflater;


    @Override
    public Class<AuthViewModel> getViewModel() {
        return AuthViewModel.class;
    }

    @Override
    public FragmentAuthBinding getFragmentBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        layoutInflater = inflater;
        return FragmentAuthBinding.inflate(inflater, container, false);
    }

    @Override
    public AppRepository getFragmentRepository() {
        return AppRepository.getInstance(getActivity().getApplication());
    }

    @Override
    public Context getCurrentContext() {
        return getContext();
    }

    @Override
    public AuthViewModelfactory getFactory() {
        return new AuthViewModelfactory(getFragmentRepository());
    }

    @Override
    public void onFragmentReady() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appUtils.showLog("Device info: " + appSharedPreferences.getDeviceInfo(), AuthFragment.class);

        Observer<Boolean> resetObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean newName) {
                binding.etPassword.setText("");
                binding.etUserId.setText("");
            }
        };

        binding.btnLogin.setOnClickListener(view1 -> {
            userId = binding.etUserId.getText().toString();
            password = binding.etPassword.getText().toString();

            appUtils.showLog("IMEI info: " + deviceUtils.getIMEINo1(), AuthFragment.class);

            String imei =deviceUtils.getIMEINo1();

            if (NetworkUtils.isInternetOn(getCurrentContext())) {
                if (userId.isEmpty() || password.isEmpty()) {
                    ViewUtilsKt.tost(getContext(), "Enter user name and password");
                } else {
                    customProgressDialog.showProgress("loading", false);
                    try {
                        LoginRequest loginRequest = new LoginRequest();
                        loginRequest.userId = userId.toUpperCase();
                        loginRequest.password = appUtils.getSha256(password);
                        loginRequest.deviceName = deviceUtils.getDeviceInfo();
                        loginRequest.appVersion = BuildConfig.VERSION_NAME;
                        loginRequest.todayDate = getAllInstance.dateFactroy.getTodayDate();
                        loginRequest.locCoordinate = "00.00";
                        loginRequest.appLoginTime = appUtils.getRandomOtp();// change on every request
                        loginRequest.logoutTime = "";
                        loginRequest.androidVersion = "0";
                        loginRequest.androidApiVersion = "0";
                        loginRequest.appRequest="";
                        loginRequest.deviceImei = "083f1df032b675b3";


                        JSONObject logInObject = new JSONObject(loginRequest.javaToJson());
                       /* logInObject.accumulate("user_id",userId.toUpperCase());
                        logInObject.accumulate("user_password",appUtils.getSha256(password));
                        logInObject.accumulate("IMEI",imei);
                        logInObject.accumulate("device_name",deviceUtils.getDeviceInfo());
                        logInObject.accumulate("app_version",BuildConfig.VERSION_NAME);
                        logInObject.accumulate("date",getAllInstance.dateFactroy.getTodayDate());
                        logInObject.accumulate("location_coordinate","0.0");
                        logInObject.accumulate("app_login_time","0");
                        logInObject.accumulate("app_request","0");
                        logInObject.accumulate("logout_time","0");
                        logInObject.accumulate("android_version","0");
                        logInObject.accumulate("android_api_version","0");*/
                        appUtils.showLog("JSON OBJECT::" + logInObject.toString(), AuthFragment.class);

                        mResultCallBack = new VolleyResult() {
                            @Override
                            public void notifySuccess(String requestType, JSONObject response) {
                                customProgressDialog.hideProgress();
                                LoginError loginError = new LoginError();
                                appUtils.showLog("volly response::" + response.toString(), AuthFragment.class);
                                try {
                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    JSONObject dataObject = jsonObject.getJSONObject("data");
                                    JSONObject userObject = dataObject.getJSONObject("user_data");
                                    if (userObject.has("Errorstatus")) {
                                        String errorMessage = userObject.getString("Errorstatus");

                                        if (errorMessage.equalsIgnoreCase("Invalid UserID !!!")) {
                                            loginError.imageId="0";
                                            loginError.errorMessage = errorMessage;
                                            loginError.errorDetail = "Please check your UserId & Password";
                                            viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                                    .observe(getViewLifecycleOwner(), resetObserver);
                                        } else if (errorMessage.equalsIgnoreCase("Invalid Password!!!")) {
                                            loginError.imageId="0";
                                            loginError.errorMessage = errorMessage;
                                            loginError.errorDetail = "Please check your UserId & Password";
                                            viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                                    .observe(getViewLifecycleOwner(), resetObserver);

                                        } else if (errorMessage.equalsIgnoreCase("Invalid Login !!!")) {
                                            loginError.imageId="0";
                                            loginError.errorMessage = errorMessage;
                                            loginError.errorDetail = "Please check your UserId & Password";
                                            viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                                    .observe(getViewLifecycleOwner(), resetObserver);


                                        } else {

                                        }

                                    } else {
                                        MainDataResponse monthlyTrackingDataEntity = MainDataResponse.jsonToJava(response.toString());
                                        viewModel.insertLoginData(monthlyTrackingDataEntity);
                                        appSharedPreferences.setValidUserId(userId);
                                        appSharedPreferences.setLoginStatus("done");
                                        appSharedPreferences.setImeiNumber(getAllInstance.deviceUtils.getIMEINo1());
                                        appSharedPreferences.setDeviceInfo(getAllInstance.deviceUtils.getDeviceInfo());
                                        Intent intent = new Intent(getContext(), MpinActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void notifyError(String requestType, VolleyError error) {
                                customProgressDialog.hideProgress();
                                appUtils.showLog("volly rerror::" + error.toString(), AuthFragment.class);
                                LoginError loginError = new LoginError();
                                loginError.imageId="0";
                                loginError.errorDetail="Server Error!!";
                                loginError.errorDetail="Check your internet connectivity/Please try after some time";
                                viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                        .observe(getViewLifecycleOwner(), resetObserver);

                            }
                        };
                        volleyService.postDataVolley("login", "https://nrlm.gov.in/nrlmwebservicedemo/services/agey/login", logInObject, mResultCallBack);


                    } catch (Exception e) {
                        appUtils.showLog("eee" + e.toString(), AuthFragment.class);
                    }
                }

            } else {
                appUtils.showLog("*****NetWork is OFFF****", AuthFragment.class);
                viewModel.noInterNetConnection(getCurrentContext());
            }
        });

        binding.tvForgetPassword.setOnClickListener(view1 -> {
            NavDirections action = AuthFragmentDirections.actionAuthFragmentToForgetPasswordFragment();
            navController.navigate(action);
        });
    }

    private void callObserver() {
        final Observer<Boolean> nameObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean newName) {
                customProgressDialog.hideProgress();
                appSharedPreferences.setValidUserId(userId);
                Intent intent = new Intent(getContext(), MpinActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        };

        Observer<String> responseObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                customProgressDialog.hideProgress();
                ViewUtilsKt.tost(getCurrentContext(), "Login done ");
            }
        };


        //  viewModel.getAuthResponse(logInObject);

                       /*// viewModel.getTestApi(jsonObject).observe(getViewLifecycleOwner(), responseObserver);
                        // viewModel.loginAuthentication(jsonObject);
                        //  viewModel.testApiiii(jplaceholderTest);
                        //  viewModel.getDataFromApi();
                        viewModel.getTestApi(jsonObject);*/



                   /* viewModel.show().observe(getViewLifecycleOwner(), nameObserver);
                    appSharedPreferences.setLoginStatus("done");*/
    }

}



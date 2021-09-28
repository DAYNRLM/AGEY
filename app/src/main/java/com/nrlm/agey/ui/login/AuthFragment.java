package com.nrlm.agey.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import com.nrlm.agey.BuildConfig;
import com.nrlm.agey.MainActivity;
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
import com.nrlm.agey.utils.Cryptography;
import com.nrlm.agey.utils.CustomProgressDialog;
import com.nrlm.agey.utils.NetworkUtils;
import com.nrlm.agey.utils.PrefrenceManager;
import com.nrlm.agey.utils.ViewUtilsKt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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

        //Invalid date!!!

        binding.btnLogin.setOnClickListener(view1 -> {
            userId = binding.etUserId.getText().toString();
            password = binding.etPassword.getText().toString();

            appUtils.showLog("IMEI info: " + deviceUtils.getIMEINo1(), AuthFragment.class);
            appUtils.showLog("password: " +appUtils.getSha256(password), AuthFragment.class);

            String imei =deviceUtils.getIMEINo1();

            if (NetworkUtils.isInternetOn(getCurrentContext())) {
                if (userId.isEmpty() || password.isEmpty()) {
                    ViewUtilsKt.tost(getContext(), getCurrentContext().getResources().getString(R.string.toast_enter_userName_password));
                } else {
                    customProgressDialog.showProgress(getCurrentContext().getResources().getString(R.string.dialog_loading), false);
                    try {
                        LoginRequest loginRequest = new LoginRequest();
                        loginRequest.userId = userId.toUpperCase();
                        loginRequest.password = appUtils.getSha256(password);//"c6024fd19953c32dc6e2b8fe91684a16a889cc8482157f1ec652616517537239";
                        loginRequest.deviceName = deviceUtils.getDeviceInfo();
                        loginRequest.appVersion = BuildConfig.VERSION_NAME;
                        loginRequest.todayDate = getAllInstance.dateFactroy.getTodayDate();
                        loginRequest.locCoordinate = "00.00";
                        loginRequest.appLoginTime = appUtils.getRandomOtp();
                        loginRequest.logoutTime = appSharedPreferences.getLogOutTime();
                        loginRequest.androidVersion = "0";
                        loginRequest.androidApiVersion = "0";
                        loginRequest.appRequest="";
                        loginRequest.deviceImei = "083f1df032b675b3";//


                        JSONObject logInObject = new JSONObject(loginRequest.javaToJson());



                        JSONObject encryptedObject =new JSONObject();
                        try {
                            Cryptography cryptography = new Cryptography();

                            encryptedObject.accumulate("data",cryptography.encrypt(logInObject.toString()));
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (NoSuchPaddingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        } catch (InvalidAlgorithmParameterException e) {
                            e.printStackTrace();
                        } catch (IllegalBlockSizeException e) {
                            e.printStackTrace();
                        } catch (BadPaddingException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        appUtils.showLog("JSON encrypted::" + encryptedObject.toString(), AuthFragment.class);

                        mResultCallBack = new VolleyResult() {
                            @Override
                            public void notifySuccess(String requestType, JSONObject response) {
                                customProgressDialog.hideProgress();
                                LoginError loginError = new LoginError();
                                appUtils.showLog(":::::volly response::" + response.toString(), AuthFragment.class);
                                //{"data":"i+ZwCwPQ1HOGK6Ub8K5JZTs18ADdBC+6lgfaY0wvjg+5MZbedSs+vqMk9ECjPjtXz3ChTcydJ+Q5\r\na7PRC4G2VQLSv4lA9kOAX4RCueNyW3hGI45skrmP1lmAIv7iliwA"}
                                //{"data":{"user_data":{"Errorstatus":"Invalid UserID !!!"}},"message":"success","status":1}

                                try {
                                    JSONObject jsonObject;
                                    String convertedData = "";

                                    if(response.has("data")){
                                        String getData =response.getString("data");
                                        Cryptography cryptography = new Cryptography();
                                        convertedData =cryptography.decrypt(getData);
                                        appUtils.showLog("******decrypt response******"+cryptography.decrypt(getData), AuthFragment.class);

                                        jsonObject  = new JSONObject( cryptography.decrypt(getData));

                                    }else {
                                        jsonObject  = new JSONObject(response.toString());
                                    }

                                    String msgStatus = jsonObject.getString("message");

                                    if(msgStatus.equalsIgnoreCase("failure")){
                                        viewModel.noInterNetConnection(getCurrentContext());
                                    }else {
                                        JSONObject dataObject = jsonObject.getJSONObject("data");
                                        JSONObject userObject = dataObject.getJSONObject("user_data");
                                        if (userObject.has("Errorstatus")) {
                                            String errorMessage = userObject.getString("Errorstatus");
                                            if (errorMessage.equalsIgnoreCase("Invalid UserID !!!")) {
                                                loginError.imageId="0";
                                                loginError.errorMessage = errorMessage;
                                                loginError.errorDetail = getCurrentContext().getResources().getString(R.string.error_invalid_userId);
                                                viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                                        .observe(getViewLifecycleOwner(), resetObserver);
                                            } else if (errorMessage.equalsIgnoreCase("Invalid Password!!!")) {
                                                loginError.imageId="0";
                                                loginError.errorMessage = errorMessage;
                                                loginError.errorDetail = getCurrentContext().getResources().getString(R.string.error_invalid_userId);;
                                                viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                                        .observe(getViewLifecycleOwner(), resetObserver);

                                            } else if (errorMessage.equalsIgnoreCase("Invalid Login !!!")) {
                                                loginError.imageId="0";
                                                loginError.errorMessage = errorMessage;
                                                loginError.errorDetail = getCurrentContext().getResources().getString(R.string.error_invalid_userId);
                                                viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                                        .observe(getViewLifecycleOwner(), resetObserver);
                                            }else if (errorMessage.equalsIgnoreCase("Invalid date!!!")) {
                                                loginError.imageId="0";
                                                loginError.errorMessage = errorMessage;
                                                loginError.errorDetail = "Check your device Date and Time";
                                                viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                                        .observe(getViewLifecycleOwner(), resetObserver);
                                            } else if(errorMessage.equalsIgnoreCase(" Please wait for 15 minutes you exceed limit more than 5 !!!")){
                                                loginError.imageId="0";
                                                loginError.errorMessage = errorMessage;
                                                loginError.errorDetail = "Try after 15 Minutes!!!";
                                                viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                                        .observe(getViewLifecycleOwner(), resetObserver);
                                            } else{
                                                //This IMEI No. is used by another user
                                                loginError.imageId="0";
                                                loginError.errorMessage = errorMessage;
                                                loginError.errorDetail = "You are already logged in on This mobile";
                                                viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                                        .observe(getViewLifecycleOwner(), resetObserver);
                                            }

                                        } else {
                                            // MainDataResponse monthlyTrackingDataEntity = MainDataResponse.jsonToJava(response.toString());


                                            MainDataResponse monthlyTrackingDataEntity = MainDataResponse.jsonToJava(convertedData);
                                            viewModel.insertLoginData(monthlyTrackingDataEntity);
                                            appSharedPreferences.setValidUserId(userId);
                                            appSharedPreferences.setLoginStatus("done");
                                            appSharedPreferences.setImeiNumber(getAllInstance.deviceUtils.getIMEINo1());
                                            appSharedPreferences.setDeviceInfo(getAllInstance.deviceUtils.getDeviceInfo());
                                            Intent intent = new Intent(getContext(), MpinActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (NoSuchPaddingException e) {
                                    e.printStackTrace();
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                } catch (InvalidKeyException e) {
                                    e.printStackTrace();
                                } catch (InvalidAlgorithmParameterException e) {
                                    e.printStackTrace();
                                } catch (IllegalBlockSizeException e) {
                                    e.printStackTrace();
                                } catch (BadPaddingException e) {
                                    e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void notifyError(String requestType, VolleyError error) {
                                customProgressDialog.hideProgress();
                                appUtils.showLog("volly rerror::" + error.toString(), AuthFragment.class);
                                LoginError loginError = new LoginError();
                                loginError.imageId="0";
                                loginError.errorMessage=getCurrentContext().getResources().getString(R.string.error_server_error);
                                loginError.errorDetail=getCurrentContext().getResources().getString(R.string.error_server_msg);
                                viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                        .observe(getViewLifecycleOwner(), resetObserver);

                            }
                        };
                        //https://nrlm.gov.in/nrlmwebservicedemo/services/agey/login
                        appUtils.showLog("URL::::"+ PrefrenceManager.LOGIN_URL,AuthFragment.class);
                        appUtils.showLog("ENCRYPTED OBJECT::::"+ encryptedObject,AuthFragment.class);

                        volleyService.postDataVolley("synData", PrefrenceManager.LOGIN_URL, encryptedObject, mResultCallBack);
                        //volleyService.postDataVolley("synData", "https://nrlm.gov.in/nrlmwebservice/services/agey/login", encryptedObject, mResultCallBack);


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
            NavDirections action = AuthFragmentDirections.actionAuthFragmentToSendOtpFragment();
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
                ViewUtilsKt.tost(getCurrentContext(), getCurrentContext().getResources().getString(R.string.toast_login_done));
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
    }

}



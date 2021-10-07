package com.nrlm.agey.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;

import com.android.volley.VolleyError;
import com.nrlm.agey.R;
import com.nrlm.agey.databinding.FragmentAuthBinding;
import com.nrlm.agey.databinding.FragmentSendOtpBinding;
import com.nrlm.agey.model.LoginError;
import com.nrlm.agey.network.vollyCall.VolleyResult;
import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.utils.Cryptography;
import com.nrlm.agey.utils.PrefrenceManager;
import com.nrlm.agey.utils.ViewUtilsKt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SendOtpFragment extends BaseFragment<AuthViewModel, FragmentSendOtpBinding, AppRepository,AuthViewModelfactory>  {
    LayoutInflater layoutInflater;
    String generatedOtp;
    @Override
    public Class<AuthViewModel> getViewModel() {
        return AuthViewModel.class;
    }

    @Override
    public FragmentSendOtpBinding getFragmentBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        layoutInflater = inflater;
        return FragmentSendOtpBinding.inflate(inflater, container, false);
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


        Observer<Boolean> resetObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean newName) {
                binding.etMobileNumber.setText("");
            }
        };


        binding.btnSendOtp.setOnClickListener(view1 -> {

            if(binding.etMobileNumber.getText().toString().isEmpty()||binding.etMobileNumber.getText().toString().length()<10){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_enter_10digit_mobile_number));
            }else {
                customProgressDialog.showProgress(getCurrentContext().getString(R.string.dialog_loading),false);
                JSONObject otpObject =new JSONObject();
                try {
                    generatedOtp =appUtils.getRandomOtp();
                    otpObject.accumulate("mobileno",binding.etMobileNumber.getText().toString());
                    otpObject.accumulate("message",generatedOtp);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JSONObject encryptedObject =new JSONObject();
                try {
                    Cryptography cryptography = new Cryptography();
                    encryptedObject.accumulate("data",cryptography.encrypt(otpObject.toString()));
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
                // {"status":"This mobile no. is not exist"}

                mResultCallBack=new VolleyResult() {
                    @Override
                    public void notifySuccess(String requestType, JSONObject response) {
                        customProgressDialog.hideProgress();
                        LoginError loginError = new LoginError();
                        try {
                            if(response.has("status")){
                                String status = response.getString("status");
                                if(status.equalsIgnoreCase("This mobile no. is not exist")){
                                    loginError.imageId="0";
                                    loginError.errorMessage = status;
                                    loginError.errorDetail = "This mobile number is not Registered, Please try with Registered Mobile number";
                                    viewModel.showErrorDialog(loginError, getCurrentContext(), layoutInflater)
                                            .observe(getViewLifecycleOwner(), resetObserver);
                                    //8791551593
                                }else {
                                   // customProgressDialog.hideProgress();
                                    appSharedPreferences.setGenOtp(generatedOtp);
                                    NavDirections action = SendOtpFragmentDirections.actionSendOtpFragmentToForgetPasswordFragment();
                                    navController.navigate(action);
                                }
                            }
                        }catch (Exception e){
                            appUtils.showLog("Expection in response" + e.toString(), AuthFragment.class);
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
                volleyService.postDataVolley("sendOTP", PrefrenceManager.SEND_OTP_URL,encryptedObject,mResultCallBack);


               /* Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        customProgressDialog.hideProgress();
                        NavDirections action = SendOtpFragmentDirections.actionSendOtpFragmentToForgetPasswordFragment();
                        navController.navigate(action);
                    }
                }, 2000);*/
            }

        });
    }
}

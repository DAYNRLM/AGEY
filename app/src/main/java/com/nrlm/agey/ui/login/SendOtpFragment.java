package com.nrlm.agey.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;

import com.android.volley.VolleyError;
import com.nrlm.agey.R;
import com.nrlm.agey.databinding.FragmentAuthBinding;
import com.nrlm.agey.databinding.FragmentSendOtpBinding;
import com.nrlm.agey.network.vollyCall.VolleyResult;
import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.utils.ViewUtilsKt;

import org.json.JSONException;
import org.json.JSONObject;

public class SendOtpFragment extends BaseFragment<AuthViewModel, FragmentSendOtpBinding, AppRepository,AuthViewModelfactory>  {
    LayoutInflater layoutInflater;
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


        binding.btnSendOtp.setOnClickListener(view1 -> {

            if(binding.etMobileNumber.getText().toString().isEmpty()||binding.etMobileNumber.getText().toString().length()<10){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_enter_10digit_mobile_number));
            }else {
                customProgressDialog.showProgress(getCurrentContext().getString(R.string.dialog_loading),false);
                JSONObject otpObject =new JSONObject();
                try {
                    otpObject.accumulate("mobileno",binding.etMobileNumber.getText().toString());
                    otpObject.accumulate("message",getString(R.string.otp_greeting)+ " "+ appUtils.getRandomOtp()+ " "+getString(R.string.otp_massage));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mResultCallBack=new VolleyResult() {
                    @Override
                    public void notifySuccess(String requestType, JSONObject response) {

                    }

                    @Override
                    public void notifyError(String requestType, VolleyError error) {

                    }
                };
                volleyService.postDataVolley("sendOTP","",otpObject,mResultCallBack);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        customProgressDialog.hideProgress();
                        NavDirections action = SendOtpFragmentDirections.actionSendOtpFragmentToForgetPasswordFragment();
                        navController.navigate(action);
                    }
                }, 2000);
            }

        });
    }
}

package com.nrlm.agey.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;

import com.android.volley.VolleyError;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nrlm.agey.R;
import com.nrlm.agey.databinding.FragmentForgetPasswordBinding;
import com.nrlm.agey.databinding.FragmentSendOtpBinding;
import com.nrlm.agey.model.LoginError;
import com.nrlm.agey.network.vollyCall.VolleyResult;
import com.nrlm.agey.repository.AppRepository;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.ui.home.MonthlyTrackingFragmentThreeDirections;
import com.nrlm.agey.utils.NetworkUtils;
import com.nrlm.agey.utils.PrefrenceManager;
import com.nrlm.agey.utils.ViewUtilsKt;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPasswordFragment extends BaseFragment<AuthViewModel, FragmentForgetPasswordBinding, AppRepository, AuthViewModelfactory> {
    LayoutInflater layoutInflater;

    @Override
    public Class<AuthViewModel> getViewModel() {
        return AuthViewModel.class;
    }

    @Override
    public FragmentForgetPasswordBinding getFragmentBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        layoutInflater = inflater;
        return FragmentForgetPasswordBinding.inflate(inflater, container, false);
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


        binding.btnUpdatePw.setOnClickListener(view1 -> {
            if (binding.etUserId.getText().toString().isEmpty()) {
                ViewUtilsKt.tost(getCurrentContext(), getCurrentContext().getResources().getString(R.string.toast_enter_userid));
            } else if (binding.etMobileNumber.getText().toString().isEmpty() || binding.etMobileNumber.getText().toString().length() < 10) {
                ViewUtilsKt.tost(getCurrentContext(), getCurrentContext().getResources().getString(R.string.toast_enter_10digit_mobile_number));
            } else if (binding.etPassword.getText().toString().isEmpty() || binding.etPassword.getText().toString().length() < 6) {
                ViewUtilsKt.tost(getCurrentContext(), getCurrentContext().getResources().getString(R.string.toast_password_error_msg));
            } else if (binding.etConfirmPassword.getText().toString().isEmpty() || binding.etPassword.getText().toString().length() < 6) {
                ViewUtilsKt.tost(getCurrentContext(), getCurrentContext().getResources().getString(R.string.toast_password_error_msg));
            } else if (binding.etEnterOtp.getText().toString().isEmpty() || binding.etEnterOtp.getText().toString().length() < 4) {
                ViewUtilsKt.tost(getCurrentContext(), getCurrentContext().getResources().getString(R.string.toast_otp_error_msg));
            } else {

                if (NetworkUtils.isInternetOn(getCurrentContext())) {
                    customProgressDialog.showProgress(getCurrentContext().getResources().getString(R.string.dialog_loading), false);
                    JSONObject restObject = new JSONObject();
                    try {
                        restObject.accumulate("mobileno", binding.etMobileNumber.getText().toString());
                        restObject.accumulate("password", appUtils.getSha256(binding.etConfirmPassword.getText().toString().trim()));
                        restObject.accumulate("login_id", binding.etUserId.getText().toString());
                        restObject.accumulate("imei_no", "");
                        restObject.accumulate("device_name", "");
                        restObject.accumulate("location_coordinate", "");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mResultCallBack = new VolleyResult() {
                        @Override
                        public void notifySuccess(String requestType, JSONObject response) {
                            customProgressDialog.hideProgress();

                            try {
                                if (response.getString("status").equalsIgnoreCase("Updated Successfully!!!")) {
                                    new MaterialAlertDialogBuilder(getContext()).setTitle( getCurrentContext().getResources().getString(R.string.dialog_reset_password)).setIcon(R.drawable.ic_baseline_signal_wifi_connected_no_internet)
                                            .setMessage(getCurrentContext().getResources().getString(R.string.dialog_reset_done_msg))
                                            .setPositiveButton(getCurrentContext().getResources().getString(R.string.dialog_ok_btn), (dialogInterface, i) -> {
                                                dialogInterface.dismiss();
                                                NavDirections action = ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToAuthFragment();
                                                navController.navigate(action);
                                            }).show();
                                } else {

                                    String msg =  response.getString("status") + getCurrentContext().getResources().getString(R.string.dialog_error_reset_msg);

                                    new MaterialAlertDialogBuilder(getContext()).setTitle(getCurrentContext().getResources().getString(R.string.dialog_reset_password)).setIcon(R.drawable.ic_baseline_signal_wifi_connected_no_internet)
                                            .setMessage(msg)
                                            .setPositiveButton(getCurrentContext().getResources().getString(R.string.dialog_ok_btn), (dialogInterface, i) -> {
                                                dialogInterface.dismiss();
                                                binding.etUserId.setText("");
                                                binding.etMobileNumber.setText("");
                                                binding.etPassword.setText("");
                                                binding.etConfirmPassword.setText("");
                                                binding.etEnterOtp.setText("");
                                            }).setNegativeButton(getCurrentContext().getResources().getString(R.string.dialog_go_to_login), (dialogInterface, i) -> {
                                        dialogInterface.dismiss();
                                        NavDirections action = ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToAuthFragment();
                                        navController.navigate(action);
                                    }).show();
                                }
                            } catch (Exception e) {
                                appUtils.showLog("***expection in json****" + e, ForgetPasswordFragment.class);

                            }
                        }

                        @Override
                        public void notifyError(String requestType, VolleyError error) {

                        }
                    };
                    volleyService.postDataVolley("resetPassword", "https://nrlm.gov.in/nrlmwebservicedemo/services/forgotagey/resetPassword", restObject, mResultCallBack);


                } else {
                    appUtils.showLog("*****NetWork is OFFF****", ForgetPasswordFragment.class);
                    viewModel.noInterNetConnection(getCurrentContext());
                }
            }
        });


    }
}

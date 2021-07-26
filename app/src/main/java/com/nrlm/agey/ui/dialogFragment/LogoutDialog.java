package com.nrlm.agey.ui.dialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.nrlm.agey.R;
import com.nrlm.agey.repository.HomeRepository;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.ui.home.HomeActivity;
import com.nrlm.agey.ui.login.AuthActivity;
import com.nrlm.agey.utils.AppDateFactory;
import com.nrlm.agey.utils.AppSharedPreferences;
import com.nrlm.agey.utils.AppUtils;
import com.nrlm.agey.utils.CustomProgressDialog;
import com.nrlm.agey.utils.ViewUtilsKt;

public class LogoutDialog extends DialogFragment {
    AlertDialog alertDialog;
    CustomProgressDialog customProgressDialog;
    HomeRepository homeRepository;
    AppUtils appUtils;
    AppSharedPreferences appSharedPreferences;
    AppDateFactory appDateFactory;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        customProgressDialog =CustomProgressDialog.newInstance(requireContext());
        homeRepository = HomeRepository.getInstance(getActivity().getApplication());
        appUtils=AppUtils.getInstance();
        appDateFactory= AppDateFactory.getInstance();
        appSharedPreferences=AppSharedPreferences.getInstance(requireContext());
        alertDialog =   new MaterialAlertDialogBuilder(requireContext()).setIcon(R.drawable.ic_baseline_logout)
                .setTitle(getContext().getResources().getString(R.string.dialog_sign_out_title)).setMessage(getContext().getResources().getString(R.string.dialog_sign_out_msg))
                .setCancelable(false)
                .setPositiveButton(getContext().getResources().getString(R.string.dialog_btn_signout), (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    ViewUtilsKt.tost(requireContext(),getContext().getResources().getString(R.string.toast_success_sign_out));
                    homeRepository.deleteTables();
                    appSharedPreferences.removeDataAtLogout();
                    appSharedPreferences.setLogOutTime(appDateFactory.getDateTime());
                    Intent intent = new Intent(getContext(), AuthActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);
                    getActivity().finish();

                }).setNegativeButton(getContext().getResources().getString(R.string.dialog_cancel_btn), (dialogInterface, i) -> {
            dialogInterface.dismiss();
        }).show();
        setCancelable(false);
        return alertDialog;
    }
}

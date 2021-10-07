package com.nrlm.agey.ui.mpin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.nrlm.agey.R;
import com.nrlm.agey.database.entity.UserDetailEntity;
import com.nrlm.agey.databinding.FragmentSetMpinBinding;
import com.nrlm.agey.databinding.FragmentVerifyMpinBinding;
import com.nrlm.agey.repository.MpinRepository;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.ui.home.HomeActivity;
import com.nrlm.agey.utils.ViewUtilsKt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VerifyMpinFragment extends BaseFragment<MpinViewModel, FragmentVerifyMpinBinding, MpinRepository, MpinViewModelfactory> {
    int counter;

    @Override
    public Class<MpinViewModel> getViewModel() {
        return MpinViewModel.class;
    }

    @Override
    public FragmentVerifyMpinBinding getFragmentBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentVerifyMpinBinding.inflate(inflater, container, false);
    }

    @Override
    public MpinRepository getFragmentRepository() {
        return MpinRepository.getInstance(getActivity().getApplication());
    }

    @Override
    public Context getCurrentContext() {
        return getContext();
    }

    @Override
    public MpinViewModelfactory getFactory() {
        return new MpinViewModelfactory(getFragmentRepository());
    }

    @Override
    public void onFragmentReady() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        counter = Integer.parseInt(appSharedPreferences.getMpinCount());

        List<UserDetailEntity> userdata = viewModel.getUserData();
        binding.tvUsermsg.setText(getCurrentContext().getResources().getString(R.string.tv_mpin_user_msg));
        String str = "";
        for (UserDetailEntity data : userdata) {
            str = getCurrentContext().getResources().getString(R.string.tv_mpin_userId) + data.user_id;
        }
        binding.tvUserDetail.setText(str);

        binding.btnLogout.setOnClickListener(view1 -> {
            navController.navigate(R.id.logoutDialog2);

        });

        binding.btnVerify.setOnClickListener(view1 -> {
            String getMpin = binding.pinviewGetMpin.getText().toString();

            if (isTimeValidForLogin()) {
                binding.tvMpieError.setVisibility(View.GONE);
            }

            if (counter < 1) {
                if (appSharedPreferences.getCountDownTime().equalsIgnoreCase("")) {
                    checkTime();
                    binding.tvMpieError.setVisibility(View.VISIBLE);
                    binding.tvMpieError.setText("Wrong PIN limit reached. Try after 15 minutes.");
                    ViewUtilsKt.tost(getCurrentContext(), "not allowed");
                } else {
                    binding.tvMpieError.setVisibility(View.VISIBLE);
                    binding.tvMpieError.setText("Wrong PIN limit reached. Try after 15 minutes.");
                    ViewUtilsKt.tost(getCurrentContext(), "not allowed");
                }

            } else {
                if (getMpin.isEmpty()) {
                    ViewUtilsKt.tost(getCurrentContext(), getCurrentContext().getResources().getString(R.string.toast_mpin_not_empty));
                } else {
                    if (getMpin.equalsIgnoreCase(appSharedPreferences.getMpin())) {
                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else {
                        ViewUtilsKt.tost(getCurrentContext(), getCurrentContext().getResources().getString(R.string.toast_mpin_wrong_msg));
                        binding.pinviewGetMpin.setText("");
                        binding.tvMpieError.setVisibility(View.VISIBLE);
                        binding.tvMpieError.setText("Wrong PIN " + counter + " attempt remaining.");
                        counter = counter - 1;
                        appSharedPreferences.setMpinCount("" + counter);
                        ViewUtilsKt.tost(getCurrentContext(), "" + counter);
                    }
                }
            }


        });


    }

    private void checkTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        appUtils.showLog("diff time   ::" + df.format(calendar.getTime()), VerifyMpinFragment.class);
        // Add 10 minutes to current date
        calendar.add(Calendar.MINUTE, 1);
        appUtils.showLog("time after 10  min.  ::" + df.format(calendar.getTime()), VerifyMpinFragment.class);
        appSharedPreferences.setCountDownTime("" + df.format(calendar.getTime()));

        df.format(calendar.getTime());

    }

    public boolean isTimeValidForLogin() {
        boolean status = false;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if (appSharedPreferences.getCountDownTime().equalsIgnoreCase("")) {
            status = false;
        } else {
            Date savedDateTime = getDateFormate(appSharedPreferences.getCountDownTime());
            Date currentDateTime = getDateFormate(df.format(calendar.getTime()));
            appUtils.showLog("savedDateTime   ::" + savedDateTime, VerifyMpinFragment.class);
            appUtils.showLog("currentDateTime   ::" + currentDateTime, VerifyMpinFragment.class);
            if (currentDateTime.compareTo(savedDateTime) >= 0) {
                status = true;
                appSharedPreferences.setCountDownTime("");
                appSharedPreferences.setMpinCount("3");
            }
        }
        return status;
    }


    public Date getDateFormate(String date) {
        Date convertedDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            convertedDate = sdf.parse(date);
            sdf.format(convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public void goToNextScreen() {

    }

}

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
            checkTime();
            String getMpin = binding.pinviewGetMpin.getText().toString();
            if (counter <=1 ) {

                binding.tvMpieError.setVisibility(View.VISIBLE);
                binding.tvMpieError.setText("Wrong PIN limit reached. Try after 15 minutes.");
                ViewUtilsKt.tost(getCurrentContext(), "not allowed");
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
                        binding.tvMpieError.setText("Wrong PIN "+ counter+" attempt remaining.");
                        appSharedPreferences.setMpinCount("" + counter);
                        counter=counter-1;
                        ViewUtilsKt.tost(getCurrentContext(), "" + counter);
                    }
                }
            }
        });


    }

    private void checkTime() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
        Date date1 = null;
        try {
            date1 = format.parse("08:00:12 pm");
            Date date2 = format.parse("05:30:12 pm");
            long mills = date1.getTime() - date2.getTime();
           /* Log.v("Data1", ""+date1.getTime());
            Log.v("Data2", ""+date2.getTime());*/
           /* int hours = (int) (mills/(1000 * 60 * 60));
            int mins = (int) (mills % (1000*60*60));*/


            long millis = date1.getTime() - date2.getTime();
            int hours = (int) (millis / (1000 * 60 * 60));
            int mins = (int) ((millis / (1000 * 60)) % 60);

            String diff = hours + ":" + mins;

            appUtils.showLog("diff time   ::"+diff,VerifyMpinFragment.class);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}

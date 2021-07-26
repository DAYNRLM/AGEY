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

import java.util.List;

public class VerifyMpinFragment extends BaseFragment<MpinViewModel, FragmentVerifyMpinBinding,MpinRepository,MpinViewModelfactory> {
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
        return  new MpinViewModelfactory(getFragmentRepository());
    }

    @Override
    public void onFragmentReady() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<UserDetailEntity> userdata=viewModel.getUserData();
        binding.tvUsermsg.setText(getCurrentContext().getResources().getString(R.string.tv_mpin_user_msg));
        String str ="";
        for(UserDetailEntity data:userdata){
             str = getCurrentContext().getResources().getString(R.string.tv_mpin_userId)+data.user_id;
        }
        binding.tvUserDetail.setText(str);

        binding.btnLogout.setOnClickListener(view1 -> {
            navController.navigate(R.id.logoutDialog2);

        });

        binding.btnVerify.setOnClickListener(view1 -> {
            String getMpin =binding.pinviewGetMpin.getText().toString();
            if(getMpin.isEmpty()){
                ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_mpin_not_empty));
            }else {
                if(getMpin.equalsIgnoreCase(appSharedPreferences.getMpin())){
                    Intent intent =new Intent(getContext(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else {
                    ViewUtilsKt.tost(getCurrentContext(),getCurrentContext().getResources().getString(R.string.toast_mpin_wrong_msg));
                }
            }
        });
    }


    /* MaterialButton btn_verify;

    public VerifyMpinFragment() {
        super(R.layout.fragment_verify_mpin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_verify =view.findViewById(R.id.btn_verify);

        btn_verify.setOnClickListener(view1 -> {
            Intent intent =new Intent(getContext(), HomeActivity.class);
            startActivity(intent);
        });
    }

    *//*  public static VerifyMpinFragment newInstance() {
        VerifyMpinFragment veriFyMpin = new VerifyMpinFragment();
        return veriFyMpin;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_verify_mpin;
    }

    @Override
    public void onFragmentReady() {

    }*/
}

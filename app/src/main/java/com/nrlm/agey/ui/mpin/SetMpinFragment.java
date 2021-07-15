package com.nrlm.agey.ui.mpin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.nrlm.agey.R;
import com.nrlm.agey.databinding.FragmentMonthlyTrackingBinding;
import com.nrlm.agey.databinding.FragmentSetMpinBinding;
import com.nrlm.agey.repository.HomeRepository;
import com.nrlm.agey.repository.MpinRepository;
import com.nrlm.agey.ui.BaseFragment;
import com.nrlm.agey.ui.home.HomeViewModel;
import com.nrlm.agey.ui.home.HomeViewModelFactory;
import com.nrlm.agey.utils.ViewUtilsKt;

public class SetMpinFragment extends BaseFragment<MpinViewModel, FragmentSetMpinBinding, MpinRepository,MpinViewModelfactory> {
    @Override
    public Class<MpinViewModel> getViewModel() {
            return MpinViewModel.class;
    }

    @Override
    public FragmentSetMpinBinding getFragmentBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentSetMpinBinding.inflate(inflater, container, false);
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



        binding.btnSetMpin.setOnClickListener(view1 -> {
            String mpin =binding.pinviewFirst.getText().toString();
            String verifyMpin = binding.pinviewSecond.getText().toString();

            if(mpin.isEmpty()){
                ViewUtilsKt.tost(getContext(),"set Mpin First");
            }else if(verifyMpin.isEmpty()){
                ViewUtilsKt.tost(getContext(),"Confirm Mpin First");
            }else {
                if(mpin.equalsIgnoreCase(verifyMpin)){
                    appSharedPreferences.setMpin(verifyMpin);
                    NavDirections action = SetMpinFragmentDirections.actionSetMpinFragment2ToVerifyMpinFragment();
                    navController.navigate(action);

                }else {
                    ViewUtilsKt.tost(getContext(),"Confirm Mpin and set Mpin is not Matched");
                }
            }

        });


    }

    /* MaterialButton btn_setMpin;

    public SetMpinFragment() {
        super(R.layout.fragment_set_mpin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = NavHostFragment.findNavController(this);

        btn_setMpin =view.findViewById(R.id.btn_setMpin);

        btn_setMpin.setOnClickListener(view1 -> {

            *//*****directly used by destination id*****//*
           // navController.navigate(R.id.verifyMpinFragment);

            *//****directly used by action id action created in nav_graph********//*
           // navController.navigate(R.id.action_setMpinFragment2_to_verifyMpinFragment);


            *//***********using safe arge library****************//*
            NavDirections action = SetMpinFragmentDirections.actionSetMpinFragment2ToVerifyMpinFragment();
            navController.navigate(action);
        });



    }



    *//* public static SetMpinFragment newInstance() {
        SetMpinFragment setMpinFrag = new SetMpinFragment();
        return setMpinFrag;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_set_mpin;
    }

    @Override
    public void onFragmentReady() {

    }*/


}
